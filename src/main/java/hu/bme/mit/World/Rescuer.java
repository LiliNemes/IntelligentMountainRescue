package hu.bme.mit.World;

import java.util.ArrayList;
import java.util.List;

public abstract class Rescuer {
    Field allocatedToInjured;
    Field currentLocation;
    List<Field> path = new ArrayList<>();

    public abstract Action step(boolean bothCanStep);


    public Field getCurrentLocation() {
        return this.currentLocation;
    }

    public void injuredFallen(Field location) {
        if(location == allocatedToInjured){
            allocatedToInjured = null;
            path.clear();
        }
    }
}
