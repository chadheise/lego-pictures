package io.chadheise.lego.app;

import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = "--input", description = "The path of the input file (jpg) to convert")
    private String inputFile;

    @Parameter(names = "--output", description = "The name and location to put the output file (png)")
    private String outputFile;

    @Parameter(names = "--colors", description = "The path of a CSV file containing RGB color values to use in the output")
    private String colorsFile;

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getColorsFile() {
        return colorsFile;
    }

}
