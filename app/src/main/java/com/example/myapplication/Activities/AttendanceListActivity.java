package com.example.myapplication.Activities;

import static com.example.myapplication.Activities.ManagerActivity.attendanceList;
import static com.example.myapplication.FBRef.refOrg;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Obj.Presence;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AttendanceListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView attendanceLV;
    AlertDialog.Builder adb2;
    //private ValueEventListener vel;
    String[] attendanceArr=new String[attendanceList.size()];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        attendanceLV=findViewById(R.id.attendanceLV);

        for (int i=0; i<attendanceList.size(); i++){
            Presence presence=attendanceList.get(i);
            attendanceArr[i]=presence.getUsername();
        }

        ArrayAdapter<String> adp=new ArrayAdapter<String>(AttendanceListActivity.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, attendanceArr);
        attendanceLV.setAdapter(adp);

        attendanceLV.setOnItemClickListener(this);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent si=new Intent(AttendanceListActivity.this, MapActivity.class);
        si.putExtra("latitude",attendanceList.get(position).getLatitude());
        si.putExtra("longitude", attendanceList.get(position).getLongitude());
        startActivity(si);
    }
}