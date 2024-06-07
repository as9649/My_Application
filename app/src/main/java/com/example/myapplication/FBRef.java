package com.example.myapplication;
import androidx.annotation.NonNull;

import com.example.myapplication.Obj.Organization;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FBRef {
    public static String uid, date;
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static FirebaseAuth refAuth=FirebaseAuth.getInstance();
    public static DatabaseReference refOrganizations=FBDB.getReference("Organizations");
    public static DatabaseReference refOrganizationsList=FBDB.getReference("Organizations list");
    public static DatabaseReference refPresence, refOrg, refActiveUsers, refUsersOnHold;


    /*
    הפעולה מחזירה את רשימת שמות הארגונים שקיימים בבסיס הנתונים
     */
    public static ArrayList<String> getOrganizationsList(){
        ArrayList<String> orgList=new ArrayList<String>();
        orgList.add("Organization:");
        orgList.add("myOrg");
        refOrganizationsList.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    DataSnapshot dataSnapshot=task.getResult();
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        orgList.add(data.getKey());
                    }
                }
            }
        });
        return orgList;
    }

    public static void getUser(FirebaseUser fbuser, String organization){
        uid = fbuser.getUid();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String date = df.format(c);

        refOrg=refOrganizations.child(organization);
        refPresence=refOrg.child("Presence").child(date).child(uid);
        refActiveUsers=refOrg.child("Active users").child(uid);
        refUsersOnHold=refOrg.child("Users on hold").child(uid);
    }


}
