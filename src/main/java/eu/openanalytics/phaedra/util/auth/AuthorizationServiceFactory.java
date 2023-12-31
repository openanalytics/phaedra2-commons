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
package eu.openanalytics.phaedra.util.auth;

import eu.openanalytics.phaedra.util.auth.impl.JwtAuthorizationService;
import eu.openanalytics.phaedra.util.auth.impl.MockAuthorizationService;

public class AuthorizationServiceFactory {

	public static IAuthorizationService create() {
		return create(null);
	}

	public static IAuthorizationService create(ClientCredentialsTokenGenerator ccTokenGen) {
		if (AuthenticationConfigHelper.isInTestScope()) {
			return new MockAuthorizationService();
		}
		return new JwtAuthorizationService(ccTokenGen);
	}
}
