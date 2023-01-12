package com.ibee.mbordernest.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibee.mbordernest.R;
import com.ibee.mbordernest.adapter.HomeLeftAdapter;
import com.ibee.mbordernest.adapter.StoresAdapter;
import com.ibee.mbordernest.adapter.TotalListTableReservedAdapter;
import com.ibee.mbordernest.databinding.ActivityHomeScreenBinding;
import com.ibee.mbordernest.model.HomeLeftModel;
import com.ibee.mbordernest.model.StoreChangeModelClass;
import com.ibee.mbordernest.model.TotalListTableModelClass;
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
import java.util.List;

public class HomeScreenActivity extends BaseActivity {

    StoresAdapter storesAdapter; // PeopleAdapter6 NameIdAdapter

    ActivityHomeScreenBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor mEditorObj;

    JSONArray TableCategoeirs = new JSONArray();
    JSONArray TableNames = new JSONArray();
    JSONArray TableZones = new JSONArray();

    List<HomeLeftModel> homeLeftModels;
    HomeLeftAdapter homeLeftAdapter;

    List<TotalListTableModelClass> totalListTableModelClasses;
    TotalListTableReservedAdapter totalListTableReservedAdapter;
    String St_Table1 = "";
    JSONArray Table1 = new JSONArray();

    List<StoreChangeModelClass> storeChangeModelClasses;

    String seletedId = "";
    String seletedType = "";
    String seletedZone = "Bar Counter";


