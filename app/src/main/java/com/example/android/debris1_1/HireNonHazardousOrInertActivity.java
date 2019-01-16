package com.example.android.debris1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;

public class HireNonHazardousOrInertActivity extends AppCompatActivity {

    TextView messageNonHazardousOrInert;
    TextView textViewOtherGeneralWaste;
    boolean isInert;
    LinearLayout linearLayoutOtherGeneralWaste;
    LinearLayout linearLayoutOnlyInertItems;
    CheckBox checkBoxOtherGeneralWaste;
    CheckBox checkBoxOnlyInertItems;
    Button continueButton;
    TextView whatWasOrdered;
    TextView wasteType;
    TextView subtotalTextView;
    ScrollView scrollView;
    double subtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_non_hazardous_or_inert);

        messageNonHazardousOrInert = findViewById(R.id.message_non_hazardous_or_inert);
        textViewOtherGeneralWaste = findViewById(R.id.text_view_other_general_waste_non_hazardous_or_inert);
        linearLayoutOtherGeneralWaste = findViewById(R.id.linear_layout_other_general_waste_non_hazardous_or_inert);
        linearLayoutOnlyInertItems = findViewById(R.id.linear_layout_only_items_above_non_hazardous_or_inert);
        checkBoxOtherGeneralWaste = findViewById(R.id.check_box_other_general_waste_non_hazardous_or_inert);
        checkBoxOnlyInertItems = findViewById(R.id.check_box_only_items_above_non_hazardous_or_inert);
        whatWasOrdered = findViewById(R.id.what_was_ordered_text_view_non_hazardous_or_inert);
        wasteType = findViewById(R.id.waste_type_text_view_non_hazardous_or_inert);
        subtotalTextView = findViewById(R.id.subtotal_text_view_non_hazardous_or_inert);
        continueButton = findViewById(R.id.continue_button_non_hazardous_or_inert);
        isInert = false;
        String message;
        String otherGeneralWaste;
        scrollView = findViewById(R.id.scrollView_hire_non_hazardous);

        if(Control.CONTROL.getCurrentOrder().getSkipType() != Skip.DUMPY_BAG && Control.CONTROL.getCurrentOrder().getSkipType() != Skip.WO_MAN_WITH_VAN) {
            if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                message = "GOOD. Your skips contain no hazardous waste!!";
                otherGeneralWaste = "Skips include other general waste";
            } else {
                message = "GOOD. Your skip contains no hazardous waste!!";
                otherGeneralWaste = "Skip includes other general waste";
            }
        } else if (Control.CONTROL.getCurrentOrder().getSkipType() == Skip.DUMPY_BAG) {
            if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                message = "GOOD. Your skip bags contain no hazardous waste!!";
                otherGeneralWaste = "Skip bags include other general waste";
                 } else {
                message = "GOOD. Your skip bag contains no hazardous waste!!";
                otherGeneralWaste = "Skip bag includes other general waste";
            }
        } else  {
            if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                message = "GOOD. Your vans contain no hazardous waste!!";
                otherGeneralWaste = "Vans include other general waste";
            } else {
                message = "GOOD. Your van contains no hazardous waste!!";
                otherGeneralWaste = "Van includes other general waste";
            }
        }

        messageNonHazardousOrInert.setText(message);
        textViewOtherGeneralWaste.setText(otherGeneralWaste);

        linearLayoutOtherGeneralWaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutOtherGeneralWaste.setBackgroundColor(getResources().getColor(R.color.cyan));
                linearLayoutOnlyInertItems.setBackgroundColor(getResources().getColor(R.color.background));
                checkBoxOtherGeneralWaste.setChecked(true);
                checkBoxOnlyInertItems.setChecked(false);
                isInert = false;
                workOutAndDisplaySubtotal();
                Control.CONTROL.focusScrollViewToBottom(scrollView);
            }
        });

        linearLayoutOnlyInertItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutOtherGeneralWaste.setBackgroundColor(getResources().getColor(R.color.background));
                linearLayoutOnlyInertItems.setBackgroundColor(getResources().getColor(R.color.cyan));
                checkBoxOtherGeneralWaste.setChecked(false);
                checkBoxOnlyInertItems.setChecked(true);
                isInert = true;
                workOutAndDisplaySubtotal();
                Control.CONTROL.focusScrollViewToBottom(scrollView);
            }
        });

        workOutAndDisplaySubtotal();

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Control.CONTROL.getCurrentOrder().setPrice(subtotal);
                if(!isInert) {
                    Control.CONTROL.getCurrentOrder().setWasteType("Non-Hazardous");
                } else {
                    Control.CONTROL.getCurrentOrder().setWasteType("Inert");
                }

                Intent nextPageIntent = new Intent(HireNonHazardousOrInertActivity.this, HireIsPermitRequired.class);
                startActivity(nextPageIntent);
            }
        });
    }

    private void workOutAndDisplaySubtotal(){

        Skip skip = Control.CONTROL.getCurrentOrder().getSkipType();
        String numberOfSkips = Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() + " x ";
        String skipType = "";
        String wasteTypeString = "";

        if(!isInert){

            wasteTypeString = "Non-Hazardous";

            if(skip==Skip.MAXI_SKIP){
                skipType = "Maxi Skip";
                subtotal = 240;
            } else if (skip==Skip.MIDI_SKIP){
                skipType = "Midi Skip";
                subtotal = 200;
            } else if (skip==Skip.MINI_SKIP){
                skipType = "Mini Skip";
                subtotal = 170;
            } else if (skip==Skip.DUMPY_BAG){
                skipType = "Skip Bag";
                subtotal = 80;
            } else {
                skipType = "Van";
                subtotal = 100;
            }
        } else {
            wasteTypeString = "Inert";

            if(skip==Skip.MAXI_SKIP){
                skipType = "Maxi Skip";
                subtotal = 200;
            } else if (skip==Skip.MIDI_SKIP){
                skipType = "Midi Skip";
                subtotal = 170;
            } else if (skip==Skip.MINI_SKIP){
                skipType = "Mini Skip";
                subtotal = 150;
            } else if (skip==Skip.DUMPY_BAG){
                skipType = "Skip Bag";
                subtotal = 60;
            } else {
                skipType = "Van";
                subtotal = 80;
            }
        }

        subtotal = subtotal * Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered();

        whatWasOrdered.setText(numberOfSkips + skipType);
        wasteType.setText(wasteTypeString);
        String subtotalString = Control.CONTROL.moneyFormat(subtotal);
        subtotalTextView.setText(subtotalString);
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
                Intent nextPageIntent = new Intent(HireNonHazardousOrInertActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            case R.id.menu_action_user_settings:
                Intent settingsIntent = new Intent(HireNonHazardousOrInertActivity.this, UserSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
