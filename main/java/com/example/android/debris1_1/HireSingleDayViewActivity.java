package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class HireSingleDayViewActivity extends AppCompatActivity {

    ImageButton leftADay;
    ImageButton rightADay;
    TextView displayDateTextView;
    Date dateOfPage;
    int day;
    int month;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_single_day_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        leftADay = (ImageButton) findViewById(R.id.back_one_day_hire_single_day_view);
        rightADay = (ImageButton) findViewById(R.id.forward_one_day_hire_single_day_view);
        displayDateTextView = (TextView) findViewById(R.id.date_text_view_string_single_day_view);
        dateOfPage = Control.CONTROL.getCurrentOrder().getDateOfSkipArrival().getTime();

        Calendar c = Calendar.getInstance();
        c.setTime(dateOfPage);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        String dateToSetAtTop = Control.CONTROL.getDateAsAStringInFormat08NOV2018(year, month, day);
        displayDateTextView.setText(dateToSetAtTop);


        leftADay.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                leftOrRightADay(false);
            }
        });
        rightADay.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                leftOrRightADay(true);
            }
        });



        Button confirmOrder = (Button) findViewById(R.id.button_confirm_hire_day_view);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                Intent nextPageIntent = new Intent(HireSingleDayViewActivity.this, HireConfirmOrderActivity.class);
                startActivity(nextPageIntent);
            }
        });


    }

    private void leftOrRightADay(boolean isItRight){

        Calendar c = Calendar.getInstance();
        c.setTime(dateOfPage);

        //this adds a day or subtracts a day to the date of page depending on which button was pressed
        //Calendar.add https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#add-int-int-
        if(isItRight){
            c.add(Calendar.DATE, 1);
        } else {
            c.add(Calendar.DATE, -1);
        }
        dateOfPage = c.getTime();

        //todo initCompaniesForThisDate();
    }

    private void initCompaniesForThisDate(){

        //TODO Company.getListOfCompaniesWhoServiceThisPostcode(postcode);
        //This


    }

}
