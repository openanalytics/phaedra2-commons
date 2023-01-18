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

import org.apache.commons.lang3.StringUtils;

public class VersionUtils {

	private static final IVersioningScheme VERSIONING_SCHEME = new DefaultVersioningScheme();

	public static String generateNewVersion(String currentVersion) {
		if (StringUtils.isBlank(currentVersion)) {
			return VERSIONING_SCHEME.generateInitialVersion();
        } else {
        	return VERSIONING_SCHEME.incrementVersion(currentVersion);
        }
	}

	public static boolean isValidVersionNumber(String versionNumber) {
		return VERSIONING_SCHEME.isValidVersion(versionNumber);
	}
}
