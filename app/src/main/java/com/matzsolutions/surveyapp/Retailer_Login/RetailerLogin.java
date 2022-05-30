package com.matzsolutions.surveyapp.Retailer_Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.matzsolutions.surveyapp.CustomToast;
import com.matzsolutions.surveyapp.HaballError;
import com.matzsolutions.surveyapp.Loader;
import com.matzsolutions.surveyapp.ProcessingError;
import com.matzsolutions.surveyapp.R;
import com.matzsolutions.surveyapp.Retailor.RetailorDashboard;
import com.matzsolutions.surveyapp.Retailor.Retailor_SignUp.SignUp;
import com.matzsolutions.surveyapp.SSL_HandShake;
import com.matzsolutions.surveyapp.TextField;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class RetailerLogin extends AppCompatActivity {

    private Button btn_login, btn_signup;
    //    private Button btn_signup;
    public ImageButton btn_back;
    private TextInputEditText et_username, et_password;
    private TextInputLayout layout_username, layout_password;
    private Toolbar tb;
    private RequestQueue queue;
    private String URL_Token = "https://surveyapp.matzsolutions.com/API/Login.php";
    //    private String URL_FORGOT_PASSWORD = "https://175.107.203.97:4014/api/Users/forgot";
//    private String URL_FORGOT_PASSWORD = "https://uatdistributor.haball.pk/api/users/forgot";
    private HttpURLConnection urlConnection = null;
    private java.net.URL url;
    private String token;
    private String success_text = "", language = "";
    //    private ProgressDialog progressDialog;
    private Loader loader;
    private String URL_Profile = "https://175.107.203.97:4014/api/retailer/";
    private TextView login_heading, footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_login);

////        Drawable background_drawable = getResources().getDrawable(R.drawable.background_logo);
////        background_drawable.setAlpha(80);
////        RelativeLayout rl_main_background = findViewById(R.id.rl_main_background);
////        rl_main_background.setBackground(background_drawable);
//        // selected Language Value
//        SharedPreferences languageType = getSharedPreferences("changeLanguage",
//                Context.MODE_PRIVATE);
//        language = languageType.getString("language", "");
//
//        login_heading = findViewById(R.id.login_heading);
//        footer = findViewById(R.id.footer);
//
//        SharedPreferences selectedProducts = getSharedPreferences("selectedProducts_retailer",
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = selectedProducts.edit();
//        editor.remove("selected_products_qty");
//        editor.remove("selected_products");
//        editor.commit();
//        String IsTermAndConditionAccepted = "";
//        String UpdatePassword = "";
//        String Token = "";
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        if (!sharedPreferences.getString("Login_Token", "").equals(""))
//            Token = sharedPreferences.getString("Login_Token", "");
//        if (!sharedPreferences.getString("IsTermAndConditionAccepted", "").equals(""))
//            IsTermAndConditionAccepted = sharedPreferences.getString("IsTermAndConditionAccepted", "");
//        if (!sharedPreferences.getString("UpdatePassword", "").equals(""))
//            UpdatePassword = sharedPreferences.getString("UpdatePassword", "");
//        // Log.i("Token Splash", Token);
//        // Log.i("User Type", sharedPreferences.getString("User_Type", ""));
////
////        if (!Token.equals("")) {
////            Intent intent = new Intent(RetailerLogin.this, RetailorDashboard.class);
////            StatusKVP statusKVP = new StatusKVP(getApplicationContext(), Token);
////            startActivity(intent);
////            finish();
////        }

        btn_login = findViewById(R.id.retailer_btn_login);
        btn_login.setEnabled(false);
        btn_login.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
//        btn_signup = findViewById(R.id.ret_btn_signup);
        btn_signup = findViewById(R.id.ret_btn_support);
        layout_username = findViewById(R.id.layout_username);
        layout_password = findViewById(R.id.layout_password);

