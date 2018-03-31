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
    private SimpleDateFormat dayOfWeek;
    private ArrayList<Order> ordersFromThisUser;

    private Control(){
        currentCompanies = new ArrayList<>();
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        dayOfWeek = new SimpleDateFormat("EE dd MMM yyyy");
        initCompanyDummyData();
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

    public ArrayList<Order> getOrdersFromThisUser() {
        return ordersFromThisUser;
    }

    public void setOrdersFromThisUser(ArrayList<Order> ordersFromThisUser) {
        this.ordersFromThisUser = ordersFromThisUser;
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

    protected String getDateAsAStringInFormatWed18JUN2018(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        String toReturn =  dayOfWeek.format(c.getTime());
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

    /*
    This creates 9 fake companies and adds them to the currentCompanies ArrayList.
    It first empties the ArrayList to stop the possibility of the same companies being added twice.
     */
    protected void initCompanyDummyData(){
        currentCompanies = new ArrayList<>();

        Company company1 = new Company("Jimmy Skips", "NE4 5AA", "jimmyskips@gmail.com", "0191 567392", 120, 50, 20, 0, 0);
        Company company2 = new Company("Biffa", "NE1 1DF", "biffa@biffa.com", "0800 200 300", 160, 100, 100, 50, 10);
        Company company3 = new Company("Fenham Skips", "NE4 9EP", "fenhamskips@mail.com", "0191 493475", 140, 20, 0, 0, 0);
        Company company4 = new Company("Ryton SkipCo", "NE40 3AH", "rightonryton@hotmail.co.uk", "0191 276 358", 170, 50, 50, 50, 50);
        Company company5 = new Company("Yellow Skips South Shields", "NE33 5RT", "yellowskipssouthshields@mail.com", "0191 988 429", 200, 40, 0, 30, 5);
        Company company6 = new Company("Expensive Skips", "NE8 3BE", "expensiveskips@gmail.com", "0191 44 55 66", 225, 50, 50, 30, 0);
        Company company7 = new Company("Taylor SkipCo", "NE27 0FG", "taylorskipgroup@aol.com", "0191 743 599", 150, 25, 10, 5, 5);
        Company company8 = new Company("Heavy Duty Skips", "NE34 6ET", "heavyskips@gmail.com", "0191 11 00 11", 170, 100, 0, 0, 0);
        Company company9 = new Company("Yoho Pirate Skips Alnwick", "NE66 2PN", "yohoskips@mail.com", "0191 526 1819", 190, 20, 20, 20, 0);

        company1.setRating(2.2); company2.setRating(3.4); company3.setRating(4.4); company4.setRating(3.8); company5.setRating(4.5); company6.setRating(4.2); company7.setRating(1.8); company8.setRating(3.9); company9.setRating(4);

        currentCompanies.add(company1); currentCompanies.add(company2); currentCompanies.add(company3); currentCompanies.add(company4); currentCompanies.add(company5); currentCompanies.add(company6); currentCompanies.add(company7); currentCompanies.add(company8); currentCompanies.add(company9);
    }

}
