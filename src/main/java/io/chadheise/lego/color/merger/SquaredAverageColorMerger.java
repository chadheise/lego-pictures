package io.chadheise.lego.color.merger;

import java.awt.*;
import java.util.Collection;

/* Combines colors into a single color by taking the average of the squared RGB values */
public class SquaredAverageColorMerger implements ColorMerger {

    @Override
    public Color apply(final Collection<Color> colors) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (Color color : colors) {
            redSum += (color.getRed() * color.getRed());
            greenSum += (color.getGreen() * color.getGreen());
            blueSum += (color.getBlue() * color.getBlue());
        }

        int redAverage = redSum / colors.size();
        int greenAverage = greenSum / colors.size();
        int blueAverage = blueSum / colors.size();

        return new Color((int) Math.sqrt(redAverage), (int) Math.sqrt(greenAverage), (int) Math.sqrt(blueAverage));
    }

}