    static final String[] numbers = new String[]{
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", ".", "0", "%", "<", "C", "OK"};
    AlertDialog dialogValidatePin;
    String orderTypeId = "",
            orderTypeName = "",
            table_id = "",
            pos_table_id = "",
            table_name = "", StorderId = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(HomeScreenActivity.this);
        mEditorObj = sharedPreferences.edit();
        St_Table1 = sharedPreferences.getString("StoresArray", "");
        try {
            Table1 = new JSONArray(St_Table1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (Table1.length() > 1) {
            binding.storeChange.setVisibility(View.VISIBLE);
        } else {
            binding.storeChange.setVisibility(View.GONE);
        }
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.getString("from").equals("Opendailog")) {
//                St_Table1 = b.getString("Table1JsonArry");

                OpenListOfStores();
            } else {
                GetOrderUser(sharedPreferences.getString("StoreKey", ""), sharedPreferences.getString("User_Id", ""));
            }
        } else {
            GetOrderUser(sharedPreferences.getString("StoreKey", ""), sharedPreferences.getString("User_Id", ""));
        }

        binding.recharege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeScreenActivity.this, WalletRechargeActivity.class);
                startActivity(i);

            }
        });
        binding.storeChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenListOfStores();

            }
        });

       /* binding.tableCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(HomeScreenActivity.this,TestPrintActivity.class);
                startActivity(i);
                finish();
            }
        });*/

        binding.llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i=new Intent(HomeScreenActivity.this,TestPrintActivity.class);
                startActivity(i);*/

                new AlertDialog.Builder(HomeScreenActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharedPreferences.edit()
                                        .clear()
                                        .commit();
                                Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(intent);

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        binding.barCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletedZone = "Bar Counter";
                binding.barCounter.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
                binding.barCounter.setTextColor(getResources().getColor(R.color.white));

                binding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.white));
                binding.ladiesLounge.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.danceSection.setBackgroundColor(getResources().getColor(R.color.white));
                binding.danceSection.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.smokingZone.setBackgroundColor(getResources().getColor(R.color.white));
                binding.smokingZone.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.vipArea.setBackgroundColor(getResources().getColor(R.color.white));
                binding.vipArea.setTextColor(getResources().getColor(R.color.icon_color_blue));

                settingnavigation2(seletedId, seletedType, "1");
            }
        });
        binding.ladiesLounge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletedZone = "Ladies Lounge";

                binding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
                binding.ladiesLounge.setTextColor(getResources().getColor(R.color.white));

                binding.barCounter.setBackgroundColor(getResources().getColor(R.color.white));
                binding.barCounter.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.danceSection.setBackgroundColor(getResources().getColor(R.color.white));
                binding.danceSection.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.smokingZone.setBackgroundColor(getResources().getColor(R.color.white));
                binding.smokingZone.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.vipArea.setBackgroundColor(getResources().getColor(R.color.white));
                binding.vipArea.setTextColor(getResources().getColor(R.color.icon_color_blue));

                settingnavigation2(seletedId, seletedType, "2");
            }
        });
        binding.danceSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletedZone = "Dance Section";

                binding.danceSection.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
                binding.danceSection.setTextColor(getResources().getColor(R.color.white));

                binding.barCounter.setBackgroundColor(getResources().getColor(R.color.white));
                binding.barCounter.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.white));
                binding.ladiesLounge.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.smokingZone.setBackgroundColor(getResources().getColor(R.color.white));
                binding.smokingZone.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.vipArea.setBackgroundColor(getResources().getColor(R.color.white));
                binding.vipArea.setTextColor(getResources().getColor(R.color.icon_color_blue));

                settingnavigation2(seletedId, seletedType, "3");
            }
        });
        binding.smokingZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletedZone = "Smoking Zone";

                binding.smokingZone.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
                binding.smokingZone.setTextColor(getResources().getColor(R.color.white));

                binding.barCounter.setBackgroundColor(getResources().getColor(R.color.white));
                binding.barCounter.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.white));
                binding.ladiesLounge.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.danceSection.setBackgroundColor(getResources().getColor(R.color.white));
                binding.danceSection.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.vipArea.setBackgroundColor(getResources().getColor(R.color.white));
                binding.vipArea.setTextColor(getResources().getColor(R.color.icon_color_blue));

                settingnavigation2(seletedId, seletedType, "4");
            }
        });
        binding.vipArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletedZone = "VIP Area";

                binding.vipArea.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
                binding.vipArea.setTextColor(getResources().getColor(R.color.white));

                binding.barCounter.setBackgroundColor(getResources().getColor(R.color.white));
                binding.barCounter.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.white));
                binding.ladiesLounge.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.danceSection.setBackgroundColor(getResources().getColor(R.color.white));
                binding.danceSection.setTextColor(getResources().getColor(R.color.icon_color_blue));
                binding.smokingZone.setBackgroundColor(getResources().getColor(R.color.white));
                binding.smokingZone.setTextColor(getResources().getColor(R.color.icon_color_blue));

                settingnavigation2(seletedId, seletedType, "5");
            }
        });
    }

    private void OpenListOfStores() {
        RecyclerView my_recycler_dailog;
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreenActivity.this);

        LayoutInflater inflater = LayoutInflater.from(builder.getContext());
        View dialogView = (View) inflater.inflate(R.layout.activity_list_of_stores, null);

        my_recycler_dailog = (RecyclerView) dialogView.findViewById(R.id.my_recycler_dailog);

        storeChangeModelClasses = new ArrayList<>();
        for (int i = 0; i < Table1.length(); i++) {
            StoreChangeModelClass navigationMenuModel = null;
//            NameIdModelReservedClass navigationMenuModel2 = null;

            try {
                navigationMenuModel = new StoreChangeModelClass(Table1.getJSONObject(i).getString("storeId"),
                        Table1.getJSONObject(i).getString("storeName"),
                        Table1.getJSONObject(i).getString("storeKey"),
                        Table1.getJSONObject(i).getString("currency"),
                        Table1.getJSONObject(i).getString("showImages"));

                storeChangeModelClasses.add(navigationMenuModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        GridLayoutManager mLayoutManager = new GridLayoutManager(HomeScreenActivity.this, 1);
        my_recycler_dailog.setLayoutManager(mLayoutManager);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        storesAdapter = new StoresAdapter(HomeScreenActivity.this, storeChangeModelClasses, dialog);
        my_recycler_dailog.setAdapter(storesAdapter);
        dialog.show();
        dialog.getWindow().setLayout(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);

    }

    public class GetOrderTypesByUserMethod extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Validating credentials...");

        }

        @Override
        protected String doInBackground(String... arg0) {
            JSONObject json = new JSONObject();

            try {
                json.put("userId", arg0[1]);
                json.put("uKey", arg0[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_GetOrderTypesByUser, ServiceHandler.POST, json, "");


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
                    parseGetUserData(result);
                } else {
                    ShowToast.toastMessage(HomeScreenActivity.this, "No Data");
                }


            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(HomeScreenActivity.this, "Error");
            }
        }
    }

    private void LoadDataMethod() {
        homeLeftModels = new ArrayList<>();
        for (int i = 0; i < TableCategoeirs.length(); i++) {
            HomeLeftModel navigationMenuModel = null;
            try {
                navigationMenuModel = new HomeLeftModel(TableCategoeirs.getJSONObject(i).getString("name"),
                        TableCategoeirs.getJSONObject(i).getString("orderTypeId"),
                        TableCategoeirs.getJSONObject(i).getString("count"));

                homeLeftModels.add(navigationMenuModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        homeLeftAdapter = new HomeLeftAdapter(HomeScreenActivity.this, homeLeftModels, "Home");
        binding.myRecycler.setAdapter(homeLeftAdapter);
        GridLayoutManager mLayoutManager = new GridLayoutManager(HomeScreenActivity.this, 1);
        binding.myRecycler.setLayoutManager(mLayoutManager);
        settingnavigation2(homeLeftModels.get(0).getId(), homeLeftModels.get(0).getTable_name(), "1");

    }

    public void GetOrderUser(String uKey, String user_id) {
        if (CheckNetworkConnection.isNetWorkConnectionAvailable(HomeScreenActivity.this)) {
            binding.storeTitle.setText(sharedPreferences.getString("StoreName", ""));
            new GetOrderTypesByUserMethod().execute(uKey, user_id);
        } else {
            ShowToast.toastMsgNetworkConeection(HomeScreenActivity.this);
        }
    }

    public void settingnavigation2(String getId, String tableType, String zoneId) {
        seletedId = getId;
        seletedType = tableType;
        totalListTableModelClasses = new ArrayList<>();
        if (zoneId.equals("1")) {
            binding.barCounter.setBackgroundColor(getResources().getColor(R.color.icon_color_blue));
            binding.barCounter.setTextColor(getResources().getColor(R.color.white));

            binding.ladiesLounge.setBackgroundColor(getResources().getColor(R.color.white));
            binding.ladiesLounge.setTextColor(getResources().getColor(R.color.icon_color_blue));
            binding.danceSection.setBackgroundColor(getResources().getColor(R.color.white));
            binding.danceSection.setTextColor(getResources().getColor(R.color.icon_color_blue));
            binding.smokingZone.setBackgroundColor(getResources().getColor(R.color.white));
            binding.smokingZone.setTextColor(getResources().getColor(R.color.icon_color_blue));
            binding.vipArea.setBackgroundColor(getResources().getColor(R.color.white));
            binding.vipArea.setTextColor(getResources().getColor(R.color.icon_color_blue));
        }
        if (getId.equals("1")) {
            binding.llZones.setVisibility(View.VISIBLE);
            binding.viewLine.setVisibility(View.VISIBLE);
        } else {
            binding.llZones.setVisibility(View.GONE);
            binding.viewLine.setVisibility(View.GONE);

        }

        for (int i = 0; i < TableNames.length(); i++) {
            TotalListTableModelClass navigationMenuModel = null;
            try {
                if (TableNames.getJSONObject(i).getString("orderTypeId").equals(getId) && TableNames.getJSONObject(i).getString("zoneId").equals(zoneId)) {

                    navigationMenuModel = new TotalListTableModelClass(TableNames.getJSONObject(i).getString("tableName"),
                            TableNames.getJSONObject(i).getString("tableId"),
                            TableNames.getJSONObject(i).getString("isOpen"), getId,
                            TableNames.getJSONObject(i).getInt("orderId"),
                            TableNames.getJSONObject(i).getBoolean("sentToBilling"),
                            tableType,
                            TableNames.getJSONObject(i).getString("customer"),
                            TableNames.getJSONObject(i).getString("saleAmount"),
                            TableNames.getJSONObject(i).getString("posTableId"));

                    totalListTableModelClasses.add(navigationMenuModel);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        totalListTableReservedAdapter = new TotalListTableReservedAdapter(HomeScreenActivity.this, totalListTableModelClasses);
        binding.myRecycler2.setAdapter(totalListTableReservedAdapter);
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(HomeScreenActivity.this, 6);
        binding.myRecycler2.setLayoutManager(mLayoutManager2);

    }

    public void CallToOpenTable(int positon) {
        if (totalListTableModelClasses.get(positon).getIsopen().equals("false")) {
            Intent i = new Intent(HomeScreenActivity.this, GuestInfoActivity.class);//GuestInfoActivity
            i.putExtra("table_id", totalListTableModelClasses.get(positon).getId());
            i.putExtra("pos_table_id", totalListTableModelClasses.get(positon).getPosTableId());
            i.putExtra("table_name", totalListTableModelClasses.get(positon).getTable_name());
            i.putExtra("order_type_id", totalListTableModelClasses.get(positon).getOrderType());
            i.putExtra("order_type_name", totalListTableModelClasses.get(positon).getOrderTypeName());
            i.putExtra("orderId", totalListTableModelClasses.get(positon).getOrderId());
            i.putExtra("zoneName", seletedZone);//payed

            startActivity(i);
        } /*else if (totalListTableModelClasses.get(positon).getIsopen().equals("freeze")) {
            orderTypeId = totalListTableModelClasses.get(positon).getOrderType();
            orderTypeName = totalListTableModelClasses.get(positon).getOrderTypeName();
            table_id = totalListTableModelClasses.get(positon).getId();
            pos_table_id = totalListTableModelClasses.get(positon).getPosTableId();
            table_name = totalListTableModelClasses.get(positon).getTable_name();
            StorderId = String.valueOf(totalListTableModelClasses.get(positon).getOrderId());

//            openDailogEdit("Open freeze table", totalListTableModelClasses.get(positon).getOrderId() + "");
            ValidataApiMethod("****", "OPENTABLE", "Open freeze table", totalListTableModelClasses.get(positon).getOrderId()+"");


        } else if (totalListTableModelClasses.get(positon).getIsopen().equals("true")) {*/ else {
            Intent i = new Intent(HomeScreenActivity.this, OrderAndBill.class);
            i.putExtra("orderTypeId", totalListTableModelClasses.get(positon).getOrderType());
            i.putExtra("orderTypeName", totalListTableModelClasses.get(positon).getOrderTypeName());
            i.putExtra("table_id", totalListTableModelClasses.get(positon).getId());
            i.putExtra("pos_table_id", totalListTableModelClasses.get(positon).getPosTableId());
            i.putExtra("table_name", totalListTableModelClasses.get(positon).getTable_name());
            i.putExtra("customer_id", "0");
            i.putExtra("orderId", totalListTableModelClasses.get(positon).getOrderId());
            i.putExtra("StatusOfOrder", totalListTableModelClasses.get(positon).getSentToBilling());//payed
            i.putExtra("zoneName", seletedZone);//payed

            startActivity(i);
        }
    }

    private void openDailogEdit(final String reason, final String orderId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreenActivity.this);

        LayoutInflater inflater = LayoutInflater.from(builder.getContext());
        View dialogView = (View) inflater.inflate(R.layout.dailog_table, null);

        GridView gridView = (GridView) dialogView.findViewById(R.id.grid_view);
        final TextView pin_tv = (TextView) dialogView.findViewById(R.id.pin_tv);
        final TextView pin_tv_dummy = (TextView) dialogView.findViewById(R.id.pin_tv_dummy);
        pin_tv_dummy.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeScreenActivity.this, R.layout.simple_list_item2, numbers);
        gridView.setAdapter(adapter);
        builder.setView(dialogView);


        dialogValidatePin = builder.create();
        dialogValidatePin.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialogValidatePin.getWindow().getAttributes();

        wmlp.gravity = Gravity.RIGHT;
        wmlp.x = 10;
        wmlp.y = 10;

        dialogValidatePin.show();
        dialogValidatePin.getWindow().setLayout(500, ViewGroup.LayoutParams.WRAP_CONTENT);


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
                    if (!pinVery[0].isEmpty()) {
                        ValidataApiMethod(pinVery[0], "OPENTABLE", reason, orderId);
                    } else {
                        ShowToast.toastMessage(HomeScreenActivity.this, "Please enter valid pin");
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
                        ShowToast.toastMessage(HomeScreenActivity.this, "Only 4 Digits pin is allowed");
                    }

                }


            }
        });

    }

    private void ValidataApiMethod(String pinNumber, String action, String reason, String orderIId) {
        if (CheckNetworkConnection.isNetWorkConnectionAvailable(HomeScreenActivity.this)) {
            String User_key = sharedPreferences.getString("StoreKey", "");

            new ValidataApiCall().execute(User_key, pinNumber, action, reason, orderIId);
        } else {
            ShowToast.toastMsgNetworkConeection(HomeScreenActivity.this);
        }
    }

    public class ValidataApiCall extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Loading...");
        }

        @Override
        protected String doInBackground(String... arg0) {
            JSONObject json = new JSONObject();

            try {
                json.put("uKey", arg0[0]);
                json.put("userId", sharedPreferences.getString("User_Id", ""));
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
                dismissProgress();

            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                dismissProgress();
                if (result != null) {
                    parseValidataApiCall(result);
                } else {
                    ShowToast.toastMessage(HomeScreenActivity.this, "No Data");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(HomeScreenActivity.this, "Error");
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
//                            dialogValidatePin.dismiss();
                            Intent i = new Intent(HomeScreenActivity.this, OrderAndBill.class);
                            i.putExtra("orderTypeId", orderTypeId);
                            i.putExtra("orderTypeName", orderTypeName);
                            i.putExtra("table_id", table_id);
                            i.putExtra("pos_table_id", pos_table_id);
                            i.putExtra("table_name", table_name);
                            i.putExtra("customer_id", "0");
                            i.putExtra("orderId", Integer.parseInt(StorderId));
                            i.putExtra("StatusOfOrder", false);//payed
                            i.putExtra("zoneName", seletedZone);//payed

                            startActivity(i);
                        } else {
                            ShowToast.toastMessage(HomeScreenActivity.this, "Invalid pin");
                        }
                    }
                } else {
                    if (jsonObj.has("message")) {
                        String message = jsonObj.getString("message");
                        ShowToast.toastMessage(HomeScreenActivity.this, message);

//                            showErrorDialog(message);
                    } else {
                        ShowToast.toastMessage(HomeScreenActivity.this, "No Data");
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            ShowToast.toastMessage(HomeScreenActivity.this, "No Data");
        }
    }

    public void parseGetUserData(String soapObject) throws ParseException {
        // TODO Auto-generated method stub
        try {
            if (soapObject != null) {
                JSONObject jsonObj = new JSONObject(soapObject);
                if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                    if (jsonObj.has("data")) {
                        JSONObject jsonObject = jsonObj.getJSONObject("data");
                        if (jsonObject.has("table")) {
                            JSONArray table1 = jsonObject.getJSONArray("table");
                            TableCategoeirs = table1;
                        }
                        if (jsonObject.has("table1")) {
                            JSONArray Table1 = jsonObject.getJSONArray("table1");
                            TableNames = Table1;
                        }
                        if (jsonObject.has("table2")) {
                            JSONArray Table2 = jsonObject.getJSONArray("table2");
                            TableZones = Table2;
                        }
                        LoadDataMethod();

                    } else {
                        ShowToast.toastMessage(HomeScreenActivity.this, "No Data");

                    }
                } else {
                    if (jsonObj.has("message")) {
                        String message = jsonObj.getString("message");
                        ShowToast.toastMessage(HomeScreenActivity.this, message);

//                            showErrorDialog(message);
                    } else {
                        ShowToast.toastMessage(HomeScreenActivity.this, "Error");
                    }
                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ShowToast.toastMessage(HomeScreenActivity.this, "Error");
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        new GetOrderTypesByUserMethod().execute(sharedPreferences.getString("StoreKey", ""), sharedPreferences.getString("User_Id", ""));
    }
}