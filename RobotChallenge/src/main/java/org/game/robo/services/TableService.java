package org.game.robo.services;

import org.game.robo.grid.Table;
import org.game.robo.grid.TableFactory;
import org.game.robo.utils.TableType;

public class TableService {

    private TableService() {
    }

    private static class TableServiceSingleton {
        private static final TableService INSTANCE = new TableService();
    }

    public static TableService getInstance() {
        return TableService.TableServiceSingleton.INSTANCE;
    }

    public Table createTable(TableType type) {
        return TableFactory.getTable(type);
    }

}
