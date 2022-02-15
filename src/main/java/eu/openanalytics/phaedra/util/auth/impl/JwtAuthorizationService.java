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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;

import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

public class JwtAuthorizationService implements IAuthorizationService {

	private static final String CLAIM_REALM_ACCESS = "realm_access";
	private static final String CLAIM_ROLES = "roles";
	private static final String ROLE_ADMIN = "phaedra2-admin";
	private static final String ROLE_USER = "phaedra2-user";
	private static final String ROLE_TEAM_PREFIX = "phaedra2-team-";
	private static final String DEFAULT_ACCESS_DENIED_MSG = "Not authorized to perform this operation";
	
	private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationService.class);
	
	@Override
	public void performAccessCheck(Predicate<Object> accessCheck) {
		performAccessCheck(accessCheck, null);
	}

	@Override
	public void performAccessCheck(Predicate<Object> accessCheck, Function<AccessDeniedException, String> messageCustomizer) {
		try {
			boolean access = checkForCurrentPrincipal(accessCheck);
			if (!access) throw new AccessDeniedException(DEFAULT_ACCESS_DENIED_MSG);
		} catch (AccessDeniedException e) {
			String msg = (messageCustomizer == null) ? e.getMessage() : messageCustomizer.apply(e);
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, msg);
		}		
	}

	@Override
	public void performOwnershipCheck(String entityOwner) {
		performOwnershipCheck(entityOwner, null);
	}

	@Override
	public void performOwnershipCheck(String entityOwner, String errorMessage) {
		String currentPrincipalName = getCurrentPrincipalName();
		if (currentPrincipalName == null || !currentPrincipalName.equals(entityOwner)) {
			String msg = (errorMessage == null) ? DEFAULT_ACCESS_DENIED_MSG : errorMessage;
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, msg);
		}
	}

	@Override
	public String getCurrentPrincipalName() {
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		return (currentAuth == null) ? null : currentAuth.getName();
	}

	@Override
	public boolean hasUserAccess() {
		return checkForCurrentPrincipal(principal -> (hasAdminAccess() || hasRole(principal, ROLE_USER)));
	}

	@Override
	public boolean hasAdminAccess() {
		return checkForCurrentPrincipal(principal -> hasRole(principal, ROLE_ADMIN));
	}

	@Override
	public boolean hasTeamAccess(String... teams) {
		return checkForCurrentPrincipal(principal -> hasAdminAccess() || Arrays.stream(teams).anyMatch(team -> hasRole(principal, ROLE_TEAM_PREFIX + team)));
	}

	private static boolean checkForCurrentPrincipal(Predicate<Object> tester) {
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		if (currentAuth == null || currentAuth.getPrincipal() == null || tester == null) return false;
		return tester.test(currentAuth.getPrincipal());
	}
	
	private static boolean hasRole(Object principal, String roleName) {
		if (principal == null) return false;
		
		Jwt accessToken = null;
		if (principal instanceof Jwt) {
			accessToken = (Jwt) principal;
		} else {
			log.debug(String.format("Unsupported principal type: %s. Only JWTs are supported.", principal));
			return false;
		}
		
		Map<String, Object> realmAccess = accessToken.getClaimAsMap(CLAIM_REALM_ACCESS);
		if (realmAccess == null) return false;
		
		List<?> roles = (List<?>) realmAccess.get(CLAIM_ROLES);
		log.debug(String.format("Inspecting JWT token for role '%s'. Available roles: %s", roleName, roles));
		
		if (roles == null || roles.isEmpty()) return false;
		return roles.stream().anyMatch(role -> roleName.equalsIgnoreCase(String.valueOf(role)));
	}

}
