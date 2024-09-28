package io.chadheise.lego.color.palette;

import java.awt.Color;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public final class NamedColorPalette implements ColorPalette {

    private final Map<String, Color> nameToColor;
    private final Map<Color, String> colorToName;

    public NamedColorPalette(
            final Map<String, Color> nameToColor,
            final Map<Color, String> colorToName) {
        this.nameToColor = nameToColor;
        this.colorToName = colorToName;
    }

    @Override
    public Set<Color> getColors() {
        return colorToName.keySet();
    }

    public String getName(final Color color) {
        return colorToName.get(color);
    }

    public Color getColor(final String name) {
        return nameToColor.get(name);
    }

    public final Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private final Map<String, Color> nameToColor = new HashMap<>();
        private final Map<Color, String> colorToName = new HashMap<>();

        public Builder withColor(String name, Color color) {
            nameToColor.put(name, color);
            colorToName.put(color, name);
            return this;
        }

        public ColorPalette build() {
            return new NamedColorPalette(nameToColor, colorToName);
        }

    }

}
