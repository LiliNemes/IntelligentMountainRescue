package hu.bme.mit.World.fields;

import hu.bme.mit.World.users.Drawable;
import hu.bme.mit.World.users.Injured;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Field {
    protected String baseDir = "src/main/java/hu/bme/mit/World/images/";
    protected final List<Drawable> visitors = new ArrayList<>();
    protected final Coordinate coordinate;
    protected final HashMap<Direction, Field> neighbours = new HashMap<>();
    protected BufferedImage img = null;

    public Field(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setNeighbour(Direction dir, Field field){
        neighbours.put(dir, field);
    }

    public Field getNeighbour(Direction dir){
        if (neighbours.containsKey(dir))
            return neighbours.get(dir);
        return null;
    }

    public void removeVisitor(Drawable visitor){
        visitors.remove(visitor);
    }

    public void addVisitor(Drawable visitor){
        this.visitors.add(visitor);
    }

    public boolean addInjured(Injured injured){
        this.visitors.add(injured);
        injured.setLocation(this);
        return true;
    }

    public Coordinate getCoordinate(){
        return coordinate;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void draw(Graphics2D g2, int fieldSize) {
        g2.drawImage(this.img, this.coordinate.x() * fieldSize + 1, this.coordinate.y() * fieldSize + 1, fieldSize - 1, fieldSize - 1, null);

        for (Drawable drawable : this.visitors) {
            drawable.draw(g2, fieldSize);
        }
    }

    public void clearVisitors() {
        visitors.clear();
    }
}
