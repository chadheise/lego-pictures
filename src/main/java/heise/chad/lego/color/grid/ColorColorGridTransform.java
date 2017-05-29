package heise.chad.lego.color.grid;

import java.awt.Color;
import java.util.function.Function;

/**
 * Transforms a color grid into another color grid with each color changed based
 * on the provided ColorTransform.
 *
 */
public class ColorColorGridTransform implements Function<ColorGrid, ColorGrid> {

    private final Function<Color, Color> colorTransform;

    public ColorColorGridTransform(final Function<Color, Color> colorTransform) {
        this.colorTransform = colorTransform;
    }

    @Override
    public ColorGrid apply(final ColorGrid colorGrid) {
        MutableColorGridImpl outputColorGrid = new MutableColorGridImpl(
                colorGrid.getWidth(), colorGrid.getHeight());
        for (int x = 0; x < colorGrid.getWidth(); x++) {
            for (int y = 0; y < colorGrid.getHeight(); y++) {
                Color inputColor = colorGrid.getColor(x, y);
                Color outputColor = colorTransform.apply(inputColor);
                outputColorGrid.setColor(outputColor, x, y);
            }
        }
        return outputColorGrid;
    }

}
