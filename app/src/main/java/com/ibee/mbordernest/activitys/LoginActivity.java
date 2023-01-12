package com.ibee.mbordernest.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ibee.mbordernest.MainActivity;
import com.ibee.mbordernest.databinding.ActivityLoginBinding;
import com.ibee.mbordernest.network.CheckNetworkConnection;
import com.ibee.mbordernest.parserclass.ServiceHandler;
import com.ibee.mbordernest.utils.Constants;
import com.ibee.mbordernest.utils.ShowToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor mEditorObj;

    ActivityLoginBinding binding;
    JSONArray table1 = new JSONArray();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        mEditorObj = sharedPreferences.edit();


        if (sharedPreferences.getString("User_name", "") != null && !sharedPreferences.getString("User_name", "").equals("")
                && sharedPreferences.getString("StoreKey", "") != null && !sharedPreferences.getString("StoreKey", "").equals("")) {
            Intent i = new Intent(LoginActivity.this, HomeScreenActivity.class);
            startActivity(i);
            finish();
        }

        binding.login.setOnClickListener(v -> {

            binding.userId.getText().toString();
            if (binding.userId.getText().toString().equals("")) {
                ShowToast.toastMessage(LoginActivity.this, "Please Enter Email-Id");

            } else {
                binding.password.getText().toString();
                if (binding.password.getText().toString().equals("")) {
                    ShowToast.toastMessage(LoginActivity.this, "Please Enter Password");
                } else {
                    if (CheckNetworkConnection.isNetWorkConnectionAvailable(LoginActivity.this)) {

//                        new onLogin().execute(binding.userId.getText().toString(), binding.password.getText().toString());
                        onLogin(binding.userId.getText().toString(), binding.password.getText().toString());
                    } else {
                        ShowToast.toastMsgNetworkConeection(LoginActivity.this);
                    }
                }
            }
        });
    }

    private void onLogin(String userId, String password) {
        showProgress("Validating credentials...");
        StringRequest request = new StringRequest(Request.Method.POST,
                Constants.BASE_URL + Constants.SERVICE_ON_LOGIN,
                response -> {
                    if (response != null) {
                        try {
                            parseDataLogin(response);
                        } catch (ParseException e) {
                            dismissProgress();

                            e.printStackTrace();
                        }
                    } else {
                        ShowToast.toastMessage(LoginActivity.this, "No Data Found!");
                    }
                }, error -> {
            dismissProgress();
            Log.d("error", error.toString());
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userId);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Volley.newRequestQueue(this, new Constants.CustomHurlStack()).
                add(request);
    }

    private void parseDataLogin(String soapObject) throws ParseException {

        try {
            dismissProgress();
            if (soapObject != null) {
                JSONObject jsonObj = new JSONObject(soapObject);
                if (jsonObj.has("status") && jsonObj.getString("status").equals("success")) {
                    if (jsonObj.has("data")) {
                        JSONObject jsonObject = jsonObj.getJSONObject("data");
                        if (jsonObject.has("table")) {

                            JSONArray jsonArray = jsonObject.getJSONArray("table");
                            mEditorObj.putString("User_name", jsonArray.getJSONObject(0).getString("name")).commit();
                            mEditorObj.putString("User_Mobile", jsonArray.getJSONObject(0).getString("mobile")).commit();
                            mEditorObj.putString("User_Id", jsonArray.getJSONObject(0).getString("userId")).commit();

                            mEditorObj.putString("Email_id", jsonArray.getJSONObject(0).getString("email")).commit();
                            mEditorObj.putString("UniqueId", jsonArray.getJSONObject(0).getString("uniqueId")).commit();
                            mEditorObj.putString("roleName", jsonArray.getJSONObject(0).getString("roleName")).commit();
//                            loginUserId = jsonArray.getJSONObject(0).getString("userId");
                        }
                        if (jsonObject.has("table1")) {
                            table1 = jsonObject.getJSONArray("table1");

                            mEditorObj.putString("StoresArray", table1 + "").commit();
                            if (table1.length() > 1) {
                                Intent i = new Intent(LoginActivity.this, HomeScreenActivity.class);
                                i.putExtra("from", "Opendailog");
                                i.putExtra("Table1JsonArry", table1 + "");
                                startActivity(i);
                                finish();

                            } else {

                                mEditorObj.putString("StoreId", table1.getJSONObject(0).getString("storeId")).commit();
                                mEditorObj.putString("StoreName", table1.getJSONObject(0).getString("storeName")).commit();
                                mEditorObj.putString("StoreKey", table1.getJSONObject(0).getString("storeKey")).commit();
                                mEditorObj.putString("Currency", table1.getJSONObject(0).getString("currency")).commit();
                                mEditorObj.putString("ShowImages", table1.getJSONObject(0).getString("showImages")).commit();

                                Intent i = new Intent(LoginActivity.this, HomeScreenActivity.class);
                                i.putExtra("from", "login");
                                startActivity(i);
                                finish();
                            }

                        }


                    }
                } else {
                    ShowToast.toastMessage(LoginActivity.this, jsonObj.getString("message"));
                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ShowToast.toastMessage(LoginActivity.this, "Error");
            dismissProgress();

        }
    }


}