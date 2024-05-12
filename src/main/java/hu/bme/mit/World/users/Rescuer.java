package hu.bme.mit.World.users;

import hu.bme.mit.World.fields.Field;

import java.util.ArrayList;
import java.util.List;

public abstract class Rescuer implements Drawable {
    protected Field targetLocation;
    protected final Field currentLocation;
    protected final List<Field> path = new  ArrayList<>();
    protected final int id;

    public Rescuer(Field location, int id){
        this.currentLocation = location;
        location.addVisitor(this);
        this.id = id;
    }

    public abstract Action step(boolean bothCanStep);


    public Field getCurrentLocation() {
        return this.currentLocation;
    }

    public void injuredFallen(Field location) {
        if(location == targetLocation){
            targetLocation = null;
            path.clear();
        }
    }

    public Integer getId() {
        return id;
    }

    public boolean canGetThere(Field field){
        return true;
    }

    public void setTargetLocation(Field location){
        this.targetLocation = location;
    }
}
