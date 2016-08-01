package generic.color.palette;

import generic.color.measure.ColorMeasure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public abstract class ComparatorColorPalette implements ColorPalette {
	
	protected Map<Integer, Color> RGBToColor = new TreeMap<Integer, Color>();
	private ColorMeasure colorComparator;
	
	public ComparatorColorPalette(ColorMeasure colorComparator) {
		this.colorComparator = colorComparator;
	}
	
	// TODO: Refactor
	/**
	 * Returns a sorted collection of the colors in this palette based on their distance to the input palette
	 * @param color
	 */
	private ArrayList<Color> sortByDistance(Color color) {
		// Sort colors in palette by distance to input color
		TreeMap<Double, Color> paletteColors = new TreeMap<Double, Color>();
		for (Color c : RGBToColor.values()) {
			paletteColors.put(colorComparator.getDistance(color, c), c);
		}
		
		// Convert to ArrayList
		return new ArrayList<Color>(paletteColors.values());	
	}
	
	public Color getClosestColor(Color color) {
		return sortByDistance(color).get(0);
	}
	
}
