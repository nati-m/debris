package com.example.android.debris1_1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class HireHomePageActivity extends AppCompatActivity {

    EditText enterAddressLine1;
    EditText enterAddressLine2;
    EditText enterPostcode;
    DatePicker datePicker;
    Button dateDisplayButton;
    int year;
    int month;
    int day;
    Calendar calenderWithDateOfSkipArrival = Calendar.getInstance();
    Spinner numberOfSkipsWantedSpinner;
    Spinner skipSizeSpinner;
    ArrayList<Integer> numberOfSkipsWantedSpinnerCategories;
    ArrayList<String> skipSizeSpinnerCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enterAddressLine1 = (EditText) findViewById(R.id.enter_address_line_1_hire_home_page);
        enterAddressLine2 = (EditText) findViewById(R.id.enter_address_line_2_hire_home_page);
        enterPostcode = (EditText) findViewById(R.id.enter_postcode_hire_home_page);
        dateDisplayButton = (Button)findViewById(R.id.date_display_button_hire_home_page);

        //This sets the postcode hint to the user's postcode
        enterPostcode.setHint(Control.CONTROL.getCurrentUser().getPostCode());

        //Set date to default 5 days after the current date on both the  date display button
        //and the calendarWithDateOfSkipArrival variable, which is the one the user edits for their order.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 5);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDateDisplayedOnButton(year, month, day);
        calenderWithDateOfSkipArrival.setTimeInMillis(calendar.getTimeInMillis());

        //Date Picker is set up
        datePicker = new DatePicker(this);

        //This sets the date choosing button to pop up a date picker when clicked,
        //by calling the method setDate.
        dateDisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(view);
            }
        });


        //see https://www.tutorialspoint.com/android/android_datepicker_control.htm


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
        skipSizeSpinnerCategories.add("Maxi Skip (8yd)");
        skipSizeSpinnerCategories.add("Midi Skip (4yd)");
        skipSizeSpinnerCategories.add("Mini Skip (2yd)");
        skipSizeSpinnerCategories.add("Dumpy Bag");

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
                Snackbar.make(view, "The standard skip size is a Maxi skip (8 square yards), others may be cheaper.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }

    /*
    This creates a date picker dialog which the user can then use to choose a date for skip arrival
     */
    public void setDate(View view) {
        showDialog(111);
    }

    /*
    This creates a dialog with which the user can select a date.
    It has a theme, DatePickerStyle, which can be edited in styles.xml
    It sets the minimum date for selection to day after the current day.
    It sets the maximum date for selection for one year from the current day.

    There is also a commented out section which if uncommented will change the DatePicker's
    style to a calendar view, rather than a spinner. If this is preferred in a later version
    of the app, simply uncomment this section.
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 111) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerStyle,
                    dateListener, year, month, day);

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 1); //This sets c's date to tomorrow
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

            c.add(Calendar.YEAR, 1);
            c.add(Calendar.DATE, -1); //This sets c's date to a year in the future from now
            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());


            //This is here in case a Calendar rather than a Spinner view is wanted in future.
            //Simply uncomment to do so.
            //datePickerDialog.getDatePicker().setCalendarViewShown(true);
            //datePickerDialog.getDatePicker().setSpinnersShown(false);


            return datePickerDialog;
        }
        return null;
    }

    /*
    When the date picker is used and a date is chosen, the date displayed on the button is updated
    to reflect the user's choice.
    The variable calenderWithDateOfSkipArrival is also updated to match the user's choice. This
    will be used as the date in the Order object created when the user clicks the Order button ("Get A Skip")
     */
    private DatePickerDialog.OnDateSetListener dateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker,
                                      int year, int month, int day) {
                    setDateDisplayedOnButton(year, month, day);
                    calenderWithDateOfSkipArrival.set(year, month, day);

                }
            };



    private void setDateDisplayedOnButton(int year, int month, int day){
        //Months start at 0 in Calendar class -
        // don't change the month variable to month+1 as month is from a calendar to start with!

        //The CONTROL feature is a centralised function returning a String that returns dates in the
        //format dd MMM YYYY, e.g. 08 NOV 2018. This makes the date format consistent across the app.
        dateDisplayButton.setText(Control.CONTROL.getDateAsAStringInFormat08NOV2018(year, month, day));
    }

    /*
    This method makes sure that an order is not placed without all the relevant information.
    It does not allow the user to make an order without at least the first line of an address and
    a postcode.
    The date of skip arrival, if the user does nothing, will automatically be set for 5 days from
    the current date (this is implemented in the OnCreate method).
     */
    public void attemptGetASkipButton(){
        // Reset errors.
        enterAddressLine1.setError(null);
        enterAddressLine2.setError(null);
        enterPostcode.setError(null);

        // Store values at time of order attempt
        String addressLine1 = enterAddressLine1.getText().toString();
        String addressLine2 = enterAddressLine2.getText().toString();
        String postcode = enterPostcode.getText().toString();


        boolean cancel = false; //this becomes true if there are any errors in the form, stopping the next page from being loaded
        View focusView = null;

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

            if (skipSizeString == "Midi Skip (4yd)") {
                skip = Skip.MIDI_SKIP;
            } else if (skipSizeString == "Mini Skip (2yd)") {
                skip = Skip.MINI_SKIP;
            } else if (skipSizeString == "Dumpy Bag"){
                skip = Skip.DUMPY_BAG;
            }

            for(int i = 0; i < numberOfSkipsInt; i++){
                skipArrayList.add(skip);
            }


            //create a new Order object with this Address, Postcode, Date, and ArrayList of Skips ordered
            Order currentOrder = new Order(addressLine1, addressLine2, postcode, skipArrayList, calenderWithDateOfSkipArrival);

            //sets this to the current order the app is focused on in CONTROL
            Control.CONTROL.setCurrentOrder(currentOrder);

            //goes to next page
            Intent nextPageIntent = new Intent(HireHomePageActivity.this, HireSingleDayViewActivity.class);
            startActivity(nextPageIntent);

        }

    }



    private boolean isPostcodeValid(String postcode){
        //TODO this

        return true;
    }

}
