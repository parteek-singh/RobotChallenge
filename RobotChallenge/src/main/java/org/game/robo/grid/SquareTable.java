package org.game.robo.grid;

import org.game.robo.configs.PropertiesLoader;
import org.game.robo.model.Position;
import org.game.robo.utils.RoboConstants;

import java.util.UUID;

public class SquareTable implements Table {

    private final UUID id;
    private final int rows;
    private final int columns;

    public SquareTable() {
        this.rows = Integer.parseInt(PropertiesLoader.getInstance().getProperty(RoboConstants.GRID_ROWS));
        this.columns = Integer.parseInt(PropertiesLoader.getInstance().getProperty(RoboConstants.GRID_COLS));
        this.id = UUID.randomUUID();
    }

    public SquareTable(int rows, int cols) {
        this.rows = rows;
        this.columns = cols;
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean isValidPosition(Position position) {
        return !(
                position.getX() > this.columns - 1 || position.getX() < 0 ||
                        position.getY() > this.rows - 1 || position.getY() < 0
        );
    }

    @Override
    public UUID getId() {
        return this.id;
    }
}
