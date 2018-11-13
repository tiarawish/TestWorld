package com.techhouse.apps.testworld;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.firebase.ui.auth.AuthUI;
        import com.firebase.ui.auth.ErrorCodes;
        import com.firebase.ui.auth.IdpResponse;
        import com.google.firebase.auth.FirebaseAuth;

        import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    Button btn_login = findViewById(R.id.LoginAct_btn_login);

    FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 7001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Authentication
        mAuth = FirebaseAuth.getInstance();

        // Interface
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginProcess();
            }
        });
    }

    private void startMainActivity() {
    }

    private void startLoginProcess() {
        if (mAuth.getCurrentUser() != null) {
            Intent i = new Intent(this, LandingActivity.class);
            startActivity(i);
        } else {
            // Start FirebaseUI
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build()))
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // FirebaseUI Result
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                Intent i = new Intent(this, LandingActivity.class);
                startActivity(i);
                finish();
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
            }
        }
    }
}
