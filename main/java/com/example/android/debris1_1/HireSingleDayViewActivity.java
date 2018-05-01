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
import android.widget.CheckBox;
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
    TextView permitPrice;
    TextView priceSubtotal;
    Date dateOfPage;
    int day;
    int month;
    int year;
    RecyclerView companyRecyclerView;
    CompanyRecyclerViewArrayAdapter companyRecyclerViewArrayAdapter;
    Spinner sortCompaniesBySpinner;
    ArrayList<String> spinnerSortCompaniesByCategories;
    ArrayAdapter<String> sortCompaniesArrayAdapter;
    Skip skipType;
    CheckBox permitCheckBox;
    boolean permitRequired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_single_day_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        leftADay = findViewById(R.id.back_one_day_hire_single_day_view);
        rightADay = findViewById(R.id.forward_one_day_hire_single_day_view);
        displayDateTextView = findViewById(R.id.date_text_view_string_single_day_view);
        permitPrice = findViewById(R.id.permit_price_text_view_single_day_view);
        priceSubtotal = findViewById(R.id.subtotal_text_view);
        dateOfPage = Control.CONTROL.getCurrentOrder().getDateOfSkipArrival().getTime();
        skipType = Control.CONTROL.getCurrentOrder().getSkipType();
        permitCheckBox = findViewById(R.id.check_box_permit_needed_single_day_view);

        //This means whenever the permit check box is checked, the price updates
        permitCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePriceAndAddToTextViewAndCurrentOrder();
            }
        });

        //Populates Control's ArrayList of relevant companies for single day view
        //First it sets to new ArrayList to initialise or delete any leftover data
        Control.CONTROL.setSingleDayViewCompanies(new ArrayList<Company>());
        Control.CONTROL.setSingleDayViewCompanies(getRelevantCompaniesArrayList());

        //This sets the date displayed at the top of the page to the date the user chose on the previous page
        updateDateDisplayedAtTop();


        leftADay.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.setTime(Control.CONTROL.getCurrentOrder().getDateOfSkipArrival().getTime());
                c.add(Calendar.DAY_OF_YEAR, -1);

                //check that the date the user wants to navigate to is between tomorrow and a year
                //from today. If not, the action cancels.
                if(!isDateWithinRange(c)){
                    return;
                }

                Control.CONTROL.getCurrentOrder().setDateOfSkipArrival(c);

                Intent refreshThisPageAtNewDate = new Intent(HireSingleDayViewActivity.this, HireSingleDayViewActivity.class);
                startActivity(refreshThisPageAtNewDate);

            }
        });
        rightADay.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.setTime(Control.CONTROL.getCurrentOrder().getDateOfSkipArrival().getTime());
                c.add(Calendar.DAY_OF_YEAR, 1);

                //check that the date the user wants to navigate to is between tomorrow and a year
                //from today. If not, the action cancels.
                if(!isDateWithinRange(c)){
                    return;
                }

                Control.CONTROL.getCurrentOrder().setDateOfSkipArrival(c);

                Intent refreshThisPageAtNewDate = new Intent(HireSingleDayViewActivity.this, HireSingleDayViewActivity.class);
                startActivity(refreshThisPageAtNewDate);
            }
        });

        companyRecyclerViewArrayAdapter = new CompanyRecyclerViewArrayAdapter(getRelevantCompaniesArrayList(), this);

        companyRecyclerView = findViewById(R.id.recycler_view_companies_single_day_view);
        companyRecyclerView.setHasFixedSize(true);
        companyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        companyRecyclerView.setAdapter(companyRecyclerViewArrayAdapter);

        companyRecyclerViewArrayAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = companyRecyclerView.getChildViewHolder(v).getAdapterPosition();
                Control.CONTROL.setCompanySelectedInRecyclerView(Control.CONTROL.getSingleDayViewCompanies().get(pos));

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
                    Control.CONTROL.setSingleDayViewCompanies(ratingOrderArrayList);
                    Collections.sort(ratingOrderArrayList, Company.ratingComparator);
                    reOrderCompanies(ratingOrderArrayList);
                }
                if (pos == 1){
                    ArrayList<Company> priceOrderArrayList = getRelevantCompaniesArrayList();
                    Control.CONTROL.setSingleDayViewCompanies(priceOrderArrayList);
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
                Control.CONTROL.getCurrentOrder().setPermitRequired(permitCheckBox.isChecked());
                Control.CONTROL.getCurrentOrder().setChosenSkipCo(Control.CONTROL.getCompanySelectedInRecyclerView());
                //the price in current Order will still be set from last time user clicked a
                //company column or the permit required checkbox

                Intent nextPageIntent = new Intent(HireSingleDayViewActivity.this, HireConfirmOrderActivity.class);
                startActivity(nextPageIntent);
            }
        });


    }
    //TODO http://www.egscomics.com/index.php?id=493

    private ArrayList<Company> getRelevantCompaniesArrayList(){
        ArrayList<Company> toReturn = new ArrayList<>();
        //Only 4 Skip instances exist so this will be equal to any other skip the same size
        int numberOfSkipsWanted = Control.CONTROL.getCurrentOrder().getSkipsOrderedArrayList().size();
        ArrayList<Company> allCompanyArrayList = Control.CONTROL.getCurrentCompanies();

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

//        int positionOfCurrentlyHighlightedCompany;
//        if (Control.CONTROL.getCompanySelectedInRecyclerView() == null){
//            positionOfCurrentlyHighlightedCompany = -1;
//            //This won't change the colour of any columns to cyan as no column has a negative index
//        } else {
//            positionOfCurrentlyHighlightedCompany = Control.CONTROL.getSingleDayViewCompanies().indexOf(Control.CONTROL.getCompanySelectedInRecyclerView());
//        }
//
//        for (int i = 0; i < companyRecyclerViewArrayAdapter.getViewsArrayList().size(); i++){
//            if (i == positionOfCurrentlyHighlightedCompany){
//                companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(Color.CYAN);
//            }
//            else companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(getResources().getColor(R.color.background));
//        }
//

        companyRecyclerViewArrayAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = companyRecyclerView.getChildViewHolder(v).getAdapterPosition();
                Control.CONTROL.setCompanySelectedInRecyclerView(Control.CONTROL.getSingleDayViewCompanies().get(pos));

                //This updates the price stored in currentOrder and displayed on the screen to reflect selection
                updatePriceAndAddToTextViewAndCurrentOrder();

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
    This finds the current price without a permit by getting the price of the selected skip company's
    skips of the user-selected size, then multiplying it by how many they want.
    It then checks if a permit is required (i.e. the checkbox about the skip being on a public
    highway is checked). If one is required, the price of the permit is added.
    This will differ per region as each local council is different.
    It then updates the current order to have the price of the current selection stored. If the
    user changes their mind it will change, but if not this will be the price they pay if they
    confirm their order.
    It then updates the price/subtotal TextView to reflect the user's selection.
     */
    private void updatePriceAndAddToTextViewAndCurrentOrder(){
        if (Control.CONTROL.getCompanySelectedInRecyclerView() == null){
            return;
        }
        //This stops the app crashing if no company is selected, and leaves the price unchanged

        double priceDouble = Control.CONTROL.getCompanySelectedInRecyclerView().getDefaultPriceDifferentDependingOnSkipType(skipType)
                * Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered();

        //TODO make a separate amount for each council, depending on which postcode the order is going to
        if(permitCheckBox.isChecked()){
            priceDouble += 20;
            permitRequired = true;
        } else {
            permitRequired = false;
        }

        Control.CONTROL.getCurrentOrder().setPrice(priceDouble);
        Control.CONTROL.getCurrentOrder().setPermitRequired(permitRequired);

        String priceString = "£" + priceDouble + "0";
        priceSubtotal.setText(priceString);
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
