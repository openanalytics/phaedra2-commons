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
package eu.openanalytics.phaedra.util.caching;

import eu.openanalytics.phaedra.util.caching.model.CacheConfig;
import eu.openanalytics.phaedra.util.caching.model.ICache;
import eu.openanalytics.phaedra.util.caching.model.impl.EhCacheHeap;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.MemoryUnit;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

public class CacheFactory {

	private static final CacheManager CM = CacheManager.create();

	public static <T> ICache<T> createCache(String name) {
		return createCache(new CacheConfig(name));
	}

	public static <T> ICache<T> createCache(CacheConfig config) {
		CacheConfiguration cacheConfiguration = new CacheConfiguration(config.name, 0)
				.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU)
				.timeToLiveSeconds(config.ttl)
				.timeToIdleSeconds(config.tti)
				;

		if (config.maxBytes > 0) {
			cacheConfiguration.maxBytesLocalHeap(config.maxBytes, MemoryUnit.BYTES);
		}

		Cache cache = new Cache(cacheConfiguration);
		CM.addCache(cache);
		return new EhCacheHeap<>(config.name, cache);
	}
}
