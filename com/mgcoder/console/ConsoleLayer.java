package com.mgcoder.console;

import com.mgcoder.util.MGString;
import com.mgcoder.graphics.Color256;

import java.awt.*;
import java.io.*;

/**
 * @author Michael Giuliano
 * @since 1.0.0
 */
public class ConsoleLayer {

    public static final int MIN_COLOR_CODE = 0;
    public static final int MAX_COLOR_CODE = 255;
    private static final String CLEAR_SCREEN = "\033[H\033[2J";
    public static final Color256 DEFAULT_FOREGROUND_COLOR = Color256.getColor(Color256.WHITE);
    public static final Color256 DEFAULT_BACKGROUND_COLOR = Color256.getColor(Color256.BLACK);
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    public ConsolePixel256[][] layer;

    public ConsoleLayer(int width, int height) {
        this.width = width;
        this.height = height;

        layer = new ConsolePixel256[width][height];
        initLayer(layer);
    }

    /*
     * Instantiates all of the elements of the screen array.
     */
    protected void initLayer(ConsolePixel256[][] layer) {
        for(int y = 0; y < layer[0].length; y++) {
            for(int x = 0; x < layer.length; x++) {
                layer[x][y] = new ConsolePixel256();
            }
        }
    }

    /**
     * Gets the screen width.
     * @return the screen width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the screen height.
     * @return the screen height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the x position.
     * @return the x position.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x position.
     * @param value the x position.
     */
    public void setX(int value) {
        x = value;
    }

    /**
     * Gets the y position.
     * @return the y position.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y position.
     * @param value the y position.
     */
    public void setY(int value) {
        y = value;
    }

    /**
     * Gets the x, y position.
     * @return the x, y position.
     */
    public Point getPosition() {
        return new Point(x, y);
    }

    /**
     * Sets the x, y position.
     * @param x the x position.
     * @param y the y position.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the x, y position.
     * @param position the x, y position.
     */
    public void setPosition(Point position) {
        setPosition(position.x, position.y);
    }

//    /**
//     * Gets the current cursor position.
//     * @return the current cursor position.
//     */
//    public Point getCursor() {
//        return cursor;
//    }
//
//    /**
//     * Moves the cursor to the specified position.
//     * @param x the column to move the cursor to.
//     * @param y the row to move the cursor to.
//     */
//    public void setCursor(int x, int y) {
//        char escCode = 0x1B;
//        System.out.print(String.format("%c[%d;%df",escCode,y,x));
//        cursor.setLocation(x, y);
//    }
//
//    /**
//     * Moves the cursor to the specified position.
//     * @param cursor the x and y position where to move the cursor.
//     */
//    public void setCursor(Point cursor) {
//        if(cursor != null)
//            this.cursor = cursor;
//    }

    /**
     * Gets the foreground color of a ConsolePixel256 on this ConsoleLayer.
     * @param x the x coordinate on the screen (in character blocks).
     * @param y the y coordinate on the screen (in character blocks).
     * @return the foreground color of the character block.
     */
    public Color256 getForegroundColor(int x, int y) {
        return Color256.getColor(layer[x][y].getForegroundColor().getCode());
    }

    /**
     * Sets the foreground color of a ConsolePixel256 on this ConsoleLayer.
     * @param x the x coordinate on the screen (in character blocks).
     * @param y the y coordinate on the screen (in character blocks).
     * @param color the color.
     * @throws IndexOutOfBoundsException Thrown if the x or y coordinate is outside of the screen size.
     */
    public void setForegroundColor(int x, int y, Color256 color) throws IndexOutOfBoundsException{
        layer[x][y].setForegroundColor(color);
    }

    /**
     * Sets the foreground color of a ConsolePixel256 on this ConsoleLayer.
     * @param x the x coordinate on the screen (in character blocks).
     * @param y the y coordinate on the screen (in character blocks).
     * @param color the color code.
     * @throws IndexOutOfBoundsException Thrown if the x or y coordinate is outside of the screen size. Also thrown
     *         if the color is not within the allowable color range.
     */
    public void setForegroundColor(int x, int y, int color) throws IndexOutOfBoundsException {
        if(color < MIN_COLOR_CODE || color > MAX_COLOR_CODE)
            throw new IndexOutOfBoundsException("Color code must be from 0 to 255.");

        setForegroundColor(x, y, Color256.getColor(color));
    }

