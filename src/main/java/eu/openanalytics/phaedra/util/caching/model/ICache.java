package eu.openanalytics.phaedra.util.caching.model;

import java.util.List;

public interface ICache<T> {

	public String getName();
	
	public T get(CacheKey key);

	public T put(CacheKey key, T value);

	public boolean remove(CacheKey key);

	public boolean contains(CacheKey key);

	public List<CacheKey> getKeys();

	public void clear();

}
