package hu.bme.mit.World.fields;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Forest extends Field {

    private final String imageName = "forest.jpg";

    public Forest(Coordinate coordinate) {
        super(coordinate);
        try {
            this.img = ImageIO.read(new File(baseDir + imageName));
        } catch (IOException ex) {
            System.err.println("Forest image not found: " + baseDir + imageName);
        }
    }
}
