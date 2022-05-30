package com.matzsolutions.surveyapp.Retailor.ui.Support;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.matzsolutions.surveyapp.CustomToast;
import com.matzsolutions.surveyapp.HaballError;
import com.matzsolutions.surveyapp.Loader;
import com.matzsolutions.surveyapp.MyJsonArrayRequest;
import com.matzsolutions.surveyapp.ProcessingError;
import com.matzsolutions.surveyapp.R;
import com.matzsolutions.surveyapp.Retailor.RetailorDashboard;
import com.matzsolutions.surveyapp.SSL_HandShake;
import com.matzsolutions.surveyapp.SelectDateFragment;
import com.matzsolutions.surveyapp.TextField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class Support_Ticket_Form_Fragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    //    private TextInputEditText BName, Email, MobileNo, Comment;
//    private TextInputLayout layout_BName, layout_Email, layout_MobileNo, layout_Comment;
    private String DistributorId;
    private Button btn_back, ticket_btn;
    //    private Spinner IssueType, critcicality, Preffered_Contact;
    //    private String URL_SPINNER_ISSUETYPE = "https://175.107.203.97:4014/api/lookup/public/ISSUE_TYPE_PRIVATE";
//    private String URL_SPINNER_CRITICALITY = "https://175.107.203.97:4014/api/lookup/public/CRITICALITY_PRIVATE";
//    private String URL_SPINNER_PREFFEREDCONTACT = "https://175.107.203.97:4014/api/lookup/public/CONTRACTING_METHOD";
    private String URL_TICkET = "https://surveyapp.matzsolutions.com/API/AddResponses.php";
    private String URL_FetchQuestions = "https://surveyapp.matzsolutions.com/API/GetQuestions.php";

    private String Token;
    private Typeface myFont;
    private int keyDel;
    private String first_name = "", email = "", phone_number = "";
    private Boolean changed = false;
    private Loader loader;
    private AlertDialog alertDialog;
    private LinearLayout rl_fields;
    private Map<Integer, String> answers = new HashMap<>();
    private int ExpectedAnswersCount = -1;
    private boolean isError = false;
    private int apiResponse = 0;
    private int year1, year2, month1, month2, date1, date2;
    private String fromDate = "";
    private EditText first_date;
    private int question_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_support__ticket__form__retailerform, container, false);

        alertDialog = new AlertDialog.Builder(getContext()).create();

        SharedPreferences data = getContext().getSharedPreferences("SendData",
                Context.MODE_PRIVATE);
        first_name = data.getString("first_name", "");
        email = data.getString("email", "");
        phone_number = data.getString("phone_number", "");

        loader = new Loader(getContext());

        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);

        btn_back = root.findViewById(R.id.btn_back);

        rl_fields = root.findViewById(R.id.rl_fields);
//
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final String txt_BName = String.valueOf(BName.getText());
//                final String txt_Email = String.valueOf(Email.getText());
//                final String txt_MobileNo = String.valueOf(MobileNo.getText());
//                final String txt_Comment = String.valueOf(Comment.getText());
//
//                BName.clearFocus();
//                Email.clearFocus();
//                MobileNo.clearFocus();
//                Comment.clearFocus();
//                if (!txt_BName.equals(first_name) || !txt_Email.equals(email) || !txt_MobileNo.equals(phone_number) || !txt_Comment.equals("") || !issueType.equals(getResources().getString(R.string.issue_type)) || !Criticality.equals(getResources().getString(R.string.criticality)) || !PrefferedContacts.equals(getResources().getString(R.string.preferred_method_of_contacting))) {
//                    showDiscardDialog();
//                } else {
//                        fm.popBackStack();
//                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
//                    editorOrderTabsFromDraft.putString("TabNo", "0");
//                    editorOrderTabsFromDraft.apply();

                Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                ((FragmentActivity) getContext()).startActivity(login_intent);
                ((FragmentActivity) getContext()).finish();

