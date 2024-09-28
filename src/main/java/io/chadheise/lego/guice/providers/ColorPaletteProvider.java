package io.chadheise.lego.guice.providers;

import java.awt.Color;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import com.google.inject.Key;
import com.google.inject.Provider;

import io.chadheise.lego.color.palette.ColorPalette;
import io.chadheise.lego.color.palette.FixedColorPalette;

@SuppressWarnings("SpellCheckingInspection")
public class ColorPaletteProvider implements Provider<ColorPalette> {

    /* File path to a CSV file representing color values
    * Acceptable columns: name, hexColor, hex, r, g, b, red, green, blue */
    private final String filePath;

    public ColorPaletteProvider(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public ColorPalette get() {
        FixedColorPalette.Builder bldr = new FixedColorPalette.Builder();

        try (InputStream input = new FileInputStream(this.filePath)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine(); // Ignore first line of headers

            Map<String, Integer> headerIndices = new HashMap<>();
            String[] headers = line.split(",");
            for (int i = 0; i < headers.length; i++) {
                headerIndices.put(headers[i].toLowerCase(), i);
            }

            line = br.readLine(); // Get the first line of RGB values

            while (line != null) {
                String[] values = line.split(",");

                Color color = null;

                String hexColor = readHexColor(headerIndices, values);
                if (hexColor != null) {
                    color = Color.decode(hexColor);
                } else {
                    Integer r = readRed(headerIndices, values);
                    Integer g = readGreen(headerIndices, values);
                    Integer b = readBlue(headerIndices, values);
                    if (r == null || g == null || b == null) {
                        throw new RuntimeException("Unable to parse colors from file. Valid columns are: name, hexColor, hex, r, g, b, red, green, blue");
                    }
                    color = new Color(r, g, b);
                }

                bldr.withColor(color);
                line = br.readLine();
            }
            return bldr.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readHexColor(Map<String, Integer> headerIndices, String[] line) {
        Integer index = null;
        if (headerIndices.containsKey("hexcolor")) {
            index = headerIndices.get("hexcolor");
        } else if (headerIndices.containsKey("hex")) {
            index = headerIndices.get("hex");
        }

        if (index == null) {
            return null;
        }

        return line[index];
    }

    private static Integer readRed(Map<String, Integer> headerIndices, String[] line) {
        Integer index = null;
        if (headerIndices.containsKey("red")) {
            index = headerIndices.get("red");
        } else if (headerIndices.containsKey("r")) {
            index = headerIndices.get("r");
        }

        if (index == null) {
            return null;
        }

        return Integer.parseInt(line[index]);
    }

    private static Integer readGreen(Map<String, Integer> headerIndices, String[] line) {
        Integer index = null;
        if (headerIndices.containsKey("green")) {
            index = headerIndices.get("green");
        } else if (headerIndices.containsKey("g")) {
            index = headerIndices.get("g");
        }

        if (index == null) {
            return null;
        }

        return Integer.parseInt(line[index]);
    }

    private static Integer readBlue(Map<String, Integer> headerIndices, String[] line) {
        Integer index = null;
        if (headerIndices.containsKey("blue")) {
            index = headerIndices.get("blue");
        } else if (headerIndices.containsKey("b")) {
            index = headerIndices.get("b");
        }

        if (index == null) {
            return null;
        }

        return Integer.parseInt(line[index]);
    }
}
