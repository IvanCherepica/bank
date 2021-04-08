package com.bank.example.sqltracker;

public class SqlCountMismatchException extends RuntimeException {
    public SqlCountMismatchException(String statement, int expectedCount, int actualCount) {
        super("Expected " + statement + " query count: " + expectedCount + ", actual: " + actualCount);
    }
}
