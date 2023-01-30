package org.game.robo.grid;

import org.game.robo.exception.RobotException;
import org.game.robo.utils.TableType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableFactory {

    private static final Logger logger = LoggerFactory.getLogger(TableFactory.class);

    public static Table getTable(TableType type) {
        final Table table;

        if (type == TableType.SQUARE) {
            table = new SquareTable();
        } else {
            logger.error("Invalid Table type {}", type);
            throw new RobotException("Invalid Table type");
        }
        return table;
    }
}