//                }

            }
        });

        ticket_btn = root.findViewById(R.id.ticket_btn);
        ticket_btn.setEnabled(false);
        ticket_btn.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (TextUtils.isEmpty(BName.getText().toString()) ||
//                        TextUtils.isEmpty(Email.getText().toString()) ||
////                        TextUtils.isEmpty(Comment.getText().toString()) ||
//                        TextUtils.isEmpty(MobileNo.getText().toString())) {
//
//                    Snackbar.make(v, "Please Enter All Required Fields", Snackbar.LENGTH_SHORT).show();
//                } else {
                try {
                    makeTicketAddRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                }

            }
        });


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keyDel = 0;
//                checkFieldsForEmptyValues();

            }
        };


        TextWatcher textWatcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keyDel = 0;
//                checkFieldsForEmptyValues();
//                checkEmail();
            }
        };
//
//        BName.addTextChangedListener(textWatcher);
//        Email.addTextChangedListener(textWatcher1);
//        MobileNo.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                MobileNo.setOnKeyListener(new View.OnKeyListener() {
//                    @Override
//                    public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                        if (keyCode == KeyEvent.KEYCODE_DEL)
//                            keyDel = 1;
//                        return false;
//                    }
//                });
//
//                if (keyDel == 0) {
//                    int len = MobileNo.getText().length();
//                    if (len == 4) {
//                        MobileNo.setText(MobileNo.getText() + "-");
//                        MobileNo.setSelection(MobileNo.getText().length());
//                    }
//                } else {
//                    keyDel = 0;
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // TODO Auto-generated method stub
//                checkFieldsForEmptyValues();
//                checkMobile();
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                // TODO Auto-generated method stub
//            }
//        });

        try {
            getQuestions();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        alertDialog = new AlertDialog.Builder(getContext()).create();
        return root;
    }

    private void showDiscardDialog() {
        // Log.i("CreatePayment", "In Dialog");
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText(getResources().getString(R.string.discard_text));
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
//                fm.popBackStack();
                SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                editorOrderTabsFromDraft.putString("TabNo", "0");
                editorOrderTabsFromDraft.apply();

                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container_ret, new SupportFragment());
                fragmentTransaction.commit();
//                Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
//                ((FragmentActivity) getContext()).startActivity(login_intent);
//                ((FragmentActivity) getContext()).finish();

            }
        });

        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
        img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        if (!alertDialog.isShowing())
            alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        View.OnKeyListener listener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    final String txt_BName = String.valueOf(BName.getText());
//                    final String txt_Email = String.valueOf(Email.getText());
//                    final String txt_MobileNo = String.valueOf(MobileNo.getText());
//                    final String txt_Comment = String.valueOf(Comment.getText());
//
////                    BName.clearFocus();
////                    Email.clearFocus();
////                    MobileNo.clearFocus();
////                    Comment.clearFocus();
//                    if (!txt_BName.equals(first_name) || !txt_Email.equals(email) || !txt_MobileNo.equals(phone_number) || !txt_Comment.equals("") || !issueType.equals(getResources().getString(R.string.issue_type)) || !Criticality.equals(getResources().getString(R.string.criticality)) || !PrefferedContacts.equals(getResources().getString(R.string.preferred_method_of_contacting))) {
//                        showDiscardDialog();
//                        return true;
//                    } else {
////                        fm.popBackStack();
//                        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
//                                Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
//                        editorOrderTabsFromDraft.putString("TabNo", "0");
//                        editorOrderTabsFromDraft.apply();
//
//                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container_ret, new SupportFragment());
//                        fragmentTransaction.commit();
//
//                    }
//                    return true;
//                }
                return false;
            }
        };
