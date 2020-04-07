package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

public class CreateTableCommand implements DatabaseCommand {
    private static final String ERROR_FIX = "Table already exists";
    private static final int ARGS_NUM = 2;
    private ExecutionEnvironment environment;
    private ArgumentsParser parser = new ArgumentsParser();
    private String databaseName;
    private String tableName;

    public CreateTableCommand(ExecutionEnvironment env, String[] args) {
        environment = env;
        parser.setArgs(args);
        databaseName = parser.getArgument(ArgumentsParser.argsNames.DatabaseName);
        tableName = parser.getArgument(ArgumentsParser.argsNames.TableName);
    }

    @Override
    public DatabaseCommandResult execute() {
        if (parser.argsLength() != ARGS_NUM) {
            throw new IllegalArgumentException("Wrong arguments number, expected " + ARGS_NUM + ", got: " + parser.argsLength());
        }
        return environment.getDatabase(databaseName)
                .map(database -> tryCreateTable(database, tableName))
                .orElse(DatabaseCommandResult.error(ERROR_FIX));
    }

    private DatabaseCommandResult tryCreateTable(Database database, String tableName) {
        try {
            database.createTableIfNotExists(tableName);
            return DatabaseCommandResult.success("Table created.");
        } catch (DatabaseException exc) {
            return DatabaseCommandResult.error(ERROR_FIX);
        }
    }

}