//        layout_username.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
//        layout_password.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));

        loader = new Loader(RetailerLogin.this);

        et_username = findViewById(R.id.txt_username);
        et_password = findViewById(R.id.txt_password);

        new TextField().changeColor(this, layout_username, et_username);
        new TextField().changeColor(this, layout_password, et_password);

        nullifySharedPreference();

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (layout_username.getDefaultHintTextColor() == ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color))) {
                    layout_username.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                    et_username.setTextColor(getResources().getColor(R.color.textcolor));
                    layout_username.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                }

                if (layout_password.getDefaultHintTextColor() == ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color))) {
                    layout_password.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                    layout_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                    et_password.setTextColor(getResources().getColor(R.color.textcolor));
                    layout_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (layout_username.getDefaultHintTextColor() == ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color))) {
                    layout_username.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                    et_username.setTextColor(getResources().getColor(R.color.textcolor));
                    layout_username.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                }

                if (layout_password.getDefaultHintTextColor() == ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color))) {
                    layout_password.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                    layout_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                    et_password.setTextColor(getResources().getColor(R.color.textcolor));
                    layout_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFieldsForEmptyValues();

            }
        };

        et_username.addTextChangedListener(textWatcher);
        et_password.addTextChangedListener(textWatcher);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View customView = inflater.inflate(R.layout.actio   n_bar_main, null);
//
//        actionBar.setCustomView(customView);
//        actionBar.setDisplayShowCustomEnabled(true);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        LayoutInflater inflater = LayoutInflater.from(this);

        View customView = inflater.inflate(R.layout.action_bar_main_without_back, null);


        bar.setCustomView(customView);
        bar.setDisplayShowCustomEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        bar.setTitle("");
//        btn_back = customView.findViewById(R.id.btn_back);
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RetailerLogin.this, Register_Activity.class);
//                startActivity(intent);
//            }
//        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(RetailerLogin.this, RetailorDashboard.class);
//                startActivity(intent);
                try {
                    loginRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        btn_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(RetailerLogin.this, SignUp.class);
//                startActivity(intent);
//            }
//        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RetailerLogin.this, SignUp.class);
                startActivity(intent);
            }
        });
//
//        btn_password.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RetailerLogin.this, Forgot_Pass_Retailer.class);
//                startActivity(intent);
//            }
//        });


////        et_username.setText("kamran.khan@mailinator.com");
//        et_username.setText("shabbir.khan@mailinator.com");
////        et_username.setText("uzair.ali@mailinator.com");
//        et_password.setText("@dmin123");
        checkFieldsForEmptyValues();
