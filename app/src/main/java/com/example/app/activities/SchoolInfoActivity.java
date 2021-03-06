package com.example.app.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapters.OrgFragmentAdapter;
import com.example.app.adapters.SchoolDataPagerAdapter;
import com.example.app.models.School;

import org.parceler.Parcels;

public class SchoolInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_info);
        School mSelectedSchool = Parcels.unwrap(getIntent().getParcelableExtra("SelectedSchoolInfo"));
        TextView mSchoolName = (TextView) findViewById(R.id.dashboard_schoolname);
        mSchoolName.setText(mSelectedSchool.getSchoolName());

        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        //attach the onpagechangelistener to the viewpager
        viewpager.setAdapter(new OrgFragmentAdapter(getSupportFragmentManager(), this, mSelectedSchool));

        TabLayout layout = (TabLayout) findViewById(R.id.sliding_tabs);
        layout.setupWithViewPager(viewpager);
    }
}
