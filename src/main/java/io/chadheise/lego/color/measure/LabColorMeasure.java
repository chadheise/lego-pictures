package io.chadheise.lego.color.measure;

import io.chadheise.lego.color.ColorLab;
import io.chadheise.lego.color.RGBToXYZ;
import io.chadheise.lego.color.XYZToLab;

import java.awt.*;

/* Compares 2 colors by first converting them to the L*a*b* color space, them measuring their distance */
public class LabColorMeasure implements ColorMeasure {

    @Override
    public double getDistance(Color color1, Color color2) {
        ColorLab labColor1 = new RGBToXYZ().andThen(new XYZToLab()).apply(color1);
        ColorLab labColor2 = new RGBToXYZ().andThen(new XYZToLab()).apply(color2);

        double diffL = labColor1.getL() - labColor2.getL();
        double diffA = labColor1.getA() - labColor2.getA();
        double diffB = labColor1.getB() - labColor2.getB();
        return Math.sqrt(diffL * diffL + diffA * diffA + diffB * diffB);
    }
}
