package hu.bme.mit.World.users;

import hu.bme.mit.World.fields.Direction;
import hu.bme.mit.World.fields.Field;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class for the Troop.
 */
public class Troop extends Rescuer{

    private final String imageName = "troop.png";
    private final int slowness = 3;

    public Troop(Field location, int id) {
        super(location, id);
    }

    /**
     * The troop can move in every third time step, but cannot land on mountain fields.
     * @param bothCanStep true if both rescuers can step, false otherwise (if only the helicopter can step).
     * @return the action of the helicopter.
     */
    @Override
    public Action step(boolean bothCanStep) {
        if (!bothCanStep) {
            return Action.MOVE;
        } else {
            if (!path.isEmpty()) {
                Direction direction = path.removeFirst();
                currentLocation.removeVisitor(this);
                currentLocation = currentLocation.getNeighbour(direction);
                currentLocation.addVisitor(this);

                if (currentLocation == targetLocation) {
                    if (!path.isEmpty())
                        throw new RuntimeException("Path should be empty when reaching the target");

                    return Action.PICKUP;
                } else if (targetLocation == null && path.isEmpty()) {
                    return Action.DELIVER;
                }

            }
            return Action.MOVE;
        }
    }

    @Override
    public int getWeightedDistance(int physicalDistance) {
        return this.slowness * physicalDistance;
    }

    /**
     * Draws the troop on the field.
     * @param g the graphics object.
     * @param fieldSize the size of the field.
     */
    @Override
    public void draw(Graphics2D g, int fieldSize) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(currentLocation.getBaseDir() + imageName));
        } catch (IOException ex) {
            System.err.println("Troop image not found: " + currentLocation.getBaseDir() + imageName);
        }
        g.drawImage(img, currentLocation.getCoordinate().x() * fieldSize + 6, currentLocation.getCoordinate().y()
                * fieldSize + 2, fieldSize - 6, fieldSize - 12, null);
    }
}
