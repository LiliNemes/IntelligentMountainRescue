package hu.bme.mit.RescueFramework;

import hu.bme.mit.World.Map;

public class RescueFramework {
    // The map the simulator uses
    static Map map = null;
    static MainFrame mainFrame = null;
    public static void main(String[] args) {
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
