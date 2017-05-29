package heise.chad.lego.color.grid;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.function.Function;

/**
 * Transforms a BufferedImage into a ColorGrid
 *
 */
public class BufferedImageColorGridTransform implements Function<BufferedImage, ColorGrid> {

    @Override
    public ColorGrid apply(final BufferedImage bufferedImage) {
        return new ColorGrid() {

            @Override
            public int getHeight() {
                return bufferedImage.getHeight();
            }

            @Override
            public int getWidth() {
                return bufferedImage.getWidth();
            }

            @Override
            public Color getColor(int x, int y) {
                int rgb = bufferedImage.getRGB(x, y);
                return new Color(rgb);
            }

        };
    }

}
