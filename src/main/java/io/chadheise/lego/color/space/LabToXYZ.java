package io.chadheise.lego.color;

import java.util.function.Function;

/* Converts an L*a*b* color to a color in the XYZ color space using the standard D65 illuminant */
public class LabToXYZ implements Function<ColorLab, ColorXYZ> {

    private static final double CBRT_EPSILON = 6.0 / 29.0;
    private static final double KAPPA = 24389.0 / 27.0; // (29/3)^3 = 24389/27

    @Override
    public ColorXYZ apply(final ColorLab labColor) {
        double fy = (labColor.getL() + 16.0) / 116.0;
        double fx = (labColor.getA() / 500.0) + fy;
        double fz = fy - (labColor.getB() / 200.0);

        double x = inverseTransform(fx) * 0.9504492182750991;
        double y = labColor.getL() > 8 ? Math.pow(fy, 3.0) : labColor.getL() / KAPPA;
        double z = inverseTransform(fz) * 1.0889166484304715;

        return new ColorXYZ(x, y, z);
    }

    private double inverseTransform(double value) {
        if (value > CBRT_EPSILON) {
            return Math.pow(value, 3.0);
        }
        return (value * 116.0 - 16.0) / KAPPA;
    }
}

