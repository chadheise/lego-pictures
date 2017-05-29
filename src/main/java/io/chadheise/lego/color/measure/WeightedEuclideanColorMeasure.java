package io.chadheise.lego.color.measure;

import java.awt.Color;

/**
 * Uses http://www.compuphase.com/cmetric.htm to calculate the distance between
 * two colors
 * 
 * @author chadheise
 * 
 */
public class WeightedEuclideanColorMeasure implements ColorMeasure {

    @Override
    public double getDistance(Color color1, Color color2) {
        double rmean = (color1.getRed() + color2.getRed()) / 2;

        double r = color1.getRed() - color2.getRed();
        double g = color1.getGreen() - color2.getGreen();
        double b = color1.getBlue() - color2.getBlue();

        // return Math.sqrt( (2+(rmean/256))*r*r + 4*g*g +
        // (2+((255-rmean)/256))*b*b );

        double high = 1;
        double medium = 50;
        double low = 1000;
        double rc = 1;
        double gc = 1;
        double bc = 1;
        if (r > g && r > b) {
            rc = high;
            if (g > b) {
                gc = medium;
            } else {
                bc = medium;
            }
        }
        if (g > r && g > b) {
            gc = high;
            if (r > b) {
                rc = medium;
            } else {
                bc = medium;
            }
        }
        if (b > r && b > g) {
            bc = high;
            if (r > g) {
                rc = medium;
            } else {
                gc = medium;
            }
        }

        return Math.sqrt(rc * r * r + gc * g * g + bc * b * b);

    }

}
