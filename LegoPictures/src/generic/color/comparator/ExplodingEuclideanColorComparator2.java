package generic.color.comparator;

import java.awt.Color;

public class ExplodingEuclideanColorComparator2 implements ColorComparator {

	private int threshold;
	
	private double rFactor;
	private double gFactor;
	private double bFactor;
	
	public ExplodingEuclideanColorComparator2(double rFactor, double gFactor, double bFactor, int threshold) {
		this.rFactor = rFactor;
		this.gFactor = gFactor;
		this.bFactor = bFactor;
		this.threshold = threshold;
	}
	
	@Override
	public double getDistance(Color color1, Color color2) {
		int rValue = color1.getRed();
		int gValue = color1.getGreen();
		int bValue = color1.getBlue();
		
		if (isRed(rValue, gValue, bValue)) {
			rValue = explodeValue(color1.getRed(), rFactor);
		}
		else if (isGreen(rValue, gValue, bValue)) {
			gValue = explodeValue(color1.getGreen(), gFactor);
		}
		else if (isBlue(rValue, gValue, bValue)) {
			bValue = explodeValue(color1.getGreen(), bFactor);
		}
		else if (isYellow(rValue, gValue, bValue)) {
			rValue = explodeValue(color1.getRed(), rFactor);
			gValue = explodeValue(color1.getGreen(), gFactor);
		}
		else if (isAqua(rValue, gValue, bValue)) {
			gValue = explodeValue(color1.getGreen(), gFactor);
			bValue = explodeValue(color1.getGreen(), bFactor);
		}
		
		int rDiff = rValue - color2.getRed();
		int gDiff = gValue - color2.getGreen();
		int bDiff = bValue - color2.getBlue();
		
		return Math.sqrt(rDiff*rDiff + bDiff*bDiff + gDiff*gDiff);
	}

	private int explodeValue(int value, double factor) {
		int remainingDistance = 255-value; // Max distance - current value
		return (int) (remainingDistance*factor + value);
	}
	
	private boolean isRed(int r, int g, int b) {
		return (r > b + threshold && r > g + threshold);
	}
	
	private boolean isGreen(int r, int g, int b) {
		return (g > r + threshold && g > b + threshold);
	}
	
	private boolean isBlue(int r, int g, int b) {
		return (b > r + threshold && b > g + threshold);
	}
	
	private boolean isYellow(int r, int g, int b) {
		return (r > b + threshold && Math.abs(r-g) < threshold);
	}
	
	private boolean isAqua(int r, int g, int b) {
		return (g > r + threshold && Math.abs(g-b) < threshold);
	}
	
}
