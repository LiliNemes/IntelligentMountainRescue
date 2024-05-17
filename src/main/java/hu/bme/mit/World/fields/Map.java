package hu.bme.mit.World.fields;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the map of the world
 */
public class Map {
    private int heightY;
    private int widthX;
    private Field[][] fields;
    private final List<Station> stations = new ArrayList<>();

    /**
     * Sets the fields of the map and sets the neighbours of the fields.
     * @param fields the fields of the map
     */
    public void setFields(Field[][] fields){
        this.fields = fields;
        for (Field[] field : fields){
            for (Field f : field){
                if(f.getCoordinate().y()!=0)
                    f.setNeighbour(Direction.UP, fields[f.getCoordinate().x()][f.getCoordinate().y() - 1]);
                if(f.getCoordinate().y()!=(heightY - 1))
                    f.setNeighbour(Direction.DOWN, fields[f.getCoordinate().x()][f.getCoordinate().y() + 1]);
                if(f.getCoordinate().x()!=0)
                    f.setNeighbour(Direction.LEFT, fields[f.getCoordinate().x() - 1][f.getCoordinate().y()]);
                if(f.getCoordinate().x()!=(widthX - 1))
                    f.setNeighbour(Direction.RIGHT, fields[f.getCoordinate().x() + 1][f.getCoordinate().y()]);
            }
        }
    }

    /**
     * Adds a station to the map.
     * @param station the station to be added.
     */
    public void addStation(Station station){
        stations.add(station);
    }

    /**
     * Returns the stations of the map.
     * @return the stations of the map.
     */
    public List<Station> getStations(){
        return stations;
    }

    /**
     * Returns the width of the map.
     * @return the width of the map.
     */
    public int getWidth() {
        return widthX;
    }

    /**
     * Returns the height of the map.
     * @return the height of the map.
     */
    public int getHeight() {
        return heightY;
    }
    /**
     * Returns the field at the given coordinates.
     * @param width the width coordinate of the field.
     * @param height the height coordinate of the field.
     * @return the field at the given coordinates.
     */
    public Field getField(int width, int height){
        return fields[width][height];
    }

    /**
     * Sets the height of the map.
     * @param height the height of the map.
     */
    public void setHeight(int height) {
        this.heightY = height;
    }

    /**
     * Sets the width of the map.
     * @param width the width of the map.
     */
    public void setWidth(int width) {
        this.widthX = width;
    }

    /**
     * Calculates the shortest path possible between two fields.
     * @param start the starting field.
     * @param end the ending field.
     * @return the fields of the map.
     */
    public static List<Direction> getPath(Field start, Field end){
        ArrayList<Direction> path = new ArrayList<>();
        //UP
        for (int i = start.getCoordinate().y(); i > end.getCoordinate().y(); i--){
            path.add(Direction.UP);
        }
        //DOWN
        for (int i = start.getCoordinate().y(); i < end.getCoordinate().y(); i++){
            path.add(Direction.DOWN);
        }
        //LEFT
        for (int i = start.getCoordinate().x(); i > end.getCoordinate().x(); i--){
            path.add(Direction.LEFT);
        }
        //RIGHT
        for (int i = start.getCoordinate().x(); i < end.getCoordinate().x(); i++){
            path.add(Direction.RIGHT);
        }
        return path;
    }

    /**
     * Resets the visitors of all the fields.
     */
    public void reset() {
        for (Field[] field : fields){
            for (Field f : field){
                f.clearVisitors();
            }
        }
    }

    public List<Direction> getNearestStation(Field location) {
        int minDistance = fields.length;
        List<Direction> shortestPath = null;

        for (Station station : stations){
            List<Direction> p = getPath(location, station);
            if (p.size() <= minDistance){
                minDistance = p.size();
                shortestPath = p;
            }
        }

        return shortestPath;
    }
}
