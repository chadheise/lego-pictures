package heise.chad.lego.generic.color.grid;

import java.awt.Color;

import heise.chad.lego.generic.transform.Transform;

/**
 * Transforms a color grid into another color grid with each color changed based
 * on the provided ColorTransform.
 *
 */
public class ColorColorGridTransform implements Transform<ColorGrid> {

	private final Transform<Color> colorTransform;

	public ColorColorGridTransform(final Transform<Color> colorTransform) {
		this.colorTransform = colorTransform;
	}

	@Override
	public ColorGrid transform(final ColorGrid colorGrid) {
		MutableColorGridImpl outputColorGrid = new MutableColorGridImpl(
				colorGrid.getWidth(), colorGrid.getHeight());
		for (int x = 0; x < colorGrid.getWidth(); x++) {
			for (int y = 0; y < colorGrid.getHeight(); y++) {
				Color inputColor = colorGrid.getColor(x, y);
				Color outputColor = colorTransform.transform(inputColor);
				outputColorGrid.setColor(outputColor, x, y);
			}
		}
		return outputColorGrid;
	}

}
