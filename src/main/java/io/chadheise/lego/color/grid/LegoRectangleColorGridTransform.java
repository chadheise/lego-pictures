package io.chadheise.lego.color.grid;

import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

import io.chadheise.lego.brick.BrickUnit;
import io.chadheise.lego.color.merger.ColorMerger;

/**
 * Takes as input a color grid and compresses the width and height to match a
 * desired width using rectangle Lego shaped pixels.
 */
public class LegoRectangleColorGridTransform implements Function<ColorGrid, ColorGrid> {

    private final int desiredWidth;
    private final ColorMerger colorMerger;

    public LegoRectangleColorGridTransform(int desiredWidth, ColorMerger colorMerger) {
        this.desiredWidth = desiredWidth;
        this.colorMerger = colorMerger;
    }

    @Override
    public ColorGrid apply(ColorGrid colorGrid) {
        int gridWidth = desiredWidth;
        int gridHeight = getGridHeight(colorGrid);
        MutableColorGrid outputColorGrid = new MutableColorGridImpl(gridWidth,
                gridHeight);

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                Color gridColor = this.colorMerger.apply(getGridColors(colorGrid, x, y));
                outputColorGrid.setColor(gridColor, x, y);
            }
        }
        return outputColorGrid;
    }

    private int getPixelsPerGridWidth(ColorGrid colorGrid) {
        // TODO: Validate this returns > 0
        return colorGrid.getWidth() / desiredWidth;
    }

    private int getPixelsPerGridHeight(ColorGrid colorGrid) {
        // TODO: Validate this returns > 0
        return colorGrid.getHeight() / getGridHeight(colorGrid);
    }

    private int getGridHeight(ColorGrid colorGrid) {
        double heightWidthRatio = (double) colorGrid.getHeight()
                / (double) colorGrid.getWidth();
        double brickWidthToHeightRatio = BrickUnit.MILLIMETER_WIDTH
                / BrickUnit.MILLIMETER_HEIGHT;
        return (int) Math.round(heightWidthRatio * brickWidthToHeightRatio
                * (double) desiredWidth);

    }

    private Collection<Color> getGridColors(ColorGrid colorGrid, int x, int y) {
        int pixelsPerGridWidth = getPixelsPerGridWidth(colorGrid);
        int pixelsPerGridHeight = getPixelsPerGridHeight(colorGrid);

        Collection<Color> gridPixelColors = new HashSet<Color>();
        // Iterate pixels in grid
        for (int w = x * pixelsPerGridWidth; w < (x + 1) * pixelsPerGridWidth; w++) {
            for (int h = y * pixelsPerGridHeight; h < (y + 1)
                    * pixelsPerGridHeight; h++) {
                gridPixelColors.add(colorGrid.getColor(w, h));
            }
        }

        return gridPixelColors;
    }

}
