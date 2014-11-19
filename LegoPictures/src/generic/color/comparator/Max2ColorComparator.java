package generic.color.comparator;

import java.awt.Color;

public class Max2ColorComparator implements ColorComparator {

	@Override
	public double getDistance(Color color1, Color color2) {
		int red = color1.getRed();
		int green = color1.getGreen();
		int blue = color1.getBlue();
		
		double diffX = 1;
		double diffY = 1;

		if (red < green && red < blue) {
			diffX = green - color2.getGreen();
			diffY = blue - color2.getBlue();
		}
		else if (green < red && green < blue) {
			diffX = red - color2.getRed();
			diffY = blue - color2.getBlue();
		}
		else if (blue < red && blue < green) {
			diffX = red - color2.getRed();
			diffY = green - color2.getGreen();
		}
		
		return Math.sqrt(diffX*diffX + diffY*diffY);
		
	}

}
