package hu.bme.mit.World.users;

import hu.bme.mit.World.fields.Field;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class for the Helicopter rescuer.
 */
public class Helicopter extends Rescuer{

    private final String imageName = "helicopter.png";

    public Helicopter(Field location, int id) {
        super(location, id);
    }

    @Override
    public boolean canGetThere(Field field){
        return field.getFlatness();
    }

    /**
     * The helicopter can move in every time step, but cannot land on mountain fields.
     * @param bothCanStep true if both rescuers can step, false otherwise (if only the helicopter can step).
     * @return the action of the helicopter.
     */
    @Override
    public Action step(boolean bothCanStep) {
        //TODO
        //Lépjen a path-ját követve.
        //Ha elér az emberéhez vegye fel.
        //Olyan Actionnel térjen vissza ami igaz arra amit csinált.
        return Action.MOVE;
    }

    /**
     * Draws the helicopter on the field.
     * @param g the graphics object.
     * @param fieldSize the size of the field.
     */
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
