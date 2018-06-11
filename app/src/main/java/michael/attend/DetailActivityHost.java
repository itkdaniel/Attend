package michael.attend;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailActivityHost extends AppCompatActivity {

    Context context;
    int position;
    Intent i;
    String current_uid;
    ListView listView;
    TextView no_students;
    Button takeAttendance;
    String[] names;
    String[] emails;
    ArrayList<User> users;
    DatabaseReference dbr;
    String title,description, host;
    CustomListAdapter adapter;
    TextView event_title, event_description, event_host;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_host);
        current_uid = LoginHome.user_uid;

        listView = (ListView) findViewById(R.id.attendees);
        no_students = (TextView) findViewById(R.id.no_students);

        i = getIntent();
        title = i.getStringExtra("title");
        description = i.getStringExtra("description");
        host = i.getStringExtra("host");
        position = i.getIntExtra("position",0);
        Log.d("user_in_group_name", title);


        takeAttendance = findViewById(R.id.take_attendance);
//        context = this;
        // this records the host's coordinates and sets takeAttendance  to valid
        takeAttendance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // compare coordinates
            }
        });

        event_title = (TextView) findViewById(R.id.group_name);
        event_description = (TextView) findViewById(R.id.group_description);
        event_host = (TextView) findViewById(R.id.host_name);


        // populating listview with students



        dbr = FirebaseDatabase.getInstance().getReference().child("total_groups").child(title).child("Users");
        users = new ArrayList<User>();
        Query query = dbr;

         Log.d("user_in_group_name", dbr.child(current_uid).toString());


         query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                  if(dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    users.add(snapshot.getValue(User.class));
//                    Log.d("user_in_group_name", "inside ondatachange");

                            User user = snapshot.getValue(User.class);
//                    Log.d("user_in_group_name", user.toString());
                            users.add(user);
//                    Log.d("user_in_group_name", user.name);
                            names = new String[users.size()];
                            emails = new String[users.size()];
                        }


                        for (int q = 0; q < users.size(); q++) {
                            names[q] = users.get(q).name;
                            emails[q] = users.get(q).email;

                            Log.d("user_in_group_name", "for loop log: " + "name: " + names[q] + " email: "
                                    + emails[q] + q + " size:" + names.length);
                        }
                      try {
                          event_title.setText(title);
                          event_description.setText(description);
                          event_host.setText(host);

                          adapter = new CustomListAdapter(DetailActivityHost.this, names, emails);
                          listView.setAdapter(adapter);

                          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                              @Override
                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                  if (position > -1) {
                                      Toast.makeText(getApplicationContext(), names[position] + " - " + emails[position], Toast.LENGTH_LONG).show();
                                  }
                              }
                          });
                      }
                      catch (NullPointerException e) {

                          no_students.setVisibility(View.VISIBLE);
                          listView.setVisibility(View.INVISIBLE);
                      }

                    }
                    else {
                        Log.d("user_in", "nothing found");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) { }
            });

//        try {
//            event_title.setText(title);
//            event_description.setText(description);
//            event_host.setText(host);
//
//            adapter = new CustomListAdapter(DetailActivityHost.this, names, emails);
//            listView.setAdapter(adapter);
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    if (position > -1) {
//                        Toast.makeText(getApplicationContext(), names[position] + " - " + emails[position], Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//        }
//        catch (NullPointerException e) {
//
//            no_students.setVisibility(View.VISIBLE);
//            listView.setVisibility(View.INVISIBLE);
//        }



    }
}
