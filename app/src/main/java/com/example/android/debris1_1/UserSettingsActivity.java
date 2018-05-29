package com.example.android.debris1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserSettingsActivity extends AppCompatActivity {

    Button changeDefaultAddressButton;
    TextView defaultAddressTextView;
    LinearLayout appearingChangeAddressInputLinearLayout;
    EditText enterAddressLine1;
    EditText enterAddressLine2;
    EditText enterPostcode;
    Button cancelAddressChange;
    Button confirmAddressChange;

    Button addEmailAddressButton;
    LinearLayout appearingAddEmailLinearLayout;
    LinearLayout appearingAddEmailLinearLayoutButtons;
    TextView usersEmailTextView;
    TextView usersAdditionalEmailsTextView;
    EditText addEmailEditText;
    Button cancelAddEmailButton;
    Button confirmAddEmailButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        changeDefaultAddressButton = findViewById(R.id.edit_default_address_button_settings);
        defaultAddressTextView = findViewById(R.id.users_address_text_view_settings);
        appearingChangeAddressInputLinearLayout = findViewById(R.id.choose_new_address_linear_layout_settings);
        enterAddressLine1 = findViewById(R.id.enter_address_line_1_settings);
        enterAddressLine2 = findViewById(R.id.enter_address_line_2_settings);
        enterPostcode = findViewById(R.id.enter_postcode_settings);
        cancelAddressChange = findViewById(R.id.cancel_change_address_button_settings);
        confirmAddressChange = findViewById(R.id.confirm_change_address_button_settings);
        addEmailAddressButton = findViewById(R.id.add_email_button_settings);
        appearingAddEmailLinearLayout = findViewById(R.id.enter_new_email_linear_layout_settings);
        appearingAddEmailLinearLayoutButtons = findViewById(R.id.enter_new_email_buttons_linear_layout_settings);
        usersEmailTextView = findViewById(R.id.users_email_text_view_settings);
        usersAdditionalEmailsTextView = findViewById(R.id.users_additional_emails_text_view_settings);
        addEmailEditText = findViewById(R.id.enter_new_email_edit_text_settings);
        cancelAddressChange = findViewById(R.id.cancel_add_email_button_settings);
        confirmAddEmailButton = findViewById(R.id.confirm_add_email_button_settings);

        //Update the page to show the user's personal information.
        //These methods will be called again whenever any of the user's information is changed.
        updateDefaultAddressTextView();
        updateUsersEmailAndAdditionalEmailsTextViews();

        //Make the views only accessed by pressing a button disappear from view until their respective buttons are pressed
        appearingChangeAddressInputLinearLayout.setVisibility(View.GONE);
        appearingAddEmailLinearLayout.setVisibility(View.GONE);
        appearingAddEmailLinearLayoutButtons.setVisibility(View.GONE);

        changeDefaultAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appearingChangeAddressInputLinearLayout.setVisibility(View.VISIBLE);
                changeDefaultAddressButton.setVisibility(View.GONE);
            }
        });

        cancelAddressChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDefaultAddressButton.setVisibility(View.VISIBLE);
                appearingChangeAddressInputLinearLayout.setVisibility(View.GONE);
            }
        });

        confirmAddressChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptAddressChange();
            }
        });


    }

    private void attemptAddressChange() {
        boolean cancel = false;

        enterAddressLine1.setError(null);
        enterAddressLine2.setError(null);
        enterPostcode.setError(null);

        String addressLine1 = enterAddressLine1.getText().toString();
        String addressLine2 = enterAddressLine2.getText().toString();
        String postcode = enterPostcode.getText().toString();

        if (TextUtils.isEmpty(addressLine1)) {
            enterAddressLine1.setError(getString(R.string.error_field_required));
            cancel = true;
        }

        //Address Line 2 is not required

        if (TextUtils.isEmpty(postcode)) {
            enterPostcode.setError(getString(R.string.error_field_required));
            cancel = true;
        }

        if (!isPostcodeValid(postcode)) {
            enterPostcode.setError("This is not a valid postcode.");
            cancel = true;
        }

        if (!cancel) {

            postcode = Control.CONTROL.formatPostcode(postcode);

            Control.CONTROL.getCurrentUser().setAddressLine1(addressLine1);
            Control.CONTROL.getCurrentUser().setAddressLine2(addressLine2);
            Control.CONTROL.getCurrentUser().setPostCode(postcode);

            String uid = Control.CONTROL.getCurrentUser().getFirebaseUid();
            DatabaseReference thisUsersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            //And now to update the Firebase Database with this user's new postcode and firebaseDataFilledOut set to true
            thisUsersDatabaseReference.setValue(Control.CONTROL.getCurrentUser());

            changeDefaultAddressButton.setVisibility(View.VISIBLE);
            appearingChangeAddressInputLinearLayout.setVisibility(View.GONE);
            updateDefaultAddressTextView();
        }
    }

    private boolean isPostcodeValid(String postcode) {
        //TODO this

        if (postcode == null || postcode.length() < 5 || postcode.length() > 8) {
            return false;
        }

        return true;
    }

    private void updateDefaultAddressTextView() {
        String usersAddressAndPostcode;
        if (Control.CONTROL.getCurrentUser().getAddressLine2().isEmpty()) {
            usersAddressAndPostcode = Control.CONTROL.getCurrentUser().getAddressLine1() +
                    "\n" + Control.CONTROL.getCurrentUser().getPostCode();
        } else {
            usersAddressAndPostcode = Control.CONTROL.getCurrentUser().getAddressLine1() +
                    "\n" + Control.CONTROL.getCurrentUser().getAddressLine2() +
                    "\n" + Control.CONTROL.getCurrentUser().getPostCode();
        }
        defaultAddressTextView.setText(usersAddressAndPostcode);
    }

    private void updateUsersEmailAndAdditionalEmailsTextViews(){
        usersEmailTextView.setText(Control.CONTROL.getCurrentUser().getEmail());

        if(Control.CONTROL.getCurrentUser().getNumberOfAdditionalEmails() == 0){
            usersAdditionalEmailsTextView.setText("None");
            return;
        }
        else if (Control.CONTROL.getCurrentUser().getNumberOfAdditionalEmails() == 1){
            usersAdditionalEmailsTextView.setText(Control.CONTROL.getCurrentUser().getAdditionalEmail1());
            return;
        }
        else if (Control.CONTROL.getCurrentUser().getNumberOfAdditionalEmails() > 1){
            usersAdditionalEmailsTextView.setText(Control.CONTROL.getCurrentUser().getAdditionalEmail1()
                + "\n" + Control.CONTROL.getCurrentUser().getAdditionalEmail2());
            return;
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
                Intent nextPageIntent = new Intent(UserSettingsActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            case R.id.menu_action_user_settings:
                Intent settingsIntent = new Intent(UserSettingsActivity.this, UserSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
