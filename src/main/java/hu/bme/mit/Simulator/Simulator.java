package hu.bme.mit.Simulator;

import hu.bme.mit.RescueFramework.RescueFramework;
import hu.bme.mit.World.fields.Field;
import hu.bme.mit.World.fields.Map;
import hu.bme.mit.World.users.Action;
import hu.bme.mit.World.users.Injured;
import hu.bme.mit.World.users.Rescuer;

import java.util.ArrayList;
import java.util.HashMap;

public class Simulator {
    private final HashMap<Integer, Injured> injureds = new HashMap<>();
    private final ArrayList<Injured> toRemove = new ArrayList<>();
    private final HashMap<Integer, Rescuer> rescuers = new HashMap<>();
    private int time=0;
    private final int helicopterSpeed = 3;
    private TimeStepper timeStepper = null;
    private boolean stopped = true;
    private int savedPeople = 0;
    private int deadPeople = 0;

    public void start(){
        if(timeStepper!=null){
            timeStepper.stop();
        }
        stopped = false;
        timeStepper = new TimeStepper(this);
        new Thread(timeStepper).start();
    }

    public void step(){
        time++;
        boolean bothCanStep = time % helicopterSpeed == 0;
        boolean deathHappened = false;

        for (Rescuer rescuer : rescuers.values()){
            Action whatHappened = rescuer.step(bothCanStep);
            switch(whatHappened){
                case MOVE -> {}
                case PICKUP -> rescuedPerson(rescuer.getCurrentLocation());
                case DELIVER -> deliveredPerson();
            }
        }
        for (Injured injured : injureds.values()){
            boolean isDead = injured.step();
            if(isDead){
                injuredTragedy(injured);
            }
        }

        RescueFramework.refresh();
    }

    private void rescuedPerson(Field location){
        for(Integer injuredId : injureds.keySet()){
           if ( injureds.get(injuredId).getLocation() == location){
               injureds.remove(injuredId);
               break;
           }
        }
    }

    public void setRescuerTarget(int rescuerId, int injuredId){
        rescuers.get(rescuerId).setTargetLocation(injureds.get(injuredId).getLocation());
    }

    private void deliveredPerson(){
        this.savedPeople++;
        optimization();
    }

    private void injuredTragedy(Injured injured){
        this.deadPeople++;
        for (Rescuer rescuer : rescuers.values()) {
            rescuer.injuredFallen(injured.getLocation());
        }
        optimization();
    }

    private void optimization(){
        // storing the bids: HashMap<rescuer, HasMap<injured, distance>>
        HashMap<Integer, HashMap<Integer, Integer>> bids = new HashMap<>();
        for (Rescuer rescuer : rescuers.values()){
            HashMap<Integer, Integer> distances = new HashMap<>();
            for (Injured injured : injureds.values()){
                if (rescuer.canGetThere(injured.getLocation()))
                    distances.put(injured.getId(),
                            Map.getPath(rescuer.getCurrentLocation(), injured.getLocation()).size());
            }
            if (!distances.isEmpty())
                bids.put(rescuer.getId(), distances);
        }

        if (!bids.isEmpty()){
            RescueFramework.getEnvironment().setBids(bids);
            RescueFramework.getEnvironment().informRescuers();
        }
    }

    public void addRescuer(Rescuer rescuer) {
        this.rescuers.put(rescuer.getId(), rescuer);
    }

    public void addInjured(Injured injured){
        injureds.put(injured.getId(), injured);
        optimization();
    }

    public void stop() {
        stopped = true;
        if (timeStepper != null)
            timeStepper.stop();
    }

    public int getSavedCount() {
        return savedPeople;
    }

    public int getDeadCount() {
        return deadPeople;
    }

    public void reset() {
        stop();
        rescuers.clear();
        injureds.clear();
        deadPeople = 0;
        savedPeople = 0;
        time = 0;
    }

    public void setSpeed(int speed) {
        timeStepper.setSpeed(speed);
    }
}
