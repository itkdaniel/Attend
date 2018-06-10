package michael.attend;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivityHost extends AppCompatActivity {

    Context context;
    int position;
    Intent i;
    String current_uid;
    ListView listView;
    Button takeAttendance;
    String[] names = {};
    String[] emails = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_host);
        current_uid = LoginHome.user_uid;

        i = getIntent();
        String title = i.getStringExtra("title");
        String description = i.getStringExtra("description");
        String host = i.getStringExtra("host");
        position = i.getIntExtra("position",0);

        takeAttendance = findViewById(R.id.take_attendance);
        context = this;
        // this records the host's coordinates and sets takeAttendance  to valid
        takeAttendance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // compare coordinates
            }
        });

        TextView event_title = (TextView) findViewById(R.id.group_name);
        TextView event_description = (TextView) findViewById(R.id.group_description);
        TextView event_host = (TextView) findViewById(R.id.host_name);

        DatabaseReference dbf = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid)
                .child("user_groups").child("host_groups");

        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        event_title.setText(title);
        event_description.setText(description);
        event_host.setText(host);

        CustomListAdapter adapter = new CustomListAdapter(this, names, emails);
        listView = (ListView) findViewById(R.id.attendees);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position > -1){
                    Toast.makeText(getApplicationContext(), names[position] + " - " + emails[position], Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
