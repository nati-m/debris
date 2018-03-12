package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.debris1_1.backend.Order;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HireHomePageActivity extends AppCompatActivity {

    EditText enterAddressLine1;
    EditText enterAddressLine2;
    EditText enterPostcode;
    EditText enterDate; //This will later be changed for a DatePicker, e.g.
    // http://abhiandroid.com/ui/datepicker
    // https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enterAddressLine1 = (EditText) findViewById(R.id.enter_address_line_1_hire_home_page);
        enterAddressLine2 = (EditText) findViewById(R.id.enter_address_line_2_hire_home_page);
        enterPostcode = (EditText) findViewById(R.id.enter_postcode_hire_home_page);
        enterDate = (EditText) findViewById(R.id.enter_date_hire_home_page);

        //This sets the postcode hint to the user's postcode
        enterPostcode.setHint(Control.CONTROL.getCurrentUser().getPostCode());

        Button getASkip = (Button) findViewById(R.id.button_get_a_skip);
        getASkip.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {

                attemptOrder();
            }
        });

        Spinner skipSizeSpinner = (Spinner) findViewById(R.id.skip_size_spinner_hire_home_page);

        // Spinner click listener
        //skipSizeSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        ArrayList<String> spinnerCategories = new ArrayList<>();
        spinnerCategories.add("Maxi Skip (8yd)");
        spinnerCategories.add("Midi Skip (4yd)");
        spinnerCategories.add("Mini Skip (2yd)");
        spinnerCategories.add("Dumpy Bag");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerCategories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        skipSizeSpinner.setAdapter(dataAdapter);

        //Setting a toast for the info button
        ImageButton infoImageButton = (ImageButton) findViewById(R.id.information_image_button_hire_home_page);
        infoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "The standard skip size is a Maxi skip (8 square yards), others may be cheaper.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    private void attemptOrder(){

        // Reset errors.
        enterAddressLine1.setError(null);
        enterAddressLine2.setError(null);
        enterPostcode.setError(null);
        enterDate.setError(null);

        // Store values at time of order attempt
        String addressLine1 = enterAddressLine1.getText().toString();
        String addressLine2 = enterAddressLine2.getText().toString();
        String postcode = enterPostcode.getText().toString();
        String dateTempString = enterDate.getText().toString();


        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(addressLine1)){
            enterAddressLine1.setError(getString(R.string.error_field_required));
            focusView = enterAddressLine1;
            cancel = true;
        }

        //Address Line 2 is not required

        if(TextUtils.isEmpty(postcode)){
            enterPostcode.setError(getString(R.string.error_field_required));
            focusView = enterPostcode;
            cancel = true;
        }

        if(TextUtils.isEmpty(dateTempString)){
            enterDate.setError(getString(R.string.error_field_required));
            focusView = enterDate;
            cancel = true;
        }


        if(!isPostcodeValid(postcode)){
            enterPostcode.setError("This is not a valid postcode.");
            focusView = enterPostcode;
            cancel = true;
        }

        if(!cancel){
            //create a new Order object
            Order currentOrder = new Order(Control.CONTROL.getCurrentUser(), addressLine1, addressLine2, postcode, dateTempString);

            //sets this to the current order the app is focused on in CONTROL
            Control.CONTROL.setCurrentOrder(currentOrder);

            //goes to next page

            Intent nextPageIntent = new Intent(HireHomePageActivity.this, HireSingleDayViewActivity.class);
            startActivity(nextPageIntent);

        }
    }

    private boolean isPostcodeValid(String postcode){
        //TO BE CREATED

        return true;
    }

}
