package com.mgcoder.console;

import com.mgcoder.graphics.Color256;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Michael Giuliano
 * @since 1.0.0
 */
public class Console {
    /** Escape sequence to reset the console foreground and background colors back to the default colors. */
    public static final String COLOR_RESET = "\u001B[0m";

    /** Escape sequence to clear the console screen. */
    public static final String CLEAR_SCREEN = "\033[H\033[2J";

    /** Code to set the console window to raw mode. */
    public static final int CONSOLE_MODE_RAW = 1;

    /** Code to set the console window to cooked mode. */
    public static final int CONSOLE_MODE_COOKED = 2;

    // Default foreground color for the console.
    private static final Color256 DEFAULT_FOREGROUND_COLOR = Color256.getColor(Color256.WHITE);

    // Default background color for the console.
    private static final Color256 DEFAULT_BACKGROUND_COLOR = Color256.getColor(Color256.BLACK);

    // The number of columns in the console.
    private int screenWidth = 81;

    // The number of rows in the console.
    private int screenHeight = 25;

    // Stores the layers that are to be printed in the console window.
    private ArrayList<ConsoleLayer> layers = new ArrayList<>();

    // The current position of the cursor in the console window.
    private Point cursor = new Point();

    /**
     * Creates a Console object with the specified columns and rows.
     * @param screenWidth the number of columns.
     * @param screenHeight the number of rows.
     */
    public Console(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        addLayer();
        setCursor(0,0);
    }

    /**
     * Gets the screen width.
     * @return the screen width.
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Gets the screen height.
     * @return the screen height.
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Creates a new {@link com.mgcoder.console.ConsoleLayer ConsoleLayer} at the top of the layers list.
     * @return If adding the new layer was successful, a reference to the new layer is returned. Null otherwise.
     */
    public ConsoleLayer addLayer() {
        ConsoleLayer layer = new ConsoleLayer(screenWidth, screenHeight);
        return addLayer(layer);
    }

    /**
     * Adds the specified {@link com.mgcoder.console.ConsoleLayer ConsoleLayer} to the top of the layers list.
     * @param layer the layer to add to the layers list.
     * @return If adding the new layer was successful, a reference to the new layer is returned. Null otherwise.
     */
    public ConsoleLayer addLayer(ConsoleLayer layer) {
        return layers.add(layer) ? layers.get(layers.size() - 1) : null;
    }

    /**
     * Moves a layer to a specified location in the list of layers.
     * @param srcIndex the layer index that will be moved.
     * @param destIndex the index that the source layer should be moved to.
     * @return True if a move occurred. False otherwise.
     * @throws IndexOutOfBoundsException Thrown if an index specified is out of range in the list of layers.
     */
    public boolean moveLayer(int srcIndex, int destIndex) throws IndexOutOfBoundsException {
        ConsoleLayer srcLayer = layers.get(srcIndex);
        ConsoleLayer destLayer = layers.get(destIndex);

        if(srcIndex < destIndex) {
            layers.add(destIndex, srcLayer);
            layers.remove(srcIndex);
            layers.add(destIndex - 1, destLayer);
            layers.remove(destIndex + 1);
        }
        else if(srcIndex > destIndex) {
            layers.add(destIndex, srcLayer);
            layers.remove(srcIndex + 1);
        }
        else
            return false;

        return true;
    }

    /**
     * Swaps the position of two layers in the list of layers.
     * @param layer1Index a layer index to swap.
     * @param layer2Index a layer index to swap.
     * @return True if a swap occurred. False otherwise.
     * @throws IndexOutOfBoundsException Thrown if an index specified is out of range in the list of layers.
     */
    public boolean swapLayers(int layer1Index, int layer2Index) throws IndexOutOfBoundsException {
        ConsoleLayer layer1 = layers.get(layer1Index);
        ConsoleLayer layer2 = layers.get(layer2Index);

        if(layer1Index == layer2Index)
            return false;

        layers.set(layer1Index, layer2);
        layers.set(layer2Index, layer1);
        return true;
    }

    /**
     * Moves a layer one position forward in the list of layers.
     * @param index the index of the layer to move.
     * @return True if a move occurred. False otherwise.
     * @throws IndexOutOfBoundsException Thrown if an index specified is out of range in the list of layers.
     */
    public boolean moveLayerForward(int index) throws IndexOutOfBoundsException {
        if(index == layers.size() - 1)
            return false;

        swapLayers(index, index + 1);
        return true;
    }

