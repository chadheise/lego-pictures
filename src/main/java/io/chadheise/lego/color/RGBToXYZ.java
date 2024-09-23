package io.chadheise.lego.color;

import java.util.function.Function;

import java.awt.*;

/* Converts an RGB color to a color in the XYZ color space */
public class RGBToXYZ implements Function<Color, ColorXYZ> {

    @Override
    public ColorXYZ apply(final Color rgbColor) {
        double r = correctGamma(rgbColor.getRed());
        double g = correctGamma(rgbColor.getGreen());
        double b = correctGamma(rgbColor.getBlue());

        double x = r * 0.4124108464885388 + g * 0.3575845678529519 + b * 0.18045380393360833;
        double y = r * 0.21264934272065283 + g * 0.7151691357059038 + b * 0.07218152157344333;
        double z = r * 0.019331758429150258 + g * 0.11919485595098397 + b * 0.9503900340503373;

        return new ColorXYZ(x, y, x);
    }

    private double correctGamma(int value) {
        if (value <= 10) {
            return value / 3294.6;
        }

        return Math.pow(((value + 14.025) / 269.025), 2.4);
    }
}
