package com.matzsolutions.surveyapp.Retailor;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.matzsolutions.surveyapp.Loader;
import com.matzsolutions.surveyapp.R;
import com.matzsolutions.surveyapp.Retailer_Login.RetailerLogin;
import com.matzsolutions.surveyapp.Retailor.ui.Support.SupportFragment;
import com.matzsolutions.surveyapp.SSL_HandShake;
import com.techatmosphere.expandablenavigation.model.HeaderModel;
import com.techatmosphere.expandablenavigation.view.ExpandableNavigationListView;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class RetailorDashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private FragmentTransaction fragmentTransaction;
    private ImageView notification_icon;
    private DrawerLayout drawer;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    //    List<CustomExpandableListModel> headerList = new ArrayList<>();
//    HashMap<CustomExpandableListModel, List<CustomExpandableListModel>> childList = new HashMap<>();
    private ExpandableNavigationListView navigationExpandableListView;
    private String username, companyname, Token;
    private TextView tv_username, tv_user_company, footer_item_1;
    //    private TextView tv_username, tv_user_company;
    boolean doubleBackToExitPressedOnce = false;
    private Socket iSocket;
    private static final String URL = "https://175.107.203.97:4014/";
    private String UserId;
    private JSONArray userRights;
    private List<String> NavList = new ArrayList<>();
    private int notification = 0;
    private String URL_Logout = "https://175.107.203.97:4014/api/users/logout";
    private int UnReadNotifications = 0;
    //    private List<Retailer_Notification_Model> NotificationList = new ArrayList<>();
    private String language = "";
    private MultiStateToggleButton mstb_multi_id;
    Context context;
    private Switch toggle_online_payment;
    private boolean toggle_online_payment_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailor_dashboard);
        Paper.init(this);
//
//        mstb_multi_id = findViewById(R.id.mstb_multi_id);
//        mstb_multi_id.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
//            @Override
//            public void onValueChanged(int position) {
//                if (position == 0) {
//                    Paper.book().write("language", "en");
//                } else {
//                    Paper.book().write("language", "ur");
//                }
//                updateView((String) Paper.book().read("language"));
//                Log.d("mstb_multi_id", "Position: " + position);
//            }
//        });

        // selected language
        SharedPreferences languageType = getSharedPreferences("changeLanguage",
                Context.MODE_PRIVATE);
        language = languageType.getString("language", "");
        String defaultSelectedLanguage = language;

        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        username = sharedPreferences.getString("Name", "");
        companyname = sharedPreferences.getString("CompanyName", "");
        Token = sharedPreferences.getString("Login_Token", "");
        UserId = sharedPreferences.getString("UserId", "");

        new SSL_HandShake().handleSSLHandshake();

        IO.Options opts = new IO.Options();
//            opts.query = "userId=" + UserId;
//        try {
//            iSocket = IO.socket(URL, opts);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }


//        new MyAsyncTask().execute();

//        getNotificationCount();

        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        drawer =

                findViewById(R.id.drawer_layout_retailor);

        notification_icon = (ImageView) toolbar.findViewById(R.id.notification_icon_retailer);
        tv_username = toolbar.findViewById(R.id.tv_username);
        tv_user_company = toolbar.findViewById(R.id.tv_user_company);


        drawer =

                findViewById(R.id.drawer_layout_retailor);
        navigationExpandableListView =

                findViewById(R.id.expandable_navigation);

        footer_item_1 =

                findViewById(R.id.footer_item_1);
//        changeLanguage();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
//        drawer.setScrimColor(Color.parseColor("#33000000"));
        drawer.setScrimColor(

                getResources().

                        getColor(android.R.color.transparent));

        toggle.syncState();
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawer.setDrawerListener(toggle);

//        try {
//            userRights = new JSONArray(sharedPreferences.getString("UserRights", ""));
//            // Log.i("userRights", String.valueOf(userRights));
//        } catch (
//                JSONException e) {
//            e.printStackTrace();
//        }


        boolean UserAlert = false;
        boolean Distributor_Preferences = false;
        boolean Retailer_Profile = false;
        boolean Order_Add_Update = false;
        boolean Order_Export = false;
        boolean Order_View = false;
        boolean Kyc_add_update = false;
        boolean User_Change_Password = false;
        boolean Payment_Add_Update = false;
        boolean Payment_View = false;

