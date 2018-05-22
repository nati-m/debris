package com.example.android.debris1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;

public class HireReviewOrderActivity extends AppCompatActivity {

    boolean showIsPermitDefinitelyNotRequired;
    boolean showIsDriveWideEnoughForNonPermit;

    boolean showIsDefinitelyNotHazardous;
    boolean showIsDefinitelyInert;

    boolean showEditTextDriverInstructions;
    boolean showSpaceOnHighwayMessage;

    int maxPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_review_order);

        maxPoints = 0;

        if(!Control.CONTROL.getCurrentOrder().getPermitRequired()){
            showIsPermitDefinitelyNotRequired = true;
            showIsDriveWideEnoughForNonPermit = false;
            maxPoints++;
        }
        else {
            showIsPermitDefinitelyNotRequired = false;
            showIsDriveWideEnoughForNonPermit = true;
            maxPoints++;
        }

        if(!Control.CONTROL.getCurrentOrder().getWasteType().equals("Hazardous")){
            showIsDefinitelyNotHazardous = true;
            maxPoints++;
        } else {
            showIsDefinitelyNotHazardous = false;
        }

        if(Control.CONTROL.getCurrentOrder().getWasteType().equals("Inert")){
            showIsDefinitelyInert = true;
            maxPoints++;
        } else {
            showIsDefinitelyInert = false;
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
                Intent nextPageIntent = new Intent(HireReviewOrderActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            case R.id.menu_action_user_settings:
                Intent settingsIntent = new Intent(HireReviewOrderActivity.this, UserSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
