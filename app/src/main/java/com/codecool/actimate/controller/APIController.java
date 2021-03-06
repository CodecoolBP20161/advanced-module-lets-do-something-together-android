package com.codecool.actimate.controller;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.codecool.actimate.view.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class APIController {
    private static final String TAG = APIController.class.getSimpleName();

    public static JSONObject createJson(HashMap<String, String> data) {
        Set set = data.entrySet();
        Iterator iterator = set.iterator();
        JSONObject json = new JSONObject();
        try {
            while(iterator.hasNext()) {
                Map.Entry mapEntry = (Map.Entry)iterator.next();
                json.accumulate(mapEntry.getKey().toString(), mapEntry.getValue().toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    public static String postHttpData(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        Log.d(TAG, "postHttpData: " + url + " -> " + responseBody);
        return responseBody;
    }

    public static void setLoggedOut(SharedPreferences mSharedPreferences) {
        mSharedPreferences.edit().putBoolean("loggedIn", false).apply();
        Log.d(TAG, "setLoggedOut: loggedIn = " + mSharedPreferences.getBoolean("loggedIn", false));
    }

    public static void setLoggedIn(SharedPreferences mSharedPreferences) {
        mSharedPreferences.edit().putBoolean("loggedIn", true).apply();
        Log.d(TAG, "setLoggedIn: loggedIn = " + mSharedPreferences.getBoolean("loggedIn", false));
    }

    public static Boolean tryToLogin(String url, HashMap data){
        try {
            String response = APIController.postHttpData(url,
                    APIController.createJson(data).toString());
            if (response.equals("success")) {
                return true;
            } else if (response.equals("wrong password")) {
                LoginActivity.setStatus("wrong password");
            } else {
                LoginActivity.setStatus("wrong email");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean tryToRegister(String url, HashMap data){
        try {
            String response = APIController.postHttpData(url,
                    APIController.createJson(data).toString());
            if (response.equals("success")) {
                return true;
            } else if (response.equals("fail")) {
                if (LoginActivity.getStatus().equals("")) {
                    LoginActivity.setStatus("already registered");

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
