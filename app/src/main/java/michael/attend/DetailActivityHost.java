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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivityHost extends AppCompatActivity {

    Context context;
    int position;
    Intent i;
    String uid;
    ListView listView;
    Button takeAttendance;
    String[] names ;

    String[] emails ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_host);
        uid = LoginHome.user_uid;

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
                int request_permission = 1;
                GPSLocator x = new GPSLocator(getApplicationContext());
                Location l = x.getLocation();
                if (l == null) {
                    ActivityCompat.requestPermissions(DetailActivityHost.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request_permission);


                }
                if (l != null) {
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
//
//
                    //makes attendance valid and record host's coordinates in firebase
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("user_groups").child(String.valueOf(position)).child("location").child("lat");
                    databaseReference.setValue(lat);
                    Toast.makeText(context,"Taking attendance for (" + String.valueOf(position) + ")", Toast.LENGTH_LONG).show();
//
//                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("user_groups").child(String.valueOf(position)).child("location").child("lon");
//                    databaseReference1.setValue(lon);
                }
            }
        });



        TextView event_title = (TextView) findViewById(R.id.group_name);
        TextView event_description = (TextView) findViewById(R.id.group_description);
        TextView event_host = (TextView) findViewById(R.id.host_name);

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
