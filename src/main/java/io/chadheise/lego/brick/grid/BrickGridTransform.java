package io.chadheise.lego.brick.grid;

import java.awt.Color;
import java.util.function.Function;

import io.chadheise.lego.brick.Brick;
import io.chadheise.lego.color.grid.ColorGrid;

/* Converts a color grid into a brick grid by combining adjacent pixels of the
   same color into a single, wide brick */
public class BrickGridTransform implements Function<ColorGrid, BrickGrid> {

    public BrickGrid apply(ColorGrid colorGrid) {
        BrickGrid brickGrid = new BrickGrid(colorGrid.getWidth(),
                colorGrid.getHeight());

        for (int y = 0; y < colorGrid.getHeight(); y++) { // Iterate rows
            for (int x = 0; x < colorGrid.getWidth(); x++) {
                // Brick brick = new Brick(colorGrid.getColor(x, y), 1);
                // brickGrid.setBrick(brick, x, y);

                int width = 1;
                Color color = colorGrid.getColor(x, y);
                while (x + width - 1 < colorGrid.getWidth() && colorGrid.getColor(x + width - 1, y) == color) {
                    width++;
                }
                width--;// Width has gone one too far
                Brick brick = new Brick(color, width);
                brickGrid.setBrick(brick, x, y);
                x += width - 1;
            }
        }

        return brickGrid;
    }
}
