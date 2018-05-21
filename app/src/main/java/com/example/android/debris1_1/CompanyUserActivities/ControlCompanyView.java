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

    }

    protected static ControlCompanyView getINSTANCE(){
        return INSTANCE;
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
