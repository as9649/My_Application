package com.example.myapplication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FBRef {
    public static String uid;
    public static String date;
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refPresence, refOrganizations, refOrg, refInactiveUsers, refActiveUsers, refUsersOnHold, refYears;

    public static void getUser(FirebaseUser fbuser, String organization){
        uid = fbuser.getUid();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String date = df.format(c);
        refOrganizations=FBDB.getReference("Organizations");
        refOrg=refOrganizations.child(organization);
        refPresence=refOrg.child("Presence").child(date).child(uid);
        refInactiveUsers=refOrg.child("Inactive users").child(uid);
        refActiveUsers=refOrg.child("Active users").child(uid);
        refUsersOnHold=refOrg.child("Users on hold").child(uid);
        refYears=FBDB.getReference("Years").child(uid);
    }
    public static FirebaseAuth refAuth=FirebaseAuth.getInstance();

}
