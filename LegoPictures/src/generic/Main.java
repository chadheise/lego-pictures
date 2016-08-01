package generic;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import generic.color.grid.ColorColorGridTransform;
import generic.color.grid.ColorGrid;
import generic.color.grid.ColorGridPrinter;
import generic.color.grid.ImageFileColorGrid;
import generic.color.grid.transform.LegoRectangleColorGridTransform;
import generic.color.measure.ColorMeasure;
import generic.color.measure.EuclideanColorMeasure;
import generic.color.palette.ColorGridToPalette;
import generic.color.palette.ColorPalette;
import generic.color.palette.MedianCutColorPaletteTransform;
import generic.color.transform.ColorPaletteColorTransform;
import generic.transform.CompositeTransform;
import generic.transform.Transform;
import lego.BrickGrid;
import lego.BrickGridFactory;
import lego.BrickGridPrinter;
import lego.color.palette.LegoColorPalette;
import lego.transform.BrickGridSplitter1;

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
        Transform<ColorPalette> colorPaletteTransform = new MedianCutColorPaletteTransform(5);
        ColorPalette originalPalette = ColorGridToPalette.getColorPalette(colorGrid);
        ColorPalette newPalette = colorPaletteTransform.transform(originalPalette);
        Transform<Color> colorTransform = new ColorPaletteColorTransform(
                newPalette, colorMeasure);
        ColorGrid colorSpaceGrid = ColorGridToPalette.getColorGrid(newPalette);
        ColorGridPrinter.print(colorSpaceGrid, 100, 10, baseDir + "colorSpace.png", imageFormat);

        // Build main transform
        Transform<ColorGrid> mainTransform = new CompositeTransform.Builder<ColorGrid>()
                .withTransform(new LegoRectangleColorGridTransform(60))
                .withTransform(new ColorColorGridTransform(colorTransform))
                .build();

        colorGrid = mainTransform.transform(colorGrid);

        ColorGridPrinter.print(colorGrid, 5, 6, outputFileName, imageFormat);
        getStats(colorGrid);

        BrickGrid brickGrid = BrickGridFactory.generateBrickGrid(colorGrid);
        Transform<BrickGrid> splitter = new BrickGridSplitter1();
        brickGrid = splitter.transform(brickGrid);
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
