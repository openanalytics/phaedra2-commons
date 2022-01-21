package eu.openanalytics.phaedra.util.auth;

import eu.openanalytics.phaedra.util.auth.impl.JwtAuthorizationService;
import eu.openanalytics.phaedra.util.auth.impl.MockAuthorizationService;

public class AuthorizationServiceFactory {

	public static IAuthorizationService create() {
		try {
			//TODO Find a better way to handle this in test contexts
			Class.forName("org.springframework.boot.test.context.SpringBootTest");
			return new MockAuthorizationService();
		} catch (ClassNotFoundException e) {
			return new JwtAuthorizationService();
		}
	}
}
