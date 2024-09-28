package io.chadheise.lego.color;

import java.util.function.Function;

import java.awt.*;

/* Converts an XYZ color to a color in the RGB color space */
public class XYZToRGB implements Function<ColorXYZ, Color> {

    @Override
    public Color apply(final ColorXYZ xyzColor) {

        double x = xyzColor.getX();
        double y = xyzColor.getY();
        double z = xyzColor.getZ();

        double r = x * 3.240812398895283 - y * 1.5373084456298136 - z * 0.4985865229069666;
        double g = x * -0.9692430170086407 + y * 1.8759663029085742 + z * 0.04155503085668564;
        double b = x * 0.055638398436112804 - y * 0.20400746093241362 + z * 1.0571295702861434;

        r = correctGamma(r);
        g = correctGamma(g);
        b = correctGamma(b);

        return new Color((float) r, (float) g, (float) b);
    }

    private double correctGamma(double value) {
        if (value <= 0.00313066844250060782371) {
            return 3294.6 * value;
        }

        return 269.025 * Math.pow(value, 5.0 / 12.0) - 14.025;
    }
}
