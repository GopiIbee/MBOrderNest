package com.ibee.mbordernest.activitys;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibee.mbordernest.R;
import com.ibee.mbordernest.adapter.CartItemListAdapter;
import com.ibee.mbordernest.adapter.CategoryNameIdAdapter;
import com.ibee.mbordernest.adapter.HomeLeftAdapter;
import com.ibee.mbordernest.adapter.PaymentDicsountAdapter;
import com.ibee.mbordernest.adapter.ProductsNameIdAdapter;
import com.ibee.mbordernest.adapter.SwapLeftAdapter;
import com.ibee.mbordernest.adapter.SwapTableAdapter;
import com.ibee.mbordernest.databinding.ActivityDialogOrderAndBillBinding;
import com.ibee.mbordernest.databinding.ActivityOrderAndBillBinding;
import com.ibee.mbordernest.databinding.DailogTableListBinding;
import com.ibee.mbordernest.model.BillSplitCartItemListModel;
import com.ibee.mbordernest.model.CartItemListModel;
import com.ibee.mbordernest.model.CategoriesNameIdModelClass;
import com.ibee.mbordernest.model.HomeLeftModel;
import com.ibee.mbordernest.model.IpAddresKOTModel;
import com.ibee.mbordernest.model.PaymentDicsountModel;
import com.ibee.mbordernest.model.ProductsNameIdModelClass;
import com.ibee.mbordernest.model.SendKotCartItemListModel;
import com.ibee.mbordernest.model.SwapLeftModel;
import com.ibee.mbordernest.model.SwapTableModelClass;
import com.ibee.mbordernest.model.TotalListTableModelClass;
import com.ibee.mbordernest.model.ZonesModelClass;
import com.ibee.mbordernest.network.CheckNetworkConnection;
import com.ibee.mbordernest.parserclass.ServiceHandler;
import com.ibee.mbordernest.printer.EscPosPrinter;
import com.ibee.mbordernest.printer.tcp.TcpConnection;
import com.ibee.mbordernest.utils.Constants;
import com.ibee.mbordernest.utils.ShowToast;
import com.ibee.mbordernest.utils.SimpleGestureFilter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class OrderAndBill extends BaseActivity implements SimpleGestureFilter.SimpleGestureListener, SearchView.OnQueryTextListener {
    String str = "Right";
    int fromEdit = 0;
    ActivityOrderAndBillBinding binding;
    String Login_user_id = "", Login_user_name = "", User_key = "", storeId = "", Currency = "";
    String tableid, postableid, orderTypeId, orderTypeIdName, st_tableName, st_no_pax = "0", st_customer_id = "", st_tablePin = "", st_sub_total = "";
    int OrderId = 0;
    Boolean StatusOfOrder = false;
    JSONArray orderItems = new JSONArray();
    JSONArray paymentModes = new JSONArray();
    JSONArray ncNames = new JSONArray();
    JSONArray subTables = new JSONArray();
    JSONArray subCategories = new JSONArray();
    JSONArray items = new JSONArray();
    JSONArray addOns = new JSONArray();
    JSONArray categories = new JSONArray();
    private SimpleGestureFilter detector;

    private SharedPreferences sharedPreferences;
    List<CartItemListModel> cartItemListModels = new ArrayList<>();
    CartItemListAdapter cartItemListAdapter;

    List<CategoriesNameIdModelClass> categoriesNameIdModelClasses;
    CategoryNameIdAdapter categoryNameIdAdapter;

    List<ZonesModelClass> zonesModelClasses;
//    ZonesAdapter zonesAdapter;

    List<PaymentDicsountModel> paymentDicsountModels = new ArrayList<>();
    PaymentDicsountAdapter paymentDicsountAdapter;

    List<IpAddresKOTModel> ipAddresKOTModels = new ArrayList<>();
    List<ProductsNameIdModelClass> productsNameIdModelClasses;
    ProductsNameIdAdapter productsNameIdAdapter;

    String OrderNumber = "";
    Double totalAmount;
    Double balanceAmount, balanceAmt;
    String BillNumber = "";
    int st_wallet;
    int eWardsMinRedemptionValue = 0;
    int checkPymetDone = 0;
    int canclebuttonED = 0;
    int onswipString = 0;
    String zoneName = "";
    String seletedId = "";
    String seletedType = "";
    String seletedZone = "Bar Counter";
    static final Integer[] images = new Integer[]{R.drawable.icon_edit,
            R.drawable.icon_repeat,
            R.drawable.icon_print};//  1stpos R.drawable.icon_news, 3pos  R.drawable.ic_baseline_cancel_24,// 5pos R.drawable.ic_outline_done_outline_24

    static final String[] numbers = new String[]{
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", ".", "0", "%", "<", "C", "OK"};
    AlertDialog dialogValidatePin;
    String action = "";
    int Billtype = 0;
    int checkReOpenDismis = 0;

    List NameStingAr = new ArrayList();
    List ValueStingAr = new ArrayList();
    List TaxNameStingAr = new ArrayList();
    List TaxValueStingAr = new ArrayList();
    String StoreName = "", CompanyName = "", ContactNo = "", Address = "", Branch = "";
    String GrandTotal = "", SubTotal = "", Discount = "0.0";
    String isFromBillOrKitchen = "";
    List<SwapLeftModel> swapLeftModels;
    SwapLeftAdapter swapLeftAdapter;
    String paymentIdNC = "";
    String paytypeNC = "";
    String SelectedNCID = "";

    JSONArray TableNames = new JSONArray();
    List<SwapTableModelClass> swapTableModelClasses;
    SwapTableAdapter swapTableAdapter;
    //    RecyclerView swapTableRecycler;
    AlertDialog dialogBilling;
    ActivityDialogOrderAndBillBinding aDOABBinding;
    DailogTableListBinding dailogTableListBinding;
    String DiscountType = "";
    List<BillSplitCartItemListModel> billSplitCartItemListModels = new ArrayList<>();
    String detailIds = "";
    LinearLayout ll_error2;
    TextView error_message2;
    AlertDialog dialog_apply_coupon;
    JSONArray Table3 = new JSONArray();
    List<String> ipaddList = new ArrayList<>();
    List<SendKotCartItemListModel> sendKotCartItemListModels = new ArrayList<>();
    List<String> kotsList = new ArrayList<>();
    String KotNo;

    Calendar c;
    String formattedDate = "";
    String formattedTime = "";

    String[] namesInNC;// = new ArrayList<>();
    int[] idsInNC;// = new ArrayList<>();
    String toOrderId = "";
    String[] namesIntoOrderId;// = new ArrayList<>();
    int[] idsIntoOrderId;// = new ArrayList<>();
    String IpAddresValue = "10.0.0.166";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderAndBillBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(OrderAndBill.this);
        detector = new SimpleGestureFilter(this, this);

        Currency = sharedPreferences.getString("Currency", "");

        Login_user_id = sharedPreferences.getString("User_Id", "");
        User_key = sharedPreferences.getString("StoreKey", "");
        storeId = sharedPreferences.getString("StoreId", "");
        Login_user_name = sharedPreferences.getString("User_name", "");
        if (sharedPreferences.contains("roleName")) {
            if (sharedPreferences.getString("roleName", "").equals("Casher") || sharedPreferences.getString("roleName", "").equals("Manager")) {
                binding.updateCasherRl.setVisibility(View.VISIBLE);
            } else {
                binding.updateCasherRl.setVisibility(View.GONE);

            }
        } else {
            binding.updateCasherRl.setVisibility(View.GONE);

        }

        Bundle b = getIntent().getExtras();
        if (b != null) {
            orderTypeId = b.getString("orderTypeId");
            orderTypeIdName = b.getString("orderTypeName");
            tableid = b.getString("table_id");
            postableid = b.getString("pos_table_id");
            st_tableName = b.getString("table_name");
            binding.tableName.setText(st_tableName);

            st_customer_id = b.getString("customer_id");
            OrderId = b.getInt("orderId");
            if (b.containsKey("StatusOfOrder")) {
                StatusOfOrder = b.getBoolean("StatusOfOrder");
            }

            if (b.containsKey("guest_name") && !b.getString("guest_name").isEmpty()) {
                binding.guestName.setText(b.getString("guest_name"));
            }

            if (b.containsKey("mobile_number") && !b.getString("mobile_number").isEmpty()) {
                binding.mobileNumber.setText(b.getString("mobile_number"));
            }

            if (b.containsKey("no_per") && !b.getString("no_per").isEmpty()) {
                st_no_pax = b.getString("no_per");
                binding.noPax.setText(st_no_pax);
            }
            if (b.containsKey("zoneName") && !b.getString("zoneName").isEmpty()) {
                zoneName = b.getString("zoneName");
            }
        }
        binding.gridView2.setAdapter(new ImageAdapterGridView(this));
        binding.updateCartList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailIds = "";
                String seletedReson = "";
                /*if (binding.spinnerUpdateCartItems.getSelectedItem().equals("Sub Table")) {
                    seletedReson = "Sub Table";
                    binding.spinnerUpdateCartItems.setSelection(0);
                    openDialogComment("reason for " + binding.spinnerUpdateCartItems.getSelectedItem(), seletedReson);

                } else*/
                if (binding.spinnerUpdateCartItems.getSelectedItem().equals("Split Item")) {
                    for (int i = 0; i < billSplitCartItemListModels.size(); i++) {
                        if (i == 0) {
                            detailIds = billSplitCartItemListModels.get(i).getDetailId() + "";
                        } else {
                            detailIds = detailIds + "," + billSplitCartItemListModels.get(i).getDetailId() + "";
                        }

                    }
                    seletedReson = "Split Item";
//                    binding.spinnerUpdateCartItems.setSelection(0);
                    openDialogComment("reason for " + binding.spinnerUpdateCartItems.getSelectedItem(), seletedReson);

                } else if (binding.spinnerUpdateCartItems.getSelectedItem().equals("Move Item")) {
                    for (int i = 0; i < billSplitCartItemListModels.size(); i++) {
                        if (i == 0) {
                            detailIds = billSplitCartItemListModels.get(i).getDetailId() + "";
                        } else {
                            detailIds = detailIds + "," + billSplitCartItemListModels.get(i).getDetailId() + "";
                        }

                    }
                    seletedReson = "Move Item";
//                    binding.spinnerUpdateCartItems.setSelection(0);
                    openDialogComment("reason for " + binding.spinnerUpdateCartItems.getSelectedItem(), seletedReson);

                } else if (binding.spinnerUpdateCartItems.getSelectedItem().equals("Delete Order")) {
                    seletedReson = "delete";
//                    binding.spinnerUpdateCartItems.setSelection(0);
                    openDialogComment("reason for " + binding.spinnerUpdateCartItems.getSelectedItem(), seletedReson);

                } else if (billSplitCartItemListModels.size() > 0) {
                    for (int i = 0; i < billSplitCartItemListModels.size(); i++) {
                        if (i == 0) {
                            detailIds = billSplitCartItemListModels.get(i).getDetailId() + "";
                        } else {
                            detailIds = detailIds + "," + billSplitCartItemListModels.get(i).getDetailId() + "";
                        }

                    }

                    if (binding.spinnerUpdateCartItems.getSelectedItem().equals("Cancel")) {
                        fromEdit = 1;
                        seletedReson = "Cancelled";

                    } else if (binding.spinnerUpdateCartItems.getSelectedItem().equals("NC")) {
                        seletedReson = "NC";
                    }
                    openDialogComment("reason for " + binding.spinnerUpdateCartItems.getSelectedItem(), seletedReson);

//                            CallApiPrintDetails("false", detailIds);
                } else {
                    Toast.makeText(getBaseContext(), "Please check items for splitting ", Toast.LENGTH_LONG).show();

                }
                Log.e("detailids", detailIds + " " + billSplitCartItemListModels.size());
            }
        });

        binding.spinnerUpdateCartItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos != 0) {

                    if (onswipString == 1) {
                        if (binding.spinnerUpdateCartItems.getSelectedItem().equals("Delete Order")
                        ) {// || binding.spinnerUpdateCartItems.getSelectedItem().equals("Sub Table")
                            updatecartUI(true);
                            cartItemListAdapter.checkBoxsChange(0, 0);

                        } else {
                            cartItemListAdapter.checkBoxsChange(1, 0);

                        }
                    } else {
                        if (binding.spinnerUpdateCartItems.getSelectedItem().equals("Split Item")) {
//                            updatecartUI(true);
                            cartItemListAdapter.checkBoxsChange(1, 1);

                        } else {
                            Toast.makeText(getBaseContext(), "Please send order to KOT before billing", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {

                    updatecartUI(false);
                    if (onswipString == 1) {

                        cartItemListAdapter.checkBoxsChange(0, 0);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.gridView2.setOnItemClickListener((parent, v, position, id) -> {

            if (position == 0) {
                if (onswipString == 1) {
                    openDialogComment("reason for edit", "edit");
                } else {
                    Toast.makeText(getBaseContext(), "Please send order to KOT before editing", Toast.LENGTH_LONG).show();
                }
            }/* else if (position == 1) {
                if (onswipString == 1) {
                    cartItemListAdapter.checkBoxsChange(1, 0);
                    Billtype = 0;
                    if (billSplitCartItemListModels.size() > 0) {
                        for (int i = 0; i < billSplitCartItemListModels.size(); i++) {
                            if (i == 0) {
                                detailIds = billSplitCartItemListModels.get(i).getDetailId() + "";
                            } else {
                                detailIds = detailIds + "," + billSplitCartItemListModels.get(i).getDetailId() + "";
                            }

                        }
                        Log.e("detailids", detailIds);
//                            CallApiPrintDetails("false", detailIds);
                    } else {
                        Toast.makeText(getBaseContext(), "Please check items for splitting ", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please send order to KOT before billing", Toast.LENGTH_LONG).show();
                }
            }*/ else if (position == 1) {
                if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
                    new GetOpenTablesByUserMethod().execute(User_key);
                } else {
                    ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                }
            } /*else if (position == 3) {
                if (onswipString == 1) {
                    openDialogComment("reason for delete", "delete");
                } else {
                    Toast.makeText(getBaseContext(), "Please send order to KOT before deleting", Toast.LENGTH_LONG).show();
                }

            }*/ else if (position == 2) {
                if (onswipString == 1) {
                    Billtype = 1;
                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
                        new GetPrintDetails().execute(User_key, OrderId + "", "true", "");
                    } else {
                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please send order to KOT before billing", Toast.LENGTH_LONG).show();
                }
            } /*else if (position == 5) {
                ShowToast.toastMessage(OrderAndBill.this, "Enter payment details before closing the order");

            }*/
        });
        if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
            new GetCategoriesAndProducts().execute(User_key, Login_user_id);
        } else {
            ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
        }


        if (OrderId == 0) {
            Animation RightSwipe2 = AnimationUtils.loadAnimation(OrderAndBill.this, R.anim.left_right);
            binding.ll4.startAnimation(RightSwipe2);
            binding.ll5.startAnimation(RightSwipe2);
            binding.ll2.setVisibility(View.GONE);
            binding.ll3.setVisibility(View.GONE);
            binding.ll4.setVisibility(View.VISIBLE);
            binding.ll5.setVisibility(View.VISIBLE);
            str = "Left";
        }

        binding.sentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SendToKotMethod(User_key, "false");
                if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {

                    new SendToKot().execute(User_key, "false");
                } else {
                    ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                }

                if (str.equals("Left")) {
                    binding.ll2.setVisibility(View.VISIBLE);
                    binding.ll3.setVisibility(View.VISIBLE);
                    binding.ll4.setVisibility(View.GONE);
                    binding.ll5.setVisibility(View.GONE);

                    str = "Right";

                }
            }
        });

        binding.cancleAllOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (canclebuttonED == 0 && onswipString == 0) {
                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
                        new CalncleAllOderMethod().execute(User_key);
                    } else {
                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "This order is edited. Send it to KOT only.", Toast.LENGTH_LONG).show();

                }
            }
        });
        if (StatusOfOrder == true) {
            openDailogAfterBill();
        }
        setupSearchView();

    }

    private void updatecartUI(boolean b) {

        if (b) {
            binding.updateCartList.setBackground(getResources().getDrawable(R.drawable.rectangle_shape2));

            binding.updateCartList.setEnabled(true);
        } else {
            binding.updateCartList.setBackground(getResources().getDrawable(R.drawable.rectangle_shape_full));
            binding.updateCartList.setEnabled(false);
        }
        binding.updateCartList.setPadding(10, 5, 10, 5);

    }

    private void setupSearchView() {
        binding.searchItem.setIconifiedByDefault(false);
        binding.searchItem.setOnQueryTextListener(this);
//        search_item.setSubmitButtonEnabled(true);
        binding.searchItem.setQueryHint(("Search Products"));
    }

    private void openDailogAfterBill() {


        AlertDialog.Builder builder = new AlertDialog.Builder(OrderAndBill.this, R.style.DialogTheme);

        aDOABBinding = ActivityDialogOrderAndBillBinding.inflate(getLayoutInflater());
        View dialogView = aDOABBinding.getRoot();


        builder.setView(dialogView);
        GridLayoutManager mLayoutManager5 = new GridLayoutManager(OrderAndBill.this, 1);

        aDOABBinding.myRecyclerPaymentModeDialog.setLayoutManager(mLayoutManager5);


        aDOABBinding.llGuestDetailsDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderAndBill.this, GuestInfoActivity.class);
                i.putExtra("table_id", tableid);
                i.putExtra("pos_table_id", postableid);
                i.putExtra("table_name", st_tableName);
                i.putExtra("order_type_id", orderTypeId);
                i.putExtra("order_type_name", orderTypeIdName);
                i.putExtra("orderId", OrderId);
                i.putExtra("StatusOfOrder", StatusOfOrder);

                startActivity(i);
                finish();
            }
        });
        aDOABBinding.billAndGuestInfoDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.mobileNumber.getText().toString().equals("0000000000")) {
                    Intent i = new Intent(OrderAndBill.this, GuestInfoActivity.class);
                    i.putExtra("table_id", tableid);
                    i.putExtra("pos_table_id", postableid);
                    i.putExtra("table_name", st_tableName);
                    i.putExtra("order_type_id", orderTypeId);
                    i.putExtra("order_type_name", orderTypeIdName);
                    i.putExtra("orderId", OrderId);
                    i.putExtra("StatusOfOrder", StatusOfOrder);

                    startActivity(i);
                    finish();
                } else {
                    if (onswipString == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderAndBill.this);
                        builder.setMessage("Do you want to sms bill to customer's mobile? ");
                        builder.setTitle("SMS Bill");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                new SendBillSMSMathod().execute(User_key, OrderId + "");

                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                        dialog.show();

                    } else {
                        Toast.makeText(getBaseContext(), "Please send order to KOT before Billing", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        aDOABBinding.tvReopenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!st_tablePin.isEmpty()) {
                    openDialogComment("reason for reopen", "reopen");
                } else {
                    Toast.makeText(getBaseContext(), "This function not enable", Toast.LENGTH_LONG).show();
                }
            }
        });
        aDOABBinding.llRecive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDailogEdit2("recevied", "", "", "");
            }
        });

        aDOABBinding.compliteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPymetDone == 1) {
                    CompliteTheOrder(User_key);
                } else {
                    ShowToast.toastMessage(OrderAndBill.this, "Enter payment details before closing the order");
                }
            }
        });

        dialogBilling = builder.create();


        dialogBilling.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogBilling.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialogBilling.setCanceledOnTouchOutside(false);
        checkReOpenDismis = 0;
        dialogBilling.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (checkReOpenDismis == 0) {
                    finish();
                } else {
                    dialog.dismiss();
                }
            }
        });

        dialogBilling.show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s != null) {
            if (s.equals("")) {
                callMainCategory(categories);
            } else {
                settingnavigation2(0, s);
            }
//            categoryNameIdAdapter.filter(s.trim());
        }
        return false;
    }

    public class SendBillSMSMathod extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Sending bill sms...");
        }

        @Override
        protected String doInBackground(String... arg0) {
//            soapObject = SoapObjectProvider.SendSmsBill(API_TOKEN, "", "");
//            return null;

            JSONObject json = new JSONObject();

            try {
                json.put("uKey", arg0[0]);
                json.put("orderId", arg0[1]);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_SendBillToCustomer, ServiceHandler.POST, json, "");


            } catch (IOException e) {
                e.printStackTrace();
                dismissProgress();
            }

