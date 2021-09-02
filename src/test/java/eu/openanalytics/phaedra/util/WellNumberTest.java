package eu.openanalytics.phaedra.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WellNumberTest {

	@Test
	public void testWellNumbers() {
		String wellRowLabel = "C";
		String wellCoordinate = "C13";
		int wellNr = 61;
		int[] wellPosition = { 3, 13 };
		int colsPerRow = 24;

		Assertions.assertEquals(wellCoordinate, WellNumberUtils.getWellCoordinate(wellPosition[0], wellPosition[1]));
		Assertions.assertArrayEquals(wellPosition, WellNumberUtils.getWellPosition(wellNr, colsPerRow));
		Assertions.assertEquals(wellPosition[0], WellNumberUtils.getWellRowNumber(wellCoordinate));
		Assertions.assertEquals(wellPosition[1], WellNumberUtils.getWellColumnNumber(wellCoordinate));
		Assertions.assertEquals(wellNr, WellNumberUtils.getWellNr(wellCoordinate, colsPerRow));
		Assertions.assertEquals(wellRowLabel, WellNumberUtils.getWellRowLabel(wellPosition[0]));
		Assertions.assertEquals(wellNr, WellNumberUtils.getWellNr(wellPosition[0], wellPosition[1], colsPerRow));
	}
}
