package io.chadheise.lego.color.merger;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModeColorMerger implements ColorMerger {

    @Override
    public Color apply(Collection<Color> colors) {
        Map<Color, Integer> frequencies = new HashMap<>();

        for(Color color : colors) {
            int frequency = frequencies.getOrDefault(color, 0);
            frequencies.put(color, frequency + 1);
        }

        Color mostFrequentColor = null;
        int maxFrequency = 0;
        for (Map.Entry<Color,Integer> entry : frequencies.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostFrequentColor = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }

        // TODO: Handle ties

        if (mostFrequentColor == null) {
            return new Color(0,0,0);
        }

        return mostFrequentColor;
    }
}
