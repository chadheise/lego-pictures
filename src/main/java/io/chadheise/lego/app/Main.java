package io.chadheise.lego.app;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import javax.imageio.ImageIO;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Injector;

import io.chadheise.lego.brick.grid.*;
import io.chadheise.lego.color.grid.BufferedImageColorGridTransform;
import io.chadheise.lego.color.grid.ColorColorGridTransform;
import io.chadheise.lego.color.grid.ColorGrid;
import io.chadheise.lego.color.grid.LegoRectangleColorGridTransform;
import io.chadheise.lego.color.measure.*;
import io.chadheise.lego.color.palette.ColorPalette;
import io.chadheise.lego.color.palette.LegoColorPalette;
import io.chadheise.lego.color.transform.ColorPaletteColorTransform;
import io.chadheise.lego.guice.LegoModule;

public class Main {

    public static void main(final String[] argv) throws IOException {
        // Use PNG not JPEG to avoid compression artifacts
        String imageFormat = "PNG";

        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .args(argv)
                .build();

        Injector injector = Guice.createInjector(new LegoModule(args.getColorsFile()));
        ColorPalette palette = injector.getInstance(ColorPalette.class);

        Function<BufferedImage, ColorGrid> rectangle_fxn = new BufferedImageColorGridTransform()
                .andThen(new LegoRectangleColorGridTransform(args.getWidth()));

        BufferedImage inputImage = ImageIO.read(new File(args.getInputFile()));
        ColorGrid colorGrid = rectangle_fxn.apply(inputImage);

        // ColorMeasure colorMeasure = new ExplodingEuclideanColorMeasure2(.5, .7, .8, 20);
        // RecenteredColorMeasure uses the full palette to add detail at the expense of color accuracy
        // ColorMeasure euclideanColorMeasure = new EuclideanColorMeasure();
        // ColorMeasure colorMeasure = new RecenteredColorMeasure(palette, colorGrid, euclideanColorMeasure);
        ColorMeasure colorMeasure = new LabColorMeasure();
        Function<Color, Color> colorTransform = new ColorPaletteColorTransform(palette, colorMeasure);

        Function<ColorGrid, BrickGrid> fxn = new ColorColorGridTransform(colorTransform)
                .andThen(new BrickGridTransform())
                .andThen(new BrickGridSplitter2());

        // Apply grid without converting to lego colors
        // Function<ColorGrid, BrickGrid> fxn2 = new BrickGridTransform()
        //    .andThen(new BrickGridSplitter1());
        // BrickGrid brickGrid = fxn2.apply(colorGrid);

        BrickGrid brickGrid = fxn.apply(colorGrid);

        BufferedImage outputImage = new BufferedImageBrickGridTransform().apply(brickGrid);
        ImageIO.write(outputImage, imageFormat, new File(args.getOutputFile()));

        getStats(brickGrid);
    }

    private static void getStats(ColorGrid canvas) {
        Map<Color, Integer> map = new HashMap<>();
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
