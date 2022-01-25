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
