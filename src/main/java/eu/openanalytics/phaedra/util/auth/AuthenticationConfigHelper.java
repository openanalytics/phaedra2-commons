package eu.openanalytics.phaedra.util.auth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class AuthenticationConfigHelper {

	public static SecurityFilterChain configure(HttpSecurity http) throws Exception {
		if (isInTestScope()) {
			http
				.authorizeRequests()
					.anyRequest().permitAll()
				.and()
					.csrf().disable();
			return http.build();
		}
		
		http
			.authorizeRequests()
				.anyRequest().authenticated()
			.and()
				.csrf().disable()
				.oauth2ResourceServer().jwt();
		return http.build();
	}
	
	public static boolean isInTestScope() {
		try {
			Class.forName("org.springframework.boot.test.context.SpringBootTest");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
