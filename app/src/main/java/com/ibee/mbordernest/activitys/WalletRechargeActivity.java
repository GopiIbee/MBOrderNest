package com.ibee.mbordernest.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.ibee.mbordernest.R;
import com.ibee.mbordernest.databinding.DailogSuccessBinding;
import com.ibee.mbordernest.databinding.OtpLayoutRechargeBinding;
import com.ibee.mbordernest.databinding.WalletRechargeLayoutBinding;
import com.ibee.mbordernest.network.CheckNetworkConnection;
import com.ibee.mbordernest.parserclass.ServiceHandler;
import com.ibee.mbordernest.utils.Constants;
import com.ibee.mbordernest.utils.ShowToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WalletRechargeActivity extends BaseActivity {

    AlertDialog dialog_wallet;
    AlertDialog dialog_suces;
    String User_key = "";
    String Login_user_id = "";
    private SharedPreferences sharedPreferences;
    String API_TOKEN = "";
    String ids[] = null;
    String names[] = null;

    String recids[] = null;
    String recnames[] = null;
    String recAmount[] = null;
    Spinner spinner_payment_mode;
    Spinner spinner_recharge_mode;

    String selected_id = "";
    String selected_rechared_id = "";
    String verificationid = "";
    FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    WalletRechargeLayoutBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = WalletRechargeLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WalletRechargeActivity.this);

        User_key = sharedPreferences.getString("StoreKey", "");
        API_TOKEN = sharedPreferences.getString("accesskey", "");
        Login_user_id = sharedPreferences.getString("User_Id", "");

        spinner_payment_mode = (Spinner) findViewById(R.id.spinner_payment_mode);
        spinner_recharge_mode = (Spinner) findViewById(R.id.spinner_recharge_mode);
        callGetPaymentModes();

        binding.sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.sendOtp.getText().toString().equals("Send Otp")) {
                    if (binding.customerName.getText().toString().isEmpty()) {
                        ShowToast.toastMessage(WalletRechargeActivity.this, "Please enter customer name");
                    } else if (binding.customerMobile.getText().toString().length() != 10) {
                        ShowToast.toastMessage(WalletRechargeActivity.this, "Please enter valid customer mobile number");
                    } else {
                        callSendOTP();
                    }
                } else {
                    if (binding.rechargeAmount.getText().toString().isEmpty()) {
                        ShowToast.toastMessage(WalletRechargeActivity.this, "Please enter Amount");
                    } else if (!spinner_payment_mode.getSelectedItem().toString().equals("Cash") &&
                            binding.transRefNo.getText().toString().isEmpty()) {
                        ShowToast.toastMessage(WalletRechargeActivity.this, "Please enter Transaction Ref No");
                    } else {
                        callOnRechargeApi();
                    }
                }
            }
        });

        spinner_payment_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < names.length; i++) {

                    if (spinner_payment_mode.getSelectedItem().equals(names[i])) {
                        if (names[i].toLowerCase().trim().equals("cash")) {
                            selected_id = ids[i];
                            binding.transRefNo.setVisibility(View.GONE);
                            binding.tvTransRefNo.setVisibility(View.GONE);
                        } else {
                            selected_id = ids[i];
                            binding.transRefNo.setVisibility(View.VISIBLE);
                            binding.tvTransRefNo.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_recharge_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < recnames.length; i++) {

                    if (spinner_recharge_mode.getSelectedItem().equals(recnames[i])) {
                        selected_rechared_id = recids[i];
                        binding.rechargeAmount.setText("Amount to be collected is " + recAmount[i] + "");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationid = s;
//                dismissProgress();

            }
        };
    }

    private void callFirebaseSMS(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void callSendOTP() {

        if (CheckNetworkConnection.isNetWorkConnectionAvailable(WalletRechargeActivity.this)) {
            new callSendOTPMathod().execute(User_key, binding.customerMobile.getText().toString());
        } else {
            ShowToast.toastMsgNetworkConeection(WalletRechargeActivity.this);
        }
    }

    public class callSendOTPMathod extends AsyncTask<String, String, String> {
        //        SoapObject soapObject = null;
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Sending OTP...");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();

            try {
                json.put("mobile", arg0[1]);
                json.put("uKey", arg0[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_InitRecharge, ServiceHandler.POST, json, "");


            } catch (IOException e) {
                e.printStackTrace();
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
                            openDailogRechage(jsonObj.getString("data"));
                        }
                    } else {
                        if (jsonObj.has("message")) {
                            String message = jsonObj.getString("message");
                            ShowToast.toastMessage(WalletRechargeActivity.this, message);
                        } else {
                            ShowToast.toastMessage(WalletRechargeActivity.this, getResources().getString(R.string.some_error));
                        }
                    }

                } else {
                    ShowToast.toastMessage(WalletRechargeActivity.this, getResources().getString(R.string.some_error));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(WalletRechargeActivity.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();

        }
    }


    private void openDailogRechage(String OTPResponce) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WalletRechargeActivity.this);

        OtpLayoutRechargeBinding otpLayoutRechargeBinding = OtpLayoutRechargeBinding.inflate(getLayoutInflater());
        View dialogView = otpLayoutRechargeBinding.getRoot();


        otpLayoutRechargeBinding.otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!otpLayoutRechargeBinding.otp1.getText().toString().equals("")) {
                    otpLayoutRechargeBinding.otp2.requestFocus();
                }

            }
        });
        otpLayoutRechargeBinding.otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otpLayoutRechargeBinding.otp2.getText().toString().equals("")) {
                    otpLayoutRechargeBinding.otp1.requestFocus();

                } else {
                    otpLayoutRechargeBinding.otp3.requestFocus();
                }
            }
        });
        otpLayoutRechargeBinding.otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otpLayoutRechargeBinding.otp3.getText().toString().equals("")) {
                    otpLayoutRechargeBinding.otp2.requestFocus();

                } else {
                    otpLayoutRechargeBinding.otp4.requestFocus();
                }
            }
        });
        otpLayoutRechargeBinding.otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otpLayoutRechargeBinding.otp4.getText().toString().equals("")) {
                    otpLayoutRechargeBinding.otp3.requestFocus();

                } else {
                    otpLayoutRechargeBinding.otp5.requestFocus();
                }

            }
        });
        otpLayoutRechargeBinding.otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otpLayoutRechargeBinding.otp5.getText().toString().equals("")) {
                    otpLayoutRechargeBinding.otp4.requestFocus();
                } else {
                    otpLayoutRechargeBinding.otp6.requestFocus();
                }
            }
        });
        otpLayoutRechargeBinding.otp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (otpLayoutRechargeBinding.otp6.getText().toString().equals("")) {
                    otpLayoutRechargeBinding.otp5.requestFocus();
                }
            }
        });


        otpLayoutRechargeBinding.otpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpvalue = otpLayoutRechargeBinding.otp1.getText().toString() +
                        otpLayoutRechargeBinding.otp2.getText().toString() + otpLayoutRechargeBinding.otp3.getText().toString() + otpLayoutRechargeBinding.otp4.getText().toString() +
                        otpLayoutRechargeBinding.otp5.getText().toString() + otpLayoutRechargeBinding.otp6.getText().toString();


                if (otpvalue.length() == 6) {
                    /*PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, otpvalue);
                    signInWithPhoneAuthCredential(credential);*/
                    if (otpvalue.equals(OTPResponce)) {

                        callGetPaymentModes();
                        binding.llHideLayout.setVisibility(View.VISIBLE);
                        binding.lldropdoen.setVisibility(View.VISIBLE);
//                    trans_ref_no.setVisibility(View.VISIBLE);
                        binding.commnet.setVisibility(View.VISIBLE);
                        binding.tvCommnet.setVisibility(View.VISIBLE);
                        binding.sendOtp.setText("Recharge Wallet");

                        binding.customerMobile.setEnabled(false);
                        binding.customerName.setEnabled(false);
                        dialog_wallet.dismiss();
                    }

                } else {

                    ShowToast.toastMessage(WalletRechargeActivity.this, "Invalid OTP");
                }
            }
        });


        builder.setView(dialogView);
        dialog_wallet = builder.create();
        dialog_wallet.show();
        dialog_wallet.setCanceledOnTouchOutside(false);
        dialog_wallet.getWindow().setLayout(800, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

  /*  private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            callGetPaymentModes();
                            ll_hide_layout.setVisibility(View.VISIBLE);
                            lldropdoen.setVisibility(View.VISIBLE);
//                    trans_ref_no.setVisibility(View.VISIBLE);
                            commnet.setVisibility(View.VISIBLE);
                            tv_commnet.setVisibility(View.VISIBLE);
                            send_otp.setText("Recharge Wallet");

                            customer_mobile.setEnabled(false);
                            customer_name.setEnabled(false);
                            dialog_wallet.dismiss();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                ShowToast.toastMessage(WalletRechargeActivity.this, "The verification code entered was invalid");

                            }
                        }
                    }
                });
    }
*/

    private void callGetPaymentModes() {
        if (CheckNetworkConnection.isNetWorkConnectionAvailable(WalletRechargeActivity.this)) {
            new callGetPaymentMathod().execute(User_key);
        } else {
            ShowToast.toastMsgNetworkConeection(WalletRechargeActivity.this);
        }
    }

    public class callGetPaymentMathod extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Payment Modes...");
        }

        @Override
        protected String doInBackground(String... arg0) {


            JSONObject json = new JSONObject();

            try {
                json.put("uKey", arg0[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_GetWalletSettings, ServiceHandler.POST, json, "");


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

                            if (jsonObject.has("paymentModes")) {
                                JSONArray PaymentModeArray = jsonObject.getJSONArray("paymentModes");

                                ids = new String[PaymentModeArray.length()];
                                names = new String[PaymentModeArray.length()];
                                for (int i = 0; i < PaymentModeArray.length(); i++) {
                                    ids[i] = PaymentModeArray.getJSONObject(i).getString("id");
                                    names[i] = PaymentModeArray.getJSONObject(i).getString("name");
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(WalletRechargeActivity.this, android.R.layout.simple_spinner_item, names);

                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                Spinner sItems = (Spinner) findViewById(R.id.spinner_payment_mode);
                                sItems.setAdapter(adapter);


                            }
                            if (jsonObject.has("rechargeModes")) {
                                JSONArray RechargeModeArray = jsonObject.getJSONArray("rechargeModes");

                                recids = new String[RechargeModeArray.length()];
                                recnames = new String[RechargeModeArray.length()];
                                recAmount = new String[RechargeModeArray.length()];
                                for (int i = 0; i < RechargeModeArray.length(); i++) {
                                    recids[i] = RechargeModeArray.getJSONObject(i).getString("id");
                                    recnames[i] = RechargeModeArray.getJSONObject(i).getString("name");
                                    recAmount[i] = RechargeModeArray.getJSONObject(i).getString("amount");
                                }


                                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(WalletRechargeActivity.
                                        this, android.R.layout.simple_spinner_item, recnames);

                                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                Spinner sItems2 = (Spinner) findViewById(R.id.spinner_recharge_mode);
                                sItems2.setAdapter(adapter2);

                            }

                        }
                    } else {
                        if (jsonObj.has("message")) {
                            String message = jsonObj.getString("message");
                            ShowToast.toastMessage(WalletRechargeActivity.this, message);

                        } else {
                            ShowToast.toastMessage(WalletRechargeActivity.this, getResources().getString(R.string.some_error));
                        }
                    }

                } else {
                    ShowToast.toastMessage(WalletRechargeActivity.this, getResources().getString(R.string.some_error));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(WalletRechargeActivity.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();
        }
    }


    private void callOnRechargeApi() {
        if (CheckNetworkConnection.isNetWorkConnectionAvailable(WalletRechargeActivity.this)) {
            new callDoRechargeMathod().execute(User_key,
                    binding.customerMobile.getText().toString(),
                    binding.customerName.getText().toString(),
                    selected_id,
                    binding.rechargeAmount.getText().toString(),
                    binding.transRefNo.getText().toString(),
                    binding.commnet.getText().toString(),
                    Login_user_id,
                    selected_rechared_id);
        } else {
            ShowToast.toastMsgNetworkConeection(WalletRechargeActivity.this);
        }
    }

    public class callDoRechargeMathod extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            showProgress("Recharge Processing...");
        }

        @Override
        protected String doInBackground(String... arg0) {

            JSONObject json = new JSONObject();

            try {
                json.put("uKey", arg0[0]);
                json.put("mobile", arg0[1]);
                json.put("name", arg0[2]);
                json.put("payId", arg0[3]);
                json.put("amt", arg0[4]);
                json.put("transNo", arg0[5]);
                json.put("comments", arg0[6]);
                json.put("userId", arg0[7]);
                json.put("recId", arg0[8]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceHandler sh = new ServiceHandler();
            try {
                response = sh.callToServer(Constants.BASE_URL + Constants.SERVICE_DoRechargeV1, ServiceHandler.POST, json, "");


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

                            AlertDialog.Builder builder = new AlertDialog.Builder(WalletRechargeActivity.this);


                            DailogSuccessBinding dailogSuccessBinding = DailogSuccessBinding.inflate(getLayoutInflater());
                            View dialogView = dailogSuccessBinding.getRoot();


                            dailogSuccessBinding.totalAmount.setText(jsonObj.getString("data"));

                            dailogSuccessBinding.ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(WalletRechargeActivity.this, HomeScreenActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });


                            dailogSuccessBinding.messageStatus.setText("Recharge Successfull !");
                            dailogSuccessBinding.textMessage.setText("customer wallet has been credited with " +
                                    binding.rechargeAmount.getText().toString() + " . Total available balance is");

                            builder.setView(dialogView);
                            dialog_suces = builder.create();
                            dialog_suces.show();
                            dialog_suces.setCanceledOnTouchOutside(false);
                            dialog_suces.getWindow().setLayout(1200, ViewGroup.LayoutParams.WRAP_CONTENT);

                        } else {
                            if (jsonObj.has("message")) {
                                String message = jsonObj.getString("message");

                                AlertDialog.Builder builder = new AlertDialog.Builder(WalletRechargeActivity.this);
                                DailogSuccessBinding dailogSuccessBinding = DailogSuccessBinding.inflate(getLayoutInflater());
                                View dialogView = dailogSuccessBinding.getRoot();


                                dailogSuccessBinding.ok.setText("Try again!");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    dailogSuccessBinding.ok.setBackground(getDrawable(R.drawable.gradiant_red));
                                }
                                dailogSuccessBinding.totalAmount.setVisibility(View.GONE);
                                dailogSuccessBinding.ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog_suces.dismiss();
                                    }
                                });


                                dailogSuccessBinding.messageStatus.setText("Recharge failed !");
                                dailogSuccessBinding.textMessage.setText(message);

                                builder.setView(dialogView);
                                dialog_suces = builder.create();
                                dialog_suces.show();
                                dialog_suces.setCanceledOnTouchOutside(false);


                                dialog_suces.getWindow().setLayout(1200, ViewGroup.LayoutParams.WRAP_CONTENT);

                            } else {
                                ShowToast.toastMessage(WalletRechargeActivity.this, getResources().getString(R.string.some_error));
                            }
                        }
                    }
                } else {
                    ShowToast.toastMessage(WalletRechargeActivity.this, getResources().getString(R.string.some_error));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ShowToast.toastMessage(WalletRechargeActivity.this, getResources().getString(R.string.some_error));
            }
            dismissProgress();
        }
    }
}