//        BName.setOnKeyListener(listener);
//        Email.setOnKeyListener(listener);
//        MobileNo.setOnKeyListener(listener);
//        Comment.setOnKeyListener(listener);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    final String txt_BName = String.valueOf(BName.getText());
//                    final String txt_Email = String.valueOf(Email.getText());
//                    final String txt_MobileNo = String.valueOf(MobileNo.getText());
//                    final String txt_Comment = String.valueOf(Comment.getText());
//
////                    BName.clearFocus();
////                    Email.clearFocus();
////                    MobileNo.clearFocus();
////                    Comment.clearFocus();
//                    if (!txt_BName.equals(first_name) || !txt_Email.equals(email) || !txt_MobileNo.equals(phone_number) || !txt_Comment.equals("") || !issueType.equals(getResources().getString(R.string.issue_type)) || !Criticality.equals(getResources().getString(R.string.criticality)) || !PrefferedContacts.equals(getResources().getString(R.string.preferred_method_of_contacting))) {
//                        showDiscardDialog();
//                        return  true;
//                    } else {
////                        fm.popBackStack();
//                        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
//                                Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
//                        editorOrderTabsFromDraft.putString("TabNo", "0");
//                        editorOrderTabsFromDraft.apply();
//
//                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container_ret, new SupportFragment());
//                        fragmentTransaction.commit();
//
//                    }
//
//                    return  true;
//                }
                return false;
            }
        });

    }

    private void getQuestions() throws JSONException {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("SupportId",
                Context.MODE_PRIVATE);
        JSONObject map = new JSONObject();
        map.put("surveyId", sharedPreferences.getString("SupportId", ""));

        System.out.println(map);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_FetchQuestions, map, new Response.Listener<JSONArray>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONArray result) {
                System.out.println(result);

                Gson gson = new Gson();
                Type type = new TypeToken<List<SurveyQuestionsModel>>() {
                }.getType();

                List<SurveyQuestionsModel> survey_questions = gson.fromJson(String.valueOf(result), type);
                ExpectedAnswersCount = survey_questions.size();
int question_number = 1;
                for (final SurveyQuestionsModel survey_question :
                        survey_questions) {
                    TextView textView1 = new TextView(getContext());
                    textView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
//                    textView1.setText(survey_question.getQuestion());
                    SpannableString ss2 = new SpannableString(String.valueOf(question_number++) + ": " + survey_question.getQuestion());
                    ss2.setSpan(new StyleSpan(Typeface.BOLD), 0, ss2.length(), 0);
                    textView1.append(ss2);

                    textView1.setTextSize(18);
                    textView1.setTextColor(getResources().getColor(R.color.rv_box_color));
                    textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
                    rl_fields.addView(textView1);

                    if (survey_question.getType().equals("MCQ")) {
                        RadioGroup rgp = new RadioGroup(getContext());
                        rgp.setOrientation(LinearLayout.HORIZONTAL);
                        rgp.setPadding(0, 0, 0, 30);
                        rl_fields.addView(rgp);

                        for (final SurveyAnswerModel answer : survey_question.getAnswers()) {
                            final RadioButton rbn = new RadioButton(getContext());
                            rbn.setId(View.generateViewId());
                            rbn.setText(answer.getOptions());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                            rbn.setLayoutParams(params);
                            rbn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    System.out.println(rbn.getText());
                                    answers.put(answer.getQuestionID(), String.valueOf(rbn.getText()));
                                    checkAndEnableSubmitButton();
                                }
                            });
                            rgp.addView(rbn);
                        }
                    } else if (survey_question.getType().equals("Paragraph")) {
                        final EditText et = new EditText(getContext());
                        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        et.setLayoutParams(p);
                        et.setPadding(0, 0, 0, 30);
//                        et.setOnKeyListener(new View.OnKeyListener() {
//                            @Override
//                            public boolean onKey(View view, int i, KeyEvent keyEvent) {
////                                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                                System.out.println("onkeypressed");
//                                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {
//
//                                    System.out.println("onkeypressed");
//                                    answers.put(survey_question.getQuestionID(), et.getText().toString());
//                                    checkAndEnableSubmitButton();
//
//                                    return true;
//                                }
//                                //                                }
//                                checkAndEnableSubmitButton();
//                                return false;
//                            }
//                        });
                        et.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                answers.put(survey_question.getQuestionID(), et.getText().toString());
                                checkAndEnableSubmitButton();
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
//                        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                            @Override
//                            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
//                                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getAction() == KeyEvent.ACTION_DOWN))) {
//                                    answers.put(survey_question.getQuestionID(), String.valueOf(textView.getText()));
//                                    checkAndEnableSubmitButton();
//                                    return true;
//                                } else {
//                                    return false;
//                                }
//                            }
//                        });

                        rl_fields.addView(et);
                    } else if (survey_question.getType().equals("Dropdown")) {
                        Spinner s = new Spinner(getContext());
                        s.setPadding(0, 0, 0, 30);
                        //Prepar adapter
                        //HERE YOU CAN ADD ITEMS WHICH COMES FROM SERVER.
                        final MyDropdownData items[] = new MyDropdownData[survey_question.getAnswers().size()];
                        int counter = 0;
//                        items[counter++] = new MyDropdownData("Select Option", -1);
                        for (SurveyAnswerModel answer : survey_question.getAnswers()) {
                            items[counter++] = new MyDropdownData(answer.getOptions(), answer.getOptionID());
                        }

                        ArrayAdapter<MyDropdownData> adapter = new ArrayAdapter<MyDropdownData>(getContext(),
                                android.R.layout.simple_spinner_item, items);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s.setAdapter(adapter);
                        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                MyDropdownData d = items[position];

                                //Get selected value of key
                                int value = d.getValue();
                                String key = d.getSpinnerText();

                                if (value > 0)
                                    answers.put(survey_question.getQuestionID(), key);
                                checkAndEnableSubmitButton();
                            }

                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        rl_fields.addView(s);
                    } else if (survey_question.getType().equals("Date")) {
                        TableRow row = new TableRow(getContext());
                        row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        row.setPadding(0, 0, 0, 30);

                        final EditText edit = new EditText(getContext());
                        edit.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        edit.setHint("Tap to select ");
                        edit.setFocusable(false);
                        edit.setClickable(true);

                        edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                DialogFragment newFragment = new SelectDateFragment(edit);
//                                newFragment.show(getParentFragmentManager(), "DatePicker");
                                first_date = edit;
                                question_id = survey_question.getQuestionID();
                                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                                DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DialogTheme, Support_Ticket_Form_Fragment.this,
                                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH));
                                dialog.show();
                            }
                        });
