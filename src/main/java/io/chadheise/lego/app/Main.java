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

import io.chadheise.lego.brick.grid.BrickGrid;
import io.chadheise.lego.brick.grid.BrickGridSplitter1;
import io.chadheise.lego.brick.grid.BrickGridTransform;
import io.chadheise.lego.brick.grid.BufferedImageBrickGridTransform;
import io.chadheise.lego.color.grid.BufferedImageColorGridTransform;
import io.chadheise.lego.color.grid.ColorColorGridTransform;
import io.chadheise.lego.color.grid.ColorGrid;
import io.chadheise.lego.color.grid.LegoRectangleColorGridTransform;
import io.chadheise.lego.color.measure.ColorMeasure;
import io.chadheise.lego.color.measure.ExplodingEuclideanColorMeasure2;
import io.chadheise.lego.color.palette.ColorPalette;
import io.chadheise.lego.color.palette.LegoColorPalette;
import io.chadheise.lego.color.transform.ColorPaletteColorTransform;
import io.chadheise.lego.guice.LegoModule;

public class Main {

    public static void main(final String[] argv) throws IOException {
        String imageFormat = "PNG"; // Use PNG not JPEG to avoid compression
                                    // artifacts

        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .args(argv)
                .build();

        Injector injector = Guice.createInjector(new LegoModule());
        ColorPalette p = injector.getInstance(ColorPalette.class);

        ColorPalette palette = new LegoColorPalette();
        ColorMeasure colorMeasure = new ExplodingEuclideanColorMeasure2(.5, .7, .8, 20);
        Function<Color, Color> colorTransform = new ColorPaletteColorTransform(palette, colorMeasure);

        Function<BufferedImage, BrickGrid> fxn = new BufferedImageColorGridTransform()
                .andThen(new LegoRectangleColorGridTransform(60))
                .andThen(new ColorColorGridTransform(colorTransform))
                .andThen(new BrickGridTransform())
                .andThen(new BrickGridSplitter1());

        BufferedImage inputImage = ImageIO.read(new File(args.getInputFile()));
        BrickGrid brickGrid = fxn.apply(inputImage);
        BufferedImage outputImage = new BufferedImageBrickGridTransform().apply(brickGrid);
        ImageIO.write(outputImage, imageFormat, new File(args.getOutputFile()));

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
