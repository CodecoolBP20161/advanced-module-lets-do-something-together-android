package com.codecool.actimate.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.codecool.actimate.R;
import com.codecool.actimate.controller.APIController;

import java.io.IOException;
import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String PREFS_KEY = "com.codecool.actimate.preferences";
    private static SharedPreferences mSharedPreferences;
    private Context context;
    private final static String URL = "https://actimate.herokuapp.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        context = EditProfileActivity.this;
        mSharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    // TODO implement male selected
                    break;
            case R.id.radio_female:
                if (checked)
                    // TODO implement female selected
                    break;
        }
    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkbox_tennis:
                if (checked) {
                    // TODO implement
                } else {
                    // TODO implement
                }
                break;
            default:
                break;
        }
    }
    protected boolean logout(View view){
        APIController.setLoggedOut(mSharedPreferences);
        Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        return true;
    }

    protected boolean goToHome(View view){
        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    protected boolean goToNewEvent(View view){
        Intent intent = new Intent(EditProfileActivity.this, AddNewEventActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_header_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_to_main:
                return goToHome(this.findViewById(R.id.action_to_main));
            case R.id.action_logout:
                return logout(this.findViewById(R.id.action_logout));
            case R.id.action_new_event:
                return goToNewEvent(this.findViewById(R.id.action_new_event));
        }
        return false;
    }

    private class LoadData extends AsyncTask<String, Void, String> {
        public String data;
        @Override
        protected String doInBackground(String... params) {
            try {
                data = APIController.getHttpData("http://192.168.160.55:8888/interests");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }

}
