package lego;

import generic.color.comparator.ColorComparator;
import generic.color.palette.ComparatorColorPalette;

import java.awt.Color;

public class SimpleLegoColorPalette extends ComparatorColorPalette {
	
	public SimpleLegoColorPalette(ColorComparator colorComparator) {
		super(colorComparator);
		
		Color tmpColor;
		// Black
		tmpColor = new Color(0, 0, 0);
		RGBToColor.put(tmpColor.getRGB(), tmpColor);
		// White
		tmpColor = new Color(255, 255, 255);
		RGBToColor.put(tmpColor.getRGB(), tmpColor);
		// Red
		tmpColor = new Color(230, 0, 18);
		RGBToColor.put(tmpColor.getRGB(), tmpColor);
		// Blue
		tmpColor = new Color(0, 46, 237);
		RGBToColor.put(tmpColor.getRGB(), tmpColor);
		// Yellow
		tmpColor = new Color(246, 222, 0);
		RGBToColor.put(tmpColor.getRGB(), tmpColor);
		// Dark Bluish Grey
		tmpColor = new Color(71, 75, 78);
		RGBToColor.put(tmpColor.getRGB(), tmpColor);
		// Light Bluish Grey
		tmpColor = new Color(159, 163, 188);
		RGBToColor.put(tmpColor.getRGB(), tmpColor);
		// Reddish Brown
		tmpColor = new Color(78, 24, 17);
		RGBToColor.put(tmpColor.getRGB(), tmpColor);
		// Green
		tmpColor = new Color(7, 159, 32);
		RGBToColor.put(tmpColor.getRGB(), tmpColor);

	}

}
