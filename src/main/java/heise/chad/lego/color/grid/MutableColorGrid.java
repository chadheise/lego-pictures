package heise.chad.lego.color.grid;

import java.awt.Color;

public interface MutableColorGrid extends ColorGrid {
	void setColor(Color color, int x, int y);
}
