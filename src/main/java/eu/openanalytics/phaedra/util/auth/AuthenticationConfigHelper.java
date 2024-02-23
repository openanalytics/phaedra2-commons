/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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
		if (Boolean.valueOf(System.getProperty("phaedra2.testmode"))) return true;
		try {
			Class.forName("org.springframework.boot.test.context.SpringBootTest");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
