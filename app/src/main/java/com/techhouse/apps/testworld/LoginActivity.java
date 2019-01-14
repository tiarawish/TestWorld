package com.techhouse.apps.testworld;

        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.firebase.ui.auth.AuthUI;
        import com.firebase.ui.auth.ErrorCodes;
        import com.firebase.ui.auth.IdpResponse;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;

        import java.util.Arrays;
        import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button btn_login, button;

    TextView tv_Login;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore mFirestore;

    private static final int RC_SIGN_IN = 7001; // FirebaseUI RC

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login  = findViewById(R.id.LoginAct_btn_login);
        button = findViewById(R.id.button);
        tv_Login = findViewById(R.id.textView);

        mFirestore = FirebaseFirestore.getInstance();

        // Authentication
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
        }
        // Interface
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startLoginProcess();
                startFirebaseUIAuth();
                //startRegisterActivity();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), List.class);
                startActivity(i);
            }
        });

        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStuff();
            }
        });

    }

    private void doStuff() {
        Evento e1 = new Evento("Test", "20.20.2000", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        mFirestore.collection("events").add(e1);
        Evento e2 = new Evento("Dummbumm", "11.11.1111" , "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged");
        mFirestore.collection("events").add(e2);
        Evento e3 = new Evento("Ulle", "14.14.1999", "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.");
        mFirestore.collection("events").add(e3);
    }

    private void startFirebaseUIAuth(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null ) {

        } else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(Arrays.asList(
                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                            new AuthUI.IdpConfig.EmailBuilder().build()
                    )).build(),RC_SIGN_IN);
        }
    }

    // FirebaseUI Result
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // FirebaseUI Result
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                mUser = mAuth.getCurrentUser();

                if (mFirestore.collection("users").document(mUser.getUid()).get() != null) {
                    // User exists
                    Intent i = new Intent(LoginActivity.this, LandingActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    // Uses is new
                    User u = new User(mUser.getDisplayName(), mUser.getEmail());
                    mFirestore.collection("users").document(mUser.getUid()).set(u)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "User added to Database", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(LoginActivity.this, LandingActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else if (task.isComplete()) {
                                        Toast.makeText(LoginActivity.this, R.string.went_wrong, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Terribly wrong...",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(getApplicationContext(),R.string.sign_in_cancelled, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Sign in errored
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(getApplicationContext(),R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(getApplicationContext(), getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Login/Register
    private void startRegisterActivity(){
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }

    private void startLoginProcess() {
        if (mAuth.getCurrentUser() != null) {
            Intent i = new Intent(this, LandingActivity.class);
            startActivity(i);
        } else {

            mAuth.createUserWithEmailAndPassword("david.hornung90@gmail.com", "test1234")
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

}
