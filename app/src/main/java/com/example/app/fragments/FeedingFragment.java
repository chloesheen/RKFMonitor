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
import com.example.app.models.Food;
import com.example.app.models.School;
import com.example.app.util.ChildViewClickListener;

import org.parceler.Parcels;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import static com.example.app.util.Constants.GET_DAILY_ATTENDANCE;
import static com.example.app.util.Constants.GET_DAILY_FOOD;
import static com.example.app.util.Constants.GET_MONTHLY_ATTENDANCE;
import static com.example.app.util.Constants.GET_MONTHLY_FOOD;
import static com.example.app.util.DateUtils.setOnlyDate;

public class FeedingFragment extends Fragment implements CallbackListener, ClickListener {

    private School mSchoolData;
    private int mRequestCode;
    private SchoolFeedingAdapter mAdapter;
    private Date mSelecteddate;
    private CallbackListener mListener;
    private static boolean monthly;

    //parameter for request code
    public static FeedingFragment newInstance (School school, boolean isMonthly){
        FeedingFragment fragment = new FeedingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("SchoolInformation", Parcels.wrap(school));
        fragment.setArguments(bundle);
        monthly = isMonthly;
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
        //add get request for retrieving feeding calendar from db
        HttpGetRequests task = new HttpGetRequests(getRequestCode(monthly), mListener, getContext());
        task.execute("");
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
                Calendar selectedDate = mAdapter.getSelectedDate(position);
                ArrayList<Food> foodServed = (ArrayList<Food>) selectedDate.getSecond();
                Intent intent = new Intent(getContext(), FeedingActivity.class);
                intent.putExtra("selectedfood", Parcels.wrap(foodServed));
                startActivity(intent);

            }
        });
    }

    private int getRequestCode (boolean isMonthly){
        if (isMonthly)
            return GET_MONTHLY_ATTENDANCE;
        else
            return GET_DAILY_ATTENDANCE;
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
                    break;
                case GET_MONTHLY_FOOD:
                    ArrayList<Calendar> monthlycalendars = (ArrayList<Calendar>) obj;
                    for (Calendar calendar : monthlycalendars) {
                        mSchoolData.addCalendar(calendar);
                    }
                    break;
            }
        }
    }
}
