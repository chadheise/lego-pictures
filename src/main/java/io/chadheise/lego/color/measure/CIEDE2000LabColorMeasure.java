package io.chadheise.lego.color.measure;

import io.chadheise.lego.color.ColorLab;
import io.chadheise.lego.color.RGBToXYZ;
import io.chadheise.lego.color.XYZToLab;

import java.awt.*;

public class CIEDE2000LabColorMeasure implements ColorMeasure{
    @Override
    public double getDistance(Color color1, Color color2) {
        ColorLab labColor1 = new RGBToXYZ().andThen(new XYZToLab()).apply(color1);
        ColorLab labColor2 = new RGBToXYZ().andThen(new XYZToLab()).apply(color2);

        double L1 = labColor1.getL();
        double a1 = labColor1.getA();
        double b1 = labColor1.getB();
        double L2 = labColor2.getL();
        double a2 = labColor2.getA();
        double b2 = labColor2.getB();

        double avgL = (L1 + L2) / 2.0;
        double C1 = Math.sqrt(a1 * a1 + b1 * b1);
        double C2 = Math.sqrt(a2 * a2 + b2 * b2);
        double avgC = (C1 + C2) / 2.0;

        double G = 0.5 * (1 - Math.sqrt(Math.pow(avgC, 7) / (Math.pow(avgC, 7) + Math.pow(25.0, 7))));
        double a1Prime = (1 + G) * a1;
        double a2Prime = (1 + G) * a2;

        double C1Prime = Math.sqrt(a1Prime * a1Prime + b1 * b1);
        double C2Prime = Math.sqrt(a2Prime * a2Prime + b2 * b2);
        double avgCPrime = (C1Prime + C2Prime) / 2.0;

        double h1Prime = Math.atan2(b1, a1Prime);
        if (h1Prime < 0) h1Prime += 2 * Math.PI;
        double h2Prime = Math.atan2(b2, a2Prime);
        if (h2Prime < 0) h2Prime += 2 * Math.PI;

        double deltaLPrime = L2 - L1;
        double deltaCPrime = C2Prime - C1Prime;

        double deltahPrime;
        if (C1Prime * C2Prime == 0) {
            deltahPrime = 0;
        } else {
            deltahPrime = h2Prime - h1Prime;
            if (deltahPrime > Math.PI) deltahPrime -= 2 * Math.PI;
            if (deltahPrime < -Math.PI) deltahPrime += 2 * Math.PI;
        }

        double deltaHPrime = 2.0 * Math.sqrt(C1Prime * C2Prime) * Math.sin(deltahPrime / 2.0);

        double avgLPrime = (L1 + L2) / 2.0;
        double avgCPrimeSquare = avgCPrime * avgCPrime;

        double avgHPrime;
        if (C1Prime * C2Prime == 0) {
            avgHPrime = h1Prime + h2Prime;
        } else {
            avgHPrime = (h1Prime + h2Prime) / 2.0;
            if (Math.abs(h1Prime - h2Prime) > Math.PI) {
                if (h1Prime + h2Prime < 2 * Math.PI) avgHPrime += Math.PI;
                else avgHPrime -= Math.PI;
            }
        }

        double T = 1 - 0.17 * Math.cos(avgHPrime - Math.toRadians(30))
                + 0.24 * Math.cos(2 * avgHPrime)
                + 0.32 * Math.cos(3 * avgHPrime + Math.toRadians(6))
                - 0.20 * Math.cos(4 * avgHPrime - Math.toRadians(63));

        double deltaTheta = Math.toRadians(30) * Math.exp(-Math.pow((avgHPrime - Math.toRadians(275)) / Math.toRadians(25), 2));
        double Rc = 2 * Math.sqrt(Math.pow(avgCPrimeSquare, 7) / (Math.pow(avgCPrimeSquare, 7) + Math.pow(25.0, 7)));

        double SL = 1 + ((0.015 * (avgLPrime - 50) * (avgLPrime - 50)) / Math.sqrt(20 + (avgLPrime - 50) * (avgLPrime - 50)));
        double SC = 1 + 0.045 * avgCPrime;
        double SH = 1 + 0.015 * avgCPrime * T;

        double RT = -Math.sin(2 * deltaTheta) * Rc;

        return Math.sqrt(Math.pow(deltaLPrime / SL, 2)
                + Math.pow(deltaCPrime / SC, 2)
                + Math.pow(deltaHPrime / SH, 2)
                + RT * (deltaCPrime / SC) * (deltaHPrime / SH));
    }
}
