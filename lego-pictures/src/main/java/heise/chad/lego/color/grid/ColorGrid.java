package heise.chad.lego.color.grid;

import java.awt.Color;

public interface ColorGrid {
	int getHeight();
	int getWidth();
	Color getColor(int x, int y);
}
