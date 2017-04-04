package com.codecool.actimate.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codecool.actimate.R;
import com.codecool.actimate.controller.APIController;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.HashMap;

public class AddNewEventActivity extends AppCompatActivity {

    private static final String TAG = AddNewEventActivity.class.getSimpleName();
    private final static String PREFS_KEY = "com.codecool.actimate.preferences";
    private static SharedPreferences mSharedPreferences;
    private Context context = AddNewEventActivity.this;
//    private final static String URL = "https://actimate.herokuapp.com";
    private final static String URL = "http://192.168.160.55:8888";
//    private final static String URL = "http://192.168.161.109:8080";

    private static String TOKEN;
    private static String LOCATION;
    private static String LATLNG;
    private EventTask mEventTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        mSharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        TOKEN = mSharedPreferences.getString("token", null);

        final Button button = (Button) findViewById(R.id.create_event);
        button.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          buttonCreate();
                                      }
                                  });

        String interestOptions[] = getResources().getStringArray(R.array.interests);


        Spinner mySpinner = (Spinner) findViewById(R.id.activity_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.row, R.id.interest_options, interestOptions);
        mySpinner.setAdapter(adapter);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setHint(getResources().getString(R.string.event_where));

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();
        autocompleteFragment.setFilter(typeFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LOCATION = place.getAddress().toString();
                LATLNG = place.getLatLng().toString();
                LATLNG = LATLNG.replace("lat/lng: (","");
                LATLNG = LATLNG.replace(")","");

                Log.i(TAG, "Place: " + LOCATION + " - " + LATLNG);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }


    protected boolean logout(View view) {
        APIController.setLoggedOut(mSharedPreferences);
        Intent intent = new Intent(AddNewEventActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    protected boolean goToProfile(View view) {
        Intent intent = new Intent(AddNewEventActivity.this, EditProfileActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    protected boolean goToHome(View view) {
        Intent intent = new Intent(AddNewEventActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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


    public void buttonCreate() {
        EditText mEdit = (EditText)findViewById(R.id.name_of_event);
        String mName = mEdit.getText().toString();

        Spinner mSpinner = (Spinner)findViewById(R.id.activity_spinner);
        String mInterest = APIController.selectInterest(mSpinner.getSelectedItem().toString(), AddNewEventActivity.this);

        Log.d(TAG, "buttonCreate: " + mInterest);

        String mLocation = LOCATION;
        String latLng[] = LATLNG.split(",");
        float mLat = Float.parseFloat(latLng[0]);
        float mLng = Float.parseFloat(latLng[1]);

        mEdit = (EditText)findViewById(R.id.event_amount_of_participants);
        Integer mParticipants = Integer.parseInt(mEdit.getText().toString());

        mEdit = (EditText)findViewById(R.id.introduction);
        String mDescription = mEdit.getText().toString();

        Button mButton = (Button)findViewById(R.id.date_button);
        String mDate = mButton.getText().toString();
        mDate += "T";
        mButton = (Button)findViewById(R.id.time_button);
        mDate += mButton.getText().toString();
        mDate += ":00.000Z";

        mEventTask = new EventTask(mName, mInterest, mLocation, mLat, mLng, mDate, mParticipants, mDescription);
        mEventTask.execute((Void) null);

    }

    public class EventTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final String mInterest;
        private final String mLocation;
        private final float mLat;
        private final float mLng;
        private final String mDate;
        private final Integer mParticipants;
        private final String mDescription;
        private Boolean status;


        EventTask(String name, String interest, String location, Float lat, Float lng,
                              String date, Integer participants, String description) {
            mName = name;
            mInterest = interest;
            mLocation = location;
            mLat = lat;
            mLng = lng;
            mDate = date;
            mParticipants = participants;
            mDescription = description;

        }

        void toastError(String s) {
            Toast.makeText(getApplicationContext(),
                    s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("name", mName);
            data.put("interest", mInterest);
            data.put("location", mLocation);
            data.put("lat", Float.toString(mLat));
            data.put("lng", Float.toString(mLng));
            data.put("date", mDate);
            data.put("participants", Integer.toString(mParticipants));
            data.put("description", mDescription);


            if (!APIController.isNetworkAvailable(AddNewEventActivity.this)) {
                toastError(getResources().getString(R.string.error_no_connection));
                return false;
            }
            status = APIController.tryToSendData(URL + "/u/create_event", data, TOKEN);
            return status;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                toastError(getResources().getString(R.string.event_success));
                Intent intent = new Intent(AddNewEventActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                toastError(getResources().getString(R.string.event_fail));
            }
        }
    }
}