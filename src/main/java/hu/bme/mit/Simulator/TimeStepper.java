package hu.bme.mit.Simulator;

/**
 * The time stepper class for the simulation.
 * stopped: the state of the time stepper.
 * speed: the speed of the time stepper.
 * simulator: the simulator of the time stepper.
 */
public class TimeStepper implements Runnable{

    private boolean stopped;
    private int speed;
    private final Simulator simulator;

    public TimeStepper(Simulator simulator){
        stopped = false;
        speed = 1000;

        this.simulator = simulator;
    }

    /**
     * Steps the simulation.
     */
    public void run() {

        while(!stopped)
        {
            simulator.step();

            try {
                Thread.sleep(speed);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops the time stepper.
     */
    public void stop() {
        stopped = true;
    }

    /**
     * Sets the speed of the time stepper.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
