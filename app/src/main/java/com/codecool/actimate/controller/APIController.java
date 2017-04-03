package com.codecool.actimate.controller;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Button;

import com.codecool.actimate.R;
import com.codecool.actimate.view.AddNewEventActivity;
import com.codecool.actimate.view.LoginActivity;
import com.codecool.actimate.view.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIController {
    private static final String TAG = APIController.class.getSimpleName();

    public static JSONObject createJson(HashSet<String> dataSet) {
        HashMap<String, String> data = new HashMap<>();
        for (Iterator<String> i = dataSet.iterator(); i.hasNext(); ) {
            data.put(i.toString(), "true");
        }
        return createJson(data);
    }

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

    public static Request requestBuilder(String url, String json){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }

    public static Request requestBuilder(String url, String json, String token){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("X-AUTH-TOKEN", token)
                .build();
        return request;
    }

    public static String getHttpData(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        Log.d(TAG, "getHttpData: " + url + " -> " + responseBody);
        return responseBody;
    }


    public static String postHttpData(String url, String json) throws IOException {

        Request request = (requestBuilder(url, json));

        OkHttpClient client = new OkHttpClient();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        Log.d(TAG, "postHttpData: " + url + " -> " + responseBody);
        return responseBody;
    }

    public static String postHttpData(String url, String json, String token) throws IOException {

        Request request = (requestBuilder(url, json, token));

        OkHttpClient client = new OkHttpClient();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        Log.d(TAG, "postHttpData: " + url + " -> " + responseBody);
        return responseBody;
    }

    public static void setLoggedOut(SharedPreferences mSharedPreferences) {
        mSharedPreferences.edit().putString("token", null).apply();
        Log.d(TAG, "setLoggedOut: token = " + mSharedPreferences.getString("token", null));
    }

    public static void setLoggedIn(SharedPreferences mSharedPreferences, String token) {
        mSharedPreferences.edit().putString("token", token).apply();
        Log.d(TAG, "setLoggedIn: token = " + mSharedPreferences.getString("token", null));
    }

    public static Boolean tryToLogin(String url, HashMap data){
        try {
            String response = APIController.postHttpData(url,
                    APIController.createJson(data).toString());
            try {
                JSONObject jsonObj = new JSONObject(response);

                switch (jsonObj.getString("status")){
                    case "success":
                        String token = jsonObj.getString("token");
                        LoginActivity.setToken(token);
                        return true;
                    case "wrong password":
                        LoginActivity.setStatus("wrong password");
                        return false;
                    case "wrong email":
                        LoginActivity.setStatus("wrong email");
                        return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean tryToRegister(String url, HashMap data){

        String response = null;
        try {
            response = APIController.postHttpData(url,
                    APIController.createJson(data).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObj = new JSONObject(response);

            switch (jsonObj.getString("status")){
                case "success":
                    return true;
                case "fail":
                    LoginActivity.setStatus("already registered");
                    return false;
                default:
                    return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean tryToSendData(String url, HashMap data, String token){
        try {
            String response = APIController.postHttpData(url,
                    APIController.createJson(data).toString(), token);
            try {
                JSONObject jsonObj = new JSONObject(response);

                switch (jsonObj.getString("status")){
                    case "Event saved into the database":
                        return true;
                    default:
                        return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
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

    public static String selectInterest(String interest, Activity activity) {
        if (interest.equals(activity.getResources().getString(R.string.tennis))){
            return "tennis";
        }
        if (interest.equals(activity.getResources().getString(R.string.gokart))){
            return "gokart";
        }
        if (interest.equals(activity.getResources().getString(R.string.running))){
            return "running";
        }
        if (interest.equals(activity.getResources().getString(R.string.cardgames))){
            return "cardGames";
        }
        if (interest.equals(activity.getResources().getString(R.string.cinema))){
            return "cinema";
        }
        if (interest.equals(activity.getResources().getString(R.string.theater))){
            return "theater";
        }
        if (interest.equals(activity.getResources().getString(R.string.citywalks))){
            return "cityWalks";
        }
        if (interest.equals(activity.getResources().getString(R.string.hiking))){
            return "hiking";
        }
        return null;
    }
}
