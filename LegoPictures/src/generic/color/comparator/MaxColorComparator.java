package generic.color.comparator;

import java.awt.Color;

public class MaxColorComparator implements ColorComparator {

	@Override
	public double getDistance(Color color1, Color color2) {
		int red = color1.getRed();
		int green = color1.getGreen();
		int blue = color1.getBlue();
		
		// If the color is mostly red, return the closest red value
		if (red > green && red > blue) {
			return Math.abs(color2.getRed() - red);
		}
		// If the color is mostly green, return the closest green value
		else if (green > red && green > blue) {
			return Math.abs(color2.getGreen() - green);
		}
		// If the color is mostly blue, return the closest blue value
		return Math.abs(color2.getBlue() - blue);
		
	}

}
