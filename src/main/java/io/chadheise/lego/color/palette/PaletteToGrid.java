package io.chadheise.lego.color.palette;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import io.chadheise.lego.color.comparator.DistanceColorComparator;
import io.chadheise.lego.color.grid.ColorGrid;
import io.chadheise.lego.color.grid.MutableColorGrid;
import io.chadheise.lego.color.grid.MutableColorGridImpl;
import io.chadheise.lego.color.measure.ColorMeasure;
import io.chadheise.lego.color.measure.EuclideanColorMeasure;

public class PaletteToGrid implements Function<ColorPalette, ColorGrid> {

    @Override
    public ColorGrid apply(ColorPalette colorPalette) {
        List<Color> colors = new ArrayList<>(colorPalette.getColors());
        MutableColorGrid grid = new MutableColorGridImpl(1, colors.size());

        Color white = new Color(255, 255, 255);
        ColorMeasure colorMeasure = new EuclideanColorMeasure();
        Collections.sort(colors, new DistanceColorComparator(white, colorMeasure));

        int i = 0;
        for (Color c : colors) {
            grid.setColor(c, 0, i);
            i++;
        }

        return grid;
    }

}
