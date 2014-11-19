package lego;

import generic.color.grid.ColorGrid;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class BrickGrid implements ColorGrid {
	//TODO: Make private
	public List<List<BrickUnit>> brickUnits; // outside array is rows, each row contains an array of columns
	
	public BrickGrid(int width, int height) {
		brickUnits = new ArrayList<List<BrickUnit>>(height);
		for (int i=0; i < height; i++) {
			List<BrickUnit> array = new ArrayList<BrickUnit>();
			for (int j=0; j < width; j++) {
				array.add(null);
			}
			brickUnits.add(array);
		}		
	}
	
	public Brick getBrick(int column, int row) {
		return brickUnits.get(row).get(column).getBrick();
	}
	
	/**
	 * Inserts a brick starting at the provided position
	 * @param brick
	 * @param column
	 * @param row
	 */
	public void setBrick(Brick brick, int column, int row) {
		// TODO: Validate row and column are < height & width & that the brick width doesn't exceed the grid	
		BrickUnit brickUnit = new BrickUnit(brick);
		for (int i = column; i < column + brick.getWidth(); i++) {
			brickUnits.get(row).set(i, brickUnit);
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
