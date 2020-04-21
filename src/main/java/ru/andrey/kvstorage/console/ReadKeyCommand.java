package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

import java.util.Optional;

public class ReadKeyCommand implements DatabaseCommand {
    private static final int ARGS_NUM = 3;
    private ExecutionEnvironment environment;
    private ArgumentsParser parser = new ArgumentsParser();
    private String databaseName;
    private String tableName;
    private String key;

    public ReadKeyCommand(ExecutionEnvironment env, String[] args) {
        environment = env;
        parser.setArgs(args);
        databaseName = parser.getArgument(ArgumentsParser.argsNames.DATABASE_NAME);
        tableName = parser.getArgument(ArgumentsParser.argsNames.TABLE_NAME);
        key = parser.getArgument(ArgumentsParser.argsNames.KEY);
    }

    @Override
    public DatabaseCommandResult execute() {
        if (parser.argsLength() != ARGS_NUM) {
            throw new IllegalArgumentException("Wrong arguments number, expected " + ARGS_NUM + ", got: " + parser.argsLength());
        }
        Optional<Database> database = environment.getDatabase(databaseName);
        if (database.isPresent()) {
            return tryReadKey(database.get(), tableName, key);
        } else {
            return DatabaseCommandResult.error("No table with given name");
        }
    }

    private DatabaseCommandResult tryReadKey(Database database, String tableName, String key) {
        try {
            return DatabaseCommandResult.success(database.read(tableName, key));
        } catch (DatabaseException exc) {
            return DatabaseCommandResult.error(exc.getMessage());
        }
    }
}
