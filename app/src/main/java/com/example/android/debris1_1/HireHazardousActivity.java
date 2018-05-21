package com.example.android.debris1_1;

import android.content.Intent;
import android.support.design.widget.Snackbar;
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

public class HireHazardousActivity extends AppCompatActivity {

    LinearLayout gasCanistersLinearLayout;
    CheckBox checkboxGasCanisters;
    Button continueButton;
    boolean isIllegal;

    TextView whatWasOrdered;
    TextView wasteType;
    TextView subtotalTextView;
    double subtotal;

    TextView messageHazardous;
    TextView messageConfirmNoGasCanisters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_hazardous);

        gasCanistersLinearLayout = findViewById(R.id.linear_layout_gas_canisters_hazardous);
        checkboxGasCanisters = findViewById(R.id.check_box_gas_canisters_hazardous);
        continueButton = findViewById(R.id.continue_button_hazardous);
        isIllegal = true;
        whatWasOrdered = findViewById(R.id.what_was_ordered_text_view_hazardous);
        wasteType = findViewById(R.id.waste_type_text_view_hazardous);
        subtotalTextView = findViewById(R.id.subtotal_text_view_hazardous);
        messageHazardous = findViewById(R.id.message_hazardous);
        messageConfirmNoGasCanisters = findViewById(R.id.confirm_no_gas_canisters_text_view_hazardous);

        String message; String confirmNoGas;
        if(Control.CONTROL.getCurrentOrder().getSkipType() != Skip.DUMPY_BAG) {
            if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                message = "Your skips contain hazardous waste.";
                confirmNoGas = "Confirm below that your skips will not contain Gas Canisters / Bottles:";
            } else {
                message = "Your skip contains hazardous waste.";
                confirmNoGas = "Confirm below that your skip will not contain Gas Canisters / Bottles:";
            }
        } else {
            if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                message = "Your skip bags contain hazardous waste.";
                confirmNoGas = "Confirm below that your skip bags will not contain Gas Canisters / Bottles:";
            } else {
                message = "Your skip bag contains hazardous waste.";
                confirmNoGas = "Confirm below that your skip bag will not contain Gas Canisters / Bottles:";
            }
        }
        messageHazardous.setText(message);
        messageConfirmNoGasCanisters.setText(confirmNoGas);

        String ordered = Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() + " x ";
        if(Control.CONTROL.getCurrentOrder().getSkipType() == Skip.MAXI_SKIP){
            subtotal = 720;
            ordered += "Maxi Skip";
        } else if (Control.CONTROL.getCurrentOrder().getSkipType() == Skip.MIDI_SKIP) {
            subtotal = 600;
            ordered += "Midi Skip";
        } else if (Control.CONTROL.getCurrentOrder().getSkipType() == Skip.MINI_SKIP){
            subtotal = 510;
            ordered += "Mini Skip";
        } else {
            subtotal = 240;
            ordered += "Skip Bag";
        }
        whatWasOrdered.setText(ordered);

        subtotal = subtotal * Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered();

        gasCanistersLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxGasCanisters.isChecked()){
                    continueButton.setBackgroundColor(getResources().getColor(R.color.greyAndDull));
                    checkboxGasCanisters.setChecked(false);
                    subtotalTextView.setText("N/A");
                    subtotalTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    wasteType.setText("Illegal");
                    wasteType.setTextColor(getResources().getColor(R.color.colorPrimary));
                    gasCanistersLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    isIllegal = true;
                }
                else
                {
                    continueButton.setBackgroundColor(getResources().getColor(R.color.softerAmber));
                    checkboxGasCanisters.setChecked(true);
                    subtotalTextView.setText("£" + subtotal + "0");
                    subtotalTextView.setTextColor(getResources().getColor(R.color.black));
                    wasteType.setText("Hazardous");
                    wasteType.setTextColor(getResources().getColor(R.color.black));
                    gasCanistersLinearLayout.setBackgroundColor(getResources().getColor(R.color.cyan));
                    isIllegal = false;
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkboxGasCanisters.setError(null);
                if(!isIllegal){
                    Control.CONTROL.getCurrentOrder().setWasteType("Hazardous");
                    Control.CONTROL.getCurrentOrder().setPrice(subtotal);

                    Intent nextPageIntent = new Intent(HireHazardousActivity.this, HireIsPermitRequired.class);
                    startActivity(nextPageIntent);
                }
                else {
                    checkboxGasCanisters.setError("It is illegal for us to dispose of this.");
                    Snackbar.make(v, "Please confirm there are no gas canisters in your order, or abort this order.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
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
                Intent nextPageIntent = new Intent(HireHazardousActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