//
//        UserAlert = true;
//        Distributor_Preferences = true;
//        Retailer_Profile = true;
//        Order_Add_Update = true;
//        Order_Export = true;
//        Order_View = true;
//        Kyc_add_update = true;
//        User_Change_Password = true;
//        Payment_Add_Update = true;
//        Payment_View = true;
//

//        for (
//                int i = 0; i < userRights.length(); i++) {
//            try {
//                JSONObject userRightsData = new JSONObject(String.valueOf(userRights.get(i)));
//                if (userRightsData.get("Title").equals("Distributor Preferences")) {
//                    Distributor_Preferences = true;
//                }
//                if (userRightsData.get("Title").equals("UserAlert")) {
//                    UserAlert = true;
//                }
//                if (userRightsData.get("Title").equals("Kyc add/update")) {
//                    Kyc_add_update = true;
//                }
//                if (userRightsData.get("Title").equals("Order Add/Update")) {
//                    Order_Add_Update = true;
//                }
//                if (userRightsData.get("Title").equals("Payment Add/Update")) {
//                    Payment_Add_Update = true;
//                }
//                if (userRightsData.get("Title").equals("Retailer Profile")) {
//                    Retailer_Profile = true;
//                }
//                if (userRightsData.get("Title").equals("User Change Password")) {
//                    User_Change_Password = true;
//                }
//                if (userRightsData.get("Title").equals("Payment View")) {
//                    Payment_View = true;
//                }
//                if (userRightsData.get("Title").equals("Order Export")) {
//                    Order_Export = true;
//                }
//                if (userRightsData.get("Title").equals("Order View")) {
//                    Order_View = true;
//                }
////                // Log.i("userRightsData", String.valueOf(userRights.get(i)));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

        SharedPreferences retailerInfo = getSharedPreferences("Retailer_UserRights",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor retailerInfo_editor = retailerInfo.edit();
        retailerInfo_editor.putString("UserAlert", String.valueOf(UserAlert));
        retailerInfo_editor.putString("Distributor_Preferences", String.valueOf(Distributor_Preferences));
        retailerInfo_editor.putString("Retailer_Profile", String.valueOf(Retailer_Profile));
        retailerInfo_editor.putString("Order_Add_Update", String.valueOf(Order_Add_Update));
        retailerInfo_editor.putString("Order_Export", String.valueOf(Order_Export));
        retailerInfo_editor.putString("Order_View", String.valueOf(Order_View));
        retailerInfo_editor.putString("Kyc_add_update", String.valueOf(Kyc_add_update));
        retailerInfo_editor.putString("User_Change_Password", String.valueOf(User_Change_Password));
        retailerInfo_editor.putString("Payment_Add_Update", String.valueOf(Payment_Add_Update));
        retailerInfo_editor.putString("Payment_View", String.valueOf(Payment_View));
        retailerInfo_editor.apply();

        if (!UserAlert)
            notification_icon.setVisibility(View.GONE);

        if (Payment_View || Order_View) {
//            SharedPreferences OrderId = getSharedPreferences("OrderId",
//                    Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor1 = OrderId.edit();
//            editor1.putString("OrderId", "7415");
//            editor1.putString("Status", "Pending");
//            editor1.putString("InvoiceUpload", "0");
//            editor1.putString("InvoiceStatus", "null");
//            editor1.commit();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.main_container_ret, new RetailerViewOrder()).addToBackStack("tag");
//            fragmentTransaction.commit();

//            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tabs());
//            fragmentTransaction.commit();
//            NavList.add(getResources().getString(R.string.dashboard));
        } else {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.main_container_ret, new SupportFragment());
            fragmentTransaction.commit();
        }

        if (Kyc_add_update)
            NavList.add(getResources().getString(R.string.my_network));
        if (Order_Add_Update)
            NavList.add(getResources().getString(R.string.place_order));
        if (Payment_Add_Update)
            NavList.add(getResources().getString(R.string.make_payment));
        if (Retailer_Profile)
            NavList.add(getResources().getString(R.string.profile));
        NavList.add(getResources().getString(R.string.support));
        NavList.add(getResources().getString(R.string.logout));


        tv_username.setText("Hi, " + username);
        tv_user_company.setText(companyname);


        navigationExpandableListView.init(this);
        if (Payment_View || Order_View)
            navigationExpandableListView.addHeaderModel(new

                    HeaderModel(getResources().getString(R.string.dashboard)));
        if (Kyc_add_update)
            navigationExpandableListView.addHeaderModel(new

                    HeaderModel(getResources().getString(R.string.my_network)));
        if (Order_Add_Update)
            navigationExpandableListView.addHeaderModel(new

                    HeaderModel(getResources().getString(R.string.place_order)));
        if (Payment_Add_Update)
            navigationExpandableListView.addHeaderModel(new

                    HeaderModel(getResources().getString(R.string.make_payment)));
        if (Retailer_Profile)
            navigationExpandableListView.addHeaderModel(new

                    HeaderModel(getResources().getString(R.string.profile)));
        navigationExpandableListView.addHeaderModel(new

                HeaderModel(getResources().getString(R.string.support)));
        navigationExpandableListView.addHeaderModel(new

                HeaderModel(getResources().getString(R.string.logout)));
