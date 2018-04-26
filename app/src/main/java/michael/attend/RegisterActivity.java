package michael.attend;


import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class RegisterActivity extends AppCompatActivity {

//    TextView register_name = (TextView) findViewById(R.id.register_name);
//    TextView register_email = (TextView) findViewById(R.id.register_email);
//    TextView register_password = (TextView) findViewById(R.id.register_password);

    TextInputEditText register_name;
    TextInputEditText register_email;
    TextInputEditText register_password, register_confirm;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        register_name = (TextInputEditText) findViewById(R.id.register_name);
    }

    public void onRegistration_register(View view) {
        register_name = (TextInputEditText) findViewById(R.id.register_name);
        register_email = (TextInputEditText) findViewById(R.id.register_password);
        register_password = (TextInputEditText) findViewById(R.id.register_password);
        register_confirm = (TextInputEditText) findViewById(R.id.confirm_password);

        String name = register_name.getText().toString();
        String email = register_email.getText().toString();
        String password = register_password.getText().toString();
        String confirm = register_confirm.getText().toString();


//        Log.d("TAG", "name : " + name);
//        Toast.makeText(this, "Fuck You for Registering " + name, LENGTH_LONG).show();
        attempt_registration();
//        finish();

    }

    private void attempt_registration(){
        register_confirm = (TextInputEditText) findViewById(R.id.confirm_password);

        // Reset errors.
        register_name.setError(null);
        register_email.setError(null);
        register_password.setError(null);

        String name = register_name.getText().toString();
        String email = register_email.getText().toString();
        String password = register_password.getText().toString();
        String confirm = register_confirm.getText().toString();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){

            if(isPasswordMatch(password,confirm, register_confirm, "Passwords Do Not Match"));{
                Log.d("Register Status: ", "Registration Success");

            }

        }


    }

    private boolean isPasswordMatch(String password, String confirm, TextInputEditText confirm_password, String message){
        String name = register_name.getText().toString();
        if (!password.trim().contentEquals(confirm.trim())){
            confirm_password.setError(message);
            hideKeyboard(confirm_password);
            Log.d("Password verification", message);
            return false;
        }else{
//            confirm_password.setErrorEnabled(false);
            finish();
            Log.d("TAG", "name : " + name);
            Toast.makeText(this, "Fuck You for Registering " + name, LENGTH_LONG).show();
            return true;
        }
    }

    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
