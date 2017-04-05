package com.codecool.actimate.view;

import android.app.Fragment;
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

        TextView textView = (TextView) rootView.findViewById(R.id.event_card_name_value);
        textView.setText(mName);

        textView = (TextView) rootView.findViewById(R.id.event_card_activity_value);
        textView.setText(mInterest);

        textView = (TextView) rootView.findViewById(R.id.event_card_date_value);
        textView.setText(mDate);

        textView = (TextView) rootView.findViewById(R.id.event_card_location_value);
        textView.setText(mLocation);

        return rootView;
    }

    public void setAttributes(String name, String interest, String date, String location) {
        mName = name;
        mInterest = interest;
        mDate = date;
        mLocation = location;
    }
}