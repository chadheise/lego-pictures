package heise.chad.lego.generic.color.measure;

import java.awt.Color;

public class RatioColorMeasure implements ColorMeasure {

	@Override
	public double getDistance(Color color1, Color color2) {
		double red1 = color1.getRed();
		double green1 = color1.getGreen();
		double blue1 = color1.getBlue();
		
		double red2 = color2.getRed();
		double green2 = color2.getGreen();
		double blue2 = color2.getBlue();
		
		// Prevent divide by 0 errors and ratios of 0 for any 0 value
		if (red1 == 0) {
			red1 = 1;
		}
		if (green1 == 0) {
			green1 = 1;
		}
		if (blue1 == 0) {
			blue1 = 1;
		}
		
		// Prevent divide by 0 errors and ratios of 0 for any 0 value
		if (red2 == 0) {
			red2 = 1;
		}
		if (green2 == 0) {
			green2 = 1;
		}
		if (blue2 == 0) {
			blue2 = 1;
		}
		
		double redToGreen1 = red1/green1;
		double redToBlue1 = red1/blue1;
		double greenToBlue1 = green1/blue1;
		
		double redToGreen2 = red2/green2;
		double redToBlue2 = red2/blue2;
		double greenToBlue2 = green2/blue2;
		
		double diffRedToGreen = redToGreen1 - redToGreen2;
		double diffRedToBlue = redToBlue1 - redToBlue2;
		double diffGreenToBlue = greenToBlue1 - greenToBlue2;
		
		return Math.sqrt(diffRedToGreen*diffRedToGreen + diffRedToBlue*diffRedToBlue + diffGreenToBlue*diffGreenToBlue);
		
	}

}
