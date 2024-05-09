package hu.bme.mit.Simulator;

import hu.bme.mit.RescueFramework.RescueFramework;
import hu.bme.mit.World.Action;
import hu.bme.mit.World.Field;
import hu.bme.mit.World.Injured;
import hu.bme.mit.World.Rescuer;

import java.util.HashMap;
import java.util.List;

public class Simulator {
    private java.util.Map<Field, Injured> injureds = new HashMap<Field, Injured>();
    private List<Rescuer> rescuers;
    private int time=0;
    private int helicopterSpeed=3;
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
        for (Rescuer rescuer : rescuers){
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

    private void rescuedPerson(Field field){
        injureds.remove(field);
    }
    private void deliveredPerson(){
        this.savedPeople++;
    }
    private void injuredTragedy(Injured injured){
        injureds.remove(injured.getLocation());
        this.deadPeople++;
        for (Rescuer rescuer : rescuers){
            rescuer.injuredFallen(injured.getLocation());
        }
        optimization();
    }

    private void optimization(){

    }

    public java.util.Map<Field, Injured> getInjureds() {
        return injureds;
    }

    public void setInjureds(java.util.Map<Field, Injured> injureds) {
        this.injureds = injureds;
    }

    public List<Rescuer> getRescuers() {
        return rescuers;
    }

    public void setRescuers(List<Rescuer> rescuers) {
        this.rescuers = rescuers;
    }
    public void addRescuer(Rescuer rescuer) {
        this.rescuers.add(rescuer);
    }
    public void addInjured(Injured injured, Field field){
        this.injureds.put(field, injured);
    }


}
