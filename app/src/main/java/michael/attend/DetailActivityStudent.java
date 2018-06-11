package michael.attend;

import android.content.Context;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailActivityStudent extends AppCompatActivity {

    Context context;
    int position;
    Intent i;
    String current_uid;
    ListView listView;
    Button takeAttendance;
    Context mContext;
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

        current_uid = LoginHome.user_uid;
        i = getIntent();

        title = i.getStringExtra("title");
        String description = i.getStringExtra("description");
        String host = i.getStringExtra("host");
        position = i.getIntExtra("position", 0);

        takeAttendance = findViewById(R.id.student_take_attendance);
        context = this;
        studentLog = new ArrayList<ListData>();


        takeAttendance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a");
                time = timeFormat.format(calendar.getTime());

                //date
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                date = dateFormat.format(calendar.getTime());


                ListData logIn = new ListData();
                logIn.sessionTime = time;
                logIn.sessionDate = date;

                studentLog.add(logIn);

                mDataBase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid).
                        child("user_groups").child("student_groups").child(title).child("Log").child(String.valueOf(position));
                mDataBase.setValue(logIn);




            }
        });

        TextView event_title = (TextView) findViewById(R.id.student_group_name);
        TextView event_description = (TextView) findViewById(R.id.student_group_description);
        TextView event_host = (TextView) findViewById(R.id.student_host_name);

        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid)
                .child("user_groups").child("student_groups").child(title).child("Log").child(String.valueOf(position));

//        db1.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    ListData ld = snapshot.getValue(ListData.class);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

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
