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
package eu.openanalytics.phaedra.util.caching.model.impl;

import java.util.Collections;
import java.util.List;

import eu.openanalytics.phaedra.util.caching.model.CacheKey;
import eu.openanalytics.phaedra.util.caching.model.ICache;

public class NoopCache <T> implements ICache<T> {

	@Override
	public String getName() {
		return "No-op Cache";
	}

	@Override
	public T get(CacheKey key) {
		return null;
	}

	@Override
	public T put(CacheKey key, T value) {
		return value;
	}

	@Override
	public boolean remove(CacheKey key) {
		return false;
	}

	@Override
	public boolean contains(CacheKey key) {
		return false;
	}

	@Override
	public List<CacheKey> getKeys() {
		return Collections.emptyList();
	}

	@Override
	public void clear() {
		// No-op
	}

}
