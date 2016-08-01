package heise.chad.lego.pictures.example;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import heise.chad.lego.brick.grid.BrickGrid;
import heise.chad.lego.brick.grid.BrickGridPrinter;
import heise.chad.lego.brick.grid.BrickGridSplitter1;
import heise.chad.lego.brick.grid.BrickGridTransform;
import heise.chad.lego.color.grid.ColorColorGridTransform;
import heise.chad.lego.color.grid.ColorGrid;
import heise.chad.lego.color.grid.ImageFileColorGrid;
import heise.chad.lego.color.grid.LegoRectangleColorGridTransform;
import heise.chad.lego.color.measure.ColorMeasure;
import heise.chad.lego.color.measure.ExplodingEuclideanColorMeasure2;
import heise.chad.lego.color.palette.ColorPalette;
import heise.chad.lego.color.palette.LegoColorPalette;
import heise.chad.lego.color.transform.ColorPaletteColorTransform;

public class Main {

    public static void main(String[] args) throws IOException {
        String picName = "GoldenDome";
        String baseDir = "/Users/chadheise/Documents/programming/lego/pics/";
        String imageFileName = baseDir + picName + ".jpg";
        String outputFileName = baseDir + picName + "_lego_brick.png";
        String imageFormat = "PNG"; // Use PNG not JPEG to avoid compression
                                    // artifacts

        ColorPalette palette = new LegoColorPalette();
        ColorMeasure colorMeasure = new ExplodingEuclideanColorMeasure2(.5, .7, .8, 20);
        Function<Color, Color> colorTransform = new ColorPaletteColorTransform(palette, colorMeasure);

        Function<ColorGrid, BrickGrid> fxn = new LegoRectangleColorGridTransform(60)
                .andThen(new ColorColorGridTransform(colorTransform))
                .andThen(new BrickGridTransform())
                .andThen(new BrickGridSplitter1());

        ColorGrid colorGrid = new ImageFileColorGrid(imageFileName);

        BrickGrid brickGrid = fxn.apply(colorGrid);
        BrickGridPrinter.print(brickGrid, outputFileName, imageFormat);
        getStats(brickGrid);
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
