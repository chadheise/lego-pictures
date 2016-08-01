package generic.color.palette;

import generic.color.comparator.RGB;
import generic.transform.Transform;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MedianCutColorPaletteTransform implements Transform<ColorPalette> {

	private final int numSplits;

	/**
	 * Construct a MedianCutColorPaletteTransform using the provided number of
	 * splits. For n splits, there will be 2^n colors remaining after the
	 * transform.
	 * 
	 * @param numSplits
	 *            the number of subdivisions to perform
	 */
	public MedianCutColorPaletteTransform(int numSplits) {
		this.numSplits = numSplits;
	}

	@Override
	public ColorPalette transform(ColorPalette colorPalette) {
		// masterList contains all groupings of like colors
		ArrayList<List<Color>> masterList = new ArrayList<>();
		masterList.add(new ArrayList<Color>(colorPalette.getColors()));

		for (int i = 0; i < numSplits; i++) {
			Collection<List<Color>> temp = new ArrayList<>();
			for (List<Color> colorList : masterList) {
				temp.addAll(getSublists(colorList));
			}
			masterList.addAll(temp);
		}

		// Average the colors in each sublist to create the new color palette
		Set<Color> colors = new HashSet<>();
		for (List<Color> sublist : masterList) {
			colors.add(averageColors(sublist));
		}

		return new FixedColorPalette(colors);
	}

	/**
	 * Subdivide the colors in the provided collection into 2 lists
	 * 
	 * @param colors
	 *            a collection of colors to split
	 * @return 2 lists split by the median cut algorithm
	 */
	private Collection<List<Color>> getSublists(Collection<Color> colors) {
		ArrayList<Color> chosenList = chooseSortedList(colors);
		return splitList(chosenList);
	}

	/**
	 * Sorts the colors by their red, green and blue colors. The list for the
	 * color with the greatest variance is returned.
	 * 
	 * @param colors
	 *            a collection of colors
	 * @return a list of colors sorted by red, green or blue, whichever has the
	 *         largest variance
	 */
	private ArrayList<Color> chooseSortedList(Collection<Color> colors) {
		// TODO: Improve efficiency by not sorting list each time
		ArrayList<Color> byRed = sortByColor(colors, RGB.RED);
		int redDiff = getMinMaxDiff(byRed, RGB.RED);

		ArrayList<Color> byGreen = sortByColor(colors, RGB.GREEN);
		int greenDiff = getMinMaxDiff(byGreen, RGB.GREEN);

		ArrayList<Color> byBlue = sortByColor(colors, RGB.BLUE);
		int blueDiff = getMinMaxDiff(byBlue, RGB.BLUE);

		RGB largestDiff = getLargestDiff(redDiff, greenDiff, blueDiff);
		ArrayList<Color> chosenList = byRed; // Arbitrarily default to red
		switch (largestDiff) {
		case RED:
			chosenList = byRed;
			break;
		case GREEN:
			chosenList = byGreen;
			break;
		case BLUE:
			chosenList = byBlue;
			break;
		}

		return chosenList;
	}

	/**
	 * Sorts a collection of colors increasing by their red, green, or blue
	 * values
	 * 
	 * @param colors
	 *            the collection of colors to sort
	 * @param rgb
	 *            the color attribute to sort them by
	 * @return a list of colors sorted by red, green or blue
	 */
	private ArrayList<Color> sortByColor(final Collection<Color> colors, RGB rgb) {
		ArrayList<Color> colorList = new ArrayList<Color>(colors);
		Collections.sort(colorList, rgb.getComparator());
		return colorList;
	}

	/**
	 * For red, green, or blue, get the difference between the most saturated
	 * and least saturated. Assumes the provided list is sorted by the desired
	 * color.
	 * 
	 * @param colors
	 *            the input colors
	 * @param rgb
	 *            the red, green, or blue attribute to get the range of
	 * @return the difference between the maximum and minimum values for red,
	 *         green, or blue in the provided list of colors
	 */
	private int getMinMaxDiff(ArrayList<Color> colors, RGB rgb) {
		return rgb.getValue(colors.get(0))
				- rgb.getValue(colors.get(colors.size() - 1));
	}

	private RGB getLargestDiff(int redDiff, int greenDiff, int blueDiff) {
		if (redDiff > greenDiff && redDiff > blueDiff) {
			return RGB.RED;
		} else if (greenDiff > redDiff && greenDiff > blueDiff) {
			return RGB.GREEN;
		}
		return RGB.BLUE;
	}

	/**
	 * Split a list in two by the median value.
	 * 
	 * @param inputList
	 *            a list of colors to split
	 * @return 2 sublists of the input colors split on the median
	 */
	private Collection<List<Color>> splitList(ArrayList<Color> inputList) {
		int medianIndex = inputList.size() / 2;
		List<Color> part1 = inputList.subList(0, medianIndex);
		List<Color> part2 = inputList.subList(medianIndex, inputList.size());

		Collection<List<Color>> lists = new HashSet<>();
		lists.add(part1);
		lists.add(part2);

		return lists;
	}

	/**
	 * For a collection of colors, average all their values and generate a new
	 * color.
	 * 
	 * @param colors
	 *            a collection of colors to average
	 * @return the average color of the group
	 */
	private Color averageColors(Collection<Color> colors) {
		int redTotal = 0;
		int greenTotal = 0;
		int blueTotal = 0;

		for (Color c : colors) {
			redTotal += c.getRed();
			greenTotal += c.getGreen();
			blueTotal += c.getBlue();
		}

		int redAvg = redTotal / colors.size();
		int greenAvg = greenTotal / colors.size();
		int blueAvg = blueTotal / colors.size();

		return new Color(redAvg, greenAvg, blueAvg);
	}

}
