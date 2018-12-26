package com.example.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapters.SchoolAttendanceAdapter;

import org.parceler.Parcels;

public class AttendanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Class selectedClass = Parcels.unwrap(getIntent().getParcelableExtra("selectedclass"));

        TextView className = (TextView) findViewById(R.id.thisclass);
        className.setText(selectedClass.getName());

        RecyclerView dailyAttendanceList = (RecyclerView) findViewById(R.id.attendances);
        //SchoolAttendanceAdapter adapter = new SchoolAttendanceAdapter(this, selectedClass.)
    }
}
