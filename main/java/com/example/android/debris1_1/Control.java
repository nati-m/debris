package com.example.android.debris1_1;

import com.example.android.debris1_1.backend.Company;
import com.example.android.debris1_1.backend.Order;
import com.example.android.debris1_1.backend.PublicUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Paaat VII on 12/03/2018.
 *
 *
 * There is ONE instance of this class called CONTROL (all caps).
 * It contains the constants for e.g. which user is logged in or which company is being ordered from at this moment.
 * It resets upon startup, though later in development logins will be allowed to be constant (stay signed in),
 * and in this case the instance of this class will begin with the current user.
 */

public class Control {

    protected static Control CONTROL = new Control();

    private PublicUser currentUser;
    private Order currentOrder;
    private ArrayList<Company> currentCompanies;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;

    private Control(){
        currentCompanies = new ArrayList<>();
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("EEE d MMMM yyyy");
    }


    protected void setCurrentUser(PublicUser publicUser){
        currentUser = publicUser;
    }

    protected void setCurrentOrder(Order order){
        currentOrder = order;
    }

    /*
    This adds a company object to the currentCompanies ArrayList.
    This could also be achieved by typing CONTROL.getCurrentCompanies().add(newCompanyObject),
    but this method is just here as a shortcut.
     */
    protected void addCompanyToCurrentCompaniesArrayList(Company company){
        currentCompanies.add(company);
    }



    protected PublicUser getCurrentUser(){
        return currentUser;
    }

    protected Order getCurrentOrder(){
        return currentOrder;
    }

    /*
    This returns the address the order will be sent to as a String
    The String will include 1 or 2 address lines and a postcode.
    The String is presented with each piece of the address on a separate line.
     */
    protected String getCurrentOrderAddressAsString(){
        String toReturn;
        toReturn = currentOrder.getAddressLine1();

        //Address line 2 is not required, this just adds it if it isn't "". Shouldn't be null but check.
        if(!currentOrder.getAddressLine2().equals("")){
            toReturn += "\n" + currentOrder.getAddressLine2();
        }

        toReturn += "\n" + currentOrder.getPostCode();

        return toReturn;
    }

    protected String getDateAsAStringInFormat08NOV2018(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        String toReturn= simpleDateFormat.format(c.getTime());
        return toReturn;
    }

    protected ArrayList<Company> getCurrentCompanies(){
        return currentCompanies;
    }

    protected SimpleDateFormat getSimpleDateFormat(){
        return simpleDateFormat;
    }

    protected Calendar calendar(){
        return calendar;
    }

}
