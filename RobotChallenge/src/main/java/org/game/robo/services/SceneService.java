package org.game.robo.services;

import org.game.robo.engine.GamePay;
import org.game.robo.grid.Table;
import org.game.robo.model.Position;
import org.game.robo.model.Robot;
import org.game.robo.model.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SceneService {

    private final Logger logger = LoggerFactory.getLogger(SceneService.class);
    private final Map<UUID, Scene> sceneMap;

    private SceneService() {
        this.sceneMap = new HashMap<>();
    }

    private static class SceneServiceSingleton {
        private static final SceneService INSTANCE = new SceneService();
    }

    public static SceneService getInstance() {
        return SceneService.SceneServiceSingleton.INSTANCE;
    }

    public UUID createScene(Table table) {
        Scene scene = new Scene(table);
        this.sceneMap.put(scene.getUuid(), scene);
        return scene.getUuid();
    }

    public void addRobotToScene(UUID sceneUuid, Robot robot) {
        Scene scene = sceneMap.get(sceneUuid);
        if(Objects.nonNull(scene)){
            scene.addRobot(robot);
        }
        else {
            logger.error("Scene {} cannot found.", sceneUuid);
        }
    }

    public Robot getRobotInScene(UUID sceneUuid, int robotId){
        Scene scene = sceneMap.get(sceneUuid);
        if(Objects.nonNull(scene)){
            return scene.getRobot(robotId);
        }
        logger.error("Scene {} cannot found.", sceneUuid);
        return null;

    }

    public Robot getActiveRobot(UUID sceneUuid) {
        Scene scene = sceneMap.get(sceneUuid);
        if(Objects.nonNull(scene)){
            return scene.getActiveRobot();
        }
        logger.error("Scene {} cannot found.", sceneUuid);
        return null;
    }

    public void setActiveRobotId(UUID sceneUuid, int activeRobotId) {
        Scene scene = sceneMap.get(sceneUuid);
        if(Objects.nonNull(scene)){
            scene.setActiveRobotId(activeRobotId);
        }
        else {
            logger.error("Scene {} cannot found.", sceneUuid);
        }
    }

    public boolean isPositionValidOnTable(UUID sceneUuid, Position position) {
        Scene scene = sceneMap.get(sceneUuid);
        if(Objects.nonNull(scene)){
            return scene.getTable().isValidPosition(position);
        }
        logger.error("Scene {} cannot found.", sceneUuid);
        return false;
    }

    public int getTotalNumOfRobots(UUID sceneUuid) {
        Scene scene = sceneMap.get(sceneUuid);
        if(Objects.nonNull(scene)){
            return scene.getTotalNoOfRobots();
        }
        logger.error("Scene {} cannot found.", sceneUuid);
        return 0;
    }

}
