package heise.chad.lego.color.palette;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import heise.chad.lego.color.comparator.DistanceColorComparator;
import heise.chad.lego.color.grid.ColorGrid;
import heise.chad.lego.color.grid.MutableColorGrid;
import heise.chad.lego.color.grid.MutableColorGridImpl;
import heise.chad.lego.color.measure.ColorMeasure;
import heise.chad.lego.color.measure.EuclideanColorMeasure;

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