    /**
     * Gets the background color of a ConsolePixel256 on this ConsoleLayer.
     * @param x the x coordinate on the screen (in character blocks).
     * @param y the y coordinate on the screen (in character blocks).
     * @return the background color of a ConsolePixel256 on this ConsoleLayer.
     */
    public Color256 getBackgroundColor(int x, int y) {
        return Color256.getColor(layer[x][y].getBackgroundColor().getCode());
    }

    /**
     * Sets the background color of a ConsolePixel256 on this ConsoleLayer.
     * @param x the x coordinate on the screen (in character blocks).
     * @param y the y coordinate on the screen (in character blocks).
     * @param color the color.
     * @throws IndexOutOfBoundsException Thrown if the x or y coordinate is outside of the screen size.
     */
    public void setBackgroundColor(int x, int y, Color256 color) throws IndexOutOfBoundsException{
        layer[x][y].setBackgroundColor(color);
    }

    /**
     * Sets the background color of a ConsolePixel256 on this ConsoleLayer.
     * @param x the x coordinate on the screen (in character blocks).
     * @param y the y coordinate on the screen (in character blocks).
     * @param color the color code.
     * @throws IndexOutOfBoundsException Thrown if the x or y coordinate is outside of the screen size. Also thrown
     *         if the color is not within the allowable color range.
     */
    public void setBackgroundColor(int x, int y, int color) throws IndexOutOfBoundsException {
        if(color < MIN_COLOR_CODE || color > MAX_COLOR_CODE)
            throw new IndexOutOfBoundsException("Color code must be from 0 to 255.");

        setBackgroundColor(x, y, Color256.getColor(color));
    }

    /**
     * Gets a ConsolePixel256 object in the layer.
     * @param x x the x coordinate on the screen (in character blocks).
     * @param y the y coordinate on the screen (in character blocks).
     * @return the ConsolePixel256 object in the layer at the specified location.
     * @throws IndexOutOfBoundsException Thrown in the x and y coordinates are outside of the layer bounds.
     */
    public ConsolePixel256 getPixel(int x, int y) throws IndexOutOfBoundsException {
        return layer[x][y];
    }

    public void setPixel(int x, int y, ConsolePixel256 pixel) throws NullPointerException {
        if(pixel == null)
            throw new NullPointerException();

        layer[x][y] = pixel;
    }

