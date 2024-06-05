package com.example.myapplication.Activities;

import static com.example.myapplication.FBRef.*;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Obj.Organization;
import com.example.myapplication.R;

public class AdminActivity extends AppCompatActivity {

    EditText orgNameET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        orgNameET=findViewById(R.id.orgNameET);

    }

    public void createNewOrg(View view) {
        Organization newOrg=new Organization(orgNameET.getText().toString());
        refOrganizationsList.setValue(newOrg);
        orgNameET.setText("");
    }
}