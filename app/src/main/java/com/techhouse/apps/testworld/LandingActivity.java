package com.techhouse.apps.testworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LandingActivity extends AppCompatActivity {
    TextView tv_intro;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        tv_intro = findViewById(R.id.LandingAct_tv_intro);

        // Authentication
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            tv_intro.setText("No User logged in...");
        } else {
            mUser = mAuth.getCurrentUser();
            tv_intro.setText("User: " + mUser.getDisplayName());
        }
    }
}
