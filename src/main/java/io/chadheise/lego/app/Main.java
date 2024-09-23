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
import io.chadheise.lego.color.merger.*;
import io.chadheise.lego.color.palette.ColorPalette;
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

        // Initialize color manipulation objects
        ColorPalette palette = injector.getInstance(ColorPalette.class);
        ColorMeasure colorMeasure = new RedMeanColorMeasure();
        Function<Color, Color> colorTransform = new ColorPaletteColorTransform(palette, colorMeasure);

        // Read input image & convert to ColorGrid interface
        BufferedImage inputImage = ImageIO.read(new File(args.getInputFile()));
        ColorGrid colorGrid = new BufferedImageColorGridTransform().apply(inputImage);

        // Transform color grid based on rectangular lego brick shapes
        colorGrid = new LegoRectangleColorGridTransform(args.getWidth(), new AverageColorMerger())
                .apply(colorGrid);

        // Manipulate colors
        colorGrid = new ColorColorGridTransform(colorTransform).apply(colorGrid);

        // Convert to bricks by joining adjacent matching colors and subdividing into bricks
        BrickGrid brickGrid = new BrickGridTransform()
                .andThen(new BrickGridSplitter2())
                .apply(colorGrid);

        // Generate output image
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
