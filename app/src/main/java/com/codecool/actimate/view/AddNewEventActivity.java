package com.codecool.actimate.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.codecool.actimate.R;
import com.codecool.actimate.controller.APIController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class AddNewEventActivity extends AppCompatActivity {

    private static final String TAG = AddNewEventActivity.class.getSimpleName();
    private final static String PREFS_KEY = "com.codecool.actimate.preferences";
    private static SharedPreferences mSharedPreferences;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        String interestOptions[] = getResources().getStringArray(R.array.interests);


        Spinner mySpinner = (Spinner) findViewById(R.id.activity_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.row, R.id.interest_options, interestOptions);
        mySpinner.setAdapter(adapter);
    }

    protected boolean logout(View view) {
        APIController.setLoggedOut(mSharedPreferences);
        Intent intent = new Intent(AddNewEventActivity.this, LoginActivity.class);
        startActivity(intent);
        return true;
    }

    protected boolean goToProfile(View view) {
        Intent intent = new Intent(AddNewEventActivity.this, EditProfileActivity.class);
        startActivity(intent);
        return true;
    }

    protected boolean goToHome(View view) {
        Intent intent = new Intent(AddNewEventActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    public void showTimePickerDialog(View v) {
        android.support.v4.app.DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setButtonText(int id, String text) {
        Button button = (Button) findViewById(id);
        button.setText(text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_header_new_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_to_profile:
                return goToProfile(this.findViewById(R.id.action_to_profile));
            case R.id.action_logout:
                return logout(this.findViewById(R.id.action_logout));
            case R.id.action_to_main:
                return goToHome(this.findViewById(R.id.action_to_main));

        }
        return false;
    }
}