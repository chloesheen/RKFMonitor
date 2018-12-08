package com.example.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.models.School;
import com.example.app.models.Student;
import com.example.app.util.Pair;

import java.util.ArrayList;

public class SchoolListAdapter extends ArrayAdapter<School> {

    private ArrayList<School> mSchoolList;


    public SchoolListAdapter(Context context, ArrayList<School> schools) {
        super(context, 0, schools);
        mSchoolList = schools;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        final School school = mSchoolList.get(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.school_list_item, parent, false);
        }

        TextView schoolName = (TextView) view.findViewById(R.id.nameOfSchool);
        schoolName.setText(school.getSchoolName());
        return view;
    }
}
