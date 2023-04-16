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
                    int newBrickWidth;

                    // This order is important. Prefer full, 4 width bricks if possible
                    if (!hasSeamInPreviousRow(newGrid, w + 4, h)) {
                        newBrickWidth = 4;
                    } else if (!hasSeamInPreviousRow(newGrid, w + 2, h)) {
                        // Next, prefer bricks of size 2 so the seams are in the middle of 4-wide bricks
                        newBrickWidth = 2;
                    } else if (!hasSeamInPreviousRow(newGrid, w + 3, h)) {
                        // Since the seam won't be in the middle, use the next largest brick (3)
                        newBrickWidth = 3;
                    } else if (!hasSeamInPreviousRow(newGrid, w + 1, h)) {
                        // Use size 1 if necessary to avoid a seam
                        newBrickWidth = 1;
                    } else {
                        // If a seam cannot be avoided, use the largest standard brick (4)
                        newBrickWidth = 4;
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

                    // Because we prioritized bricks of width 2 over width 3, its possible we will
                    // end up with some bricks of width 2 next to a brick of width 1 of the same color
                    // based on where the seams are. If this happens, re-combine them into a single
                    // brick of width 3
                    if (newBrickWidth == 1 && w != 0) {
                        Brick previousBrick = newGrid.getBrick(w - 1, h);
                        if (previousBrick.getWidth() == 2 && previousBrick.getColor() == newBrick.getColor()) {
                            Brick threeWideBrick = new Brick(newBrick.getColor(), 3);
                            newGrid.setBrick(threeWideBrick, w - 2, h);
                        }
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
