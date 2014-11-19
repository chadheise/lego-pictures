package lego;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BrickGridPrinter {

	private static final int WIDTH_MULTIPLE = 5;
	private static final int HEIGHT_MULTIPLE = 6;
	private static final Color LINE_COLOR = new Color(0, 0, 0); // Black

	public static void print(BrickGrid brickGrid, String fileName, String imageFormat)
			throws IOException {
		// Print as plain grid
		// TODO: Refactor to remove duplicate code
		// ColorGridPrinter.print(brickGrid, WIDTH_MULTIPLE, HEIGHT_MULTIPLE,
		// fileName, imageFormat);

		int imageWidth = WIDTH_MULTIPLE * brickGrid.getWidth();
		int imageHeight = HEIGHT_MULTIPLE * brickGrid.getHeight();
		int imageType = BufferedImage.TYPE_INT_RGB;

		BufferedImage image = new BufferedImage(imageWidth, imageHeight,
				imageType);
		for (int y = 0; y < brickGrid.getHeight(); y++) {
			for (int x = 0; x < brickGrid.getWidth(); x++) {
				// Iterate pixels in grid
				for (int h = y * HEIGHT_MULTIPLE; h < (y + 1) * HEIGHT_MULTIPLE; h++) {

					Brick previousBrick = null;
					if (x != 0) {
						previousBrick = brickGrid.getBrick(x-1, y);
					} 
					for (int w = x * WIDTH_MULTIPLE; w < (x + 1)
							* WIDTH_MULTIPLE; w++) {
						// Paint colored rectangle
						image.setRGB(w, h, brickGrid.getColor(x, y).getRGB());
						
						// Picture is drawn top left to bottom right
						if (h == (y + 1) * HEIGHT_MULTIPLE - 1) { // If we're on the last pixel of the row
							// Paint lines over edges of colored rectangle
							image.setRGB(w, h, LINE_COLOR.getRGB());
						}
						
						if (previousBrick == null || !brickGrid.getBrick(x, y).equals(previousBrick)) { // We've encounter the next brick
							if (w == x * WIDTH_MULTIPLE) { // If we're on the first pixel of the brick
								// Paint lines over edges of colored rectanlge
								image.setRGB(w, h, LINE_COLOR.getRGB());
							}

						}
					}
				}

			}
		}

		File f = new File(fileName);
		ImageIO.write(image, imageFormat, f);

		// Add lines between bricks

	}

}
