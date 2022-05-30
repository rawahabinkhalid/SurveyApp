package com.matzsolutions.surveyapp.Retailor.ui.Support;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.matzsolutions.surveyapp.HaballError;
import com.matzsolutions.surveyapp.Loader;
import com.matzsolutions.surveyapp.MyJsonArrayRequest;
import com.matzsolutions.surveyapp.ProcessingError;
import com.matzsolutions.surveyapp.R;
import com.matzsolutions.surveyapp.SSL_HandShake;
import com.matzsolutions.surveyapp.TextField;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Retailer_Support_Ticket_View extends Fragment {
    private String Token, DistributorId;
    private Button btn_delete, btn_back, ticket_btn;
    private TextInputLayout layout_txt_business_name, layout_txt_email_address, layout_txt_mobile_number, layout_txt_comments;

    //    private String URL_SUPPORT_VIEW = "https://175.107.203.97:4014/api/contact//";
    private String URL_SUPPORT_VIEW = "https://surveyapp.matzsolutions.com/API/GetSurveyFilledResponse.php";
    private TextView tv_ticket_id;
    private TextInputEditText txt_business_name;
    private TextInputEditText txt_email_address;
    private TextInputEditText txt_mobile_number;
    private EditText txt_issue_type;
    private EditText txt_criticality;
    private EditText txt_preferred_contact_method;
    private TextInputEditText txt_comments;
    private String ID;
    private FragmentTransaction fragmentTransaction;
    private HashMap<String, String> RetailerIssueTypePrivateKVP = new HashMap<>();
    private HashMap<String, String> RetailerCriticalityPrivateKVP = new HashMap<>();
    private HashMap<String, String> RetailerContactingMethodKVP = new HashMap<>();
    private String RetailerIssueTypePrivateKVPString, RetailerCriticalityPrivateKVPString, RetailerContactingMethodKVPString;
    private Loader loader;
    private LinearLayout rl_fields;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_support__ticket__form__retailerform, container, false);

        loader = new Loader(getContext());
        rl_fields = root.findViewById(R.id.rl_fields);

        btn_back = root.findViewById(R.id.btn_back);
        ticket_btn = root.findViewById(R.id.ticket_btn);
        ticket_btn.setVisibility(View.GONE);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container_ret, new SupportFragment()).addToBackStack("tag");
                fragmentTransaction.commit();
            }
        });

        try {
            fetchSupportData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }


    private void fetchSupportData() throws JSONException {
        loader.showLoader();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("SupportId",
                Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesLogin = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        JSONObject map = new JSONObject();
        map.put("surveyId", sharedPreferences.getString("SupportId", ""));
        map.put("UserID", sharedPreferencesLogin.getString("UserID", ""));

        new SSL_HandShake().handleSSLHandshake();

        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_SUPPORT_VIEW, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loader.hideLoader();
                System.out.println(response);

                Gson gson = new Gson();
                Type type = new TypeToken<List<SurveyQuestionsModel>>() {
                }.getType();
                List<SurveyQuestionsModel> survey_questions = gson.fromJson(String.valueOf(response), type);

                int counter = 1;
                for (final SurveyQuestionsModel survey_question :
                        survey_questions) {
                    TextView textView1 = new TextView(getContext());
                    textView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

                    SpannableString ss1 = new SpannableString(survey_question.getQuestion());
                    ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
                    textView1.append("Question " + counter + ": ");
                    textView1.append(" ");
                    textView1.append(ss1);

                    textView1.setTextSize(18);
                    textView1.setTextColor(getResources().getColor(R.color.rv_box_color));
                    textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
                    rl_fields.addView(textView1);

                    TextView textView2 = new TextView(getContext());
                    textView2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    SpannableString ss2 = new SpannableString(survey_question.getAnswer());
                    ss2.setSpan(new StyleSpan(Typeface.BOLD), 0, ss2.length(), 0);
                    textView2.append("Answer: ");
                    textView2.append(" ");
                    textView2.append(ss2);

                    //                    textView2.setText("Answer: " + survey_question.getAnswer());
                    textView2.setTextSize(18);
                    textView2.setTextColor(getResources().getColor(R.color.rv_box_color));
                    textView2.setPadding(20, 20, 20, 40);// in pixels (left, top, right, bottom)
                    rl_fields.addView(textView2);

                    counter++;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new ProcessingError().showError(getContext());
                new HaballError().printErrorMessage(getContext(), error);

                error.printStackTrace();
                // Log.i("onErrorResponse", "Error");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);

    }


    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container_ret, new SupportFragment()).addToBackStack("null");
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });

    }

}