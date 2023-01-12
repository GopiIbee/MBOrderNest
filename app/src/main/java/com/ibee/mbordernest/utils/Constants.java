package com.ibee.mbordernest.utils;


import android.util.Base64;
import android.util.Log;

import com.android.volley.BuildConfig;
import com.android.volley.toolbox.HurlStack;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.tls.HandshakeCertificates;
import okhttp3.tls.HeldCertificate;


public class Constants {

    //    public static String API_KEY = "aKey";
//    public static String SERVICE_URL = "https://api.ordernest.com/service.asmx";
    public static String BASE_URL = "https://wsmb.ordernest.com/";

    public static String SERVICE_GetVersionV1 = "getnewversion/";
    public static String SERVICE_ON_LOGIN = "onlogin";
    public static String SERVICE_GetOrderTypesByUser = "getordertypesbyuser";
    public static String SERVICE_GetCategoriesAndProducts = "getcategoriesandproducts";
    public static String SERVICE_AddProductToOrder = "addproducttoorder";
    public static String SERVICE_SaveCustomer = "savecustomer";
    public static String SERVICE_UpdateProductInOrder = "updateproductinorder";
    public static String SERVICE_SaveComment = "savecomment";
    public static String SERVICE_DeleteProductInOrder = "deleteproductinorder";
    public static String SERVICE_CancelOrNCProductInOrder = "cancelorncproductinorder";
    public static String SERVICE_GetOpenTablesByUser = "getopentablesbyuser";
    public static String SERVICE_SwapTable = "swaptable";
    public static String SERVICE_CancelOrderItems = "cancelorderitems";
    public static String SERVICE_CloseOrder = "closeorder";
    public static String SERVICE_GetOrderPrintDetails = "getorderprintdetails";
    public static String SERVICE_SendOrderToKot = "sendordertokot";
    public static String SERVICE_CheckeWardsBalance = "checkewardsbalance";
    public static String SERVICE_ValidatePIN = "validatepin";
    public static String SERVICE_ApplyDiscount = "applydiscount";
    public static String SERVICE_PayOrder = "payorder";
    public static String SERVICE_RedeemeWardsPoints = "redeemewardspoints";
    public static String SERVICE_RedeemeWardsCoupon = "redeemewardscoupon";
    public static String SERVICE_ApplyCoupon = "applycoupon";
    public static String SERVICE_SendBillToCustomer = "sendbilltocustomer";
    public static String SERVICE_GetWalletSettings = "getwalletsettings";

    public static String SERVICE_DoRechargeV1 = "dorechargev1";
    public static String SERVICE_InitRecharge = "initrecharge";
    public static String SERVICE_SplitTable = "splittable";
    public static String SERVICE_MoveItems = "moveitems";
    public static String SERVICE_OnDownload = "ondownload";

