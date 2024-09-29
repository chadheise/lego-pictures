package io.chadheise.lego.app;

import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = "--input", description = "The path of the input file (jpg) to convert")
    private String inputFile;

    @Parameter(names = "--output", description = "The name and location to put the output file (png)")
    private String outputFile;

    @Parameter(names = "--colors", description = "The path of a CSV file containing RGB color values to use in the output")
    private String colorsFile;

    @Parameter(names = "--width", description = "The width of the output where 1 unit corresponds to a 1x1 lego brick")
    private int width;

    @Parameter(names = "--preColorTransform", description = "Whether or not to transform the colors before generating bricks. Enabling can sometimes give better results but is slower.")
    private boolean preColorTransform = false;

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getColorsFile() {
        return colorsFile;
    }

    public int getWidth() {
        return width;
    }

    public boolean getPreColorTransform() {
        return preColorTransform;
    }

}
