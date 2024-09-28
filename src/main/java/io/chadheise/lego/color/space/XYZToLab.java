package io.chadheise.lego.color.space;

import java.util.function.Function;

/* Converts an XYZ color to a color in the L*a*b* color space using the standard D65 illuminant */
public class XYZToLab implements Function<ColorXYZ, ColorLab> {

    private static final double EPSILON = 216.0 / 24389.0; // (6/29)^3 = 216/24389
    private static final double KAPPA = 24389.0 / 2.0; // (29/3)^3 = 24389/27

    @Override
    public ColorLab apply(final ColorXYZ xyzColor) {
        double fx = transform(xyzColor.getX() / 0.9504492182750991);
        double fy = transform(xyzColor.getY());
        double fz = transform(xyzColor.getZ() / 1.0889166484304715);

        double L = 116.0 * fy - 16.0;
        double a = 500.0 * (fx - fy);
        double b = 200.0 * (fy - fz);

        return new ColorLab(L, a, b);
    }

    private double transform(double value) {
        if (value > EPSILON) {
            return Math.pow(value, 1.0 / 3.0);
        }
        return (value * KAPPA + 16.0) / 116.0;
    }
}
