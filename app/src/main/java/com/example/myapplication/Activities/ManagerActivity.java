package com.example.myapplication.Activities;

import static com.example.myapplication.FBRef.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Obj.Presence;
import com.example.myapplication.Obj.User;
import com.example.myapplication.R;
import com.example.myapplication.FBRef;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ManagerActivity extends AppCompatActivity {
    Button listBtn, attendanceListBtn;
    public static ArrayList<Presence> attendanceList;

    public static ArrayList<User> nonActiveUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        initViews();

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent si = new Intent(ManagerActivity.this, nonActiveListActivity.class);
                startActivity(si);
            }
        });

        attendanceListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent si = new Intent(ManagerActivity.this, AttendanceListActivity.class);
                startActivity(si);
            }
        });

    }

    private void initViews() {
        listBtn=findViewById(R.id.listBtn);
        attendanceListBtn=findViewById(R.id.attendanceListBtn);
        nonActiveUsers = new ArrayList<User>();
        attendanceList=new ArrayList<Presence>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refOrg.child("Users on hold").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    DataSnapshot dS = task.getResult();
                    nonActiveUsers.clear();
                    for (DataSnapshot data : dS.getChildren()){
                        User user=data.getValue(User.class);
                        if (!user.isActive())
                            nonActiveUsers.add(data.getValue(User.class));
                    }
                    listBtn.setText(""+nonActiveUsers.size());
                }
            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String date = df.format(c);

        refOrg.child("Presence").child(date).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task2) {
                if (task2.isSuccessful()){
                    DataSnapshot dataSnapshot=task2.getResult();
                    attendanceList.clear();
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        Presence presence=data.getValue(Presence.class);
                        attendanceList.add(presence);
                    }
                }
                attendanceListBtn.setText("Attendance list ("+attendanceList.size()+")");
            }
        });

    }
}