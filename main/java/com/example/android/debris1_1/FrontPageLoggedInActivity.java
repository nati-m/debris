package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.android.debris1_1.Control.CONTROL;

public class FrontPageLoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page_logged_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView welcomeUserName = findViewById(R.id.welcome_user_name_front_page_logged_in);

        welcomeUserName.setText("Welcome, " + CONTROL.getCurrentUser().getName());

        Button toHireSection = findViewById(R.id.hire_button_front_page);
        toHireSection.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                Intent nextPageIntent = new Intent(FrontPageLoggedInActivity.this, HireHomePageActivity.class);
                startActivity(nextPageIntent);
            }
        });

        Button toOrderSection = findViewById(R.id.track_button_front_page);
        toOrderSection.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {

                //create a new Order object with this Address, Postcode, Date, and ArrayList of Skips ordered
                ArrayList<Skip> skipsOrdered = new ArrayList<>();
                skipsOrdered.add(Skip.MIDI_SKIP);
                //skipsOrdered.add(Skip.MIDI_SKIP);
                Calendar c = Calendar.getInstance(); c.add(Calendar.MONTH, 1);
                Order currentOrder = new Order(Control.CONTROL.getCurrentUser(), "9 The Street", "", "NE4 2ER", skipsOrdered, c);

                //sets this to the current order the app is focused on in CONTROL
                Control.CONTROL.setCurrentOrder(currentOrder);

                Intent nextPageIntent = new Intent(FrontPageLoggedInActivity.this, TrackOrdersActivity.class);
                startActivity(nextPageIntent);
            }
        });

        Button TEMPtoCompanyRecyclerView = findViewById(R.id.TEMPBUTTON);
        TEMPtoCompanyRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPageIntent = new Intent(FrontPageLoggedInActivity.this, ThrowAwayCompanyListTest.class);
                startActivity(nextPageIntent);
            }
        });
    }

}
