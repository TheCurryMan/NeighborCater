package hackthenorth.neighborcater;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import hackthenorth.neighborcater.models.Kitchen;

public class CreateLoginActivity extends AppCompatActivity {


    EditText mEmailInput;
    EditText mPassword;
    Button mCreateButton;
    Button mLoginButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("P", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("P", "onAuthStateChanged:signed_out");
                }
            }
        };

        mEmailInput = (EditText) findViewById(R.id.editText);
        mPassword = (EditText) findViewById(R.id.editText2);
        mCreateButton = (Button) findViewById(R.id.button);
        mLoginButton = (Button) findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();


        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mEmailInput.getText().toString();
                String password = mPassword.getText().toString();

                mAuth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(CreateLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("P", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                if (!task.isSuccessful()) {
                                    Toast.makeText(CreateLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(CreateLoginActivity.this, "Created Account", Toast.LENGTH_SHORT).show();
                                    Intent choosingActivity = new Intent(CreateLoginActivity.this, ChoosingActivity.class);
                                    startActivity(choosingActivity);
                                }

                            }
                        });
            }
        });


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mEmailInput.getText().toString();
                String password = mPassword.getText().toString();
                mDatabase = FirebaseDatabase.getInstance().getReference("kitchens");


                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(CreateLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("P", "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w("P", "signInWithEmail:failed", task.getException());
                                    Toast.makeText(CreateLoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                                }else{
                                    Toast.makeText(getApplicationContext(), "Authenticated!", Toast.LENGTH_SHORT).show();
                                    Intent choosingActivity = new Intent(CreateLoginActivity.this, ChoosingActivity.class);
                                    startActivity(choosingActivity);
                                    Toast.makeText(CreateLoginActivity.this, "Authenticated!", Toast.LENGTH_SHORT).show();
//
//
                                    Intent choosingIntent = new Intent(CreateLoginActivity.this, ChoosingActivity.class);
                                    startActivity(choosingIntent);

                                }

                            }
                        });
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
