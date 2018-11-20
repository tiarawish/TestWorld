package com.techhouse.apps.testworld;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    // Interface
    EditText et_firstname, et_lastname, et_email, et_pass1, et_pass2;
    Button btn_regNow;

    // Firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Interface
        et_firstname = findViewById(R.id.RegisterAct_et_firstname);
        et_lastname = findViewById(R.id.RegisterAct_et_lastname);
        et_email = findViewById(R.id.RegisterAct_et_email);
        et_pass1 = findViewById(R.id.RegisterAct_et_pass1);
        et_pass2 = findViewById(R.id.RegisterAct_et_pass2);

        btn_regNow = findViewById(R.id.RegisterAct_btn_register);
        btn_regNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    }

    private void startRegister() {
        String sFirstname, sLastname, sEmail, sPass1, sPass2;

        sFirstname = et_firstname.getText().toString();
        sLastname = et_lastname.getText().toString();
        sEmail = et_email.getText().toString();
        sPass1 = et_pass1.getText().toString();
        sPass2 = et_pass2.getText().toString();

        if (sPass1.equals(sPass2)) {
            Toast.makeText(getApplicationContext(), getString(R.string.fui_password_no_match), Toast.LENGTH_LONG).show();
            return;
        }

        if (!checkValidEmail(sEmail)){
            Toast.makeText(getApplicationContext(), getString(R.string.fui_email_not_valid), Toast.LENGTH_LONG).show();
            return;
        }
        
        mAuth.createUserWithEmailAndPassword(sEmail,sPass1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   //Toast.makeText(RegisterActivity.this, "Success. User created.", Toast.LENGTH_SHORT).show();
                }
        });
    }

    private boolean checkValidEmail(String sEmail) {
        if (sEmail.contains("@") & sEmail.contains(".")){
            return true;
        }
        return false;
    }
}
