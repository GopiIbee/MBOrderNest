package com.ibee.mbordernest.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.ibee.mbordernest.R;
import com.ibee.mbordernest.activitys.LoginActivity;
import com.ibee.mbordernest.model.TableNamesModelClass;
import com.ibee.mbordernest.network.CheckNetworkConnection;
import com.ibee.mbordernest.parserclass.ServiceHandler;
import com.ibee.mbordernest.utils.Constants;
import com.ibee.mbordernest.utils.ShowToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class TableNameIdAdapter extends RecyclerView.Adapter<TableNameIdAdapter.ViewHolder> {
    private List<TableNamesModelClass> pressModels;
    Context context;
    String from = "";
    static final String[] numbers = new String[]{
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", ".", "0", "%", "<", "C", "OK"};
    AlertDialog dialogValidatePin;
    private SharedPreferences sharedPreferences;
    String User_key = "";
    String API_TOKEN = "";
    String orderTypeId = "",
            orderTypeName = "",
            table_id = "",
            table_name = "", StorderId = "";


    public TableNameIdAdapter(Context context, List<TableNamesModelClass> pressModels) {
        this.context = context;
        this.pressModels = pressModels;
//        this.from = from;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.table_list, parent, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        User_key = sharedPreferences.getString("StoreKey", "");
        API_TOKEN = sharedPreferences.getString("accesskey", "");

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TableNamesModelClass menuModel = pressModels.get(position);

/*

        holder.table_name.setText(menuModel.getTable_name());

        holder.table_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        if (from.equals("Home")) {
            if (menuModel.getIsOpen().equals("true")) {
                holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.grey_redlight));

                holder.table_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));

            } else if (menuModel.getIsOpen().equals("freeze")) {
                holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.grey_redlight));

                holder.table_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));

            } else {
                holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));


            }
        } else {
            holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("Home")) {
                  */
/*  if (menuModel.getIsOpen().equals("false")) {
                        Intent i = new Intent(context, GuestInfoActivity.class);//GuestInfoActivity
                        i.putExtra("table_id", menuModel.getId());
                        i.putExtra("table_name", menuModel.getTable_name());
                        i.putExtra("order_type_id", menuModel.getOrderType());
                        i.putExtra("order_type_name", menuModel.getOrderTypeName());
                        i.putExtra("orderId", menuModel.getOrderId());
                        context.startActivity(i);
                    } else*//*
 if (menuModel.getIsOpen().equals("freeze")) {
                        orderTypeId = menuModel.getOrderType();
                        orderTypeName = menuModel.getOrderTypeName();
                        table_id = menuModel.getId();
                        table_name = menuModel.getTable_name();
                        StorderId = String.valueOf(menuModel.getOrderId());


//                        openDailogEdit("Open freeze table", menuModel.getOrderId() + "");

                    }
                } */
/*else {
                    ((OrderAndBill) context).SwapTableMethod(menuModel.getId());

                }*//*

            }
        });
*/

    }
/*
    private void openDailogEdit(final String reason, final String orderId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(builder.getContext());
        View dialogView = (View) inflater.inflate(R.layout.dailog_table, null);

        GridView gridView = (GridView) dialogView.findViewById(R.id.grid_view);
        final TextView pin_tv = (TextView) dialogView.findViewById(R.id.pin_tv);
        final TextView pin_tv_dummy = (TextView) dialogView.findViewById(R.id.pin_tv_dummy);
        pin_tv_dummy.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_list_item2, numbers);
        gridView.setAdapter(adapter);
        builder.setView(dialogView);


        dialogValidatePin = builder.create();
        dialogValidatePin.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialogValidatePin.getWindow().getAttributes();

        wmlp.gravity = Gravity.RIGHT;
        wmlp.x = 10;   //x position
        wmlp.y = 10;   //y position

        dialogValidatePin.show();
        dialogValidatePin.getWindow().setLayout(500, ViewGroup.LayoutParams.WRAP_CONTENT);


        final String[] pinVery = {""};
        gridView.setOnItemClickListener((parent, v, position, id) -> {

            CharSequence pinsingle = ((TextView) v).getText();

            if (pinsingle.equals("C")) {
                pinVery[0] = "";
                pin_tv.setText("");
                pin_tv_dummy.setText("****");

            } else if (pinsingle.equals("<")) {

                if (pinVery[0].length() == 1) {
                    pinVery[0] = (pinVery[0].substring(0, pinVery[0].length() - 1));
                    pin_tv.setText("");
                    pin_tv_dummy.setText("****");

                } else if (pinVery[0].length() == 2) {
                    pinVery[0] = (pinVery[0].substring(0, pinVery[0].length() - 1));

                    pin_tv.setText("*");
                    pin_tv_dummy.setText("***");

                } else if (pinVery[0].length() == 3) {
                    pinVery[0] = (pinVery[0].substring(0, pinVery[0].length() - 1));
                    pin_tv.setText("**");
                    pin_tv_dummy.setText("**");

                } else if (pinVery[0].length() == 4) {
                    pinVery[0] = (pinVery[0].substring(0, pinVery[0].length() - 1));

                    pin_tv.setText("***");
                    pin_tv_dummy.setText("*");

                } else {
                    pinVery[0] = "";
                    pin_tv.setText("");
                    pin_tv_dummy.setText("****");

                }

            } else if (pinsingle.equals("OK")) {
                if (!pinVery[0].isEmpty()) {
                    ValidataApiMethod(pinVery[0], "OPENTABLE", reason, orderId);
                }


            } else {
                if (pinVery[0].length() != 4) {
                    if (pinVery[0].isEmpty()) {
                        pinVery[0] = String.valueOf(pinsingle);
                        pin_tv.setText("*");
                        pin_tv_dummy.setText("***");

                    } else {
                        pinVery[0] = pinVery[0] + pinsingle;

                        if (pinVery[0].length() == 2) {
                            pin_tv.setText("**");
                            pin_tv_dummy.setText("**");

                        } else if (pinVery[0].length() == 3) {
                            pin_tv.setText("***");
                            pin_tv_dummy.setText("*");

                        } else {
                            pin_tv.setText("****");
                            pin_tv_dummy.setText("");

                        }
                    }
                } else {
                    ShowToast.toastMessage(context, "Only 4 Digits pin is allowed");
                }

            }


        });

    }*/

   /* private void ValidataApiMethod(String pinNumber, String action, String reason, String orderIId) {
        if (CheckNetworkConnection.isNetWorkConnectionAvailable(context)) {
            new ValidataApiCall().execute(User_key, pinNumber, action, reason, orderIId);
        } else {
            ShowToast.toastMsgNetworkConeection(context);
        }
    }
*/
   /* public class ValidataApiCall extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {
            JSONObject json = new JSONObject();

            try {
                json.put("uKey", arg0[0]);
                json.put("userId",sharedPreferences.getString("User_Id",""));

                json.put("pin", arg0[1]);
                json.put("action", arg0[2]);
                json.put("reason", arg0[3]);
                json.put("orderId", Integer.parseInt(arg0[4]));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_ValidatePIN, ServiceHandler.POST, json, "");


            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    parseValidataApiCall(result);
                } else {
                    ShowToast.toastMessage(context, "No Data");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(context, "Error");
            }
        }
    }

    public void parseValidataApiCall(String soapObject) throws ParseException {
        try {
            if (soapObject != null) {
                JSONObject jsonObj = new JSONObject(soapObject);
                if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                    if (jsonObj.has("data")) {
                        if (jsonObj.getString("data").equals("true")) {
                            dialogValidatePin.dismiss();
                            Intent i = new Intent(context, LoginActivity.class);//OrderAndBill
                            i.putExtra("orderTypeId", orderTypeId);
                            i.putExtra("orderTypeName", orderTypeName);
                            i.putExtra("table_id", table_id);
                            i.putExtra("table_name", table_name);
                            i.putExtra("customer_id", "0");
                            i.putExtra("orderId", Integer.parseInt(StorderId));
                            i.putExtra("StatusOfOrder", false);

                            context.startActivity(i);
                        } else {
                            ShowToast.toastMessage(context, "Invalid pin");
                        }
                    }
                } else {
                    if (jsonObj.has("message")) {
                        String message = jsonObj.getString("message");
                        ShowToast.toastMessage(context, message);

                    } else {
                        ShowToast.toastMessage(context, "No Data");
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            ShowToast.toastMessage(context, "Error");
        }
    }
*/

    @Override
    public int getItemCount() {
        return pressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView table_name;


        public ViewHolder(View itemView) {
            super(itemView);
            table_name = (TextView) itemView.findViewById(R.id.list1);

        }
    }

}



