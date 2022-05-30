package com.matzsolutions.surveyapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.text.DecimalFormat;
import java.util.Calendar;

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText edit;
    private String zeroPad = "0000000000000000";
    DecimalFormat numFormat = new DecimalFormat("00");

    public SelectDateFragment(EditText edit) {
        this.edit = edit;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(edit, yy, mm+1, dd);
    }

    public void populateSetDate(EditText edit, int year, int month, int day)
    {
        edit.setText( year + "-" + numFormat.format(month) + "-" + numFormat.format(day));
    }
}