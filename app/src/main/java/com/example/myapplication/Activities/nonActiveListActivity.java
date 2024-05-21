package com.example.myapplication.Activities;

import static com.example.myapplication.Activities.ManagerActivity.nonActiveUsers;
import static com.example.myapplication.FBRef.refOrg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Obj.User;
import com.example.myapplication.R;

import java.util.ArrayList;

public class nonActiveListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    AlertDialog.Builder adb;

    public static String[] nameslist=new String[nonActiveUsers.size()];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_active_list);

        initViews();
    }

    private void initViews() {
        listView=findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this);

        for (int i=0; i<nonActiveUsers.size(); i++){
            nameslist[i]= nonActiveUsers.get(i).getUsername();
        }

        ArrayAdapter<String> adp=new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, nameslist);
        listView.setAdapter(adp);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = nonActiveUsers.get(position);
        user.setActive(true);
        adb=new AlertDialog.Builder(this);
        adb.setTitle("Activate "+user.getUsername()+"?");
        adb.setMessage("Are you sure?");

        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                refOrg.child("Active users").child(user.getUid()).setValue(user);
                refOrg.child("Users on hold").child(user.getUid()).removeValue();
                nonActiveUsers.remove(position);
                nameslist=new String[nonActiveUsers.size()];
                for (int i=0; i<nonActiveUsers.size(); i++){
                    nameslist[i]= nonActiveUsers.get(i).getUsername();
                }
                ArrayAdapter<String> adp=new ArrayAdapter<String>(nonActiveListActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, nameslist);
                listView.setAdapter(adp);
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog ad=adb.create();
        ad.show();
    }
}