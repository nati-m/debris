package com.example.android.debris1_1.CompanyUserActivities;

import com.example.android.debris1_1.Order;
import com.example.android.debris1_1.PublicUser;
import com.example.android.debris1_1.Skip;

import java.util.ArrayList;
import java.util.Calendar;

public class ControlCompanyView {

    private static ControlCompanyView INSTANCE = new ControlCompanyView();

    private ArrayList<Order> currentOrders;
    private ArrayList<Order> confirmedOrdersByThisCompany;
    private ArrayList<Order> declinedOrders;




    private ControlCompanyView(){
        currentOrders = new ArrayList<>();
        confirmedOrdersByThisCompany = new ArrayList<>();
        declinedOrders = new ArrayList<>();

        initDummyOrders();
    }

    protected static ControlCompanyView getINSTANCE(){
        return INSTANCE;
    }

    private void initDummyOrders(){

        PublicUser dummyUser1 = new PublicUser("Mrs Bloggs", "NE4 5AB", "hello@mrsbloggs.com", new ArrayList<Order>());

        ArrayList<Skip> dummyArrayList1 = new ArrayList<>();
        dummyArrayList1.add(Skip.getMaxiSkip()); dummyArrayList1.add(Skip.getMaxiSkip()); dummyArrayList1.add(Skip.getMaxiSkip());

        ArrayList<Skip> dummyArrayList2 = new ArrayList<>();
        dummyArrayList2.add(Skip.getDumpyBag());

        ArrayList<Skip> dummyArrayList3 = new ArrayList<>();
        dummyArrayList3.add(Skip.getMidiSkip()); dummyArrayList3.add(Skip.getMidiSkip());

        ArrayList<Skip> dummyArrayList4 = new ArrayList<>();
        dummyArrayList4.add(Skip.getMaxiSkip());

        Calendar skipArrival3DaysFromNow = Calendar.getInstance();
        skipArrival3DaysFromNow.add(Calendar.DAY_OF_YEAR, 3);

        Calendar skipArrivalIn10Days = Calendar.getInstance();
        skipArrivalIn10Days.add(Calendar.DAY_OF_YEAR, 10);

        Calendar skipArrivalIn6Months = Calendar.getInstance();
        skipArrivalIn6Months.add(Calendar.MONTH, 6);

        Calendar skipArrivalTomorrow = Calendar.getInstance();
        skipArrivalTomorrow.add(Calendar.DAY_OF_YEAR, 1);

        Calendar skipPickedUp10DaysAfter6Months = Calendar.getInstance();
        skipPickedUp10DaysAfter6Months.add(Calendar.MONTH, 6);
        skipArrivalIn10Days.add(Calendar.DAY_OF_YEAR, 10);


        Order dummyOrder1 = new Order("1 Willow Street", "", "NE4 5AB", dummyArrayList1, skipArrivalIn10Days);
        Order dummyOrder2 = new Order("5 Windsor Road", "Fenham", "NE4 9EN", dummyArrayList2, skipArrival3DaysFromNow);
        Order dummyOrder3 = new Order("1 Willow Street", "", "NE4 5AB", dummyArrayList3, skipArrivalIn6Months, skipArrivalIn10Days);
        Order dummyOrder4 = new Order("5 Windsor Road", "Fenham", "NE4 9EN", dummyArrayList4, skipArrivalTomorrow, skipPickedUp10DaysAfter6Months);

        currentOrders.add(dummyOrder1); currentOrders.add(dummyOrder2); currentOrders.add(dummyOrder3); currentOrders.add(dummyOrder4);

    }

    protected ArrayList<Order> getCurrentOrders(){
        return currentOrders;
    }

    protected ArrayList<Order> getConfirmedOrdersByThisCompany() {
        return confirmedOrdersByThisCompany;
    }

    protected ArrayList<Order> getDeclinedOrders() {
        return declinedOrders;
    }

}
