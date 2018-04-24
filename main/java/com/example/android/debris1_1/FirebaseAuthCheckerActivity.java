package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FirebaseAuthCheckerActivity extends AppCompatActivity {

    //Firebase Auth variables
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    //Firebase Database variables
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersDatabaseReference;
    private ChildEventListener childEventListener;
    private ValueEventListener userValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_auth_checker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        usersDatabaseReference = firebaseDatabase.getReference().child("users");


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //user.getUid(); //NULL EXCEPTION CRASHED IT
                if (user != null) {
                    // User is signed in

                    decideWhatToDoWhenUserSignsIn(user);
                } else {
                    // User is signed out
                    //onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            // new AuthUI.IdpConfig.PhoneBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .setTosUrl("https://superapp.example.com/terms-of-service.html")
                                    .setPrivacyPolicyUrl("https://superapp.example.com/privacy-policy.html")
                                    .build(),
                            Control.RC_SIGN_IN);
                }
            }
        };
    }

    private void decideWhatToDoWhenUserSignsIn(final FirebaseUser user){
        //These variables are all final so they ca be accessed in the ValueEventListener below

        final String uid = user.getUid();
        final DatabaseReference thisUsersDatabaseReference = firebaseDatabase.getReference().child("users").child(uid);
        final TextView test = findViewById(R.id.test_text_view_firebase_auth_checker);
        test.setText("decideWhatToDoWhenUserSignsIn was called");

        thisUsersDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //OnDataChange ALSO INCLUDES CHANGING FROM NOT-CHECKED, E.G. IT WILL BE CALLED ONCE INITIALLY
                if (dataSnapshot.hasChild(uid)){
                    //The user is already in the database
                    //todo if the database user has FireBaseDatabaseFilledOut, else go straight to home page


                    DatabaseReference hasThisUserFilledOutPostcode = firebaseDatabase.getReference().child("users").child(uid).child("firebaseDatabaseFilledOut");
                    hasThisUserFilledOutPostcode.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //If the user's all set up, go straight to Front Page
                            if(dataSnapshot.getValue().equals(true)){
                                Intent nextPageIntent = new Intent(FirebaseAuthCheckerActivity.this, FrontPageLoggedInActivity.class);
                                startActivity(nextPageIntent);
                            } else {
                                //else direct them to the page FirstTimeUserEnterAddressPostcode to finish entering their postcode
                                Intent nextPageIntent = new Intent(FirebaseAuthCheckerActivity.this, FirstTimeUserEnterAddressPostcode.class);
                                startActivity(nextPageIntent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    //This is the first time the user has signed in and will be added to the database
                    //before being transported to the page FirstTimeUserEnterAddressPostcode
                    test.setText("floooooooooooom");
                    PublicUser publicUser = new PublicUser(user.getDisplayName(), "", user.getEmail(), new ArrayList<Order>(), false, uid);

                    thisUsersDatabaseReference.setValue(publicUser);
                    Control.CONTROL.setCurrentUser(publicUser);

                    Intent nextPageIntent = new Intent(FirebaseAuthCheckerActivity.this, FirstTimeUserEnterAddressPostcode.class);
                    startActivity(nextPageIntent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do nothing
            }
        });



        //If this user doesn't exist i.e. it's their first login, creates their node in the database
        //with their uid as the key. firebaseDatabaseFilledOut is set to false - it will be set to
        //true once the user fills out their info.



    }



    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }

        //detachDatabaseReadListener();
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
                Intent nextPageIntent = new Intent(FirebaseAuthCheckerActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
