package com.codecool.actimate.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.codecool.actimate.R;
import com.codecool.actimate.controller.APIController;

public class AddNewEventActivity extends AppCompatActivity {

    private static final String TAG = AddNewEventActivity.class.getSimpleName();
    private final static String PREFS_KEY = "com.codecool.actimate.preferences";
    private static SharedPreferences mSharedPreferences;
    private Context context;

//    String interestOptions[] = {getResources().getString(R.string.tennis),
//            getResources().getString(R.string.gokart), getResources().getString(R.string.running),
//            getResources().getString(R.string.cardgames), getResources().getString(R.string.cinema),
//            getResources().getString(R.string.theater), getResources().getString(R.string.citywalks),
//            getResources().getString(R.string.hiking)};

//    String interestOptions[]= getResources().getStringArray(R.array.interests);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        String interestOptions[]= getResources().getStringArray(R.array.interests);


        Spinner mySpinner = (Spinner)findViewById(R.id.activity_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.row, R.id.interest_options, interestOptions);
        mySpinner.setAdapter(adapter);
    }

    protected boolean logout(View view){
        APIController.setLoggedOut(mSharedPreferences);
        Intent intent = new Intent(AddNewEventActivity.this, LoginActivity.class);
        startActivity(intent);
        return true;
    }

    protected boolean goToProfile(View view){
        Intent intent = new Intent(AddNewEventActivity.this, EditProfileActivity.class);
        startActivity(intent);
        return true;
    }

    protected boolean goToHome(View view){
        Intent intent = new Intent(AddNewEventActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
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
