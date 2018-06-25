package com.example.android.debris1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;

public class HireDriverMessageActivity extends AppCompatActivity {

    EditText messageForDriverEditText;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_driver_message);

        messageForDriverEditText = findViewById(R.id.edittext_driver_message);
        doneButton = findViewById(R.id.continue_button_hire_driver_message);

        //This means if the user clicks back and returns to this page, their message is saved
        if(Control.CONTROL.getCurrentOrder().getMessageForDriver() != null){
            String whatWasWrittenBefore = Control.CONTROL.getCurrentOrder().getMessageForDriver();
            messageForDriverEditText.setText(whatWasWrittenBefore, TextView.BufferType.EDITABLE);
        }

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageForDriverEditText.getText().toString();
                Control.CONTROL.getCurrentOrder().setMessageForDriver(message);

                Intent nextPageIntent = new Intent(HireDriverMessageActivity.this, HireReviewOrderActivity.class);
                startActivity(nextPageIntent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        //This saves the user's message if they press the back button
        String message = messageForDriverEditText.getText().toString();
        Control.CONTROL.getCurrentOrder().setMessageForDriver(message);
        super.onBackPressed();
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
                Intent nextPageIntent = new Intent(HireDriverMessageActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            case R.id.menu_action_user_settings:
                Intent settingsIntent = new Intent(HireDriverMessageActivity.this, UserSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
