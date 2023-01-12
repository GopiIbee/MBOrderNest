package com.ibee.mbordernest.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ibee.mbordernest.databinding.ActivityGuestInfoBinding;
import com.ibee.mbordernest.network.CheckNetworkConnection;
import com.ibee.mbordernest.parserclass.ServiceHandler;
import com.ibee.mbordernest.utils.Constants;
import com.ibee.mbordernest.utils.ShowToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class GuestInfoActivity extends BaseActivity {
    private SharedPreferences sharedPreferences;

    ActivityGuestInfoBinding binding;
    String tableid = "", postableid = "", tableName = "", tableOrderType = "", tableOrderTypeName = "";
    int orderId = 0;
    String Login_user_id = "", Store_key = "";
    String API_TOKEN = "";
    Boolean StatusOfOrder = false;
    int nameStatus = 0;
    String zoneName = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuestInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            tableid = b.getString("table_id").toString();
            postableid = b.getString("pos_table_id").toString();
            tableName = b.getString("table_name").toString();
            tableOrderType = b.getString("order_type_id").toString();
            tableOrderTypeName = b.getString("order_type_name").toString();
            if (b.containsKey("zoneName")) {
                zoneName = b.getString("zoneName").toString();
            }
            orderId = b.getInt("orderId");

            if (b.containsKey("StatusOfOrder")) {

                StatusOfOrder = b.getBoolean("StatusOfOrder");

            }

        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GuestInfoActivity.this);

        Login_user_id = sharedPreferences.getString("User_Id", "");
        Store_key = sharedPreferences.getString("StoreKey", "");
        API_TOKEN = sharedPreferences.getString("accesskey", "");


        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.mobileNumber.getText().toString().isEmpty() && binding.mobileNumber.getText().toString().length() != 10) {
                    ShowToast.toastMessage(GuestInfoActivity.this, "Please enter valid mobile number");
                } else {
                    if (binding.guestName.getText().toString().isEmpty()) {
                        binding.guestName.setText("Guest");
                        nameStatus = 1;
                    }
                    if (binding.noPer.getText().toString().isEmpty()) {
                        binding.noPer.setText("0");
                    }
                    SaveCustomerMethod();

                }


            }
        });
        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent i = new Intent(GuestInfoActivity.this, OrderAndBill.class);
                    i.putExtra("orderTypeId", tableOrderType);
                    i.putExtra("orderTypeName", tableOrderTypeName);
                    i.putExtra("table_id", tableid);
                    i.putExtra("pos_table_id", postableid);

                    i.putExtra("table_name", tableName);
                    i.putExtra("customer_id", "0");
                    i.putExtra("orderId", orderId);
                    i.putExtra("StatusOfOrder", StatusOfOrder);
                    i.putExtra("zoneName", zoneName);//payed


                    startActivity(i);
                    finish();
                }


            }
        });
        binding.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void SaveCustomerMethod() {
        if (CheckNetworkConnection.isNetWorkConnectionAvailable(GuestInfoActivity.this)) {
            new SaveCustomer().execute();
//            SaveCustomer2();
        } else {
            ShowToast.toastMsgNetworkConeection(GuestInfoActivity.this);
        }

    }

    private void SaveCustomer2() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://...";
            JSONObject jsonBody = new JSONObject();
            String uname = binding.guestName.getText().toString();
            String mobile = binding.mobileNumber.getText().toString();
            String staddress = binding.address.getText().toString();

            jsonBody.put("uKey", Store_key);
            jsonBody.put("userId", orderId + "");
            jsonBody.put("username", uname);
            jsonBody.put("mobile", mobile);
            jsonBody.put("address", staddress);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {

                        responseString = String.valueOf(response.statusCode);

                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SaveCustomer() {
        try {
            JSONObject jsonBody = new JSONObject();

            String uname = binding.guestName.getText().toString();
            String mobile = binding.mobileNumber.getText().toString();
            String staddress = binding.address.getText().toString();

            jsonBody.put("uKey", Store_key);
            jsonBody.put("userId", orderId + "");
            jsonBody.put("username", uname);
            jsonBody.put("mobile", mobile);
            jsonBody.put("address", staddress);

/*

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL + Constants.SERVICE_SaveCustomer, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                    parseSaveCustomerData(response.toString());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {

                        responseString = String.valueOf(response.statusCode);

                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
*/

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    Constants.BASE_URL + Constants.SERVICE_SaveCustomer,
                    jsonBody, response -> {
                try {
                    parseSaveCustomerData(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, error -> {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException | JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                error.printStackTrace();
            }) {
                @NonNull
                @Override
                public Map<String, String> getHeaders() {
                    final Map<String, String> params = new HashMap<>();
                    return params;
                }
            };
            Volley.newRequestQueue(this, new Constants.CustomHurlStack()).add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class SaveCustomer extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Updating ...");
        }

        @Override
        protected String doInBackground(String... arg0) {


            JSONObject json = new JSONObject();

            String uname = binding.guestName.getText().toString();
            String mobile = binding.mobileNumber.getText().toString();
            String staddress = binding.address.getText().toString();
            try {
                json.put("uKey", Store_key);
                json.put("userId", orderId + "");
                json.put("username", uname);
                json.put("mobile", mobile);
                json.put("address", staddress);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_SaveCustomer, ServiceHandler.POST, json, "");


            } catch (IOException e) {
                dismissProgress();
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    parseSaveCustomerData(result);
                } else {
                    ShowToast.toastMessage(GuestInfoActivity.this, "No Data");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(GuestInfoActivity.this, "Error");
            }
            dismissProgress();

        }

    }

    public void parseSaveCustomerData(String soapObject) {
        try {
            if (soapObject != null) {
                JSONObject jsonObj = new JSONObject(soapObject);
                if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                    if (jsonObj.has("data")) {


                        Intent i = new Intent(GuestInfoActivity.this, OrderAndBill.class);
                        i.putExtra("orderTypeId", tableOrderType);
                        i.putExtra("orderTypeName", tableOrderTypeName);

                        i.putExtra("table_id", tableid);
                        i.putExtra("pos_table_id", postableid);
                        i.putExtra("table_name", tableName);
                        i.putExtra("customer_id", jsonObj.getString("data"));
                        i.putExtra("orderId", orderId);
                        i.putExtra("StatusOfOrder", StatusOfOrder);

                        i.putExtra("guest_name", binding.guestName.getText().toString());

                        i.putExtra("mobile_number", binding.mobileNumber.getText().toString());
                        i.putExtra("no_per", binding.noPer.getText().toString());
                        i.putExtra("ZoneName", zoneName);//payed

                        startActivity(i);

                        finish();


                    } else {
                        ShowToast.toastMessage(GuestInfoActivity.this, "No Data");

                    }
                } else {
                    if (jsonObj.has("message")) {
                        String message = jsonObj.getString("message");
                        ShowToast.toastMessage(GuestInfoActivity.this, message);

                    } else {
                        ShowToast.toastMessage(GuestInfoActivity.this, "No Data");
                    }
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ShowToast.toastMessage(GuestInfoActivity.this, "Error");
        }
    }


}
