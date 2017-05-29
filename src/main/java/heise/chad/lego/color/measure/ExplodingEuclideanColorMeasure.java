package heise.chad.lego.color.measure;

import java.awt.Color;

public class ExplodingEuclideanColorMeasure implements ColorMeasure {

	private double rFactor;
	private double gFactor;
	private double bFactor;
	
	public ExplodingEuclideanColorMeasure(double rFactor, double gFactor, double bFactor) {
		this.rFactor = rFactor;
		this.gFactor = gFactor;
		this.bFactor = bFactor;
	}
	
	@Override
	public double getDistance(Color color1, Color color2) {
		int diffRed = explodeValue(color1.getRed(), rFactor) - color2.getRed();
		int diffGreen = explodeValue(color1.getGreen(), gFactor) - color2.getGreen();
		int diffBlue = explodeValue(color1.getBlue(), bFactor) - color2.getBlue();
		if (isBlueGreatest(color1.getRed(), color1.getGreen(), color1.getBlue())) {
			diffRed = color1.getRed() - color2.getRed();
			diffGreen = color1.getGreen() - color2.getGreen();
			diffBlue = explodeValue(color1.getBlue(), 1) - color2.getBlue();
		}
		
		return Math.sqrt(diffRed*diffRed + diffBlue*diffBlue + diffGreen*diffGreen);
	}

	private int explodeValue(int value, double factor) {
		int remainingDistance = 255-value; // Max distance - current value
		return (int) (remainingDistance*factor + value);
	}
	
	private boolean isBlueGreatest(int r, int g, int b) {
		return (b > r + 15 && b > g + 15);
	}
	
}
