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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WellNumberUtils {

	private static final Pattern WELL_COORD_PATTERN = Pattern.compile("([a-zA-Z]+) *-*_* *(\\d+)");
	private static final Pattern WELL_ROW_COL_PATTERN = Pattern.compile("[Rr](\\d+) *-*_* *[Cc](\\d+)");

	/**
	 * Get the well coordinate from a well row nr and column nr.
	 * E.g. 13,24 becomes "M24"
	 */
	public static String getWellCoordinate(int row, int col) {
		return getWellCoordinate(row, col, null);
	}

	/**
	 * Get the well coordinate from a well row nr and column nr.
	 * E.g. 13,24 becomes "M24"
	 */
	public static String getWellCoordinate(int row, int col, String separator) {
		if (separator == null) separator = "";
		return getWellRowLabel(row) + separator + col;
	}

	/**
	 * Get the row label for a well row nr.
	 * E.g. 4 becomes "D"
	 */
	public static String getWellRowLabel(int row) {
		String rowString = "";
		if (row <= 26) {
			rowString = "" + (char) (row + 64);
		} else {
			// After row Z, start with AA
			int div = row/26;
			int mod = row%26;
			rowString = "" + (char)(div + 64) + (char)(mod + 64);
		}
		return rowString;
	}

	/**
	 * Convert a well coordinate to a well number, starting at 1.
	 * E.g. "C10" with 12 columns per row becomes 34
	 *
	 * @param coordinate The well coordinate, e.g. "P24" or "R10-C12".
	 * @param colsPerRow The number of columns in the plate, e.g. 12 or 24.
	 */
	public static int getWellNr(String coordinate, int colsPerRow) {
		int row = getWellRowNumber(coordinate);
		int col = getWellColumnNumber(coordinate);
		int value = (row-1)*colsPerRow + col;
		return value;
	}

	/**
	 * Convert a well position to a well number, starting at 1.
	 * E.g. 2,3 with 12 columns per row becomes 34
	 */
	public static int getWellNr(int row, int col, int colsPerRow) {
		int value = (row-1)*colsPerRow + col;
		return value;
	}

	/**
	 * Get the well position from a well number.
	 * E.g. wellNr 96 with 12 columns per row becomes [8,12]
	 *
	 * @param wellNr The well number, starting from 1.
	 * @param colsPerRow The number of columns in the plate, e.g. 12 or 24.
	 */
	public static int[] getWellPosition(int wellNr, int colsPerRow) {
		wellNr--;
		int rowNr = 1 + wellNr / colsPerRow;
		int colNr = 1 + wellNr % colsPerRow;
		return new int[]{rowNr,colNr};
	}

	public static int getWellRowNumber(String wellCoordinate) {
		Matcher matcher = WELL_COORD_PATTERN.matcher(wellCoordinate);
		if (matcher.matches()) {
			String rowString = matcher.group(1);
			int len = rowString.length();
			int row = 0;
			for (int index = 0; index<len; index++) {
				char c = rowString.charAt(index);
				row += (c - 64) * Math.pow(26, (len-index)-1);
			}
			return row;
		} else {
			matcher = WELL_ROW_COL_PATTERN.matcher(wellCoordinate);
			if (matcher.matches()) {
				String rowString = matcher.group(1);
				return Integer.valueOf(rowString);
			} else {
				return 0;
			}
		}
	}

	public static int getWellColumnNumber(String wellCoordinate) {
		Matcher matcher = WELL_COORD_PATTERN.matcher(wellCoordinate);
		if (matcher.matches()) {
			String colString = matcher.group(2);
			int col = Integer.parseInt(colString);
			return col;
		} else {
			matcher = WELL_ROW_COL_PATTERN.matcher(wellCoordinate);
			if (matcher.matches()) {
				String colString = matcher.group(2);
				return Integer.valueOf(colString);
			} else {
				return 0;
			}
		}
	}

}
