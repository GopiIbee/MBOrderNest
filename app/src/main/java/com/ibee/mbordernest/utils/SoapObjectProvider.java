package com.ibee.mbordernest.utils;




public class SoapObjectProvider {

//    static SoapObject results = null;
    static String namespace = "http://tempuri.org/";

 /*   public static SoapObject OnCustomerLogin(String API_TOKEN, String mUser_name, String mPassword) {
        String methodNaction = Constants.SERVICE_ON_LOGIN;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

        PropertyInfo user_name = new PropertyInfo();
        user_name.namespace = namespace;
        user_name.setName("username");
        user_name.setValue(mUser_name);
        user_name.setType(String.class);
        request.addProperty(user_name);

        PropertyInfo password = new PropertyInfo();
        password.namespace = namespace;
        password.setName("password");
        password.setValue(mPassword);
        password.setType(String.class);
        request.addProperty(password);

        System.out.println("get Login " + request.toString());
        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            System.out.println("OnCustomerLoginResult " + results.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject GetOrderTypesByUser(String API_TOKEN, String uKey, String muserId) {
        String methodNaction = Constants.SERVICE_GetOrderTypesByUser;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

        PropertyInfo user_name = new PropertyInfo();
        user_name.namespace = namespace;
        user_name.setName("userId");
        user_name.setValue(muserId);
        user_name.setType(String.class);
        request.addProperty(user_name);

        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            System.out.println("GetOrderTypesByUserResult " + results.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject GetPrintdataApi(String API_TOKEN, String uKey, int orderId, int storeId, String isBilling, String detIds) {
        String methodNaction = Constants.SERVICE_GetOrderPrintDetails;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);


        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(int.class);
        request.addProperty(O_orderId);

        PropertyInfo O_storeId = new PropertyInfo();
        O_storeId.namespace = namespace;
        O_storeId.setName("storeId");
        O_storeId.setValue(storeId);
        O_storeId.setType(int.class);
        request.addProperty(O_storeId);

        PropertyInfo O_isBilling = new PropertyInfo();
        O_isBilling.namespace = namespace;
        O_isBilling.setName("isBilling");
        O_isBilling.setValue(isBilling);
        O_isBilling.setType(boolean.class);
        request.addProperty(O_isBilling);

        PropertyInfo O_detIds = new PropertyInfo();
        O_detIds.namespace = namespace;
        O_detIds.setName("detIds");
        O_detIds.setValue(detIds);
        O_detIds.setType(String.class);
        request.addProperty(O_detIds);
        // System.out.println("GetProdutst" + request.toString());

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            //   System.out.println("GetProdutstResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject GetCategoriesAndProducts(String API_TOKEN, String uKey, String muserId, String orderTypeId, int orderId) {
        String methodNaction = Constants.SERVICE_GetCategoriesAndProducts;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

        PropertyInfo user_name = new PropertyInfo();
        user_name.namespace = namespace;
        user_name.setName("userId");
        user_name.setValue(muserId);
        user_name.setType(String.class);
        request.addProperty(user_name);

        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_orderTypeId = new PropertyInfo();
        O_orderTypeId.namespace = namespace;
        O_orderTypeId.setName("orderTypeId");
        O_orderTypeId.setValue(orderTypeId);
        O_orderTypeId.setType(String.class);
        request.addProperty(O_orderTypeId);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(int.class);
        request.addProperty(O_orderId);
        System.out.println("GetProdutst" + request.toString());

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("GetProdutstResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject PayOrder(String API_TOKEN, String uKey, int orderId, String payId, String muserId, String amt) {
        String methodNaction = Constants.SERVICE_PayOrder;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_payId = new PropertyInfo();
        O_payId.namespace = namespace;
        O_payId.setName("payId");
        O_payId.setValue(payId);
        O_payId.setType(long.class);
        request.addProperty(O_payId);

        PropertyInfo user_name = new PropertyInfo();
        user_name.namespace = namespace;
        user_name.setName("userId");
        user_name.setValue(muserId);
        user_name.setType(String.class);
        request.addProperty(user_name);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(long.class);
        request.addProperty(O_orderId);

        PropertyInfo O_amt = new PropertyInfo();
        O_amt.namespace = namespace;
        O_amt.setName("amt");
        O_amt.setValue(amt);
        O_amt.setType(long.class);
        request.addProperty(O_amt);
        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("SendKotResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }


    public static SoapObject GetPaymentModes(String API_TOKEN, String uKey) {
        String methodNaction = Constants.SERVICE_GetWalletSettings;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);


        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("SendKotResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject DoRecharge(String API_TOKEN, String uKey, String mobile, String name,
                                        String payId, String amt, String transNo, String comments, String userId, String recId) {
        String methodNaction = Constants.SERVICE_DoRechargeV1;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_mobile = new PropertyInfo();
        O_mobile.namespace = namespace;
        O_mobile.setName("mobile");
        O_mobile.setValue(mobile);
        O_mobile.setType(String.class);
        request.addProperty(O_mobile);

        PropertyInfo O_name = new PropertyInfo();
        O_name.namespace = namespace;
        O_name.setName("name");
        O_name.setValue(name);
        O_name.setType(String.class);
        request.addProperty(O_name);


        PropertyInfo O_payId = new PropertyInfo();
        O_payId.namespace = namespace;
        O_payId.setName("payId");
        O_payId.setValue(payId);
        O_payId.setType(String.class);
        request.addProperty(O_payId);

        PropertyInfo O_amt = new PropertyInfo();
        O_amt.namespace = namespace;
        O_amt.setName("amt");
        O_amt.setValue(amt);
        O_amt.setType(String.class);
        request.addProperty(O_amt);


        PropertyInfo O_transNo = new PropertyInfo();
        O_transNo.namespace = namespace;
        O_transNo.setName("transNo");
        O_transNo.setValue(transNo);
        O_transNo.setType(String.class);
        request.addProperty(O_transNo);

        PropertyInfo O_comments = new PropertyInfo();
        O_comments.namespace = namespace;
        O_comments.setName("comments");
        O_comments.setValue(comments);
        O_comments.setType(String.class);
        request.addProperty(O_comments);

        PropertyInfo O_userId = new PropertyInfo();
        O_userId.namespace = namespace;
        O_userId.setName("userId");
        O_userId.setValue(userId);
        O_userId.setType(String.class);
        request.addProperty(O_userId);

        PropertyInfo O_recId = new PropertyInfo();
        O_recId.namespace = namespace;
        O_recId.setName("recId");
        O_recId.setValue(recId);
        O_recId.setType(String.class);
        request.addProperty(O_recId);

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("SendKotResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject SendOTP(String API_TOKEN, String uKey, String mobile) {
        String methodNaction = Constants.SERVICE_InitRecharge;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_mobile = new PropertyInfo();
        O_mobile.namespace = namespace;
        O_mobile.setName("mobile");
        O_mobile.setValue(mobile);
        O_mobile.setType(String.class);
        request.addProperty(O_mobile);

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("SendKotResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject SendSmsBill(String API_TOKEN, String uKey, String orderId) {
        String methodNaction = Constants.SERVICE_SendBillToCustomer;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(String.class);
        request.addProperty(O_orderId);

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("SendKotResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }


    public static SoapObject CheckeWardsBalance(String API_TOKEN, String uKey, String mobile) {
        String methodNaction = Constants.SERVICE_CheckeWardsBalance;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_mobile = new PropertyInfo();
        O_mobile.namespace = namespace;
        O_mobile.setName("mobile");
        O_mobile.setValue(mobile);
        O_mobile.setType(String.class);
        request.addProperty(O_mobile);

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("SendKotResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject RedeemeWardsPoints(String API_TOKEN, String uKey, int orderId, String points, String otp) {
        String methodNaction = Constants.SERVICE_RedeemeWardsPoints;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(long.class);
        request.addProperty(O_orderId);

        PropertyInfo O_points = new PropertyInfo();
        O_points.namespace = namespace;
        O_points.setName("points");
        O_points.setValue(points);
        O_points.setType(double.class);
        request.addProperty(O_points);

        PropertyInfo O_otp = new PropertyInfo();
        O_otp.namespace = namespace;
        O_otp.setName("otp");
        O_otp.setValue(otp);
        O_otp.setType(String.class);
        request.addProperty(O_otp);

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("SendKotResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject RedeemeWardsCode(String API_TOKEN, String uKey, int orderId, String mobile, String coupon) {
        String methodNaction = Constants.SERVICE_RedeemeWardsCoupon;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(long.class);
        request.addProperty(O_orderId);

        PropertyInfo O_mobile = new PropertyInfo();
        O_mobile.namespace = namespace;
        O_mobile.setName("mobile");
        O_mobile.setValue(mobile);
        O_mobile.setType(String.class);
        request.addProperty(O_mobile);

        PropertyInfo O_couponCode = new PropertyInfo();
        O_couponCode.namespace = namespace;
        O_couponCode.setName("coupon");
        O_couponCode.setValue(coupon);
        O_couponCode.setType(String.class);
        request.addProperty(O_couponCode);


        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("SendKotResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject RedeemOrderNestCode(String API_TOKEN, String uKey, int orderId, String couponCode) {
        String methodNaction = Constants.SERVICE_ApplyCoupon;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(long.class);
        request.addProperty(O_orderId);

        PropertyInfo O_couponCode = new PropertyInfo();
        O_couponCode.namespace = namespace;
        O_couponCode.setName("couponCode");
        O_couponCode.setValue(couponCode);
        O_couponCode.setType(String.class);
        request.addProperty(O_couponCode);


        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("SendKotResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject ValidatePIN(String API_TOKEN, String uKey, int orderId, String pin, String action, String reason) {
        String methodNaction = Constants.SERVICE_ValidatePIN;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(long.class);
        request.addProperty(O_orderId);

       *//* PropertyInfo user_name = new PropertyInfo();
        user_name.namespace = namespace;
        user_name.setName("userId");
        user_name.setValue(muserId);
        user_name.setType(String.class);
        request.addProperty(user_name);
*//*
        PropertyInfo O_pin = new PropertyInfo();
        O_pin.namespace = namespace;
        O_pin.setName("pin");
        O_pin.setValue(pin);
        O_pin.setType(String.class);
        request.addProperty(O_pin);

        PropertyInfo O_reason = new PropertyInfo();
        O_reason.namespace = namespace;
        O_reason.setName("reason");
        O_reason.setValue(reason);
        O_reason.setType(String.class);
        request.addProperty(O_reason);

        PropertyInfo O_action = new PropertyInfo();
        O_action.namespace = namespace;
        O_action.setName("action");
        O_action.setValue(action);
        O_action.setType(String.class);
        request.addProperty(O_action);
        System.out.println("ValidatePINrequest" + request.toString());

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            System.out.println("ValidatePINresults" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject SendOrderToKot(String API_TOKEN, String uKey, int orderId, String isEdit) {
        String methodNaction = Constants.SERVICE_SendOrderToKot;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_isEdit = new PropertyInfo();
        O_isEdit.namespace = namespace;
        O_isEdit.setName("isEdit");
        O_isEdit.setValue(isEdit);
        O_isEdit.setType(boolean.class);
        request.addProperty(O_isEdit);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(int.class);
        request.addProperty(O_orderId);

        System.out.println("SendKotRE" + request.toString());

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            System.out.println("SendKotResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject ApplyDiscount(String API_TOKEN, String uKey, int orderId, String discountType, String val) {
        String methodNaction = Constants.SERVICE_ApplyDiscount;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);


        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_discountType = new PropertyInfo();
        O_discountType.namespace = namespace;
        O_discountType.setName("discountType");
        O_discountType.setValue(discountType);
        O_discountType.setType(String.class);
        request.addProperty(O_discountType);

        PropertyInfo O_val = new PropertyInfo();
        O_val.namespace = namespace;
        O_val.setName("val");
        O_val.setValue(val);
        O_val.setType(double.class);
        request.addProperty(O_val);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(int.class);
        request.addProperty(O_orderId);
//        System.out.println("SendKotRE" + request.toString());

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("SendKotResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }


    public static SoapObject CancelOrderItems(String API_TOKEN, String uKey, int orderId) {
        String methodNaction = Constants.SERVICE_CancelOrderItems;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(int.class);
        request.addProperty(O_orderId);
//        System.out.println("GetProdutst" + request.toString());

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("GetProdutstResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject CloseOrder(String API_TOKEN, String uKey, int orderId) {
        String methodNaction = Constants.SERVICE_CloseOrder;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(int.class);
        request.addProperty(O_orderId);
        System.out.println("CloseOrderrequest" + request.toString());

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            System.out.println("CloseOrderResult" + results.toString());
//
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject GetOpenTablesByUser(String API_TOKEN, String uKey, String userId, String orderTypeId, String tableId) {
        String methodNaction = Constants.SERVICE_GetOpenTablesByUser;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_userId = new PropertyInfo();
        O_userId.namespace = namespace;
        O_userId.setName("userId");
        O_userId.setValue(userId);
        O_userId.setType(long.class);
        request.addProperty(O_userId);

        PropertyInfo O_orderTypeId = new PropertyInfo();
        O_orderTypeId.namespace = namespace;
        O_orderTypeId.setName("orderTypeId");
        O_orderTypeId.setValue(orderTypeId);
        O_orderTypeId.setType(int.class);
        request.addProperty(O_orderTypeId);

        PropertyInfo O_tableId = new PropertyInfo();
        O_tableId.namespace = namespace;
        O_tableId.setName("tableId");
        O_tableId.setValue(tableId);
        O_tableId.setType(long.class);
        request.addProperty(O_tableId);
//        System.out.println("GetProdutst" + request.toString());

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("GetProdutstResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject SwapTable(String API_TOKEN, String uKey, String userId, int orderId, String tableId) {
        String methodNaction = Constants.SERVICE_SwapTable;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_userId = new PropertyInfo();
        O_userId.namespace = namespace;
        O_userId.setName("userId");
        O_userId.setValue(userId);
        O_userId.setType(long.class);
        request.addProperty(O_userId);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(int.class);
        request.addProperty(O_orderId);

        PropertyInfo O_tableId = new PropertyInfo();
        O_tableId.namespace = namespace;
        O_tableId.setName("tableId");
        O_tableId.setValue(tableId);
        O_tableId.setType(long.class);
        request.addProperty(O_tableId);
//        System.out.println("GetProdutst" + request.toString());

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
//            System.out.println("GetProdutstResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject AddProductToOrder(String API_TOKEN, String uKey, String muserId, String orderTypeId, int orderId, int prodId, String tableId, String addOns, String custId, String pax) {
        String methodNaction = Constants.SERVICE_AddProductToOrder;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

        PropertyInfo user_name = new PropertyInfo();
        user_name.namespace = namespace;
        user_name.setName("userId");
        user_name.setValue(muserId);
        user_name.setType(String.class);
        request.addProperty(user_name);

        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_orderTypeId = new PropertyInfo();
        O_orderTypeId.namespace = namespace;
        O_orderTypeId.setName("orderTypeId");
        O_orderTypeId.setValue(orderTypeId);
        O_orderTypeId.setType(String.class);
        request.addProperty(O_orderTypeId);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(orderId);
        O_orderId.setType(int.class);
        request.addProperty(O_orderId);

        PropertyInfo O_prodId = new PropertyInfo();
        O_prodId.namespace = namespace;
        O_prodId.setName("prodId");
        O_prodId.setValue(prodId);
        O_prodId.setType(int.class);
        request.addProperty(O_prodId);

        PropertyInfo O_tableId = new PropertyInfo();
        O_tableId.namespace = namespace;
        O_tableId.setName("tableId");
        O_tableId.setValue(tableId);
        O_tableId.setType(String.class);
        request.addProperty(O_tableId);

        PropertyInfo O_addOns = new PropertyInfo();
        O_addOns.namespace = namespace;
        O_addOns.setName("addOns");
        O_addOns.setValue(addOns);
        O_addOns.setType(String.class);
        request.addProperty(O_addOns);

        PropertyInfo O_custId = new PropertyInfo();
        O_custId.namespace = namespace;
        O_custId.setName("custId");
        O_custId.setValue(custId);
        O_custId.setType(String.class);
        request.addProperty(O_custId);

        PropertyInfo O_pax = new PropertyInfo();
        O_pax.namespace = namespace;
        O_pax.setName("pax");
        O_pax.setValue(pax);
        O_pax.setType(String.class);
        request.addProperty(O_pax);

        System.out.println("AddProductToOrderRequest" + request.toString());
        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            System.out.println("AddProductToOrderResults" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject UpdateProductInOrder(String API_TOKEN, String uKey, String detailId, String quty) {
        String methodNaction = Constants.SERVICE_UpdateProductInOrder;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

       *//* PropertyInfo user_name = new PropertyInfo();
        user_name.namespace = namespace;
        user_name.setName("userId");
        user_name.setValue(muserId);
        user_name.setType(String.class);
        request.addProperty(user_name);*//*

        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_detailId = new PropertyInfo();
        O_detailId.namespace = namespace;
        O_detailId.setName("detailId");
        O_detailId.setValue(detailId);
        O_detailId.setType(String.class);
        request.addProperty(O_detailId);

        PropertyInfo O_qty = new PropertyInfo();
        O_qty.namespace = namespace;
        O_qty.setName("qty");
        O_qty.setValue(quty);
        O_qty.setType(int.class);
        request.addProperty(O_qty);

        System.out.println("UpdateProductInOrderResult" + request.toString());
        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            System.out.println("UpdateProductInOrderResult" + request.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject SaveCommentApi(String API_TOKEN, String uKey, String detailId, String comments) {
        String methodNaction = Constants.SERVICE_SaveComment;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

       *//* PropertyInfo user_name = new PropertyInfo();
        user_name.namespace = namespace;
        user_name.setName("userId");
        user_name.setValue(muserId);
        user_name.setType(String.class);
        request.addProperty(user_name);*//*

        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_detailId = new PropertyInfo();
        O_detailId.namespace = namespace;
        O_detailId.setName("detailId");
        O_detailId.setValue(detailId);
        O_detailId.setType(String.class);
        request.addProperty(O_detailId);

        PropertyInfo O_comments = new PropertyInfo();
        O_comments.namespace = namespace;
        O_comments.setName("comm");
        O_comments.setValue(comments);
        O_comments.setType(int.class);
        request.addProperty(O_comments);

        System.out.println("SaveCommentResult" + request.toString());
        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            System.out.println("SaveCommentResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject DeleteProductInOrder(String API_TOKEN, String uKey, String detailId, String deleteOrder, int OrderId) {
        String methodNaction = Constants.SERVICE_DeleteProductInOrder;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("orderId");
        O_orderId.setValue(OrderId);
        O_orderId.setType(int.class);
        request.addProperty(O_orderId);
       *//* PropertyInfo user_name = new PropertyInfo();
        user_name.namespace = namespace;
        user_name.setName("userId");
        user_name.setValue(muserId);
        user_name.setType(String.class);
        request.addProperty(user_name);*//*

        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo O_detailId = new PropertyInfo();
        O_detailId.namespace = namespace;
        O_detailId.setName("detailId");
        O_detailId.setValue(detailId);
        O_detailId.setType(String.class);
        request.addProperty(O_detailId);

        PropertyInfo O_deleteOrder = new PropertyInfo();
        O_deleteOrder.namespace = namespace;
        O_deleteOrder.setName("deleteOrder");
        O_deleteOrder.setValue(deleteOrder);
        O_deleteOrder.setType(boolean.class);
        request.addProperty(O_deleteOrder);

        System.out.println("DeleteProductInOrderResult" + request.toString());
        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            System.out.println("DeleteProductInOrderResult" + results.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject SaveCustomer(String API_TOKEN, String uKey, String muserId, String name, String mobile, String address, String OrderId) {
        String methodNaction = Constants.SERVICE_SaveCustomer;
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

       *//* PropertyInfo user_name = new PropertyInfo();
        user_name.namespace = namespace;
        user_name.setName("userId");
        user_name.setValue(muserId);
        user_name.setType(String.class);
        request.addProperty(user_name);
*//*
        PropertyInfo UKey = new PropertyInfo();
        UKey.namespace = namespace;
        UKey.setName("uKey");
        UKey.setValue(uKey);
        UKey.setType(String.class);
        request.addProperty(UKey);

        PropertyInfo u_name = new PropertyInfo();
        u_name.namespace = namespace;
        u_name.setName("name");
        u_name.setValue(name);
        u_name.setType(String.class);
        request.addProperty(u_name);

        PropertyInfo u_mobile = new PropertyInfo();
        u_mobile.namespace = namespace;
        u_mobile.setName("mobile");
        u_mobile.setValue(mobile);
        u_mobile.setType(String.class);
        request.addProperty(u_mobile);

        PropertyInfo u_address = new PropertyInfo();
        u_address.namespace = namespace;
        u_address.setName("mobile");
        u_address.setValue(mobile);
        u_mobile.setType(String.class);
        request.addProperty(u_mobile);

        PropertyInfo O_orderId = new PropertyInfo();
        O_orderId.namespace = namespace;
        O_orderId.setName("userId");  // this is OrderId only but param name is userId
        O_orderId.setValue(OrderId);
        O_orderId.setType(int.class);
        request.addProperty(O_orderId);

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            System.out.println("GetOrderTypesByUserResult " + results.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    //    Brand_value, Model_value,VersionInfo,"Android","",""
    public static SoapObject OnDonload(String Brand_value, String Model_value, String VersionInfo, String os, String devId, String devToken) {
        String methodNaction = "OnDownload";
        SoapObject request = new SoapObject(namespace, methodNaction);

        PropertyInfo brand = new PropertyInfo();
        brand.namespace = namespace;
        brand.setName("brand");
        brand.setValue(Brand_value);
        brand.setType(String.class);
        request.addProperty(brand);

        PropertyInfo model = new PropertyInfo();
        model.namespace = namespace;
        model.setName("model");
        model.setValue(Model_value);
        model.setType(String.class);
        request.addProperty(model);

        PropertyInfo version = new PropertyInfo();
        version.namespace = namespace;
        version.setName("version");
        version.setValue(VersionInfo);
        version.setType(String.class);
        request.addProperty(version);

        PropertyInfo O_os = new PropertyInfo();
        O_os.namespace = namespace;
        O_os.setName("os");
        O_os.setValue(os);
        O_os.setType(String.class);
        request.addProperty(O_os);

        PropertyInfo O_devId = new PropertyInfo();
        O_devId.namespace = namespace;
        O_devId.setName("devId");
        O_devId.setValue(devId);
        O_devId.setType(String.class);
        request.addProperty(O_devId);

        PropertyInfo O_devToken = new PropertyInfo();
        O_devToken.namespace = namespace;
        O_devToken.setName("devToken");
        O_devToken.setValue(devToken);
        O_devToken.setType(String.class);
        request.addProperty(O_devToken);

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static SoapObject GetVersionInfo(String API_TOKEN, String muserId) {
        String methodNaction = Constants.SERVICE_GetVersionV1;
        SoapObject request = new SoapObject(namespace, methodNaction);


        PropertyInfo user_name = new PropertyInfo();
        user_name.namespace = namespace;
        user_name.setName("userId");
        user_name.setValue(muserId);
        user_name.setType(String.class);
        request.addProperty(user_name);

        PropertyInfo apikey = new PropertyInfo();
        apikey.namespace = namespace;
        apikey.setName(Constants.API_KEY);
        apikey.setValue(API_TOKEN);
        apikey.setType(String.class);
        request.addProperty(apikey);

        try {
            results = callWebService(namespace + methodNaction, request, Constants.SERVICE_URL);
            // System.out.println("GetVersion response " + results.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private static SoapObject callWebService(String actionName,
                                             SoapObject request, String url) throws Exception {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.encodingStyle = SoapSerializationEnvelope.XSD;

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        httpTransportSE.debug = true;
        httpTransportSE.call(actionName, envelope);
        SoapObject results = (SoapObject) envelope.bodyIn;
        return results;
    }*/
}
