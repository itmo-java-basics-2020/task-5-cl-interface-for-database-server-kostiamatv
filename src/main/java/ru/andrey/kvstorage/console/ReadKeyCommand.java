package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

public class ReadKeyCommand implements DatabaseCommand {
    private static final String ERROR_FIX = "Table already exists";
    private static final int ARGS_NUM = 3;
    private ExecutionEnvironment environment;
    private ArgumentsParser parser = new ArgumentsParser();
    private String databaseName;
    private String tableName;
    private String key;

    public ReadKeyCommand(ExecutionEnvironment env, String[] args) {
        environment = env;
        parser.setArgs(args);
        databaseName = parser.getArgument(ArgumentsParser.argsNames.DatabaseName);
        tableName = parser.getArgument(ArgumentsParser.argsNames.TableName);
        key = parser.getArgument(ArgumentsParser.argsNames.Key);
    }

    @Override
    public DatabaseCommandResult execute() {
        if (parser.argsLength() != ARGS_NUM) {
            throw new IllegalArgumentException("Wrong arguments number, expected " + ARGS_NUM + ", got: " + parser.argsLength());
        }
        return environment.getDatabase(databaseName)
                .map(database -> tryReadKey(database, tableName, key))
                .orElse(DatabaseCommandResult.error(ERROR_FIX));
    }

    private DatabaseCommandResult tryReadKey(Database database, String tableName, String key) {
        try {
            return DatabaseCommandResult.success(database.read(tableName, key));
        } catch (DatabaseException exc) {
            return DatabaseCommandResult.error(ERROR_FIX);
        }
    }
}