//                        allElement_date.add(edit);

                        row.addView(edit);
                        rl_fields.addView(row);
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new ProcessingError().showError(getContext());
                new HaballError().printErrorMessage(getContext(), error);
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        Volley.newRequestQueue(this.

                getContext()).

                add(sr);
    }

    private void checkAndEnableSubmitButton() {
        System.out.println("ExpectedAnswersCount: " + ExpectedAnswersCount);
        System.out.println("answers.size(): " + answers.size());
        System.out.println("answers: " + answers);
        if (ExpectedAnswersCount > 0 && ExpectedAnswersCount == answers.size()) {
            ticket_btn.setEnabled(true);
            ticket_btn.setBackground(getResources().getDrawable(R.drawable.button_round));
        } else {
            ticket_btn.setEnabled(false);
            ticket_btn.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
        }
    }

    private void makeTicketAddRequest() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("SupportId",
                Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesLogin = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        loader.showLoader();

        for (Map.Entry<Integer, String> set :
                answers.entrySet()) {
            JSONObject map = new JSONObject();
            map.put("surveyId", sharedPreferences.getString("SupportId", ""));
            map.put("questionId", set.getKey());
            map.put("userId", sharedPreferencesLogin.getString("UserID", ""));
            map.put("answer", set.getValue());

            Log.i("TICKET_OBJECT", String.valueOf(map));
            new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_TICkET, map, new Response.Listener<JSONObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(JSONObject result) {
                    apiResponse++;
                    loader.hideLoader();
                    Log.e("RESPONSE", result.toString());
//                Toast.makeText(getContext(), "Ticket generated successfully.", Toast.LENGTH_LONG).show();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(((ViewGroup) getView().getParent()).getId(), new SupportFragment());
//                fragmentTransaction.commit();
                    try {

                        if (result.getString("message").equals("successful")) {

//                            final Dialog fbDialogue = new Dialog(getActivity());
//                            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
//                            fbDialogue.setContentView(R.layout.password_updatepopup);
//                            TextView tv_pr1, txt_header1;
//                            txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
//                            tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
//                            txt_header1.setText("Survey Submitted");
////                    tv_pr1.setText((getResources().getString(R.string.your_ticket_id)) + result.get("TicketNumber") + (getResources().getString(R.string.ticket_created_msg)));
//                            String tempStr = "Your survey response submitted successfully";
//                            tv_pr1.setText(tempStr);
//
//
//                            fbDialogue.setCancelable(true);
//                            fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
//                            WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
//                            layoutParams.y = 200;
//                            layoutParams.x = -70;// top margin
//                            fbDialogue.getWindow().setAttributes(layoutParams);
//                            fbDialogue.show();
//
//                            ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
//                            close_button.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    fbDialogue.dismiss();
//                                }
//                            });
//
//                            fbDialogue.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                                @Override
//                                public void onDismiss(DialogInterface dialog) {
//                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                                    fragmentTransaction.add(((ViewGroup) getView().getParent()).getId(), new SupportFragment());
//                                    fragmentTransaction.commit();
//                                }
//                            });
                        } else {
//                            new CustomToast().showToast((FragmentActivity) getContext(), result.getString("message"));
                            isError = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (apiResponse == answers.size()) {
                        if (!isError) {

                            final Dialog fbDialogue = new Dialog(getActivity());
                            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                            fbDialogue.setContentView(R.layout.password_updatepopup);
                            TextView tv_pr1, txt_header1;
                            txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                            tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                            txt_header1.setText("Survey Submitted");
//                    tv_pr1.setText((getResources().getString(R.string.your_ticket_id)) + result.get("TicketNumber") + (getResources().getString(R.string.ticket_created_msg)));
                            String tempStr = "Your survey response submitted successfully";
                            tv_pr1.setText(tempStr);


                            fbDialogue.setCancelable(true);
                            fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                            WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
                            layoutParams.y = 200;
                            layoutParams.x = -70;// top margin
                            fbDialogue.getWindow().setAttributes(layoutParams);
                            fbDialogue.show();

                            ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
                            close_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fbDialogue.dismiss();
                                }
                            });

                            fbDialogue.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(((ViewGroup) getView().getParent()).getId(), new SupportFragment());
                                    fragmentTransaction.commit();
                                }
                            });
                        } else {
                            new CustomToast().showToast((FragmentActivity) getContext(), "Error occurred while saving response");
                        }
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loader.hideLoader();
                    new ProcessingError().showError(getContext());
                    new HaballError().printErrorMessage(getContext(), error);
                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("Authorization", "bearer " + Token);
