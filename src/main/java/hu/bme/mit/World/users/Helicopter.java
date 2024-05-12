package hu.bme.mit.World.users;

import hu.bme.mit.World.fields.Field;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Helicopter extends Rescuer{

    private final String imageName = "helicopter.png";

    public Helicopter(Field location, int id) {
        super(location, id);
    }

    @Override
    public Action step(boolean bothCanStep) {
        //TODO
        return Action.MOVE;
    }

    @Override
    public void draw(Graphics2D g, int fieldSize) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(currentLocation.getBaseDir() + imageName));
        } catch (IOException ex) {
            System.err.println("Helicopter image not found: " + currentLocation.getBaseDir() + imageName);
        }
        g.drawImage(img, currentLocation.getCoordinate().x() * fieldSize + 6, currentLocation.getCoordinate().y()
                * fieldSize + 2, fieldSize - 6, fieldSize - 12, null);
    }
}
