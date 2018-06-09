package michael.attend;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JoinGroupActivity extends AppCompatActivity {

    TextInputEditText groupName;
    String current_uid;
    User current_user;
    DatabaseReference databaseRef;
    ArrayList<User> userList;
    User credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        if (current_uid == null) {
            current_uid = LoginHome.user_uid;
            Log.d("user_uid", current_uid);
        }
        Log.d("current_uid", current_uid);

    }

    public void onClick_JoinButton(View view) {
        current_user = LoginActivity.user1;
        groupName = findViewById(R.id.group_name);
        userList = new ArrayList<User>();
        credentials = new User();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("total_groups").child(groupName.getText().toString()).child("0").child("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        User listdata = postSnapshot.getValue(User.class);
                        Log.d("ondatachange_listdata", listdata.toString());

                        userList.add(listdata);
                        if(userList != null){
                            Log.d("ondatachange_usergroups", "groupslist not null !");

                        }else{
                            Log.d("ondatachange_usergroups", "groupslist null >:[");

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                credentials.name = current_user.getName();
                credentials.username = current_user.getEmail();
                userList.add(credentials);
                for (int i = 0; i < userList.size(); i++){
                    Log.d("users_in_group", userList.get(i).name);

                }

                databaseRef = FirebaseDatabase.getInstance().getReference();
                databaseRef.child("total_groups").child(groupName.getText().toString()).child("0").child("Users").setValue(userList);



                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        mDatabase.child("total_groups").child("wadu").setValue(userList);
    }
}

/*

totalGroups

        group 1:
        x | x@x.com
            y | y@y.com
            z | z@z.com

    group 2:
            x | x@x.com
            y | y@y.com
            z | z@z.com



Every time we make a group, we use group ID as group name (group 1) and fill it with user objects where
        - we can get user name
        - we can get user ID

        We only fill objects when users have "joined" a group ID

        This is done by searching totalGroups for a group ID, if there is a match, we will add user object into that class


*/