//                    params.put("rightid", "-1");
                    return params;
                }
            };
            Volley.newRequestQueue(this.getContext()).add(sr);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        year1 = i;
        month1 = i1;
        date1 = i2;
        updateDisplay();
    }

    private void updateDisplay() {
//            fromDate = year1 + "-" + String.format("%02d", (month1 + 1)) + "-" + String.format("%02d", date1) + "T00:00:00.000Z";
        fromDate = year1 + "-" + String.format("%02d", (month1 + 1)) + "-" + String.format("%02d", date1);
//            // Log.i("fromDate", fromDate);
        answers.put(question_id, year1 + "-" + String.format("%02d", (month1 + 1)) + "-" + String.format("%02d", date1));
        first_date.setText(new StringBuilder()
                .append(String.format("%02d", date1)).append("/").append(String.format("%02d", (month1 + 1))).append("/").append(year1));
        checkAndEnableSubmitButton();
    }
// private void printErrorMessage(VolleyError error) {
//     if (getContext() != null) {
//         if (error instanceof NetworkError) {
//             Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
//         } else if (error instanceof ServerError) {
//             Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
//         } else if (error instanceof AuthFailureError) {
//             Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
//         } else if (error instanceof ParseError) {
//             Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
//         } else if (error instanceof NoConnectionError) {
//             Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
//         } else if (error instanceof TimeoutError) {
//             Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
//         }

//         if (error.networkResponse != null && error.networkResponse.data != null) {
//             try {
//                 String message = "";
//                 String responseBody = new String(error.networkResponse.data, "utf-8");
//                 // Log.i("responseBody", responseBody);
//                 JSONObject data = new JSONObject(responseBody);
//                 // Log.i("data", String.valueOf(data));
//                 Iterator<String> keys = data.keys();
//                 while (keys.hasNext()) {
//                     String key = keys.next();
//                     message = message + data.get(key) + "\n";
//                 }
//                 Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
//             } catch (UnsupportedEncodingException e) {
//                 e.printStackTrace();
//             } catch (JSONException e) {
//                 e.printStackTrace();
//             }
//         }
//     }

// }

    class MyDropdownData {
        public MyDropdownData(String spinnerText, int value) {
            this.spinnerText = spinnerText;
            this.value = value;
        }

        public String getSpinnerText() {
            return spinnerText;
        }

        public int getValue() {
            return value;
        }

        public String toString() {
            return spinnerText;
        }

        String spinnerText;
        int value;
    }

}
