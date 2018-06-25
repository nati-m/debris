package com.example.android.debris1_1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HireOptionalChooseCollectionDateActivity extends AppCompatActivity {

    DatePicker datePicker;
    Button dateDisplayButton;
    Calendar calendarWithDateOfSkipCollection;
    int year;
    int month;
    int day;
    RecyclerView recyclerView;
    ChooseTimeArrayAdapter chooseTimeArrayAdapter;
    ArrayList<String> times;
    String selectedTime;
    TextView skipCollectionTextView;
    SimpleDateFormat dayOfWeekIncludingSimpleDateFormat;
    Button continueButton;
    TextView arrivalDateMessage;
    TextView totalPriceTextView;
    TextView numberOfDaysHired;
    TextView priceForNumberOfDaysHired;

    double newTotalPrice;
    double priceAddedForNumberOfDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_optional_choose_collection_date);

        arrivalDateMessage = (TextView) findViewById(R.id.skip_arriving_message_hire_optional_collection_date);
        dayOfWeekIncludingSimpleDateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.UK);

        String arrivalDateMessageString = "Your Skip is Arriving on " + dayOfWeekIncludingSimpleDateFormat.format(Control.CONTROL.getCurrentOrder().getDateOfSkipArrival().getTime())
                + " at " + Control.CONTROL.getCurrentOrder().getTimeOfArrival();
        arrivalDateMessage.setText(arrivalDateMessageString);

        dateDisplayButton = findViewById(R.id.date_display_button_collection_optional_choose_date);
        skipCollectionTextView = findViewById(R.id.skip_collection_time_and_date_text_view_optional_collection);

        continueButton = findViewById(R.id.continue_button_optional_collection_date);

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


        //Sets the default skip collection date to 2 days after the skip arrives.
        //Displays this date on the dateDisplayButton.
        calendarWithDateOfSkipCollection = Calendar.getInstance();
        calendarWithDateOfSkipCollection.setTime(Control.CONTROL.getCurrentOrder().getDateOfSkipArrival().getTime());
        calendarWithDateOfSkipCollection.add(Calendar.DATE, 5);
        year = calendarWithDateOfSkipCollection.get(Calendar.YEAR);
        month = calendarWithDateOfSkipCollection.get(Calendar.MONTH);
        day = calendarWithDateOfSkipCollection.get(Calendar.DAY_OF_MONTH);
        setDateDisplayedOnButton(year, month, day);

        recyclerView = findViewById(R.id.recycler_view_choose_time_skip_arrival);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptContinue();
            }
        });

        numberOfDaysHired = findViewById(R.id.number_of_days_hired_for_optional_choose_collection_date);
        priceForNumberOfDaysHired = findViewById(R.id.subtotal_hire_length_charge_optional_choose_collection_date);
        totalPriceTextView = findViewById(R.id.total_price_optional_choose_collection_date);

        resetChooseTimeRecyclerView();
        updateSkipCollectionTextView();
        updatePrices();
    }


    private boolean isItSaturday(Calendar calendar){
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //Saturday has the static value of 7, so dayOfWeek will equal Calendar.SATURDAY if Calender.DAY_OF_WEEK returned 7
        if (dayOfWeek == Calendar.SATURDAY){
            return true;
        }
        return false;
    }

    private void updateSkipCollectionTextView(){
        String message = "Collection " + dayOfWeekIncludingSimpleDateFormat.format(calendarWithDateOfSkipCollection.getTime()) + ",";

        if (selectedTime.isEmpty()){
            message += " Select Time";
        } else {
            message += " " + selectedTime;
        }

        skipCollectionTextView.setText(message);
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
            c.setTime(Control.CONTROL.getCurrentOrder().getDateOfSkipArrival().getTime());
            c.add(Calendar.DATE, 1); //This sets the minimum date to 1 day after skip arrival


            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis()); //Setting the minimum, c, defined above

            c.add(Calendar.DATE, 27); //This sets the maximum date to 28 days after the arrival date
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
                    calendarWithDateOfSkipCollection.set(year, month, day);
                    resetChooseTimeRecyclerView();
                    updateSkipCollectionTextView();
                    updatePrices();
                }
            };




    private void setDateDisplayedOnButton(int year, int month, int day){
        //Months start at 0 in Calendar class -
        // don't change the month variable to month+1 as month is from a calendar to start with!

        //The CONTROL feature is a centralised function returning a String that returns dates in the
        //format dd MMM YYYY, e.g. 08 NOV 2018. This makes the date format consistent across the app.
        dateDisplayButton.setText(Control.CONTROL.getDateAsAStringInFormat08NOV2018(year, month, day) + "?");
    }

    private void resetChooseTimeRecyclerView(){
        times = new ArrayList<>();
        times.add("8am-10am"); times.add("9am-11am"); times.add("10am-12pm"); times.add("11am-1pm"); times.add("12pm-2pm");
        //if it is not Saturday, three more times are added
        if(!isItSaturday(calendarWithDateOfSkipCollection)) {
            times.add("1pm-3pm");
            times.add("2pm-4pm");
            times.add("3pm-5pm");
        }

        chooseTimeArrayAdapter = new ChooseTimeArrayAdapter(times, this);

        recyclerView.setAdapter(chooseTimeArrayAdapter);

        chooseTimeArrayAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = recyclerView.getChildViewHolder(v).getAdapterPosition();

                selectedTime = times.get(pos);

                for (int i = 0; i < chooseTimeArrayAdapter.getViewsArrayList().size(); i++){
                    if (i == pos){
                        chooseTimeArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(Color.CYAN);
                    }
                    else chooseTimeArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(getResources().getColor(R.color.background));
                }

                updateSkipCollectionTextView();
            }
        });

        selectedTime = "";

        for (int i = 0; i < chooseTimeArrayAdapter.getViewsArrayList().size(); i++){
            chooseTimeArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(getResources().getColor(R.color.background));
        }


    }

    private void attemptContinue(){

        skipCollectionTextView.setError(null);

        if(selectedTime.isEmpty()){
            skipCollectionTextView.setError("Time choice required!");
            Snackbar.make(skipCollectionTextView, "Choose arrival time too!", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }

        //A date and time has been chosen...

        Control.CONTROL.getCurrentOrder().setDateOfSkipCollection(calendarWithDateOfSkipCollection);
        Control.CONTROL.getCurrentOrder().setTimeOfCollection(selectedTime);

        //The permit price is subtracted to keep the process in sync with users who chose not to select their collection date now
        newTotalPrice = newTotalPrice - Control.CONTROL.getCurrentOrder().getPermitPrice();
        Control.CONTROL.getCurrentOrder().setPrice(newTotalPrice);
        Control.CONTROL.getCurrentOrder().setSurchargeForLongHire(priceAddedForNumberOfDays);


        Intent nextPageIntent = new Intent(HireOptionalChooseCollectionDateActivity.this, HirePaymentActivity.class);
        startActivity(nextPageIntent);


    }

    private void updatePrices(){
        priceAddedForNumberOfDays = 0;

        long daysApart = calendarWithDateOfSkipCollection.getTimeInMillis() - Control.CONTROL.getCurrentOrder().getDateOfSkipArrival().getTimeInMillis();
        //This returns the milliseconds apart, below converts it to days
        daysApart = TimeUnit.DAYS.convert(daysApart, TimeUnit.MILLISECONDS);

        double pricePlusPermit = Control.CONTROL.getCurrentOrder().getPrice() + Control.CONTROL.getCurrentOrder().getPermitPrice();

        if(daysApart > 21){
            //Then there is a 20% surcharge
            priceAddedForNumberOfDays = pricePlusPermit;
            priceAddedForNumberOfDays = priceAddedForNumberOfDays / 5;
        } else if (daysApart > 14){
            //Then there is a 10% surcharge
            priceAddedForNumberOfDays = pricePlusPermit;
            priceAddedForNumberOfDays = priceAddedForNumberOfDays / 10;
        } else if (daysApart > 7){
            priceAddedForNumberOfDays = 7;
        }

        //This is then multiplied by the number of skips
        priceAddedForNumberOfDays = priceAddedForNumberOfDays * Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered();

        double total = pricePlusPermit + priceAddedForNumberOfDays;

        String numberOfDays = "Charge for " + daysApart + " days hired:";
        numberOfDaysHired.setText(numberOfDays);

        String priceForNumberDaysHired = Control.CONTROL.moneyFormat(priceAddedForNumberOfDays);
        priceForNumberOfDaysHired.setText(priceForNumberDaysHired);

        String totalString = Control.CONTROL.moneyFormat(total);
        totalPriceTextView.setText(totalString);

        newTotalPrice = total;

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
                Intent nextPageIntent = new Intent(HireOptionalChooseCollectionDateActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            case R.id.menu_action_user_settings:
                Intent settingsIntent = new Intent(HireOptionalChooseCollectionDateActivity.this, UserSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
