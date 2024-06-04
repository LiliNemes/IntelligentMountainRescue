package hu.bme.mit.World.users;

import hu.bme.mit.World.fields.Direction;
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
        return field.getFlatness() && (this.targetLocation != null || this.path.isEmpty());
    }

    /**
     * The helicopter is the fastest, its slowness can be 1
     * @param physicalDistance the distance to weigh
     * @return physicalDistance
     */
    @Override
    public int getWeightedDistance(int physicalDistance) {
        return physicalDistance;
    }

    /**
     * The helicopter can move in every time step, but cannot land on mountain fields.
     * @param bothCanStep true if both rescuers can step, false otherwise (if only the helicopter can step).
     * @return the action of the helicopter.
     */
    @Override
    public Action step(boolean bothCanStep) {

        // Ha mindkét mentőegység rendelkezésre áll a lépéshez,
        // akkor meg kell vizsgálni a helikopter célját és útvonalát.
        if (!path.isEmpty()) {
            Direction direction = path.removeFirst();
            currentLocation.removeVisitor(this);
            currentLocation = currentLocation.getNeighbour(direction);
            currentLocation.addVisitor(this);

            // Ha a helikopter elérte az emberéhez (céljához) tartózkodó mezőt,
            // akkor vegye fel az illetőt.
            if (currentLocation == targetLocation){
                if (!path.isEmpty())
                    throw new RuntimeException("Path should be empty when reaching the target");
                return Action.PICKUP;
            }
            // Ha a helikopter elérte a szállítási állomást,
            // és nem rendelkezik további útvonallal,
            // akkor adja le a szállított sérültet.
            else if (targetLocation == null && path.isEmpty()) {
                return Action.DELIVER;
            }

            return Action.MOVE;
        }

        // Alapértelmezett esetben, ha nincs más teendő,
        // akkor is MOVE akcióval térjen vissza.
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
