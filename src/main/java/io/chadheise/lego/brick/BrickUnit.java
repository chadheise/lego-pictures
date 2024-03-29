package io.chadheise.lego.brick;

public class BrickUnit {
    public static final Double MILLIMETER_HEIGHT = 9.6;
    public static final Double MILLIMETER_WIDTH = 8.0;
    private Brick brick = null;

    public BrickUnit(Brick brick) {
        this.brick = brick; // The brick that this brick unit is a part of
    }

    public Brick getBrick() {
        return brick;
    }

}
