package io.chadheise.lego.app;

import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = "--input", description = "The path of the input file (jpg) to convert")
    private String input;

    @Parameter(names = "--output", description = "The name and location to put the output file (png)")
    private String output;

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

}
