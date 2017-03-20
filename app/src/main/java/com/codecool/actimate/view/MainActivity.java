package com.codecool.actimate.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codecool.actimate.R;
import com.codecool.actimate.controller.APIController;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String PREFS_KEY = "com.codecool.actimate.preferences";
    private static SharedPreferences mSharedPreferences;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        mSharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        Log.d(TAG, "onCreate: loggedIn: " + mSharedPreferences.getBoolean("loggedIn", false));
    }

    protected boolean logout(View view){
        APIController.setLoggedOut(mSharedPreferences);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        return true;
    }

    protected boolean goToProfile(View view){
        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
        startActivity(intent);
        return true;
    }

    protected boolean goToNewEvent(View view){
        Intent intent = new Intent(MainActivity.this, AddNewEventActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_header, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_to_profile:
                return goToProfile(this.findViewById(R.id.action_to_profile));
            case R.id.action_logout:
                return logout(this.findViewById(R.id.action_logout));
            case R.id.action_new_event:
                return goToNewEvent(this.findViewById(R.id.action_new_event));

        }
        return false;
    }
}
