package michael.attend;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateGroupActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    final Context context = this;
    TextInputEditText groupName;
    TextInputEditText groupDescription;
    TextInputEditText hostName;
    User user1;
    String current_uid;
    ArrayList<ListData> GroupsList;
//    String user_uid = LoginActivity.user1.getName();
//    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
//        auth = FirebaseAuth.getInstance();
        if (current_uid == null){
//            current_uid = LoginHome.auth.getCurrentUser().getUid();
            current_uid = LoginHome.user_uid;
            Log.d("user_uid", current_uid);
        }

        Log.d("current_uid", current_uid);
//        Log.d("user1_name", user_uid);
    }

    public void onBackPressed(){
        finish();
    }

    public void onclick_creategroup(View view) {
        user1 = LoginActivity.user1;
//        current_uid = LoginActivity.auth.getUid();
//        Log.d("current_uid", current_uid);

        GroupsList=new ArrayList<ListData>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid).child("user_groups");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ListData listdata = postSnapshot.getValue(ListData.class);
                        Log.d("ondatachange_listdata", listdata.toString());

                        GroupsList.add(listdata);
                        if(GroupsList != null){
                            Log.d("ondatachange_usergroups", "groupslist not null !");

                        }else{
                            Log.d("ondatachange_usergroups", "groupslist null >:[");

                        }
                    }
                    for (int i = 0; i < GroupsList.size(); i++){
                        Log.d("ondatachange_groups", GroupsList.get(i).title);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("creategroup_listsize", String.valueOf(GroupsList.size()));
                groupName = findViewById(R.id.group_name);
                groupDescription = findViewById(R.id.group_description);
                hostName = findViewById(R.id.host_name);
                ListData group = new ListData();
                group.title = groupName.getText().toString();
                group.description = groupDescription.getText().toString();
                group.hostName = hostName.getText().toString();
//        ArrayList<ListData> group = new ArrayList<ListData>();
//        groupNames.add(groupName.getText().toString());
                user1.addGroup(group);
                GroupsList.add(group);
                Log.d("GroupList: ", user1.groupList.toString());
//        DatabaseReference dbf = mDatabase.child("users").push();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(current_uid).child("user_groups").setValue(GroupsList);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ondata_error", "Error: ", databaseError.toException());
            }
        });
//        Log.d("creategroup_listsize", String.valueOf(GroupsList.size()));
//        groupName = findViewById(R.id.group_name);
//        groupDescription = findViewById(R.id.group_description);
//        hostName = findViewById(R.id.host_name);
//        ListData group = new ListData();
//        group.title = groupName.getText().toString();
//        group.description = groupDescription.getText().toString();
//        group.hostName = hostName.getText().toString();
////        ArrayList<ListData> group = new ArrayList<ListData>();
////        groupNames.add(groupName.getText().toString());
//        user1.addGroup(group);
//        GroupsList.add(group);
//        Log.d("GroupList: ", user1.groupList.toString());
////        DatabaseReference dbf = mDatabase.child("users").push();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("users").child(current_uid).child("user_groups").setValue(user1.groupList);
//        finish();

//        Intent viewGroupsIntent = new Intent(context, ViewGroupsActivity.class);
//        viewGroupsIntent.putExtra("groupName", groupName.getText().toString());
//        startActivity(viewGroupsIntent);
    }

    public void onResume(){
        super.onResume();
//        current_uid = LoginHome.auth.getCurrentUser().getUid();
        current_uid = LoginHome.user_uid;
        Log.d("Create_onReume_uid: ", current_uid);

    }
}
