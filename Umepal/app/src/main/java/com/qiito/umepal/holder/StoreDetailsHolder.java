package com.qiito.umepal.holder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shiya on 1/9/15.
 */
public class StoreDetailsHolder implements Serializable {

    private StoreData store;
    private List<StoreRating> ratings;

    public StoreData getStore() {
        return store;
    }

    public void setStore(StoreData store) {
        this.store = store;
    }

    public List<StoreRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<StoreRating> ratings) {
        this.ratings = ratings;
    }
}
