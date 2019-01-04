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
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Class;
import com.example.app.models.School;

import org.parceler.Parcels;

import java.util.ArrayList;

import static com.example.app.util.Constants.GET_CLASSES;
import static com.example.app.util.Constants.GET_SCHOOLS;

public class OrganizationDashboard extends AppCompatActivity implements AdapterView.OnItemClickListener, CallbackListener {

    private ArrayList<School> mSchoolList = new ArrayList<>();
    private School mSelectedSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_dashboard);
        ListView schoolList = (ListView) findViewById(R.id.school_listview);
        SchoolListAdapter adapter = new SchoolListAdapter(this, mSchoolList);
        schoolList.setAdapter(adapter);
        schoolList.setOnItemClickListener(this);

        HttpGetRequests task = new HttpGetRequests(GET_SCHOOLS, this, this);
        task.execute("");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSelectedSchool = (School) parent.getItemAtPosition(position);
        HttpGetRequests task = new HttpGetRequests(GET_CLASSES, this, this);
        task.execute("");
    }

    @Override
    public void onCompletionHandler(boolean success, int requestcode, Object obj) {
        if (success) {
            switch (requestcode) {
                case GET_SCHOOLS:
                    mSchoolList = (ArrayList<School>) obj;
                    break;

                case GET_CLASSES:
                    ArrayList<Class> mClassList = (ArrayList<Class>) obj;
                    mSelectedSchool.addClasses(mClassList);
                    Intent intent = new Intent(this, SchoolInfoActivity.class);
                    intent.putExtra("SelectedSchoolInfo", Parcels.wrap(mSelectedSchool));
                    startActivity(intent);
                    break;
            }
        }
    }
}
