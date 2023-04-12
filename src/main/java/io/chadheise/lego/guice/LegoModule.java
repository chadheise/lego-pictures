package io.chadheise.lego.guice;

import com.google.inject.AbstractModule;

import io.chadheise.lego.color.palette.ColorPalette;
import io.chadheise.lego.guice.providers.ColorPaletteProvider;

public class LegoModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(ColorPalette.class).toProvider(ColorPaletteProvider.class);

    }

}
