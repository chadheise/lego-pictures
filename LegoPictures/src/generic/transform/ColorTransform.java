package generic.transform;

import generic.color.grid.ColorGrid;
import generic.color.grid.MutableColorGridImpl;
import generic.color.palette.ColorPalette;

import java.awt.Color;

public class ColorTransform implements ColorGridTransform {

	ColorPalette palette;
	
	public ColorTransform(ColorPalette palette) {
		this.palette = palette;
	}
	
	@Override
	public ColorGrid transform(ColorGrid colorGrid) {
		MutableColorGridImpl outputColorGrid = new MutableColorGridImpl(colorGrid.getWidth(), colorGrid.getHeight());
		for (int x=0; x < colorGrid.getWidth(); x++) {
			for (int y=0; y < colorGrid.getHeight(); y++) {
				Color inputColor = colorGrid.getColor(x, y);
				Color outputColor = palette.getClosestColor(inputColor);
				outputColorGrid.setColor(outputColor, x, y);
			}
		}
		return outputColorGrid;
	}

}
