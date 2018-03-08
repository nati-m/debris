package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class FrontPageNotSignedIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page_not_signed_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton signInFAB = (FloatingActionButton) findViewById(R.id.sign_in_fab);
        signInFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                Intent nextPageIntent = new Intent(FrontPageNotSignedIn.this, LoginActivity.class);
                startActivity(nextPageIntent);
            }
        });

        FloatingActionButton signUpFAB = (FloatingActionButton) findViewById(R.id.sign_up_fab);
        signUpFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                Intent nextPageIntent = new Intent(FrontPageNotSignedIn.this, SignUpActivity.class);
                startActivity(nextPageIntent);
            }
        });
    }

}
