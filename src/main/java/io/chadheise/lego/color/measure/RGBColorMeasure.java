package io.chadheise.lego.color.measure;

import java.awt.Color;

public class RGBColorMeasure implements ColorMeasure {

    @Override
    public double getDistance(Color color1, Color color2) {
        return Math.abs(color1.getRGB() - color2.getRGB());
    }

}
