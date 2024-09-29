package io.chadheise.lego.app;

import io.chadheise.lego.brick.Brick;
import io.chadheise.lego.brick.grid.BrickGrid;
import io.chadheise.lego.color.palette.ColorPalette;
import io.chadheise.lego.color.palette.NamedColorPalette;
import io.chadheise.lego.color.string.ColorStringHex;
import io.chadheise.lego.color.string.ColorStringRGB;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MetadataWriter {

    private final BrickGrid brickGrid;
    private final ColorPalette colorPalette;

    public MetadataWriter(final BrickGrid brickGrid, final ColorPalette colorPalette) {
        this.brickGrid = brickGrid;
        this.colorPalette = colorPalette;
    }

    public void writeSystemOut() {
        try (OutputStreamWriter osw = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)) {
            writeBrickCounts(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(final String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writeBrickCounts(writer);
        } catch (IOException e) {
            System.err.println("An error occurred while writing metadata file: " + e.getMessage());
        }
    }

    private void writeBrickCounts(final BufferedWriter writer) throws IOException {
        int brickCount = 0;

        // colorLabel -> brick width -> number of bricks
        Map<String, Map<Integer, Integer>> map = new HashMap<>();
        for (int x = 0; x < brickGrid.getWidth(); x++) {
            for (int y = 0; y < brickGrid.getHeight(); y++) {
                brickCount += 1;
                Brick brick = brickGrid.getBrick(x, y);
                Color color = brick.getColor();

                String colorName = "";
                if (colorPalette instanceof NamedColorPalette) {
                    colorName += ((NamedColorPalette) colorPalette).getName(brick.getColor());
                }
                String hexLabel = new ColorStringHex().apply(color);
                String rgbLabel = new ColorStringRGB().apply(color);
                String colorLabel = String.format("%s %s %s", colorName, hexLabel, rgbLabel);

                map.putIfAbsent(colorLabel, new HashMap<>());
                Map<Integer, Integer> counts = map.get(colorLabel);
                int width = brick.getWidth();
                counts.putIfAbsent(width, 0);
                counts.put(width, counts.get(width) + 1);

                y += width - 1; // -1 since for loop will increment by 1
            }
        }

        writer.write("Color [Brick Width] Count");
        writer.newLine();
        for (Map.Entry<String, Map<Integer, Integer>> entry : map.entrySet()) {
            for (Map.Entry<Integer, Integer> widthCounts : entry.getValue().entrySet()) {
                writer.write(String.format("%s [%d] %d", entry.getKey(), widthCounts.getKey(), widthCounts.getValue()));
                writer.newLine();
            }
        }

        writer.write("Total number of bricks: " + brickCount);
        writer.newLine();
    }
}
