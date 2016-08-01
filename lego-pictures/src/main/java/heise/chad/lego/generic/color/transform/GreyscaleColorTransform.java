package heise.chad.lego.generic.color.transform;

import java.awt.Color;

import heise.chad.lego.generic.transform.Transform;

public class GreyscaleColorTransform implements Transform<Color> {

	@Override
	public Color transform(Color color) {
		int r = (int) (.2126 * color.getRed());
		int g = (int) (.7152 * color.getGreen());
		int b = (int) (.0722 * color.getBlue());
		int sum = r + g + b;
		return new Color(sum, sum, sum);
	}
}
