package io.chadheise.lego.color.measure;

import java.awt.*;

/* Compares 2 colors using the "redmean" algorithm */
public class RedMeanColorMeasure implements ColorMeasure {

    @Override
    public double getDistance(Color color1, Color color2) {
        int diffRed = color1.getRed() - color2.getRed();
        int diffBlue = color1.getBlue() - color2.getBlue();
        int diffGreen = color1.getGreen() - color2.getGreen();

        double redMean = (color1.getRed() + color1.getRed()) / 2.0;

        return Math.sqrt((2.0 + (redMean / 256.0)) * diffRed * diffRed + 4.0 * diffGreen * diffGreen + (2.0 + ((255 - redMean) / 256.0)) * diffBlue * diffBlue);
    }
}