//        changeLanguage();
    }

    private void nullifySharedPreference() {
        SharedPreferences login_token = getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = login_token.edit();
        editor.putString("Login_Token", "");
        editor.putString("User_Type", "");
        editor.putString("Retailer_Id", "");
        editor.putString("username", "");
        editor.putString("CompanyName", "");
        editor.putString("IsTermAndConditionAccepted", "");
        editor.putString("UserId", "");

        editor.commit();

        SharedPreferences retailerInfo = getSharedPreferences("RetailerInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor retailerInfo_editor = retailerInfo.edit();
        retailerInfo_editor.putString("RetailerCode", "");
        retailerInfo_editor.putString("RetailerID", "");
        retailerInfo_editor.apply();

        SharedPreferences companyId = getSharedPreferences("SendData",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editorCompany = companyId.edit();
        editorCompany.putString("first_name", "");
        editorCompany.putString("email", "");
        editorCompany.putString("phone_number", "");
        editorCompany.apply();
    }

    private void checkFieldsForEmptyValues() {
        String username_ = et_username.getText().toString();
        String password = et_password.getText().toString();

        if (username_.equals("") || password.equals("")) {
            btn_login.setEnabled(false);
            btn_login.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            btn_login.setEnabled(true);
            btn_login.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }


    private void loginRequest() throws JSONException {
        loader.showLoader();

        JSONObject map = new JSONObject();
        map.put("email", et_username.getText().toString());
        map.put("password", et_password.getText().toString());
//        map.put("grant_type", "password");
        // Log.i("map", String.valueOf(map));
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(RetailerLogin.this);
        new SSL_HandShake().handleSSLHandshake();

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_Token, map, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
//                loader.hideLoader();
                 Log.i("result_ret", String.valueOf(result));

                try {
                    if (result.has("message") && !result.getString("message").isEmpty()) {
                        loader.hideLoader();
                        new CustomToast().showToast(RetailerLogin.this, result.getString("message"));
                    } else if (!result.getString("UserID").isEmpty()) {
                        SharedPreferences login_token = getSharedPreferences("LoginToken",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = login_token.edit();
                        editor.putString("UserID", result.getString("UserID"));
                        editor.putString("OrganizationID", result.getString("OrganizationID"));
                        editor.putString("Name", result.getString("Name"));
                        editor.putString("Email", result.getString("Email"));
                        editor.putString("Contact", result.getString("Contact"));
                        editor.putString("OrganizationID", result.getString("OrganizationID"));
                        editor.putString("CompanyName", result.getString("OrganizationName"));
                        editor.apply();

                        Intent login_intent = new Intent(RetailerLogin.this, RetailorDashboard.class);
                        startActivity(login_intent);
                        finish();
                    }
////                    if (!result.get("access_token").toString().isEmpty()) {
////                        token = result.get("access_token").toString();
////                        JSONObject userAccount = new JSONObject(String.valueOf(result.get("UserAccount")));
//////                        userRight = new JSONArray(String.valueOf(userAccount.getJSONArray("UserRights")));
//////                        // Log.i("userRight", String.valueOf(userRight));
////                        final String IsTermAndConditionAccepted = userAccount.get("IsTermAndConditionAccepted").toString();
////                        final String UpdatePassword = userAccount.get("UpdatePassword").toString();
////                        String userRights = userAccount.get("UserRights").toString();
////                        // Log.i("user account => ", userAccount.get("RetailerID").toString());
////                        final String RetailerId = userAccount.get("RetailerID").toString();
////                        final String RetailerCode = userAccount.get("RetailerCode").toString();
////                        String username = userAccount.get("Username").toString();
////                        String CompanyName = userAccount.get("CompanyName").toString();
////                        String ID = userAccount.get("UserId").toString();
////                        final String FirstName = userAccount.get("Name").toString();
////                        final String EmailAddress = userAccount.get("RetailerEmail").toString();
////                        final String Mobile = userAccount.get("RetailerMobile").toString();
////
////                        SharedPreferences login_token = getSharedPreferences("LoginToken",
////                                Context.MODE_PRIVATE);
////                        SharedPreferences.Editor editor = login_token.edit();
////                        editor.putString("Login_Token", token);
////                        editor.putString("User_Type", "Retailer");
////                        editor.putString("Online_Payments", "0");
////                        editor.putString("Retailer_Id", RetailerId);
////                        editor.putString("username", username);
////                        editor.putString("Name", FirstName);
////                        editor.putString("CompanyName", CompanyName);
////                        editor.putString("IsTermAndConditionAccepted", IsTermAndConditionAccepted);
////                        editor.putString("UpdatePassword", UpdatePassword);
////                        editor.putString("UserId", ID);
////                        editor.putString("UserRights", userRights);
////
////                        editor.apply();
////                        //updatePassword token
////
////                        StatusKVP statusKVP = new StatusKVP(RetailerLogin.this, token);
////                        URL_Profile = URL_Profile + RetailerId;
////
////                        new SSL_HandShake().handleSSLHandshake();
//////                        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(RetailerLogin.this);
////
////                        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_Profile, null, new Response.Listener<JSONObject>() {
////                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
////                            @Override
////                            public void onResponse(JSONObject result1) {
////                                loader.hideLoader();
////                                try {
////                                    SharedPreferences retailerInfo = getSharedPreferences("RetailerInfo",
////                                            Context.MODE_PRIVATE);
////                                    SharedPreferences.Editor retailerInfo_editor = retailerInfo.edit();
////                                    retailerInfo_editor.putString("RetailerCode", RetailerCode);
////                                    retailerInfo_editor.putString("RetailerID", RetailerId);
////                                    retailerInfo_editor.apply();
////
////                                    SharedPreferences companyId = getSharedPreferences("SendData",
////                                            Context.MODE_PRIVATE);
////                                    SharedPreferences.Editor editorCompany = companyId.edit();
////                                    editorCompany.putString("first_name", FirstName);
////                                    editorCompany.putString("email", EmailAddress);
////                                    editorCompany.putString("phone_number", Mobile);
////                                    editorCompany.putString("cnic", String.valueOf(result1.getString("CNIC")));
////                                    editorCompany.apply();
////                                    // Log.i("UpdatePassword", UpdatePassword);
////                                    if (IsTermAndConditionAccepted.equals("0")) {
////                                        Intent login_intent = new Intent(RetailerLogin.this, Retailer_TermsAndConditionsFragment.class);
////                                        startActivity(login_intent);
////                                        finish();
////                                    } else if (IsTermAndConditionAccepted.equals("1") && UpdatePassword.equals("0")) {
////                                        Intent login_intent = new Intent(RetailerLogin.this, Retailer_UpdatePassword.class);
////                                        startActivity(login_intent);
////                                        finish();
////                                    } else if (IsTermAndConditionAccepted.equals("1") && UpdatePassword.equals("1")) {
////                                        // Toast.makeText(RetailerLogin.this, "Login Success", Toast.LENGTH_LONG).show();
////                                        Intent login_intent = new Intent(RetailerLogin.this, RetailorDashboard.class);
////                                        startActivity(login_intent);
////                                        finish();
////                                    }
////
////
////                                } catch (JSONException e) {
////                                    e.printStackTrace();
////                                }
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                loader.hideLoader();
//                                error.printStackTrace();
//                                Toast.makeText(RetailerLogin.this, "error" + error, Toast.LENGTH_SHORT).show();
//
//                                new HaballError().printErrorMessage(RetailerLogin.this, error);
//                                new ProcessingError().showError(RetailerLogin.this);
//                                //Toast.makeText(RetailerLogin.this,error.toString(),Toast.LENGTH_LONG).show();
//                            }
//                        }) {
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                Map<String, String> params = new HashMap<String, String>();
//                                params.put("Authorization", "bearer " + token);
//                                return params;
//                            }
//                        };
//                        Volley.newRequestQueue(RetailerLogin.this).add(sr);
////                        RequestQueue requestQueue = Volley.newRequestQueue(RetailerLogin.this);
////                        requestQueue.add(sr);
//                    }
                } catch (JSONException e) {
                    loader.hideLoader();
                    new CustomToast().showToast(RetailerLogin.this, getResources().getString(R.string.invalid_password_error));
                    e.printStackTrace();
//                    try {
                    layout_username.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
                    layout_username.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                    et_username.setTextColor(getResources().getColor(R.color.error_stroke_color));

                    layout_password.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
                    layout_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                    layout_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                    et_password.setTextColor(getResources().getColor(R.color.error_stroke_color));
//                        Toast.makeText(RetailerLogin.this, result.get("ErrorMessage").toString(), Toast.LENGTH_LONG).show();
//                    } catch (JSONException ex) {
//                        ex.printStackTrace();
//                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                error.printStackTrace();

                new HaballError().printErrorMessage(RetailerLogin.this, error);
                new ProcessingError().showError(RetailerLogin.this);
                //Toast.makeText(RetailerLogin.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(sr);
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(sr);
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(RetailerLogin.this, Register_Activity.class);
//        startActivity(intent);
        finish();

    }
//
//    void changeLanguage() {
//        ChangeLanguage changeLanguage = new ChangeLanguage();
//        changeLanguage.changeLanguage(this, language);
////        if (language.equals("ur")) {
//            btn_login.setText(R.string.login);
//            layout_username.setHint(getResources().getString(R.string.user_name));
//            layout_password.setHint(getResources().getString(R.string.password));
//            btn_password.setText(R.string.Forgot_Password);
//            btn_support.setText(R.string.need_support);
//            footer.setText(R.string.all_rights_reserved);
//            login_heading.setText(R.string.login);
////        }
//    }
}
