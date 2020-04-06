package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.Database;


public enum  DatabaseCommands {

    CREATE_DATABASE{
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) {
            return new CreateDatabaseCommand(env, args);
        }
    },
    CREATE_TABLE{
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) {
            return new CreateTableCommand(env, args);
        }
    },
    UPDATE_KEY {
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) {
            return new UpdateKeyCommand(env, args);
        }
    },
    READ_KEY{
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) {
            return null;
        }
    };

    public abstract DatabaseCommand getCommand(ExecutionEnvironment env, String... args);
}

