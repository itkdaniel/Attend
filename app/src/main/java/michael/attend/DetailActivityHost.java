package michael.attend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivityHost extends AppCompatActivity {

    ListView listView;

    String[] names = {
            "Hung Lo", "LetsGo",
            "TestGuy", "NewGuy"
    };

    String[] emails = {
            "hungLo@test.com", "LetsGo@test.com",
            "testGuy@test.com", "NewGuy@test.com"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_host);

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String description = i.getStringExtra("description");
        String host = i.getStringExtra("host");

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
