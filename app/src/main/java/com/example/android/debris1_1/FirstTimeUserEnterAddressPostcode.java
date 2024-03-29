package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.debris1_1.CompanyUserActivities.CompanyHomePageLoggedInActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirstTimeUserEnterAddressPostcode extends AppCompatActivity {

    Button GO;
    EditText enterPostcode;
    EditText enterAddressLine1;
    EditText enterAddressLine2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_user_enter_address_postcode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GO = findViewById(R.id.go_button_first_time_user_enter_address_postcode);
        enterPostcode = findViewById(R.id.enter_postcode_first_time_user_enter_address_postcode);
        enterAddressLine1 = findViewById(R.id.enter_address_line_1_first_time_user_enter_address_postcode);
        enterAddressLine2 = findViewById(R.id.enter_address_line_2_first_time_user_enter_address_postcode);


        GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptPostcodeEntry();
            }
        });

    }

    private void attemptPostcodeEntry(){
        enterPostcode.setError(null);
        enterAddressLine1.setError(null);
        String userEnteredPostcode = enterPostcode.getText().toString();
        String userEnteredAddress1 = enterAddressLine1.getText().toString();
        String userEnteredAddress2 = enterAddressLine2.getText().toString();

        //TODO Replace with postcode checker from that website
        if(userEnteredPostcode.length() < 5 || userEnteredPostcode.length() > 8){
            enterPostcode.setError("Please enter a valid postcode");
            return;
        }

        if(userEnteredAddress1.isEmpty()){
            enterAddressLine1.setError("Please enter an address of at least one line");
            return;
        }

        if(userEnteredAddress1.length() < 5){
            enterAddressLine1.setError("This doesn't look long enough. Try again!");
            return;
        }



        //if the address and postcode are valid...

        //CHANGE THE FORMAT OF POSTCODE TO UPPER CASE AND WITH A SPACE IN THE MIDDLE
        userEnteredPostcode = Control.CONTROL.formatPostcode(userEnteredPostcode);

        //Updates the user's information both in the currentUser object in CONTROL and in the Firebase Database.
        //Crucially, it sets firebaseDataFilledOut to true, meaning this page will not be visited again.
        Control.CONTROL.getCurrentUser().setPostCode(userEnteredPostcode);
        Control.CONTROL.getCurrentUser().setAddressLine1(userEnteredAddress1);
        Control.CONTROL.getCurrentUser().setAddressLine2(userEnteredAddress2);
        Control.CONTROL.getCurrentUser().setFirebaseDatabaseFilledOut(true);


        String uid = Control.CONTROL.getCurrentUser().getFirebaseUid();
        DatabaseReference thisUsersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        //And now to update the Firebase Database with this user's new postcode and firebaseDataFilledOut set to true
        thisUsersDatabaseReference.setValue(Control.CONTROL.getCurrentUser());

        //If the current user is a company user, send them to company home page, if not send them to public user home page
        if(!Control.CONTROL.getCurrentUser().getIsCompanyUser()) {
            Intent nextPageIntent = new Intent(FirstTimeUserEnterAddressPostcode.this, FrontPageLoggedInActivity.class);
            startActivity(nextPageIntent);
        } else {
            Intent nextPageIntent = new Intent(FirstTimeUserEnterAddressPostcode.this, CompanyHomePageLoggedInActivity.class);
            startActivity(nextPageIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_log_out:
                AuthUI.getInstance().signOut(this);
                //Signs out of Firebase
                Control.CONTROL.setCurrentUser(new PublicUser());
                //Sets current user in CONTROL to a blank user
                Intent nextPageIntent = new Intent(FirstTimeUserEnterAddressPostcode.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            case R.id.menu_action_user_settings:
                Intent settingsIntent = new Intent(FirstTimeUserEnterAddressPostcode.this, UserSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
