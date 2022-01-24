package eu.openanalytics.phaedra.util.auth;

import eu.openanalytics.phaedra.util.auth.impl.JwtAuthorizationService;
import eu.openanalytics.phaedra.util.auth.impl.MockAuthorizationService;

public class AuthorizationServiceFactory {

	public static IAuthorizationService create() {
		if (AuthenticationConfigHelper.isInTestScope()) {
			return new MockAuthorizationService();
		}
		return new JwtAuthorizationService();
	}
}
