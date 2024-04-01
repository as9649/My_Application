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
    public static DatabaseReference refTasks;
    public static DatabaseReference refDoneTasks;
    public static DatabaseReference refYears;
    public static void getUser(FirebaseUser fbuser){
        uid = fbuser.getUid();
        refTasks=FBDB.getReference("Tasks").child(uid);
        refDoneTasks=FBDB.getReference("Done_Tasks").child(uid);
        refYears=FBDB.getReference("Years").child(uid);
    }
    public static FirebaseAuth refAuth=FirebaseAuth.getInstance();

}
