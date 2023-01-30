package org.game.robo.io;

import org.game.robo.configs.PropertiesLoader;
import org.game.robo.exception.RobotException;
import org.game.robo.utils.RoboConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader implements Reader {

    private final Logger logger = LoggerFactory.getLogger(FileReader.class);

    @Override
    public List<String> read() {

        String fileName = PropertiesLoader.getInstance().getConfiguration().getProperty(RoboConstants.COMMAND_FILENAME);
        Path path = Paths.get(fileName);
        List<String> commands;
        try (Stream<String> stream = Files.lines(path)) {

            //1) convert all content to upper case
            //2) convert it into a List
            commands = stream
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            logger.error("Not able to read command file. {0}", e);
            throw new RobotException("Invalid command file.");
        }
        return commands;
    }
}
