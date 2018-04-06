package com.example.android.debris1_1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class HireSingleDayViewActivity extends AppCompatActivity {

    ImageButton leftADay;
    ImageButton rightADay;
    TextView displayDateTextView;
    TextView priceSubtotal;
    Date dateOfPage;
    int day;
    int month;
    int year;
    ArrayList<Company> allCompanyArrayList = Control.CONTROL.getCurrentCompanies();
    ArrayList<Company> companyArrayList;
    //CompanyArrayAdapter companyArrayAdapter;
    //ListView companyListView;
    RecyclerView companyRecyclerView;
    CompanyRecyclerViewArrayAdapter companyRecyclerViewArrayAdapter = new CompanyRecyclerViewArrayAdapter(getRelevantCompaniesArrayList(), this);
    Spinner sortCompaniesBySpinner;
    ArrayList<String> spinnerSortCompaniesByCategories;
    ArrayAdapter<String> sortCompaniesArrayAdapter;
    AdapterView.OnItemSelectedListener onItemSelectedListener;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
    Skip skipType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_single_day_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        leftADay = (ImageButton) findViewById(R.id.back_one_day_hire_single_day_view);
        rightADay = (ImageButton) findViewById(R.id.forward_one_day_hire_single_day_view);
        displayDateTextView = (TextView) findViewById(R.id.date_text_view_string_single_day_view);
        priceSubtotal = (TextView) findViewById(R.id.subtotal_text_view);
        priceSubtotal.setText("OOO");
        dateOfPage = Control.CONTROL.getCurrentOrder().getDateOfSkipArrival().getTime();
        skipType = Control.CONTROL.getCurrentOrder().getSkipType();

        //This sets the date displayed at the top of the page to the date the user chose on the previous page
        updateDateDisplayedAtTop();


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

        companyArrayList = getRelevantCompaniesArrayList();


        companyRecyclerView = findViewById(R.id.recycler_view_companies_single_day_view);

        companyRecyclerView.setHasFixedSize(true);
        companyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        companyRecyclerView.setAdapter(companyRecyclerViewArrayAdapter);

        companyRecyclerViewArrayAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = companyRecyclerView.getChildViewHolder(v).getAdapterPosition();
                Control.CONTROL.setCompanySelectedInRecyclerView(companyArrayList.get(pos));

                String priceString = "£" + Control.CONTROL.getCompanySelectedInRecyclerView().getDefaultPriceDifferentDependingOnSkipType(skipType) + "0";
                priceSubtotal.setText(priceString);

                for (int i = 0; i < companyRecyclerViewArrayAdapter.getViewsArrayList().size(); i++){
                    if (i == pos){
                        companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(Color.CYAN);
                    }
                    else companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(getResources().getColor(R.color.background));
                }
            }
        });


        sortCompaniesBySpinner = (Spinner) findViewById(R.id.sort_companies_by_spinner_single_day_view);
        spinnerSortCompaniesByCategories = new ArrayList<>();
        spinnerSortCompaniesByCategories.add("Rating");
        spinnerSortCompaniesByCategories.add("Cheapest");
        sortCompaniesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerSortCompaniesByCategories);
        sortCompaniesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortCompaniesBySpinner.setAdapter(sortCompaniesArrayAdapter);

        sortCompaniesBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(pos == 0){
                    ArrayList<Company> ratingOrderArrayList = getRelevantCompaniesArrayList();
                    Control.CONTROL.setCurrentCompanies(ratingOrderArrayList);
                    Collections.sort(ratingOrderArrayList, Company.ratingComparator);
                    reOrderCompanies(ratingOrderArrayList);
                }
                if (pos == 1){
                    ArrayList<Company> priceOrderArrayList = getRelevantCompaniesArrayList();
                    Control.CONTROL.setCurrentCompanies(priceOrderArrayList);
                    Collections.sort(priceOrderArrayList, Company.priceComparator);
                    reOrderCompanies(priceOrderArrayList);
                    }

            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ImageButton confirmOrder = (ImageButton) findViewById(R.id.button_confirm_hire_day_view);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                setOrderDateToDateSelectedOnThisPage(dateOfPage);
                Intent nextPageIntent = new Intent(HireSingleDayViewActivity.this, HireConfirmOrderActivity.class);
                startActivity(nextPageIntent);
            }
        });


    }
    //TODO http://www.egscomics.com/index.php?id=493

    private ArrayList<Company> getRelevantCompaniesArrayList(){
        ArrayList<Company> toReturn = new ArrayList<>();
        Skip skipType = Control.CONTROL.getCurrentOrder().getSkipType();
        //Only 4 Skip instances exist so this will be equal to any other skip the same size
        int numberOfSkipsWanted = Control.CONTROL.getCurrentOrder().getSkipsOrderedArrayList().size();

        for (int i = 0; i < allCompanyArrayList.size(); i++){
            if (allCompanyArrayList.get(i).isThisKindOfSkipAvailable(skipType, numberOfSkipsWanted)){
                toReturn.add(allCompanyArrayList.get(i));
            }
        }
        return toReturn;
    }

    /*
    This re-orders the list of companies on the page to the order specified in the ArrayList<Company>
    newOrder. It does so by resetting the Company RecyclerView's Array Adapter with the newly ordered
    ArrayList.
     */
    private void reOrderCompanies(ArrayList<Company> newOrder){
        companyRecyclerViewArrayAdapter = new CompanyRecyclerViewArrayAdapter(newOrder, this);
        companyRecyclerView.setAdapter(companyRecyclerViewArrayAdapter);

        //TODO Set the selected company, if any, to Cyan

        companyRecyclerViewArrayAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = companyRecyclerView.getChildViewHolder(v).getAdapterPosition();
                Control.CONTROL.setCompanySelectedInRecyclerView(Control.CONTROL.getCurrentCompanies().get(pos));

                //TODO make priceString method including if thingy is checked
                String priceString = "£" + Control.CONTROL.getCompanySelectedInRecyclerView().getDefaultPriceDifferentDependingOnSkipType(skipType) + "0";
                priceSubtotal.setText(priceString);

                for (int i = 0; i < companyRecyclerViewArrayAdapter.getViewsArrayList().size(); i++){
                    if (i == pos){
                        companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(Color.CYAN);
                    }
                    else companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(getResources().getColor(R.color.background));
                }
            }
        });
    }

    /*
    This updates the current order's date to the date the user chose on this page.
    This method is called when the user clicks on the confirm order button at the bottom right of the screen.
     */
    private void setOrderDateToDateSelectedOnThisPage(Date dateOfPage){
        Calendar c = Calendar.getInstance();
        c.setTime(dateOfPage);
        Control.CONTROL.getCurrentOrder().setDateOfSkipArrival(c);
    }

    /*
    This changed what date is displayed at the top of the screen to the current dateOfPage.
    It should be called whenever the date is changed for the page, for example if the user
    hits the left or right arrow buttons.
     */
    private void updateDateDisplayedAtTop(){
        Calendar c = Calendar.getInstance();
        c.setTime(dateOfPage);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        String dateToSetAtTop = Control.CONTROL.getDateAsAStringInFormatWed18JUN2018(year, month, day);
        displayDateTextView.setText(dateToSetAtTop);
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


        //This makes sure the proposed new date is within the range between tomorrow and 1 year from now.
        //If it is not, the function aborts.
        if(!isDateWithinRange(c)){
            return;
        }

        //This sets the date of the page to Calendar c, i.e. adds 1 or takes 1 day from the date
        dateOfPage = c.getTime();
        updateDateDisplayedAtTop();

        //todo initCompaniesForThisDate();


    }

    /*
    This checks that the date is in the range from tomorrow to a year from today.
    It is used to stop the user from being able to navigate to a time in the past or more than a year
    from now using the arrow buttons.
    It returns false if the new date the user is trying to get to is out of range, and true if the
    date is within this range.
     */
    private boolean isDateWithinRange(Calendar proposedDate){
        Calendar c = Calendar.getInstance(); //this sets Calendar c's time to today

        //This checks if the date the user is trying to navigate to is before tomorrow
        if(proposedDate.before(c)){
            return false;
        }

        c.add(Calendar.YEAR, 1); //this sets Caldendar c's time to a year from now

        //This checks if the date the user is trying to navigate to is after a year from now
        if(proposedDate.after(c)){
            return false;
        }

        //the date is therefore in range so true is returned
        return true;
    }

    private void initCompaniesForThisDate(){

        //TODO Company.getListOfCompaniesWhoServiceThisPostcode(postcode);
        //This


    }

}
