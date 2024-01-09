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

import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.security.access.AccessDeniedException;

public interface IAuthorizationService {

	public void performAccessCheck(Predicate<Object> accessCheck);
	public void performAccessCheck(Predicate<Object> accessCheck, Function<AccessDeniedException, String> messageCustomizer);

	public void performOwnershipCheck(String entityOwner);
	public void performOwnershipCheck(String entityOwner, String errorMessage);

	public String getCurrentPrincipalName();
	public String getCurrentBearerToken();

	public boolean hasUserAccess();
	public boolean hasAdminAccess();
	public boolean hasTeamAccess(String... teams);

	/**
	 * Execute a task in the context of Kafka.
	 * This means any authorization checks will automatically succeed.
	 * TODO This is a temporary solution, and will be replaced with authorization checks based on message headers.
	 */
	public void runInKafkaContext(Runnable task);

}
