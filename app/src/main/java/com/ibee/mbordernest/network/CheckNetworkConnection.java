/**
 *
 */
package com.ibee.mbordernest.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class CheckNetworkConnection {

    public static boolean isNetWorkConnectionAvailable(Context context) {

        ConnectivityManager connec =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connec.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static int isNetWorkMobileORWifi(Context context){
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        int type = networkInfo.getType();
        String typeName = networkInfo.getTypeName();
        boolean connected = networkInfo.isConnected();
        return type;
    }

}
