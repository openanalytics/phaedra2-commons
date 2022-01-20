package eu.openanalytics.phaedra.util.auth;

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

public class AuthorizationHelper {

	private static final String CLAIM_REALM_ACCESS = "realm_access";
	private static final String CLAIM_ROLES = "roles";
	
	private static final String ROLE_ADMIN = "phaedra2-admin";
	private static final String ROLE_USER = "phaedra2-user";
	
	private static final String ROLE_TEAM_PREFIX = "phaedra2-team-";

	private static final Logger log = LoggerFactory.getLogger(AuthorizationHelper.class);
	
	public static void performAccessCheck(Predicate<Object> accessCheck) {
		performAccessCheck(accessCheck, null);
	}
	
	public static void performAccessCheck(Predicate<Object> accessCheck, Function<AccessDeniedException, String> messageCustomizer) {
		try {
			boolean access = checkForCurrentPrincipal(accessCheck);
			if (!access) throw new AccessDeniedException("Not authorized to perform this operation.");
		} catch (AccessDeniedException e) {
			String msg = (messageCustomizer == null) ? e.getMessage() : messageCustomizer.apply(e);
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, msg);
		}
	}
	
	public static boolean checkForCurrentPrincipal(Predicate<Object> tester) {
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		if (currentAuth == null || currentAuth.getPrincipal() == null || tester == null) return false;
		return tester.test(currentAuth.getPrincipal());
	}
	
	public static boolean hasUserAccess() {
		return checkForCurrentPrincipal(principal -> hasUserAccess(principal));
	}
	
	public static boolean hasUserAccess(Object principal) {
		return hasAdminAccess(principal) || hasRole(principal, ROLE_USER);
	}
	
	public static boolean hasAdminAccess() {
		return checkForCurrentPrincipal(principal -> hasAdminAccess(principal));
	}
	
	public static boolean hasAdminAccess(Object principal) {
		return hasRole(principal, ROLE_ADMIN);
	}
	
	public static boolean hasTeamAccess(String... teams) {
		return checkForCurrentPrincipal(principal -> hasTeamAccess(principal, teams));
	}

	public static boolean hasTeamAccess(Object principal, String... teams) {
		return hasAdminAccess(principal) || Arrays.stream(teams).anyMatch(team -> hasRole(principal, ROLE_TEAM_PREFIX + team));
	}
	
	public static boolean hasRole(Object principal, String roleName) {
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