    /**
     * Moves a layer one position backward in the list of layers.
     * @param index the index of the layer to move.
     * @return True if a move occurred. False otherwise.
     * @throws IndexOutOfBoundsException Thrown if an index specified is out of range in the list of layers.
     */
    public boolean moveLayerBackward(int index) throws IndexOutOfBoundsException {
        if(index == 0)
            return false;

        swapLayers(index, index - 1);
        return true;
    }

    /**
     * Moves a layer to the top of the list of layers.
     * @param index the index of the layer to move.
     * @return True if a move occurred. False otherwise.
     * @throws IndexOutOfBoundsException Thrown if an index specified is out of range in the list of layers.
     */
    public boolean moveLayerToFront(int index) throws IndexOutOfBoundsException {
        if(layers.size() == 1)
            return false;

        moveLayer(index, layers.size() - 1);
        return true;
    }

    /**
     * Moves a layer to the bottom of the list of layers.
     * @param index the index of the layer to move.
     * @return True if a move occurred. False otherwise.
     * @throws IndexOutOfBoundsException Thrown if an index specified is out of range in the list of layers.
     */
    public boolean moveLayerToBack(int index) throws IndexOutOfBoundsException {
        if(layers.size() == 1)
            return false;

        moveLayer(index, 0);
        return true;
    }

    /**
     * Gets the specified {@link com.mgcoder.console.ConsoleLayer ConsoleLayer} from the layers list.
     * @param index the index of the {@link com.mgcoder.console.ConsoleLayer ConsoleLayer}.
     * @return the {@link com.mgcoder.console.ConsoleLayer ConsoleLayer} at the specified index.
     * @throws IndexOutOfBoundsException Thrown if the index specified is out of range in the list of layers.
     */
    public ConsoleLayer getLayer(int index) throws IndexOutOfBoundsException {
        return layers.get(index);
    }

    /**
     * Sets the layer at the specified index.
     * @param index the index of the layer to set.
     * @param layer the layer to replace the old layer.
     * @throws IndexOutOfBoundsException Thrown if the index provided does not exist in the list of layers.
     */
    public void setLayer(int index, ConsoleLayer layer) throws IndexOutOfBoundsException {
        layers.set(index, layer);
    }

    /**
     * Clears the contents of all layers.
     */
    public void clearAllLayers() {
        for(ConsoleLayer layer : layers) {
            layer.clearLayer();
        }
    }

    /**
     * Prints all of the layers onto the console window in the order that they appear in the list of layers.
     */
    public void printLayers() {
        ConsoleLayer currentLayer;
        ConsolePixel256 currentPixel;
        ConsolePixel256 renderPixel;

        // All the layers are combined onto this layer. This is what will be printed to the screen.
        ConsoleLayer renderLayer = new ConsoleLayer(screenWidth, screenHeight);

        // Loop through all layers from front to back.
        for(int layerIndex = layers.size() - 1; layerIndex >= 0; layerIndex--) {
            currentLayer = layers.get(layerIndex);
            for (int y = 0; y < currentLayer.height; y++) {
                for (int x = 0; x < currentLayer.width; x++) {
                    currentPixel = currentLayer.getPixel(x, y);
                    // Check if the pixel at the x, y location in the current layer is visible
                    if(currentPixel.isVisible()) {
                        int destX = x + currentLayer.getX();
                        int destY = y + currentLayer.getY();
                        if(destX >= 0 && destX < renderLayer.getWidth() &&
                           destY >= 0 && destY < renderLayer.getHeight()) {
                            renderPixel = renderLayer.getPixel(x + currentLayer.getX(), y + currentLayer.getY());
                            // Check if the pixel on the render layer is null. If there is no background, then pixels on
                            // lower layers can be visible. Therefore, apply the current layer's traits to the render layer.
                            if (renderPixel.getBackgroundColor() == null) {
                                renderPixel.setBackgroundColor(currentPixel.getBackgroundColor());
                                // Check if the pixel does not have a foreground color.
                                if (renderPixel.getForegroundColor() == null) {
                                    renderPixel.setForegroundColor(currentPixel.getForegroundColor());
                                    renderPixel.setCharacter(currentPixel.getCharacter());
                                }
                            }
                        }
                    }
                }
            }
        }

        printLayer(renderLayer);
    }

    /**
     * Prints the provided layer to the console screen.
     * @param layer the layer to be printed.
     */
    public void printLayer(ConsoleLayer layer) {
        for(int y = 0; y < layer.getHeight(); y++) {
            for(int x = 0; x < layer.getWidth(); x++) {
                printPixel(x, y, layer.getPixel(x, y));
            }
        }
    }

