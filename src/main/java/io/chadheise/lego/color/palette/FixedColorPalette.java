package io.chadheise.lego.color.palette;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public final class FixedColorPalette implements ColorPalette {

    private final Set<Color> colors;

    public FixedColorPalette(Set<Color> colors) {
        this.colors = colors;
    }

    @Override
    public Set<Color> getColors() {
        return colors;
    }

    public final Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<Color> colors = new HashSet<Color>();

        public Builder withColor(Color color) {
            colors.add(color);
            return this;
        }

        public ColorPalette build() {
            return new FixedColorPalette(colors);
        }

    }

}
