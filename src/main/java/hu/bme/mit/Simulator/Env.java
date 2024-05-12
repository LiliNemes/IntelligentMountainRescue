package hu.bme.mit.Simulator;
import hu.bme.mit.RescueFramework.RescueFramework;
import jason.NoValueException;
import jason.asSyntax.*;
import jason.environment.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Env extends Environment{

    private final HashMap<Integer, HashMap<Integer, Integer>> currentBids = new HashMap<>();
    private final ArrayList<Integer> toRemove = new ArrayList<>();
    private final HashMap<Integer, String> agentNames = new HashMap<>();
    private final HashMap<String, Integer> agentIds = new HashMap<>();
    private final String controllerName = "controller";

    public HashMap<String, Integer> getAgentIds() {
        return agentIds;
    }

    public void setBids(HashMap<Integer, HashMap<Integer, Integer>> bids) {
        for (Integer rescuerId : bids.keySet()){
            for (Integer injuredId : bids.get(rescuerId).keySet()){
                if (!currentBids.containsKey(rescuerId)){
                    currentBids.put(rescuerId, new HashMap<>());
                }
                currentBids.get(rescuerId).put(injuredId, bids.get(rescuerId).get(injuredId));
            }
        }
    }

    public HashMap<Integer, String> getAgentNames() {
        return agentNames;
    }

    @Override
    public void init(String[] args){
        super.init(args);

        addPercept(controllerName, Literal.parseLiteral("name").addTerms(new StringTermImpl(controllerName)));

        RescueFramework.setEnvironment(this);
        RescueFramework.start();
    }

    @Override
    public boolean executeAction(String agName, Structure action){
        if (action.getFunctor().equals("optimize")){
            try {
                int rescuerId = (int) ((NumberTerm) action.getTerm(0)).solve();
                int injuredId = (int) ((NumberTerm) action.getTerm(1)).solve();
                updateCurrentBids(rescuerId, injuredId);
            }
            catch (NoValueException e){
                System.err.println("Bad usage of the optimize function!!\n" +
                        "Missing parameters...");
            }
        }
        else
            return super.executeAction(agName, action);
        return true; // the action was executed with success
    }

    private void updateCurrentBids(int rescuerId, int injuredId) {
        // remove the taken injureds
        currentBids.remove(rescuerId);
        for (Integer rescuer : currentBids.keySet()){
            currentBids.get(rescuer).remove(injuredId);
            if(currentBids.get(rescuer).isEmpty())
                toRemove.add(rescuer);
        }
        for (int rescuer : toRemove){
            currentBids.remove(rescuer);
        }
        toRemove.clear();
        // notify the rescuer about the decision
        RescueFramework.getSimulator().setRescuerTarget(rescuerId, injuredId);
        // if there's any injured and rescuer left, inform the rescuers
        // - if there's any rescuer, it must have a not empty injured map
        if (!currentBids.isEmpty()){
            informRescuers();
        }
    }

    @Override
    public void stop() {
        super.stop();

        RescueFramework.getSimulator().stop();
    }

    public void informRescuers() {
        int counter = 0;
        for (Integer rescuerId : currentBids.keySet()){
            for (Integer injuredId : currentBids.get(rescuerId).keySet()){
                addPercept(agentNames.get(rescuerId), Literal.parseLiteral("newBid").addTerms(new NumberTermImpl(injuredId), new NumberTermImpl(currentBids.get(rescuerId).get(injuredId))));
                counter++;
            }
            informAgsEnvironmentChanged(agentNames.get(rescuerId));
        }
        addPercept(controllerName, Literal.parseLiteral("remaining").addTerms(new NumberTermImpl(counter)));
        informAgsEnvironmentChanged(controllerName);
    }

    public void addAgent(String name, int id) {
        agentIds.put(name, id);
        agentNames.put(id, name);
        try{
            getEnvironmentInfraTier().getRuntimeServices().createAgent(name, "src/main/asl/rescuer.asl", null, null, null, null, null);
        }
        catch (Exception e){
            System.err.println("Agent creation failed.");
        }
    }

    public void reset() {
        for (String agName : agentNames.values()){
            try {
                getEnvironmentInfraTier().getRuntimeServices().killAgent(agName, controllerName, 0);
            }
            catch (Exception e){
                System.err.println("Agent deletion failed.");
            }
        }
        agentNames.clear();
        agentIds.clear();
        currentBids.clear();
    }
}
