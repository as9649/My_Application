package com.example.myapplication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBRef {
    public static String uid;
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refOrg, refInactiveUsers, refActiveUsers, refUsersOnHold, refYears;
    public static void getUser(FirebaseUser fbuser, String organization){
        uid = fbuser.getUid();
        refOrg=FBDB.getReference(organization);
        refInactiveUsers=refOrg.child("Inactive users").child(uid);
        refActiveUsers=refOrg.child("Active users").child(uid);
        refUsersOnHold=refOrg.child("Users on hold").child(uid);
        refYears=refOrg.child("Years").child(uid);
    }
    public static FirebaseAuth refAuth=FirebaseAuth.getInstance();

}
