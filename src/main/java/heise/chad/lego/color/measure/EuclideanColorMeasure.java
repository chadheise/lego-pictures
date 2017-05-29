package heise.chad.lego.color.measure;

import java.awt.Color;

public class EuclideanColorMeasure implements ColorMeasure {

	@Override
	public double getDistance(Color color1, Color color2) {
		int diffRed = color1.getRed()-color2.getRed();
		int diffBlue = color1.getBlue()-color2.getBlue();
		int diffGreen = color1.getGreen()-color2.getGreen();
		return Math.sqrt(diffRed*diffRed + diffBlue*diffBlue + diffGreen*diffGreen);
	}

}
