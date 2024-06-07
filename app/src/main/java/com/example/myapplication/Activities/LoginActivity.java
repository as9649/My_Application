package com.example.myapplication.Activities;

import static com.example.myapplication.FBRef.refActiveUsers;
import static com.example.myapplication.FBRef.refAuth;
import static com.example.myapplication.FBRef.refUsersOnHold;
import static com.example.myapplication.FBRef.uid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.FBRef;
import com.example.myapplication.Obj.User;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView tVtitle, tVregister;
    private EditText eTname, eTemail, eTpass;
    private CheckBox cBstayconnect;
    private Button btn;
    ArrayList<String> orgList;

    private String name;
    private String organization;
    public static User userdb;
    private static final String myUid="3uaXzQFKm2SMajBmmqtNANFjJ903";
    private Boolean registered,ok;
    private SharedPreferences settings;
    Intent si;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        initViews();
        registered=true;
        organization=null;
        regoption();
    }

    private void initViews() {
        tVtitle= findViewById(R.id.tVtitle);
        tVregister= findViewById(R.id.tVregister);
        eTname= findViewById(R.id.eTname);
        eTemail= findViewById(R.id.eTemail);
        eTpass= findViewById(R.id.eTpass);
        cBstayconnect= findViewById(R.id.cBstayconnect);
        btn= findViewById(R.id.btn);
        Spinner orgSpin = findViewById(R.id.orgSpin);
        orgList=FBRef.getOrganizationsList();
        ArrayAdapter<String> adp = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, orgList);
        orgSpin.setAdapter(adp);
        orgSpin.setOnItemSelectedListener(this);
    }
    /**
     * On activity start - Checking if user already logged in.
     * If logged in & asked to be remembered - pass on.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Boolean isChecked = settings.getBoolean("stayConnect", false);

        //if (refAuth.getCurrentUser() != null && isChecked) {
        if (refAuth.getCurrentUser()!=null && isChecked){

            if (refAuth.getCurrentUser().getUid().equals(myUid)) {
                Intent si = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(si);
            } else {
                organization=settings.getString("organization", " ");
                FBRef.getUser(refAuth.getCurrentUser(), organization);
                ok = false;
                refActiveUsers.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task2) {
                        userdb = task2.getResult().getValue(User.class);

                        if (userdb != null) {
                            if (userdb.isActive()) {
                                if (userdb.isManager())
                                    si = new Intent(LoginActivity.this, ManagerActivity.class);
                                else
                                    si = new Intent(LoginActivity.this, EmployeeActivity.class);
                            } else {
                                refUsersOnHold.setValue(userdb);
                                refActiveUsers.removeValue();
                                si = new Intent(LoginActivity.this, WaitingActivity.class);
                            }
                            ok = true;
                            si.putExtra("username", userdb.getUsername());
                            startActivity(si);
                        }
                    }
                });

                if (!ok) {
                    refUsersOnHold.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task3) {
                            userdb = task3.getResult().getValue(User.class);
                            if (userdb != null) {
                                if (userdb.isActive()) {
                                    refActiveUsers.setValue(userdb);
                                    refUsersOnHold.removeValue();

                                    if (userdb.isManager())
                                        si = new Intent(LoginActivity.this, ManagerActivity.class);
                                    else
                                        si = new Intent(LoginActivity.this, EmployeeActivity.class);
                                } else
                                    si = new Intent(LoginActivity.this, WaitingActivity.class);

                                ok = true;
                                si.putExtra("username", userdb.getUsername());
                                startActivity(si);
                            }
                        }
                    });
                }
            }
        }
    }


    private void regoption() {
        SpannableString ss = new SpannableString("Don't have an account?  Register here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Register");
                eTname.setVisibility(View.VISIBLE);
                btn.setText("Register");
                registered=false;
                logoption();
            }
        };
        ss.setSpan(span, 24, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void logoption() {
        SpannableString ss = new SpannableString("Already have an account?  Login here!");
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                tVtitle.setText("Login");
                eTname.setVisibility(View.INVISIBLE);
                btn.setText("Login");
                registered=true;
                regoption();
            }
        };
        ss.setSpan(span, 26, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tVregister.setText(ss);
        tVregister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Logging in or Registering to the application
     * Using:   Firebase Auth with email & password
     *          Firebase Realtime database with the object User to the branch Users
     * If login or register process is Ok saving stay connect status & pass to next activity
     */
    public void logorreg(View view) {
        if (organization!=null){
            String email = eTemail.getText().toString();
            String password = eTpass.getText().toString();

            if (email.isEmpty()){
                Toast.makeText(LoginActivity.this, "enter email", Toast.LENGTH_LONG).show();
            }
            else if (password.isEmpty()){
                Toast.makeText(LoginActivity.this, "enter password", Toast.LENGTH_LONG).show();
            }
            else if (registered) {
                String org=settings.getString("organization", null);

                if (org!=null && !organization.equals(org)){
                    Toast.makeText(LoginActivity.this, "Wrong organization", Toast.LENGTH_SHORT).show();
                }
                else {

                    final ProgressDialog pd = ProgressDialog.show(this, "Login", "Connecting...", true);
                    refAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task1) {
                                    pd.dismiss();
                                    if (task1.isSuccessful()) {

                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putBoolean("stayConnect", cBstayconnect.isChecked());
                                        editor.putString("organization", organization);
                                        editor.commit();
                                        if (refAuth.getCurrentUser().getUid().equals(myUid)) {

                                            Intent si = new Intent(LoginActivity.this, AdminActivity.class);
                                            startActivity(si);
                                        } else {
                                            FBRef.getUser(refAuth.getCurrentUser(), organization);
                                            ok = false;
                                            refActiveUsers.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DataSnapshot> task2) {
                                                    userdb = task2.getResult().getValue(User.class);

                                                    if (userdb != null) {
                                                        if (userdb.isActive()) {
                                                            if (userdb.isManager())
                                                                si = new Intent(LoginActivity.this, ManagerActivity.class);
                                                            else
                                                                si = new Intent(LoginActivity.this, EmployeeActivity.class);
                                                        } else {
                                                            refUsersOnHold.setValue(userdb);
                                                            refActiveUsers.removeValue();
                                                            si = new Intent(LoginActivity.this, WaitingActivity.class);
                                                        }
                                                        ok = true;
                                                        si.putExtra("username", userdb.getUsername());
                                                        startActivity(si);
                                                    }
                                                }
                                            });

                                            if (!ok) {
                                                refUsersOnHold.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DataSnapshot> task3) {
                                                        userdb = task3.getResult().getValue(User.class);
                                                        if (userdb != null) {
                                                            if (userdb.isActive()) {
                                                                refActiveUsers.setValue(userdb);
                                                                refUsersOnHold.removeValue();

                                                                if (userdb.isManager())
                                                                    si = new Intent(LoginActivity.this, ManagerActivity.class);
                                                                else
                                                                    si = new Intent(LoginActivity.this, EmployeeActivity.class);
                                                            } else
                                                                si = new Intent(LoginActivity.this, WaitingActivity.class);

                                                            SharedPreferences.Editor editor = settings.edit();
                                                            editor.putBoolean("stayConnect", cBstayconnect.isChecked());
                                                            editor.commit();
                                                            ok = true;
                                                            si.putExtra("username", userdb.getUsername());
                                                            startActivity(si);
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
            else {
                name = eTname.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "enter your name", Toast.LENGTH_LONG).show();
                } else {
                    final ProgressDialog pd = ProgressDialog.show(this, "Register", "Registering...", true);
                    refAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task4) {
                                    pd.dismiss();
                                    if (task4.isSuccessful()) {
                                        FBRef.getUser(refAuth.getCurrentUser(), organization);
                                        userdb = new User(uid, name, organization, false, false);
                                        refUsersOnHold.setValue(userdb);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putBoolean("stayConnect", cBstayconnect.isChecked());
                                        editor.putString("organization", organization);
                                        editor.commit();

                                        Toast.makeText(LoginActivity.this, "Successful registration", Toast.LENGTH_SHORT).show();
                                        //setActiveYear();
                                        Intent si = new Intent(LoginActivity.this, WaitingActivity.class);
                                        si.putExtra("username", userdb.getUsername());
                                        startActivity(si);

                                    } else {
                                        if (task4.getException() instanceof FirebaseAuthUserCollisionException)
                                            Toast.makeText(LoginActivity.this, "User with e-mail already exist!", Toast.LENGTH_SHORT).show();
                                        else {
                                            Log.w("LoginActivity", "createUserWithEmail:failure", task4.getException());
                                            Toast.makeText(LoginActivity.this, "User create failed.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                }
            }
        }
        else
            Toast.makeText(LoginActivity.this, "select organization", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0){
            organization=orgList.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}