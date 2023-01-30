package org.game.robo.model;

import org.game.robo.grid.Table;

import java.util.*;

public class Scene {

    private final UUID uuid;
    private final Table table;
    private final Map<Integer,Robot> robots;
    private int activeRobotId = 1;

    public Scene(Table table) {
        this.table = table;
        this.uuid = UUID.randomUUID();
        robots = new HashMap<>();
    }

    public Table getTable() {
        return this.table;
    }

    public int getTotalNoOfRobots() {
        return this.robots.values().size();
    }
    public Robot getRobot(int index) {
        return this.robots.get(index);
    }

    public void addRobot(Robot robot) {
        this.robots.put(robot.getId(), robot);
    }

    public void setActiveRobotId(int activeRobotId) {
        this.activeRobotId = activeRobotId;
    }

    public Robot getActiveRobot(){
        return this.robots.get(this.activeRobotId);
    }

    public UUID getUuid() {
        return uuid;
    }
}
