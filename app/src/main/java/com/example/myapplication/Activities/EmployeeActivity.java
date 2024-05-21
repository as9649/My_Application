package com.example.myapplication.Activities;

import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
public class EmployeeActivity extends AppCompatActivity {

    Button locationBtn;
    TextView titleTV;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        initViews();



        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initViews() {
        titleTV=findViewById(R.id.titleTV);
        locationBtn=findViewById(R.id.locationBtn);
        titleTV.setText("hello");

    }
}