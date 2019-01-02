package com.outbound.client.utils;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.http.HttpTransportOptions;


public class DataStoreUtil {

    private Datastore datastore;

    /**
     * Generates an empty class so we don't have to use a static interface
     */
    public DataStoreUtil() {
    }

    /**
     * Initializes access to the DataStore.
     */
    public synchronized void connectToDatastore() {

        if (datastore == null) {
           datastore = DatastoreOptions.getDefaultInstance().getService();
        }
    }

    /**
     * Gets a value from the GCP DataStore that the instance is connected to.
     *
     * @param kind the kind of entry we are looking for
     * @param key  the key for that entry
     * @return Entity
     */
    public Entity getEntityFromKindAndKey(String kind, String key) {
        return datastore.get(getKeyFactory(kind).newKey(key));
    }

    /**
     * Gets the KeyFactory needed to build data.
     *
     * @param kind sets the kind of KeyFactory we need
     * @return KeyFactory
     */
    private KeyFactory getKeyFactory(String kind) {
        return datastore.newKeyFactory().setKind(kind);
    }

}
