package ru.andrey.kvstorage.console;

import java.util.Optional;

public interface DatabaseCommandResult {

    Optional<String> getResult();

    DatabaseCommandStatus getStatus();

    boolean isSuccess();

    String getErrorMessage();

    static DatabaseCommandResult success(String result) {
        return new DatabaseResultClass(DatabaseCommandStatus.SUCCESS, result);
    }

    static DatabaseCommandResult error(String result) {
        return new DatabaseResultClass(DatabaseCommandStatus.FAILED, result);
    }


    enum DatabaseCommandStatus {
        SUCCESS, FAILED
    }

    class DatabaseResultClass implements DatabaseCommandResult {

        private final DatabaseCommandStatus status;
        private final String result;

        private DatabaseResultClass(DatabaseCommandStatus status, String result) {
            this.status = status;
            this.result = result;
        }

        @Override
        public Optional<String> getResult() {
            if (status == DatabaseCommandStatus.SUCCESS) {
                return Optional.of(result);
            } else {
                return Optional.empty();
            }
        }

        @Override
        public DatabaseCommandStatus getStatus() {
            return status;
        }

        @Override
        public boolean isSuccess() {
            return status == DatabaseCommandStatus.SUCCESS;
        }

        @Override
        public String getErrorMessage() {
            if (status == DatabaseCommandStatus.FAILED) {
                return result;
            } else {
                return null;
            }
        }

    }
}