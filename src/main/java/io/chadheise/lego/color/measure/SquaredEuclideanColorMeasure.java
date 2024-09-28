package io.chadheise.lego.color.measure;

import java.awt.*;

public class SquaredEuclideanColorMeasure implements ColorMeasure {
    @Override
    public double getDistance(Color color1, Color color2) {
        int diffRed = (color1.getRed() * color1.getRed()) - (color2.getRed() * color2.getRed());
        int diffBlue = (color1.getBlue() * color1.getBlue()) - (color2.getBlue() * color2.getBlue());
        int diffGreen = (color1.getGreen() * color1.getGreen()) - (color2.getGreen() * color2.getGreen());
        return Math.sqrt(diffRed + diffBlue + diffGreen);
    }
}
