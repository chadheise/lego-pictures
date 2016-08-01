package generic.color.transform;

import generic.color.measure.ColorMeasure;
import generic.color.palette.ColorPalette;
import generic.transform.Transform;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Transforms one color into another by finding the closest color in a provided
 * color palette. Closeness is determined by the provided ColorComparator
 * strategy.
 *
 */
public class ColorPaletteColorTransform implements Transform<Color> {

	private final ColorPalette palette;
	private final ColorMeasure comparator;

	public ColorPaletteColorTransform(final ColorPalette palette,
			final ColorMeasure comparator) {
		this.palette = palette;
		this.comparator = comparator;
	}

	@Override
	public Color transform(Color color) {
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
