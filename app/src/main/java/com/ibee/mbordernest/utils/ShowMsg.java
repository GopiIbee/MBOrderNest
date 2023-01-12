package com.ibee.mbordernest.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class ShowMsg {

    public static void showMsg(String msg, Context context) {
        show(msg, context);
    }

    private static void show(String msg, Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                return ;
            }
        });
        alertDialog.create();
        alertDialog.show();
    }

}
