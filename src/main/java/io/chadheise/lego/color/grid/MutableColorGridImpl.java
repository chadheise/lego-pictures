package io.chadheise.lego.color.grid;

import java.awt.Color;
import java.util.ArrayList;

public class MutableColorGridImpl implements MutableColorGrid {

    ArrayList<ArrayList<Color>> colors;

    public MutableColorGridImpl(int width, int height) {
        colors = new ArrayList<ArrayList<Color>>(width);
        for (int i = 0; i < width; i++) {
            ArrayList<Color> array = new ArrayList<Color>();
            for (int j = 0; j < height; j++) {
                array.add(null);
            }
            colors.add(i, array);
        }
    }

    @Override
    public int getHeight() {
        return colors.get(0).size();
    }

    @Override
    public int getWidth() {
        return colors.size();
    }

    @Override
    public Color getColor(int x, int y) {
        return colors.get(x).get(y);
    }

    @Override
    public void setColor(Color color, int x, int y) {
        colors.get(x).set(y, color);
    }

}
