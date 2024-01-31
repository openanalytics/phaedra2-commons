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
package eu.openanalytics.phaedra.util.jdbc;

import java.sql.Timestamp;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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
	
	public static DataSource createDataSource(Environment environment) {      
        String url = environment.getProperty("DB_URL");
		if (url == null || url.trim().isEmpty()) {
			throw new RuntimeException("No database URL configured: DB_URL");
		}
		String driverClassName = JDBCUtils.getDriverClassName(url);
		if (driverClassName == null) {
			throw new RuntimeException("Unsupported database type: " + url);
		}

		HikariConfig config = new HikariConfig();
		config.setAutoCommit(false);
		config.setMaximumPoolSize(20);
		config.setConnectionTimeout(60000);
		config.setJdbcUrl(url);
		config.setDriverClassName(driverClassName);
		config.setUsername(environment.getProperty("DB_USERNAME"));
		config.setPassword(environment.getProperty("DB_PASSWORD"));

		String schema = environment.getProperty("DB_SCHEMA");
		if (schema != null && !schema.trim().isEmpty()) {
			config.setSchema(schema);
			config.setConnectionInitSql("set search_path to " + schema);
		}

		return new HikariDataSource(config);
    }
}
