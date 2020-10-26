package eu.openanalytics.phaedra.util.caching.model;

public class CacheConfig {

	public String name;
	public int ttl;
	public int tti;
	public long maxBytes;
	
	public CacheConfig() {
		this(null);
	}
	
	public CacheConfig(String name) {
		this.name = name;
	}
	
}
