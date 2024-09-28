package io.chadheise.lego.guice.providers;

import java.awt.Color;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import com.google.inject.Key;
import com.google.inject.Provider;

import io.chadheise.lego.color.palette.ColorPalette;
import io.chadheise.lego.color.palette.FixedColorPalette;
import io.chadheise.lego.color.palette.NamedColorPalette;

@SuppressWarnings("SpellCheckingInspection")
public class ColorPaletteProvider implements Provider<ColorPalette> {

    /* File path to a CSV file representing color values
     * Acceptable columns: name, hexColor, hex, r, g, b, red, green, blue */
    private final String filePath;

    public ColorPaletteProvider(final String filePath) {
        this.filePath = filePath;
    }

    @Override
    public ColorPalette get() {
        FixedColorPalette.Builder bldr = new FixedColorPalette.Builder();
        NamedColorPalette.Builder namedBldr = new NamedColorPalette.Builder();

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

                if (headerIndices.containsKey("name")) {
                    String name = readValue(headerIndices, values, "name");
                    namedBldr.withColor(name, color);
                } else {
                    bldr.withColor(color);
                }

                line = br.readLine();
            }
            return bldr.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readValue(final Map<String, Integer> headerIndices, final String[] line, final String key) {
        Integer index = null;
        if (headerIndices.containsKey(key.toLowerCase())) {
            index = headerIndices.get(key.toLowerCase());
            return line[index];
        }
        return null;
    }

    private static String readPrioritizedValue(final Map<String, Integer> headerIndices, final String[] line, List<String> keys) {
        for(String key : keys) {
            String value = readValue(headerIndices, line, key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    private static String readHexColor(final Map<String, Integer> headerIndices, final String[] line) {
        final List<String> keys = Arrays.asList("hexColor", "hex");
        return readPrioritizedValue(headerIndices, line, keys);
    }

    private static Integer readRed(final Map<String, Integer> headerIndices, final String[] line) {
        final List<String> keys = Arrays.asList("red", "r");
        final String value = readPrioritizedValue(headerIndices, line, keys);
        return value != null ? Integer.parseInt(value) : null;
    }

    private static Integer readGreen(final Map<String, Integer> headerIndices, final String[] line) {
        final List<String> keys = Arrays.asList("green", "g");
        final String value = readPrioritizedValue(headerIndices, line, keys);
        return value != null ? Integer.parseInt(value) : null;
    }

    private static Integer readBlue(final Map<String, Integer> headerIndices, final String[] line) {
        final List<String> keys = Arrays.asList("blue", "b");
        final String value = readPrioritizedValue(headerIndices, line, keys);
        return value != null ? Integer.parseInt(value) : null;
    }
}
