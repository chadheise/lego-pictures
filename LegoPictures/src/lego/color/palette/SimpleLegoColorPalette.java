package lego.color.palette;

import generic.color.palette.ColorPalette;
import generic.color.palette.FixedColorPalette;

import java.awt.Color;
import java.util.Set;

public final class SimpleLegoColorPalette implements ColorPalette {

	private final ColorPalette delegatePalette;

	public SimpleLegoColorPalette() {
		delegatePalette = new FixedColorPalette.Builder()
				.withColor(new Color(0, 0, 0)) // Black
				.withColor(new Color(255, 255, 255)) // White
				.withColor(new Color(230, 0, 18)) // Red
				.withColor(new Color(0, 46, 237)) // Blue
				.withColor(new Color(246, 222, 0)) // Yellow
				.withColor(new Color(71, 75, 78)) // Dark Bluish Grey
				.withColor(new Color(159, 163, 188)) // Light Bluish Grey
				.withColor(new Color(78, 24, 17)) // Reddish Brown
				.withColor(new Color(7, 159, 32)) // Green
				.build();
	}

	@Override
	public Set<Color> getColors() {
		return delegatePalette.getColors();
	}

}
