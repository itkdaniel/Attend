package michael.attend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewGroupsActivity extends AppCompatActivity{

//    User user;
//    ArrayList<String> groupNames = new ArrayList<String>();
//    String groupName;
    ListView group_list;
    User user1 = LoginActivity.user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);

        ListView group_list = findViewById(R.id.group_list);

        if(LoginActivity.user1.groupList != null){
            ListAdapter eventAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, user1.groupList);
            group_list.setAdapter(eventAdapter);
        }else{
            Log.d("Viewgroups GroupList: ", user1.groupList.toString());
        }

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

    protected void onResume(){
        super.onResume();
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
