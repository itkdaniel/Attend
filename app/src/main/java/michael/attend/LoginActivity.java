package michael.attend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String email, password;
    private EditText inputName, inputEmail, inputPassword;
    public static FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    public static User user1;
    ArrayList<ListData> GroupsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // set the view now
        setContentView(R.layout.activity_login);
        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.save_login);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);



        //Get Firebase auth instanc
//        auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() != null) {
//            user1 = new User(auth.getUid(), email);
//            startActivity(new Intent(LoginActivity.this, LoginHome.class));
//            finish();
//        }

        if(saveLogin == true){
            inputEmail.setText(loginPreferences.getString("email", ""));
            inputPassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }

        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final String name = inputName.getText().toString();
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if(v == btnLogin){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputEmail.getWindowToken(),0);

                    if(saveLoginCheckBox.isChecked()){
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("email", email);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.commit();
                    }else{
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
//                                    ArrayList<String> group_list = new ArrayList<String>();
                                    // TODO: GET GROUP_LIST FROM DATA BASE AND ASSIGN TO USER1'S GROUPLIST WHEN CREATING USER INSTANCE
                                    GroupsList=new ArrayList<ListData>();
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getUid()).child("user_groups");
                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            try {
                                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                    ListData listdata = postSnapshot.getValue(ListData.class);
                                                    Log.d("ondatachange_listdata", listdata.toString());

                                                    GroupsList.add(listdata);
                                                    if(GroupsList != null){
                                                        Log.d("ondatachange_usergroups", "groupslist not null !");

                                                    }else{
                                                        Log.d("ondatachange_usergroups", "groupslist null >:[");

                                                    }
                                                }
                                                for (int i = 0; i < GroupsList.size(); i++){
                                                    Log.d("ondatachange_groups", GroupsList.get(i).title);

                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.d("ondata_error", "Error: ", databaseError.toException());
                                        }
                                    });
                                    // TODO: ==========================================================
                                    user1 = new User(auth.getUid(), email, GroupsList);
//                                    mDatabase.child("users").removeValue();
                                    Intent intent = new Intent(LoginActivity.this, LoginHome.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
