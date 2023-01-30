package org.game.robo.services;

import org.game.robo.exception.RobotException;
import org.game.robo.model.Command;
import org.game.robo.model.Face;
import org.game.robo.model.Position;
import org.game.robo.utils.FaceRotator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class CommandService {

    private final Logger logger = LoggerFactory.getLogger(CommandService.class);

    private CommandService() {
    }

    private static class CommandServiceSingleton {
        private static final CommandService INSTANCE = new CommandService();
    }

    public static CommandService getInstance() {
        return CommandService.CommandServiceSingleton.INSTANCE;
    }

    public boolean validateCommand(Command command, String[] args) {
        if (Objects.isNull(command)) {
            logger.error("Invalid command");
            return false;
        } else if (command == Command.PLACE) {
            if (args.length != 2 || args[1].split(",").length != 3) {
                logger.error("Invalid PLACE command");
                return false;
            }
        } else if (command == Command.ROBOT && args.length != 2) {
            logger.error("Invalid ROBOT command");
            return false;
        }

        return true;
    }

    public Command evaluateCommand(String cmd) {

        Command command = null;
        try {
            command = Command.valueOf(cmd);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid command");
        }
        return command;
    }

    public Position getPositionFromPlaceCmd(String arguments) {
        String[] params = arguments.split(",");
        try {
            int x = Integer.parseInt(params[0]);
            int y = Integer.parseInt(params[1]);
            Face face = Face.valueOf(params[2]);
            return new Position(x, y, face);
        } catch (Exception e) {
            throw new RobotException("Invalid arguments in PLACE command");
        }
    }

    public Face rotateFace(Face currentFace, Command command) {
         return FaceRotator.rotate(currentFace, command);

    }
}
