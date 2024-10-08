package io.chadheise.lego.app;

import io.chadheise.lego.brick.Brick;
import io.chadheise.lego.brick.grid.BrickGrid;
import io.chadheise.lego.color.measure.ColorMeasure;
import io.chadheise.lego.color.merger.ColorMerger;
import io.chadheise.lego.color.palette.ColorPalette;
import io.chadheise.lego.color.palette.NamedColorPalette;
import io.chadheise.lego.color.string.ColorStringHex;
import io.chadheise.lego.color.string.ColorStringRGB;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MetadataWriter {

    private final Args args;
    private final boolean preColorTransformEnabled;
    private final ColorPalette colorPalette;
    private final ColorMeasure colorMeasure;
    private final ColorMerger colorMerger;
    private final Function<BrickGrid, BrickGrid> brickSplitter;
    private final BrickGrid brickGrid;

    public MetadataWriter(
            final Args args,
            final boolean preColorTransformEnabled,
            final ColorPalette colorPalette,
            final ColorMeasure colorMeasure,
            final ColorMerger colorMerger,
            final Function<BrickGrid, BrickGrid> brickSplitter,
            final BrickGrid brickGrid) {
        this.args = args;
        this.preColorTransformEnabled = preColorTransformEnabled;
        this.colorPalette = colorPalette;
        this.colorMeasure = colorMeasure;
        this.colorMerger = colorMerger;
        this.brickSplitter = brickSplitter;
        this.brickGrid = brickGrid;
    }

    public void writeSystemOut() {
        try (OutputStreamWriter osw = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)) {
            writeAll(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(final String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writeAll(writer);
        } catch (IOException e) {
            System.err.println("An error occurred while writing metadata file: " + e.getMessage());
        }
    }

    private void writeAll(final BufferedWriter writer) throws IOException {
        writeArgs(writer);
        writeTransforms(writer);
        writeBrickCounts(writer);
    }

    private void writeArgs(final BufferedWriter writer) throws IOException {
        writer.write(String.format("input: %s\n", args.getInputFile()));
        writer.write(String.format("output: %s\n", args.getOutputFile()));
        writer.write(String.format("colors: %s\n", args.getColorsFile()));
        writer.write(String.format("width: %s\n", args.getWidth()));
        writer.newLine();
    }

    private void writeTransforms(final BufferedWriter writer) throws IOException {
        writer.write(String.format("preColorTransformEnabled: %b\n", preColorTransformEnabled));
        writer.write(String.format("colorMeasure: %s\n", colorMeasure.getClass()));
        writer.write(String.format("colorMerger: %s\n", colorMerger.getClass()));
        writer.write(String.format("brickSplitter: %s\n", brickSplitter.getClass()));
        writer.newLine();
    }

    private void writeBrickCounts(final BufferedWriter writer) throws IOException {
        int brickCount = 0;

        List<List<String>> brickLabels = new ArrayList<>();

        // colorLabel -> brick width -> number of bricks
        Map<String, Map<Integer, Integer>> map = new HashMap<>();
        for (int y = 0; y < brickGrid.getHeight(); y++) {
            List<String> brickLabelsInRow = new ArrayList<>();
            for (int x = 0; x < brickGrid.getWidth(); x++) {
                brickCount += 1;
                Brick brick = brickGrid.getBrick(x, y);
                Color color = brick.getColor();

                String hexLabel = new ColorStringHex().apply(color);
                String rgbLabel = new ColorStringRGB().apply(color);

                String brickLabel = hexLabel;
                String colorName = "";
                if (colorPalette instanceof NamedColorPalette) {
                    colorName += ((NamedColorPalette) colorPalette).getName(brick.getColor());
                    brickLabel = ((NamedColorPalette) colorPalette).getName(brick.getColor());
                }
                String colorLabel = String.format("%s %s %s", colorName, hexLabel, rgbLabel);

                map.putIfAbsent(colorLabel, new HashMap<>());
                Map<Integer, Integer> counts = map.get(colorLabel);
                int width = brick.getWidth();
                counts.putIfAbsent(width, 0);
                counts.put(width, counts.get(width) + 1);

                brickLabel += String.format(" [%d]", width);
                brickLabelsInRow.add(brickLabel);

                x += width - 1; // -1 since for loop will increment by 1
            }
            brickLabels.add(brickLabelsInRow);
        }

        writer.write("Brick Summary:\n");
        writer.write("Color [Brick Width] Count\n");
        for (Map.Entry<String, Map<Integer, Integer>> entry : map.entrySet()) {
            for (Map.Entry<Integer, Integer> widthCounts : entry.getValue().entrySet()) {
                writer.write(String.format("%s [%d] %d", entry.getKey(), widthCounts.getKey(), widthCounts.getValue()));
                writer.newLine();
            }
        }

        writer.write(String.format("Total number of bricks: %d\n", brickCount));
        writer.newLine();

        writer.write("Brick table: \n");
        for (List<String> label : brickLabels) {
            StringBuilder rowLabel = new StringBuilder("|");
            for (String brickLabel : label) {
                rowLabel.append(brickLabel);
                rowLabel.append("|");
            }
            writer.write(rowLabel.toString());
            writer.newLine();
        }
        writer.newLine();

        writer.write("Bricks by row: \n");
        // Iterate in reverse so that the output starts at the bottom row
        for (int row = brickLabels.size() - 1; row >= 0; row--) {
            writer.write(String.format("Row %d:\n",brickLabels.size() - row));
            for (String brickLabel : brickLabels.get(row)) {
                writer.write(brickLabel);
                writer.newLine();
            }
            writer.newLine();
        }
    }
}
