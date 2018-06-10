package michael.attend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseGroupActivity extends AppCompatActivity {

    Button buttonHost;
    Button buttonStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_group);

        Button buttonHost = findViewById(R.id.host_button);

        buttonHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseGroupActivity.this, DetailActivityHost.class);
                startActivity(intent);
            }
        });
        Button buttonStudent = findViewById(R.id.student_button);

        buttonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseGroupActivity.this, DetailActivityStudent.class);
                startActivity(intent);
            }
        });
    }
}
