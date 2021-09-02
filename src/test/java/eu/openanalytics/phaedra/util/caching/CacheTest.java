package eu.openanalytics.phaedra.util.caching;

import eu.openanalytics.phaedra.util.caching.model.CacheConfig;
import eu.openanalytics.phaedra.util.caching.model.CacheKey;
import eu.openanalytics.phaedra.util.caching.model.ICache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CacheTest {

    @Test
    public void testCache() {
        String cacheName = "TestCache";
        CacheConfig cacheConfig = new CacheConfig(cacheName);
        ICache<Object> cache = CacheFactory.createCache(cacheConfig);

        Assertions.assertEquals(cacheName, cache.getName());

        CacheKey testKey = CacheKey.create("testKey");
        Object testValue = new Object();
        cache.put(testKey, testValue);

        Assertions.assertTrue(cache.getKeys().contains(testKey));
        Assertions.assertTrue(cache.contains(testKey));
        Assertions.assertEquals(testValue, cache.get(testKey));

        cache.remove(testKey);

        Assertions.assertFalse(cache.contains(testKey));
        Assertions.assertNull(cache.get(testKey));

        Object testValue2 = new Object();
        cache.put(testKey, testValue);
        cache.put(testKey, testValue2);
        Assertions.assertEquals(testValue2, cache.get(testKey));

        cache.clear();
        Assertions.assertTrue(cache.getKeys().isEmpty());
    }
}
