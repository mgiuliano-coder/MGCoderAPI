package com.mgcoder.console;

import java.io.IOException;
import java.util.LinkedList;

/**
 * <p>Creates a Thread that listens for keyboard key presses. The key presses are stored in a buffer until the
 * {@link com.mgcoder.console.ConsoleInputThread#getNextKeyPress() getNextKeyPress} method is called.
 * If the console is in raw mode, it is recommended to sleep the current thread at least 1 millisecond
 * between each check for input.</p>
 * @author Michael Giuliano
 * @since 1.0.0
 */
public class ConsoleInputThread extends Thread {

    // Stores a buffer of keys as they are pressed.
    private LinkedList<Integer> keyPresses = new LinkedList<>();

    // Flag denoting if the ConsoleInputThread is running and listening for keyboard input.
    private boolean isListening = false;

    /**
     * Starts listening for keyboard key presses in a separate Thread. When a key is pressed, the key ASCII value
     * is added to a buffered list.
     */
    public void run() {
        isListening = true;

        while(isListening) {
            try {
                keyPresses.add(new Integer(System.in.read()));
            }
            catch(IOException ex) { }
        }
    }

    /**
     * Gets the next key press logged in the key buffer then removes it from the buffer list.
     * @return the ASCII value of the next key press in the buffer list.
     */
    public int getNextKeyPress() {
        if(keyPresses.isEmpty())
            return -1;
        else
            return keyPresses.poll().intValue();
    }

    /**
     * Checks if there is a next key press in the buffer list. This does not remove the key press from the buffer list.
     * @return true if there is a key press in the buffer list, false otherwise.
     */
    public boolean hasNextKeyPress() {
        return keyPresses.isEmpty() ? false : true;
    }

    /**
     * Removes all key presses from the buffer.
     */
    public void clearKeyPresses() {
        keyPresses.clear();
    }

    /**
     * Stops listening for keyboard input and ends the Thread.
     */
    public void stoplistening() {
        isListening = false;
    }
}
