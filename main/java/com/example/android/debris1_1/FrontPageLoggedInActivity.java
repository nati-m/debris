package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;

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

        Button TEMPtoHTTPTest = findViewById(R.id.TEMPBUTTON);
        TEMPtoHTTPTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPageIntent = new Intent(FrontPageLoggedInActivity.this, HTTPTestActivity.class);
                startActivity(nextPageIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_log_out:
                AuthUI.getInstance().signOut(this);
                //Signs out of Firebase
                Control.CONTROL.setCurrentUser(new PublicUser());
                //Sets current user in CONTROL to a blank user
                Intent nextPageIntent = new Intent(FrontPageLoggedInActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
