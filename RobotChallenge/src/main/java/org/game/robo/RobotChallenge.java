package org.game.robo;

import org.game.robo.configs.PropertiesLoader;
import org.game.robo.engine.GamePay;
import org.game.robo.grid.Table;
import org.game.robo.io.ConsoleWriter;
import org.game.robo.io.InputFactory;
import org.game.robo.io.Reader;
import org.game.robo.io.Writer;
import org.game.robo.services.SceneService;
import org.game.robo.services.TableService;
import org.game.robo.utils.InputType;
import org.game.robo.utils.RoboConstants;
import org.game.robo.utils.TableType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.UUID;


public class RobotChallenge {

    private static final Logger logger = LoggerFactory.getLogger(RobotChallenge.class);

    private final SceneService sceneService;

    public RobotChallenge(){
        sceneService = SceneService.getInstance();
    }

    public static void main(String[] args) {

        // System configurations
        System.setProperty("logback.configurationFile", "logback.xml");

        if (args.length < 1) {
            logger.error("Invalid program arguments. command file must be provided");
            System.exit(1);
        }

        // Config properties
        String commandFileName = args[0];
        PropertiesLoader loader = PropertiesLoader.getInstance();
        loader.loadProperties();
        loader.addProperty(RoboConstants.COMMAND_FILENAME, commandFileName);

        RobotChallenge robotChallenge = new RobotChallenge();
        robotChallenge.run();
    }

    void run() {

        try {

            // Read command from input
            Reader reader = InputFactory.getReader(InputType.FILE);
            List<String> commands = reader.read();

            List<String> result = startGame(commands);

            // Print result
            Writer writer = new ConsoleWriter();
            writer.write(result);

        } catch (Exception e) {
            logger.error("Error occurred  : ", e);
        }
    }

    List<String> startGame(List<String> commands){
        //Create table
        Table table = TableService.getInstance().createTable(TableType.SQUARE);

        //create scene
        UUID sceneUuid= this.sceneService.createScene(table);

        // initialise GamePay
        GamePay gamePay = new GamePay(sceneUuid);

        // Start the game logic
        return gamePay.start(commands);
    }

}


/**
 * RobotChallenge.java (MAIN) reads the input and initialise the Gameplay, board
 * GamePlay.java -> reads the command and create toy oor make movement or report
 * grid ---
 * Table.java interface
 * SquareTable.java -> the grid initialise
 * model ---
 * FACE.java-> enum
 * Direction.java-> enum
 * Position.java-> x,y ,FACE, also tells current position and next position
 * Robot.java -> robot object contains Position object
 * <p>
 * exception----
 * RobotException.java
 * <p>
 * <p>
 * Table of size 5x5 units.
 * Ignore command if falling
 * N number of Robots
 * ---------- Commands like
 * PLACE X,Y,F
 * MOVE
 * LEFT
 * RIGHT
 * REPORT
 * --------------
 * x,y -> 0,0 and f is like NORTH, SOUTH, EAST or WEST
 * The origin (0,0) can be considered to be the SOUTH WEST most corner.
 * <p>
 * first command to the robot is a PLACE command, after that, any sequence of commands may be issued, in any order, including another PLACE command. (T)
 * discard all commands in the sequence until a valid PLACE command has been executed. (T)
 * <p>
 * <p>
 * MOVE will move the toy robot one unit forward in the direction it is currently facing.
 * LEFT and RIGHT will rotate the robot 90 degrees in the specified direction without changing the position of the robot.
 * REPORT will announce the X,Y and facing of the robot.
 * <p>
 * A robot that is not on the table can choose the ignore the MOVE, LEFT, RIGHT and REPORT commands. (T)
 * The toy robot must not fall off the table during movement. This also includes the initial placement of the toy robot(T)
 * <p>
 * -----------------------------
 * Extension
 * Multiple robots will operate on the table
 * <p>
 * <p>
 * The existing system above should continue to work as-is. REPORT will also now report on how many robots are present and which robot is active (see the ROBOT command later).
 * <p>
 * PLACE will add a new robot to the table with incrementing number identifier, i.e. the first placed robot will be 'Robot 1', then the next placed robot will be 'Robot 2', then 'Robot 3', etc.
 * <p>
 * A ROBOT <number> command will make the robot identified by active i.e. subsequent commands will affect that robot's position/direction. Any command that affects position/direction (e.g. MOVE, LEFT, RIGHT...) will affect only the active robot.
 * <p>
 * By default the first robot placed will become the active robot.
 * <p>
 * -------------------- OUT
 * <p>
 * * PLACE 1,2,EAST
 * MOVE
 * MOVE
 * LEFT
 * MOVE
 * REPORT
 * * --------------
 * This will print  -> 3,3,NORTH,1,ROBOT_1
 **/