package org.game.robo;

import org.game.robo.configs.PropertiesLoader;
import org.game.robo.engine.GamePay;
import org.game.robo.grid.Table;
import org.game.robo.services.SceneService;
import org.game.robo.services.TableService;
import org.game.robo.utils.TableType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GamePlayTest {

    private final GamePay gamePay;

    public GamePlayTest() {
        PropertiesLoader.getInstance().loadProperties();
        Table table = TableService.getInstance().createTable(TableType.SQUARE);
        UUID sceneUuid =  SceneService.getInstance().createScene(table);
        gamePay = new GamePay(sceneUuid);
    }

    @Test
    public void startTest() {

        List<String> commands = Arrays.asList(
                "PLACE 1,2,EAST",
                "MOVE",
                "REPORT",
                "MOVE",
                "PLACE",
                "PLACE 2,1,NORTH",
                "MOVE",
                "ROBOT 2",
                "MOVE",
                "LEFT",
                "RIGHT",
                "REPORT");

        List<String> reports = gamePay.start(commands);
        Assertions.assertEquals(2, reports.size());
        Assertions.assertEquals("2,2,EAST,1,ROBOT 1", reports.get(0));
        Assertions.assertEquals("2,2,NORTH,2,ROBOT 2", reports.get(1));
    }

    @Test
    public void startWithWrongCommandsTest() {

        List<String> commands = Arrays.asList(
                "PLACE 1, 2,s",
                "MOVE1",
                "REPORT",
                "MOVE",
                "PLACE",
                "PLACE 1,NORTH",
                "MOVE",
                "ROBOT 2",
                "ROBOT",
                "MOVE",
                "LEFT",
                "RIGHT",
                "REPORT");

        List<String> reports = gamePay.start(commands);
        Assertions.assertEquals(0, reports.size());
    }
}
