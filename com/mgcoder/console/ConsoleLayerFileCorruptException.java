package com.mgcoder.console;

public class ConsoleLayerFileCorruptException extends Exception {
    public ConsoleLayerFileCorruptException() {
        super("The console layer file is corrupt.");
    }

    public ConsoleLayerFileCorruptException(String message) {
        super(message);
    }
}
