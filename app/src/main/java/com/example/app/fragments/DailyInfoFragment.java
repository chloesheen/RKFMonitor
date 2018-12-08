package com.example.app.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.models.School;

import org.parceler.Parcels;

public class DailyInfoFragment extends Fragment implements CalendarView.OnDateChangeListener {

    private CalendarView mCalendar;
    private TextView mSchoolname;

    private School mSchoolData;

    public static DailyInfoFragment newInstance (School school){
        DailyInfoFragment fragment = new DailyInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("SchoolInformation", Parcels.wrap(school));
        fragment.setArguments(bundle);
        return fragment;
    };


    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSchoolData = Parcels.unwrap(getArguments().getParcelable("SchoolInformation"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_info, container, false);
        mCalendar = view.findViewById(R.id.masterCalendar);

        return view;
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        String date = (month+1) + " " + dayOfMonth + " " + year;
    }



}
