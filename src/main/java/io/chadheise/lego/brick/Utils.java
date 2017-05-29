package io.chadheise.lego.brick;

public class Utils {

    public static final double MILLIMETERS_IN_AN_INCH = 25.4;

    public int widthInchesToNumBrickUnits(int widthInches) {
        return (int) ((widthInches * MILLIMETERS_IN_AN_INCH) / BrickUnit.MILLIMETER_WIDTH);
    }

    public int heightInchesToNumBrickUnits(int heightInches) {
        return (int) ((heightInches * MILLIMETERS_IN_AN_INCH) / BrickUnit.MILLIMETER_HEIGHT);
    }

}
