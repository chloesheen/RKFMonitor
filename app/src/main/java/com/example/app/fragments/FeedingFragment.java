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
import com.example.app.activities.FeedingActivity;
import com.example.app.adapters.ClassListAdapter;
import com.example.app.adapters.SchoolFeedingAdapter;
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.interfaces.CallbackListener;
import com.example.app.interfaces.ClickListener;
import com.example.app.models.Calendar;
import com.example.app.models.School;
import com.example.app.util.ChildViewClickListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;

import static com.example.app.util.Constants.GET_DAILY_FOOD;
import static com.example.app.util.Constants.GET_MONTHLY_FOOD;
import static com.example.app.util.DateUtils.setOnlyDate;

public class FeedingFragment extends Fragment implements CallbackListener, ClickListener {

    private School mSchoolData;
    private int mRequestCode;
    private SchoolFeedingAdapter mAdapter;
    private Date mSelecteddate;
    private CallbackListener mListener;

    //parameter for request code
    public static FeedingFragment newInstance (School school, int request){
        FeedingFragment fragment = new FeedingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("SchoolInformation", Parcels.wrap(school));
        bundle.putInt("RequestCode", request);
        fragment.setArguments(bundle);
        return fragment;
    };


    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        mListener = this;
        if (getArguments() != null) {
            mSchoolData = Parcels.unwrap(getArguments().getParcelable("SchoolInformation"));
            mRequestCode = getArguments().getInt("RequestCode");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feeding, container, false);
        RecyclerView mDatelistview = view.findViewById(R.id.dateList);
        mDatelistview.setFocusable(true);

        mAdapter = new SchoolFeedingAdapter(getContext(), mSchoolData.getCalendars());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mDatelistview.setLayoutManager(mLayoutManager);
        mDatelistview.setAdapter(mAdapter);
        mDatelistview.addOnItemTouchListener(new ChildViewClickListener( getContext(), mDatelistview, this));

        return view;
    }

    @Override
    public void onClick(View v, final int position) {
        final TextView datename = v.findViewById(R.id.class_name);
        datename.setFocusable(true);
        datename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.requestFocus();
                mSelecteddate = mAdapter.getSelectedDate(position);
                HttpGetRequests task = new HttpGetRequests(mRequestCode, mListener, getContext());
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
                        mSchoolData.addCalendar(calendar);
                    }
                    Intent intent = new Intent(getContext(), FeedingActivity.class);
                    intent.putExtra("selectedclass", Parcels.wrap(mSchoolData));
                    startActivity(intent);
                    break;
                case GET_MONTHLY_FOOD:
                    ArrayList<Calendar> monthlycalendars = (ArrayList<Calendar>) obj;
                    for (Calendar calendar : monthlycalendars) {
                        mSchoolData.addCalendar(calendar);
                    }
                    Intent monthlyintent = new Intent(getContext(), AttendanceActivity.class);
                    monthlyintent.putExtra("selectedclass", Parcels.wrap(mSchoolData));
                    startActivity(monthlyintent);
                    break;
            }
        }
    }
}
