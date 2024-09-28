package io.chadheise.lego.color.string;

import java.awt.*;
import java.util.function.Function;

public class ColorStringRGB implements Function<Color, String> {

    @Override
    public String apply(final Color color) {
        return String.format("(%d, %d, %d)", color.getRed(), color.getGreen(), color.getBlue());
    }
}
