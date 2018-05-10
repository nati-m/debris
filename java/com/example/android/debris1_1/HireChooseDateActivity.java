package com.example.android.debris1_1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class HireChooseDateActivity extends AppCompatActivity {

    DatePicker datePicker;
    Button dateDisplayButton;
    Calendar calenderWithDateOfSkipArrival;
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_choose_date);

        dateDisplayButton = findViewById(R.id.date_display_button_arrival_hire_choose_date);
        calenderWithDateOfSkipArrival = Calendar.getInstance();

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

        //Set date to default 5 days after the current date on both the  date display button
        //and the calendarWithDateOfSkipArrival variable, which is the one the user edits for their order.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 5);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDateDisplayedOnButton(year, month, day);
        calenderWithDateOfSkipArrival.setTimeInMillis(calendar.getTimeInMillis());

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
                    Control.CONTROL.getCurrentOrder().setDateOfSkipArrival(calenderWithDateOfSkipArrival);
                }
            };




    private void setDateDisplayedOnButton(int year, int month, int day){
        //Months start at 0 in Calendar class -
        // don't change the month variable to month+1 as month is from a calendar to start with!

        //The CONTROL feature is a centralised function returning a String that returns dates in the
        //format dd MMM YYYY, e.g. 08 NOV 2018. This makes the date format consistent across the app.
        dateDisplayButton.setText(Control.CONTROL.getDateAsAStringInFormat08NOV2018(year, month, day));
    }


}
