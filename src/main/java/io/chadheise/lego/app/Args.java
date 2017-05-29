package io.chadheise.lego.app;

import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = "--input", description = "The path of the input file (jpg) to convert")
    private String inputFile;

    @Parameter(names = "--output", description = "The name and location to put the output file (png)")
    private String outputFile;

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

}
