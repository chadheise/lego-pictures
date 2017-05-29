package io.chadheise.lego.color.comparator;

import java.awt.Color;
import java.util.Comparator;

public class GreenColorComparator implements Comparator<Color> {

    @Override
    public int compare(Color color1, Color color2) {
        return color1.getGreen() - color2.getGreen();
    }

}
