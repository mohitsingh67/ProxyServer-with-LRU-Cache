// LRUCache.java
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private final int cacheSize;
    private final Map<K, V> cache;

    public LRUCache(int cacheSize) {
        this.cacheSize = cacheSize;
        this.cache = new LinkedHashMap<>(cacheSize, 0.75f, true); // true for access order
    }

    // Get value from the cache
    public V get(K key) {
        return cache.get(key);
    }

    // Put value in the cache
    public void put(K key, V value) {
        if (cache.size() >= cacheSize) {
            // Remove the least recently used entry
            Map.Entry<K, V> eldest = cache.entrySet().iterator().next();
            cache.remove(eldest.getKey());
            System.out.println("Cache Miss - Evicting: " + eldest.getKey());
        }
        cache.put(key, value);
        System.out.println("Cache Updated - Added: " + key);
    }

    // Check if key exists in the cache
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }
}
