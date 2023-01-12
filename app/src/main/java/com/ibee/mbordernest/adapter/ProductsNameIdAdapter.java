package com.ibee.mbordernest.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ibee.mbordernest.R;
import com.ibee.mbordernest.activitys.OrderAndBill;
import com.ibee.mbordernest.model.AddOnsModel;
import com.ibee.mbordernest.model.CartItemListModel;
import com.ibee.mbordernest.model.ProductsNameIdModelClass;
import com.ibee.mbordernest.network.CheckNetworkConnection;
import com.ibee.mbordernest.parserclass.ServiceHandler;
import com.ibee.mbordernest.utils.Constants;
import com.ibee.mbordernest.utils.ShowToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ProductsNameIdAdapter extends RecyclerView.Adapter<ProductsNameIdAdapter.ViewHolder> {
    private List<ProductsNameIdModelClass> pressModels, filterList;
    Context context;
    RecyclerView list1;
    CartItemListAdapter cartItemListAdapter;
    static List<CartItemListModel> cartItemListModels = new ArrayList<>();
    //    RecyclerView my_recycler3;
    String item_name;
    int item_id;
    private SharedPreferences sharedPreferences;
    String Login_user_id = "", User_key = "";
    String ShowImages = "";
    String orderTypeId, tableid, postableid, st_no_pax = "", st_customer_id;
    int OrderId;
    private ProgressDialog mProgressDialog;
    private Dialog mDlgActivities;
    private Button mDilgActvytBtn;
    private AddOnsAdapter addOnsAdapter;
    List<AddOnsModel> addOnsModels = new ArrayList<>();
    String[] selectedItems = new String[]{};
    String API_TOKEN = "";
    JSONArray table6 = new JSONArray();
    JSONArray table2 = new JSONArray();

    public ProductsNameIdAdapter(Context context, List<ProductsNameIdModelClass> pressModels, String orderTypeId,
                                 String tableid, int OrderId, String st_no_pax, String st_customer_id, JSONArray adOns, JSONArray table2, String postableid) {
        this.context = context;
        this.pressModels = pressModels;

        this.orderTypeId = orderTypeId;
        this.tableid = tableid;
        this.postableid = postableid;
        this.OrderId = OrderId;
        this.st_no_pax = st_no_pax;
        this.table6 = adOns;
        this.st_customer_id = st_customer_id;
        this.table2 = table2;
        this.filterList = new ArrayList<ProductsNameIdModelClass>();
        this.filterList.addAll(this.pressModels);


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Login_user_id = sharedPreferences.getString("User_Id", "");
        User_key = sharedPreferences.getString("StoreKey", "");
        ShowImages = sharedPreferences.getString("ShowImages", "");
        API_TOKEN = sharedPreferences.getString("accesskey", "");

        {
            view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.home_dinign_list_items4, parent, false);
        }

//        ((OrderAndBill) context).CallToCartItems(table2);
        ViewHolder holder = new ViewHolder(view);


        return holder;
    }


    public void getOrderId() {
        OrderId = ((OrderAndBill) context).updatedOrderId();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductsNameIdModelClass menuModel = filterList.get(position);
        getOrderId();
//No Clear this comment
   /*     if (menuModel.getInventory().equals("true")) {

            holder.listquny.setVisibility(View.VISIBLE);


            holder.table_name.setText(menuModel.getName());
            holder.listquny.setText("(" + menuModel.getAvlqty() + ")");

            if (Integer.parseInt(menuModel.getAvlqty()) <= 0) {
                holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.grey_light_1));
                holder.listquny.setBackgroundColor(context.getResources().getColor(R.color.grey_light_1));
            } else {
                holder.table_name.setBackgroundColor(context.getResources().
                        getColor(R.color.colorWhite));
            }

        } else*/
        {
            holder.listquny.setVisibility(View.GONE);

            holder.table_name.setText(menuModel.getName());
            holder.table_name.setBackgroundColor(context.getResources().
                    getColor(R.color.colorWhite));
        }


        holder.table_name.setTextColor(context.getResources().
                getColor(R.color.colorPrimary));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                item_id = menuModel.getpId();
                item_name = menuModel.getName();
                int q = 0;
                int detailId = 0;
                boolean SendKot = false;

                if (cartItemListModels != null && cartItemListModels.size() != 0) {

                    int checked = 0;

                    for (int i = 0; i < cartItemListModels.size(); i++) {
                        if (item_id == (cartItemListModels.get(i).getpId())) {

                            q = (cartItemListModels.get(i).getQuantity()) + 1;
                            detailId = (cartItemListModels.get(i).getDetailId());
                            SendKot = (cartItemListModels.get(i).getSentToKOT());


                            if (SendKot == true) {
                                checked = 0;
                                break;
                            } else {
                                checked = 1;
                                break;
                            }
                        } else {
                            checked = 0;
                        }

                    }

                    if (checked == 1) {
                        if (menuModel.getAddOns().equals("null")) {

                            QuantityUpdateProduct(q, detailId);

                        } else {
                            openAddOnsDialog(menuModel.getAddOns());

                        }
                    } else {
                        if (menuModel.getAddOns().equals("null")) {
                            AddProduct("");
                        } else {
                            openAddOnsDialog(menuModel.getAddOns());
                        }
                    }

                } else {
                    if (menuModel.getAddOns().equals("null")) {
                        AddProduct("");
                    } else {
                        openAddOnsDialog(menuModel.getAddOns());
                    }
                }
            }
        });

    }


    public void filter(final String text) {

        filterList.clear();

        if (TextUtils.isEmpty(text)) {

            filterList.addAll(pressModels);

        } else {
            for (ProductsNameIdModelClass item : pressModels) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filterList.add(item);
                }
            }
        }


        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });

    }

    private void openAddOnsDialog(String adons) {

        try {
            selectedItems = adons.split(",");

            mDlgActivities = new Dialog(context);
            mDlgActivities.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDlgActivities.setContentView(R.layout.dialog_child_activities);
            mDlgActivities.show();
            mDilgActvytBtn = (Button) mDlgActivities.findViewById(R.id.btn_actvts_submit);

            list1 = (RecyclerView) mDlgActivities.findViewById(R.id.my_recycler_dailog);

            GridLayoutManager mLayoutManager = new GridLayoutManager(context, 1);
            list1.setLayoutManager(mLayoutManager);

            addOnsModels = new ArrayList<>();
            for (int j = 0; j < selectedItems.length; j++) {
                for (int i = 0; i < table6.length(); i++) {
                    AddOnsModel navigationMenuModel = null;
                    if (selectedItems[j].equals(table6.getJSONObject(i).getString("addOnId"))) {

                        navigationMenuModel = new AddOnsModel(table6.getJSONObject(i).getString("addOnId"),
                                table6.getJSONObject(i).getString("name"),
                                table6.getJSONObject(i).getString("price"));
                        addOnsModels.add(navigationMenuModel);
                    }
                }
            }


            addOnsAdapter = new AddOnsAdapter(addOnsModels, context, mDilgActvytBtn);
            list1.setAdapter(addOnsAdapter);
            mDilgActvytBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AddProduct(addOnsAdapter.getSelectedItem() + "");

                    mDlgActivities.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
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
            }


            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                dismissProgress();
                if (result != null) {
                    parseUpdateData(result);
                } else {
                    ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
            }
        }
    }

    public void parseUpdateData(String soapObject) throws ParseException {
        try {
            if (soapObject != null) {
                JSONObject jsonObj = new JSONObject(soapObject);
                if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                    if (jsonObj.has("data")) {
                        JSONObject jsonObject = jsonObj.getJSONObject("data");
//                        int bottomlayout = 0;

//                        cartItemListModels = new ArrayList<>();
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
            e.printStackTrace();
            ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
        }
    }

    @Override
    public int getItemCount() {
        return (null != filterList ? filterList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView table_name;
        TextView listquny;

        public ViewHolder(View itemView) {
            super(itemView);
            table_name = (TextView) itemView.findViewById(R.id.list1);
            listquny = (TextView) itemView.findViewById(R.id.listquny);
        }
    }

    public void AddProduct(String addOns) {


        if (CheckNetworkConnection.isNetWorkConnectionAvailable(context)) {
            new AddProductToOrderMethod().execute(addOns);
        } else {
            ShowToast.toastMsgNetworkConeection(context);
        }
    }

    public class AddProductToOrderMethod extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Adding item...");
        }

        @Override
        protected String doInBackground(String... arg0) {
            if (st_no_pax.isEmpty()) {
                st_no_pax = "0";
            }
            JSONObject json = new JSONObject();

            try {
                json.put("uKey", User_key);
                json.put("userId", Login_user_id);
                json.put("orderTypeId", orderTypeId);
                json.put("orderId", OrderId);
                json.put("prodId", item_id);
                json.put("tableId", tableid);
                json.put("POSTableId", postableid);
                json.put("addOns", arg0[0]);
                json.put("custId", st_customer_id);
                json.put("pax", st_no_pax);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_AddProductToOrder, ServiceHandler.POST, json, "");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;


        }

        @Override
        protected void onPostExecute(String result) {
            try {
                dismissProgress();
                if (result != null) {
                    parseData(result);
                } else {
                    ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(context, context.getResources().getString(R.string.some_error));
            }
        }

    }

    public void parseData(String soapObject) throws ParseException {
        // TODO Auto-generated method stub
        try {
            if (soapObject != null) {
                JSONObject jsonObj = new JSONObject(soapObject);
                if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                    if (jsonObj.has("data")) {
                        JSONObject jsonObject = jsonObj.getJSONObject("data");
                        if (jsonObject.has("table")) {
                            Log.e("cartItemListModels", jsonObject.has("table") + "");

                            JSONArray table = jsonObject.getJSONArray("table");
                            ((OrderAndBill) context).CallToCartItems(table);

                        }
                        if (jsonObject.has("table1")) {
                            JSONArray table1 = jsonObject.getJSONArray("table1");
                            ((OrderAndBill) context).updateSubTotal(table1);
                            OrderId = table1.getJSONObject(0).getInt("orderId");

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

}



