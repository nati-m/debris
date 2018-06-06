package com.example.android.debris1_1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HireHomePageActivity extends AppCompatActivity {

    EditText enterAddressLine1;
    EditText enterAddressLine2;
    EditText enterPostcode;
    Spinner numberOfSkipsWantedSpinner;
    Spinner skipSizeSpinner;
    ArrayList<Integer> numberOfSkipsWantedSpinnerCategories;
    ArrayList<String> skipSizeSpinnerCategories;

    LinearLayout usersAddressLinearLayout;
    LinearLayout selectAddressLinearLayout;
    Button chooseDifferentAddress;
    boolean useDefaultAddress;
    TextView usersAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enterAddressLine1 = findViewById(R.id.enter_address_line_1_hire_home_page);
        enterAddressLine2 = findViewById(R.id.enter_address_line_2_hire_home_page);
        enterPostcode =  findViewById(R.id.enter_postcode_hire_home_page);

        usersAddressLinearLayout = findViewById(R.id.default_user_address_linear_layout_hire_home_page);
        selectAddressLinearLayout = findViewById(R.id.choose_new_address_linear_layout_hire_home_page);
        chooseDifferentAddress = findViewById(R.id.use_different_address_button_hire_home_page);
        useDefaultAddress = true;
        usersAddress = findViewById(R.id.users_address_text_view_hire_home_page);

        //This view disappears unless the user presses a button to select a different address
        selectAddressLinearLayout.setVisibility(View.GONE);

        chooseDifferentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersAddressLinearLayout.setVisibility(View.GONE);
                selectAddressLinearLayout.setVisibility(View.VISIBLE);
                useDefaultAddress = false;
            }
        });

        String usersAddressAndPostcode;

        if(Control.CONTROL.getCurrentUser().getAddressLine2().isEmpty()){
            usersAddressAndPostcode = Control.CONTROL.getCurrentUser().getAddressLine1() +
                    "\n" + Control.CONTROL.getCurrentUser().getPostCode();
        } else {
            usersAddressAndPostcode = Control.CONTROL.getCurrentUser().getAddressLine1() +
                    "\n" + Control.CONTROL.getCurrentUser().getAddressLine2() +
                    "\n" + Control.CONTROL.getCurrentUser().getPostCode();
        }

        usersAddress.setText(usersAddressAndPostcode);


        //Sets the button Get A Skip's form entry parameters. Errors will be displayed if
        //address line 1 or postcode are blank, or if the entered text is invalid.
        // TODO Make the postcode checker
        Button getASkip = (Button) findViewById(R.id.button_get_a_skip);
        getASkip.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                attemptGetASkipButton();
            }
        });

        //Initialising the two spinners
        numberOfSkipsWantedSpinner = (Spinner) findViewById(R.id.number_of_skips_wanted_spinner_hire_home_page);
        skipSizeSpinner = (Spinner) findViewById(R.id.skip_size_spinner_hire_home_page);

        //Adding ths Spinners' Drop down elements
        numberOfSkipsWantedSpinnerCategories = new ArrayList<>();
        numberOfSkipsWantedSpinnerCategories.add(new Integer(1));
        numberOfSkipsWantedSpinnerCategories.add(new Integer(2));
        numberOfSkipsWantedSpinnerCategories.add(new Integer(3));
        numberOfSkipsWantedSpinnerCategories.add(new Integer(4));
        numberOfSkipsWantedSpinnerCategories.add(new Integer(5));
        skipSizeSpinnerCategories = new ArrayList<>();
        skipSizeSpinnerCategories.add("Maxi Skip (8yd³)");
        skipSizeSpinnerCategories.add("Midi Skip (4yd³)");
        skipSizeSpinnerCategories.add("Mini Skip (2yd³)");
        skipSizeSpinnerCategories.add("Skip Bag");

        // Creating adapters for spinner
        ArrayAdapter<Integer> dateAdapterForNumberOfSkipsWantedSpinner = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, numberOfSkipsWantedSpinnerCategories);
        ArrayAdapter<String> dataAdapterForSkipSizeSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, skipSizeSpinnerCategories);

        // Drop down layout style - list view with radio button
        dateAdapterForNumberOfSkipsWantedSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForSkipSizeSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        numberOfSkipsWantedSpinner.setAdapter(dateAdapterForNumberOfSkipsWantedSpinner);
        skipSizeSpinner.setAdapter(dataAdapterForSkipSizeSpinner);

        //Setting a toast for the info button
        ImageButton infoImageButton = (ImageButton) findViewById(R.id.information_image_button_hire_home_page);
        infoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "The standard skip size is a Maxi skip (8 cubic yards), others may be cheaper.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }



    /*
    This method makes sure that an order is not placed without all the relevant information.
    It does not allow the user to make an order without at least the first line of an address and
    a postcode.
    The date of skip arrival, if the user does nothing, will automatically be set for 5 days from
    the current date (this is implemented in the OnCreate method).
     */
    public void attemptGetASkipButton() {

        // Store values at time of order attempt
        String addressLine1;
        String addressLine2;
        String postcode;

        boolean cancel = false; //this becomes true if there are any errors in the form, stopping the next page from being loaded
        View focusView = null;

        if (!useDefaultAddress){

            // Reset errors.
            enterAddressLine1.setError(null);
            enterAddressLine2.setError(null);
            enterPostcode.setError(null);

            addressLine1 = enterAddressLine1.getText().toString();
            addressLine2 = enterAddressLine2.getText().toString();
            postcode = enterPostcode.getText().toString();

            if (TextUtils.isEmpty(addressLine1)) {
                enterAddressLine1.setError(getString(R.string.error_field_required));
                focusView = enterAddressLine1;
                cancel = true;
                }

                //Address Line 2 is not required

             if (TextUtils.isEmpty(postcode)) {
                  enterPostcode.setError(getString(R.string.error_field_required));
                  focusView = enterPostcode;
                   cancel = true;
                }


             if (!isPostcodeValid(postcode)) {
                    enterPostcode.setError("This is not a valid postcode.");
                    focusView = enterPostcode;
                    cancel = true;
                }

        } else {
            addressLine1 = Control.CONTROL.getCurrentUser().getAddressLine1();
            addressLine2  = Control.CONTROL.getCurrentUser().getAddressLine2();
            postcode = Control.CONTROL.getCurrentUser().getPostCode();
        }

        if (!cancel) {
            //create an ArrayList of Skip objects of the skip size the user chose in the skip size
            //spinner, and populate the ArrayList with the number of Skips chosen in the drop down menu.
            //In practice, this will usually be 1.
            String numberOfSkips = numberOfSkipsWantedSpinner.getSelectedItem().toString();
            int numberOfSkipsInt = Integer.parseInt(numberOfSkips);
            ArrayList<Skip> skipArrayList = new ArrayList<>();
            String skipSizeString = skipSizeSpinner.getSelectedItem().toString();

            Skip skip = Skip.MAXI_SKIP;
            //must be initialised to work in for loop below, and Skip has no accessible constructor,
            //so this is used as a default and changed if it isn't a maxi skip

            if (skipSizeString == "Midi Skip (4yd³)") {
                skip = Skip.MIDI_SKIP;
            } else if (skipSizeString == "Mini Skip (2yd³)") {
                skip = Skip.MINI_SKIP;
            } else if (skipSizeString == "Skip Bag"){
                skip = Skip.DUMPY_BAG;
            }

            for(int i = 0; i < numberOfSkipsInt; i++){
                skipArrayList.add(skip);
            }

            postcode = Control.CONTROL.formatPostcode(postcode);


            //create a new Order object with this Address, Postcode, Date, and ArrayList of Skips ordered
            Order currentOrder = new Order(addressLine1, addressLine2, postcode, skipArrayList, Calendar.getInstance());

            //sets this to the current order the app is focused on in CONTROL
            Control.CONTROL.setCurrentOrder(currentOrder);

            //goes to next page
            Intent nextPageIntent = new Intent(HireHomePageActivity.this, HireSelectSkipContentsActivity.class);
            startActivity(nextPageIntent);

        }

    }



    private boolean isPostcodeValid(String postcode){
        //TODO this

        if(postcode == null || postcode.length() < 5 || postcode.length() > 8){
            return false;
        }

        return true;
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
                Intent nextPageIntent = new Intent(HireHomePageActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            case R.id.menu_action_user_settings:
                Intent settingsIntent = new Intent(HireHomePageActivity.this, UserSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
