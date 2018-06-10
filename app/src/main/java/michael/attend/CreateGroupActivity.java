package michael.attend;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
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

    TextInputEditText groupDescription;
    ArrayList<ListData> GroupsList;
    ArrayList<ListData> totalGroups;
    ArrayList<User> usersInGroup;
    TextInputEditText groupName;
    DatabaseReference mDatabase;
    TextInputEditText hostName;
    String current_uid;
    int position;
    User user1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        if (current_uid == null) {
            current_uid = LoginHome.user_uid;
            Log.d("user_uid", current_uid);
        }
        Log.d("current_uid", current_uid);

    }

    public void onClick_CreateButton(View view) {
        user1 = LoginActivity.user1;
        GroupsList = new ArrayList<ListData>();
        totalGroups = new ArrayList<ListData>();
        usersInGroup = new ArrayList<User>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(current_uid).child("user_groups");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                try {
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        ListData listdata = postSnapshot.getValue(ListData.class);
//                        Log.d("ondatachange_listdata", listdata.toString());
//
//                        GroupsList.add(listdata);
//                        if(GroupsList != null){
//                            Log.d("ondatachange_usergroups", "groupslist not null !");
//
//                        }else{
//                            Log.d("ondatachange_usergroups", "groupslist null >:[");
//
//                        }
//                    }
//                    for (int i = 0; i < GroupsList.size(); i++){
//                        Log.d("ondatachange_groups", GroupsList.get(i).title);
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                Log.d("creategroup_listsize", String.valueOf(GroupsList.size()));
                groupName = findViewById(R.id.group_name);
                groupDescription = findViewById(R.id.group_description);
                hostName = findViewById(R.id.host_name);

                ListData group = new ListData();
                ListData allGroups = new ListData();

                group.title = groupName.getText().toString();
                group.description = groupDescription.getText().toString();
                group.hostName = hostName.getText().toString();

                Log.d("adding to user list", user1.getName());

                allGroups.title = groupName.getText().toString();






                int request_permission = 1;
                GPSLocator x = new GPSLocator(getApplicationContext());
                Location l = x.getLocation();
                if (l == null) {
                    ActivityCompat.requestPermissions(CreateGroupActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request_permission);
                }

//                if (l != null) {
//                    double lat = l.getLatitude();
//                    double lon = l.getLongitude();
//
//                    //makes attendance valid and record host's coordinates in firebase
//                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid).child("user_groups").child(String.valueOf(position)).child("location").child("lat");
//                    databaseReference.setValue(lat);
//
//                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid).child("user_groups").child(String.valueOf(position)).child("location").child("lon");
//                    databaseReference1.setValue(lon);
//                }
                group.latitude = String.valueOf(l.getLatitude());
                group.longitude = String.valueOf(l.getLongitude());
                allGroups.latitude = String.valueOf(l.getLatitude());
                allGroups.longitude = String.valueOf(l.getLongitude());

                user1.addGroup(group);
                GroupsList.add(group);
//                totalGroups.add(group); //changed here

                Log.d("GroupList: ", user1.groupList.toString());
                mDatabase = FirebaseDatabase.getInstance().getReference();
//                mDatabase.child("users").child(current_uid).child("user_groups").child("host_groups").setValue(GroupsList);
                mDatabase.child("users").child(current_uid).child("user_groups").child("host_groups").child(group.title).setValue(group);
                mDatabase.child("total_groups").child(allGroups.title).setValue(group);
                mDatabase.child("total_groups").child(allGroups.title).child("Users").setValue(usersInGroup);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ondata_error", "Error: ", databaseError.toException());
            }
        });
    }

    public void onResume(){
        super.onResume();
        current_uid = LoginHome.user_uid;
        Log.d("Create_onResume_uid: ", current_uid);

    }

    public void onBackPressed(){
        finish();
    }

}
