package io.chadheise.lego.color.merger;

import java.awt.*;
import java.util.Collection;

/* Combines colors into a single color by taking their average RGB values */
public class AverageColorMerger implements ColorMerger {

    @Override
    public Color apply(final Collection<Color> colors) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (Color color : colors) {
            redSum += color.getRed();
            greenSum += color.getGreen();
            blueSum += color.getBlue();
        }

        int redAverage = redSum / colors.size();
        int greenAverage = greenSum / colors.size();
        int blueAverage = blueSum / colors.size();

        return new Color(redAverage, greenAverage, blueAverage);
    }

}
