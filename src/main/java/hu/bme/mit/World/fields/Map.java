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
     * Sets the fields of the map
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

    public void addStation(Station station){
        stations.add(station);
    }

    public List<Station> getStations(){
        return stations;
    }

    public int getWidthX() {
        return widthX;
    }
    public int getHeight() {
        return heightY;
    }
    public Field getField(int width, int height){
        return fields[width][height];
    }

    public void setHeight(int height) {
        this.heightY = height;
    }

    public void setWidth(int width) {
        this.widthX = width;
    }

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

    public void reset() {
        for (Field[] field : fields){
            for (Field f : field){
                f.clearVisitors();
            }
        }
    }
}
