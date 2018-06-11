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

                Log.d("creategroup_listsize", String.valueOf(GroupsList.size()));
                groupName = findViewById(R.id.group_name);
                groupDescription = findViewById(R.id.group_description);
                hostName = findViewById(R.id.host_name);

                ListData group = new ListData();
                ListData allGroups = new ListData();

                group.title = groupName.getText().toString();
                group.description = groupDescription.getText().toString();
                group.hostName = hostName.getText().toString();

                Log.d("adding to user list", user1.getUid());

                allGroups.title = groupName.getText().toString();


                int request_permission = 1;
                GPSLocator x = new GPSLocator(getApplicationContext());
                Location l = x.getLocation();
                if (l == null) {
                    ActivityCompat.requestPermissions(CreateGroupActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            request_permission);
                }

//
                group.latitude = String.valueOf(l.getLatitude());
                group.longitude = String.valueOf(l.getLongitude());
                allGroups.latitude = String.valueOf(l.getLatitude());
                allGroups.longitude = String.valueOf(l.getLongitude());

                user1.addGroup(group);
                GroupsList.add(group);

                Log.d("GroupList: ", user1.groupList.toString());

                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(current_uid).child("user_groups").child("host_groups").child(group.title).setValue(group);
                mDatabase.child("total_groups").child(allGroups.title).setValue(group);

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
