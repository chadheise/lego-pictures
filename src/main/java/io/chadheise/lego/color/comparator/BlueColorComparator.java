package io.chadheise.lego.color.comparator;

import java.awt.Color;
import java.util.Comparator;

public class BlueColorComparator implements Comparator<Color> {

    @Override
    public int compare(Color color1, Color color2) {
        return color1.getBlue() - color2.getBlue();
    }

}
