package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.android.debris1_1.Control.CONTROL;

public class FrontPageLoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page_logged_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView welcomeUserName = (TextView) findViewById(R.id.welcome_user_name_front_page_logged_in);
        welcomeUserName.setText("Welcome, " + CONTROL.getCurrentUser().getName() + ".");

        Button toHireSection = (Button) findViewById(R.id.hire_button_front_page);
        toHireSection.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                Intent nextPageIntent = new Intent(FrontPageLoggedInActivity.this, HireHomePageActivity.class);
                startActivity(nextPageIntent);
            }
        });

    }

}
