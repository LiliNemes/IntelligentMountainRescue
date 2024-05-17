package hu.bme.mit.World.fields;

import hu.bme.mit.World.users.Injured;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Class for the Station field.
 */
public class Station extends Field{

    private final String imageName = "station.jpg";

    public Station(Coordinate coordinate) {
        super(coordinate);
        this.isFlat = true;

        try {
            this.img = ImageIO.read(new File(baseDir + imageName));
        } catch (IOException ex) {
            System.err.println("Station image not found: " + baseDir + imageName);
        }
    }

    @Override
    public boolean addInjured(Injured injured) {
        return false;
    }
}
