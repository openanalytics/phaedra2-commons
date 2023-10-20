/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
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
