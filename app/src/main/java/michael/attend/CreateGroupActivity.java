package michael.attend;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class CreateGroupActivity extends AppCompatActivity {

    final Context context = this;
    TextInputEditText groupName;
    TextInputEditText groupDescription;
    TextInputEditText hostName;
    User user1 = LoginActivity.user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
    }

    public void onBackPressed(){
        finish();
    }

    public void onclick_creategroup(View view) {
        groupName = findViewById(R.id.group_name);
        groupDescription = findViewById(R.id.group_description);
        hostName = findViewById(R.id.host_name);
//        ArrayList<String> groupNames = new ArrayList<String>();
//        groupNames.add(groupName.getText().toString());
        user1.addGroup(groupName.getText().toString());
        Log.d("GroupList: ", user1.groupList.toString());
        finish();

//        Intent viewGroupsIntent = new Intent(context, ViewGroupsActivity.class);
//        viewGroupsIntent.putExtra("groupName", groupName.getText().toString());
//        startActivity(viewGroupsIntent);
    }
}
