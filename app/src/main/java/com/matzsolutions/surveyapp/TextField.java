package com.matzsolutions.surveyapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.wifi.p2p.WifiP2pManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Timer;

public class TextField {
    boolean myhasFocus = false;
    public TextField() {

    }

    @SuppressLint("RtlHardcoded")
    public void changeColor(final Context context, final TextInputLayout textInputLayout, final TextInputEditText textInputEditText) {
        SharedPreferences languageType = context.getSharedPreferences("changeLanguage",
                Context.MODE_PRIVATE);
        String language = languageType.getString("language", "");
        Log.i("language_debug", language);

//        textInputEditText.setHint(textInputLayout.getHint());
//        textInputLayout.setHint("");
//        textInputLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//        textInputEditText.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//        textInputLayout.setHintTextAppearance(R.style.hint_temp);
        if (language.equals("ur")) {
//            textInputLayout.setGravity(Gravity.START);
//            textInputEditText.setGravity(Gravity.START);
//            textInputLayout.setGravity(Gravity.RIGHT);
//            textInputEditText.setGravity(Gravity.RIGHT);
        } else {
//            textInputLayout.setGravity(Gravity.END);
//            textInputEditText.setGravity(Gravity.END);
//            textInputLayout.setGravity(Gravity.RIGHT);
//            textInputEditText.setGravity(Gravity.RIGHT);
        }

        if (textInputLayout.getDefaultHintTextColor() != ColorStateList.valueOf(context.getResources().getColor(R.color.error_stroke_color)))
            textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //  textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
//                textInputEditText.setTextColor(context.getResources().getColor(R.color.textcolor));
//                textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.textcolorhint)));
                if (textInputLayout.getDefaultHintTextColor() != ColorStateList.valueOf(context.getResources().getColor(R.color.error_stroke_color))) {
                    if (!myhasFocus && textInputEditText.getText().toString().trim().equals("")) {
                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
                    } else {
                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Log.i("textchanged", "aftertextchanged");
                // Log.i("textchanged", String.valueOf(editable));
                // Log.i("textchanged", String.valueOf(textInputEditText.getText()));
                if (textInputLayout.getDefaultHintTextColor() != ColorStateList.valueOf(context.getResources().getColor(R.color.error_stroke_color))) {
                    if (!myhasFocus && textInputEditText.getText().toString().trim().equals("")) {
                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
                    } else {
                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
                    }
                }

            }
        });

        textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, final boolean hasFocus) {
                myhasFocus = hasFocus;
                textInputLayout.setHintAnimationEnabled(true);

                if (textInputLayout.getDefaultHintTextColor() != ColorStateList.valueOf(context.getResources().getColor(R.color.error_stroke_color))) {
                    if (!hasFocus && textInputEditText.getText().toString().trim().equals("")) {
                        final Timer t = new java.util.Timer();
                        t.schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        // your code here
                                        // close the thread
                                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
                                        t.cancel();
                                    }
                                },
                                50
                        );
                    } else {
                        final Timer t = new java.util.Timer();
                        t.schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        // your code here
                                        // close the thread
                                        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
                                        t.cancel();
                                    }
                                },
                                50
                        );
//
//                        textInputEditText.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                            }
//
//                            @Override
//                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                if (textInputEditText.getText().toString().trim().equals("") && !hasFocus) {
//                                    textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
//                                } else {
//                                    textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green_color)));
//                                }
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable editable) {
//
//                            }
//                        });

                    }
                }
            }
        });


    }
}
