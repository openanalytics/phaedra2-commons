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

import java.util.List;

import eu.openanalytics.phaedra.util.caching.model.CacheKey;
import eu.openanalytics.phaedra.util.caching.model.ICache;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class EhCacheHeap<T> implements ICache<T> {

	private String name;
	private Cache cache;

	public EhCacheHeap(String name, Cache cache) {
		this.name = name;
		this.cache = cache;
	}

	public String getName() {
		return name;
	}
	
	@SuppressWarnings("unchecked")
	public T get(CacheKey key) {
		Element element = cache.get(key);
		if (element == null) return null;
		return (T) element.getObjectValue();
	}

	public T put(CacheKey key, T value) {
		cache.put(new Element(key, value));
		return value;
	}

	public boolean remove(CacheKey key) {
		return cache.remove(key);
	}
	
	public boolean contains(CacheKey key) {
		// Workaround: isKeyInCache may return true for expired elements.
		// Fetching the element here is slower, but forces eviction of expired elements.
		cache.get(key);
		return cache.isKeyInCache(key);
	}

	@SuppressWarnings("unchecked")
	public List<CacheKey> getKeys() {
		return cache.getKeys();
	}

	public void clear() {
		cache.removeAll();
	}
}
