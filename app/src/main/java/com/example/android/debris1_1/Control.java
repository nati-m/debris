package com.example.android.debris1_1;

import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

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

    public static Control CONTROL = new Control();

    //FIREBASE
    public static final int RC_SIGN_IN = 777; //For use in Firebase Database
    //Firebase Auth variables
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    //Firebase Database variables
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersDatabaseReference;
    private ChildEventListener childEventListener;


    private PublicUser currentUser;
    private Order currentOrder;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat dayOfWeek;

    protected static final int VAT_PERCENTAGE = 20;
    protected static final double VAT_REMOVAL_PERCENTAGE = 83.33;
    //To remove VAT from a price that already includes it, you can't remove 20% of the new price as this is too much.
    //The price is 120% of what it would be without VAT. Hence, multiply by this number and divide by 100.

    private Control(){
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.UK);
        dayOfWeek = new SimpleDateFormat("EE dd MMM yyyy", Locale.UK);
    }

   public void setCurrentUser(PublicUser publicUser){
        currentUser = publicUser;
    }

    protected void setCurrentOrder(Order order){
        currentOrder = order;
    }

    /*
    This method removes the VAT from a double amount.
    The VAT_REMOVAL_PERCENTAGE in Control must be changed manually if VAT is no longer 20%.
    It also rounds the answer to two decimal places.
     */
    protected double removeVAT(double fromThisDouble){
        fromThisDouble = fromThisDouble * Control.VAT_REMOVAL_PERCENTAGE / 100;
        fromThisDouble = Math.round(fromThisDouble * 1.00d); //This rounds the price to 2 decimal places
        return fromThisDouble;
    }


    public ArrayList<Order> getOrdersFromThisUser() {
        return currentUser.getThisUsersOrders();
    }

    public void setOrdersFromThisUser(ArrayList<Order> ordersFromThisUser) {
        currentUser.setThisUsersOrders(ordersFromThisUser);
    }

    public PublicUser getCurrentUser(){
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

    protected String getDateAsAStringInFormatWed18JUN2018(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        String toReturn =  dayOfWeek.format(c.getTime());
        return toReturn;
    }



    protected SimpleDateFormat getSimpleDateFormat(){
        return simpleDateFormat;
    }

    protected Calendar calendar(){
        return calendar;
    }

    /*
    This changes the format of a postcode String into an upper case postcode with a space in the correct
    place and no space at the end.
     */
    protected String formatPostcode(String postcode){
        //CHANGE THE FORMAT OF POSTCODE TO UPPER CASE AND WITH A SPACE IN THE MIDDLE
        postcode = postcode.toUpperCase(); //sets the postcode to upper case

        //removes a space from the end of the postcode if there is one
        if(postcode.charAt(postcode.length() -1) == ' '){
            postcode = postcode.substring(0, postcode.length() - 1);
        }
        if(postcode.charAt(postcode.length() -1) == ' '){
            postcode = postcode.substring(0, postcode.length() - 1);
        }

        //adds a space in middle of postcode if there isn't one
        if(!postcode.contains(" ")){
            int spaceNeeded = postcode.length() - 3;
            //As postcodes are not a standard length, but the last part is always three characters,
            //this finds where the space should be, 3 characters from the end

            ArrayList<String> postcodeParts = new ArrayList<>(); //There will only be two
            for (int i = 0; i<postcode.length(); i+=spaceNeeded){
                postcodeParts.add(postcode.substring(i, Math.min(postcode.length(), i + spaceNeeded)));
            }

            postcode = postcodeParts.get(0) + " " + postcodeParts.get(1); //This puts the pieces back together again with a space in the middle
        }

        return postcode;
    }

    /*
    This formats a double into a usual money format, e.g. 23.108 returns £23.11, or 9 returns £9.00
     */
    public String moneyFormat(double amount){
        return "£" + String.format(Locale.UK, "%.2f", amount );
    }

    /*
   When called this function scrolls to the bottom of the ScrollView added as an argument.
   It is used when text is added to (or taken away from) a page, it will be in focus at the bottom of the ScrollView
    */
    protected void focusScrollViewToBottom(ScrollView scrollView){

        final ScrollView scrollView1 = scrollView;

        scrollView1.post(new Runnable() {
            @Override
            public void run() {
                scrollView1.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }




}
