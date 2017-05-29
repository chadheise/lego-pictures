package heise.chad.lego.color.comparator;

import java.awt.Color;
import java.util.Comparator;

public enum RGB {

	RED(new RedColorComparator()), //
	GREEN(new GreenColorComparator()), //
	BLUE(new BlueColorComparator());

	private Comparator<Color> comparator;

	RGB(Comparator<Color> comparator) {
		this.comparator = comparator;
	}

	public Comparator<Color> getComparator() {
		return comparator;
	}

	public int getValue(Color color) {
		int value = 0;
		switch (this) {
		case RED:
			value = color.getRed();
			break;
		case GREEN:
			value = color.getGreen();
			break;
		case BLUE:
			value = color.getBlue();
			break;
		}
		return value;
	}

}
