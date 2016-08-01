package heise.chad.lego.picture;

import java.awt.Color;
import java.util.List;

import heise.chad.lego.generic.color.grid.ColorGrid;

import java.util.ArrayList;

public class BrickGrid implements ColorGrid {
	// TODO: Make private
	public List<List<BrickUnit>> brickUnits; // outside array is rows, each row
												// contains an array of columns

	public BrickGrid(int width, int height) {
		brickUnits = new ArrayList<List<BrickUnit>>(height);
		for (int y = 0; y < height; y++) {
			List<BrickUnit> array = new ArrayList<BrickUnit>();
			for (int x = 0; x < width; x++) {
				array.add(null);
			}
			brickUnits.add(array);
		}
	}

	/**
	 * Copy constructor
	 * 
	 * @param brickGrid
	 */
	public BrickGrid(BrickGrid brickGrid) {
		this(brickGrid.getWidth(), brickGrid.getHeight());
		for (int y = 0; y < brickGrid.getHeight(); y++) {
			int x = 0;
			while (x < brickGrid.getWidth()) {
				setBrick(brickGrid.getBrick(x, y), x, y);
				x += brickGrid.getBrick(x, y).getWidth();
			}
		}
	}

	public Brick getBrick(int column, int row) {
		return brickUnits.get(row).get(column).getBrick();
	}

	/**
	 * Inserts a brick starting at the provided position
	 * 
	 * @param brick
	 * @param column
	 * @param row
	 */
	public void setBrick(Brick brick, int column, int row) {
		// TODO: Validate row and column are < height & width & that the brick
		// width doesn't exceed the grid
		BrickUnit brickUnit = new BrickUnit(brick);
		for (int x = column; x < column + brick.getWidth(); x++) {
			brickUnits.get(row).set(x, brickUnit);
		}
	}

	@Override
	public int getHeight() {
		return brickUnits.size();
	}

	@Override
	public int getWidth() {
		return brickUnits.get(0).size();
	}

	@Override
	public Color getColor(int x, int y) {
		return brickUnits.get(y).get(x).getBrick().getColor();
	}

}
