package com.ibee.mbordernest.model;

public class TableNamesModelClass {//NameIdModelClass

    private String id;
    private String table_name;
    private String Store_id;
    private String Store_name;
    private String StoreKey;
    private String isOpen;
    private String orderType;
    private String currency;
    private String showImages;
    private int orderId;
    String orderTypeName;



    public TableNamesModelClass(String table_name, String id, String  isOpen, String orderType, int orderId, String orderTypeName ) {
        this.table_name = table_name;
        this.orderType = orderType;
        this.isOpen = isOpen;
        this.id = id;
        this.orderId = orderId;
        this.orderTypeName = orderTypeName;
    }
    public TableNamesModelClass(String Store_id, String Store_name, String StoreKey, String currency, String showImages) {
        this.Store_id = Store_id;
        this.currency = currency;
        this.showImages = showImages;
//        this.total_items_list = total_items_list;
        this.Store_name = Store_name;
        this.StoreKey = StoreKey;
    }

    public String getShowImages() {
        return showImages;
    }

    public void setShowImages(String showImages) {
        this.showImages = showImages;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}

