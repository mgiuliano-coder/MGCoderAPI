package com.mgcoder.console;

public class InconsistentAnimationFrameSizesException extends Exception {
    public InconsistentAnimationFrameSizesException() {
        super("The sizes of the animation frames are not the same.");
    }

    public InconsistentAnimationFrameSizesException(String message) {
        super(message);
    }
}
