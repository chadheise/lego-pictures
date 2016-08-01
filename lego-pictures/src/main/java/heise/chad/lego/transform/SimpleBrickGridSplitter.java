package heise.chad.lego.transform;

import heise.chad.lego.generic.transform.Transform;
import heise.chad.lego.picture.Brick;
import heise.chad.lego.picture.BrickGrid;

public class SimpleBrickGridSplitter implements Transform<BrickGrid> {

	@Override
	public BrickGrid transform(BrickGrid brickGrid) {
		BrickGrid newGrid = new BrickGrid(brickGrid);
		for (int y = 0; y < newGrid.getHeight(); y++) {
			for (int x = 0; x < newGrid.getWidth(); x++) {
				int offSet = 0;
				Brick brick = newGrid.getBrick(x, y);
				while (offSet + 4 < brick.getWidth()) {
					Brick newBrick = new Brick(brick.getColor(), 4);
					newGrid.setBrick(newBrick, x + offSet, y);
					offSet += 4;
				}
				int remainder = brick.getWidth() - offSet - 4;
				if (remainder > 0) {
					Brick newBrick = new Brick(brick.getColor(), remainder);
					newGrid.setBrick(newBrick,
							x + brick.getWidth() - remainder, y);
				}

				x += brick.getWidth() - 1; // -1 since the for loop will
											// increment 1
			}
		}
		return newGrid;
	}

}
