package com.nytimes.android.external.store.base;

import android.support.annotation.NonNull;

import com.nytimes.android.external.store.base.impl.BarCode;

import rx.Observable;

/**
 * a {@link com.nytimes.android.external.store.base.impl.StoreBuilder StoreBuilder}
 * will return an instance of a store
 * <p>
 * A {@link com.nytimes.android.external.store.base.Store  Store} can
 * {@link com.nytimes.android.external.store.base.Store#get(BarCode) Store.get() } cached data or
 * force a call to {@link com.nytimes.android.external.store.base.Store#fetch(BarCode) Store.fetch() }
 * (skipping cache)
 */
public interface Store<T> {

    /**
     * Return an Observable of T for request Barcode
     * Data will be returned from oldest non expired source
     * Sources are Memory Cache, Disk Cache, Inflight, Network Response
     */
    @NonNull
    Observable<T> get(@NonNull BarCode barCode);

    /**
     * Return an Observable of T for requested Barcode skipping Memory & Disk Cache
     */
    @NonNull
    Observable<T> fetch(@NonNull BarCode barCode);

    /**
     * @return an Observable that emits new items when they arrive.
     */
    @NonNull
    Observable<T> stream();

    /**
     * Similar to  {@link com.nytimes.android.external.store.base.Store#get(BarCode) Store.get() }
     * Rather than returning a single response, Stream will stay subscribed for future emissions to the Store
     * NOTE: Stream will continue to get emissions for ANY barcode not just starting one
     *
     * @deprecated Use {@link Store#stream()}. If you need to start with the first value,
     * use {@code store.stream().startWith(store.get(barcode))}
     */
    @Deprecated
    @NonNull
    Observable<T> stream(BarCode id);

    /**
     * Clear the memory cache of all entries
     */
    void clearMemory();

    /**
     * Purge a particular entry from memory cache.
     */
    void clearMemory(@NonNull BarCode barCode);


}