//            Log.e("resSendBillToCustomer", response);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    {
                        JSONObject jsonObj = new JSONObject(result);
                        if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                            if (jsonObj.has("data")) {

                            }
                        } else {
                            if (jsonObj.has("message")) {
                                String message = jsonObj.getString("message");
                                ShowToast.toastMessage(OrderAndBill.this, message);

                            } else {
                                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                            }
                        }
                    }
                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();

        }
    }

    public class GetPrintDetails extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Loading..");
        }

        @Override
        protected String doInBackground(String... arg0) {
            JSONObject json = new JSONObject();

            try {
                json.put("uKey", arg0[0]);
                json.put("orderId", OrderId);
                json.put("storeId", storeId);
                json.put("isBilling", arg0[2]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_GetOrderPrintDetails, ServiceHandler.POST, json, "");


            } catch (IOException e) {
                e.printStackTrace();
                dismissProgress();
            }

//            Log.e("resGetOrderPrintDetails", response + " " + json);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {

                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                        if (jsonObj.has("data")) {
                            JSONObject jsonObject = jsonObj.getJSONObject("data");
                            if (jsonObject.has("table")) {//table3 is required data first
                                JSONArray table = jsonObject.getJSONArray("table");
                                if (table.length() != 0) {

                                    TaxNameStingAr = new ArrayList();
                                    TaxValueStingAr = new ArrayList();

                                    for (int i = 0; i < table.length(); i++) {
                                        TaxNameStingAr.add(table.getJSONObject(i).getString("name"));
                                        String tax = (new DecimalFormat("##.##").format(table.getJSONObject(i).getDouble("tax")));
                                        TaxValueStingAr.add(tax);
                                    }

                                }
                            }
                            if (jsonObject.has("table2")) {//table3 is required data first
                                JSONArray table2 = jsonObject.getJSONArray("table2");
                                if (table2.length() != 0) {

                                    NameStingAr = new ArrayList();
                                    ValueStingAr = new ArrayList();

                                    for (int i = 0; i < table2.length(); i++) {
                                        NameStingAr.add(table2.getJSONObject(i).getString("name"));
                                        ValueStingAr.add(table2.getJSONObject(i).getString("value"));
                                    }

                                }
                            }
                            if (jsonObject.has("table3")) {//table3 is required data first
                                JSONArray table3 = jsonObject.getJSONArray("table3");
                                if (table3.length() != 0) {


                                    StoreName = table3.getJSONObject(0).getString("storeName");
                                    CompanyName = table3.getJSONObject(0).getString("companyName");
                                    ContactNo = table3.getJSONObject(0).getString("contactNo");
                                    Address = table3.getJSONObject(0).getString("address");
                                    Branch = table3.getJSONObject(0).getString("branch");
//                                    IpAddresValue = table3.getJSONObject(0).getString("printer");
                                    SubTotal = (new DecimalFormat("##.##").format(table3.getJSONObject(0).getDouble("subTotal")));
                                    Discount = (new DecimalFormat("##.##").format(table3.getJSONObject(0).getDouble("discount")));
                                    GrandTotal = table3.getJSONObject(0).getString("grandTotal");


                                }
                            }


                            isFromBillOrKitchen = "Billing";

                            runPrintReceiptSequence(isFromBillOrKitchen, IpAddresValue);


                            if (Billtype == 1) {

                                Intent i = new Intent(OrderAndBill.this, OrderAndBill.class);
                                i.putExtra("orderTypeId", orderTypeId);
                                i.putExtra("orderTypeName", orderTypeIdName);
                                i.putExtra("table_id", tableid);
                                i.putExtra("pos_table_id", postableid);

                                i.putExtra("table_name", st_tableName);
                                i.putExtra("customer_id", st_customer_id);
                                i.putExtra("orderId", OrderId);
                                i.putExtra("StatusOfOrder", true);//payed

                                startActivity(i);
                                finish();
                            }
                        } else {
                            ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));

                        }
                    } else {
                        if (jsonObj.has("message")) {
                            String message = jsonObj.getString("message");
                            ShowToast.toastMessage(OrderAndBill.this, message);

                        } else {
                            ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                        }
                    }

                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();
        }
    }


    public class GetOpenTablesByUserMethod extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Loading..");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();

            try {
                json.put("uKey", arg0[0]);
                json.put("userId", Login_user_id);
                json.put("orderTypeId", orderTypeId);
                json.put("tableId", tableid);
                json.put("POSTableId", postableid);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_GetOpenTablesByUser, ServiceHandler.POST, json, "");


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
                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {

                        if (jsonObj.has("data")) {
                            JSONObject jsonObject = jsonObj.getJSONObject("data");
                            if (jsonObject.has("table") && jsonObject.has("table1")) {
                                TableNames = jsonObject.getJSONArray("table1");
                                openDailogTableList(jsonObject.getJSONArray("table"), jsonObject.getJSONArray("table1"));

                            }
                        }


                    } else {
                        if (jsonObj.has("message")) {
                            String message = jsonObj.getString("message");
                            ShowToast.toastMessage(OrderAndBill.this, message);

                        } else {
                            ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                        }
                    }

                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();
        }
    }

    private void openDailogTableList(JSONArray jsonArray, JSONArray jsonArray2) {

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderAndBill.this);

        dailogTableListBinding = DailogTableListBinding.inflate(getLayoutInflater());
        View dialogView = dailogTableListBinding.getRoot();


        builder.setView(dialogView);

        dailogTableListBinding.barCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletedZone = "Bar Counter";
                dailogTableListBinding.barCounter.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.barCounter.setTextColor(getResources().getColor(R.color.white));

                dailogTableListBinding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.ladiesLounge.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.danceSection.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.danceSection.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.smokingZone.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.smokingZone.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.vipArea.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.vipArea.setTextColor(getResources().getColor(R.color.icon_color_blue));

                swapnavigation(seletedId, seletedType, "1");
            }
        });
        dailogTableListBinding.ladiesLounge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletedZone = "Ladies Lounge";

                dailogTableListBinding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.ladiesLounge.setTextColor(getResources().getColor(R.color.white));

                dailogTableListBinding.barCounter.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.barCounter.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.danceSection.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.danceSection.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.smokingZone.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.smokingZone.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.vipArea.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.vipArea.setTextColor(getResources().getColor(R.color.icon_color_blue));

                swapnavigation(seletedId, seletedType, "2");
            }
        });
        dailogTableListBinding.danceSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletedZone = "Dance Section";

                dailogTableListBinding.danceSection.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.danceSection.setTextColor(getResources().getColor(R.color.white));

                dailogTableListBinding.barCounter.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.barCounter.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.ladiesLounge.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.smokingZone.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.smokingZone.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.vipArea.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.vipArea.setTextColor(getResources().getColor(R.color.icon_color_blue));

                swapnavigation(seletedId, seletedType, "3");
            }
        });
        dailogTableListBinding.smokingZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletedZone = "Smoking Zone";

                dailogTableListBinding.smokingZone.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.smokingZone.setTextColor(getResources().getColor(R.color.white));

                dailogTableListBinding.barCounter.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.barCounter.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.ladiesLounge.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.danceSection.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.danceSection.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.vipArea.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.vipArea.setTextColor(getResources().getColor(R.color.icon_color_blue));

                swapnavigation(seletedId, seletedType, "4");
            }
        });
        dailogTableListBinding.vipArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletedZone = "VIP Area";

                dailogTableListBinding.vipArea.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.vipArea.setTextColor(getResources().getColor(R.color.white));

                dailogTableListBinding.barCounter.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.barCounter.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.ladiesLounge.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.danceSection.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.danceSection.setTextColor(getResources().getColor(R.color.icon_color_blue));
                dailogTableListBinding.smokingZone.setBackgroundColor(getResources().getColor(R.color.white));
                dailogTableListBinding.smokingZone.setTextColor(getResources().getColor(R.color.icon_color_blue));

                swapnavigation(seletedId, seletedType, "5");
            }
        });

        LoadData(jsonArray);


        builder.setView(dialogView);


        final AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightPixels = displayMetrics.heightPixels;

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, heightPixels - 100);

        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangle_shape_full));

        dialog.setCanceledOnTouchOutside(false);

    }

    private void LoadData(JSONArray jsonArray) {
        swapLeftModels = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            SwapLeftModel navigationMenuModel = null;
            try {
                navigationMenuModel = new SwapLeftModel(jsonArray.getJSONObject(i).getString("name"),
                        jsonArray.getJSONObject(i).getString("orderTypeId"), "");

                swapLeftModels.add(navigationMenuModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        GridLayoutManager mLayoutManager = new GridLayoutManager(OrderAndBill.this, 1);
        dailogTableListBinding.myRecycler.setLayoutManager(mLayoutManager);
        swapLeftAdapter = new SwapLeftAdapter(OrderAndBill.this, swapLeftModels);
        dailogTableListBinding.myRecycler.setAdapter(swapLeftAdapter);
        swapnavigation(swapLeftModels.get(0).getId(), swapLeftModels.get(0).getTable_name(), "1");

    }

    public void swapnavigation(String getId, String tableType, String zoneId) {
        seletedId = getId;
        seletedType = tableType;
        swapTableModelClasses = new ArrayList<>();
        if (zoneId.equals("1")) {
            dailogTableListBinding.barCounter.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
            dailogTableListBinding.barCounter.setTextColor(getResources().getColor(R.color.white));

            dailogTableListBinding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.white));
            dailogTableListBinding.ladiesLounge.setTextColor(getResources().getColor(R.color.icon_color_blue));
            dailogTableListBinding.danceSection.setBackgroundColor(getResources().getColor(R.color.white));
            dailogTableListBinding.danceSection.setTextColor(getResources().getColor(R.color.icon_color_blue));
            dailogTableListBinding.smokingZone.setBackgroundColor(getResources().getColor(R.color.white));
            dailogTableListBinding.smokingZone.setTextColor(getResources().getColor(R.color.icon_color_blue));
            dailogTableListBinding.vipArea.setBackgroundColor(getResources().getColor(R.color.white));
            dailogTableListBinding.vipArea.setTextColor(getResources().getColor(R.color.icon_color_blue));

        }
        if (getId.equals("1")) {
            dailogTableListBinding.llZones.setVisibility(View.VISIBLE);
            dailogTableListBinding.viewLine.setVisibility(View.VISIBLE);
        } else {
            dailogTableListBinding.llZones.setVisibility(View.GONE);
            dailogTableListBinding.viewLine.setVisibility(View.GONE);

        }

        for (int i = 0; i < TableNames.length(); i++) {
            SwapTableModelClass navigationMenuModel = null;
            try {
                if (TableNames.getJSONObject(i).getString("orderTypeId").equals(getId) && TableNames.getJSONObject(i).getString("zoneId").equals(zoneId)) {


                    navigationMenuModel = new SwapTableModelClass(TableNames.getJSONObject(i).getString("tableName"),
                            TableNames.getJSONObject(i).getString("tableId"),
                            "false", getId, 0
                            , "", TableNames.getJSONObject(i).getString("posTableId"));
                    swapTableModelClasses.add(navigationMenuModel);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        swapTableAdapter = new SwapTableAdapter(OrderAndBill.this, swapTableModelClasses);
        dailogTableListBinding.swapTableRecycler.setAdapter(swapTableAdapter);
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(OrderAndBill.this, 6);
        dailogTableListBinding.swapTableRecycler.setLayoutManager(mLayoutManager2);
    }


    public void openDialogComment(String st_title, final String from) {
        final EditText comment;
        Button procced;
        TextView title;
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderAndBill.this);

        LayoutInflater inflater = LayoutInflater.from(builder.getContext());
        View dialogView = (View) inflater.inflate(R.layout.add_comment_dialog, null);


        comment = (EditText) dialogView.findViewById(R.id.comment);
        procced = (Button) dialogView.findViewById(R.id.procced);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel_back);
        title = (TextView) dialogView.findViewById(R.id.title);

        title.setText(st_title);

        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.spinnerUpdateCartItems.setSelection(0);
            }
        });
        procced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!comment.getText().toString().isEmpty()) {
                    if (from.equals("edit")) {
                        action = "EditKOT";


                    } else if (from.equals("delete")) {
                        action = "CancelOrder";


                    }/* else if (from.equals("Sub Table")) {
                        action = "Sub Table";


                    }*/ else if (from.equals("Split Item")) {
                        action = "Split Item";
                    } else if (from.equals("Move Item")) {
                        action = "Move Item";
                    } else if (from.equals("Cancelled")) {
                        action = "Cancelled";


                    } else if (from.equals("NC")) {
                        action = "NC";


                    } else if (from.equals("NC_Order")) {
                        action = "NC_Order";


                    } else if (from.equals("reopen")) {
                        action = "ReopenTable";


                    } else if (from.equals("Discount")) {
                        action = "Discount";

                    }
                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
                        new ValidataApiCall().execute(User_key, "****", action, comment.getText().toString());
                        if (Objects.equals(action, "Cancelled")) {
                            callPrintMethod("10.0.0.166");
                        }
                        binding.spinnerUpdateCartItems.setSelection(0);


                    } else {
                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                    }

