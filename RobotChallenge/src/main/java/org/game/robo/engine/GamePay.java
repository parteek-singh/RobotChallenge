package org.game.robo.engine;

import org.game.robo.exception.RobotException;
import org.game.robo.model.Command;
import org.game.robo.model.Face;
import org.game.robo.model.Position;
import org.game.robo.model.Robot;
import org.game.robo.services.CommandService;
import org.game.robo.services.RobotService;
import org.game.robo.services.SceneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GamePay {

    private final Logger logger = LoggerFactory.getLogger(GamePay.class);
    private final UUID sceneUuid;

    private final SceneService sceneService;
    private final CommandService commandService;
    private final RobotService robotService;

    public GamePay(UUID sceneUuid) {
        this.sceneUuid = sceneUuid;
        //RobotService.getInstance().reset(); TODO
        sceneService = SceneService.getInstance();
        commandService = CommandService.getInstance();
        robotService = RobotService.getInstance();
    }

    public List<String> start(List<String> commands) {
        List<String> reports = new ArrayList<>();
        for (String cmd : commands) {
            String result = executeCommand(cmd);
            if(Objects.nonNull(result)){
                reports.add(result);
            }
        }
        return reports;
    }

    public String executeCommand(String cmd) {
        String[] args = cmd.split("\\s+");

        logger.debug("Executing Command {} on scene : {}", cmd, sceneUuid);

        Command command = this.commandService.evaluateCommand(args[0]);
        boolean isValid = this.commandService.validateCommand(command, args);
        if(!isValid){
            return null;
        }
        Robot activeRobot = this.sceneService.getActiveRobot(sceneUuid);

        switch (command) {
            case PLACE:
                String placeArgs = args[1];
                executePlaceCmd(placeArgs);
                break;
            case MOVE:
                executeMoveCmd(activeRobot);
                break;
            case LEFT:
                rotateDirection(activeRobot, Command.LEFT);
                break;
            case RIGHT:
                rotateDirection(activeRobot, Command.RIGHT);
                break;
            case ROBOT:
                int index = Integer.parseInt(args[1]);
                executeRobotCmd(activeRobot, index);
                break;
            case REPORT:
                return executeReportCmd(activeRobot);
            default:
                throw new RobotException( String.format("Invalid command on scene : %s", sceneUuid.toString()));
        }
        return null;
    }

    private void executePlaceCmd(String placeArgs){
        Position startPosition = this.commandService.getPositionFromPlaceCmd(placeArgs);
        boolean isValidPosition = this.sceneService.isPositionValidOnTable(sceneUuid, startPosition);
        if(isValidPosition){
            final int id = this.sceneService.getTotalNumOfRobots(sceneUuid) + 1;
            Robot newRobot = this.robotService.createRobot(startPosition, id);
            this.sceneService.addRobotToScene(sceneUuid, newRobot);
        }
        else{
            logger.debug("Cannot place the Robot on table. Not a valid position : {}, scene : {}", startPosition, sceneUuid);
        }
    }

    private void executeMoveCmd(Robot activeRobot){
        if (Objects.nonNull(activeRobot)) {
            Position nextPosition = activeRobot.getPosition().getNextPosition();
            boolean isValidPosition = this.sceneService.isPositionValidOnTable(sceneUuid, nextPosition);
            if (isValidPosition) {
                activeRobot.setPosition(nextPosition);
                logger.debug("Moving Robot : {} on scene : {} to position : {}", activeRobot, sceneUuid, nextPosition);
            } else {
                logger.debug("Ignore MOVE command for Robot {}, scene : {}", activeRobot, sceneUuid);
            }
        }
        else{
            logger.debug("No robot is active on scene : {}", sceneUuid);
        }
    }

    private void rotateDirection(Robot activeRobot, Command command){
        if (Objects.nonNull(activeRobot)) {
            Face currentFace = activeRobot.getPosition().getFace();
            Face newFace = this.commandService.rotateFace(currentFace, command);
            activeRobot.getPosition().setFace(newFace);
            logger.debug("Rotating Robot : {} on scene : {} to newFace : {}", activeRobot, sceneUuid, newFace);

        } else{
            logger.debug("No robot is active on scene : {}", sceneUuid);
        }
    }

    private void executeRobotCmd(Robot activeRobot, int index){
        if (Objects.nonNull(activeRobot)) {
            Robot nextEligibleRobot= this.sceneService.getRobotInScene(sceneUuid, index);
            if (Objects.nonNull(nextEligibleRobot)) {
                this.sceneService.setActiveRobotId(sceneUuid, index);
                logger.debug("Activate -> old Robot : {} on scene : {} change to index : {}", activeRobot, sceneUuid, this.sceneService.getActiveRobot(sceneUuid));
            }
            else {
                logger.debug("Invalid ROBOT command. Index is not valid : {}, scene : {}", index, sceneUuid);
            }
        } else{
            logger.debug("No robot is active on scene : {}", sceneUuid);
        }
    }

    private String executeReportCmd(Robot activeRobot){
        if (Objects.nonNull(activeRobot)) {
            Position position = activeRobot.getPosition();
            int noOfRobots = this.sceneService.getTotalNumOfRobots(sceneUuid);
            return String.format("%s,%s,%s,%s,ROBOT %d", position.getX(), position.getY(), position.getFace(), noOfRobots, activeRobot.getId());
        }
        logger.debug("No robot is active on scene : {}", sceneUuid);
        return null;
    }

}
