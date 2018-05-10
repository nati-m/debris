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
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;

public class HireSelectSkipContentsActivity extends AppCompatActivity {

    Switch asbestos;
    Switch paintChemicals;
    Switch fridges;
    Switch smallElectronics;
    Switch gasCanisters;
    Switch soilStonesBricks;
    Switch concreteTilesGlass;
    Switch other;

    TextView skipWillContain;
    TextView gasCanistersTextView;

    Button continueButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_select_skip_contents);

        asbestos = findViewById(R.id.switch_asbestos_skip_contents);
        paintChemicals = findViewById(R.id.switch_paints_chemicals_skip_contents);
        fridges = findViewById(R.id.switch_fridges_skip_contents);
        smallElectronics = findViewById(R.id.switch_small_electronics_skip_contents);
        gasCanisters = findViewById(R.id.switch_gas_canisters_skip_contents);
        soilStonesBricks = findViewById(R.id.switch_soil_stones_bricks_skip_contents);
        concreteTilesGlass = findViewById(R.id.switch_concrete_tiles_glass_skip_contents);
        other = findViewById(R.id.switch_other_skip_contents);

        skipWillContain = findViewById(R.id.skips_will_contain_select_skip_contents);
        gasCanistersTextView = findViewById(R.id.gas_canisters_text_view_skip_contents);

        continueButton = findViewById(R.id.continue_button_select_skip_contents);

        if(Control.CONTROL.getCurrentOrder().getSkipType() != Skip.DUMPY_BAG) {
            if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                skipWillContain.setText("My Skips Will Contain...");
            } else {
                skipWillContain.setText("My Skip Will Contain...");
            }
        } else {
            if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                skipWillContain.setText("My Skip Bags Will Contain...");
            } else {
                skipWillContain.setText("My Skip Bag Will Contain...");
            }
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueIsPressed();
            }
        });



    }

    private void continueIsPressed(){
        gasCanistersTextView.setError(null);
        other.setError(null);

        //If no switches are selected, an error is shown and the next page is not accessed
        if(!asbestos.isChecked() && !paintChemicals.isChecked() && !fridges.isChecked() && !smallElectronics.isChecked()
                && !gasCanisters.isChecked() && !soilStonesBricks.isChecked() && !concreteTilesGlass.isChecked() && !other.isChecked()){

            String error;
            if(Control.CONTROL.getCurrentOrder().getSkipType() != Skip.DUMPY_BAG) {
                if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                    error = "Your Skips Must Contain Something!";
                } else {
                    error = "Your Skip Must Contain Something!";
                }
            } else {
                if (Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() > 1) {
                    error = "Your Skip Bags Must Contain Something!";
                } else {
                    error = "Your Skip Bag Must Contain Something!";
                }
            }

            other.setError(error);
            Snackbar.make(other, error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        //If the user selects gas cannisters, an error is shown (as they are illegal to dispose of in a skip)
        //and the next page is not shown.
        if(gasCanisters.isChecked()){
            gasCanistersTextView.setError("It is illegal for us to dispose of these");
            Snackbar.make(gasCanistersTextView, "It is illegal for us to dispose of Gas Canisters. We will not accept skips containing them.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        //Now sorting out what type of contents can begin.
        //If a skip contains anything hazardous, the WHOLE SKIP is considered hazardous.
        //The same for non-hazardous, provided there are no hazardous items as well.
        //Hazardous are triple the usual price.

        String contentsType = "Inert";

        if (asbestos.isChecked() || paintChemicals.isChecked() || fridges.isChecked()){
            contentsType = "Hazardous";
        }
        else if (smallElectronics.isChecked() || other.isChecked()){
            contentsType = "Non-Hazardous";
        }

        workOutOrderPriceAndSetItInCurrentOrder(contentsType);

        Intent nextPageIntent = new Intent(HireSelectSkipContentsActivity.this, HireChooseDateActivity.class);
        startActivity(nextPageIntent);


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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
