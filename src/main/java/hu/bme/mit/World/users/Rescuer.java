package hu.bme.mit.World.users;

import hu.bme.mit.World.fields.Direction;
import hu.bme.mit.World.fields.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rescuer in the world.
 * targetLocation is the location where the rescuer is heading to.
 * currentLocation is the location where the rescuer is currently.
 * path is the path the rescuer is following to get to the targetLocation.
 * id is the id of the rescuer.
 */
public abstract class Rescuer implements Drawable {
    // target location is not null only if the target is an injured, if it's a station,
    // it should be set to null, but the path not
    protected Field targetLocation;
    protected Field currentLocation;
    protected List<Direction> path = new  ArrayList<>();
    protected final int id;

    public Rescuer(Field location, int id){
        this.currentLocation = location;
        location.addVisitor(this);
        this.id = id;
    }

    /**
     * The rescuer steps in every timeslot.
     * @return the Action a rescuer did while stepping.
     */
    public abstract Action step(boolean bothCanStep);

    /**
     * Returns the location of the rescuer.
     * @return the location of the rescuer.
     */
    public Field getCurrentLocation() {
        return this.currentLocation;
    }

    /**
     * Checks if the injured who died was the target location of the rescuer. If yes, deletes the target and the path.
     * @param location the location of the injured who died.
     */
    public void injuredFallen(Field location) {
        if(location == targetLocation){
            targetLocation = null;
            path.clear();
        }
    }

    /**
     * Returns the ID of the rescuer.
     * @return the ID of the rescuer.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Checks if the rescuer can get to the field.
     * @param field the field to check.
     * @return true if the rescuer can get to the field, false otherwise.
     */
    public boolean canGetThere(Field field){
        return true;
    }

    /**
     * Sets the target location of the rescuer.
     * @param location the target location of the rescuer.
     */
    public void setTargetLocation(Field location){
        this.targetLocation = location;
    }

    /**
     * Sets the path of the rescuer.
     * @param path the path of the rescuer.
     */
    public void setPath(List<Direction> path){
        this.path = path;
    }

    /**
     * Resets the target location and the path of the rescuer.
     */
    public void resetTarget() {
        this.targetLocation = null;
        this.path.clear();
    }

    /**
     * Calculate the rescuer's weighted distance based on its speed
     * if the rescuer is a slower one, he/she should multiply the got distance by his/her slowness
     * @param physicalDistance the distance to weigh
     * @return the weighted distance
     */
    public abstract int getWeightedDistance(int physicalDistance);
}
