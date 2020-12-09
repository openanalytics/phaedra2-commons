package eu.openanalytics.phaedra.util.caching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.openanalytics.phaedra.util.caching.model.CacheConfig;
import eu.openanalytics.phaedra.util.caching.model.CacheKey;
import eu.openanalytics.phaedra.util.caching.model.ICache;

public class CacheTest {

	@Test
	public void testCache() {
		String cacheName = "TestCache";
		CacheConfig cacheConfig = new CacheConfig(cacheName);
		ICache<Object> cache = CacheFactory.createCache(cacheConfig);
		
		assertEquals(cacheName, cache.getName());
		
		CacheKey testKey = CacheKey.create("testKey");
		Object testValue = new Object();
		cache.put(testKey, testValue);
		
		assertTrue(cache.getKeys().contains(testKey));
		assertTrue(cache.contains(testKey));
		assertEquals(testValue, cache.get(testKey));
	
		cache.remove(testKey);
		
		assertFalse(cache.contains(testKey));
		assertNull(cache.get(testKey));
		
		Object testValue2 = new Object();
		cache.put(testKey, testValue);
		cache.put(testKey, testValue2);
		assertEquals(testValue2, cache.get(testKey));
		
		cache.clear();
		assertTrue(cache.getKeys().isEmpty());
	}
}
