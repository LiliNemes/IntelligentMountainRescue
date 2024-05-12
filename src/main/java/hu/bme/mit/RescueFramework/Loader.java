package hu.bme.mit.RescueFramework;

import hu.bme.mit.World.fields.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Loader {

    private final Map map = new Map();
    private final String configPath = "src\\main\\java\\hu\\bme\\mit\\World\\map\\map.txt";

    public void load()  {
        try {
            File configFile = new File(configPath);
            Scanner scanner = new Scanner(configFile);

            int width = Integer.parseInt(scanner.nextLine());
            int height = Integer.parseInt(scanner.nextLine());

            map.setHeight(height);
            map.setWidth(width);

            Field[][] fields = new Field[width][height];

            int row = 0;
            while(scanner.hasNextLine()) {
                String[] splitLine = scanner.nextLine().split(" ");

                for(int col=0; col < splitLine.length; col++) {
                    if(splitLine[col].equals("+"))
                        fields[col][row] = new Forest(new Coordinate(col, row));
                    else if (splitLine[col].equals("-"))
                        fields[col][row] = new Mountain(new Coordinate(col, row));
                    else {
                        Station station = new Station(new Coordinate(col, row));
                        fields[col][row] = station;
                        map.addStation(station);
                    }
                }
                row++;
            }

            map.setFields(fields);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't read the map config file.");
        }
    }

    public Map getMap(){
        return this.map;
    }
}
