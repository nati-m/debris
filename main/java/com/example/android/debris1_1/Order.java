package com.example.android.debris1_1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class Order {

    //private static final Map<OrderID, Order> ORDERS_AND_THEIR_IDS = new HashMap<>();
    //Now we're using databases this won't work but it's here as a reminder


    private String addressLine1;
    private String addressLine2;
    private String postcode;
    //private Date date;
    private Calendar dateOfSkipArrival;
    private PublicUser user;
    private double price = -999;
    private Company skipCo;
    private boolean permitRequired;
    //private Permit permit;
    private Council localCouncil;
    private ArrayList<Skip> skipsOrdered = new ArrayList<>();
    private Calendar dateSkipWillBePickedUp = null;
    private UserFeedback userFeedback;
    private int buttonTypeForTrackOrders = -999;
    private int parentTypeForExpandableListView;
    private int childTypeForExpandableListView;
    private String orderID = "ORD999999999";

    //These are used in constructing the "track your orders" page, to choose what the button should do.
    public static final int BUTTON_TYPE_EDIT_ORDER_ONLY_BEFORE_SKIP_IS_DELIVERED = 1;
    public static final int BUTTON_TYPE_CHOOSE_DATE_OF_SKIP_PICKUP = 2;
    public static final int BUTTON_TYPE_EDIT_DATE = 3;
    public static final int BUTTON_TYPE_LEAVE_FEEDBACK_NOW = 4;
    public static final int BUTTON_TYPE_VIEW_FEEDBACK = 5;
    public static final int BUTTON_TYPE_NO_BUTTON = 0;

    //Choose Date of Skip Pickup, Edit Date of Skip Pickup, Leave Feedback Now, View Feedback


    //private Company haulCo;
    //private Company tipCo;

    public Order (PublicUser user){
        this.user = user;
    }


    public Order (PublicUser user, String addressLine1, String addressLine2, String postcode, ArrayList<Skip> skipsOrdered, Calendar calendarWithDateOfSkipArrival){
        this.user = user;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2; //This may be "" but won't be null.
        this.postcode = postcode;
        this.skipsOrdered = skipsOrdered;
        dateOfSkipArrival = calendarWithDateOfSkipArrival;
    }

    public Order (PublicUser user, String addressLine1, String addressLine2, String postcode, ArrayList<Skip> skipsOrdered, Calendar calendarWithDateOfSkipArrival, Calendar optionalCalenderWithDateSkipPickedUp){
        this.user = user;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2; //This may be "" but won't be null.
        this.postcode = postcode;
        this.skipsOrdered = skipsOrdered;
        dateOfSkipArrival = calendarWithDateOfSkipArrival;
        dateSkipWillBePickedUp = optionalCalenderWithDateSkipPickedUp;
    }



    protected void setChosenSkipCo(Company company){
        skipCo = company;
    }

    protected void setifPermitRequired(boolean permitRequired){
        this.permitRequired = permitRequired;
    }

    protected void setPrice(double price){
        this.price = price;
    }



    protected void setDateOfSkipArrival(Calendar newDateOfSkipArrival){
        dateOfSkipArrival = newDateOfSkipArrival;
    }


    public String getAddressLine1(){
        return addressLine1;
    }

    public String getAddressLine2(){
        return addressLine2;
    }

    public String getPostCode(){
        return postcode;
    }

    public ArrayList<Skip> getSkipsOrderedArrayList(){
        return skipsOrdered;
    }

    public Calendar getDateOfSkipArrival(){
        return dateOfSkipArrival;
    }

    public String getDateOfSkipArrivalAsAString(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        String toReturn = simpleDateFormat.format(dateOfSkipArrival.getTime());

        return toReturn;
    }

    public Company getCompany(){
        return skipCo;
    }

    public double getPrice(){
        return price;
    }

    public Calendar getDateSkipWillBePickedUp() {
        return dateSkipWillBePickedUp;
    }

    public void setDateSkipWillBePickedUp(Calendar dateSkipWillBePickedUp) {
        this.dateSkipWillBePickedUp = dateSkipWillBePickedUp;
    }

    public UserFeedback getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(UserFeedback userFeedback) {
        this.userFeedback = userFeedback;
    }

    public int getButtonTypeForTrackOrders() {
        return buttonTypeForTrackOrders;
    }

    public void setButtonTypeForTrackOrders(int buttonTypeForTrackOrders) {
        this.buttonTypeForTrackOrders = buttonTypeForTrackOrders;
    }

    public static Comparator<Order> dateLatestFirstComparator = new Comparator<Order>() {
        @Override
        public int compare(Order order1, Order order2) {
            //double compareDouble = c1.getRating() - c2.getRating();
            long compareDouble = order1.getDateOfSkipArrival().getTimeInMillis() - order2.getDateOfSkipArrival().getTimeInMillis();
            if (compareDouble > 0) {
                return -1;
            } else {
                return 1;
            }
        }
    };

    public static Comparator<Order> dateEarliestFirstComparator = new Comparator<Order>() {
        @Override
        public int compare(Order order1, Order order2) {
            //double compareDouble = c1.getRating() - c2.getRating();
            long compareDouble = order1.getDateOfSkipArrival().getTimeInMillis() - order2.getDateOfSkipArrival().getTimeInMillis();
            if (compareDouble > 0) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    public int getParentTypeForExpandleListView() {
        return parentTypeForExpandableListView;
    }

    public void setParentTypeForExpandleListView(int parentTypeForExpandleListView) {
        this.parentTypeForExpandableListView = parentTypeForExpandleListView;
    }

    public int getChildTypeForExpandableListView() {
        return childTypeForExpandableListView;
    }

    public void setChildTypeForExpandableListView(int childTypeForExpandableListView) {
        this.childTypeForExpandableListView = childTypeForExpandableListView;
    }

    //As only skips of the same type can be ordered at at least 1 is always ordered, the skip
    //returned will be indicative of all the skips ordered
    public Skip getSkipType(){
        return skipsOrdered.get(0);
    }

    public int getNumberOfSkipsOrdered(){
        return skipsOrdered.size();
    }

    public void setSkipsOrdered(ArrayList<Skip> skipsOrdered){
        this.skipsOrdered.clear();
        this.skipsOrdered.addAll(skipsOrdered);
    }

    public String getOrderID(){
        return orderID;
    }


}
