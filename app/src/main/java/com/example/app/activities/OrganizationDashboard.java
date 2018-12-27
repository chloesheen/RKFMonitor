package com.example.app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.app.R;
import com.example.app.adapters.SchoolListAdapter;
import com.example.app.models.Class;
import com.example.app.models.School;

import org.parceler.Parcels;

import java.util.ArrayList;

public class OrganizationDashboard extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<School> mSchoolList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_dashboard);
        ListView schoolList = (ListView) findViewById(R.id.school_listview);
        SchoolListAdapter adapter = new SchoolListAdapter(this, mSchoolList);
        schoolList.setAdapter(adapter);
        schoolList.setOnItemClickListener(this);

        School school1 = new School("Valley Academy", "0101", new ArrayList<Class>());
        School school2 = new School("Soko Primary", "0110", new ArrayList<Class>());
        School school3 = new School("NEXTGen", "0111", new ArrayList<Class>());
        School school4 = new School("SHAMCo", "1110", new ArrayList<Class>());
        School school5 = new School("Elimu de Ark", "1101", new ArrayList<Class>());

        mSchoolList.add(school1);
        mSchoolList.add(school2);
        mSchoolList.add(school3);
        mSchoolList.add(school4);
        mSchoolList.add(school5);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        School selectedschool = (School) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, SchoolInfoActivity.class);
        intent.putExtra("SelectedSchoolInfo", Parcels.wrap(selectedschool));
        startActivity(intent);
    }
}
