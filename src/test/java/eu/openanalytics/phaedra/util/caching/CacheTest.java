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