//                .addHeaderModel(new HeaderModel("\n\n\n\nTerms And Conditions"))
        navigationExpandableListView.build()
                .

                        addOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                navigationExpandableListView.setSelected(groupPosition);
                                // Log.i("groupPosition", String.valueOf(groupPosition));


                                SharedPreferences retailerInfo = getSharedPreferences("Menu_Retailer",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor retailerInfo_editor = retailerInfo.edit();
                                retailerInfo_editor.putString("groupPosition", String.valueOf(groupPosition));
                                retailerInfo_editor.apply();


//                                if (NavList.contains(getResources().getString(R.string.dashboard)) && NavList.indexOf(getResources().getString(R.string.dashboard)) == id) {
////                        if (id == 0) {
//                                    // Log.i(getResources().getString(R.string.dashboard), "Dashboard Activity");
//                                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                                    fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tabs());
//                                    fragmentTransaction.commit();
//
//                                    drawer.closeDrawer(GravityCompat.START);
//                                } else if (NavList.contains(getResources().getString(R.string.my_network)) && NavList.indexOf(getResources().getString(R.string.my_network)) == id) {
////                        } else if (id == 1) {
//
//                                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
////                            fragmentTransaction.add(R.id.main_container_ret, new My_NetworkDashboard());
//                                    fragmentTransaction.add(R.id.main_container_ret, new My_Network_Fragment()).addToBackStack("tag");
//                                    fragmentTransaction.commit();
//                                    drawer.closeDrawer(GravityCompat.START);
//                                    // Log.i(getResources().getString(R.string.my_network), "My Network Activity");
//
//                                    drawer.closeDrawer(GravityCompat.START);
//                                } else if (NavList.contains(getResources().getString(R.string.place_order)) && NavList.indexOf(getResources().getString(R.string.place_order)) == id) {
//                                    SharedPreferences selectedProducts = getSharedPreferences("selectedProducts_retailer_own",
//                                            Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = selectedProducts.edit();
//                                    editor.putString("selected_products", "");
//                                    editor.putString("selected_products_qty", "");
//                                    editor.apply();
//                                    SharedPreferences selectedDraft = getSharedPreferences("FromDraft",
//                                            Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editorDraft = selectedDraft.edit();
//                                    editorDraft.putString("fromDraft", "");
//                                    editorDraft.apply();
//
////                        } else if (id == 2) {
////                            // Log.i(getResources().getString(R.string.place_order), "Place Order Activity");
////                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
////                            fragmentTransaction.add(R.id.main_container_ret, new PlaceOrderFragment());
////                            fragmentTransaction.commit();
////                            drawer.closeDrawer(GravityCompat.START);
//                                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                                    fragmentTransaction.replace(R.id.main_container_ret, new Retailer_Place_Order()).addToBackStack("tag");
//                                    fragmentTransaction.commit();
//                                    drawer.closeDrawer(GravityCompat.START);
//
//                                } else if (NavList.contains(getResources().getString(R.string.make_payment)) && NavList.indexOf(getResources().getString(R.string.make_payment)) == id) {
////                        } else if (id == 3) {
//                                    // Log.i(getResources().getString(R.string.make_payment), "Make Payment Activity");
//                                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                                    fragmentTransaction.add(R.id.main_container_ret, new CreatePaymentRequestFragment()).addToBackStack("tag1");
//                                    fragmentTransaction.commit();
//                                    drawer.closeDrawer(GravityCompat.START);
//
//                                } else if (NavList.contains(getResources().getString(R.string.profile)) && NavList.indexOf(getResources().getString(R.string.profile)) == id) {
////                        } else if (id == 4) {
//                                    // Log.i(getResources().getString(R.string.profile), "Profile Activity");
//                                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                                    fragmentTransaction.add(R.id.main_container_ret, new Profile_Tabs()).addToBackStack("tag");
//                                    fragmentTransaction.commit();
//                                    drawer.closeDrawer(GravityCompat.START);
//                                } else if (NavList.contains(getResources().getString(R.string.support)) && NavList.indexOf(getResources().getString(R.string.support)) == id) {
                                if (NavList.contains(getResources().getString(R.string.support)) && NavList.indexOf(getResources().getString(R.string.support)) == id) {
//                        } else if (id == 5) {
                                    // Log.i(getResources().getString(R.string.support), "Support Activity");
                                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.main_container_ret, new SupportFragment()).addToBackStack("tag");
                                    fragmentTransaction.commit();
                                    drawer.closeDrawer(GravityCompat.START);
                                } else if (NavList.contains(getResources().getString(R.string.logout)) && NavList.indexOf(getResources().getString(R.string.logout)) == id) {
                                    logoutUser();
                                    drawer.closeDrawer(GravityCompat.START);
                                }

                                return false;
                            }
                        });

        navigationExpandableListView.setSelected(0);

    }

    private void logoutUser() {
        final Loader loader = new Loader(RetailorDashboard.this);

        final AlertDialog alertDialog = new AlertDialog.Builder(RetailorDashboard.this).create();
        LayoutInflater inflater = LayoutInflater.from(RetailorDashboard.this);
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
        tv_discard.setText(getResources().getString(R.string.logout));
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText(getResources().getString(R.string.logout_msg));
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setText(getResources().getString(R.string.logout));
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
                loader.showLoader();
                new SSL_HandShake().handleSSLHandshake();
//
////                final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(RetailorDashboard.this);
//
//                BooleanRequest sr = new BooleanRequest(Request.Method.DELETE, URL_Logout, null, new Response.Listener<Boolean>() {
//                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//                    @Override
//                    public void onResponse(Boolean result) {
//                        loader.hideLoader();
//
//                        // Log.i(getResources().getString(R.string.logout), "Logout Activity");
//                        SharedPreferences login_token = getSharedPreferences("LoginToken",
//                                Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = login_token.edit();
//                        editor.remove("Login_Token");
//                        editor.remove("User_Type");
//                        editor.remove("Retailer_Id");
//                        editor.remove("username");
//                        editor.remove("CompanyName");
//                        editor.remove("UserId");
//                        editor.commit();

                        Intent intent = new Intent(RetailorDashboard.this, RetailerLogin.class);
                        startActivity(intent);
                        finish();
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        loader.hideLoader();
//                        error.printStackTrace();
////                        new ProcessingError().showError(RetailorDashboard.this);
//                        SharedPreferences login_token = getSharedPreferences("LoginToken",
//                                Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = login_token.edit();
//                        editor.remove("Login_Token");
//                        editor.remove("User_Type");
//                        editor.remove("Retailer_Id");
//                        editor.remove("username");
//                        editor.remove("CompanyName");
//                        editor.remove("UserId");
//                        editor.commit();
//
//
//                        Intent intent = new Intent(RetailorDashboard.this, Register_Activity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }) {
//
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("Authorization", "bearer " + Token);
//                        params.put("Content-Type", "application/json");
//
//                        return params;
//                    }
//                };
//                sr.setRetryPolicy(new DefaultRetryPolicy(
//                        15000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                Volley.newRequestQueue(RetailorDashboard.this).add(sr);
            }
        });

        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
        img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    protected void onPause() {
//        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
//        if (!tasks.isEmpty()) {
//            ComponentName topActivity = tasks.get(0).topActivity;
//            if (!topActivity.getPackageName().equals(getPackageName())) {
//                // Log.i("DebugState", "in pause");
//                logoutOnDestroy();
//
//                Intent intent = new Intent(RetailorDashboard.this, Register_Activity.class);
//                startActivity(intent);
//                finish();
//            }
//        }
        super.onPause();
    }

    @Override
    protected void onStop() {
//        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
//        if (!tasks.isEmpty()) {
//            ComponentName topActivity = tasks.get(0).topActivity;
//            if (!topActivity.getPackageName().equals(getPackageName())) {
//                // Log.i("DebugState", "in stop");
//                logoutOnDestroy();
//            }
//        }
        super.onStop();
    }

