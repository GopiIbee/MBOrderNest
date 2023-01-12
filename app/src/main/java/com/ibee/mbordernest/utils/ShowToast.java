/**
 *
 */
package com.ibee.mbordernest.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author chandu
 */
public class ShowToast {
    static Toast toastOb = null;

    public static void toastMessage(Context context, String message) {
        try {
            if (toastOb != null) {
                toastOb.cancel();
            }
            toastOb = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toastOb.setGravity(Gravity.CENTER_HORIZONTAL, 0, 10);
            toastOb.show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void toastMsgNetworkConeection(Context context) {
        try {
            if (toastOb != null) {
                toastOb.cancel();
            }
            toastOb = Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT);
            toastOb.setGravity(Gravity.CENTER_HORIZONTAL, 0, 10);
            toastOb.show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
