package generic.color.palette;

import java.awt.Color;

public class GreyScaleColorPalette implements ColorPalette {


	@Override
	public Color getClosestColor(Color color) {
		int r = (int) (.2126 * color.getRed());
		int g = (int) (.7152 * color.getGreen());
		int b = (int) (.0722 * color.getBlue());
		int sum = r + g + b;
		return new Color(sum, sum, sum);
	}

}
