package hu.bme.mit.World.fields;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Class for the Mountain field.
 */
public class Mountain extends Field{

    private final String imageName = "mountain2.jpg";

    public Mountain(Coordinate coordinate) {
        super(coordinate);
        this.isFlat = false;

        try {
            this.img = ImageIO.read(new File(baseDir + imageName));
        } catch (IOException ex) {
            System.err.println("Mountain image not found: " + baseDir + imageName);
        }
    }

}
