package generic.color.comparator;

import generic.color.measure.ColorMeasure;

import java.awt.Color;
import java.util.Comparator;

/**
 * Compares colors based on their distance from a third, predetermined color.
 *
 */
public class DistanceColorComparator implements Comparator<Color> {

	private final Color sourceColor;
	private final ColorMeasure colorMeasure;
	
	public DistanceColorComparator(Color sourceColor, ColorMeasure colorMeasure) {
		this.sourceColor = sourceColor;
		this.colorMeasure = colorMeasure;
	}
	
	@Override
	public int compare(Color color1, Color color2) {
		double distance1 = colorMeasure.getDistance(sourceColor, color1);
		double distance2 = colorMeasure.getDistance(sourceColor, color2);
		return (int) (distance1 - distance2);
	}

	
	
}
