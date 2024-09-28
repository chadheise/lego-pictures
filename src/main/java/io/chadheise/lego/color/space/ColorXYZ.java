package io.chadheise.lego.color;

/* Represents a color the XYZ color space */
public class ColorXYZ {

    private final double x;
    private final double y;
    private final double z;

    public ColorXYZ(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}
