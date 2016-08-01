package heise.chad.lego.generic.color.grid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ColorGridPrinter {

	public static void print(ColorGrid colorGrid, int widthMultiple,
			int heightMultiple, String fileName, String imageFormat)
			throws IOException {
		int imageWidth = widthMultiple * colorGrid.getWidth();
		int imageHeight = heightMultiple * colorGrid.getHeight();
		int imageType = BufferedImage.TYPE_INT_RGB;

		BufferedImage image = new BufferedImage(imageWidth, imageHeight,
				imageType);

		for (int x = 0; x < colorGrid.getWidth(); x++) {
			for (int y = 0; y < colorGrid.getHeight(); y++) {
				// Iterate pixels in grid
				for (int w = x * widthMultiple; w < (x + 1) * widthMultiple; w++) {
					for (int h = y * heightMultiple; h < (y + 1)
							* heightMultiple; h++) {
						image.setRGB(w, h, colorGrid.getColor(x, y).getRGB());
					}
				}

			}
		}

		File f = new File(fileName);
		ImageIO.write(image, imageFormat, f);
	}

}
