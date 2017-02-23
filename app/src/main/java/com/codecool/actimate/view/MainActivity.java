package com.codecool.actimate.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    protected void logout(View view){
        APIController.setLoggedOut(mSharedPreferences);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