    /**
     * Prints a {@link com.mgcoder.console.ConsolePixel256 ConsolePixel256} on the console window.
     * @param x the x position to display the pixel.
     * @param y the y position to display the pixel.
     * @param pixel the {@link com.mgcoder.console.ConsolePixel256 ConsolePixel256} object to be printed.
     */
    public void printPixel(int x, int y, ConsolePixel256 pixel) {
        setCursor(x, y);

        try {
            if (pixel.getForegroundColor() == null) pixel.setForegroundColor(DEFAULT_FOREGROUND_COLOR);
            if (pixel.getBackgroundColor() == null) pixel.setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
            if (pixel.getCharacter() == '\0') pixel.setCharacter(' ');

            System.out.print("\u001b[38;5;" + pixel.getForegroundColor().getCode() + "m" +
                    "\u001b[48;5;" + pixel.getBackgroundColor().getCode() + "m" +
                    pixel.getCharacter());
        } catch(Exception ex) { System.out.println(x + "," + y + "\n"); ex.printStackTrace(); }

        setCursor(x, y);
    }

    // Waits for the specified amount of time in milliseconds.
    private void waitMilli(long milli) {
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis() < start + milli) {}
    }

    /**
     * Gets the current cursor position.
     * @return the current cursor position.
     */
    public Point getCursor() {
        return cursor;
    }

    /**
     * Moves the cursor to the specified position.
     * @param x the column to move the cursor to.
     * @param y the row to move the cursor to.
     */
    public void setCursor(int x, int y) throws IndexOutOfBoundsException {
        if(x >= screenWidth || x < 0)
            throw new IndexOutOfBoundsException();
        if(y >= screenHeight || y < 0)
            throw new IndexOutOfBoundsException();

        char escCode = 0x1B;
        cursor.setLocation(x, y);
        System.out.print(String.format("%c[%d;%df",escCode,y,x));
    }

    /**
     * Moves the cursor to the specified position.
     * @param cursor the x and y position where to move the cursor.
     */
    public void setCursor(Point cursor) throws IndexOutOfBoundsException {
        if(cursor != null)
            setCursor(cursor.x, cursor.y);
    }

    /**
     * Resets the foreground and background colors back to the console defaults.
     */
    public void resetColorDefaults() {
        System.out.print(COLOR_RESET);
    }

    /**
     * Sets the console mode. Raw mode provides more control over the console window. Printing characters and new lines
     * are the responsibility of the programmer to handle. Input is also captured after each key stroke. Cooked mode
     * is the default mode where these things are handled for you and input is captured after the user presses return.
     * @param consoleMode the code of the mode to set the console to.
     * @return true if a valid console mode was passed in, false otherwise.
     * @throws IOException thrown if an I/O error occurs.
     * @throws InterruptedException if the current thread is interrupted by another thread while it is waiting,
     * then the wait is ended and an {@link java.lang.InterruptedException InterruptedException} is thrown.
     */
    public boolean setConsoleMode(int consoleMode) throws IOException, InterruptedException {
        String[] command;

        if(consoleMode == CONSOLE_MODE_RAW) {
            command = new String[] {"sh", "-c", "stty raw </dev/tty"};
        }
        else if(consoleMode == CONSOLE_MODE_COOKED) {
            command = new String[] {"sh", "-c", "stty cooked </dev/tty"};
        }
        else {
            return false;
        }

        Runtime.getRuntime().exec(command).waitFor();
        return true;
    }

    public void clearConsoleScreen() {
        System.out.print(CLEAR_SCREEN);
    }

    public ConsoleAnimation addAnimationFromFiles(String[] filePaths, int x, int y, int speed, boolean loop)
            throws InconsistentAnimationFrameSizesException, ConsoleLayerFileCorruptException, IOException {


        ConsoleAnimation animation = null;
        Point size = null;
        ConsoleLayer[] frames = new ConsoleLayer[filePaths.length];


        for(int i = 0; i < filePaths.length; i++) {
            frames[i] = ConsoleLayer.loadConsoleLayerFromFile(filePaths[i]);
            if(size == null) {
                size = new Point(frames[i].getWidth(), frames[i].getHeight());
            }
            else {
                if(frames[i].getWidth() != size.x && frames[i].getHeight() != size.y) {
                    throw new InconsistentAnimationFrameSizesException();
                }
            }
        }

        ConsoleLayer layer = new ConsoleLayer(size.x, size.y);
        animation = new ConsoleAnimation(layer, frames, x, y, loop);
        animation.setSpeed(speed);
        addLayer(layer);

        return animation;
    }
}