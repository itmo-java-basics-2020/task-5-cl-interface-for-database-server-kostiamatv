package ru.andrey.kvstorage.console;

public class CreateDatabaseCommand  implements DatabaseCommand{
    ExecutionEnvironment environment;
    ArgumentsParser parser = new ArgumentsParser();
    String databaseName;


    public CreateDatabaseCommand(ExecutionEnvironment env, String... args) {
        environment = env;
        parser.setArgs(args);
        databaseName = parser.getArgument(ArgumentsParser.argsNames.DatabaseName);
    }

    @Override
    public DatabaseCommandResult execute() {
        if (parser.argsLength() != 1) {
            throw new IllegalArgumentException("Wrong arguments number, expected 1, got: " + parser.argsLength());
        }
        environment.addDatabase(null);
        return DatabaseCommandResult.success("Database created");
    }
}
