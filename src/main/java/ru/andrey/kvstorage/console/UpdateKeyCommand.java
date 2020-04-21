package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

import java.util.Optional;

public class UpdateKeyCommand implements DatabaseCommand {
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
        databaseName = parser.getArgument(ArgumentsParser.argsNames.DATABASE_NAME);
        tableName = parser.getArgument(ArgumentsParser.argsNames.TABLE_NAME);
        key = parser.getArgument(ArgumentsParser.argsNames.KEY);
        value = parser.getArgument(ArgumentsParser.argsNames.VALUE);
    }

    @Override
    public DatabaseCommandResult execute() {
        if (parser.argsLength() != ARGS_NUM) {
            throw new IllegalArgumentException("Wrong arguments number, expected " + ARGS_NUM + ", got: " + parser.argsLength());
        }
        Optional<Database> database = environment.getDatabase(databaseName);
        if (database.isPresent()) {
            return tryUpdateKey(database.get(), tableName, key, value);
        } else {
            return DatabaseCommandResult.error("No table with given name");
        }
    }

    private DatabaseCommandResult tryUpdateKey(Database database, String tableName, String key, String value) {
        try {
            database.write(tableName, key, value);
            return DatabaseCommandResult.success("Successfully created/updated value.");
        } catch (DatabaseException exc) {
            return DatabaseCommandResult.error(exc.getMessage());
        }
    }
}
