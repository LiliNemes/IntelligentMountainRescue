package hu.bme.mit.RescueFramework;

import hu.bme.mit.Simulator.Env;
import hu.bme.mit.Simulator.Simulator;
import hu.bme.mit.World.fields.Coordinate;
import hu.bme.mit.World.fields.Map;
import hu.bme.mit.World.users.Helicopter;
import hu.bme.mit.World.users.Troop;

public class RescueFramework {
    // The map the simulator uses
    private static Map map = null;
    private static MainFrame mainFrame = null;
    private static Env env = null;
    private static Simulator simulator = null;
    private static int agentId = 0;

    public static void refresh() {
        if (mainFrame != null)
            mainFrame.refresh();
    }

    public static void setEnvironment(Env environment){
        env = environment;
    }

    public static Env getEnvironment(){
        return env;
    }

    public static Simulator getSimulator(){
        return simulator;
    }

    public static void start() {
        Loader loader = new Loader();
        loader.load();
        map = loader.getMap();
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);

    }

    public static void refresh() {
        if (mainFrame != null)  mainFrame.refresh();
    }


}
