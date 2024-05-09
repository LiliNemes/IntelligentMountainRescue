package hu.bme.mit.RescueFramework;

import hu.bme.mit.World.Coordinate;
import hu.bme.mit.World.Field;
import hu.bme.mit.World.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static hu.bme.mit.World.FieldType.*;


public class Loader {
    private Map map = new Map();
    public void load()  {
        try {
            File configFile = new File("sources/map.txt");
            Scanner scanner = new Scanner(configFile);
            String line_one = scanner.nextLine();
            int width = Integer.valueOf(line_one);
            String line_two = scanner.nextLine();
            int height = Integer.valueOf(line_two);
            Field[][] fields = new Field[height][width];
            int line_num = 0;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splittedLine = line.split(" ");
            for(int i=0; i< splittedLine.length; i++) {
                if(splittedLine[i].equals("+"))
                    fields[line_num][i] = new Field(FOREST, new Coordinate(line_num, i));
                else if (splittedLine[i].equals("-"))
                    fields[line_num][i] = new Field(MOUNTAIN, new Coordinate(line_num, i));
                else
                    fields[line_num][i] = new Field(STATION, new Coordinate(line_num, i));


            }
            line_num++;
        }
        map.setFields(fields);
        map.setHeight(height);
        map.setWidth(width);
        scanner.close();
        } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        }
    }

    public Map getMap(){
        return this.map;
    }
}
