/**
 * Phaedra II
 *
 * Copyright (C) 2016-2022 Open Analytics
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
package eu.openanalytics.phaedra.util.auth.impl;

import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.security.access.AccessDeniedException;

import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

public class MockAuthorizationService implements IAuthorizationService {

	@Override
	public void performAccessCheck(Predicate<Object> accessCheck) {
		// No-op, always succeeds.
	}

	@Override
	public void performAccessCheck(Predicate<Object> accessCheck, Function<AccessDeniedException, String> messageCustomizer) {
		// No-op, always succeeds.
	}

	@Override
	public void performOwnershipCheck(String entityOwner) {
		// No-op, always succeeds.
	}

	@Override
	public void performOwnershipCheck(String entityOwner, String errorMessage) {
		// No-op, always succeeds.
	}

	@Override
	public String getCurrentPrincipalName() {
		return "testuser";
	}

	@Override
	public boolean hasUserAccess() {
		return true;
	}

	@Override
	public boolean hasAdminAccess() {
		return true;
	}

	@Override
	public boolean hasTeamAccess(String... teams) {
		return true;
	}

}
