package io.chadheise.lego.color.measure;

import io.chadheise.lego.color.grid.ColorGrid;
import io.chadheise.lego.color.palette.ColorPalette;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/* Measures the distance between a color and a palette by re-centering each palette color to its closest color from the image
* RecenteredColorMeasure uses the full palette to add detail at the expense of color accuracy */
public class RecenteredColorMeasure implements ColorMeasure {

    private final ColorPalette colorPalette;
    private final ColorGrid colorGrid;
    private final ColorMeasure colorMeasure;

    private Map<Color, Color> nearestNeighbors;

    public RecenteredColorMeasure(
            final ColorPalette colorPalette,
            final ColorGrid colorGrid,
            final ColorMeasure colorMeasure) {
        this.colorPalette = colorPalette;
        this.colorGrid = colorGrid;
        this.colorMeasure = colorMeasure;
        // TODO: Consider taking in a second, colorMeasure to use when measuring distance
    }

    /* Helper to cache the pre-computed map of nearest colors */
    private Map<Color, Color> getNearestNeighbors() {
        if (this.nearestNeighbors == null) {
            this.nearestNeighbors = this.calculateNearestNeighbors();
        }
        return this.nearestNeighbors;
    }

    /* Iterate the color grid and find the "true color" closest to each
    color in the palette */
    private Map<Color, Color> calculateNearestNeighbors() {
        /* Map of color from palette to the "true color" it is closest to in the grid */
        Map<Color, Color> nearestNeighbors = new HashMap<>();
        double minDistance = 0.0;
        for (Color colorFromPalette : this.colorPalette.getColors()) {
            // For each color in the palette, iterate the ColorGrid
            for (int w = 0; w < this.colorGrid.getWidth(); w++) {
                for (int h = 0; h < this.colorGrid.getHeight(); h++) {
                    // Find the closest true color from the grid based on the colorMeasure
                    Color colorFromGrid = this.colorGrid.getColor(w, h);
                    double distance = colorMeasure.getDistance(colorFromPalette, colorFromGrid);
                    if (nearestNeighbors.get(colorFromPalette) == null || distance < minDistance) {
                        minDistance = distance;
                        nearestNeighbors.put(colorFromPalette, colorFromGrid);
                    }
                }
            }
        }
        return nearestNeighbors;
    }

    @Override
    public double getDistance(Color imageColor, Color paletteColor) {
        Map<Color, Color> nearestNeighbors = this.getNearestNeighbors();

        /* Measure the distance, not to the palette color itself but to whichever "true color" from the image is closest
        to that palette color */
        Color proxyColor = nearestNeighbors.get(paletteColor);
        return this.colorMeasure.getDistance(imageColor, proxyColor);
    }
}
