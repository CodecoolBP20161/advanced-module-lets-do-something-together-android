package com.codecool.actimate.view;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codecool.actimate.R;

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
        textView.setText("In 2 days");

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
        gd.setColor(Color.parseColor("#000000"));
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
}