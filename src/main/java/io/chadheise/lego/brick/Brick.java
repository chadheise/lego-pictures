package io.chadheise.lego.brick;

import java.awt.Color;

public class Brick {

    private Color color;
    private int width;

    public Brick(Color color, int width) {
        this.color = color;
        this.width = width;
    }

    // Copy constructor
    public Brick(Brick brick) {
        this.color = new Color(brick.getColor().getRGB());
        this.width = brick.getWidth();
    }

    public Color getColor() {
        return color;
    }

    public int getWidth() {
        return width;
    }

}
