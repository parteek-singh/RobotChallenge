package org.game.robo.grid;

import org.game.robo.model.Position;

import java.util.UUID;

public interface Table {

    boolean isValidPosition(Position position);
    UUID getId();

}
