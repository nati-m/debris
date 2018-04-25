package com.example.android.debris1_1;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static TextView testTextView;
    Button nextPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        nextPageButton = (Button) findViewById(R.id.front_page_button);
        FirebaseUser checkIfUserIsLoggedIn = FirebaseAuth.getInstance().getCurrentUser();
        if(checkIfUserIsLoggedIn != null){ // If the user is logged in already
            String usersName = checkIfUserIsLoggedIn.getDisplayName();
            String userFirstName = usersName.split(" ")[0];
            nextPageButton.setText("Continue as " + userFirstName);
            //TODO FIX BUG WHERE THIS CHANGES EVEN WHEN LOGGED OUT IF YOU LOG OUT ON MAIN PAGE
        }

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                Intent nextPageIntent = new Intent(MainActivity.this, FirebaseAuthCheckerActivity.class);
                startActivity(nextPageIntent);
            }
        });

        Button toTestTown = findViewById(R.id.go_to_test_town);
        toTestTown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPageIntent = new Intent(MainActivity.this, TestTownActivity.class);
                startActivity(nextPageIntent);
            }
        });



        //TESTING GETTING A JSON OBJECT

//        testTextView = (TextView) findViewById(R.id.testTestTestTestTest);
//        String JSONData = "Not this time pal";
//
//        URL url = null;
//        try {
//            url = new URL("http://www.simplylookupadmin.co.uk/JSONservice/JSONSearchForPostZonData.aspx?datakey=W_B7C4975860B64332998F4398F8DBA0&postcode=NE30%202AY&homepostcode=NE4%209EN");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        GetPostcodeData getPostcodeData = new GetPostcodeData(url);
//
//        GetAndHandlePostcodeData getAndHandlePostcodeData = new GetAndHandlePostcodeData("NE4 5AB", "NE6 5SQ");
//        JSONData = getAndHandlePostcodeData.getDistanceBetweenTwoPostcodesAsAString();
//        testTextView.setText(JSONData);

//        AddressLookup addressLookup = new AddressLookup();
//        int dist = -99;
//        dist = addressLookup.findDistanceBetweenTwoPostcodesInKM("NE4 9EN", "NE77XA"); //won't work yet
//        String addressText = "It didn't work";
//
//        try {
//            addressText = addressLookup.getAddressFromPostcode("NE4 5AB");
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        testTextView.setText(addressText);


//        try {
//            dist = addressLookup.findDistanceBetweenTwoPostcodesInKM("NE4 9EN", "NE77XA");
//        } catch (JSONException e){
//            e.printStackTrace();
//        }

        //testTextView.setText("" + dist);


//        JsonObjectRequest JSONRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//
//
//
//
//                        testTextView.setText(response.toString());
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO: Handle error
//
//                    }
//                });

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
                nextPageButton.setText("Log In Or Sign Up");
                //Changes the text on the button
                Intent nextPageIntent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
