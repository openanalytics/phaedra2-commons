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
package eu.openanalytics.phaedra.util.versioning;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultVersioningScheme implements IVersioningScheme {

	private static final String INITIAL_VERSION = "1.0.0";
	private static final Pattern VERSION_FORMAT = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(\\-(.*))?");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd.hhmmss");

	@Override
	public String generateInitialVersion() {
		return String.format("%s-%s", INITIAL_VERSION, generateSuffix());
	}

	@Override
	public String incrementVersion(String currentVersion) {
		Matcher matcher = VERSION_FORMAT.matcher(currentVersion);
		if (!matcher.matches()) throw new IllegalArgumentException(String.format("%s is not a valid version number", currentVersion));

		int[] digits = new int[] { Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)), Integer.valueOf(matcher.group(3)) };
		incrementVersionDigits(digits);

		return String.format("%d.%d.%d-%s", digits[0], digits[1], digits[2], generateSuffix());
	}

	@Override
	public boolean isValidVersion(String version) {
		return VERSION_FORMAT.matcher(version).matches();
	}

	private String generateSuffix() {
		return DATE_FORMAT.format(new Date());
	}

	private void incrementVersionDigits(int[] digits) {
		digits[2]++;
		if (digits[2] == 10) {
			digits[2] = 0;
			digits[1]++;
		}
		if (digits[1] == 10) {
			digits[1] = 0;
			digits[0]++;
		}
	}
}
