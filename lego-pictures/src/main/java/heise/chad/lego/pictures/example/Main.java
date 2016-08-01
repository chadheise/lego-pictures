package heise.chad.lego.pictures.example;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import heise.chad.lego.brick.grid.BrickGrid;
import heise.chad.lego.brick.grid.BrickGridFactory;
import heise.chad.lego.brick.grid.BrickGridPrinter;
import heise.chad.lego.brick.grid.BrickGridSplitter1;
import heise.chad.lego.color.grid.ColorColorGridTransform;
import heise.chad.lego.color.grid.ColorGrid;
import heise.chad.lego.color.grid.ColorGridPrinter;
import heise.chad.lego.color.grid.ImageFileColorGrid;
import heise.chad.lego.color.grid.LegoRectangleColorGridTransform;
import heise.chad.lego.color.measure.ColorMeasure;
import heise.chad.lego.color.measure.EuclideanColorMeasure;
import heise.chad.lego.color.palette.ColorGridToPalette;
import heise.chad.lego.color.palette.ColorPalette;
import heise.chad.lego.color.palette.LegoColorPalette;
import heise.chad.lego.color.palette.MedianCutColorPaletteTransform;
import heise.chad.lego.color.transform.ColorPaletteColorTransform;

public class Main {

    public static void main(String[] args) throws IOException {
        String picName = "GoldenDome";
        String baseDir = "/Users/chadheise/Documents/programming/lego/pics/";
        String imageFileName = baseDir + picName + ".jpg";
        String outputFileName = baseDir + picName + "_lego_brick8.png";
        String imageFormat = "PNG"; // Use PNG not JPEG to avoid compression
                                    // artifacts

        // Exploding
        // .2, .4, .2
        // .3, .6, .05
        // Exploding2
        // .3, .6, .5, 20
        // .5, .7, .8, 20 NotreDame with LegoColorPalette
        // .4, .3, .8. 20 spaceNeedle
        // ColorMeasure colorMeasure = new ExplodingEuclideanColorMeasure2(.5,
        // .7, .8, 20);
        ColorMeasure colorMeasure = new EuclideanColorMeasure();
        ColorPalette palette = new LegoColorPalette();
        // Transform<Color> colorTransform = new ColorPaletteColorTransform(
        // palette, comparator);

        ColorGrid colorGrid = new ImageFileColorGrid(imageFileName);

        // Quantize image colors
        Function<ColorPalette, ColorPalette> colorPaletteTransform = new MedianCutColorPaletteTransform(5);
        ColorPalette originalPalette = ColorGridToPalette.getColorPalette(colorGrid);
        ColorPalette newPalette = colorPaletteTransform.apply(originalPalette);
        Function<Color, Color> colorTransform = new ColorPaletteColorTransform(
                newPalette, colorMeasure);
        ColorGrid colorSpaceGrid = ColorGridToPalette.getColorGrid(newPalette);
        ColorGridPrinter.print(colorSpaceGrid, 100, 10, baseDir + "colorSpace.png", imageFormat);

        // Build main transform
        Function<ColorGrid, ColorGrid> mainTransform = new LegoRectangleColorGridTransform(60)
                .andThen(new ColorColorGridTransform(colorTransform));

        colorGrid = mainTransform.apply(colorGrid);

        ColorGridPrinter.print(colorGrid, 5, 6, outputFileName, imageFormat);
        getStats(colorGrid);

        BrickGrid brickGrid = BrickGridFactory.generateBrickGrid(colorGrid);
        Function<BrickGrid, BrickGrid> splitter = new BrickGridSplitter1();
        brickGrid = splitter.apply(brickGrid);
        // BrickGridTransform merger = new BrickGridMerger(3);
        // brickGrid = merger.transform(brickGrid);

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
