package michael.attend;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @SuppressLint("WrongConstant")
    public void onRegistration_register(View view) {
        Toast.makeText(this, "Fuck You for Registering!", LENGTH_LONG).show();
        finish();

    }
}
