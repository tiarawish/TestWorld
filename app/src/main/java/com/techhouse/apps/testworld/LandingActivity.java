package com.techhouse.apps.testworld;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class LandingActivity extends AppCompatActivity {
    TextView tv_intro;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    FirebaseFirestore mFirestore;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        listView = findViewById(R.id.ListView);

        mFirestore = FirebaseFirestore.getInstance();

        // Authentication
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            tv_intro.setText(getString(R.string.NoUserLogin));
        } else {
            mUser = mAuth.getCurrentUser();
            tv_intro.setText(getString(R.string.UserFlag) + mUser.getDisplayName());
        }
    }
}
