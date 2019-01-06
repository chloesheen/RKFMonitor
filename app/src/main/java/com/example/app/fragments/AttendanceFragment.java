package com.example.app.fragments;

import android.content.Intent;
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
import com.example.app.adapters.SchoolDataPagerAdapter;
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.interfaces.CallbackListener;
import com.example.app.interfaces.ClickListener;
import com.example.app.models.Class;
import com.example.app.models.School;
import com.example.app.models.Student;
import com.example.app.util.ChildViewClickListener;
import com.example.app.models.Calendar;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.TimeZone;

import static com.example.app.util.Constants.GET_DAILY_ATTENDANCE;
import static com.example.app.util.Constants.GET_MONTHLY_ATTENDANCE;
import static com.example.app.util.DateUtils.setDate;
import static com.example.app.util.DateUtils.setOnlyDate;


public class AttendanceFragment extends Fragment implements ClickListener, CallbackListener {

    private School mSchoolData;
    private ClassListAdapter mAdapter;
    private Class mSelectedclass;
    private CallbackListener mListener;
    private static boolean monthly; // class variable that will copy in variable from new instance 


    //add parameter for appropriate request code
    public static AttendanceFragment newInstance (School school, boolean isMonthly){
        AttendanceFragment fragment = new AttendanceFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("SchoolInformation", Parcels.wrap(school));
        fragment.setArguments(bundle);
        monthly = isMonthly; // copies in variable that will be the value of the fragment's boolean variable  of whether it is daily or monthly
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
                //Something here to send back to the OrgFragmentAdapter
                mSelectedclass = mAdapter.getSelectedClass(position);
                HttpGetRequests task = new HttpGetRequests(getRequestCode(monthly), mListener, getContext()); // fetches data
                task.execute("");
            }
        });
    }
    // helper function that returns request code based on the boolean value 
    // needs to be copied in the Feeding fragment 
    //used on line 94
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
                case GET_DAILY_ATTENDANCE:
                    ArrayList<Calendar> dailycalendars = (ArrayList<Calendar>) obj;
                    for (Calendar calendar : dailycalendars) {
                        mSelectedclass.addCalendar(calendar);
                    }
                    Intent intent = new Intent(getContext(), AttendanceActivity.class);
                    intent.putExtra("selectedclass", Parcels.wrap(mSelectedclass));
                    startActivity(intent);
                    break;
                case GET_MONTHLY_ATTENDANCE:
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
