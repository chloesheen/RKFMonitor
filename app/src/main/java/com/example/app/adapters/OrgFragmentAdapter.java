package com.example.app.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.app.fragments.DailyFragment;
import com.example.app.fragments.MonthlyFragment;
import com.example.app.fragments.MonthlyInfoFragment;
import com.example.app.models.School;

public class OrgFragmentAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String mTabTitles[] = {"Daily", "Monthly"};
    private Context mContext;
    private School mSchool;

    public OrgFragmentAdapter(FragmentManager manager, Context context, School school) {
        super(manager);
        mContext = context;
        mSchool = school;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            //pass in parameter for daily;
            return DailyFragment.newInstance(mSchool);
        } else if (position == 1) {
            //add parameter for monthly;
            return MonthlyFragment.newInstance(mSchool);
        }
        return null;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}

