package com.example.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;
import com.example.app.adapters.SchoolDataPagerAdapter;
import com.example.app.models.School;

import org.parceler.Parcels;

public class DailyFragment extends Fragment {
    private School mSchoolData;

    public static DailyFragment newInstance (School school){
        DailyFragment fragment = new DailyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("SchoolInformation", Parcels.wrap(school));
        fragment.setArguments(bundle);
        return fragment;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSchoolData = Parcels.unwrap(getArguments().getParcelable("SchoolInformation"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        ViewPager viewpager = view.findViewById(R.id.dailypager);

        //add the parameter for daily or monthly
        viewpager.setAdapter(new SchoolDataPagerAdapter(getFragmentManager(), getContext(), mSchoolData));

        TabLayout layout = view.findViewById(R.id.sliding_tabs);
        layout.setupWithViewPager(viewpager);

        return view;
    }

}
