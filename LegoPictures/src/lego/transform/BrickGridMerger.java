package lego.transform;

import lego.Brick;
import lego.BrickGrid;

public class BrickGridMerger implements BrickGridTransform {

	private final int mergeWidth;

	/**
	 * Initialize the merge with the number of bricks to be combined.
	 * 
	 * @param mergeNumber
	 *            The size of the bricks to create by merging smaller adjacent
	 *            bricks.
	 */
	public BrickGridMerger(int mergeNumber) {
		this.mergeWidth = mergeNumber;
	}

	@Override
	public BrickGrid transform(BrickGrid brickGrid) {
		BrickGrid newGrid = new BrickGrid(brickGrid);

		// TODO: Change to alternate iteration front-to-back, back-to-front, etc
		for (int y = 0; y < newGrid.getHeight(); y++) {
			for (int x = 0; x < newGrid.getWidth(); x++) {
				Brick brick = newGrid.getBrick(x, y);
				if (x + brick.getWidth() < newGrid.getWidth()) {
					Brick nextBrick = newGrid.getBrick(x + brick.getWidth(), y);
					if (brick.getColor() == nextBrick.getColor()
							&& brick.getWidth() + nextBrick.getWidth() == mergeWidth) {
						Brick newBrick = new Brick(brick.getColor(), mergeWidth);
						newGrid.setBrick(newBrick, x, y);
						x += mergeWidth - 1; // -1 Since for loop will increment
												// by 1
					} else {
						x += brick.getWidth() - 1; // -1 Since for loop will
													// increment by 1
					}
				}
			}
		}

		return newGrid;
	}

}
