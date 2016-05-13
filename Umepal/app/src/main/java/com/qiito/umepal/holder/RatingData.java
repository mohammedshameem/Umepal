package com.qiito.umepal.holder;

import java.io.Serializable;

/**
 * Created by shiya on 9/10/15.
 */
public class RatingData implements Serializable {

    private StoreRating rating;

    public StoreRating getRating() {
        return rating;
    }

    public void setRating(StoreRating rating) {
        this.rating = rating;
    }
}
