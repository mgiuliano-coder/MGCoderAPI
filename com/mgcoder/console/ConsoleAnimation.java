package com.mgcoder.console;

import java.awt.*;
import java.util.ArrayList;

public class ConsoleAnimation {
    public ConsoleLayer layer;
    public Console console;
    private boolean loop;
    private boolean isQuit = false;
    private ArrayList<ConsoleLayer> frames;
    private int timeBetweenFrames = 100;
    private long lastUpdateTime = 0;
    private int currentFrame = 0;

//    public ConsoleAnimation() {
//        this(null, 0, 0, false);
//    }

    public ConsoleAnimation(ConsoleLayer layer, ConsoleLayer[] frames, int x, int y, boolean loop) throws NullPointerException {
        this.layer = layer;
        layer.setX(x);
        layer.setY(y);
        this.loop = loop;
        this.frames = new ArrayList<>();

        if(frames != null) {
            for (int i = 0; i < frames.length; i++) {
                this.frames.add(frames[i]);
            }
        }
    }

    public int getSpeed() {
        return timeBetweenFrames;
    }

    public void setSpeed(int milli) {
        timeBetweenFrames = milli;
    }

    public void addFrame(ConsoleLayer frame) {
        frames.add(frame);
    }

    public ConsoleLayer getFrame(int index) {
        return frames.get(index);
    }

    public boolean update() {
        long currentTime = System.currentTimeMillis();
        boolean hasChanged = false;

        if(frames.size() > 0 && currentFrame != -1) {
            if(currentTime - lastUpdateTime >= timeBetweenFrames) {
                copyLayer(frames.get(currentFrame), layer);

                if (currentFrame == frames.size() - 1) {
                    if (loop)
                        reset();
                    else
                        currentFrame = -1;
                }
                else {
                    currentFrame++;
                }
                lastUpdateTime = currentTime;
                hasChanged = true;
            }
        }
        return hasChanged;
    }

    public void reset() {
        currentFrame = 0;
    }

    public Point getPosition() {
        return new Point(layer.getX(), layer.getY());
    }

    public void setPosition(int x, int y) {
        layer.setPosition(x, y);
    }

    private void copyLayer(ConsoleLayer from, ConsoleLayer to) {
        for(int y = 0; y < to.getHeight(); y++) {
            for(int x = 0; x < to.getWidth(); x++) {
                if(x < from.getWidth() && y < from.getHeight()) {
                    to.setPixel(x, y, from.getPixel(x, y));
                }
            }
        }
    }

    private void waitMilli(long milli) {
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis() < start + milli) {}
    }
}
