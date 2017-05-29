package heise.chad.lego.color.palette;

import java.awt.Color;
import java.util.Set;

public class LegoColorPalette implements ColorPalette {

	private final ColorPalette delegatePalette;

	public LegoColorPalette() {
		delegatePalette = new FixedColorPalette.Builder()
				.withColor(new Color(226, 0, 28))
				.withColor(new Color(118, 0, 17))
				.withColor(new Color(184, 89, 109))
				.withColor(new Color(164, 21, 65))
				.withColor(new Color(165, 101, 147))
				.withColor(new Color(146, 48, 138))
				.withColor(new Color(253, 167, 254))
				.withColor(new Color(75, 19, 122))
				.withColor(new Color(166, 144, 203))
				.withColor(new Color(158, 165, 185))
				.withColor(new Color(3, 55, 231))
				.withColor(new Color(42, 130, 255))
				.withColor(new Color(80, 156, 255))
				.withColor(new Color(73, 92, 113))
				.withColor(new Color(9, 34, 58))
				.withColor(new Color(90, 156, 203))
				.withColor(new Color(56, 178, 248))
				.withColor(new Color(70, 75, 77))
				.withColor(new Color(100, 146, 125))
				.withColor(new Color(19, 52, 33))
				.withColor(new Color(21, 158, 35))
				.withColor(new Color(148, 236, 8))
				.withColor(new Color(244, 218, 20))
				.withColor(new Color(213, 185, 138))
				.withColor(new Color(125, 98, 63))
				.withColor(new Color(253, 103, 18))
				.withColor(new Color(223, 119, 48))
				.withColor(new Color(162, 64, 11))
				.withColor(new Color(77, 23, 18))
				.withColor(new Color(38, 0, 0))
				.withColor(new Color(119, 87, 88))
				.withColor(new Color(255, 255, 255))
				.withColor(new Color(0, 0, 0)) //
				.build();
	}

	@Override
	public Set<Color> getColors() {
		return delegatePalette.getColors();
	}

}
