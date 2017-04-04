package com.codecool.actimate.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.codecool.actimate.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        String formattedMonth = (Integer.toString(month).length() > 1) ? Integer.toString(month) : "0" + Integer.toString(month);
        String formattedDay = (Integer.toString(day).length() > 1) ? Integer.toString(day) : "0" + Integer.toString(day);

        String date = Integer.toString(year) + "-" + formattedMonth + "-" + formattedDay;
        ((AddNewEventActivity)getActivity()).setButtonText(R.id.date_button, date);
    }
}