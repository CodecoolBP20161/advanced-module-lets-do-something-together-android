package com.codecool.actimate.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codecool.actimate.R;
import com.codecool.actimate.controller.APIController;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = EditProfileActivity.class.getSimpleName();
    private final static String PREFS_KEY = "com.codecool.actimate.preferences";
    private static SharedPreferences mSharedPreferences;
    private Context context = EditProfileActivity.this;
//    private final static String URL = "https://actimate.herokuapp.com";
//    private final static String URL = "http://192.168.161.148:8888";
    private final static String URL = "http://192.168.161.109:8080";
//    private final static String URL = "http://192.168.0.196:8888";
    private static String TOKEN;
    private static String GENDER;
    private static HashSet<String> interestsSet = new HashSet<String>();
    private ProfileTask mProfileTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mSharedPreferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        TOKEN = mSharedPreferences.getString("token", null);

        final Button button = (Button) findViewById(R.id.save_profile);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonUpdate();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    // TODO implement male selected
                    GENDER = "male";
                    break;
            case R.id.radio_female:
                if (checked)
                    // TODO implement female selected
                    GENDER = "female";
                    break;
        }
    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        ((CheckBox) view).getText();
        String id = APIController.selectInterest(((CheckBox) view).getText().toString(), this);

        if (checked) {
            interestsSet.add(id);
            Log.d(TAG, "onCheckboxClicked: CHECKED -> " + id);
        } else {
            interestsSet.remove(id);
        }
//        switch(view.getId()) {
//            case R.id.checkbox_tennis:
//                if (checked) {
//                    interestsSet.add(APIController.selectInterest(getResources().getString(R.string.tennis), this));
//                } else {
//                    interestsSet.remove(APIController.selectInterest(getResources().getString(R.string.tennis), this));
//                }
//                break;
//            case R.id.checkbox_gokart:
//                if (checked) {
//                    interestsSet.add(APIController.selectInterest(getResources().getString(R.string.gokart), this));
//                } else {
//                    interestsSet.remove(APIController.selectInterest(getResources().getString(R.string.gokart), this));
//                }
//                break;
//            case R.id.checkbox_running:
//                if (checked) {
//                    interestsSet.add(APIController.selectInterest(getResources().getString(R.string.running), this));
//                } else {
//                    interestsSet.remove(APIController.selectInterest(getResources().getString(R.string.running), this));
//                }
//                break;
//            case R.id.checkbox_cardgames:
//                if (checked) {
//                    interestsSet.add(APIController.selectInterest(getResources().getString(R.string.cardgames), this));
//                } else {
//                    interestsSet.remove(APIController.selectInterest(getResources().getString(R.string.cardgames), this));
//                }
//                break;
//            case R.id.checkbox_cinema:
//                if (checked) {
//                    interestsSet.add(APIController.selectInterest(getResources().getString(R.string.cinema), this));
//                } else {
//                    interestsSet.remove(APIController.selectInterest(getResources().getString(R.string.cinema), this));
//                }
//                break;
//            case R.id.checkbox_theater:
//                if (checked) {
//                    interestsSet.add(APIController.selectInterest(getResources().getString(R.string.theater), this));
//                } else {
//                    interestsSet.remove(APIController.selectInterest(getResources().getString(R.string.theater), this));
//                }
//                break;
//            case R.id.checkbox_citywalks:
//                if (checked) {
//                    interestsSet.add(APIController.selectInterest(getResources().getString(R.string.citywalks), this));
//                } else {
//                    interestsSet.remove(APIController.selectInterest(getResources().getString(R.string.citywalks), this));
//                }
//                break;
//            case R.id.checkbox_hiking:
//                if (checked) {
//                    interestsSet.add(APIController.selectInterest(getResources().getString(R.string.hiking), this));
//                } else {
//                    interestsSet.remove(APIController.selectInterest(getResources().getString(R.string.hiking), this));
//                }
//                break;
//            default:
//                break;
//        }
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

    public void buttonUpdate() {
        EditText mEdit = (EditText)findViewById(R.id.first_name);
        String mFirstName = mEdit.getText().toString();

        mEdit = (EditText)findViewById(R.id.last_name);
        String mLastName = mEdit.getText().toString();

        mEdit = (EditText)findViewById(R.id.language);
        String mLanguage = mEdit.getText().toString();

        String mGender = GENDER;

        mEdit = (EditText)findViewById(R.id.introduction);
        String mIntroduction = mEdit.getText().toString();

        String mInterests = APIController.createJson(interestsSet).toString();

        mProfileTask = new ProfileTask(mFirstName, mLastName, mLanguage, mGender, mIntroduction, mInterests);
        mProfileTask.execute((Void) null);
    }


    public class ProfileTask extends AsyncTask<Void, Void, Boolean> {

        private final String mFirstName;
        private final String mLastName;
        private final String mLanguage;
        private final String mGender;
        private final String mIntroduction;
        private final String mInterests;
        private Boolean status;


        ProfileTask(String firstName, String lastName, String language, String gender,
                    String introduction, String interests) {
            mFirstName = firstName;
            mLastName = lastName;
            mLanguage = language;
            mGender = gender;
            mIntroduction = introduction;
            mInterests = interests;

        }

        void toastError(String s) {
            Toast.makeText(getApplicationContext(),
                    s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("firstName", mFirstName);
            data.put("lastName", mLastName);
            data.put("gender", mGender);
            data.put("language", mLanguage);
            data.put("introduction", mIntroduction);
            data.put("interests", mInterests);


            if (!APIController.isNetworkAvailable(EditProfileActivity.this)) {
                toastError(getResources().getString(R.string.error_no_connection));
                return false;
            }
            status = APIController.tryToSendData(URL + "/u/edit-profile", data, TOKEN);
            return status;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                toastError(getResources().getString(R.string.success));
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                toastError(getResources().getString(R.string.fail));
            }
        }
    }

}
