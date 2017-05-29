package io.chadheise.lego.brick.grid;

import java.util.function.Function;

import io.chadheise.lego.brick.Brick;

public class BrickGridSplitter1 implements Function<BrickGrid, BrickGrid> {

    // Best place to put a seam is 2 away from a seam above or below (for bricks
    // of length 4 or less)

    // Do this 1 row at a time:
    // Put a line everywhere you can
    // Combine unnecessary splits

    @Override
    public BrickGrid apply(BrickGrid brickGrid) {
        BrickGrid newGrid = new BrickGrid(brickGrid);

        // For first row, make largest bricks as possible, up to 4 wide
        for (int x = 0; x < newGrid.getWidth(); x++) {
            int offSet = 0;
            Brick brick = newGrid.getBrick(x, 0);
            while (offSet + 4 < brick.getWidth()) {
                Brick newBrick = new Brick(brick.getColor(), 4);
                newGrid.setBrick(newBrick, x + offSet, 0);
                offSet += 4;
            }
            int remainder = brick.getWidth() - offSet - 4;
            if (remainder > 0) {
                Brick newBrick = new Brick(brick.getColor(), remainder);
                newGrid.setBrick(newBrick, x + brick.getWidth() - remainder, 0);
            }

            x += brick.getWidth() - 1; // -1 since the for loop will
                                       // increment 1
        }

        // Place a seem 2 away from a seem in the previous row, then recombine
        // unnecessary splits
        for (int y = 1; y < newGrid.getHeight(); y++) {
            for (int x = 0; x < newGrid.getWidth(); x++) {
                Brick brick = newGrid.getBrick(x, y);
                // once seam is on left in previous row, add 2 (if possible) and
                // put seam on left in current row
                if (isSeamOnLeftInPreviousRow(newGrid, x, y)) {
                    int size = 1;
                    if (isNextSameColor(newGrid, x, y)) {
                        size++;
                    }
                    // if (isNextSameColor(newGrid, x + 1, y)) {
                    // size++;
                    // }
                    Brick newBrick = new Brick(brick.getColor(), size);
                    newGrid.setBrick(newBrick, x, y);
                    x += size - 1; // -1 since the for loop will increment 1
                }
            }
        }
        return newGrid;
    }

    private boolean isSeamOnLeftInPreviousRow(BrickGrid brickGrid, int column,
            int row) {
        int previousRow = row - 1;
        if (column <= 0) { // The left side of the image is treated as a seam
            return true;
        } else if (brickGrid.getBrick(column - 1, previousRow) == brickGrid
                .getBrick(column, previousRow)) {
            return true;
        }
        return false;
    }

    private boolean isNextSameColor(BrickGrid brickGrid, int column, int row) {
        if (column + 1 >= brickGrid.getWidth()) {
            return false;
        } else if (brickGrid.getColor(column, row) == brickGrid.getColor(
                column + 1, row)) {
            return true;
        }
        return false;
    }

}
