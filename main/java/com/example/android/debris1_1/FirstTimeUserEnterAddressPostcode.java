package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.debris1_1.CompanyUserActivities.CompanyHomePageLoggedInActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirstTimeUserEnterAddressPostcode extends AppCompatActivity {

    Button GO;
    EditText enterPostcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_user_enter_address_postcode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GO = findViewById(R.id.go_button_first_time_user_enter_address_postcode);
        enterPostcode = findViewById(R.id.enter_postcode_first_time_user_enter_address_postcode);

        GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptPostcodeEntry();
            }
        });

    }

    private void attemptPostcodeEntry(){
        enterPostcode.setError(null);
        String userEnteredPostcode = enterPostcode.getText().toString();

        //TODO Replace with postcode checker from that website
        if(userEnteredPostcode.length() < 5 || userEnteredPostcode.length() > 8){
            enterPostcode.setError("Please enter a valid postcode");
            return;
        }
        //if the postcode is valid...

        //CHANGE THE FORMAT OF POSTCODE TO UPPER CASE AND WITH A SPACE IN THE MIDDLE
        userEnteredPostcode = userEnteredPostcode.toUpperCase(); //sets the postcode to upper case

        //removes a space from the end of the postcode if there is one
        if(userEnteredPostcode.charAt(userEnteredPostcode.length() -1) == ' '){
            userEnteredPostcode = userEnteredPostcode.substring(0, userEnteredPostcode.length() - 1);
        }
        if(userEnteredPostcode.charAt(userEnteredPostcode.length() -1) == ' '){
            userEnteredPostcode = userEnteredPostcode.substring(0, userEnteredPostcode.length() - 1);
        }

        //adds a space in middle of postcode if there isn't one
        if(!userEnteredPostcode.contains(" ")){
            int spaceNeeded = userEnteredPostcode.length() - 3;
            //As postcodes are not a standard length, but the last part is always three characters,
            //this finds where the space should be, 3 characters from the end

            ArrayList<String> postcodeParts = new ArrayList<>(); //There will only be two
            for (int i = 0; i<userEnteredPostcode.length(); i+=spaceNeeded){
                postcodeParts.add(userEnteredPostcode.substring(i, Math.min(userEnteredPostcode.length(), i + spaceNeeded)));
            }

            userEnteredPostcode = postcodeParts.get(0) + " " + postcodeParts.get(1); //This puts the pieces back together again with a space in the middle
        }

        //Updates the user's information both in the currentUser object in CONTROL and in the Firebase Database.
        //Crucially, it sets firebaseDataFilledOut to true, meaning this page will not be visited again.

        Control.CONTROL.getCurrentUser().setPostCode(userEnteredPostcode);
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

}
