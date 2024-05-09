package hu.bme.mit.RescueFramework;

import hu.bme.mit.World.Field;
import hu.bme.mit.World.FieldType;
import hu.bme.mit.World.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PaintPanel extends JPanel {
    private int cellSize = 0;

    public PaintPanel(){
        super();
        setDoubleBuffered(true);
        setBackground(new Color(2, 35, 145));


    }

    @Override
    public void paintComponent(Graphics g){
        Map map = RescueFramework.map;
        if (map == null) {
            return;
        }
        // Calculate cellSize
        int cellWidth = (int)Math.floor(getWidth()/map.getWidth());
        int cellHeight = (int)Math.floor(getHeight()/map.getHeight());
        cellSize = Math.min(cellWidth, cellHeight);

        // Convert Graphics to Graphics2D
        Graphics2D g2 = (Graphics2D) g;

        // Paint cells one by one
        for (int x = 0; x<map.getWidth(); x++) {
            for (int y = 0; y<map.getHeight(); y++) {
                Field field = map.getField(x, y);

                // Paint custom floor color
                if (field.getType()== FieldType.FOREST) {
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(new File("images/forest1.jpg"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    g2.drawImage(img, x*cellSize+1, y*cellSize+1, cellSize-1, cellSize-1, null);
                }
                else if(field.getType()== FieldType.MOUNTAIN){
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(new File("images/mountain1.jpg"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    g2.drawImage(img, x*cellSize+1, y*cellSize+1, cellSize-1, cellSize-1, null);
                }
                else {
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(new File("images/station1.jpg"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    g2.drawImage(img, x*cellSize+1, y*cellSize+1, cellSize-1, cellSize-1, null);
                }
            }
        }
    }

    public void mouseClicked(int x, int y) {
        //RescueFramework.log("Click at "+x+"x"+y);
        if (cellSize == 0) return;

        // Determine cell
        int cellX = x/cellSize;
        int cellY = y/cellSize;

        /*// Move the first robot if there is one
        Robot r = RescueFramework.map.getRobots().get(0);
        if (r != null) {
            RescueFramework.map.moveRobot(r, RescueFramework.map.getCell(cellX, cellY));
        }
        */

    }

}
