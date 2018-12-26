package com.example.app.fragments;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.activities.AttendanceActivity;
import com.example.app.adapters.ClassListAdapter;
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.interfaces.ClickListener;
import com.example.app.models.Class;
import com.example.app.models.School;
import com.example.app.models.Student;
import com.example.app.util.ChildViewClickListener;

import org.parceler.Parcels;

import java.util.TimeZone;

import static com.example.app.util.DateUtils.setDate;
import static com.example.app.util.DateUtils.setOnlyDate;
import static java.util.Calendar.MONDAY;

public class DailyInfoFragment extends Fragment implements ClickListener {

    private TextView mDate;
    private School mSchoolData;
    private ClassListAdapter mAdapter;

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
        RecyclerView mClasslistview = view.findViewById(R.id.class_list);
        mClasslistview.setFocusable(true);
        mAdapter = new ClassListAdapter(getContext(), mSchoolData.getClasses());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mClasslistview.setLayoutManager(mLayoutManager);
        mClasslistview.setAdapter(mAdapter);
        mClasslistview.addOnItemTouchListener(new ChildViewClickListener( getContext(), mClasslistview, this));

        return view;
    }

    @Override
    public void onClick(View v, final int position) {
        final TextView classname = v.findViewById(R.id.class_name);
        classname.setFocusable(true);
        classname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.requestFocus();
                Class selectedclass = mAdapter.getSelectedClass(position);
                Intent intent = new Intent(getContext(), AttendanceActivity.class);
                intent.putExtra("selectedclass", Parcels.wrap(selectedclass));
                startActivity(intent);
            }
        });
    }
}
