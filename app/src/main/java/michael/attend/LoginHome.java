package michael.attend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginHome extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.user_menu, menu);
//        return true;
//    }

    public void on_addGroup(View view) {

        Toast.makeText(this, "Fuck you for creating a group bitch", Toast.LENGTH_LONG).show();

    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.menu_profile){
            Intent intent = new Intent(LoginHome.this, ProfileActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void onclick_viewgroups(View view) {
        startActivity(new Intent(LoginHome.this, ViewGroupsActivity.class));
    }

    public void create_group(View view) {
        startActivity(new Intent(LoginHome.this, CreateGroupActivity.class));
    }

    public void onStop(){
        super.onStop();
        if (auth.getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();

        }
    }
}
