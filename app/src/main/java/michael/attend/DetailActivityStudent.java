package michael.attend;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Math.atan2;

public class DetailActivityStudent extends AppCompatActivity {

    Context context;
    int position;
    Intent i;
    String current_uid;
    ListView listView;
    Button recordAttendance;
    Context mContext = this;
    DatabaseReference mDataBase;
    String title;
    String time;
    String date;
    ArrayList<ListData> studentLog;
    ArrayList<ListData> displayLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_student);

        final Context c = this.context;
        current_uid = LoginHome.user_uid;
        i = getIntent();

        title = i.getStringExtra("title");
        String description = i.getStringExtra("description");
        String host = i.getStringExtra("host");
        position = i.getIntExtra("position", 0);

        recordAttendance = findViewById(R.id.student_take_attendance);
        studentLog = new ArrayList<ListData>();


        recordAttendance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("total_groups").child(title);

                dbr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ListData ld = new ListData();
                        ld = dataSnapshot.getValue(ListData.class);


                        // fetches student's current location
                        int request_permission = 1;
                        GPSLocator x = new GPSLocator(getApplicationContext());
                        Location l = x.getLocation();
                        if (l == null) {
                            ActivityCompat.requestPermissions(DetailActivityStudent.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    request_permission);
                        }

//
                        double studentLatitude = l.getLatitude();
                        double studentLongitude = l.getLongitude();
                        double groupLatitude = Double.valueOf(ld.latitude);
                        double groupLongitude = Double.valueOf(ld.longitude);

                        //distance formula to convert coordinates to miles
                        double dlat = studentLatitude - groupLatitude;
                        double dlon = studentLongitude - groupLongitude;

                        double a = Math.pow(Math.sin(dlat/2),2) + Math.cos(groupLatitude) * Math.cos(studentLatitude) * Math.pow(Math.sin(dlon/2),2);
                        double c = 2 * atan2( Math.sqrt(a), Math.sqrt(1-a) );
                        double d =  c * 3959;

                        Log.d("Nithy", "distance in miles: " + d);
                        if(ld.inSession && d < 1 ) {
                            //time
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a");
                            time = timeFormat.format(calendar.getTime());

                            //date
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                            date = dateFormat.format(calendar.getTime());

                            Toast.makeText(mContext,"Your attendance was recorded successfully", Toast.LENGTH_LONG).show();
                            Event event = new Event();
                            event.time = time;
                            event.date = date;

                            int i = Integer.valueOf(ld.numEvents);
                            i--;
                            String s = Integer.toString(i);

                            DatabaseReference dbr2 = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid).
                                    child("user_groups").child("student_groups").child(title).child("History").child(s);
                            dbr2.setValue(event);



//                            studentLog.add(logIn);
                        }
                        else
                            Toast.makeText(mContext,"Class is not in session", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


//                mDataBase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid).
//                        child("user_groups").child("student_groups").child(title).child("history").child();
//                mDataBase.setValue(logIn);




            }
        });

        TextView event_title = (TextView) findViewById(R.id.student_group_name);
        TextView event_description = (TextView) findViewById(R.id.student_group_description);
        TextView event_host = (TextView) findViewById(R.id.student_host_name);





        event_title.setText(title);
        event_description.setText(description);
        event_host.setText(host);

//        CustomListAdapter adapter = new CustomListAdapter(this, names, emails);
//        listView = (ListView) findViewById(R.id.student_attendees);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position > -1) {
//                    Toast.makeText(getApplicationContext(), names[position] + " - " + emails[position], Toast.LENGTH_LONG).show();
//                }
//            }
//        });

    }
}