//                    openDailogEdit(from, comment.getText().toString());
                    dialog.dismiss();
                } else {
                    ShowToast.toastMessage(OrderAndBill.this, "Please enter the reason");
                }
            }
        });


//        dialog.getWindow().setLayout(1200, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);

    }

    private void openDailogEdit(final String from, final String reason) {

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderAndBill.this);

        LayoutInflater inflater = LayoutInflater.from(builder.getContext());
        View dialogView = (View) inflater.inflate(R.layout.dailog_table, null);

        GridView gridView = (GridView) dialogView.findViewById(R.id.grid_view);
        final TextView pin_tv = (TextView) dialogView.findViewById(R.id.pin_tv);
        final TextView pin_tv_dummy = (TextView) dialogView.findViewById(R.id.pin_tv_dummy);
        pin_tv_dummy.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_list_item2, numbers);
        gridView.setAdapter(adapter);
        builder.setView(dialogView);


        dialogValidatePin = builder.create();
        dialogValidatePin.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialogValidatePin.getWindow().getAttributes();

        wmlp.gravity = Gravity.RIGHT;
        wmlp.x = 10;   //x position
        wmlp.y = 10;   //y position

        dialogValidatePin.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
       /* if (width > 1280) {
            dialogValidatePin.getWindow().setLayout(500, ViewGroup.LayoutParams.WRAP_CONTENT);

        } else*/
        {
            dialogValidatePin.getWindow().setLayout(500, ViewGroup.LayoutParams.WRAP_CONTENT);

        }


        final String[] pinVery = {""};
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

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

                    if (from.equals("edit")) {
                        action = "EditKOT";


                    } else if (from.equals("delete")) {
                        action = "CancelOrder";


                    }/* else if (from.equals("Sub Table")) {
                        action = "Sub Table";


                    }*/ else if (from.equals("Split Item")) {
                        action = "Split Item";
                    } else if (from.equals("Move Item")) {
                        action = "Move Item";
                    } else if (from.equals("Cancelled")) {
                        action = "Cancelled";


                    } else if (from.equals("NC")) {
                        action = "NC";


                    } else if (from.equals("NC_Order")) {
                        action = "NC_Order";


                    } else if (from.equals("reopen")) {
                        action = "ReopenTable";


                    } else if (from.equals("Discount")) {
                        action = "Discount";

                    }
                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
                        new ValidataApiCall().execute(User_key, pinVery[0], action, reason);
                    } else {
                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
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
                        ShowToast.toastMessage(OrderAndBill.this, "Only 4 Digits pin is allowed");
                    }

                }
            }
        });

    }


    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView(Context c) {
            mContext = c;
        }

        public int getCount() {
            return images.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;
                if (width > 1280) {
                    mImageView.setLayoutParams(new GridView.LayoutParams(80, 80));

                } else {
                    mImageView.setLayoutParams(new GridView.LayoutParams(50, 50));

                }


                mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mImageView.setBackground(getResources().getDrawable(R.drawable.round_shape));
                }*/
                mImageView.setPadding(5, 5, 5, 5);
            } else {
                mImageView = (ImageView) convertView;
            }
            mImageView.setImageResource(images[position]);
            return mImageView;
        }

    }

    public class SendToKot extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Loading..");
        }

        @Override
        protected String doInBackground(String... arg0) {
            if (arg0[1].equals("1")) {
                canclebuttonED = 1;
            }

            JSONObject json = new JSONObject();

            try {
                json.put("userId", sharedPreferences.getString("User_Id", ""));
                json.put("uKey", arg0[0]);
                json.put("orderId", OrderId);
                json.put("isEdit", arg0[1]);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_SendOrderToKot, ServiceHandler.POST, json, "");


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
                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                        if (jsonObj.has("data")) {
                            JSONObject jsonObject = jsonObj.getJSONObject("data");
                            if (jsonObject.has("table")) {
                                Log.e("cartItemListModels1", jsonObject.has("table") + "");

                                JSONArray table = jsonObject.getJSONArray("table");
                                if (table.length() != 0) {

                                    CallToCartItems(table);
                                }
                            }


                            if (jsonObject.has("table1")) {//table3 is required data first
                                JSONArray table1 = jsonObject.getJSONArray("table1");
                                if (table1.length() != 0) {

                                    updateSubTotal(table1);
                                }
                            }


                            if (jsonObject.has("table3")) {//table3 is required data first
                                Table3 = jsonObject.getJSONArray("table3");
                                ipaddList = new ArrayList<>();
                                for (int i = 0; i < Table3.length(); i++) {

                                    if (ipaddList.contains(Table3.getJSONObject(i).getString("printerIP"))) {

                                        continue;
                                    } else {
                                        ipaddList.add(Table3.getJSONObject(i).getString("printerIP"));
                                    }
                                }

                            }

                        }
                        if (ipaddList.size() > 0) {
                            for (int ipAdd = 0; ipAdd < ipaddList.size(); ipAdd++) {
                                callPrintMethod(ipaddList.get(ipAdd).toString());
                            }
                        }


                    } else {
                        if (jsonObj.has("message")) {

                            String message = jsonObj.getString("message");
                            ShowToast.toastMessage(OrderAndBill.this, message);

                        } else {

                            ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                        }
                    }
                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();
        }
    }

    public void callPrintMethod(String IpAddress) {
        {
            SendKotCartItemListModel SendKotCartMenuModel = null;

            sendKotCartItemListModels = new ArrayList<>();
            kotsList = new ArrayList<>();
            if (fromEdit == 1) {
                for (int i = 0; i < Table3.length(); i++) {
                    try {
                        if (IpAddress.equals(Table3.getJSONObject(i).getString("printerIP"))) {//+ "," + Table3.getJSONObject(i).getString("Num")
//                            ipaddresPCount = Table3.getJSONObject(i).getString("printerIP");
                            if (!kotsList.contains(Table3.getJSONObject(i).getString("num"))) {// + "," + Table3.getJSONObject(i).getString("Num")

                                kotsList.add(Table3.getJSONObject(i).getString("num"));
                            }


                            KotNo = (Table3.getJSONObject(i).getString("num"));

                            int newqty = 0, newPid = 0;
                            Double newPrice = 0.0;
                            String comm = "";
                            if (Table3.getJSONObject(i).getString("status").equals("deleted")) {
                                newqty = 0;
                                newPid = 0;
                                newPrice = 0.0;
                                comm = Table3.getJSONObject(i).getString("status");
                            } else {
                                newqty = Table3.getJSONObject(i).getInt("qty");
                                newPid = Table3.getJSONObject(i).getInt("pId");
                                newPrice = Table3.getJSONObject(i).getDouble("price");
                                comm = Table3.getJSONObject(i).getString("comments");
                            }

                            SendKotCartMenuModel = new SendKotCartItemListModel(Table3.getJSONObject(i).getInt("detailId"),
                                    newPid,
                                    Table3.getJSONObject(i).getString("name"),
                                    newqty,
                                    Table3.getJSONObject(i).getInt("oldQty"),
                                    newPrice,
                                    true,
                                    comm);
                            sendKotCartItemListModels.add(SendKotCartMenuModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        dismissProgress();
                    }
                }

            } else {
                for (int i = 0; i < Table3.length(); i++) {
                    try {
                        if (IpAddress.equals(Table3.getJSONObject(i).getString("printerIP"))) {
//                            ipaddresPCount = Table3.getJSONObject(i).getString("printerIP");

                            if (!kotsList.contains(Table3.getJSONObject(i).getString("num"))) {// + "," + Table3.getJSONObject(i).getString("Num")

                                kotsList.add(Table3.getJSONObject(i).getString("num"));
                            }

                            SendKotCartMenuModel = new SendKotCartItemListModel(Table3.getJSONObject(i).getInt("detailId"),
                                    Table3.getJSONObject(i).getInt("pId"),
                                    Table3.getJSONObject(i).getString("name"),
                                    Table3.getJSONObject(i).getInt("qty"),
                                    Table3.getJSONObject(i).getInt("oldQty"),
                                    Table3.getJSONObject(i).getDouble("price"),
                                    true,
                                    Table3.getJSONObject(i).getString("comments"));
                            sendKotCartItemListModels.add(SendKotCartMenuModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        dismissProgress();

                    }
                }

            }
            for (int ii = 0; ii < kotsList.size(); ii++) {
                if (ii == 0) {
                    KotNo = kotsList.get(ii);
                } else {
                    KotNo = KotNo + "," + kotsList.get(ii);

                }
            }

            if (fromEdit == 0) {
                isFromBillOrKitchen = "Kitchen";
//                callPrintMethod(ipaddresPCount);

                runPrintReceiptSequence(isFromBillOrKitchen, IpAddress);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        runPrintReceiptSequence(isFromBillOrKitchen, IpAddress);

                    }
                }, 100);   //5 seconds
            } else {
                isFromBillOrKitchen = "Cancel";
                runPrintReceiptSequence(isFromBillOrKitchen, IpAddress);
            }

            if (fromEdit == 1) {
                fromEdit = 0;
            }


        }
    }

    @SuppressLint("DefaultLocale")
    private void runPrintReceiptSequence(String isFromBillOrKitchen, String ipAddress) {
        String textData = "";
        c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");

        formattedDate = df.format(c.getTime());
        formattedTime = tf.format(c.getTime());

        if (isFromBillOrKitchen.equals("Kitchen") || isFromBillOrKitchen.equals("Cancel")) {

            if (isFromBillOrKitchen.equals("Cancel"))
                textData = ("[C]<font size='wide' >Cancel</font> \n\n");

            textData = textData + ("[C]<b>" + orderTypeIdName + " " + zoneName + "</b> \n")
                    + ("[L]By:" + Login_user_name + "[R]<b> KOT:" + KotNo + "</b>\n")
                    + ("[L]Bill No: " + BillNumber + "[R]Covers: " + st_no_pax + "\n")
                    + ("[L]Punch KOT: " + formattedDate + " " + formattedTime + "[R]<b>Table : " + st_tableName + "</b>\n")
                    + ("[L]------------------------------------------------\n");
            StringBuilder res1 = new StringBuilder();

            res1.append(String.format("%2s %-40s %4s%n", "NO", " Item Name", "Qty"));

            textData = textData + (res1);
            textData = textData + ("[L]------------------------------------------------\n");

            StringBuilder res = new StringBuilder();

            if (fromEdit == 1) {
                for (int i = 0; i < billSplitCartItemListModels.size(); ++i) {
                    res.append(String.format("%2d %-40s %4s%n", i + 1, "<b>" + billSplitCartItemListModels.get(i).getName() + " " + billSplitCartItemListModels.get(i).getComment() + "</b>", "<b>" + billSplitCartItemListModels.get(i).getQuantity() + "</b>"));
                }
            } else {
                for (int i = 0; i < sendKotCartItemListModels.size(); ++i) {
                    res.append(String.format("%2d %-40s %4s%n", i + 1, "<b>" + sendKotCartItemListModels.get(i).getName() + " " + sendKotCartItemListModels.get(i).getComment() + "</b>", "<b>" + sendKotCartItemListModels.get(i).getQuantity() + "</b>"));
                }
            }

            textData = textData + (res + "\n");

        } else if (isFromBillOrKitchen.equals("Billing")) {

            int toNo_Qty = 0;
            textData = ("[C]" + StoreName + "\n")
                    + ("[C]" + CompanyName + "\n")
                    + ("[C]" + Address + "\n")
                    + ("[C]" + Branch + "\n")
                    + ("[C]" + ContactNo + "\n\n\n")
                    + ("[L]Date : " + formattedDate)
                    + ("[R]Time : " + formattedTime + "\n")
                    + ("[L]Billed By : " + Login_user_name)
                    + ("[R]Bill No : " + BillNumber + " \n")
                    + ("[L]Order Type: " + orderTypeIdName)
                    + ("[R]Table : " + st_tableName + "\n")
                    + ("------------------------------------------------\n")
                    + (String.format("%2s %-32s %3s %5s%n", "NO", "Item Name", "Qty", "Price"))
                    + ("------------------------------------------------\n");
            StringBuilder res = new StringBuilder();

            if (Billtype == 0) {

                for (int i = 0; i < billSplitCartItemListModels.size(); i++) {

                    toNo_Qty = toNo_Qty + billSplitCartItemListModels.get(i).getQuantity();

                    res.append(String.format("%2d %-32s %3s %5s%n", i + 1, billSplitCartItemListModels.get(i).getName(), billSplitCartItemListModels.get(i).getQuantity(), billSplitCartItemListModels.get(i).getPrice().toString()));
                }

            } else {

                for (int i = 0; i < cartItemListModels.size(); i++) {

                    toNo_Qty = toNo_Qty + cartItemListModels.get(i).getQuantity();
                    res.append(String.format("%2d %-32s %3s %5s%n", i + 1, cartItemListModels.get(i).getName(), cartItemListModels.get(i).getQuantity(), cartItemListModels.get(i).getPrice().toString()));


                }


            }
            textData = textData + (res + "\n");

            textData = textData + ("------------------------------------------------\n");
            if (Billtype == 0) {
                textData = textData + ("[L]Total no.items : " + billSplitCartItemListModels.size() + "[R]  Total no.qty : " + toNo_Qty + "  \n");

            } else {
                textData = textData + ("[L]Total no.items : " + cartItemListModels.size() + "[R]  Total no.qty : " + toNo_Qty + "  \n");

                cartItemListModels.clear();
            }
            textData = textData + ("[C]------------------------------------------------\n");

            textData = textData + ("[L]SUBTOTAL  [R]" + String.format("%15s", ": " + SubTotal) + "\n");
            if (!Discount.equals("0")) {
                textData = textData + ("[L]Discount  [R]" + String.format("%15s", ": " + Discount) + "\n");
            }
            for (int i = 0; i < TaxNameStingAr.size(); i++) {
                textData = textData + ("[L]" + TaxNameStingAr.get(i) + "[R]" + String.format("%15s", ": " + TaxValueStingAr.get(i) + "\n"));
            }

            textData = textData + ("------------------------------------------------\n");
            textData = textData + ("[L]Grand Total     [R]" + String.format("%15s", ": " + GrandTotal + " \n"));
            textData = textData + ("------------------------------------------------\n");


            for (int j = 0; j < NameStingAr.size(); j++) {
                textData = textData + (NameStingAr.get(j) + " : " + ValueStingAr.get(j) + "\n");
            }


            textData = textData + ("\n[C]Thank you ! Please visit again.\n");


        }
        String guestName = "";
        if (binding.guestName.getText().toString().isEmpty()) {
            guestName = "Guest";
        } else {
            guestName = binding.guestName.getText().toString();

        }
        textData = textData + ("[C]Guest Name: " + guestName + "\n\n");
        String finalTextData = textData;
        new Thread(new Runnable() {
            public void run() {
                try {

                    EscPosPrinter printer = new EscPosPrinter(new TcpConnection(ipAddress, 9100, 15 * 1000), 203, 48f, 48);
                    printer.printFormattedTextAndCut((finalTextData));
                    printer.disconnectPrinter();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());

//                    runPrintReceiptSequence(isFromBillOrKitchen, ipAddress);
//                                Toast.makeText(TestPrintActivity.this,""+e.getMessage(),Toast.LENGTH_LONG);
                }
            }
        }).start();

    }


    public class CalncleAllOderMethod extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Loading ..");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();

            try {
                json.put("uKey", arg0[0]);
                json.put("orderId", OrderId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_CancelOrderItems, ServiceHandler.POST, json, "");


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
                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                        if (jsonObj.has("data")) {
                            JSONObject jsonObject = jsonObj.getJSONObject("data");

                            if (jsonObject.has("table1")) {//table3 is required data first
                                JSONArray table1 = jsonObject.getJSONArray("table1");
                                if (table1.length() != 0) {
                                    updateSubTotal(table1);
                                }

                            }


                            if (jsonObject.has("table")) {
//                            Log.e("cartItemListTable", Table2.toString());

                                JSONArray table = jsonObject.getJSONArray("table");
                                if (table.length() != 0) {

                                    CallToCartItems(table);


                                } else {
//                                    new CloseOrderMethod().execute(User_key);

                                    CompliteTheOrder(User_key);
                                }
                            }
                        }


                    } else {
                        if (jsonObj.has("message")) {
                            String message = jsonObj.getString("message");
                            ShowToast.toastMessage(OrderAndBill.this, message);
                        } else {
                            ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                        }
                    }
                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    public void UpdateBillPrint(int detailId, int getpId, String name, int quantity, Double price, Boolean sentToKOT, String comment) {
        BillSplitCartItemListModel navigationMenuModel = null;

        updatecartUI(true);

        navigationMenuModel = new BillSplitCartItemListModel(detailId,
                getpId,
                name,
                quantity,
                price,
                sentToKOT,
                comment);
        billSplitCartItemListModels.add(navigationMenuModel);
    }

    public void UpdateBillPrintRemove(int detailId) {
        if (detailId == 0) {
            billSplitCartItemListModels.clear();
            updatecartUI(false);


        } else {
            for (int j = 0; j < billSplitCartItemListModels.size(); j++) {
                if (detailId == billSplitCartItemListModels.get(j).getDetailId()) {
                    billSplitCartItemListModels.remove(j);
                }
            }
            if (billSplitCartItemListModels.size() <= 0) {

                updatecartUI(false);

            }
        }
    }


    public class GetCategoriesAndProducts extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Loading..");
        }

        @Override
        protected String doInBackground(String... arg0) {
            JSONObject json = new JSONObject();

            try {
                json.put("userId", arg0[1]);
                json.put("uKey", arg0[0]);
                json.put("orderTypeId", orderTypeId);
                json.put("orderId", OrderId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_GetCategoriesAndProducts, ServiceHandler.POST, json, "");


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
                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                        if (jsonObj.has("data")) {
                            JSONObject jsonObject = jsonObj.getJSONObject("data");
                            if (jsonObject.has("orderAmounts")) {
                                //Table3 is orderItems  Table1 is items  Table6 is addOns

                                JSONArray orderAmounts = jsonObject.getJSONArray("orderAmounts");

                                if (orderAmounts.length() != 0) {
                                    if (orderAmounts.getJSONObject(0).getString("mobile") != null &&
                                            !orderAmounts.getJSONObject(0).getString("mobile").isEmpty() &&
                                            !orderAmounts.getJSONObject(0).getString("mobile").equals("null")) {
                                        binding.mobileNumber.setText(orderAmounts.getJSONObject(0).getString("mobile"));
                                    }

                                    if (orderAmounts.getJSONObject(0).getString("customerName") != null &&
                                            !orderAmounts.getJSONObject(0).getString("customerName").isEmpty()
                                            && !orderAmounts.getJSONObject(0).getString("customerName").equals("null")) {
                                        binding.guestName.setText(orderAmounts.getJSONObject(0).getString("customerName"));

                                    }
                                    if (orderAmounts.getJSONObject(0).getString("pax") != null
                                            && !orderAmounts.getJSONObject(0).getString("pax").isEmpty()
                                            && !orderAmounts.getJSONObject(0).getString("pax").equals("null")) {
                                        binding.noPax.setText(orderAmounts.getJSONObject(0).getString("pax"));
                                    }


                                    updateSubTotal(orderAmounts);

                                }
                            }
                            if (jsonObject.has("orderItems")) {

                                orderItems = jsonObject.getJSONArray("orderItems");//Table2 is orderItems

                                if (orderItems.length() != 0) {


                                    CallToCartItems(orderItems);

                                }
                            }

                            if (jsonObject.has("paymentModes")) {
                                paymentModes = jsonObject.getJSONArray("paymentModes");

                            }
                            if (jsonObject.has("ncNames")) {
                                ncNames = jsonObject.getJSONArray("ncNames");
                                namesInNC = new String[ncNames.length()];
                                idsInNC = new int[ncNames.length()];

                                for (int i = 0; i < ncNames.length(); i++) {
                                    namesInNC[i] = (ncNames.getJSONObject(i).getString("ncName"));
                                    idsInNC[i] = (ncNames.getJSONObject(i).getInt("ncId"));

                                }

                            }
                            if (jsonObject.has("subTables")) {
                                subTables = jsonObject.getJSONArray("subTables");
                                namesIntoOrderId = new String[subTables.length()];
                                idsIntoOrderId = new int[subTables.length()];

                                for (int i = 0; i < subTables.length(); i++) {
                                    namesIntoOrderId[i] = (subTables.getJSONObject(i).getString("tableName"));
                                    idsIntoOrderId[i] = (subTables.getJSONObject(i).getInt("orderId"));

                                }

                            }
                            if (jsonObject.has("subCategories")) {//Table is categories

                                subCategories = jsonObject.getJSONArray("subCategories");
                            }
                            if (jsonObject.has("categories")) {//Table is categories

                                categories = jsonObject.getJSONArray("categories");
                                if (jsonObject.has("items")) {
                                    items = jsonObject.getJSONArray("items");
                                    //NameIdAdapter2 is replaceed to CategoryNameIdAdapter

                                }
                                callMainCategory(categories);
                            /*    categoriesNameIdModelClasses = new ArrayList<>();
                                CategoriesNameIdModelClass navigationMenuModel = null;
                                for (int i = 0; i < categories.length(); i++) {

                                    navigationMenuModel = new CategoriesNameIdModelClass(categories.getJSONObject(i).getString("categoryName"),
                                            categories.getJSONObject(i).getInt("categoryId"));
                                    categoriesNameIdModelClasses.add(navigationMenuModel);

                                }

                                GridLayoutManager mLayoutManager = new GridLayoutManager(OrderAndBill.this, 1);
                                binding.myRecycler.setLayoutManager(mLayoutManager);
                                categoryNameIdAdapter = new CategoryNameIdAdapter(OrderAndBill.this, categoriesNameIdModelClasses, subCategories);
                                binding.myRecycler.setAdapter(categoryNameIdAdapter);*/

                            }
                            {
                                paymentDicsountModels = new ArrayList<>();

                                PaymentDicsountModel navigationMenuModel = new PaymentDicsountModel("1", "Flat discount", "", "%");
                                paymentDicsountModels.add(navigationMenuModel);
                                PaymentDicsountModel navigationMenuModel2 = new PaymentDicsountModel("2", "Flat discount", "", "Fixed");
                                paymentDicsountModels.add(navigationMenuModel2);
                                PaymentDicsountModel navigationMenuModel3 = new PaymentDicsountModel("3", "Apply coupon", "", "");
                                paymentDicsountModels.add(navigationMenuModel3);
                                GridLayoutManager mLayoutManagerD = new GridLayoutManager(OrderAndBill.this, 1);
                                binding.myRecyclerDiscounts.setLayoutManager(mLayoutManagerD);
                                paymentDicsountAdapter = new PaymentDicsountAdapter(OrderAndBill.this, paymentDicsountModels, "discount");
                                binding.myRecyclerDiscounts.setAdapter(paymentDicsountAdapter);

                            }
                            if (jsonObject.has("addOns")) {
                                addOns = jsonObject.getJSONArray("addOns");
                            }
                            if (jsonObject.has("paymentModes")) {
                                paymentModes = jsonObject.getJSONArray("paymentModes");


                                paymentDicsountModels = new ArrayList<>();

                                for (int i = 0; i < paymentModes.length(); i++) {
                                    PaymentDicsountModel navigationMenuModel = null;

                                    navigationMenuModel = new PaymentDicsountModel(paymentModes.getJSONObject(i).getString("paymentId")
                                            , paymentModes.getJSONObject(i).getString("name"),
                                            paymentModes.getJSONObject(i).getString("amount"));
                                    paymentDicsountModels.add(navigationMenuModel);
                                }
                                GridLayoutManager mLayoutManager5 = new GridLayoutManager(OrderAndBill.this, 1);
                                binding.myRecyclerPaymentMode.setLayoutManager(mLayoutManager5);

                                paymentDicsountAdapter = new PaymentDicsountAdapter(OrderAndBill.this, paymentDicsountModels, "payment_act");
                                binding.myRecyclerPaymentMode.setAdapter(paymentDicsountAdapter);
                                if (StatusOfOrder == true) {
                                    paymentDicsountAdapter = new PaymentDicsountAdapter(OrderAndBill.this, paymentDicsountModels, "payment");
                                    aDOABBinding.myRecyclerPaymentModeDialog.setAdapter(paymentDicsountAdapter);
                                }
                            }
                            if (jsonObject.has("printers")) {

                                JSONArray printers = jsonObject.getJSONArray("printers");

                                ipAddresKOTModels = new ArrayList<>();

                                for (int i = 0; i < printers.length(); i++) {
                                    IpAddresKOTModel navigationMenuModel = null;

                                    navigationMenuModel = new IpAddresKOTModel(printers.getJSONObject(i).getString("name"),
                                            printers.getJSONObject(i).getString("iPAddress"));
                                    ipAddresKOTModels.add(navigationMenuModel);

                                }
                            }

                        } else {
                            ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));

                        }
                    } else {
                        if (jsonObj.has("message")) {
                            String message = jsonObj.getString("message");
                            ShowToast.toastMessage(OrderAndBill.this, message);

//                            showErrorDialog(message);
                        } else {
                            ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                        }
                    }
                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();
        }
    }

    public void callMainCategory(JSONArray categories) {

        categoriesNameIdModelClasses = new ArrayList<>();
        CategoriesNameIdModelClass navigationMenuModel = null;
        for (int i = 0; i < categories.length(); i++) {

            try {
                navigationMenuModel = new CategoriesNameIdModelClass(categories.getJSONObject(i).getString("categoryName"),
                        categories.getJSONObject(i).getInt("categoryId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            categoriesNameIdModelClasses.add(navigationMenuModel);

        }

        GridLayoutManager mLayoutManager = new GridLayoutManager(OrderAndBill.this, 1);
        binding.myRecycler.setLayoutManager(mLayoutManager);
        categoryNameIdAdapter = new CategoryNameIdAdapter(OrderAndBill.this, categoriesNameIdModelClasses, subCategories);
        binding.myRecycler.setAdapter(categoryNameIdAdapter);

    }

    public void updateSubTotal(JSONArray orderAmounts) {

//        billSplitCartItemListModels = new ArrayList<>();
//        JSONArray Table3 = new JSONArray();
//        Table3 = table3;

        try {
            if (orderAmounts.length() > 0) {
                if (orderAmounts.getJSONObject(0).has("orderId")) {
                    OrderId = orderAmounts.getJSONObject(0).getInt("orderId");

                }
                if (orderAmounts.getJSONObject(0).has("serviceCharge")) {
                    binding.serviceCharge.setText(orderAmounts.getJSONObject(0).getInt("serviceCharge") + "");

                }
                if (orderAmounts.getJSONObject(0).has("orderNo")) {
                    OrderNumber = orderAmounts.getJSONObject(0).getString("orderNo");
                    binding.tableName.setText(st_tableName + " - " + OrderNumber);

                }


                binding.total.setText(Currency + " " + orderAmounts.getJSONObject(0).getString("grandTotal"));

                totalAmount = Double.parseDouble(orderAmounts.getJSONObject(0).getString("grandTotal"));
                balanceAmt = Double.parseDouble(orderAmounts.getJSONObject(0).getString("balance")); //- Double.parseDouble(orderAmounts.getJSONObject(0).getString("Received"));
                binding.subTotal.setText(Currency + " " + orderAmounts.getJSONObject(0).getString("subTotal"));
                st_sub_total = (orderAmounts.getJSONObject(0).getString("subTotal"));
                binding.discount.setText(Currency + " " + orderAmounts.getJSONObject(0).getString("discount"));
                binding.taxs.setText(Currency + " " + orderAmounts.getJSONObject(0).getString("tax"));
                binding.grandTotal.setText(Currency + " " + orderAmounts.getJSONObject(0).getString("grandTotal"));

                if (StatusOfOrder == true) {
                    aDOABBinding.totalDialog.setText(Currency + " " + orderAmounts.getJSONObject(0).getString("grandTotal"));
                    aDOABBinding.subTotalDialog.setText(Currency + " " + orderAmounts.getJSONObject(0).getString("subTotal"));
                    aDOABBinding.discountDialog.setText(Currency + " " + orderAmounts.getJSONObject(0).getString("discount"));
                    aDOABBinding.taxsDialog.setText(Currency + " " + orderAmounts.getJSONObject(0).getString("tax"));
                    aDOABBinding.grandTotalDialog.setText(Currency + " " + orderAmounts.getJSONObject(0).getString("grandTotal"));
                    aDOABBinding.guestNameDialog.setText(binding.guestName.getText().toString());
                    aDOABBinding.noPaxDialog.setText("0");
                    aDOABBinding.mobileNumberDialog.setText(binding.mobileNumber.getText().toString());
                    st_wallet = (orderAmounts.getJSONObject(0).getInt("wallet"));
                    if (st_wallet != 0) {
                        aDOABBinding.walletDialog.setText(st_wallet + "");
                    }
                }
                if (orderAmounts.getJSONObject(0).has("customerId")) {
                    st_customer_id = (orderAmounts.getJSONObject(0).getString("customerId"));
                }
                if (orderAmounts.getJSONObject(0).has("pax")) {
                    st_no_pax = (orderAmounts.getJSONObject(0).getString("pax"));
                }
                if (orderAmounts.getJSONObject(0).has("tablePin")) {
                    st_tablePin = (orderAmounts.getJSONObject(0).getString("tablePin"));
                }

                if (orderAmounts.getJSONObject(0).has("billNo")) {
                    BillNumber = (orderAmounts.getJSONObject(0).getString("billNo"));
                }
                if (orderAmounts.getJSONObject(0).has("wallet")) {

                    st_wallet = (orderAmounts.getJSONObject(0).getInt("wallet"));
                    if (st_wallet != 0) {
                        binding.wallet.setText(st_wallet + "");
                    }

                }
                if (orderAmounts.getJSONObject(0).has("eWards_MinRedemption")) {
                    eWardsMinRedemptionValue = orderAmounts.getJSONObject(0).getInt("eWards_MinRedemption");

                }
                if (orderAmounts.getJSONObject(0).getString("received").equals(orderAmounts.getJSONObject(0).getString("grandTotal"))) {
                    checkPymetDone = 1;
//                    CompliteTheOrder(User_key);
                } else {
                    checkPymetDone = 0;

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void CallToCartItems(JSONArray orderItems) {
        cartItemListModels = new ArrayList<>();
        int bottomlayout = 0;

        for (int i = 0; i < orderItems.length(); i++) {
            CartItemListModel navigationMenuModel = null;

            try {
                navigationMenuModel = new CartItemListModel(orderItems.getJSONObject(i).getInt("detailId"),
                        orderItems.getJSONObject(i).getInt("pId"),
                        orderItems.getJSONObject(i).getString("name"),
                        orderItems.getJSONObject(i).getInt("quantity"),
                        orderItems.getJSONObject(i).getDouble("price") * orderItems.getJSONObject(i).getDouble("quantity"),
                        orderItems.getJSONObject(i).getBoolean("sentToKOT"),
                        orderItems.getJSONObject(i).getString("comments"));

                cartItemListModels.add(navigationMenuModel);

                if (!orderItems.getJSONObject(i).getBoolean("sentToKOT") && bottomlayout == 0) {
                    callUpdateBottomLL(false);
                    bottomlayout = 1;
                }
                if (bottomlayout == 0) {
                    callUpdateBottomLL(true);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Collections.reverse(cartItemListModels);
        GridLayoutManager mLayoutManager3 = new GridLayoutManager(OrderAndBill.this, 1);
        binding.myRecycler3.setLayoutManager(mLayoutManager3);
        cartItemListAdapter = new CartItemListAdapter(OrderAndBill.this, cartItemListModels);
        binding.myRecycler3.setAdapter(cartItemListAdapter);//my_recycler3 replace to recyclerAddedProducts

    }

    public int updatedOrderId() {
        return OrderId;
    }

    public void callUpdateBottomLL(Boolean SendKot) {
        if (SendKot == true) {
            onswipString = 1;
            binding.bottomLl.setVisibility(View.GONE);

        } else {
            onswipString = 0;
            binding.bottomLl.setVisibility(View.VISIBLE);
        }
    }

    public void settingnavigation2(int getId, String test) {
        productsNameIdModelClasses = new ArrayList<>();
//        test = "100";
        ProductsNameIdModelClass navigationMenuModel2 = null;
        for (int i = 0; i < items.length(); i++) {
            try {
                String name = items.getJSONObject(i).getString("name");
                if (getId != 0) {
                    if (items.getJSONObject(i).getInt("subCategoryId") == getId) {
                        navigationMenuModel2 = new ProductsNameIdModelClass(items.getJSONObject(i).getInt("pId")
                                , items.getJSONObject(i).getLong("price"),
                                items.getJSONObject(i).getLong("taxPer"),
                                items.getJSONObject(i).getString("name"),
                                items.getJSONObject(i).getInt("categoryId"),
                                items.getJSONObject(i).getString("addOns"),
                                items.getJSONObject(i).getString("inventory"),
                                items.getJSONObject(i).getInt("availableQty") + "");

                        productsNameIdModelClasses.add(navigationMenuModel2);

                    }
                } else {
                    if (name.toLowerCase(Locale.ROOT).contains(test.toLowerCase(Locale.ROOT))) {
                        navigationMenuModel2 = new ProductsNameIdModelClass(items.getJSONObject(i).getInt("pId")
                                , items.getJSONObject(i).getLong("price"),
                                items.getJSONObject(i).getLong("taxPer"),
                                items.getJSONObject(i).getString("name"),
                                items.getJSONObject(i).getInt("categoryId"),
                                items.getJSONObject(i).getString("addOns"),
                                items.getJSONObject(i).getString("inventory"),
                                items.getJSONObject(i).getString("availableQty") + "");

                        productsNameIdModelClasses.add(navigationMenuModel2);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(OrderAndBill.this, 4);
        binding.myRecycler2.setLayoutManager(mLayoutManager2);

        productsNameIdAdapter = new ProductsNameIdAdapter(OrderAndBill.this, productsNameIdModelClasses, orderTypeId, tableid, OrderId, st_no_pax, st_customer_id, addOns, orderItems, postableid);
        binding.myRecycler2.setAdapter(productsNameIdAdapter);
    }

    @Override
    public void onSwipe(int direction) {
        if (fromEdit == 0) {
            switch (direction) {

                case SimpleGestureFilter.SWIPE_RIGHT:
                    if (str.equals("Left")) {
                        Animation RightSwipe = AnimationUtils.loadAnimation(OrderAndBill.this, R.anim.right_left);
                        binding.ll4.startAnimation(RightSwipe);
                        binding.ll5.startAnimation(RightSwipe);
                        binding.ll2.setVisibility(View.VISIBLE);
                        binding.ll3.setVisibility(View.VISIBLE);
                        binding.ll4.setVisibility(View.GONE);
                        binding.ll5.setVisibility(View.GONE);
                        str = "Right";
                    }

                    break;
                case SimpleGestureFilter.SWIPE_LEFT:
                    if (str.equals("Right")) {
                        Animation RightSwipe2 = AnimationUtils.loadAnimation(OrderAndBill.this, R.anim.left_right);
                        binding.ll4.startAnimation(RightSwipe2);
                        binding.ll5.startAnimation(RightSwipe2);
                        binding.ll2.setVisibility(View.GONE);
                        binding.ll3.setVisibility(View.GONE);
                        binding.ll4.setVisibility(View.VISIBLE);
                        binding.ll5.setVisibility(View.VISIBLE);


                        str = "Left";
                    }
                    break;

            }

        } else {
            ShowToast.toastMessage(OrderAndBill.this, "Finish changes in edited KOT to add more products to the order");
        }
    }


    @Override
    public void onDoubleTap() {

    }

    public class ValidataApiCall extends AsyncTask<String, String, String> {
        String response = null;
        String Reason = "";

        @Override
        protected void onPreExecute() {
            showProgress("Loading..");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();
            Reason = arg0[3];


            try {
                json.put("uKey", arg0[0]);
                json.put("userId", sharedPreferences.getString("User_Id", ""));
                json.put("pin", arg0[1]);
                json.put("action", arg0[2]);
                json.put("reason", arg0[3]);
                json.put("orderId", OrderId);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_ValidatePIN, ServiceHandler.POST, json, "");


            } catch (IOException e) {
                e.printStackTrace();
                dismissProgress();
            }

            Log.e("json", response + " \n" + json);
            return response;
        }

        @Override
        protected void onPostExecute(String soapObject) {
            try {
                if (soapObject != null) {
                    JSONObject jsonObj = new JSONObject(soapObject);
                    if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                        if (jsonObj.has("data")) {
                            if (jsonObj.getString("data").equals("true")) {
                                if (action.equals("EditKOT")) {
                                    fromEdit = 1;
                                    binding.cancleAllOrder.setEnabled(false);
                                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {

                                        new SendToKot().execute(User_key, "true");
                                    } else {
                                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                                    }
                                    dialogValidatePin.dismiss();
                                } else if (action.equals("Split Item")) {//action.equals("Sub Table") ||

                                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {

                                        new SplitTable().execute(User_key, "true");
                                    } else {
                                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                                    }
                                    dialogValidatePin.dismiss();
                                } else if (action.equals("Move Item")) {

                                   /* if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {

                                        new SplitTable().execute(User_key, "true");
                                    } else {
                                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                                    }*/
                                    dialogValidatePin.dismiss();
                                    callToListOfSubTable();
                                } else if (action.equals("ReopenTable")) {
                                    StatusOfOrder = false;
                                    checkReOpenDismis = 1;
                                    dialogValidatePin.dismiss();
                                    dialogBilling.dismiss();
                                } else if (action.equals("CancelOrder")) {
                                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
                                        dialogValidatePin.dismiss();
                                        cartItemListAdapter.callDeleteProdut(OrderId);

                                    } else {
                                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                                    }
                                } else if (action.equals("NC_Order")) {
                                    dialogValidatePin.dismiss();

                                    selectDropdoenlist();

                                } else if (action.equals("Cancelled") || action.equals("NC")) {
                                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
//                                        dialogValidatePin.dismiss();
                                        callDeleteAllProduts(action, Reason);

                                    } else {
                                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                                    }
                                } else if (action.equals("Discount")) {
                                    dialogValidatePin.dismiss();

                                    openDailogEdit2("Discount", "", "", "");
                                }
                            } else {
                                ShowToast.toastMessage(OrderAndBill.this, "Invalid pin");
                            }
                        }
                    } else {
                        if (jsonObj.has("message")) {
                            String message = jsonObj.getString("message");
                            ShowToast.toastMessage(OrderAndBill.this, message);

                        } else {
                            ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                        }
                    }
                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();
        }
    }

    private void callToListOfSubTable() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderAndBill.this);
        builder.setTitle("Select and Move to Other Table");

        builder.setItems(namesIntoOrderId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                toOrderId = String.valueOf(idsIntoOrderId[which]);
//                openDailogEdit2("payment", paymentIdNC, String.valueOf(balanceAmt), paytypeNC);
                if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {

                    new SplitTable().execute(User_key, "true");
                } else {
                    ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void selectDropdoenlist() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderAndBill.this);
        builder.setTitle("Select and Move to NC");

        builder.setItems(namesInNC, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SelectedNCID = String.valueOf(idsInNC[which]);
                openDailogEdit2("payment", paymentIdNC, String.valueOf(balanceAmt), paytypeNC);

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public class SplitTable extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Loading...");

        }

        @Override
        protected String doInBackground(String... arg0) {
            JSONObject json = new JSONObject();

            try {
                json.put("userId", sharedPreferences.getString("User_Id", ""));
                json.put("uKey", arg0[0]);
                if (!action.equals("Move Item")) {
                    json.put("tableId", tableid);
                    json.put("POSTableId", postableid);
                } else {
                    json.put("toOrderId", toOrderId);

                }
                if (action.equals("Split Item") || action.equals("Move Item")) {
                    json.put("detIds", detailIds);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                if (action.equals("Move Item")) {
                    response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_MoveItems, ServiceHandler.POST, json, "");

                } else {
                    response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_SplitTable, ServiceHandler.POST, json, "");
                }

            } catch (IOException e) {
                e.printStackTrace();
                dismissProgress();

            }

            return response;

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                dismissProgress();
                if (result != null) {
                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                        if (jsonObj.has("data")) {
                            JSONObject jsonObject = jsonObj.getJSONObject("data");
                            if (jsonObject.has("table")) {
                                JSONArray table1 = jsonObject.getJSONArray("table");
                              /*  navigationMenuModel = new TotalListTableModelClass(table1.getJSONObject(i).getString("tableName"),
                                        TableNames.getJSONObject(i).getString("tableId"),
                                        TableNames.getJSONObject(i).getString("isOpen"), getId,
                                        TableNames.getJSONObject(i).getInt("orderId"),
                                        TableNames.getJSONObject(i).getBoolean("sentToBilling"),
                                        tableType,
                                        TableNames.getJSONObject(i).getString("customer"),
                                        TableNames.getJSONObject(i).getString("saleAmount"),
                                        TableNames.getJSONObject(i).getString("posTableId"));*/
                                Intent i = new Intent(OrderAndBill.this, OrderAndBill.class);
                                i.putExtra("orderTypeId", orderTypeId);
                                i.putExtra("orderTypeName", orderTypeIdName);
                                i.putExtra("table_id", table1.getJSONObject(0).getString("tableId"));
                                i.putExtra("pos_table_id", table1.getJSONObject(0).getString("posTableId"));
                                i.putExtra("table_name", table1.getJSONObject(0).getString("tableName"));
                                i.putExtra("customer_id", "0");
                                i.putExtra("orderId", table1.getJSONObject(0).getInt("orderId"));
                                i.putExtra("StatusOfOrder", false);//payed
                                i.putExtra("zoneName", seletedZone);//payed

                                startActivity(i);
                                finish();
                            }
                        }
                    }
//                    parseGetUserData(result);
                } else {
                    ShowToast.toastMessage(OrderAndBill.this, "No Data");
                }


            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, "Error");
            }
        }
    }

    private void callDeleteAllProduts(String action, String reason) {

        new DeleteProductToOrderMethod().execute(action, reason);
    }

    public class DeleteProductToOrderMethod extends AsyncTask<String, String, String> {
        String response = null;


        @Override
        protected void onPreExecute() {
            showProgress(action + " items...");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();

            try {
                json.put("detIds", detailIds);
                json.put("uKey", User_key);
                json.put("deleteOrder", 0);
                json.put("orderId", OrderId);
                json.put("action", arg0[0]);
                json.put("reason", arg0[1]);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_CancelOrNCProductInOrder, ServiceHandler.POST, json, "");


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
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
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

                        if (jsonObject.has("table")) {

                            JSONArray table = jsonObject.getJSONArray("table");

                            if (table.length() == 0) {
                                Intent i = new Intent(OrderAndBill.this, HomeScreenActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                            CallToCartItems(table);

                        }
                        if (jsonObject.has("table1")) {
                            JSONArray table1 = jsonObject.getJSONArray("table1");
                            updateSubTotal(table1);
                        }


                    } else {
                        ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));

                    }
                } else {
                    if (jsonObj.has("message")) {
                        String message = jsonObj.getString("message");
                        ShowToast.toastMessage(OrderAndBill.this, message);

                    } else {
                        ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                    }
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
        }
    }


    public void SwapTableMethod(String tableId, String postableid) {
        if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
            new SwapTable().execute(User_key, tableId, postableid);
        } else {
            ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
        }

    }

    public class SwapTable extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Loading ..");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();

            try {
                json.put("uKey", arg0[0]);
                json.put("userId", Login_user_id);
                json.put("orderId", OrderId);
                json.put("tableId", arg0[1]);
                json.put("POSTableId", arg0[2]);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_SwapTable, ServiceHandler.POST, json, "");


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
                    {
                        JSONObject jsonObj = new JSONObject(result);
                        if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
//                            dialogBilling.dismiss();

                            Intent i = new Intent(OrderAndBill.this, HomeScreenActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);

                        } else {
                            if (jsonObj.has("message")) {
                                String message = jsonObj.getString("message");
                                ShowToast.toastMessage(OrderAndBill.this, message);
                            } else {
                                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                            }
                        }

                    }

                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();
        }
    }

    public class CloseOrderMethod extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Closing order ...");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();

            try {
                json.put("uKey", arg0[0]);
                json.put("orderId", OrderId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_CloseOrder, ServiceHandler.POST, json, "");


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

                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {

                        if (dialogBilling != null && dialogBilling.isShowing()) {
                            dialogBilling.dismiss();
                        }
                        Intent i = new Intent(OrderAndBill.this, HomeScreenActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
//                        finish();

                    } else {
                        if (jsonObj.has("message")) {
                            String message = jsonObj.getString("message");
                            ShowToast.toastMessage(OrderAndBill.this, message);

//                            showErrorDialog(message);
                        } else {
                            ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                        }
                    }

                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();

        }
    }

    private void openDailogEdit2(final String from, final String PayId, String balanceAmt, String payType) {

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderAndBill.this);

        LayoutInflater inflater = LayoutInflater.from(builder.getContext());
        View dialogView = (View) inflater.inflate(R.layout.dailog_table, null);

        GridView gridView = (GridView) dialogView.findViewById(R.id.grid_view);
        final TextView pin_tv = (TextView) dialogView.findViewById(R.id.pin_tv);
        TextView pay_type = (TextView) dialogView.findViewById(R.id.pay_type);
        if (!payType.equals("")) {
            pay_type.setVisibility(View.VISIBLE);
            pay_type.setText(payType);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_list_item2, numbers);
        gridView.setAdapter(adapter);
        builder.setView(dialogView);

        pin_tv.setText(balanceAmt);

        final AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.RIGHT;
        wmlp.x = 10;   //x position
        wmlp.y = 10;   //y position

        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        dialog.getWindow().setLayout(500, ViewGroup.LayoutParams.WRAP_CONTENT);


        dialog.setCanceledOnTouchOutside(false);
        final String[] pinVery = {""};
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                CharSequence pinsingle = ((TextView) v).getText();

                if (pinsingle.equals("C")) {
                    pinVery[0] = "";
                    pin_tv.setText("");

                } else if (pinsingle.equals("<")) {


                    if (pinVery[0].length() == 0) {
                        pinVery[0] = "";
                        pin_tv.setText("");

                    } else if (pinVery[0].length() == 1) {
                        pin_tv.setText("");
                        pinVery[0] = "";

                    } else {
                        pin_tv.setText(pinVery[0].substring(0, pinVery[0].length() - 1));
                        pinVery[0] = pinVery[0].substring(0, pinVery[0].length() - 1);
                    }


                } else if (pinsingle.equals("OK")) {

                    if (from.equals("payment")) {
                        if (pin_tv.getText().toString().isEmpty()) {
                            ShowToast.toastMessage(OrderAndBill.this, "Please Enter Amount");
                        } else {
                            Double payamount = Double.parseDouble(pin_tv.getText().toString());
                            if (payamount <= totalAmount) {
                                if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
                                    new PayOrderMathod().execute(User_key, PayId, payamount + "");
                                } else {
                                    ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                                }
                                dialog.dismiss();
                            } else {
                                ShowToast.toastMessage(OrderAndBill.this, "Amount exceeding order total ");
                            }

                        }
                    } else if (from.equals("recevied")) {
                        if (pin_tv.getText().toString().isEmpty()) {
                            ShowToast.toastMessage(OrderAndBill.this, "Please Enter Amount");
                        } else {
                            Double payamount = Double.parseDouble(pin_tv.getText().toString());

                            binding.recevied.setText(Currency + " " + payamount);

                            balanceAmount = (payamount - totalAmount);
                            binding.balance.setText(Currency + " " + balanceAmount + "");

                            if (StatusOfOrder == true) {
                                aDOABBinding.receviedDialog.setText(Currency + " " + payamount);
                                aDOABBinding.balanceDialog.setText(Currency + " " + balanceAmount + "");
                            }
                            dialog.dismiss();

                        }
                    } else if (from.equals("Discount")) {

                        if (pin_tv.getText().toString().isEmpty()) {
                            ShowToast.toastMessage(OrderAndBill.this, "Please enter value ");
                        } else {
                            String repalceValue = pin_tv.getText().toString().replace("%", "");

                            Double payamount = Double.parseDouble(repalceValue);
                            Double halfAmont = totalAmount / 2;
                            if (DiscountType.equals("inr")) {
                                if (halfAmont < payamount) {
                                    ShowToast.toastMessage(OrderAndBill.this, "Maximum applicable discount for this order is " + Currency + " " + halfAmont);

                                } else {
                                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
                                        callDiscountApi(repalceValue);
                                        dialog.dismiss();
                                    } else {
                                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                                    }
                                }
                            } else {
                                if (50 < payamount) {
                                    ShowToast.toastMessage(OrderAndBill.this, "Maximum applicable discount for this order is 50%");

                                } else {
                                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
                                        callDiscountApi(repalceValue);
                                        dialog.dismiss();
                                    } else {
                                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                                    }
                                }
                            }
                        }
                    }

                } else {

                    if (pinVery[0].isEmpty()) {
                        pinVery[0] = String.valueOf(pinsingle);
                        pin_tv.setText(pinVery[0]);

                    } else {
                        pinVery[0] = pinVery[0] + pinsingle;
                        pin_tv.setText(pinVery[0]);
                    }
                }
            }
        });

    }

    public class PayOrderMathod extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Updating payment details...");
        }

        @Override
        protected String doInBackground(String... arg0) {
            JSONObject json = new JSONObject();

            try {
                json.put("uKey", arg0[0]);
                json.put("orderId", OrderId);
                json.put("payId", arg0[1]);
                json.put("userId", Login_user_id);
                json.put("amt", arg0[2]);
                if (!SelectedNCID.equals("")) {
                    json.put("ncId", SelectedNCID);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_PayOrder, ServiceHandler.POST, json, "");


            } catch (IOException e) {
                e.printStackTrace();
                dismissProgress();
            }

            Log.e("resPayOrder", response + "\n" + json);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    {
                        JSONObject jsonObj = new JSONObject(result);
                        if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                            if (jsonObj.has("data")) {
                                JSONObject jsonObject = jsonObj.getJSONObject("data");


                                if (jsonObject.has("table")) {//table3 is required data first
                                    JSONArray Table = jsonObject.getJSONArray("table");
                                    if (Table.length() != 0) {

                                        updateSubTotal(Table);
                                    }
                                }
                                if (jsonObject.has("table1")) {
                                    JSONArray Table1 = jsonObject.getJSONArray("table1");
                                    paymentDicsountModels = new ArrayList<>();

                                    for (int i = 0; i < Table1.length(); i++) {
                                        PaymentDicsountModel navigationMenuModel = null;

                                        navigationMenuModel = new PaymentDicsountModel(Table1.getJSONObject(i).getString("paymentId")
                                                , Table1.getJSONObject(i).getString("name"),
                                                Table1.getJSONObject(i).getString("amount"));
                                        paymentDicsountModels.add(navigationMenuModel);
                                    }
                                    paymentDicsountAdapter = new PaymentDicsountAdapter(OrderAndBill.this, paymentDicsountModels, "payment_act");
                                    binding.myRecyclerPaymentMode.setAdapter(paymentDicsountAdapter);
                                    if (StatusOfOrder == true) {
                                        paymentDicsountAdapter = new PaymentDicsountAdapter(OrderAndBill.this, paymentDicsountModels, "payment");
                                        aDOABBinding.myRecyclerPaymentModeDialog.setAdapter(paymentDicsountAdapter);
                                    }
                                }
                            }
                        } else {
                            if (jsonObj.has("message")) {
                                String message = jsonObj.getString("message");
                                ShowToast.toastMessage(OrderAndBill.this, message);

//                            showErrorDialog(message);
                            } else {
                                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                            }
                        }
                    }
                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();

        }
    }

    public void callPaymentRequest(String id, String paytype) {
        if (paytype.equals("NC")) {
            paymentIdNC = id;
            paytypeNC = paytype;
            openDialogComment("reason for NC", "NC_Order");
        } else {
            openDailogEdit2("payment", id, String.valueOf(balanceAmt), paytype);
        }
    }

    public void callDiscountRequest(String type) {
        if (onswipString == 1) {
            if (type.equals("%")) {
                DiscountType = "per";
                openDialogComment("reason for discount", "Discount");

            } else if (type.equals("Fixed")) {
                DiscountType = type.toLowerCase();
                openDialogComment("reason for discount", "Discount");

            } else {
                //Apply coupon
                openDailogApplyCoupon();
            }
        } else {
            Toast.makeText(getBaseContext(), "After send order to KOT, you can give's discount", Toast.LENGTH_LONG).show();
        }
    }

    private void CompliteTheOrder(String user_key) {
        if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
            new CloseOrderMethod().execute(user_key);
        } else {
            ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
        }

    }

    private void callDiscountApi(String valueForDis) {
        new ApplyDiscountApi().execute(valueForDis);

    }

    public class ApplyDiscountApi extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Validating discount...");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();

            try {
                json.put("uKey", User_key);
                json.put("orderId", OrderId);
                json.put("discountType", DiscountType);
                json.put("val", arg0[0]);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_ApplyDiscount, ServiceHandler.POST, json, "");


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
                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {

                        if (jsonObj.has("data")) {
                            JSONObject jsonObject = jsonObj.getJSONObject("data");

                            if (jsonObject.has("table")) {//table3 is required data first
                                JSONArray Table = jsonObject.getJSONArray("table");
                                if (Table.length() != 0) {

                                    updateSubTotal(Table);
                                }
                            }
                        }

                    } else {
                        if (jsonObj.has("message")) {
                            String message = jsonObj.getString("message");
                            ShowToast.toastMessage(OrderAndBill.this, message);

//                            showErrorDialog(message);
                        } else {
                            ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                        }
                    }
                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();

        }
    }

    private void openDailogApplyCoupon() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(OrderAndBill.this);
        final TextView StoreCode, ewardcode, tv_enter_type;

        final EditText code_points;
        Button redeem_now;

        LayoutInflater inflater = LayoutInflater.from(builder.getContext());
        final View dialogView = (View) inflater.inflate(R.layout.e_rewards_layout2, null);

        StoreCode = (TextView) dialogView.findViewById(R.id.StoreCode);
        ewardcode = (TextView) dialogView.findViewById(R.id.ewardcode);
        tv_enter_type = (TextView) dialogView.findViewById(R.id.tv_enter_type);

        ll_error2 = (LinearLayout) dialogView.findViewById(R.id.ll_error2);
        error_message2 = (TextView) dialogView.findViewById(R.id.error_message2);
        code_points = (EditText) dialogView.findViewById(R.id.code_points);
        redeem_now = (Button) dialogView.findViewById(R.id.redeem_now);


        StoreCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fromto = 0;
                tv_enter_type.setText("Enter Coupon");
                StoreCode.setBackground(ContextCompat.getDrawable(OrderAndBill.this, R.drawable.layer_store));
                StoreCode.setTextColor(getResources().getColor(R.color.icon_color_blue));
                ewardcode.setTextColor(getResources().getColor(R.color.colorWhite));

                ewardcode.setBackground(ContextCompat.getDrawable(OrderAndBill.this, R.drawable.layer_store2));
            }
        });

