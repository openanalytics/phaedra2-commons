package eu.openanalytics.phaedra.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class WellNumberTest {

	@Test
	public void testWellNumbers() {
		String wellRowLabel = "C";
		String wellCoordinate = "C13";
		int wellNr = 61;
		int[] wellPosition = { 3, 13 };
		int colsPerRow = 24;
		
		assertEquals(wellCoordinate, WellNumberUtils.getWellCoordinate(wellPosition[0], wellPosition[1]));
		assertTrue(Arrays.equals(wellPosition, WellNumberUtils.getWellPosition(wellNr, colsPerRow)));
		assertEquals(wellPosition[0], WellNumberUtils.getWellRowNumber(wellCoordinate));
		assertEquals(wellPosition[1], WellNumberUtils.getWellColumnNumber(wellCoordinate));
		assertEquals(wellNr, WellNumberUtils.getWellNr(wellCoordinate, colsPerRow));
		assertEquals(wellRowLabel, WellNumberUtils.getWellRowLabel(wellPosition[0]));
		assertEquals(wellNr, WellNumberUtils.getWellNr(wellPosition[0], wellPosition[1], colsPerRow));
	}
}
