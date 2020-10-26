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
