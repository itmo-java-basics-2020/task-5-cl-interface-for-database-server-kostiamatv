package ru.andrey.kvstorage.console;

public class ArgumentsParser {
    private String[] args;

    enum argsNames {
        DatabaseName, TableName, Key, Value
    }


    public void setArgs(String... args) {
        this.args = args;
    }

    public String getArgument(argsNames arg) {
        int id = arg.ordinal();
        if (args.length > id) {
            return args[id];
        }
        return null;
    }

    public int argsLength() {
        return args.length;
    }

}