    //    public static String SERVICE_DoRecharge = "DoRecharge";
    public static String SHARED_PREFERENCES_KEY_PLAYSTORE_LINK = "https://play.google.com/store/apps/details?id=com.ibee.ordernest";//com.
    static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDBsuR5+n1WS9CDkAUKiU0Yk2lucKdROvlAbUPKG4RZ7WZQSkoFDnqWwWBY+6S+jxj2o6JOTvtnFKUq82bfSK35qImnLaGZHGjynBSuo/tC14YehkpqUObKJpeMT/207pmVEzqmfmpl+MGPCxeS5CvwBjUuwHSkz9AWBmCph131kjtmet9JqD8x71fV5DdIEEzxiS9XNTEFlkHrsnC2TgsokkZq3OhhvENvUYvhEd0fAMkUaY8S0MILFOEs1e6RxzZilEuZyJ44AjDhAdr7G5nAESQTuRM2xQqVmFa50RJ2AXsLNrVVsjS8tfXqXbEyP3+rZVYqrfLN1KQnBSxC//sLAgMBAAECggEAWk3myx+HM6Zg7hVE5GaitOR08PJAb1SJRAos4pr1l19gpeocnELl0EcTfijD47ufLc2YK94SwzoSzvVvboXZ5l023+QcRN/D5efylIEK8Vy9wJlnNC3Hi1N2sAnTBHmAzMmMPHcPb78Lrn7DXM5X1a1vOf6r4vKn1rzeb+sWWRgl7jeKPQaC6041J/2WJAxrjsfE8UVBA8kXV204d3TJsP6g1FkhU3x5OG18IsYMihiiSYdLXgA2qyv8Hrrn/YaTb2ATQpz6GMNlT60ZRblXI2lG51TckIzgQcYe/jUgqS7O7kt6jI4lzMEiI4nxcKZ5iTsTNyvSgW8BIux5YkBcAQKBgQDsNYt8eKGOInIP008HkU3HGRieCIO+0PyRRQSpe84N4VMi/TB79tM+v4paPYeK8G/RZulbYX9hoyBCOP4neTR3GBkTLdkTh1BklyZGhcLJaQmL5JCbE15Yb7SQQl879y8aC0QQceS7y52pKPEu/CwSYv5L1XKUfABZs1Dvy8YeCwKBgQDR7YrBTkNnS/k2u1MpbtmVhSl4FD4JafRWTwAXMDNVDe8sp0kBc33ZmKWxBmgGVme1QDJsltybUBZ59p2w8HfteWfX/q06PbGpz1egKi+ImJSWSRmoIskr2ahywvOhypXq/HnEaO7Q+gTJdQbrDmD2AeZXWNe/3qCj61qVH0C3AQKBgAwNb2CahyPJfihwj8fDEkTmcusdZBuIblq7OtGrD4iFxsgyQxcQMRcyjsyr9w6P1iTJK38OKoHA4RR/8/fvp02sXRsta7YjYWbAj6Npj9bXFTWeJALFqjq4+G2j9VgJ9M1PZdmw9grfCNxoymWrWFRX2MHl0kLhUONC0w5pR2KhAoGAONjdkTkzUX6mTubtPzQQYcf8kdLRddUmYFTmTmE0FV0/nlTylR0EQG9wtv5oHwc48EEhJny34qsOWxqoWyjTIRCWSUxi9nmMadKHBQ1Hb/L5kbTb6Mb6uRAo6GLYfMCSVl7mToSn3xlFQJSbOf3bzeJ30MFzMzcDQ0BReD5NVQECgYEAqhu2yzSU5lbfnTLaQ9OomGOARrIQP4T9UoSbPrta8NT6lkBUm5lGkSNc/skBs+HUJRWLvCR5CpJBhz8nXCCVfyXrgRdhctA/gRV20nw/li2jdV0gJvZ1tXtTmtK7ADhwPpzqCQQTXWQqNo8MgZd357TSp0k/LylQJJqiwY0C+fg=";
    static String Audience = "OrderNestClients";
    static String Issuer = "omt:client";

