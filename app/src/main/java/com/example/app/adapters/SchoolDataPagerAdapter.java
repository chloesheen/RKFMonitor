package com.example.app.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.app.fragments.AttendanceFragment;
import com.example.app.fragments.FeedingFragment;
import com.example.app.fragments.MonthlyInfoFragment;
import com.example.app.models.School;

public class SchoolDataPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String mTabTitles[] = {"Attending", "Feeding"};
    private Context mContext;
    private School mSchool;
    public static boolean monthly; // boolean to see if we should get monthly or daily information,
    // monthly = true -> month data
    // monthly = false -> daily data


    //add parameter for daily or monthly tab
    public SchoolDataPagerAdapter(FragmentManager manager, Context context, School school, boolean isMonthly) {
        super(manager);
        monthly = isMonthly;
        mContext = context;
        mSchool = school;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) { // tabs for attendance/ feeding
        if (position == 0) { // attendance
            return AttendanceFragment.newInstance(mSchool,this.monthly);
        } else if (position == 1) { // feeding
            return FeedingFragment.newInstance(mSchool,this.monthly);
        }
        return null;
    }
    

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}
