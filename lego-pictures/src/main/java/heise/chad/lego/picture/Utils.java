package heise.chad.lego.picture;

public class Utils {

	public static final double millimetersInAnInch = 25.4;
	
	int widthInchesToNumBrickUnits(int widthInches) {
		return (int) ((widthInches*millimetersInAnInch)/BrickUnit.millimeterWidth);
	}
	
	int heightInchesToNumBrickUnits(int heightInches) {
		return (int) ((heightInches*millimetersInAnInch)/BrickUnit.millimeterHeight);
	}
	
}
