package org.game.robo.io;

import org.game.robo.exception.RobotException;
import org.game.robo.utils.InputType;

public class InputFactory {

    public static Reader getReader(InputType type) {
        Reader reader;
        if (InputType.FILE == type) {
            reader = new FileReader();
        } else {
            // TODO will implement system.io
            throw new RobotException("Invalid reader type. Currently we support only FILE Type");
        }
        return reader;
    }
}