    public static final String mtls_certificate_key = "MIIEFTCCAv2gAwIBAgIUEZujZVlrYL4Q2dmHkO7TRHEb0agwDQYJKoZIhvcNAQELBQAwgagxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMRYwFAYDVQQHEw1TYW4gRnJhbmNpc2NvMRkwFwYDVQQKExBDbG91ZGZsYXJlLCBJbmMuMRswGQYDVQQLExJ3d3cuY2xvdWRmbGFyZS5jb20xNDAyBgNVBAMTK01hbmFnZWQgQ0EgYzhkM2MxMDU2NGFkNjFjZGZjZWM4YzYxNWI3NjczMjgwHhcNMjIxMTIxMDgzNDAwWhcNMzIxMTE4MDgzNDAwWjAiMQswCQYDVQQGEwJVUzETMBEGA1UEAxMKQ2xvdWRmbGFyZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMZsJqTVLlySY/0BEJ47DM6nHcOhLOk75oSdHpb+ttB8m+ivRaQwB+XRZbfje4eq5QdOCpgz/DoEcwyHtRzOz0FqSpg2HVX4zPKODoyp5fBEwE8hT9thgHzdtmJyNbfGSpljJxFBqoatqp6VSggVaw4/5U6pRi7C7thEk0ilKbJvPvUeAcRXXIxvt/yIj4JgACERfkwGks4DOOg5q5IQthGyOFulAgO+x2oYPojf/mpvB+URc73cHyvjy88DiiJxSO9k8EipAmcadHWavPV0s7tDOALWl/rhbNfIzwxPZ8uc9UQ+SnkLsbkEXeRf0ejBafckNywMEQUL7R9qJVKYe/0CAwEAAaOBuzCBuDATBgNVHSUEDDAKBggrBgEFBQcDAjAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBShCnXDSBCynat6fs4fMvbVaXm+jTAfBgNVHSMEGDAWgBRGf8y0ChgUJxZxnHzjGJ+G42Gc9TBTBgNVHR8ETDBKMEigRqBEhkJodHRwOi8vY3JsLmNsb3VkZmxhcmUuY29tLzUzOGFlMjc3LTIwN2ItNDNhZC1iZTIxLTdmYjA4Y2IzZDc1Yy5jcmwwDQYJKoZIhvcNAQELBQADggEBAFafF5zeC4VPu08CDf1AbQuzhiEf4t6U5IenmDUyRyzUeSgSoNJwyIUPe3doS3AfU8ab91Z9Znws86q2XCIAxGlGwqB8bgNcuo+D2/wtBPFKa8cJ7EMqPC6YUJQ6wU8DcLuBCxoVboanSXAfRrZdmjYFiY4f5L2uvxj1Ng1+m8qW+ub3iB9lzdoV9rZ4i1Ty0nBZH4cZsSXFjWljlOdGonJAqSOXWeQxKf54vW/G1ru9DPGNEBrxgYuoTVAa5UcpxfgWU6JmD65fQYv9w9wC814nq9ui5V+6Nm9FtUUkwxwOJidYfZIzhxYrhW+rQkqjdE67+VyLLp780Y0B1FuuVeU=";
    // Field from default config.
    public static final String mtls_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDGbCak1S5ckmP9ARCeOwzOpx3DoSzpO+aEnR6W/rbQfJvor0WkMAfl0WW343uHquUHTgqYM/w6BHMMh7Uczs9BakqYNh1V+Mzyjg6MqeXwRMBPIU/bYYB83bZicjW3xkqZYycRQaqGraqelUoIFWsOP+VOqUYuwu7YRJNIpSmybz71HgHEV1yMb7f8iI+CYAAhEX5MBpLOAzjoOauSELYRsjhbpQIDvsdqGD6I3/5qbwflEXO93B8r48vPA4oicUjvZPBIqQJnGnR1mrz1dLO7QzgC1pf64WzXyM8MT2fLnPVEPkp5C7G5BF3kX9HowWn3JDcsDBEFC+0faiVSmHv9AgMBAAECggEARjCVzvM2bjq+sg4BYefDlQqaxcwrBepRlhG6c3FxG/kocbwmp3jyIkLbd5w543j5gYcCymqBS8itJf6Pe7xQpnRCJOvnnrTfUuIblXi4RTEc0iEXjaRE/mazkMmT5Ql49B+V/026U9/1HhnzUT66WxUROa05jWsZcFl5vFXolRyTUBzeQDQfJ8s3GqDMYEHbfhSOMX0YpMPTe3p7j2o7p28YElKMc5htFudGyFKnd8dvDJ7NcG/tVrRYinQ+3w1V3P3DvtIZz+VYZmPw1S2azYHfrCtIXSz02dvMv/wkDzuEqUM1eZLfYIXkJSDyu2XnIEXlRYm5PDcZ15+LmP4LJQKBgQDx1zGrUeFgZyVHBcBmsU57HwgdZ9jHSyWWLEnYNrL9L+uXp6YZzjc/mJRsSflpdsRs9NNGlMrMUJD8NBYnbrPsgUlC5e47QvLlakGWvFyVtc/HgqZbhA3923rET5yqr03eQghtje9OZQW6a6yNV51BVbMIZkgCCEvqFl/fJ3HipwKBgQDSCjAoO6+DRkr+mQgQEdSfXgVP0asX9eFJvRi/vEGEyWhfsN0kMNZxuskHT0h3LR89IZUuISg6epJp2Z5LiBBkH4iEC/3rua4nR7mrhEGVFMJvoL4Ppfli9lWtOyA23KRiRvlUmagy1fZ7VN23j30n9IEOUEixgzkJr5zzPnI0uwKBgQCnbdBZR2q9Dyvjq7u0XPR0kNtRYF/YOdJf0XGeWuA4IUD90XdRTk85nCzFe8FMnokG1cifiMuOAAkgUf80dZPjHO7XfdAc/xCie5nE/csrap/bBDygByCYq6am2mcgVBWY1iZ3klgm8hGxKnJfuFSKswYrvoQDxDKqcasWF3zk6QKBgQCoIRejXQOFZYMqgXz+wAce+rftyLvYFi4cRI3HDP61ODOgRYb/LUi7CPVaMJClNS1IVdWAc3IsPWlt6ZDYjTXxr3wrlx3Oz3ckYjmxndCRcWiiylzZUdZq7Rxv6mBDCiNbt8xleAo3hyYiWzCPewCINyQHPSSzU4MYycYsv9PTeQKBgQCt70Vm7gM9xd+JZHcHpcuNv1w/HBA1xt1g/3UuTXAiczmPDlDO/pipE8WBL5fpCayR7u8QvCWjM8eFF0GrfkbOn+6v9tfcdzA79B6bbA4m3PhQk3BK4iFw99XfxPpmHqSkZEpLRkGiZY1VviTNL+6NaMuPSMjKvqOFhNPHvf4QKQ==";

