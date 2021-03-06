package heise.chad.lego.brick.grid;

import java.awt.Color;
import java.util.function.Function;

import heise.chad.lego.brick.Brick;
import heise.chad.lego.color.grid.ColorGrid;

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
