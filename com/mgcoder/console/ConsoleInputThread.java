package com.mgcoder.console;

import java.io.IOException;
import java.util.LinkedList;

public class ConsoleInputThread extends Thread {

    private LinkedList<Integer> keyPresses = new LinkedList<>();
    private boolean isListening = false;

    public void run() {
        isListening = true;

        while(isListening) {
            try {
                keyPresses.add(new Integer(System.in.read()));
            }
            catch(IOException ex) { }
        }
    }

    public int getNextKeyPress() {
        if(keyPresses.isEmpty())
            return -1;
        else
            return keyPresses.poll().intValue();
    }

    public void clearKeyPresses() {
        keyPresses.clear();
    }

    public void stoplistening() {
        isListening = false;
    }
}
