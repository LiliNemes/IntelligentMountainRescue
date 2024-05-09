package hu.bme.mit.World;

public class Injured {
    int ID;
    int health;
    Field location;
    public Injured(int health, Field location, int ID){
        this.health = health;
        this.location = location;
        this.ID = ID;
    }

    public boolean step(){
        health--;
        return health == 0;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Field getLocation() {
        return location;
    }

    public void setLocation(Field location) {
        this.location = location;
    }
}
