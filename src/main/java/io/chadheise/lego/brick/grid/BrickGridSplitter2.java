package io.chadheise.lego.brick.grid;

import io.chadheise.lego.brick.Brick;

import java.util.function.Function;

public class BrickGridSplitter2 implements Function<BrickGrid, BrickGrid> {

    @Override
    public BrickGrid apply(BrickGrid brickGrid) {
        BrickGrid newGrid = new BrickGrid(brickGrid);

        for (int h = 0; h < newGrid.getHeight(); h++) {
            for (int w = 0; w < newGrid.getWidth(); w++) {
                Brick brick = newGrid.getBrick(w, h);
                // Don't divide any bricks smaller than 4
                if (brick.getWidth() <= 4) {
                    newGrid.setBrick(brick, w, h);
                    // Increment one less than width since for loop also increments 1
                    w += brick.getWidth() - 1;
                } else {
                    // Prefer the largest brick possible that doesn't line up with a seam
                    // but if a seam is inevitable regardless of width, just use the largest
                    // allowed brick width
                    int maxBrickWidth = 4;
                    int newBrickWidth = maxBrickWidth;
                    for (int n = maxBrickWidth; n > 0; n--) {
                        if (!hasSeamInPreviousRow(newGrid, w + n, h)) {
                            newBrickWidth = n;
                            break;
                        }
                    }

                    // Split brick into 2
                    Brick newBrick = new Brick(brick.getColor(), newBrickWidth);
                    newGrid.setBrick(newBrick, w, h);

                    int remainingBrickWidth = brick.getWidth() - newBrickWidth;
                    if (remainingBrickWidth > 0) {
                        int newPos = w + newBrickWidth;
                        Brick remainingBrick = new Brick(brick.getColor(), remainingBrickWidth);
                        newGrid.setBrick(remainingBrick, w + newBrickWidth, h);
                    }

                    // Increment one less than width since for loop also increments 1
                    w += newBrickWidth - 1;
                }
            }
        }

        return newGrid;
    }

    private boolean hasSeamInPreviousRow(BrickGrid brickGrid, int column, int row) {
        // Return true if we're on the right side of the image
        if (column == brickGrid.getWidth() - 1) {
            return true;
        }

        int previousRow = row - 1;
        if (previousRow >= 0) {
            /*  Return true if there is a seam in the previous row
            (there is an edge between 2 different bricks) */
            if (brickGrid.getBrick(column - 1, previousRow) != brickGrid.getBrick(column, previousRow)) {
                return true;
            }
        }

        int nextRow = row + 1;
        if (nextRow < brickGrid.getHeight()) {
            /*  Return true if there is a seam in the next row
            (there is an edge between 2 different bricks) */
            if (brickGrid.getBrick(column - 1, nextRow) != brickGrid.getBrick(column, nextRow)) {
                return true;
            }
        }


        return false;
    }
}
