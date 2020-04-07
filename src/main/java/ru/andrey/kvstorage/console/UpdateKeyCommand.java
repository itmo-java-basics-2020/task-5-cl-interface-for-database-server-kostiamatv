package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

public class UpdateKeyCommand implements DatabaseCommand {
    private static final String ERROR_FIX = "Table already exists";
    private static final int ARGS_NUM = 4;
    private ExecutionEnvironment environment;
    private ArgumentsParser parser = new ArgumentsParser();
    private String databaseName;
    private String tableName;
    private String key;
    private String value;

    public UpdateKeyCommand(ExecutionEnvironment env, String[] args) {
        environment = env;
        parser.setArgs(args);
        databaseName = parser.getArgument(ArgumentsParser.argsNames.DatabaseName);
        tableName = parser.getArgument(ArgumentsParser.argsNames.TableName);
        key = parser.getArgument(ArgumentsParser.argsNames.Key);
        value = parser.getArgument(ArgumentsParser.argsNames.Value);
    }

    @Override
    public DatabaseCommandResult execute() {
        if (parser.argsLength() != ARGS_NUM) {
            throw new IllegalArgumentException("Wrong arguments number, expected " + ARGS_NUM + ", got: " + parser.argsLength());
        }
        return environment.getDatabase(databaseName)
                .map(database -> tryUpdateKey(database, tableName, key, value))
                .orElse(DatabaseCommandResult.error(ERROR_FIX));
    }

    private DatabaseCommandResult tryUpdateKey(Database database, String tableName, String key, String value) {
        try {
            database.write(tableName, key, value);
            return DatabaseCommandResult.success("Successfully created/updated value.");
        } catch (DatabaseException exc) {
            return DatabaseCommandResult.error(ERROR_FIX);
        }
    }
}
