package michael.attend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewGroupsActivity extends AppCompatActivity{

//    User user;
//    ArrayList<String> groupNames = new ArrayList<String>();
//    String groupName;
    ListView group_list;
    User user1 = LoginActivity.user1;
    String[] listItems;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String current_uid;
    private DatabaseReference mUserReference;
    private ArrayList<User> userList;
    ArrayList<ListData> groups;
    DatabaseReference groupsRef;
    FirebaseDatabase database;
    ListData groups_list;
    Context mContext;
    User current_user;
    String uid;
    ArrayList<ListData> GroupsList;





    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);
        group_list = findViewById(R.id.group_list);

        auth = FirebaseAuth.getInstance();
        uid = LoginHome.user_uid;
        database = FirebaseDatabase.getInstance();
        groupsRef = database.getReference("users");

        userList = new ArrayList<User>();
        groups = new ArrayList<ListData>();
//        auth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        current_uid = auth.getCurrentUser().getUid();
//
//        mUserReference = FirebaseDatabase.getInstance().getReference().child(current_uid);


        GroupsList=new ArrayList<ListData>();
        mContext = this;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("user_groups");
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
                Log.d("GroupList_size", String.valueOf(GroupsList.size()));


                if(GroupsList != null){
                    listItems = new String[GroupsList.size()];
                    Log.d("GroupList_size onCreate", String.valueOf(GroupsList.size()));

                }
                Log.d("listitems_size", String.valueOf(listItems.length));

                if(GroupsList != null){
                    for(int i = 0; i < GroupsList.size(); i++){
                        listItems[i] = GroupsList.get(i).title;
                    }
//            ListAdapter eventAdapter = new ArrayAdapter<ListData>(this,android.R.layout.simple_list_item_1, user1.groupList);
                    ListAdapter eventAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, listItems);
                    group_list.setAdapter(eventAdapter);
                }else{
                    Log.d("Viewgroups GroupList: ", user1.groupList.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ondata_error", "Error: ", databaseError.toException());
            }
        });


//        Log.d("GroupList_size", String.valueOf(GroupsList.size()));
//
//
//        if(GroupsList != null){
//            listItems = new String[GroupsList.size()];
//            Log.d("GroupList_size onCreate", String.valueOf(GroupsList.size()));
//
//        }
//        Log.d("listitems_size", String.valueOf(listItems.length));
//
//        if(GroupsList != null){
//            for(int i = 0; i < GroupsList.size(); i++){
//                listItems[i] = GroupsList.get(i).title;
//            }
////            ListAdapter eventAdapter = new ArrayAdapter<ListData>(this,android.R.layout.simple_list_item_1, user1.groupList);
//            ListAdapter eventAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItems);
//            group_list.setAdapter(eventAdapter);
//        }else{
//            Log.d("Viewgroups GroupList: ", user1.groupList.toString());
//        }




//        Intent i;
//        if((i=getIntent()) == null){
//            ListAdapter eventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groupNames);
//            group_list.setAdapter(eventAdapter);
//
//        }else{
//            i = getIntent();
//            groupName = i.getStringExtra("groupName");
//            groupNames.add(groupName);
//            ListAdapter eventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groupNames);
//            group_list.setAdapter(eventAdapter);
//        }

//        String[] classes = {"CMPS121", "CMPS102", "CMPS143", "CMPS140", "AMS131", "CMPS111", "CMPS112", "CMPS115", "CMPS184"};


    }

//    public User currentUser(){
//        return SignupActivity.user1;
//    }

    @Override
