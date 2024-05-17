package hu.bme.mit.RescueFramework;

import hu.bme.mit.World.fields.Field;
import hu.bme.mit.World.users.Injured;
import hu.bme.mit.World.fields.Map;

import javax.swing.*;
import java.awt.*;

public class PaintPanel extends JPanel {
    private int fieldSize = 0;
    private int InjuredId = 0;

    public PaintPanel(){
        super();
        setDoubleBuffered(true);
        setBackground(new Color(2, 35, 145));
    }

    @Override
    public void paintComponent(Graphics g){
        Map map = RescueFramework.getMap();
        if (map == null) {
            return;
        }
        // Calculate cellSize
        int cellWidth = getWidth() / map.getWidth();
        int cellHeight = getHeight() / map.getHeight();
        fieldSize = Math.min(cellWidth, cellHeight);

        // Convert Graphics to Graphics2D
        Graphics2D g2 = (Graphics2D) g;

        // Paint fields one by one
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                map.getField(x, y).draw(g2, fieldSize);
            }
        }
    }

    public void mouseClicked(int x, int y) {
        //RescueFramework.log("Click at "+x+"x"+y);
        if (fieldSize == 0) return;

        // Determine cell
        int coordinateX = x/ fieldSize;
        int coordinateY = y/ fieldSize;

        Field f = RescueFramework.getMap().getField(coordinateX, coordinateY);
        Injured injured = new Injured(InjuredId);
        if(f.addInjured(injured)){
            InjuredId++;
            RescueFramework.getSimulator().addInjured(injured);
        }
    }
}
