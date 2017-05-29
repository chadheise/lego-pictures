package io.chadheise.lego.color.transform;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.function.Function;

import io.chadheise.lego.color.measure.ColorMeasure;
import io.chadheise.lego.color.palette.ColorPalette;

/**
 * Transforms one color into another by finding the closest color in a provided
 * color palette. Closeness is determined by the provided ColorMeasure strategy.
 *
 */
public class ColorPaletteColorTransform implements Function<Color, Color> {

    private final ColorPalette palette;
    private final ColorMeasure comparator;

    public ColorPaletteColorTransform(final ColorPalette palette,
            final ColorMeasure comparator) {
        this.palette = palette;
        this.comparator = comparator;
    }

    @Override
    public Color apply(Color color) {
        return sortByDistance(color).get(0);
    }

    /**
     * Returns a sorted collection of the colors in this palette based on their
     * distance to the input color
     * 
     * @param color
     */
    private ArrayList<Color> sortByDistance(Color color) {
        // Sort colors in palette by distance to input color
        TreeMap<Double, Color> paletteColors = new TreeMap<Double, Color>();
        for (Color c : palette.getColors()) {
            paletteColors.put(comparator.getDistance(color, c), c);
        }

        // Convert to ArrayList
        return new ArrayList<Color>(paletteColors.values());
    }

}
