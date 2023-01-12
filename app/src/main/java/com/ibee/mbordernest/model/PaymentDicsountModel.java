package com.ibee.mbordernest.model;

public class PaymentDicsountModel {

    private String id;
    private String nameOrCode;
    private String offerValue;
    private String discountType;
    private String amount;



    public PaymentDicsountModel(String id, String nameOrCode, String amount) {
        this.id = id;
        this.nameOrCode = nameOrCode;
        this.amount = amount;
    }
    public PaymentDicsountModel(String id, String nameOrCode, String offerValue, String discountType) {
        this.id = id;
        this.nameOrCode = nameOrCode;
        this.offerValue = offerValue;
        this.discountType = discountType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameOrCode() {
        return nameOrCode;
    }

    public void setNameOrCode(String nameOrCode) {
        this.nameOrCode = nameOrCode;
    }

    public String getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(String offerValue) {
        this.offerValue = offerValue;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }
}

