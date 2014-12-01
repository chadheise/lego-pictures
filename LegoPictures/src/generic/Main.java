package generic;

import generic.color.comparator.ColorComparator;
import generic.color.comparator.ExplodingEuclideanColorComparator2;
import generic.color.grid.ColorGrid;
import generic.color.grid.ColorGridPrinter;
import generic.color.grid.ImageFileColorGrid;
import generic.color.palette.ColorPalette;
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

import lego.BrickGrid;
import lego.BrickGridFactory;
import lego.BrickGridPrinter;
import lego.LegoColorPalette;
import lego.transform.BrickGridTransform;
import lego.transform.BrickGridSplitter1;

public class Main {

	public static void main(String[] args) throws IOException {
		String picName = "GoldenDome";
		String imageFileName = "/Users/chadheise/Documents/programming/lego/pics/"
				+ picName + ".jpg";
		String outputFileName = "/Users/chadheise/Documents/programming/lego/pics/"
				+ picName + "_lego_brick5.png";
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
		BrickGridTransform splitter = new BrickGridSplitter1();
		brickGrid = splitter.transform(brickGrid);
//		BrickGridTransform merger = new BrickGridMerger(3);
//		brickGrid = merger.transform(brickGrid);

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
