package io.chadheise.lego.color;

/* Represents a color the L*a*b* color space */
public class ColorLab {

    private final double lStar; // lightness component

    private final double aStar; // green-red component

    private final double bStar; // blue-yellow component

    public ColorLab(double lStar, double aStar, double bStar) {
        this.lStar = lStar;
        this.aStar = aStar;
        this.bStar = bStar;
    }

    public double getL() {
        return this.lStar;
    }

    public double getA() {
        return this.aStar;
    }

    public double getB() {
        return this.bStar;
    }

}
