package org.game.robo;


import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import org.game.robo.configs.PropertiesLoader;
import org.game.robo.engine.GamePay;
import org.game.robo.exception.RobotException;
import org.game.robo.grid.Table;
import org.game.robo.io.InputFactory;
import org.game.robo.io.Reader;
import org.game.robo.services.SceneService;
import org.game.robo.services.TableService;
import org.game.robo.utils.InputType;
import org.game.robo.utils.RoboConstants;
import org.game.robo.utils.TableType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RobotChallengeTest {

    public RobotChallengeTest(){
        PropertiesLoader loader = PropertiesLoader.getInstance();
        loader.loadProperties();
        loader.addProperty(RoboConstants.COMMAND_FILENAME, "./../testData/commands.txt");
    }

    @Test
    @ExpectSystemExitWithStatus(1)
    public void WithNoArgumentTest(){
        String [] args = {};
        RobotChallenge.main(args);
    }

    @Test
    public void mainTest(){
        String [] args = {"./../testData/commands.txt"};
        RobotChallenge.main(args);
    }


    @Test
    public void startGame() {

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

        RobotChallenge robotChallenge = new RobotChallenge();
        List<String> reports = robotChallenge.startGame(commands);
        Assertions.assertEquals(2, reports.size());
        Assertions.assertEquals("2,2,EAST,1,ROBOT 1", reports.get(0));
        Assertions.assertEquals("2,2,NORTH,2,ROBOT 2", reports.get(1));
    }

    @Test
    public void runTest(){
        Reader reader = InputFactory.getReader(InputType.FILE);
        List<String> commands = reader.read();
        //Create table
        Table table = TableService.getInstance().createTable(TableType.SQUARE);

        // initialise GamePay
        UUID sceneUuid =  SceneService.getInstance().createScene(table);
        GamePay gamePay = new GamePay(sceneUuid);
        List<String> reports = gamePay.start(commands);

        Assertions.assertEquals(2,reports.size());
        Assertions.assertEquals("3,4,NORTH,2,ROBOT 1",reports.get(0));
        Assertions.assertEquals("3,2,EAST,2,ROBOT 2",reports.get(1));
    }

    @Test
    public void runTestWithInvalidTable(){
        //Create table
        Assertions.assertThrows(RobotException.class, () -> TableService.getInstance().createTable(null));
    }

}
