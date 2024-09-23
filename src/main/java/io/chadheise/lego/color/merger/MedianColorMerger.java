package io.chadheise.lego.color.merger;

import io.chadheise.lego.color.comparator.DistanceColorComparator;
import io.chadheise.lego.color.measure.ColorMeasure;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/* Combines colors into a single color by taking their median */
public class MedianColorMerger implements ColorMerger {

    private final ColorMeasure colorMeasure;

    public MedianColorMerger(final ColorMeasure colorMeasure) {
        this.colorMeasure = colorMeasure;
    }
    @Override
    public Color apply(final Collection<Color> colors) {
        Color white = new Color(0, 0, 0);
        DistanceColorComparator comparator = new DistanceColorComparator(white, this.colorMeasure);
        ArrayList<Color> colorsList = new ArrayList<>(colors);
        colorsList.sort(comparator);

        int midPoint = colors.size() / 2;
        return colorsList.get(midPoint);
    }

}
