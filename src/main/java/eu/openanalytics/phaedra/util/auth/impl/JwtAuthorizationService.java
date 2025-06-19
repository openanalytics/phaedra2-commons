/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
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
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;

import eu.openanalytics.phaedra.util.auth.ClientCredentialsTokenGenerator;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;

public class JwtAuthorizationService implements IAuthorizationService {

	private static final String CLAIM_REALM_ACCESS = "realm_access";
	private static final String CLAIM_ROLES = "roles";
	private static final String DEFAULT_ACCESS_DENIED_MSG = "Not authorized to perform this operation";

	private static final String ROLE_NAME_ADMIN = "ROLE_NAME_ADMIN";
	private static final String ROLE_NAME_USER = "ROLE_NAME_USER";
	private static final String ROLE_TEAM_PREFIX = "ROLE_PREFIX_TEAM";
	
	private static final String DEFAULT_ROLE_ADMIN = "phaedra-admin";
	private static final String DEFAULT_ROLE_USER = "phaedra-user";
	private static final String DEFAULT_ROLE_TEAM_PREFIX = "phaedra-team-";

	private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationService.class);

	private ClientCredentialsTokenGenerator clientCredentialsTokenGenerator;

	public JwtAuthorizationService(ClientCredentialsTokenGenerator clientCredentialsTokenGenerator) {
		this.clientCredentialsTokenGenerator = clientCredentialsTokenGenerator;
	}

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
		if (hasAdminAccess()) return;
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
	public String getCurrentBearerToken() {
		// Attempt to use the current auth context, if there is one
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		if (currentAuth != null) {
			Jwt accessToken = getJWT(currentAuth.getPrincipal());
			if (accessToken != null) return accessToken.getTokenValue();
		}

		// Attempt to use client credentials, if there is no user auth context
		if (clientCredentialsTokenGenerator != null) {
			return clientCredentialsTokenGenerator.obtainToken().getTokenValue();
		}

		return null;
	}

	@Override
	public boolean hasUserAccess() {
		String roleName = getRoleName(ROLE_NAME_USER, DEFAULT_ROLE_USER);
		return checkForCurrentPrincipal(principal -> (hasAdminAccess() || hasRole(principal, roleName)));
	}

	@Override
	public boolean hasAdminAccess() {
		String roleName = getRoleName(ROLE_NAME_ADMIN, DEFAULT_ROLE_ADMIN);
		return checkForCurrentPrincipal(principal -> hasRole(principal, roleName));
	}

	@Override
	public boolean hasTeamAccess(String... teams) {
		String rolePrefix = getRoleName(ROLE_TEAM_PREFIX, DEFAULT_ROLE_TEAM_PREFIX);
		return checkForCurrentPrincipal(principal -> hasAdminAccess() || Arrays.stream(teams).anyMatch(team -> hasRole(principal, rolePrefix + team)));
	}

	@Override
	public void runInKafkaContext(Runnable task) {
		final Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		try {
			SecurityContextHolder.getContext().setAuthentication(new KafkaAuthentication());
			task.run();
		} finally {
			SecurityContextHolder.getContext().setAuthentication(currentAuth);
		}
	}

	private static boolean checkForCurrentPrincipal(Predicate<Object> tester) {
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		if (currentAuth == null || currentAuth.getPrincipal() == null || tester == null) return false;
		return tester.test(currentAuth.getPrincipal());
	}

	private static boolean hasRole(Object principal, String roleName) {
		if (principal == null) return false;

		if (principal.equals(KafkaAuthentication.PRINCIPAL)) return true;

		Jwt accessToken = getJWT(principal);
		if (accessToken == null) return false;

		Map<String, Object> realmAccess = accessToken.getClaimAsMap(CLAIM_REALM_ACCESS);
		if (realmAccess == null) return false;

		List<?> roles = (List<?>) realmAccess.get(CLAIM_ROLES);
		log.debug(String.format("Inspecting JWT token for role '%s'. Available roles: %s", roleName, roles));

		if (roles == null || roles.isEmpty()) return false;
		return roles.stream().anyMatch(role -> roleName.equalsIgnoreCase(String.valueOf(role)));
	}

	private static String getRoleName(String roleNameKey, String defaultRoleName) {
		String name = System.getenv(roleNameKey);
		if (name == null || name.isBlank()) name = defaultRoleName;
		return name;
	}

	private static Jwt getJWT(Object principal) {
		Jwt accessToken = null;
		if (principal instanceof Jwt) {
			accessToken = (Jwt) principal;
		} else {
			log.debug(String.format("Unsupported principal type: %s. Only JWTs are supported.", principal));
		}
		return accessToken;
	}

	private static class KafkaAuthentication extends AbstractAuthenticationToken {

		private static final long serialVersionUID = -6574504446597627288L;
		private static final String PRINCIPAL = KafkaAuthentication.class.getSimpleName();

		public KafkaAuthentication() {
			super(null);
		}

		@Override
		public Object getCredentials() {
			return PRINCIPAL;
		}

		@Override
		public Object getPrincipal() {
			return PRINCIPAL;
		}
	}
}
