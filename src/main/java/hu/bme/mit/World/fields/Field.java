package hu.bme.mit.World.fields;

import hu.bme.mit.World.users.Drawable;
import hu.bme.mit.World.users.Injured;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Abstract class for fields.
 * baseDir: the base directory for the images of teh fields.
 * visitors: the list of visitors on the field who are all drawable. Can be rescuers or injureds.
 * coordinate: the coordinate of the field.
 * neighbours: the neighbours of the field in all directions possible.
 * img: the image of the field.
 */
public abstract class Field {

    protected final List<Drawable> visitors = new ArrayList<>();

    protected boolean isFlat;
    protected final Coordinate coordinate;
    protected final HashMap<Direction, Field> neighbours = new HashMap<>();

    protected final String baseDir = "src/main/java/hu/bme/mit/World/images/";
    protected BufferedImage img = null;

    /**
     * Constructor for the field.
     * @param coordinate the coordinate of the field.
     */
    public Field(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Sets the neighbour of the field in the given direction.
     * @param dir the direction of the neighbour.
     * @param field the field to be set as neighbour.
     */
    public void setNeighbour(Direction dir, Field field){
        neighbours.put(dir, field);
    }

    /**
     * Returns the neighbour of the field in the given direction.
     * @param dir the direction of the neighbour.
     * @return the neighbour of the field in the given direction.
     */
    public Field getNeighbour(Direction dir){
        if (neighbours.containsKey(dir))
            return neighbours.get(dir);
        return null;
    }

    /**
     * Removes the visitor from the field.
     * @param visitor the visitor to be removed.
     */
    public void removeVisitor(Drawable visitor){
        visitors.remove(visitor);
    }

    /**
     * Adds a visitor to the field.
     * @param visitor the visitor to be added.
     */
    public void addVisitor(Drawable visitor){
        this.visitors.add(visitor);
    }

    /**
     * Adds an injured to the field.
     * @param injured the injured to be added.
     * @return true if the injured was added successfully, false otherwise.
     */
    public boolean addInjured(Injured injured){
        this.visitors.add(injured);
        injured.setLocation(this);
        return true;
    }

    /**
     * Returns the coordinate of the field.
     * @return the coordinate of the field.
     */
    public Coordinate getCoordinate(){
        return coordinate;
    }

    public boolean getFlatness(){
        return isFlat;
    }

    /**
     * Returns the base directory of the images of the fields.
     * @return the base directory of the images of the fields.
     */
    public String getBaseDir() {
        return baseDir;
    }

    /**
     * Draws the field and its visitors.
     * @param g2 the graphics object to draw on.
     * @param fieldSize the size of the field.
     */
    public void draw(Graphics2D g2, int fieldSize) {
        g2.drawImage(this.img, this.coordinate.x() * fieldSize + 1, this.coordinate.y() * fieldSize + 1, fieldSize - 1, fieldSize - 1, null);

        for (Drawable drawable : this.visitors) {
            drawable.draw(g2, fieldSize);
        }
    }

    /**
     * Clears the visitors of the field.
     */
    public void clearVisitors() {
        visitors.clear();
    }
}
