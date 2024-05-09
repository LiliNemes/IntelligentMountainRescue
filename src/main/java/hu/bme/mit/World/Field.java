package hu.bme.mit.World;

import java.util.List;

public class Field {
    private final FieldType type;
    private Injured injured;
    private Coordinate coordinate;
    private List<Field> neighbours;

    public Field(FieldType type, Coordinate coordinate) {
        this.type = type;
        this.coordinate = coordinate;
    }

    public FieldType getType(){
        return this.type;
    }



}
