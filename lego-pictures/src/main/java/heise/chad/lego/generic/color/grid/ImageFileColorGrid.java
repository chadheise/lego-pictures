package heise.chad.lego.generic.color.grid;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageFileColorGrid implements ColorGrid {

	private String imageFileName;
	private BufferedImage image;
	
	public ImageFileColorGrid(final String imageFileName) throws IOException {
		this.imageFileName = imageFileName;
		File file = new File(imageFileName);
		image = ImageIO.read(file);
	}
	
	@Override
	public int getHeight() {
		return image.getHeight();
	}

	@Override
	public int getWidth() {
		return image.getWidth();
	}

	@Override
	public Color getColor(int x, int y) {
		int rgb = image.getRGB(x, y);
		return new Color(rgb);
	}
	
	public String getFileName() {
		return imageFileName;
	}
	
}
