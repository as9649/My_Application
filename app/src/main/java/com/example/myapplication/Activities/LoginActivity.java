package com.example.myapplication.Activities;

import static com.example.myapplication.FBRef.refActiveUsers;
import static com.example.myapplication.FBRef.refAuth;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Obj.User;
import com.example.myapplication.R;
import com.example.myapplication.FBRef;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView tVtitle, tVregister;
    private EditText eTname, eTemail, eTpass;
    private CheckBox cBstayconnect;
    private Button btn;

    private String name, email, password, uid;
    private User userdb;
    private Boolean stayConnect, registered;
    private SharedPreferences settings;
    private int activeYear = 1970;
    private final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        initViews();
        stayConnect=false;
        registered=true;
        regoption();
    }

    /**
     * On activity start - Checking if user already logged in.
     * If logged in & asked to be remembered - pass on.
     */
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Boolean isChecked=settings.getBoolean("stayConnect",false);
//        Intent si = new Intent(LoginActivity.this,MainActivity.class);
//        if (refAuth.getCurrentUser()!=null && isChecked) {
//            FBRef.getUser(refAuth.getCurrentUser());
//            stayConnect=true;
//            si.putExtra("isNewUser",false);
//            startActivity(si);
//        }
//    }

    /**
     * On activity pause - If logged in & asked to be remembered - kill activity.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (stayConnect) finish();
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
        if (registered) {
            email=eTemail.getText().toString();
            password=eTpass.getText().toString();

            final ProgressDialog pd=ProgressDialog.show(this,"Login","Connecting...",true);
            refAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                FBRef.getUser(refAuth.getCurrentUser());
                                //Log.d("MainActivity", "signinUserWithEmail:success");
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                settings = getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                                activeYear = settings.getInt("activeYear",1970);
                                if (activeYear == 1970) {
                                //    lostData();
                                } else {
                                    SharedPreferences.Editor editor=settings.edit();
                                    editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                                    editor.commit();
                                    //Intent si = new Intent(LoginActivity.this,MainActivity.class);
                                    //startActivity(si);
                                }
                            } else {
                                //Log.d("MainActivity", "signinUserWithEmail:fail");
                                Toast.makeText(LoginActivity.this, "e-mail or password are wrong!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            name=eTname.getText().toString();
            email=eTemail.getText().toString();
            password=eTpass.getText().toString();

            final ProgressDialog pd=ProgressDialog.show(this,"Register","Registering...",true);
            refAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                FirebaseUser user = refAuth.getCurrentUser();
                                FBRef.getUser(user);
                                //Log.d("MainActivity", "createUserWithEmail:success");
                                uid = user.getUid();
                                userdb=new User(uid,name,null,0,null,true,null);
                                refActiveUsers.child(uid).setValue(userdb);
                                Toast.makeText(LoginActivity.this, "Successful registration", Toast.LENGTH_SHORT).show();
                                //setActiveYear();
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(LoginActivity.this, "User with e-mail already exist!", Toast.LENGTH_SHORT).show();
                                else {
                                    //Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "User create failed.",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
        }
    }


    private void initViews() {
        tVtitle=(TextView) findViewById(R.id.tVtitle);
        eTname=(EditText)findViewById(R.id.eTname);
        eTemail=(EditText)findViewById(R.id.eTemail);
        eTpass=(EditText)findViewById(R.id.eTpass);
        cBstayconnect=(CheckBox)findViewById(R.id.cBstayconnect);
        tVregister=(TextView) findViewById(R.id.tVregister);
        btn=(Button)findViewById(R.id.btn);
    }
}