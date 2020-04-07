package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

import java.util.Optional;

public class CreateTableCommand implements DatabaseCommand {
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
        Optional<Database> database = environment.getDatabase(databaseName);
        if (database.isPresent()) {
            return tryCreateTable(database.get(), tableName);
        } else {
            return DatabaseCommandResult.error("No table with given name");
        }
    }

    private DatabaseCommandResult tryCreateTable(Database database, String tableName) {
        try {
            database.createTableIfNotExists(tableName);
            return DatabaseCommandResult.success("Table created.");
        } catch (DatabaseException exc) {
            return DatabaseCommandResult.error(exc.getMessage());
        }
    }

}
