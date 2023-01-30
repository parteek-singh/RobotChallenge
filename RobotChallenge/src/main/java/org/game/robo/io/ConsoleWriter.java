package org.game.robo.io;

import java.util.List;

public class ConsoleWriter implements Writer {

    @Override
    public void write(List<String> reports) {
        System.out.println("******* OUTPUT ********");
        reports.forEach(System.out::println);
        System.out.println("************************");
    }
}
