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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by rowandempster on 9/18/16.
 */

public class CreateUserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;

    EditText nameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText phoneEditText;
    Button signUpButton;
    Button iHaveAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user_layout);

        nameEditText = (EditText) this.findViewById(R.id.create_user_name_edit_text);
        emailEditText = (EditText) this.findViewById(R.id.create_user_email_edit_text);
        passwordEditText = (EditText) this.findViewById(R.id.create_user_password_edit_text);
        phoneEditText = (EditText) this.findViewById(R.id.create_user_number_edit_text);
        signUpButton = (Button) this.findViewById(R.id.create_user_create_account_button);
        iHaveAccountButton = (Button) this.findViewById(R.id.create_user_i_have_account_button);

        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("users");

        iHaveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInIntent = new Intent(CreateUserActivity.this, CreateLoginActivity.class);
                startActivity(logInIntent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                        .addOnCompleteListener(CreateUserActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("P", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                if (!task.isSuccessful()) {
                                    Toast.makeText(CreateUserActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }else{
                                    HashMap<String, Object> userInfo = new HashMap<String, Object>();
                                    userInfo.put("name", nameEditText.getText().toString());
                                    userInfo.put("number", phoneEditText.getText().toString());
                                    userInfo.put("email", emailEditText.getText().toString());
                                    databaseRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(userInfo);
                                    Toast.makeText(CreateUserActivity.this, "Created Account", Toast.LENGTH_SHORT).show();
                                    Intent choosingActivity = new Intent(CreateUserActivity.this, ChoosingActivity.class);
                                    startActivity(choosingActivity);
                                }

                            }
                        });
            }
        });
    }
}
