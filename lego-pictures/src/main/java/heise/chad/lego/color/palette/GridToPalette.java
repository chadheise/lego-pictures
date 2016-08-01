package heise.chad.lego.color.palette;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import heise.chad.lego.color.grid.ColorGrid;

public final class GridToPalette implements Function<ColorGrid, ColorPalette> {

    @Override
    public ColorPalette apply(ColorGrid colorGrid) {
        Set<Color> colors = new HashSet<Color>();

        for (int y = 0; y < colorGrid.getHeight(); y++) {
            for (int x = 0; x < colorGrid.getWidth(); x++) {
                colors.add(colorGrid.getColor(x, y));
            }
        }

        return new FixedColorPalette(colors);
    }

}
