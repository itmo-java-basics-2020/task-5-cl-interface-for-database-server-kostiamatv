package ru.andrey.kvstorage.console;

public class CreateDatabaseCommand  implements DatabaseCommand{
    private ExecutionEnvironment environment;
    private static final int ARGS_NUM = 1;
    private ArgumentsParser parser = new ArgumentsParser();
    private String databaseName;

    public CreateDatabaseCommand(ExecutionEnvironment env, String... args) {
        environment = env;
        parser.setArgs(args);
        databaseName = parser.getArgument(ArgumentsParser.argsNames.DatabaseName);
    }

    @Override
    public DatabaseCommandResult execute() {
        if (parser.argsLength() != ARGS_NUM) {
            throw new IllegalArgumentException("Wrong arguments number, expected " + ARGS_NUM + ", got: " + parser.argsLength());
        }
        environment.addDatabase(null);
        return DatabaseCommandResult.success("Database created");
    }
}
