package eu.openanalytics.phaedra.util.auth;

import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.security.access.AccessDeniedException;

public interface IAuthorizationService {

	public void performAccessCheck(Predicate<Object> accessCheck);
	public void performAccessCheck(Predicate<Object> accessCheck, Function<AccessDeniedException, String> messageCustomizer);
	
	public void performOwnershipCheck(String entityOwner);
	public void performOwnershipCheck(String entityOwner, String errorMessage);
	
	public String getCurrentPrincipalName();
	
	public boolean hasUserAccess();
	public boolean hasAdminAccess();
	public boolean hasTeamAccess(String... teams);

}
