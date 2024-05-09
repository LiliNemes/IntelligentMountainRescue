package hu.bme.mit.World;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private int height;
    private int width;
    private Field[][] fields;
    private List<Injured> injureds;

    public void Map(){
        injureds = new ArrayList<>();
    }

    public void setFields(Field[][] fields){
        this.fields = fields;
    }


    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Field getField(int width, int height){
        return fields[height][width];
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void addInjured(Injured injured){
        this.injureds.add(injured);
    }
    public List<Injured> getInjureds(){
        return this.injureds;
    }

    public void setInjureds(List<Injured> injureds) {
        this.injureds.addAll(injureds);
    }
}
