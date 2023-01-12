package com.ibee.mbordernest.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.ibee.mbordernest.R;
import com.ibee.mbordernest.activitys.HomeScreenActivity;
import com.ibee.mbordernest.activitys.OrderAndBill;
import com.ibee.mbordernest.model.CartItemListModel;
import com.ibee.mbordernest.network.CheckNetworkConnection;
import com.ibee.mbordernest.parserclass.ServiceHandler;
import com.ibee.mbordernest.utils.Constants;
import com.ibee.mbordernest.utils.ShowToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartItemListAdapter extends RecyclerView.Adapter<CartItemListAdapter.ViewHolder> {
    private List<CartItemListModel> pressModels;

    Context context;
    private ProgressDialog mProgressDialog;
    private SharedPreferences sharedPreferences;
    String Login_user_id = "", User_key = "";

    int checkDeleteOrHere = 0;
    int OrderId = 0;
    String API_TOKEN = "";
    int check = 0;
    int sentToKot = 0;
    String Currency;

    public CartItemListAdapter(Context context, List<CartItemListModel> pressModels) {
        this.context = context;
        this.pressModels = pressModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.cart_list_items, parent, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Currency = sharedPreferences.getString("Currency", "");

        Login_user_id = sharedPreferences.getString("User_Id", "");
        User_key = sharedPreferences.getString("StoreKey", "");

        API_TOKEN = sharedPreferences.getString("accesskey", "");

        getOrderId();
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final CartItemListModel menuModel = pressModels.get(position);


        holder.name.setText(menuModel.getName());
        holder.quty.setText(menuModel.getQuantity() + "");
        holder.price.setText(Currency + " " + new DecimalFormat("##.##").format(menuModel.getPrice()));
        if (!menuModel.getComment().isEmpty() && !menuModel.getComment().equals("null")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(3, 3, 3, 3);
                holder.bt_cooment.setTextSize(20);
                holder.bt_cooment.setBackground(context.getResources().getDrawable(R.drawable.rectangle_shape_commet));

                holder.bt_cooment.setPadding(25, 3, 25, 3);
                holder.bt_cooment.setLayoutParams(params);
            }
        }


        if (menuModel.getSentToKOT() || sentToKot==1) {
            holder.delete.setVisibility(View.GONE);

            if (check == 1) {
                holder.checkbox.setVisibility(View.VISIBLE);
                holder.checkbox.setChecked(false);
            }else {
                holder.checkbox.setVisibility(View.GONE);
                holder.checkbox.setChecked(false);
                ((OrderAndBill) context).UpdateBillPrintRemove(0);

            }
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.checkbox.isChecked()) {

                        ((OrderAndBill) context).UpdateBillPrint(
                                pressModels.get(position).getDetailId(),
                                pressModels.get(position).getpId(),
                                pressModels.get(position).getName(),
                                pressModels.get(position).getQuantity(),
                                pressModels.get(position).getPrice(),
                                pressModels.get(position).getSentToKOT(),
                                pressModels.get(position).getComment());


                    } else {
                        ((OrderAndBill) context).UpdateBillPrintRemove(pressModels.get(position).getDetailId());

                    }
                }
            });
            holder.ll1.setBackgroundColor(context.getResources().getColor(R.color.icon_color_blue3));
            holder.quty.setEnabled(false);
            holder.ll1.setOnClickListener(v -> ShowToast.toastMessage(context, "Click \"Edit\" to update the order"));
        } else {

           /* holder.ll1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int detailId = menuModel.getDetailId();

                    openDialogQuntity(detailId, menuModel.getName(), menuModel.getQuantity() + "");

                    return false;
                }
            });*/


            holder.minis.setOnClickListener(v -> {

                if (CheckNetworkConnection.isNetWorkConnectionAvailable(context)) {
                    int detailId = menuModel.getDetailId();
                    boolean checkkot = menuModel.getSentToKOT();

                    int q = (menuModel.getQuantity()) - 1;
                    if (q == 0) {
                        callDelete(detailId, checkkot);
                    } else {
                        QuantityUpdateProduct(q, detailId);

                    }
                } else {
                    ShowToast.toastMsgNetworkConeection(context);
                }
            });

            holder.plus.setOnClickListener(v -> {
                if (CheckNetworkConnection.isNetWorkConnectionAvailable(context)) {

                    int detailId = menuModel.getDetailId();


                    int q = (menuModel.getQuantity()) + 1;
                    QuantityUpdateProduct(q, detailId);
                } else {
                    ShowToast.toastMsgNetworkConeection(context);
                }
            });

            holder.delete.setOnClickListener(v -> {
                if (CheckNetworkConnection.isNetWorkConnectionAvailable(context)) {

                    int detailId = menuModel.getDetailId();
                    boolean checkkot = menuModel.getSentToKOT();
                    callDelete(detailId, checkkot);
                } else {
                    ShowToast.toastMsgNetworkConeection(context);
                }

            });

            holder.bt_cooment.setOnClickListener(v -> {
                int detailId = menuModel.getDetailId();
                String comment = "";
                if (!menuModel.getComment().equals("null")) {

                    comment = menuModel.getComment();

                }
                openDialogComment(detailId, comment);
            });
        }

    }

    private void openDialogQuntity(final int detailId, String itemName, String oldQunty) {
        final EditText quntity;
        TextView item_name;
        Button cancle, ok;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(builder.getContext());
        View dialogView = (View) inflater.inflate(R.layout.add_quntity_update_dialog, null);

        quntity = (EditText) dialogView.findViewById(R.id.quntity);
        cancle = (Button) dialogView.findViewById(R.id.cancle);
        item_name = (TextView) dialogView.findViewById(R.id.item_name);

        ok = (Button) dialogView.findViewById(R.id.ok);

        item_name.setText(itemName);
        quntity.setText(oldQunty);
        quntity.setSelection(oldQunty.length());

        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!quntity.getText().toString().equals("") && Integer.parseInt(quntity.getText().toString().trim()) > 0) {
                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(context)) {
                        QuantityUpdateProduct(Integer.parseInt(quntity.getText().toString()), detailId);
                        dialog.dismiss();
                    } else {
                        ShowToast.toastMsgNetworkConeection(context);
                    }
                } else {
                    ShowToast.toastMessage(context, "Enter valid item quantity");

                }

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(1200, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);

