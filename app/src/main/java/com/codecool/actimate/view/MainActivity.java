package com.codecool.actimate.view;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codecool.actimate.R;
import com.codecool.actimate.controller.APIController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String PREFS_KEY = "com.codecool.actimate.preferences";
    private static SharedPreferences mSharedPreferences;
    private Context context;
    private static String RESP = null;
//    private static String RESP = "{\"events\":{\"12\":{\"date\":\"2017-04-04 11:19:02.757\",\"lng\":765,\"interest\":\"tennis\",\"name\":\"kjhg\",\"description\":\"gjkl\",\"location\":\"gj\",\"lat\":23,\"participants\":4},\"34\":{\"date\":\"2017-04-04 11:19:48.986\",\"lng\":87,\"interest\":\"running\",\"name\":\"ljkljk\",\"description\":\"lkjélkjélk\",\"location\":\"élkjélkj\",\"lat\":97,\"participants\":7},\"13\":{\"date\":\"2017-04-04 11:19:02.757\",\"lng\":765,\"interest\":\"tennis\",\"name\":\"kjhg\",\"description\":\"gjkl\",\"location\":\"gj\",\"lat\":23,\"participants\":4},\"35\":{\"date\":\"2017-04-04 11:19:48.986\",\"lng\":87,\"interest\":\"running\",\"name\":\"ljkljk\",\"description\":\"lkjélkjélk\",\"location\":\"élkjélkj\",\"lat\":97,\"participants\":7}}}";
    private static JSONObject JSON;
    private static String TOKEN;
    private static String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        URL = getResources().getString(R.string.url);

        mSharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        TOKEN = mSharedPreferences.getString("token", null);

        if (APIController.isNetworkAvailable(MainActivity.this)) {
            EventCardsTask mEventCardsTask = new EventCardsTask();
            mEventCardsTask.execute((Void) null);
        } else {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.error_no_connection), Toast.LENGTH_LONG).show();
        }

        if (RESP != null) {
            reloadEventCards(RESP);
        }
    }

    private void reloadEventCards(String eventCards) {

        try {
            JSON = new JSONObject(eventCards);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        JSONArray eventsArray;
        try {
            eventsArray = JSON.getJSONArray("events");
        } catch (JSONException e) {
            eventsArray = null;
            e.printStackTrace();
        }
        for (int i = 0; i < eventsArray.length(); i++) {

            JSONObject event = null;
            try {
                event = eventsArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String name = event.getString("name");
                String interest = this.getResources().getString(APIController.reverseSelectInterest(event.getString("interest"), context));
                String date = event.getString("date").substring(0, 16).replace(" ", "\t");
                String location = event.getString("location");
                String color = event.getString("color");

                EventCardFragment fragment = new EventCardFragment();
                fragment.setAttributes(name, interest, date, location, color);
                fragmentTransaction.add(R.id.fragment_container, fragment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    protected boolean logout(View view){
        APIController.setLoggedOut(mSharedPreferences);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    protected boolean goToProfile(View view){
        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    protected boolean goToNewEvent(View view){
        Intent intent = new Intent(MainActivity.this, AddNewEventActivity.class);
        startActivity(intent);
        finish();
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
    private class EventCardsTask extends AsyncTask<Void, Void, Boolean> {
        private String data;


        void toastError(String s) {
            Toast.makeText(getApplicationContext(),
                    s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            data = APIController.tryToFetchData(URL + "/u/events", TOKEN);
            return data != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                if (!data.equals(RESP) && !data.equals("{}") ) {
                    RESP = data;
                    reloadEventCards(data);
                }
            } else {
                toastError(getResources().getString(R.string.event_fail));
            }
        }
    }
}
