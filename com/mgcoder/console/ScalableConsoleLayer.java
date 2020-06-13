package com.mgcoder.console;

/**
 * @author Michael Giuliano
 * @since 1.0.0
 */
public class ScalableConsoleLayer extends ConsoleLayer {
    public static final int ANCHOR_TOP_LEFT = 1;
    public static final int ANCHOR_TOP_RIGHT = 2;
    public static final int ANCHOR_BOTTOM_LEFT = 3;
    public static final int ANCHOR_BOTTOM_RIGHT = 4;

    public ScalableConsoleLayer(int width, int height) {
        super(width, height);
    }

    //TODO: Test scale method
    public boolean scale(int width, int height, int anchor) throws IllegalArgumentException {
        if(width < 1 || height < 1)
            throw new IllegalArgumentException("Width and height cannot be less than 1.");

        ConsolePixel256[][] newLayer = new ConsolePixel256[width][height];
        initLayer(newLayer);

        int startX, startY;
        if(anchor == ANCHOR_TOP_LEFT) {
            startX = 0;
            startY = 0;
        }
        else if( anchor == ANCHOR_TOP_RIGHT) {
            startX = getWidth() - width;
            startY = 0;
        }
        else if( anchor == ANCHOR_BOTTOM_LEFT) {
            startX = 0;
            startY = getHeight() - height;
        }
        else if( anchor == ANCHOR_BOTTOM_RIGHT) {
            startX = getWidth() - width;
            startY = getHeight() - height;
        }
        else
            return false;

        for(int srcY = startY, destY = 0; destY < height; srcY++, destY++) {
            for(int srcX = startX, destX = 0; destX < width; srcX++, destX++) {
                newLayer[destX][destY] = layer[srcX][srcY];
            }
        }
        this.width = width;
        this.height = height;

        return true;
    }
}
