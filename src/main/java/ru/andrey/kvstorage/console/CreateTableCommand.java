package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;

public class CreateTableCommand implements DatabaseCommand {
    private static final String ERROR_FIX = "Table already exists";
    ExecutionEnvironment environment;
    ArgumentsParser parser = new ArgumentsParser();
    String databaseName;
    String tableName;



    public CreateTableCommand(ExecutionEnvironment env, String[] args) {
        environment = env;
        parser.setArgs(args);
        databaseName = parser.getArgument(ArgumentsParser.argsNames.DatabaseName);
        tableName = parser.getArgument(ArgumentsParser.argsNames.TableName);
    }

    @Override
    public DatabaseCommandResult execute() {
        if (parser.argsLength() != 2) {
            throw new IllegalArgumentException("Wrong arguments number, expected 1, got: " + parser.argsLength());
        }
        return environment.getDatabase(databaseName).map(database -> tryCreateTable(database, tableName))
                .orElse(DatabaseCommandResult.error(ERROR_FIX));
    }

    private DatabaseCommandResult tryCreateTable(Database database, String tableName){
        try{
            database.createTableIfNotExists(tableName);
            return DatabaseCommandResult.success("Table created.");
        }
        catch (DatabaseException exc){
            return DatabaseCommandResult.error(ERROR_FIX);
        }
    }

}
