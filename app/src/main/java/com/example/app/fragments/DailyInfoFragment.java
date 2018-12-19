package com.example.app.fragments;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapters.ClassListAdapter;
import com.example.app.models.School;

import org.parceler.Parcels;

import java.util.TimeZone;

import static com.example.app.util.DateUtils.setDate;
import static com.example.app.util.DateUtils.setOnlyDate;
import static java.util.Calendar.MONDAY;

public class DailyInfoFragment extends Fragment implements CalendarView.OnDateChangeListener {

    private TextView mDate;
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
        mDate = view.findViewById(R.id.date_name);
        mDate.setText(setOnlyDate());
        RecyclerView classlistview = view.findViewById(R.id.class_list);
        //ClassListAdapter adapter = new ClassListAdapter(getContext(), )
        return view;
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        String date = (month+1) + " " + dayOfMonth + " " + year;
    }



}
