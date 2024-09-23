package io.chadheise.lego.color.merger;

import io.chadheise.lego.color.grid.ColorGrid;

import java.awt.Color;
import java.util.Collection;
import java.util.function.Function;

/* Interface for combining multiple colors into a single color */
public interface ColorMerger extends Function<Collection<Color>, Color> {

}