//        comment.performClick();
    }

    private void openDialogComment(final int detailId, String St_comment) {
        final EditText comment;
        Button procced;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(builder.getContext());
        View dialogView = (View) inflater.inflate(R.layout.add_comment_dialog, null);

        comment = (EditText) dialogView.findViewById(R.id.comment);
        procced = (Button) dialogView.findViewById(R.id.procced);


        comment.setText(St_comment);

        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        procced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckNetworkConnection.isNetWorkConnectionAvailable(context)) {
                    AddComment(comment.getText().toString(), detailId + "");
                    dialog.dismiss();
                } else {
                    ShowToast.toastMsgNetworkConeection(context);
                }
            }
        });
        dialog.getWindow().setLayout(1200, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);

//        comment.performClick();
    }

    private void AddComment(String commnet, String detailId) {
        new SaveComment().execute(commnet, detailId);

    }

    public void checkBoxsChange(int ischeck,int isFromSentToKot) {
        check = ischeck;
        sentToKot=0;
        sentToKot=isFromSentToKot;

        notifyDataSetChanged();
    }

    public class SaveComment extends AsyncTask<String, String, String> {
        String response = null;


        @Override
        protected void onPreExecute() {
            showProgress("Saving comment...");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();

            try {
                json.put("uKey", User_key);
                json.put("detailId", arg0[1]);
                json.put("comm", arg0[0]);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_SaveComment, ServiceHandler.POST, json, "");


            } catch (IOException e) {
                e.printStackTrace();
                dismissProgress();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    parseSaveComment(result);
                } else {
                    ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
                }


            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
            }
            dismissProgress();

        }
    }

    public void parseSaveComment(String soapObject) throws ParseException {
        try {
            if (soapObject != null) {
                JSONObject jsonObj = new JSONObject(soapObject);
                if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                    if (jsonObj.has("data")) {
                        JSONObject jsonObject = jsonObj.getJSONObject("data");
                        if (jsonObject.has("table")) {

                            JSONArray table = jsonObject.getJSONArray("table");


                            ((OrderAndBill) context).CallToCartItems(table);

                        }

                    } else {
                        ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));

                    }
                } else {
                    if (jsonObj.has("message")) {
                        String message = jsonObj.getString("message");
                        ShowToast.toastMessage(context, message);

                    } else {
                        ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
        }
    }

    public void callDeleteProdut(int orderId) {
        OrderId = orderId;
        checkDeleteOrHere = 1;
        new DeleteProductToOrderMethod().execute("0", "true");

    }

    private void callDelete(final int detailId, final Boolean checkkot) {
        if (pressModels.size() == 1) {
            final AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
            alertbox.setMessage("Choose to delete complete order or current item!");
            alertbox.setTitle("Alert ");

            alertbox.setPositiveButton("Delete complete order", (arg0, arg1) -> {
                checkDeleteOrHere = 1;
                if (checkkot) {
//                    ((OrderAndBill) context).SendToKotMethod(User_key, "false");
                }

                new DeleteProductToOrderMethod().execute(String.valueOf(detailId), "true");

            });
            alertbox.setNeutralButton("Delete item", (dialog, which) -> {
                checkDeleteOrHere = 0;
                if (checkkot) {
//                    ((OrderAndBill) context).SendToKotMethod(User_key, "false");
                }
                new DeleteProductToOrderMethod().execute(String.valueOf(detailId), "false");
            });
            alertbox.show();
        } else {
            checkDeleteOrHere = 0;

            new DeleteProductToOrderMethod().execute(String.valueOf(detailId), "false");

        }

    }


    public void getOrderId() {
        OrderId = ((OrderAndBill) context).updatedOrderId();
    }

    public class DeleteProductToOrderMethod extends AsyncTask<String, String, String> {
        String response = null;


        @Override
        protected void onPreExecute() {
            showProgress("Deleting item...");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();

            try {
                json.put("uKey", User_key);
                json.put("detailId", arg0[0]);
                json.put("deleteOrder", arg0[1]);
                json.put("orderId", OrderId);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_DeleteProductInOrder, ServiceHandler.POST, json, "");


            } catch (IOException e) {
                e.printStackTrace();
                dismissProgress();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    parseDeleteData(result);
                } else {
                    ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
            }
            dismissProgress();

        }
    }

    public void parseDeleteData(String soapObject) throws ParseException {
        // TODO Auto-generated method stub
        try {
            if (soapObject != null) {
                JSONObject jsonObj = new JSONObject(soapObject);
                if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                    if (jsonObj.has("data")) {
                        JSONObject jsonObject = jsonObj.getJSONObject("data");

///sdf


                        if (jsonObject.has("table")) {

                            JSONArray table = jsonObject.getJSONArray("table");

                            if (checkDeleteOrHere == 1) {
                                Intent i = new Intent(context, HomeScreenActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(i);
                            } else {
                                if (table.length() == 0) {
//                                    ((OrderAndBill) context).callUpdateBottomLL2();

                                }
                                ((OrderAndBill) context).CallToCartItems(table);


                            }


                        }
                        if (jsonObject.has("table1")) {
                            JSONArray table1 = jsonObject.getJSONArray("table1");

                            ((OrderAndBill) context).updateSubTotal(table1);


                        }


                    } else {
                        ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));

                    }
                } else {
                    if (jsonObj.has("message")) {
                        String message = jsonObj.getString("message");
                        ShowToast.toastMessage(context, message);

                    } else {
                        ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
                    }
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
        }
    }

    @Override
    public int getItemCount() {
        return pressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, bt_cooment;
        ImageView image, delete, minis, plus;
        LinearLayout ll1;
        CheckBox checkbox;
        TextView quty;

        public ViewHolder(View itemView) {
            super(itemView);

            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);

            ll1 = (LinearLayout) itemView.findViewById(R.id.ll1);
            price = (TextView) itemView.findViewById(R.id.price);
            bt_cooment = (TextView) itemView.findViewById(R.id.bt_cooment);
            name = (TextView) itemView.findViewById(R.id.name);
            quty = (TextView) itemView.findViewById(R.id.quty);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            minis = (ImageView) itemView.findViewById(R.id.minis);
            plus = (ImageView) itemView.findViewById(R.id.plus);

        }
    }


    private void QuantityUpdateProduct(int q, int detailId) {
        if (CheckNetworkConnection.isNetWorkConnectionAvailable(context)) {

            new UpdateProductToOrderMethod().execute(String.valueOf(q), String.valueOf(detailId));
        } else {
            ShowToast.toastMsgNetworkConeection(context);
        }

    }

    public class UpdateProductToOrderMethod extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Updating ...");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();

            try {
                json.put("uKey", User_key);
                json.put("detailId", arg0[1]);
                json.put("qty", arg0[0]);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_UpdateProductInOrder, ServiceHandler.POST, json, "");


            } catch (IOException e) {
                e.printStackTrace();
                dismissProgress();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    parseUpdateData(result);
                } else {
                    ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
                }


            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
            }
            dismissProgress();

        }

    }

    public void parseUpdateData(String soapObject) throws ParseException {
        // TODO Auto-generated method stub
        try {
            if (soapObject != null) {
                JSONObject jsonObj = new JSONObject(soapObject);
                if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                    if (jsonObj.has("data")) {
                        JSONObject jsonObject = jsonObj.getJSONObject("data");
                        pressModels = new ArrayList<>();
                        int bottomlayout = 0;

                        if (jsonObject.has("table")) {
                            JSONArray table = jsonObject.getJSONArray("table");

                            ((OrderAndBill) context).CallToCartItems(table);

                        }
                        if (jsonObject.has("table1")) {
                            JSONArray table1 = jsonObject.getJSONArray("table1");
                            ((OrderAndBill) context).updateSubTotal(table1);
                        }


                    } else {
                        ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));

                    }
                } else {
                    if (jsonObj.has("message")) {
                        String message = jsonObj.getString("message");
                        ShowToast.toastMessage(context, message);

                    } else {
                        ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
                    }
                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
        }
    }

    protected void showProgress(String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            dismissProgress();

        mProgressDialog = ProgressDialog.show(context, "", msg);
    }

    protected void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}


