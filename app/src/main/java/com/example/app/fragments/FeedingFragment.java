package com.example.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.activities.AttendanceActivity;
import com.example.app.adapters.ClassListAdapter;
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Calendar;
import com.example.app.models.School;
import com.example.app.util.ChildViewClickListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import static com.example.app.util.Constants.GET_DAILY_FOOD;
import static com.example.app.util.Constants.GET_MONTHLY_FOOD;
import static com.example.app.util.DateUtils.setOnlyDate;

public class FeedingFragment extends Fragment implements CallbackListener {

    private TextView mDate;
    private School mSchoolData;
    private ClassListAdapter mAdapter;
    private Class mSelectedclass;
    private CallbackListener mListener;

    public static FeedingFragment newInstance (School school){
        FeedingFragment fragment = new FeedingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("SchoolInformation", Parcels.wrap(school));
        fragment.setArguments(bundle);
        return fragment;
    };


    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        mListener = this;
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
                mSelectedclass = mAdapter.getSelectedClass(position);
                HttpGetRequests task = new HttpGetRequests(GET_DAILY_ATTENDANCE, mListener, getContext());
                task.execute("");
            }
        });
    }

    @Override
    public void onCompletionHandler(boolean success, int requestcode, Object obj){
        if (success) {
            switch(requestcode) {
                case GET_DAILY_FOOD:
                    ArrayList<Calendar> dailycalendars = (ArrayList<Calendar>) obj;
                    for (Calendar calendar : dailycalendars) {
                        mSelectedclass.addCalendar(calendar);
                    }
                    Intent intent = new Intent(getContext(), AttendanceActivity.class);
                    intent.putExtra("selectedclass", Parcels.wrap(mSelectedclass));
                    startActivity(intent);
                    break;
                case GET_MONTHLY_FOOD:
                    ArrayList<Calendar> monthlycalendars = (ArrayList<Calendar>) obj;
                    for (Calendar calendar : monthlycalendars) {
                        mSelectedclass.addCalendar(calendar);
                    }
                    Intent monthlyintent = new Intent(getContext(), AttendanceActivity.class);
                    monthlyintent.putExtra("selectedclass", Parcels.wrap(mSelectedclass));
                    startActivity(monthlyintent);
                    break;
            }
        }
    }
}
