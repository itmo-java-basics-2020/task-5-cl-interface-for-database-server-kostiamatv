package ru.andrey.kvstorage;

import ru.andrey.kvstorage.console.DatabaseCommand;
import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.DatabaseCommands;
import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.logic.Database;

import java.util.Arrays;
import java.util.TreeMap;

public class DatabaseServer {

    private final ExecutionEnvironment env;

    public DatabaseServer(ExecutionEnvironment env) {
        this.env = env;
    }

    public static void main(String[] args) {
    }

    public DatabaseCommandResult executeNextCommand(String commandText) {
        if (commandText == null){
            return DatabaseCommandResult.error("Empty/null command.");
        }
        String[] args = commandText.split(" ");

        DatabaseCommands possibleCommands;
        try{
            possibleCommands = DatabaseCommands.valueOf(args[0]);
        }
        catch (IllegalArgumentException exc){
            return DatabaseCommandResult.error("Wrong command");
        }
        DatabaseCommand command = possibleCommands.getCommand(env, Arrays.copyOfRange(args, 1, args.length));
        return tryExecute(command);
    }

    private DatabaseCommandResult tryExecute(DatabaseCommand command){
        try{
            return command.execute();
        }
        catch (IllegalArgumentException exc){
            return DatabaseCommandResult.error(exc.getMessage());
        }
    }
}
