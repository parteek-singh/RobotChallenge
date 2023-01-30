package org.game.robo.utils;

import org.game.robo.exception.RobotException;
import org.game.robo.model.Command;
import org.game.robo.model.Face;

public class FaceRotator {

    public static Face rotate(Face face, Command command){
        Face result;
        switch (command) {
            case LEFT:
                result = rotateLeft(face);
                break;
            case RIGHT:
                result = rotateRight(face);
                break;
            default:
                throw new RobotException("Unsupported direction " + command);
        }
        return result;
    }

    private static Face rotateRight(Face face) {
        switch (face) {
            case NORTH:
                return Face.EAST;
            case EAST:
                return Face.SOUTH;
            case SOUTH:
                return Face.WEST;
            case WEST:
                return Face.NORTH;
            default:
                throw new RobotException("Unsupported face " + face);
        }
    }

    private static Face rotateLeft(Face face) {
        switch (face) {
            case NORTH:
                return Face.WEST;
            case EAST:
                return Face.NORTH;
            case SOUTH:
                return Face.EAST;
            case WEST:
                return Face.SOUTH;
            default:
                throw new RobotException("Unsupported face " + face);
        }
    }
}