    /**
     * Clears all of the contents of the layer.
     */
    public void clearLayer() {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                layer[x][y].clearPixel();
            }
        }
    }

    /**
     * Fills the foreground of the screen with a color with the option to clear the text.
     * @param color the color to use to fill.
     * @param clearText true will clear all text, false will keep the text.
     */
    public void fillForeground(Color256 color, boolean clearText) {

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                layer[x][y].setForegroundColor(color);
                if(clearText)
                    layer[x][y].setCharacter('\0');
            }
        }
    }

    /**
     * Fills the background of the screen with a color with the option to clear the text.
     * @param color the color to use to fill.
     * @param clearText true will clear all text, false will keep the text.
     */
    public void fillBackground(Color256 color, boolean clearText) {

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                layer[x][y].setBackgroundColor(color);
                if(clearText)
                    layer[x][y].setCharacter('\0');
            }
        }
    }

    /**
     * Sets text in the screen at the specified location.
     * @param x the columns to place the text.
     * @param y the row to place the text.
     * @param text the text to put on the screen.
     * @param wrapText flag to indicate whether or not to wrap the text to the next line.
     */
    public void setText(int x, int y, Object text, boolean wrapText) {
        setText(x, y, text, null, null, wrapText);
    }

    /**
     * Sets text in the screen at the specified location with a foreground and background color.
     * @param x the columns to place the text.
     * @param y the row to place the text.
     * @param text the text to put on the screen.
     * @param foreground the foreground color of the text. Null will not change the foreground for this text.
     * @param background the background color of the text. Null will not change the background for this text.
     * @param wrapText flag to indicate whether or not to wrap the text to the next line.
     */
    public void setText(int x, int y, Object text, Color256 foreground, Color256 background, boolean wrapText) {
        if(text == null) return;

        byte[] bytes = text.toString().getBytes();

        int index = 0;
        int currentX = x;
        int currentY = y;
        boolean stop = false;

        for(int i = 0; i < bytes.length && !stop; i++) {
            if(currentX >= width) {
                if(wrapText) {
                    currentX = 0;
                    ++currentY;
                }
                else {
                    stop = true;
                }
            }

            if(currentY >= height) {
                stop = true;
            }

            if(!stop) {
                layer[currentX][currentY].setCharacter((char)bytes[i]);
                if(foreground != null) layer[currentX][currentY].setForegroundColor(foreground);
                if(background != null) layer[currentX][currentY].setBackgroundColor(background);
            }
            ++currentX;
        }
    }

    public void offsetSublayer(int startX, int startY, int width, int height, int xOffset, int yOffset) {

    }

    // TODO: Write method to save ConsoleLayer
    public void saveConsoleLayerToFile(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        // File type code. i = single sprite
        writer.write("i");

        // Width and height
        //Integer.toHexString(image.getWidth()).substring
        writer.write("");

        writer.close();
    }

    // TODO: Write method to load ConsoleLayer
    public static ConsoleLayer loadConsoleLayerFromFile(String path) throws IOException, ConsoleLayerFileCorruptException {

        StringBuilder fileContent = getFileContent(path);

        return validateFileContent(fileContent.toString());
    }

    private static ConsoleLayer validateFileContent(String fileContent)
            throws IOException, ConsoleLayerFileCorruptException, NumberFormatException {
        int width, height;
        int hasCharacters;
        int hasForeground;
        int hasBackground;
        int sumOfFlags;
        String[] values;

        values = MGString.splitToStringArray(fileContent, 0, fileContent.length() - 1, 2);

        // Get the width and height of the layer
        width = Integer.parseInt(values[0], 16);
        height = Integer.parseInt(values[1], 16);
        hasCharacters = Integer.parseInt(values[2], 16);
        hasForeground = Integer.parseInt(values[3], 16);
        hasBackground = Integer.parseInt(values[4], 16);

        // Check if the flags are valid values.
        if(hasCharacters < 0 || hasCharacters > 1 ||
                hasForeground < 0 || hasForeground > 1 ||
                hasBackground < 0 || hasBackground > 1)
            throw new ConsoleLayerFileCorruptException("Invalid flags.");
        sumOfFlags = hasCharacters + hasForeground + hasBackground;

        // Check that the layer data has the correct number of values per pixel.
        if((fileContent.length() - 10) % (sumOfFlags * 2) != 0)
            throw new ConsoleLayerFileCorruptException("Invalid pixel elements.");

        // Check if the data for each pixel has 3 elements. If not, data is missing.
        if((values.length - 5) % sumOfFlags != 0 ||
                width * height != (values.length - 5) / sumOfFlags)
            throw new ConsoleLayerFileCorruptException("Number of pixels does not match size of layer.");

        //
        ConsoleLayer layer = new ConsoleLayer(width, height);
        ConsolePixel256 pixel = null;
        int index = 5;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                pixel = new ConsolePixel256();

//                try {
                    if (hasCharacters == 1) {
                        if (!values[index].equals("--"))
                            pixel.setCharacter((char) Integer.parseInt(values[index], 16));
                        index++;
                    }

                    if (hasForeground == 1) {
                        if (!values[index].equals("--"))
                            pixel.setForegroundColor(Integer.parseInt(values[index], 16));
                        index++;
                    }

                    if (hasBackground == 1) {
                        if (!values[index].equals("--"))
                            pixel.setBackgroundColor(Integer.parseInt(values[index], 16));
                        index++;
                    }

                    layer.setPixel(x, y, pixel);
//                }
//                catch(Exception ex) {
//                    ex.printStackTrace();
//                }
            }
        }

        return layer;
    }

    private static StringBuilder getFileContent(String path) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        String line;

        BufferedReader reader = new BufferedReader(new FileReader(path));

        while((line = reader.readLine()) != null) {
            fileContent.append(line);
        }

        reader.close();
        return fileContent;
    }
}