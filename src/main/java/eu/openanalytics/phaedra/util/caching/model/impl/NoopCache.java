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
