package michael.attend;


import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.*;

import static android.widget.Toast.LENGTH_LONG;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText register_name;
    TextInputEditText register_email;
    TextInputEditText register_password, register_confirm, register_username;
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

        attempt_registration();
//        finish();

    }

    private void attempt_registration(){
        register_name = (TextInputEditText) findViewById(R.id.register_name);
        register_username = (TextInputEditText) findViewById(R.id.register_username);
        register_email = (TextInputEditText) findViewById(R.id.register_email);
        register_confirm = (TextInputEditText) findViewById(R.id.confirm_password);
        register_username = (TextInputEditText) findViewById(R.id.register_username);

        // Reset errors.
        register_name.setError(null);
        register_email.setError(null);
        register_password.setError(null);

        String name = register_name.getText().toString();
        String email = register_email.getText().toString();
        String password = register_password.getText().toString();
        String confirm = register_confirm.getText().toString();
        String username = register_username.getText().toString();

        isInputEmpty(register_name);
        isInputEmpty(register_username);
        isInputEmpty(register_email);
        isInputEmpty(register_password);
        isInputEmpty(register_confirm);

        if(!password.isEmpty() && !confirm.isEmpty()){
            if(isPasswordMatch(password,confirm, register_confirm, "Passwords Do Not Match"));{
                User user = new User(name, username, email, password);
                // SQL DATABASE LOGIC HERE

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
            finish();
            Log.d("TAG", "name : " + name);
            Toast.makeText(this, "Fuck You for Registering " + name, LENGTH_LONG).show();
            return true;
        }
    }

    private boolean isInputEmpty(TextInputEditText editText){
        String text = editText.getText().toString().trim();
        if(text.isEmpty()){
            editText.setError("Input Invalid");
            hideKeyboard(editText);
            Log.d("INPUT VALIDITY", "NOT VALID");
            return true;
        }else{
            Log.d("INPUT VALIDITY", "VALID");
            return false;
        }
    }

//    value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches())     IS EMAIL VALID
    private boolean isEmailValid(TextInputEditText email_editText){
        String email = email_editText.getText().toString().trim();
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_editText.setError("Invalid Email");
            hideKeyboard(email_editText);
            Log.d("EMAIL VALIDITY", "VALID");
            return false;
        }else{
            return true;
        }

    }

    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