//    public void onStart(){
//        super.onStart();
//
//        ValueEventListener userListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User current_user = dataSnapshot.getValue(User.class);
//                groupList = current_user.groupList;
//                Log.d("")
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//    }

    protected void onResume(){
        super.onResume();
        ListView group_list = findViewById(R.id.group_list);

        mContext = this;
        uid = LoginHome.user_uid;

//        GroupsList=new ArrayList<ListData>();
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("user_groups");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
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
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("ondata_error", "Error: ", databaseError.toException());
//            }
//        });

//        if(user1.groupList != null){
//            listItems = new String[user1.groupList.size()];
//
//        }
//        if(LoginActivity.user1.groupList != null){
//            Log.d("grouplist_not_null", "not null !");
//            Log.d("listsize", String.valueOf(LoginActivity.user1.groupList.size()));
//            for(int i = 0; i < user1.groupList.size(); i++){
//                listItems[i] = user1.groupList.get(i).title;
//                Log.d("ListItem", listItems[i]);
//            }
////            ListAdapter eventAdapter = new ArrayAdapter<ListData>(this,android.R.layout.simple_list_item_1, user1.groupList);
//            ListAdapter eventAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItems);
//            group_list.setAdapter(eventAdapter);
//        }else{
//            Log.d("grouplist_null", "null :((");
//            Log.d("Viewgroups GroupList: ", user1.groupList.toString());
//        }
//        if(GroupsList != null){
//            listItems = new String[GroupsList.size()];
//            Log.d("GroupList_size onResume", String.valueOf(GroupsList.size()));
//            for(int i = 0; i < GroupsList.size(); i++){
//                listItems[i] = GroupsList.get(i).title;
//                Log.d("ListItem", listItems[i]);
//            }
//            ListAdapter eventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
//            group_list.setAdapter(eventAdapter);
//        }else{
//            Log.d("GroupList_status", "Empty");
//            Log.d("GroupList_size", String.valueOf(GroupsList.size()));
//
//        }

//        groupsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()){
//                    User user = userSnapshot.getValue(User.class);
//                    userList.add(user);
//                    Log.d("ondatachange_key", userSnapshot.getKey());
//
//                    Log.d("ondatachange", userList.get(0).getName());
//
//                    if(userSnapshot.getKey().equals(uid)){
//                        current_user = user;
//                        groups = current_user.groupList;
//                        Log.d("onDataChange_set_user", uid);
//                        Log.d("datachange_set_user" ,"name " + current_user.getName());
////                        Log.d("ondatachange_group", user.getGroupList().toString());
//                        if(current_user.groupList != null){
//                            Log.d("ondatachange_group", "grouplist not null!");
//
//                        }else{
//                            Log.d("ondatachange_group", "grouplist null.... >:(");
//
//                        }
//
//                        break;
//                    }
////                            userList.add(user);
//                }
////                        groups = current_user.getGroupList();
////                        for(ListData group : current_user.getGroupList()){
////                            groups.add(group);
////                        }
////                        Toast.makeText(mContext, "ondatachange_group " + current_user.getGroupList().get(0).title, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        group_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("onDataChange_itemclick", uid);
//                Log.d("user" ,"name " + current_user.getName());

//                final int group_position = position;
//                groupsRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()){
//                            User user = userSnapshot.getValue(User.class);
//                            if(user.getName() == uid){
//                                current_user = user;
//                                Log.d("onDataChange", uid);
//                                Log.d("user" ,"name " + current_user.getName());
//
//                                break;
//                            }
////                            userList.add(user);
//                        }
////                        groups = current_user.getGroupList();
////                        for(ListData group : current_user.getGroupList()){
////                            groups.add(group);
////                        }
////                        Toast.makeText(mContext, "group " + groups.get(0).title, Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
            }
        });
//        ListView group_list = findViewById(R.id.group_list);
//        TextView no_groups = findViewById(R.id.no_groups);

//        no_groups.setVisibility(View.VISIBLE);
//        group_list.setVisibility(View.VISIBLE);
//        if(user1.groupList != null){
//            ListAdapter eventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, user1.groupList);
//            group_list.setAdapter(eventAdapter);
//        }else{
//            Log.d("Viewgroups GroupList: ", user1.groupList.toString());
//
//        }

    }

    public void onBackPressed(){
//        startActivity(new Intent(ViewGroupsActivity.this, LoginHome.class));
        finish();
    }
}
