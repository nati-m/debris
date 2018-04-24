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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        if(userEnteredPostcode.length() <= 4 || userEnteredPostcode.length() >= 8){
            enterPostcode.setError("Please enter a valid postcode");
            return;
        }

        //if the postcode is valid...
        //Updates the user's information both in the currentUser object in CONTROL and in the Firebase Database.
        //Crucially, it sets firebaseDataFilledOut to true, meaning this page will not be visited again.

        Control.CONTROL.getCurrentUser().setPostCode(userEnteredPostcode);
        Control.CONTROL.getCurrentUser().setFirebaseDatabaseFilledOut(true);

        String uid = Control.CONTROL.getCurrentUser().getFirebaseUid();
        DatabaseReference thisUsersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        //And now to update the Firebase Database with this user's new postcode and firebaseDataFilledOut set to true
        thisUsersDatabaseReference.setValue(Control.CONTROL.getCurrentUser());

        //TODO send them somewhere else if they are a company
        Intent nextPageIntent = new Intent(FirstTimeUserEnterAddressPostcode.this, FrontPageLoggedInActivity.class);
        startActivity(nextPageIntent);

    }

}
