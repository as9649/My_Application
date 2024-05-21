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

import com.example.myapplication.Obj.User;
import com.example.myapplication.R;
import com.example.myapplication.FBRef;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {
    Button listBtn;
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

    }

    private void initViews() {
        listBtn=findViewById(R.id.listBtn);
        listBtn.setText("");
        nonActiveUsers = new ArrayList<User>();
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

    }
}