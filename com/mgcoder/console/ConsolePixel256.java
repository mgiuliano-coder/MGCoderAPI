package com.mgcoder.console;

import com.mgcoder.graphics.Color256;

/**
 * @author Michael Giuliano
 * @since 1.0.0
 */
public class ConsolePixel256 {
    /** The foreground color of the pixel's character. */
    private Color256 foregroundColor;

    /** The background color of the pixel. */
    private Color256 backgroundColor;

    /** The character in the pixel. */
    private char character;

    /**
     * Creates a ConsolePixel256 instance with no character and no foreground or background color.
     */
    public ConsolePixel256() {
        this('\0', null, null);
    }

    /**
     * Creates a ConsolePixel256 instance with the given character, foreground color, and background color.
     * @param character the character in the pixel.
     * @param foregroundColor the foreground color of the pixel's character. Null for no foreground color.
     * @param backgroundColor the background color of the pixel. Null for no background color.
     */
    public ConsolePixel256(char character, Color256 foregroundColor, Color256 backgroundColor) {
        setCharacter(character);
        setForegroundColor(foregroundColor);
        setBackgroundColor(backgroundColor);
    }

    /**
     * Creates a ConsolePixel256 instance with the given character, foreground color, and background color.
     * @param character the character in the pixel.
     * @param foregroundColor the foreground color of the pixel's character
     * @param backgroundColor the background color of the pixel.
     */
    public ConsolePixel256(char character, int foregroundColor, int backgroundColor) {
        setCharacter(character);
        this.foregroundColor = setForegroundColor(foregroundColor);
        this.backgroundColor = setBackgroundColor(backgroundColor);
    }

    /**
     * Gets the foreground color code.
     * @return the foreground color code.
     */
    public Color256 getForegroundColor() {
        return foregroundColor;
    }

    /**
     * Sets the foreground color.
     * @param foregroundColor the foreground color code.
     * @return The Color256 object if the color passed in is a valid Color256 code. null if it is not valid.
     */
    public Color256 setForegroundColor(int foregroundColor) {
        if(foregroundColor < 0 || foregroundColor > 255)
        {
            return null;
        }
        else {
            this.foregroundColor = Color256.getColor(foregroundColor);
            return this.foregroundColor;
        }
    }

    /**
     * Sets the foreground color.
     * @param foregroundColor the foreground color.
     */
    public void setForegroundColor(Color256 foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    /**
     * Gets the background color.
     * @return the background color.
     */
    public Color256 getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the background color.
     * @param backgroundColor the background color code.
     * @return the Color256 object if the color passed in is a valid Color256 code. null if it is not valid.
     */
    public Color256 setBackgroundColor(int backgroundColor) {
        if(backgroundColor < 0 || backgroundColor > 255)
        {
            return null;
        }
        else {
            this.backgroundColor = Color256.getColor(backgroundColor);
            return this.backgroundColor;
        }

    }

    /**
     * Sets the background color.
     * @param backgroundColor the background color.
     */
    public void setBackgroundColor(Color256 backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Gets the unicode character.
     * @return the unicode character.
     */
    public char getCharacter() {
        return character;
    }

    /**
     * Sets the unicode character.
     * @param character the unicode character.
     */
    public void setCharacter(char character) {
        this.character = character;
    }

    /**
     * Determines if all characteristics of this pixel are null.
     * @return True if the background, foreground, and character are null. False if otherwise.
     */
    public boolean isVisible() {
        if(getBackgroundColor() != null || getForegroundColor() != null && getCharacter() != '\0')
            return true;
        else
            return false;
    }

    /**
     * Sets the background, foreground, and character to null.
     */
    public void clearPixel() {
        setBackgroundColor(null);
        setForegroundColor(null);
        setCharacter('\0');
    }
}