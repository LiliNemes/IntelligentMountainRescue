package hu.bme.mit.World.fields;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Class for the Forest field.
 */
public class Forest extends Field {

    private final String imageName = "forest.jpg";

    /**
     * Constructor for the Forest class.
     * @param coordinate the coordinate of the field.
     */
    public Forest(Coordinate coordinate) {
        super(coordinate);
        this.isFlat = true;
        try {
            this.img = ImageIO.read(new File(baseDir + imageName));
        } catch (IOException ex) {
            System.err.println("Forest image not found: " + baseDir + imageName);
        }
    }
}
