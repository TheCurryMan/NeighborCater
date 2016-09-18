package hackthenorth.neighborcater;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by rowandempster on 9/18/16.
 */

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent chooserIntent = new Intent(LauncherActivity.this, ChoosingActivity.class);
            startActivity(chooserIntent);

            // User is signed in
        } else {
            Intent loginIntent = new Intent(LauncherActivity.this, CreateUserActivity.class);
            startActivity(loginIntent);
            // No user is signed in
        }
    }
}
