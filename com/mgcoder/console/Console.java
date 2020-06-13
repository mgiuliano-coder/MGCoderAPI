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
    public static final String COLOR_RESET = "\u001B[0m";
//    public static final String COLOR_BLACK = "\u001B[30m";
//    public static final String COLOR_RED = "\u001B[31m";
//    public static final String COLOR_GREEN = "\u001B[32m";
//    public static final String COLOR_YELLOW = "\u001B[33m";
//    public static final String COLOR_BLUE = "\u001B[34m";
//    public static final String COLOR_PURPLE = "\u001B[35m";
//    public static final String COLOR_CYAN = "\u001B[36m";
//    public static final String COLOR_WHITE = "\u001B[37m";
//    public static final String COLOR_BLACK_BACKGROUND = "\u001B[40m";
//    public static final String COLOR_RED_BACKGROUND = "\u001B[41m";
//    public static final String COLOR_GREEN_BACKGROUND = "\u001B[42m";
//    public static final String COLOR_YELLOW_BACKGROUND = "\u001B[43m";
//    public static final String COLOR_BLUE_BACKGROUND = "\u001B[44m";
//    public static final String COLOR_PURPLE_BACKGROUND = "\u001B[45m";
//    public static final String COLOR_CYAN_BACKGROUND = "\u001B[46m";
//    public static final String COLOR_WHITE_BACKGROUND = "\u001B[47m";

    public static final String CLEAR_SCREEN = "\033[H\033[2J";
    private static final Color256 DEFAULT_FOREGROUND_COLOR = Color256.colors[Color256.WHITE];
    private static final Color256 DEFAULT_BACKGROUND_COLOR = Color256.colors[Color256.BLACK];
    private int screenWidth = 81;
    private int screenHeight = 25;

    private Color256 foregroundColor = Color256.colors[Color256.WHITE];
    private Color256 backgroundColor = Color256.colors[Color256.BLACK];
    public ArrayList<ConsoleLayer> layers = new ArrayList<>();
    private Point cursor = new Point();

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
     * Creates a new layer at the top of the layers list.
     * @return If adding the new layer was successful, a reference to the new layer is returned. Null otherwise.
     */
    public ConsoleLayer addLayer() {
        ConsoleLayer layer = new ConsoleLayer(screenWidth, screenHeight);
        return addLayer(layer);
    }

    /**
     * Adds the specified layer to the top of the layers list.
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
     * Gets the specified ConsoleLayer.
     * @param index the index of the ConsoleLayer.
     * @return the ConsoleLayer at the specified index.
     * @throws IndexOutOfBoundsException Thrown if the index specified is out of range in the list of layers.
     */
    public ConsoleLayer getLayer(int index) throws IndexOutOfBoundsException {
        return layers.get(index);
    }

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

//    /**
//     * Combines all of the layers in the list of layers in the order they appear and prints each layer's
//     * ConsolePixel256 in the console window.
//     */
//    public void printLayers() {
//
//        ConsoleLayer tempLayer;
//        int layerIndex;
//        ConsolePixel256 pixelToPrint;
//        ConsolePixel256 nextPixel;
//        Point originalCursor = (Point)cursor.clone();
//
//        // Iterate through each pixel position in the console screen size.
//        for(int y = 0; y < screenHeight; y++) {
//            for(int x = 0; x < screenWidth; x++) {
//                layerIndex = layers.size() - 1;
//                tempLayer = layers.get(layerIndex);
//                pixelToPrint = tempLayer.getPixel(x - tempLayer.getX(), y - tempLayer.getY());
//
//                // Determine what background, foreground, and character should be displayed by iterating through
//                // each layer from front to back. If it is determined that no characteristics of a lower layer
//                // would be visible, stop evaluating further layers and exit this loop. This is done to avoid
//                // unnecessary processing of pixels if they will not be visible.
//                while((pixelToPrint.getBackgroundColor() == null && layerIndex >= 0)) {
//
//                    nextPixel = layers.get(layerIndex).getPixel(x, y);
//
//                    // Check for a visible foreground color
//                    if(pixelToPrint.getForegroundColor() == null && nextPixel.getForegroundColor() != null)
//                        pixelToPrint.setForegroundColor(nextPixel.getForegroundColor());
//
//                    // Check for a visible background color
//                    if(pixelToPrint.getBackgroundColor() == null && nextPixel.getBackgroundColor() != null)
//                        pixelToPrint.setBackgroundColor(nextPixel.getBackgroundColor());
//
//                    // Check for a visible character
//                    if(pixelToPrint.getCharacter() == '\0' && nextPixel.getCharacter() != '\0')
//                        pixelToPrint.setCharacter(nextPixel.getCharacter());
//
//                    layerIndex--;
//                }
//
//                // Print the final processed pixel.
//                printPixel(x, y, pixelToPrint);
//            }
//        }
//
//        // Set the cursor back to where it was before printing.
//        setCursor(originalCursor);
//    }

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
                            // Check if the pixel on the render layer is null. If there is so background, then pixels on
                            // lower layers can be visible. Therefor, apply the current layer's traits to the render layer.
                            if (renderPixel.getBackgroundColor() == null) {
                                renderPixel.setBackgroundColor(currentPixel.getBackgroundColor());
                                //
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

    public void printLayer(ConsoleLayer layer) {
        for(int y = 0; y < layer.getHeight(); y++) {
            for(int x = 0; x < layer.getWidth(); x++) {
                printPixel(x, y, layer.getPixel(x, y));
            }
        }
    }

    /**
     * Prints a ConsolePixel256 on the console window.
     * @param x the x position to display the pixel.
     * @param y the y position to display the pixel.
     * @param pixel the ConsolePixel256 object to be printed.
     */
    public void printPixel(int x, int y, ConsolePixel256 pixel) {
        setCursor(x, y);

        try {
            //waitMilli(2000);
            if (pixel.getForegroundColor() == null) pixel.setForegroundColor(DEFAULT_FOREGROUND_COLOR);
            if (pixel.getBackgroundColor() == null) pixel.setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
            if (pixel.getCharacter() == '\0') pixel.setCharacter(' ');

            System.out.print("\u001b[38;5;" + pixel.getForegroundColor().getCode() + "m" +
                    "\u001b[48;5;" + pixel.getBackgroundColor().getCode() + "m" +
                    pixel.getCharacter());
        } catch(Exception ex) { System.out.println(x + "," + y + "\n"); ex.printStackTrace(); }

        setCursor(x, y);
    }

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

    public void resetColorDefaults() {
        System.out.print(COLOR_RESET);
    }

    public static final int CONSOLE_MODE_RAW = 1;
    public static final int CONSOLE_MODE_COOKED = 2;

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