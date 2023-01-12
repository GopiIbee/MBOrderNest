package com.ibee.mbordernest.model;

public class StoreChangeModelClass {

    private String Store_id;
    private String Store_name;
    private String StoreKey;
    private String currency;
    private String showImages;


    public StoreChangeModelClass(String Store_id, String Store_name, String StoreKey, String currency, String showImages) {
        this.Store_id = Store_id;
        this.currency = currency;
        this.showImages = showImages;
        this.Store_name = Store_name;
        this.StoreKey = StoreKey;
    }

    public String getStore_id() {
        return Store_id;
    }

    public void setStore_id(String store_id) {
        Store_id = store_id;
    }

    public String getStore_name() {
        return Store_name;
    }

    public void setStore_name(String store_name) {
        Store_name = store_name;
    }

    public String getStoreKey() {
        return StoreKey;
    }

    public void setStoreKey(String storeKey) {
        StoreKey = storeKey;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getShowImages() {
        return showImages;
    }

    public void setShowImages(String showImages) {
        this.showImages = showImages;
    }
}

