package org.game.robo.model;

import org.game.robo.exception.RobotException;

public class Position {
    int x;
    int y;
    Face face;

    public Position(Position position) {
        this.x = position.getX();
        this.y = position.getY();
        this.face = position.getFace();
    }

    public Position(int x, int y, Face face) {
        this.x = x;
        this.y = y;
        this.face = face;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Face getFace() {
        return this.face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public void change(int x, int y) {
        this.x = this.x + x;
        this.y = this.y + y;
    }

    public Position getNextPosition() throws RobotException {
        if (this.face == null)
            throw new RobotException("Robot face cannot be null");

        // new position to validate
        Position newPosition = new Position(this);
        switch (this.face) {
            case NORTH:
                newPosition.change(0, 1);
                break;
            case EAST:
                newPosition.change(1, 0);
                break;
            case SOUTH:
                newPosition.change(0, -1);
                break;
            case WEST:
                newPosition.change(-1, 0);
                break;
        }
        return newPosition;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", face=" + face +
                '}';
    }
}
