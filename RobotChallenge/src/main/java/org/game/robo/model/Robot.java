package org.game.robo.model;

public class Robot {
    private Position position;
    private final int id;

    public Robot(Position position, int id){
        this.position = position;
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public Position getPosition(){
        return this.position;
    }

    public void setPosition(Position pos){
        this.position = pos;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "position=" + position +
                ", id=" + id +
                '}';
    }
}
