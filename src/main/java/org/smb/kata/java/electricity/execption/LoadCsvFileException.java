package org.smb.kata.java.electricity.execption;

public class LoadCsvFileException extends RuntimeException{

    private static final String MESSAGE = "Error during import csv data";

    public LoadCsvFileException() {
        super(MESSAGE);
    }
}
