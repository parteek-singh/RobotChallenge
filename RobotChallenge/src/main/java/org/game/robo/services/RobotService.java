package org.game.robo.services;

import org.game.robo.model.Position;
import org.game.robo.model.Robot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RobotService {

    private final Logger logger = LoggerFactory.getLogger(RobotService.class);

    private RobotService() { }

    private static class RobotServiceSingleton {
        private static final RobotService INSTANCE = new RobotService();
    }

    public static RobotService getInstance() {
        return RobotService.RobotServiceSingleton.INSTANCE;
    }

    public Robot createRobot(Position position, int id) {
        Robot robot = new Robot(position, id);
        logger.debug("Robot with id {} created.", robot.getId());
        return robot;
    }
}
