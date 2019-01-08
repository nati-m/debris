package com.example.android.debris1_1;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;

public class HireSelectSkipContentsActivity extends AppCompatActivity {

    LinearLayout noneOfTheseItemsPresent;
    LinearLayout oneOrMoreOfTheseItemsPresent;
    boolean isToxic;
    CheckBox checkBoxNonePresent;
    CheckBox checkBoxOneOrMorePresent;

    TextView skipWillContain;
    TextView dontPanic;

    Button continueButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_select_skip_contents);

        noneOfTheseItemsPresent = findViewById(R.id.linear_layout_none_of_these_items_present_skip_content);
        oneOrMoreOfTheseItemsPresent = findViewById(R.id.linear_layout_one_or_more_items_present_skip_content);
        isToxic = true;
        checkBoxNonePresent = findViewById(R.id.check_box_none_of_these_items_present_skip_content);
        checkBoxOneOrMorePresent = findViewById(R.id.check_box_one_or_more_items_present_skip_content);

        skipWillContain = findViewById(R.id.skips_will_contain_select_skip_contents);
        dontPanic = findViewById(R.id.dont_panic_skip_contents);

        continueButton = findViewById(R.id.continue_button_select_skip_contents);

        if(Control.CONTROL.getCurrentOrder().getSkipType() != Skip.DUMPY_BAG && Control.CONTROL.getCurrentOrder().getSkipType() != Skip.WO_MAN_WITH_VAN) {
            if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                skipWillContain.setText("I confirm that, unless the switch below is marked as such, my skips will contain none of the following:");
            } else {
                skipWillContain.setText("I confirm that, unless the switch below is marked as such, my skip will contain none of the following:");
            }
        } else if (Control.CONTROL.getCurrentOrder().getSkipType() == Skip.DUMPY_BAG){
            if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                skipWillContain.setText("I confirm that, unless the switch below is marked as such, my skip bags will contain none of the following:");
            } else {
                skipWillContain.setText("I confirm that, unless the switch below is marked as such, my skip bag will contain none of the following:");
            }
        }
        else { //This means it's a van, and the don't panic footnote wording is changed too
            if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                skipWillContain.setText("I confirm that, unless the switch below is marked as such, my filled vans will contain none of the following:");
            } else {
                skipWillContain.setText("I confirm that, unless the switch below is marked as such, my filled van will contain none of the following:");
            }
            dontPanic.setText("(*Don’t panic if it does, we just need this information to keep your order compliant and to get you the right van provider.)");
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueIsPressed();
            }
        });

        noneOfTheseItemsPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isToxic = false;
                noneOfTheseItemsPresent.setBackgroundColor(getResources().getColor(R.color.cyan));
                oneOrMoreOfTheseItemsPresent.setBackgroundColor(getResources().getColor(R.color.background));
                checkBoxNonePresent.setChecked(true);
                checkBoxOneOrMorePresent.setChecked(false);
            }
        });

        oneOrMoreOfTheseItemsPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isToxic = true;
                noneOfTheseItemsPresent.setBackgroundColor(getResources().getColor(R.color.background));
                oneOrMoreOfTheseItemsPresent.setBackgroundColor(getResources().getColor(R.color.cyan));
                checkBoxNonePresent.setChecked(false);
                checkBoxOneOrMorePresent.setChecked(true);
            }
        });



    }

    private void continueIsPressed(){

        if(isToxic){
            Intent nextPageIntent = new Intent(HireSelectSkipContentsActivity.this, HireHazardousActivity.class);
            startActivity(nextPageIntent);
        } else {
            Intent nextPageIntent = new Intent(HireSelectSkipContentsActivity.this, HireNonHazardousOrInertActivity.class);
            startActivity(nextPageIntent);
        }



    }

    private void workOutOrderPriceAndSetItInCurrentOrder(String contentsType){

        //This does nothing if used with a contents type that is not Hazardous, Non-Hazardous or Inert.
        if(!contentsType.equals("Non-Hazardous") && !contentsType.equals("Hazardous") && !contentsType.equals("Inert")){
            return;
        }

        double price = -999;

        //Hazardous and non-hazardous are done at the same time as hazardous prices are simply non-hazardous tripled
        if (contentsType.equals("Non-Hazardous") || contentsType.equals("Hazardous")){

            Skip skip = Control.CONTROL.getCurrentOrder().getSkipType();
            if(skip==Skip.MAXI_SKIP){
                price = 240;
            } else if (skip==Skip.MIDI_SKIP){
                price = 200;
            } else if (skip==Skip.MINI_SKIP){
                price = 170;
            } else if (skip==Skip.DUMPY_BAG){
                price = 80;
            }

            //The price is tripled if the contents are hazardous
            if(contentsType.equals("Hazardous")){
                price = price * 3;
            }
        }
        else if (contentsType.equals("Inert")){
            Skip skip = Control.CONTROL.getCurrentOrder().getSkipType();
            if(skip==Skip.MAXI_SKIP){
                price = 200;
            } else if (skip==Skip.MIDI_SKIP){
                price = 170;
            } else if (skip==Skip.MINI_SKIP){
                price = 150;
            } else if (skip==Skip.DUMPY_BAG){
                price = 60;
            }
        }

        //The price is multiplied by how many skips are ordered
        price = price * Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered();

        //The current order's price is set
        Control.CONTROL.getCurrentOrder().setPrice(price);

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
                Intent nextPageIntent = new Intent(HireSelectSkipContentsActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            case R.id.menu_action_user_settings:
                Intent settingsIntent = new Intent(HireSelectSkipContentsActivity.this, UserSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
