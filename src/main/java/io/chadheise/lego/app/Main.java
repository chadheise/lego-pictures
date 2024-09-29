package io.chadheise.lego.app;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import javax.imageio.ImageIO;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Injector;

import io.chadheise.lego.brick.grid.*;
import io.chadheise.lego.color.grid.*;
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
        Function<ColorGrid, ColorGrid> colorGridTransform = new ColorColorGridTransform(colorTransform);

        // Read input image & convert to ColorGrid interface
        BufferedImage inputImage = ImageIO.read(new File(args.getInputFile()));
        ColorGrid colorGrid = new BufferedImageColorGridTransform().apply(inputImage);

        // Transform the input image to the color palettes colors
        boolean preColorTransformEnabled = true;
        if (preColorTransformEnabled) {
            colorGrid = colorGridTransform.apply(colorGrid);
        }

        // Output after converting the color palette but before merging into lego bricks
//        ColorGridPrinter.print(colorGrid, 1, 1, args.getOutputFile(), imageFormat);
//        System.exit(0);

        // Transform color grid based on rectangular lego brick shapes
        ColorMerger colorMerger = new SquaredAverageColorMerger();
        colorGrid = new LegoRectangleColorGridTransform(args.getWidth(), colorMerger)
                .apply(colorGrid);

        // Manipulate colors
        colorGrid = colorGridTransform.apply(colorGrid);

        // Convert to bricks by joining adjacent matching colors and subdividing into bricks
        Function<BrickGrid, BrickGrid> brickSplitter = new BrickGridSplitter2();
        BrickGrid brickGrid = new BrickGridTransform()
                .andThen(brickSplitter)
                .apply(colorGrid);

        // Generate output image
        BufferedImage outputImage = new BufferedImageBrickGridTransform().apply(brickGrid);
        ImageIO.write(outputImage, imageFormat, new File(args.getOutputFile()));

        // Generate metadata output
        String fileNameRoot = args.getOutputFile().split("\\.")[0];
        String outputTextFilePath = fileNameRoot + ".txt";

        MetadataWriter metadataWriter = new MetadataWriter(
                args,
                preColorTransformEnabled,
                palette,
                colorMeasure,
                colorMerger,
                brickSplitter,
                brickGrid);
        metadataWriter.writeFile(outputTextFilePath);
        metadataWriter.writeSystemOut();
    }

}
