package heise.chad.lego.generic.color.comparator;

import java.awt.Color;
import java.util.Comparator;

public class RedColorComparator implements Comparator<Color> {

	@Override
	public int compare(Color color1, Color color2) {
		return color1.getRed() - color2.getRed();
	}

}
