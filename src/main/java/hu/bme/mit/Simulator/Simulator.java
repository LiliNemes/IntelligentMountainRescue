package hu.bme.mit.Simulator;

import hu.bme.mit.RescueFramework.RescueFramework;
import hu.bme.mit.World.fields.Direction;
import hu.bme.mit.World.fields.Field;
import hu.bme.mit.World.fields.Map;
import hu.bme.mit.World.users.Action;
import hu.bme.mit.World.users.Injured;
import hu.bme.mit.World.users.Rescuer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The simulator class for the simulation.
 * injureds: the list of injureds in the simulation.
 * toRemove: the list of injureds and rescuers to be removed from the simulation.
 * rescuers: the list of rescuers in the simulation.
 * time: the current time of the simulation.
 * helicopterSpeed: the speed of the helicopter.
 * timeStepper: the timeStepper of the simulation.
 * stopped: the state of the simulation.
 * savedPeople: the number of saved people.
 * deadPeople: the number of dead people.
 */
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

    /**
     * Starts the simulation.
     */
    public void start(){
        if(timeStepper!=null){
            timeStepper.stop();
        }
        stopped = false;
        timeStepper = new TimeStepper(this);
        new Thread(timeStepper).start();
    }

    /**
     * Steps the simulation.
     */
    public void step(){
        time++;
        boolean bothCanStep = time % helicopterSpeed == 0;
        boolean deathHappened = false;

        for (Rescuer rescuer : rescuers.values()){
            Action whatHappened = rescuer.step(bothCanStep);
            switch(whatHappened){
                case PICKUP -> rescuedPerson(rescuer, rescuer.getCurrentLocation());
                case DELIVER -> deliveredPerson();
                default -> {}
            }
        }
        for (Injured injured : injureds.values()){
            boolean isDead = injured.step();
            if(isDead){
                injuredTragedy(injured);
                toRemove.add(injured);
                deathHappened = true;
            }
        }

        if (deathHappened){
            for (Injured injured : toRemove){
                injureds.remove(injured.getId());
            }
            toRemove.clear();
            optimization();
        }

        RescueFramework.refresh();
    }

    /**
     * Handles the case when an injured is rescued (picked up).
     * @param location the location of the rescued injured.
     */
    private void rescuedPerson(Rescuer rescuer, Field location){
        for(Integer injuredId : injureds.keySet()){
           if ( injureds.get(injuredId).getLocation() == location){
               injureds.remove(injuredId);
               break;
           }
        }
        List<Direction> path = RescueFramework.getMap().getNearestStation(location);
        rescuer.setPath(path);
    }

    /**
     * Sets the target location of the rescuer.
     * @param rescuerId the id of the rescuer.
     * @param injuredId the id of the injured.
     */
    public void allocateInjured(int rescuerId, int injuredId){
        List<Direction> path = Map.getPath(rescuers.get(rescuerId).getCurrentLocation(), injureds.get(injuredId).getLocation());
        rescuers.get(rescuerId).setTargetLocation(injureds.get(injuredId).getLocation());
        rescuers.get(rescuerId).setPath(path);
    }

    /**
     * Handles the case when an injured is delivered.
     */
    private void deliveredPerson(){
        this.savedPeople++;
        optimization();
    }

    /**
     * Handles the case when an injured dies.
     * @param injured the injured who died.
     */
    private void injuredTragedy(Injured injured){
        this.deadPeople++;
        for (Rescuer rescuer : rescuers.values()) {
            rescuer.injuredFallen(injured.getLocation());
        }
    }

    /**
     * Starts the bidding.
     */
    private void optimization(){
        // storing the bids: HashMap<rescuer, HasMap<injured, distance>>
        HashMap<Integer, HashMap<Integer, Integer>> bids = new HashMap<>();
        // for each rescuer
        for (Rescuer rescuer : rescuers.values()){
            HashMap<Integer, Integer> distances = new HashMap<>();
            //for each injured calculates the distance from the rescuer.
            for (Injured injured : injureds.values()){
                if (rescuer.canGetThere(injured.getLocation()))
                    distances.put(injured.getId(),
                            Map.getPath(rescuer.getCurrentLocation(), injured.getLocation()).size());
            }
            //If the rescuer can reach at least one injured, adds the distances to the bids.
            if (!distances.isEmpty())
                bids.put(rescuer.getId(), distances);
        }
        //If there are any bids, sets the bids and informs the rescuers.
        if (!bids.isEmpty()){
            RescueFramework.getEnvironment().setBids(bids);
            RescueFramework.getEnvironment().informRescuers();
        }
    }

    /**
     * Adds a rescuer to the simulation.
     * @param rescuer the rescuer to be added.
     */
    public void addRescuer(Rescuer rescuer) {
        this.rescuers.put(rescuer.getId(), rescuer);
    }

    /**
     * Adds an injured to the simulation.
     * @param injured the injured to be added.
     */
    public void addInjured(Injured injured){
        injureds.put(injured.getId(), injured);
        optimization();
    }

    /**
     * Stops the simulation.
     */
    public void stop() {
        stopped = true;
        if (timeStepper != null)
            timeStepper.stop();
    }

    /**
     * Returns the number of saved people.
     * @return the number of saved people.
     */
    public int getSavedCount() {
        return savedPeople;
    }

    /**
     * Returns the number of dead people.
     * @return the number of dead people.
     */
    public int getDeadCount() {
        return deadPeople;
    }

    /**
     * Resets the simulation.
     */
    public void reset() {
        stop();
        rescuers.clear();
        injureds.clear();
        deadPeople = 0;
        savedPeople = 0;
        time = 0;
    }

    /**
     * Sets the speed of the simulation.
     * @param speed the speed of the simulation.
     */
    public void setSpeed(int speed) {
        timeStepper.setSpeed(speed);
    }
}
