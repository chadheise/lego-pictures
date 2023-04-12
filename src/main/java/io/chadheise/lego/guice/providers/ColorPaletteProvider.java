package io.chadheise.lego.guice.providers;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.google.inject.Provider;

import io.chadheise.lego.color.palette.ColorPalette;
import io.chadheise.lego.color.palette.FixedColorPalette;

public class ColorPaletteProvider implements Provider<ColorPalette> {

    private static final String FILE_PATH = "resources/colorPalettes/lego.csv";

    @Override
    public ColorPalette get() {

        System.out.println("Inside color pallete provider");
        System.out.println(getClass());

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("colorPalettes/lego.csv").getFile());
        System.out.println("File: " + file);

        try {
            System.out.println(getClass().getResource("/resources/colorPalettes/lego.csv").toURI());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        FixedColorPalette.Builder bldr = new FixedColorPalette.Builder();
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            stream.forEach(l -> {
                System.out.println(l);
                String[] rgbVals = l.split(",");
                int r = Integer.valueOf(rgbVals[0]);
                int g = Integer.valueOf(rgbVals[1]);
                int b = Integer.valueOf(rgbVals[2]);
                bldr.withColor(new Color(r, g, b));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