//
//    private Emitter.Listener onNewMessage = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    String username;
//                    String message;
//                    try {
//                        username = data.getString("username");
//                        message = data.getString("message");
//                    } catch (JSONException e) {
//                        return;
//                    }
//
//                    // add the message to view
//                    addMessage(username, message);
//                }
//            });
//        }
//    };
//
//    @Override
//    protected void onDestroy() {
////        // Log.i("DebugState", "in destroy");
////
////        logoutOnDestroy();
//        super.onDestroy();
//
////        // Log.i("Destroyed", "destroyed");
//    }
//
//    private void logoutOnDestroy() {
//        // Log.i("Destroyed1", "destroyed");
////        new SSL_HandShake().handleSSLHandshake();
//        BooleanRequest sr = new BooleanRequest(Request.Method.DELETE, URL_Logout, null, new Response.Listener<Boolean>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(Boolean result) {
////                loader.hideLoader();
//                // Log.i("Destroyed12", "destroyed");
//
//                // Log.i(getResources().getString(R.string.logout), "Logout Activity");
//                SharedPreferences login_token = getSharedPreferences("LoginToken",
//                        Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = login_token.edit();
//                editor.remove("Login_Token");
//                editor.remove("User_Type");
//                editor.remove("Retailer_Id");
//                editor.remove("username");
//                editor.remove("CompanyName");
//                editor.remove("UserId");
//                editor.commit();
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                loader.hideLoader();
//                error.printStackTrace();
////                new ProcessingError().showError(RetailorDashboard.this);
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                params.put("Content-Type", "application/json");
//
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(RetailorDashboard.this).add(sr);
//    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            super.onBackPressed();
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
//            FragmentManager fm = getSupportFragmentManager();
//            if (fm.getBackStackEntryCount() == 0) {
            if (doubleBackToExitPressedOnce) {
//                    super.onBackPressed();
//                    finishAffinity();
                logoutUser();

                return;
            }
            this.doubleBackToExitPressedOnce = true;
            //Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1500);
