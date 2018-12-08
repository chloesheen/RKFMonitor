package com.example.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.models.School;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthlyInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthlyInfoFragment extends Fragment {


    private School mSchool;

    private TextView mSchoolName;
    private TextView mAcademicYear;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param school School
     * @return A new instance of fragment MonthlyInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthlyInfoFragment newInstance(School school) {
        MonthlyInfoFragment fragment = new MonthlyInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("School", Parcels.wrap(school));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSchool = Parcels.unwrap(getArguments().getParcelable("School"));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monthly_info, container, false);
        mSchoolName = view.findViewById(R.id.dashboard_schoolname);
        mAcademicYear = view.findViewById(R.id.academic_year);

        ListView monthList = view.findViewById(R.id.monthList);
        ArrayList<String> months = new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, months);
        monthList.setAdapter(adapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
