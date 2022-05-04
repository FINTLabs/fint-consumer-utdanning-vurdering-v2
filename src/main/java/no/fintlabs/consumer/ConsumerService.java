package no.fintlabs.consumer;

import no.fintlabs.cache.Cache;
import no.fintlabs.cache.CacheManager;

import java.io.Serializable;
import java.util.stream.Stream;

// TODO: 01/03/2022 Move to fint-core-cache
public abstract class ConsumerService<T extends Serializable> {

    private Cache<T> cache;
    private CacheManager cacheManager;

    public ConsumerService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        cache = initializeCache(cacheManager);
    }

    protected abstract Cache<T> initializeCache(CacheManager cacheManager);

    protected Cache<T> getCache() {
        return cache;
    }

    public long getLastUpdated() {
        return cache.getLastUpdated();
    }

    public int getCacheSize() {
        return cache.size();
    }

    public Stream<T> streamSliceSince(long sinceTimeStamp, int offset, int size) {
        return null;
    }

    public Stream<T> streamSlice(int offset, int size) {
        return null;
    }

    public Stream<T> streamSince(long sinceTimeStamp) {
        return cache.streamSince(sinceTimeStamp);
    }

    public Stream<T> streamAll() {
        return cache.stream();
    }

    public Stream<T> streamByHashCode(int hashCode) {
        return cache.streamByHashCode(hashCode);
    }
}