/*
        ewardcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromto = 1;
                tv_enter_type.setText("eWards Coupon");
                StoreCode.setBackground(ContextCompat.getDrawable(OrderAndBill.this, R.drawable.layer_store2));
                ewardcode.setBackground(ContextCompat.getDrawable(OrderAndBill.this, R.drawable.layer_store));
                ewardcode.setTextColor(getResources().getColor(R.color.icon_color_blue));
                StoreCode.setTextColor(getResources().getColor(R.color.colorWhite));

            }
        });
*/


        redeem_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!code_points.getText().toString().equals("")) {

//                    if (fromto == 0) {
                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
                        new ApplyCodeMathod().execute(User_key, code_points.getText().toString());
                    } else {
                        ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                    }
                    /*} else {
                        if (CheckNetworkConnection.isNetWorkConnectionAvailable(OrderAndBill.this)) {
                            new RedeemeWardsCodeMathod().execute(User_key, code_points.getText().toString());
                        } else {
                            ShowToast.toastMsgNetworkConeection(OrderAndBill.this);
                        }
                    }*/
                }
            }
        });
        builder.setView(dialogView);
        dialog_apply_coupon = builder.create();
        dialog_apply_coupon.show();
        dialog_apply_coupon.setCanceledOnTouchOutside(false);
        dialog_apply_coupon.getWindow().setLayout(800, ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    public class ApplyCodeMathod extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Applying Code...");
        }

        @Override
        protected String doInBackground(String... arg0) {


            JSONObject json = new JSONObject();

            try {
                json.put("orderId", OrderId);
                json.put("uKey", arg0[0]);
                json.put("coupon", arg0[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_ApplyCoupon, ServiceHandler.POST, json, "");


            } catch (IOException e) {
                e.printStackTrace();
                dismissProgress();
            }

//            Log.e("resApplyCoupon", response);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    {
                        JSONObject jsonObj = new JSONObject(result);
                        if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                            if (jsonObj.getString("message").equals("coupon applied")) {
                                dialog_apply_coupon.dismiss();
                                if (jsonObj.has("data")) {
                                    JSONObject jsonObject = jsonObj.getJSONObject("data");

                                    if (jsonObject.has("table")) {
                                        JSONArray data = jsonObject.getJSONArray("table");
                                        updateSubTotal(data);

                                    }
                                }
                            } else {
                                if (jsonObj.has("message")) {
                                    String message = jsonObj.getString("message");
                                    ShowToast.toastMessage(OrderAndBill.this, message);
                                } else {
                                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                                }
                            }

                        } else {
                            if (jsonObj.has("message")) {
                                String message = jsonObj.getString("message");
                                ShowToast.toastMessage(OrderAndBill.this, message);

//                            showErrorDialog(message);
                            } else {
                                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                            }
                        }
                    }
                } else {
                    ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(OrderAndBill.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();

        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (str.equals("Left")) {
            binding.ll2.setVisibility(View.VISIBLE);
            binding.ll3.setVisibility(View.VISIBLE);
            binding.ll4.setVisibility(View.GONE);
            binding.ll5.setVisibility(View.GONE);

            str = "Right";

        } else {
            if (fromEdit == 1) {
                ShowToast.toastMessage(OrderAndBill.this, "This order is edited. Send it to KOT to navigate back to orders.");
            } else {
                finish();
            }
        }
    }

}