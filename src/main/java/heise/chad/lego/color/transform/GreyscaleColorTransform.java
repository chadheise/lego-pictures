package heise.chad.lego.color.transform;

import java.awt.Color;
import java.util.function.Function;

public class GreyscaleColorTransform implements Function<Color, Color> {

    @Override
    public Color apply(Color color) {
        int r = (int) (.2126 * color.getRed());
        int g = (int) (.7152 * color.getGreen());
        int b = (int) (.0722 * color.getBlue());
        int sum = r + g + b;
        return new Color(sum, sum, sum);
    }
}
