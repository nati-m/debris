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
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;

public class HireIsPermitRequired extends AppCompatActivity {

    CheckBox permitRequiredCheckBox;
    CheckBox permitNotRequiredCheckBox;
    LinearLayout permitRequiredLinearLayout;
    LinearLayout permitNotRequiredLinearLayout;

    TextView permitForXSkips;
    TextView price;
    double permitPrice;
    boolean permitRequired;
    TextView messageAboutPermit;

    String messagePermitIsRequired;
    String messagePermitIsNotRequired;

    Button acceptAndContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_is_permit_required);

        permitForXSkips = findViewById(R.id.permit_for_x_skips_is_permit_required);
        price = findViewById(R.id.permit_price_is_permit_required);
        permitRequired = true;
        permitPrice = 20.00 * Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered();
        messageAboutPermit = findViewById(R.id.permit_message_is_permit_required);
        acceptAndContinue = findViewById(R.id.accept_and_continue_button_hire_permit_required);
        String numberOfSkips;

        if(Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() == 1){
            numberOfSkips = "Permit for 1 Skip: ";
            messagePermitIsRequired = "As your skip will be placed on a public highway, it is a legal requirement to purchase a permit.";
            messagePermitIsNotRequired = "As your skip will be placed on private land, a permit is not required.";

        } else {
            numberOfSkips = "Permits for " + Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() + " Skips: ";
            messagePermitIsRequired = "As your skips will be placed on a public highway, it is a legal requirement to purchase a permit.";
            messagePermitIsNotRequired = "As your skips will be placed on private land, a permit is not required.";
        }

        if(Control.CONTROL.getCurrentOrder().getSkipType() != Skip.DUMPY_BAG) {
            permitForXSkips.setText(numberOfSkips);
            messageAboutPermit.setText(messagePermitIsRequired);
        } else {
            if(Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() == 1){
                numberOfSkips = "Permit for 1 Skip Bag: ";
            } else {
                numberOfSkips = "Permits for "  + Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() + " Skip Bags: ";
            }
            permitForXSkips.setText(numberOfSkips);
        }

        permitRequiredCheckBox = findViewById(R.id.check_box_permit_required_is_permit_required);
        permitRequiredLinearLayout = findViewById(R.id.linear_layout_permit_required_is_permit_required);
        permitNotRequiredCheckBox = findViewById(R.id.check_box_permit_not_required_is_permit_required);
        permitNotRequiredLinearLayout = findViewById(R.id.linear_layout_permit_not_required_is_permit_required);

        permitPrice = 20.00 * Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered();
        price.setText("£" + permitPrice + "0");

        if(Control.CONTROL.getCurrentOrder().getSkipType() != Skip.DUMPY_BAG) {

            permitRequiredLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    permitNotRequiredLinearLayout.setBackgroundColor(getResources().getColor(R.color.background));
                    permitNotRequiredCheckBox.setChecked(false);
                    permitRequiredLinearLayout.setBackgroundColor(getResources().getColor(R.color.cyan));
                    permitRequiredCheckBox.setChecked(true);
                    permitRequired = true;
                    messageAboutPermit.setText(messagePermitIsRequired);
                    messageAboutPermit.setTextColor(getResources().getColor(R.color.colorPrimary));
                    permitPrice = 20.00 * Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered();
                    price.setText("£" + permitPrice + "0");
                }
            });

            permitNotRequiredLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    permitRequiredLinearLayout.setBackgroundColor(getResources().getColor(R.color.background));
                    permitRequiredCheckBox.setChecked(false);
                    permitNotRequiredLinearLayout.setBackgroundColor(getResources().getColor(R.color.cyan));
                    permitNotRequiredCheckBox.setChecked(true);
                    price.setText("£0");
                    permitRequired = false;
                    messageAboutPermit.setText(messagePermitIsNotRequired);
                    messageAboutPermit.setTextColor(getResources().getColor(R.color.greenConfirm));
                    permitPrice = 0;
                }
            });

        } else {
            permitRequiredLinearLayout.setVisibility(View.GONE);
            permitNotRequiredLinearLayout.setVisibility(View.GONE);
            price.setText("£0");
            permitRequired = false;
            messageAboutPermit.setTextColor(getResources().getColor(R.color.greenConfirm));
            messageAboutPermit.setText("Luckily, permits are not required for Skip Bags (phew).\n\nPress button below to continue your order.");
        }

        acceptAndContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Control.CONTROL.getCurrentOrder().setPermitRequired(permitRequired);
                Control.CONTROL.getCurrentOrder().setPermitPrice(permitPrice);

                Intent nextPageIntent = new Intent(HireIsPermitRequired.this, HireChooseDateActivity.class);
                startActivity(nextPageIntent);

            }
        });
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
                Intent nextPageIntent = new Intent(HireIsPermitRequired.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}