    /*
    public static RSAPrivateKey stringToPrivate() {

        try {
            // Read in the key into a String
            StringBuilder pkcs8Lines = new StringBuilder();
            BufferedReader rdr = new BufferedReader(new StringReader(mtls_private_key));
            String line;
            while ((line = rdr.readLine()) != null) {
                pkcs8Lines.append(line);
            }

            // Remove the "BEGIN" and "END" lines, as well as any whitespace

            String pkcs8Pem = pkcs8Lines.toString();
            pkcs8Pem = pkcs8Pem.replace("-----BEGIN PRIVATE KEY-----", "");
            pkcs8Pem = pkcs8Pem.replace("-----END PRIVATE KEY-----", "");
            pkcs8Pem = pkcs8Pem.replaceAll("\\s+", "");

            // Base64 decode the result
            byte[] pkcs8EncodedBytes = Base64.decode(pkcs8Pem, Base64.DEFAULT);

            // extract the private key
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
            return privKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
*/


    public static class CustomHurlStack extends HurlStack {
        @Override
        protected HttpURLConnection createConnection(URL url) throws IOException {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
            try {
                httpsURLConnection.setSSLSocketFactory(getSSLSocketFactoryForMTLS());
                Log.i("SSL", "SUCCESS");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return httpsURLConnection;
        }
    }

    private static SSLSocketFactory getSSLSocketFactoryForMTLS() {
        PrivateKey privateKey = getMTLSPrivateKey();
        X509Certificate certificate = getMTLSCertificateKey();
        if (privateKey != null && certificate != null) {

            KeyPair keypair = new KeyPair(certificate.getPublicKey(), privateKey);

            HandshakeCertificates handshakeCertificates = new HandshakeCertificates.Builder()
                    .addPlatformTrustedCertificates()
                    .heldCertificate(new HeldCertificate(keypair, certificate))
                    .build();

            SSLSocketFactory sslSocketFactory = handshakeCertificates.sslSocketFactory();
            return sslSocketFactory;
        } else {
            Log.e("private key", "null");
        }
        return null;
    }

    private static PrivateKey getMTLSPrivateKey() {
        try {
            byte[] encoded = Base64.decode(Constants.mtls_private_key, Base64.DEFAULT);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static X509Certificate getMTLSCertificateKey() {
        byte[] encoded = Base64.decode(Constants.mtls_certificate_key, Base64.DEFAULT);
        InputStream pubCertInputStream = new ByteArrayInputStream(encoded);
        CertificateFactory certificateFactory = null;
        X509Certificate certificate = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            certificate = (X509Certificate) certificateFactory.generateCertificate(pubCertInputStream);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return certificate;
    }

}
