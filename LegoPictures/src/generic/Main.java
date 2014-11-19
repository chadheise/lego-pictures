package generic;

import generic.color.comparator.ColorComparator;
import generic.color.comparator.ExplodingEuclideanColorComparator2;
import generic.color.comparator.RGBColorComparator;
import generic.color.grid.ColorGrid;
import generic.color.grid.ColorGridPrinter;
import generic.color.grid.ImageFileColorGrid;
import generic.color.palette.ColorPalette;
import generic.color.palette.ComparatorColorPalette;
import generic.color.palette.GreyScaleColorPalette;
import generic.transform.ColorGridTransform;
import generic.transform.ColorTransform;
import generic.transform.CompositeTransform;
import generic.transform.LegoRectangleColorGridTransform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.Color;

import lego.Brick;
import lego.BrickGrid;
import lego.BrickGridFactory;
import lego.BrickGridPrinter;
import lego.BrickUnit;
import lego.LegoColorPalette;
import lego.SimpleLegoColorPalette;

public class Main {

	public static void main(String[] args) throws IOException {
		String picName = "GoldenDome";
		String imageFileName = "/Users/chadheise/Documents/programming/lego/pics/"
				+ picName + ".jpg";
		String outputFileName = "/Users/chadheise/Documents/programming/lego/pics/"
				+ picName + "_lego_brick2.png";
		String imageFormat = "PNG"; // Use PNG not JPEG to avoid compression
									// artifacts

		ArrayList<ColorGridTransform> transforms = new ArrayList<ColorGridTransform>();
		transforms.add(new LegoRectangleColorGridTransform(60));

		// transforms.add(new ColorTransform(new GreyScaleColorPalette()));

		// Exploding
		// .2, .4, .2
		// .3, .6, .05
		// Exploding2
		// .3, .6, .5, 20
		// .5, .7, .8, 20 NotreDame with LegoColorPalette
		// .4, .3, .8. 20 spaceNeedle
		ColorComparator comparator = new ExplodingEuclideanColorComparator2(.5,
				.7, .8, 20);
		ColorPalette palette = new LegoColorPalette(comparator);
		transforms.add(new ColorTransform(palette));

		ColorGrid colorGrid = new ImageFileColorGrid(imageFileName);
		colorGrid = new CompositeTransform(transforms).transform(colorGrid);

		ColorGridPrinter.print(colorGrid, 5, 6, outputFileName, imageFormat);
		getStats(colorGrid);

		BrickGrid brickGrid = BrickGridFactory.generateBrickGrid(colorGrid);

		// Simple brick splitter
		for (int y = 0; y < brickGrid.getHeight(); y++) {
			for (int x = 0; x < brickGrid.getWidth(); x++) {
				int offSet = 0;
				Brick brick = brickGrid.getBrick(x, y);
				while (offSet + 4 < brick.getWidth()) {
					Brick newBrick = new Brick(brick.getColor(), 4);
					brickGrid.setBrick(newBrick, x + offSet, y);
					offSet += 4;
				}
				int remainder = brick.getWidth() - offSet - 4;
				if (remainder > 0) {
					Brick newBrick = new Brick(brick.getColor(),
							remainder);
					brickGrid.setBrick(newBrick, x + brick.getWidth() - remainder, y);
				}

				x += brick.getWidth() - 1; // -1 since the for loop will
											// increment 1
			}
		}
		
		
		// Best place to put a seam is 2 away from a seam above or below (for bricks of length 4 or less)
		
		// Do this 1 row at a time:
		// Put a line everywhere you can
		// Combine unneccessary splits

		BrickGridPrinter.print(brickGrid, outputFileName, imageFormat);

	}

	private static void getStats(ColorGrid canvas) {
		Map<Color, Integer> map = new HashMap<Color, Integer>();
		for (int x = 0; x < canvas.getWidth(); x++) {
			for (int y = 0; y < canvas.getHeight(); y++) {
				Color color = canvas.getColor(x, y);
				if (!map.containsKey(color)) {
					map.put(color, 0);
				}
				map.put(color, map.get(color) + 1);
			}
		}

		for (Entry<Color, Integer> entry : map.entrySet()) {
			System.out.println(entry.getValue() + ": " + entry.getKey());
		}

	}

}
