package ru.andrey.kvstorage.console;

public class CreateDatabaseCommand  implements DatabaseCommand{
    ExecutionEnvironment environment;
    String[] args;

    public CreateDatabaseCommand(ExecutionEnvironment env, String... args) {
        environment = env;
        this.args = args;
    }

    @Override
    public DatabaseCommandResult execute() {
        if (args.length != 1) {
            return DatabaseCommandResult.error("Wrong number of arguments: expected 1, got: " + args.length);
        }
        environment.addDatabase(null);
        return DatabaseCommandResult.success("Database created");
    }
}
