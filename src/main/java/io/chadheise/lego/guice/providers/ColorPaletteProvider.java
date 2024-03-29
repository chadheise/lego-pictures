package io.chadheise.lego.guice.providers;

import java.awt.Color;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import com.google.inject.Provider;

import io.chadheise.lego.color.palette.ColorPalette;
import io.chadheise.lego.color.palette.FixedColorPalette;

@SuppressWarnings("SpellCheckingInspection")
public class ColorPaletteProvider implements Provider<ColorPalette> {

    private static final String FILE_PATH = "/colorPalettes/lego.csv";

    @Override
    public ColorPalette get() {
        FixedColorPalette.Builder bldr = new FixedColorPalette.Builder();

        try (InputStream input = getClass().getResourceAsStream(FILE_PATH)) {
            assert input != null;

            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            br.readLine(); // Ignore first line of headers
            String line = br.readLine(); // Get the first line of RGB values

            while (line != null) {
                String[] rgbValues = line.split(",");
                int r = Integer.parseInt(rgbValues[0]);
                int g = Integer.parseInt(rgbValues[1]);
                int b = Integer.parseInt(rgbValues[2]);
                bldr.withColor(new Color(r, g, b));
                line = br.readLine();
            }
            return bldr.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
