package generic.color.comparator;

import java.awt.Color;

public class RGBColorComparator implements ColorComparator {

	@Override
	public double getDistance(Color color1, Color color2) {
		return Math.abs(color1.getRGB() - color2.getRGB());
	}

}