//            } else {
////            super.onBackPressed();
//                fm.popBackStack();
//            }
        }
    }
//
//    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Thread.sleep(1000);
//                getNotificationCount();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            new MyAsyncTask().execute();
//        }
//
//
//        private void getNotificationCount() {
////            iSocket.emit("userId", UserId);
//
//            if (iSocket.connected()) {
//                iSocket.emit("userId", UserId);
//                iSocket.on("userId" + UserId, new Emitter.Listener() {
//                    @Override
//                    public void call(final Object... args) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                JSONObject data = (JSONObject) args[0];
////                                // Log.i("notificationTest", "String.valueOf(args)");
////                                // Log.i("Notification_found", String.valueOf(data));
//                                try {
////                                    // Log.i("notificationTest", String.valueOf(data.get("UnSeenCount")));
////                                    Toast.makeText(RetailorDashboard.this, String.valueOf(data.get("UnSeenCount")), Toast.LENGTH_SHORT).show();
//                                    Gson gson = new Gson();
//                                    Type type = new TypeToken<List<Retailer_Notification_Model>>() {
//                                    }.getType();
//
//                                    notification = Integer.parseInt(String.valueOf(data.get("UnSeenCount")));
//                                    NotificationList = gson.fromJson(String.valueOf(data.getJSONArray("data")), type);
//                                    int i = 0;
//                                    for (i = 0; i < NotificationList.size(); i++) {
//                                        if (NotificationList.get(i).getSeen().equals("0"))
//                                            break;
//                                    }
//
//                                    if (notification != 0) {
//                                        notification_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_black_24dp));
//                                        if (UnReadNotifications != 0 && UnReadNotifications != notification) {
//                                            String CHANNEL_ID = getString(R.string.default_notification_channel_id);
//                                            createNotificationChannel();
//
//
//                                            Intent intent = new Intent(RetailorDashboard.this, RetailerLogin.class);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                            PendingIntent pendingIntent = PendingIntent.getActivity(RetailorDashboard.this, 0 /* Request code */, intent,
//                                                    PendingIntent.FLAG_ONE_SHOT);
//
//                                            String channelId = getString(R.string.default_notification_channel_id);
//                                            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                                            NotificationCompat.Builder notificationBuilder =
//                                                    new NotificationCompat.Builder(RetailorDashboard.this, channelId)
//                                                            .setSmallIcon(R.mipmap.ic_launcher_round)
//                                                            .setContentTitle(NotificationList.get(i).getSubject())
//                                                            .setContentText(NotificationList.get(i).getAlertMessage())
//                                                            .setAutoCancel(true)
//                                                            .setSound(defaultSoundUri)
//                                                            .setStyle(new NotificationCompat.BigTextStyle()
//                                                                    .bigText(NotificationList.get(i).getAlertMessage()))
//                                                            .setContentIntent(pendingIntent);
//
//                                            NotificationManager notificationManager =
//                                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                                            // Since android Oreo notification channel is needed.
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                                NotificationChannel channel = new NotificationChannel(channelId,
//                                                        "Channel human readable title",
//                                                        NotificationManager.IMPORTANCE_DEFAULT);
//                                                notificationManager.createNotificationChannel(channel);
//                                            }
//
//                                            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//
//
//                                            // Log.i("Notification_found", "new notification");
//                                        }
//                                        UnReadNotifications = notification;
//                                    } else {
//                                        notification_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_notifications_black_24dp));
//                                    }
//                                } catch (
//                                        JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                });
//
//            }
//
//
//        }
//
//    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification_Haball_Retailer";
            String description = "Notification_Haball_Retailer";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String CHANNEL_ID = getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //
//    private class MyAsyncTaskForMenu extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            getSelectedMenuItem();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            new MyAsyncTaskForMenu().execute();
//        }
//
//
//        private void getSelectedMenuItem() {
//            SharedPreferences sharedPreferences = getSharedPreferences("Menu_Retailer",
//                    Context.MODE_PRIVATE);
//            int tempGroupPosition = Integer.parseInt(sharedPreferences.getString("groupPosition", "-1"));
//            if(tempGroupPosition != myGroupPosition) {
//                myGroupPosition = tempGroupPosition;
//                navigationExpandableListView.setSelected(myGroupPosition);
//
//            }
//
//        }
//
//    }
//    void changeLanguage() {
//        ChangeLanguage changeLanguage = new ChangeLanguage();
//        changeLanguage.changeLanguage(this, language);
//        footer_item_1.setText(R.string.term_and_condition);
//        if (language.equals("ur")) {
//            mstb_multi_id.setValue(1);
//            // btn_login.setText(R.string.login);
////        layout_username.setHint(getResources().getString(R.string.user_name));
////        layout_password.setHint(getResources().getString(R.string.password));
//            //btn_password.setText(R.string.Forgot_Password);
//            //btn_support.setText(R.string.need_support);
//        } else {
//            mstb_multi_id.setValue(0);
//        }
//    }
//
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(LanguageHelper.onAttach(newBase, language));
//    }
//
//    private void updateView(String lang) {
//        context = LanguageHelper.setLocale(this, lang);
//        Resources resources = context.getResources();
//        SharedPreferences languageType = getSharedPreferences("changeLanguage",
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = languageType.edit();
//        editor.putString("language", lang);
//        editor.apply();
//        ChangeLanguage changeLanguage = new ChangeLanguage();
//        changeLanguage.changeLanguage(this, lang);
//
//        if (!language.equals(lang)) {
////            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container_ret);
////            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
////            fragmentTransaction.detach(currentFragment);
////            fragmentTransaction.attach(currentFragment);
////            fragmentTransaction.commit();
//
//            final Loader loader = new Loader(RetailorDashboard.this);
//            loader.showLoader();
//
//            try {
//                new CountDownTimer(5000, 100) {
//
//                    public void onTick(long millisUntilFinished) {
//                    }
//
//                    public void onFinish() {
//                        // Log.i("mContext_hide", String.valueOf(mContext));
//                        loader.hideLoader();
//                        Intent intent = new Intent(RetailorDashboard.this, RetailorDashboard.class);
//                        startActivity(intent);
//
//                    }
//                }.start();
//            } catch (IllegalArgumentException ignored) {
//
//            }
//
//        }
//    }
}
