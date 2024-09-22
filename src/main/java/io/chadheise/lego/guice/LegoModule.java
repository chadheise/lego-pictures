package io.chadheise.lego.guice;

import com.google.inject.AbstractModule;

import io.chadheise.lego.color.palette.ColorPalette;
import io.chadheise.lego.guice.providers.ColorPaletteProvider;

public class LegoModule extends AbstractModule {

    private final String colorPaletteFilePath;

    public LegoModule(String colorPaletteFilePath) {
        this.colorPaletteFilePath = colorPaletteFilePath;
    }

    @Override
    protected void configure() {

        bind(ColorPalette.class).toProvider(new ColorPaletteProvider(this.colorPaletteFilePath));

    }

}
