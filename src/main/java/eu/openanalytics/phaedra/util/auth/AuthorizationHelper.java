package eu.openanalytics.phaedra.util.auth;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;

public class AuthorizationHelper {

	private static final String CLAIM_ROLES = "realm_access.roles";
	
	private static final String ROLE_ADMIN = "phaedra2-admin";
	private static final String ROLE_USER = "phaedra2-admin";
	
	private static final String ROLE_TEAM_PREFIX = "phaedra2-team-";

	public static boolean hasUserAccess(Jwt accessToken) {
		return hasRole(accessToken, ROLE_USER);
	}
	
	public static boolean hasAdminAccess(Jwt accessToken) {
		return hasRole(accessToken, ROLE_ADMIN);
	}
	
	public static boolean hasTeamAccess(Jwt accessToken, String... teams) {
		return hasAdminAccess(accessToken) || Arrays.stream(teams).anyMatch(team -> hasRole(accessToken, ROLE_TEAM_PREFIX + team));
	}
	
	public static boolean hasRole(Jwt accessToken, String roleName) {
		if (accessToken == null) return false;
		
		List<String> roles = accessToken.getClaimAsStringList(CLAIM_ROLES);
		if (roles == null || roles.isEmpty()) return false;
		
		return roles.stream().anyMatch(role -> role.equalsIgnoreCase(roleName));
	}
	
}
