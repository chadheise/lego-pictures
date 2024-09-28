package io.chadheise.lego.color.string;

import java.awt.Color;
import java.util.function.Function;

public class ColorStringHex implements Function<Color, String> {

    @Override
    public String apply(final Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }
}
