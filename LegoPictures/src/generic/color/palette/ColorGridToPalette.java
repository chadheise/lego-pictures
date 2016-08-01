package generic.color.palette;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import generic.color.comparator.DistanceColorComparator;
import generic.color.grid.ColorGrid;
import generic.color.grid.MutableColorGrid;
import generic.color.grid.MutableColorGridImpl;
import generic.color.measure.ColorMeasure;
import generic.color.measure.EuclideanColorMeasure;

public final class ColorGridToPalette {

	public static final ColorPalette getColorPalette(ColorGrid colorGrid) {
		Set<Color> colors = new HashSet<Color>();
		
		for (int y = 0; y < colorGrid.getHeight(); y++) {
			for (int x = 0; x < colorGrid.getWidth(); x++) {
				colors.add(colorGrid.getColor(x, y));
			}
		}		
		
		return new FixedColorPalette(colors);
	}
	
	public static final ColorGrid getColorGrid(ColorPalette colorPalette) {
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
