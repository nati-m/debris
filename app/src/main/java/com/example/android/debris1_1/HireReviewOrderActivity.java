package com.example.android.debris1_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
}
