package hu.bme.mit.Simulator;
import hu.bme.mit.RescueFramework.RescueFramework;
import jason.NoValueException;
import jason.asSyntax.*;
import jason.environment.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Environment for the simulation.
 * currentBids: the current bids of the rescuers.
 * toRemove: the list of injureds and rescuers to be removed from the current bids.
 * agentNames: the names of the agents mapped with their IDs.
 * agentIds: the ids of the agents mapped with their namess.
 * controllerName: the name of the controller agent.
 */
public class Env extends Environment{

    private final HashMap<Integer, HashMap<Integer, Integer>> baseBids = new HashMap<>();
    private final HashMap<Integer, HashMap<Integer, Integer>> currentBids = new HashMap<>();
    private int iteration = 1;
    private int costSum = 0;

    private final ArrayList<Integer> toRemove = new ArrayList<>();
    private final HashMap<Integer, String> agentNames = new HashMap<>();
    private final HashMap<String, Integer> agentIds = new HashMap<>();
    private final String controllerName = "controller";

    public HashMap<String, Integer> getAgentIds() {
        return agentIds;
    }

    /**
     * Sets the bids of the rescuers.
     * @param bids the bids of the rescuers (every rescuer bids for every reachable injured).
     */
    public void setBids(HashMap<Integer, HashMap<Integer, Integer>> bids) {
        currentBids.clear();
        baseBids.clear();

        for (Integer rescuerId : bids.keySet()){
            //Updates the current bids for every rescuer, if a rescuer is new it adds it to the currentBids map.
            for (Integer injuredId : bids.get(rescuerId).keySet()){
                if (!baseBids.containsKey(rescuerId)){
                    baseBids.put(rescuerId, new HashMap<>());
                    currentBids.put(rescuerId, new HashMap<>());
                }
                baseBids.get(rescuerId).put(injuredId, bids.get(rescuerId).get(injuredId));
                currentBids.get(rescuerId).put(injuredId, bids.get(rescuerId).get(injuredId));
            }
        }
        iteration = 1;
        costSum = 0;
    }

    public HashMap<Integer, String> getAgentNames() {
        return agentNames;
    }

    /**
     * Inicializálja a játékot, kontroller ágenst.
     */
    @Override
    public void init(String[] args){
        super.init(args);

        addPercept(controllerName, Literal.parseLiteral("name").addTerms(new StringTermImpl(controllerName)));

        RescueFramework.setEnvironment(this);
        RescueFramework.start();
    }

    /**
     * Executes the action.
     * @param agName the name of the agent.
     * @param action the action to be executed.
     * @return true if the action was executed with success, false otherwise.
     */
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

    /**
     * Updates the current bids.
     * @param rescuerId the id of the rescuer.
     * @param injuredId the id of the injured.
     */
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

        // update the bidding parameters
        costSum += baseBids.get(rescuerId).get(injuredId);
        iteration++;

        for (Integer rescuer : currentBids.keySet()){
            for (Integer injured : currentBids.get(rescuer).keySet()){
                currentBids.get(rescuer).put(injured, (costSum + baseBids.get(rescuer).get(injured)) * 10 / iteration);
            }
        }

        // notify the rescuer about the decision
        RescueFramework.getSimulator().allocateInjured(rescuerId, injuredId);
        // if there's any injured and rescuer left, inform the rescuers
        // - if there's any rescuer, it must have a not empty injured map
        if (!currentBids.isEmpty()){
            informRescuers();
        }
    }

    /**
     * Stops the simulation.
     */
    @Override
    public void stop() {
        super.stop();

        RescueFramework.getSimulator().stop();
    }

    /**
     * Informs the rescuers about the new bids.
     */
    public void informRescuers() {
        clearAllPercepts();

        int counter = 0;
        //For every rescuer.
        for (Integer rescuerId : currentBids.keySet()){
            //For every injured it can reach.
            for (Integer injuredId : currentBids.get(rescuerId).keySet()){
                //Adding new perception to the agent in the form of injured - bid for this injured.
                addPercept(agentNames.get(rescuerId), Literal.parseLiteral("newBid").addTerms(new NumberTermImpl(injuredId), new NumberTermImpl(currentBids.get(rescuerId).get(injuredId))));
                counter++;
            }
            informAgsEnvironmentChanged(agentNames.get(rescuerId));
        }
        //Adding perception to the controller agent about the remaining bids and initializing the best cost for the injureds.
        addPercept(controllerName, Literal.parseLiteral("remaining").addTerms(new NumberTermImpl(counter)));
        addPercept(controllerName, Literal.parseLiteral("bestCost").addTerms(
                new NumberTermImpl(RescueFramework.getMap().getHeight() + RescueFramework.getMap().getWidth())
        ));
        informAgsEnvironmentChanged(controllerName);
    }

    /**
     * Adds a new agent to the simulation.
     * @param name the name of the agent.
     * @param id the id of the agent.
     */
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

    /**
     * Resets the simulation.
     */
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
        baseBids.clear();
    }
}
