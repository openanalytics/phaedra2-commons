/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
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
package eu.openanalytics.phaedra.util.jdbc;

import java.sql.Timestamp;
import java.util.Optional;

public class JDBCUtils {

	private enum DbType {

		Oracle("oracle.jdbc.OracleDriver"),
		Postgresql("org.postgresql.Driver"),
		H2("org.h2.Driver"),
		MonetDB("nl.cwi.monetdb.jdbc.MonetDriver"),
		Unknown("");

		private String driverClassName;

		private DbType(String driverClassName) {
			this.driverClassName = driverClassName;
		}

		public String getDriverClassName() {
			return driverClassName;
		}

		public static DbType getByName(String name) {
			for (DbType t: values()) {
				if (t.name().equalsIgnoreCase(name)) return t;
			}
			return Unknown;
		}

		public static DbType getByURL(String url) {
			String type = url.split(":")[1];
			return getByName(type);
		}
	};

	public static String getDriverClassName(String url) {
		return Optional
				.of(DbType.getByURL(url))
				.map(DbType::getDriverClassName)
				.orElse(null);
	}

	public static Timestamp toTimestamp(java.util.Date date) {
		return (date == null) ? null : Timestamp.from(date.toInstant());
	}
}
