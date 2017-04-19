package com.codecool.actimate.view;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codecool.actimate.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventCardFragment extends Fragment {
    View rootView;
    String mName;
    String mInterest;
    String mDate;
    String mLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.event_card, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.event_card_date);
        textView.setText(mDate);

        textView = (TextView) rootView.findViewById(R.id.event_card_relative_date);
        Integer mYear = Integer.parseInt(mDate.substring(0,4));
        Integer mMonth = Integer.parseInt(mDate.substring(5,7));
        Integer mDay = Integer.parseInt(mDate.substring(8,10));

        PrettyTime p = new PrettyTime(getCurrentLocale());
        Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, mDay);
        textView.setText(p.format(new Date(c.getTimeInMillis())));

        textView = (TextView) rootView.findViewById(R.id.event_card_name);
        textView.setText(mName);

        textView = (TextView) rootView.findViewById(R.id.event_card_location);
        textView.setText(mLocation);

        textView = (TextView) rootView.findViewById(R.id.event_card_owner);
        textView.setText("Owner");

        textView = (TextView) rootView.findViewById(R.id.event_card_interest);
        textView.setText(mInterest);

        textView = (TextView) rootView.findViewById(R.id.event_card_more);
        textView.setText(">");

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#1e26b8"));
        gd.setCornerRadius(10);
        rootView.findViewById(R.id.event_card_interest_tag).setBackgroundDrawable(gd);
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            rootView.findViewById(R.id.event_card_interest_tag).setBackgroundDrawable(gd);
        } else {
            rootView.findViewById(R.id.event_card_interest_tag).setBackground(gd);
        }

        return rootView;
    }

    public void setAttributes(String name, String interest, String date, String location) {
        mName = name;
        mInterest = interest;
        mDate = date;
        mLocation = location;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public Locale getCurrentLocale(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return getResources().getConfiguration().locale;
        }
    }
}