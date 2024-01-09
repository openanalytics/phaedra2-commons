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
package eu.openanalytics.phaedra.util.caching.model;

import java.io.Serializable;
import java.util.Arrays;

import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;

public class CacheKey implements Serializable {

	private static final long serialVersionUID = -1040985176667819827L;

	@IgnoreSizeOf
	private Object[] keyParts;

	public CacheKey(Object... keyParts) {
		this.keyParts = keyParts;
	}

	public Object getKeyPart(int i) {
		return keyParts[i];
	}

	public int getKeyLength() {
		return keyParts.length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(keyParts);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheKey other = (CacheKey) obj;
		if (!Arrays.equals(keyParts, other.keyParts))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CacheKey [keyParts=" + Arrays.toString(keyParts) + "]";
	}

	public static CacheKey create(Object... keyParts) {
		return new CacheKey(keyParts);
	}
}
