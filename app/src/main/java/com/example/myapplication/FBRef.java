package com.example.myapplication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBRef {
    public static String uid;
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refInactiveUsers=FBDB.getReference("Inactive users");
    public static DatabaseReference refActiveUsers=FBDB.getReference("Active users");
    public static DatabaseReference refUsersOnHold=FBDB.getReference("Users on hold");
    public static void getUser(FirebaseUser fbuser, String organization){
        uid = fbuser.getUid();
        refInactiveUsers=FBDB.getReference(organization).child("Inactive users").child(uid);
        refActiveUsers=FBDB.getReference(organization).child("Active users").child(uid);
        refUsersOnHold=FBDB.getReference(organization).child("Users on hold").child(uid);
    }
    public static FirebaseAuth refAuth=FirebaseAuth.getInstance();

}
