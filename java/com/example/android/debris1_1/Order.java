package com.example.android.debris1_1;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class Order {

    //private static final Map<OrderID, Order> ORDERS_AND_THEIR_IDS = new HashMap<>();
    //Now we're using databases this won't work but it's here as a reminder


    private String addressLine1;
    private String addressLine2;
    private String postCode;
    private Calendar dateOfSkipArrival;
    private double price = -999;
    private Company skipCo;
    private boolean permitRequired;
    private ArrayList<Skip> skipsOrdered = new ArrayList<>();
    private Calendar dateOfSkipCollection = null;
    private int buttonTypeForTrackOrders = -999;


    //For Firebase:
    private String dateOfSkipArrivalString;
    private String dateOfSkipCollectionString;
    private String skipTypeString;
    private int numberOfSkipsOrdered;
    private boolean collectionDateSpecified = false;
    private String userUID;
    private String companyUID;
    private String orderUIDakaFirebaseDatabaseKey;

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");

    //THINGS FIREBASE WILL DO INSTEAD
    //private UserFeedback userFeedback;
    //private String orderID = "ORD999999999";
    //private Permit permit;
    //private Council localCouncil;

    //These are used in constructing the "track your orders" page, to choose what the button should do.
    public static final int BUTTON_TYPE_EDIT_ORDER_ONLY_BEFORE_SKIP_IS_DELIVERED = 1;
    public static final int BUTTON_TYPE_CHOOSE_DATE_OF_SKIP_PICKUP = 2;
    public static final int BUTTON_TYPE_EDIT_DATE = 3;
    public static final int BUTTON_TYPE_LEAVE_FEEDBACK_NOW = 4;
    public static final int BUTTON_TYPE_VIEW_FEEDBACK = 5;
    public static final int BUTTON_TYPE_NO_BUTTON = 0;



    public Order (){ } //So Firebase Database works


    public Order (String addressLine1, String addressLine2, String postCode, ArrayList<Skip> skipsOrdered, Calendar calendarWithDateOfSkipArrival){
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2; //This may be "" but won't be null.
        this.postCode = postCode;
        this.skipsOrdered = skipsOrdered;
        numberOfSkipsOrdered = skipsOrdered.size();
        skipTypeString = skipsOrdered.get(0).getSkipTypeAsSimplerString();
        dateOfSkipArrival = calendarWithDateOfSkipArrival;
        dateOfSkipArrivalString = simpleDateFormat.format(dateOfSkipArrival.getTime());
        collectionDateSpecified = false;
        userUID = Control.CONTROL.getCurrentUser().getFirebaseUid();
    }

    public Order (String addressLine1, String addressLine2, String postCode, ArrayList<Skip> skipsOrdered, Calendar calendarWithDateOfSkipArrival, Calendar optionalCalenderWithDateSkipPickedUp){
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2; //This may be "" but won't be null.
        this.postCode = postCode;
        this.skipsOrdered = skipsOrdered;
        numberOfSkipsOrdered = skipsOrdered.size();
        skipTypeString = skipsOrdered.get(0).getSkipTypeAsSimplerString();
        dateOfSkipArrival = calendarWithDateOfSkipArrival;
        dateOfSkipArrivalString = simpleDateFormat.format(dateOfSkipArrival.getTime());
        dateOfSkipCollection = optionalCalenderWithDateSkipPickedUp;
        dateOfSkipCollectionString = simpleDateFormat.format(dateOfSkipCollection.getTime());
        collectionDateSpecified = true;
        userUID = Control.CONTROL.getCurrentUser().getFirebaseUid();
    }

    //The constructor for Firebase
    public Order (String addressLine1, String addressLine2, boolean collectionDateSpecified, String dateOfSkipArrivalString, int numberOfSkipsOrdered, String postCode, double price, String skipTypeString, String userUID, String companyUID, boolean permitRequired) throws ParseException {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2; //This may be "" but won't be null.
        this.postCode = postCode;
        this.collectionDateSpecified = collectionDateSpecified;
        this.numberOfSkipsOrdered = numberOfSkipsOrdered;
        this.dateOfSkipArrivalString = dateOfSkipArrivalString;
        this.price = price;
        this.skipTypeString = skipTypeString;
        this.userUID = userUID;
        this.companyUID = companyUID;
        this.permitRequired = permitRequired;
        //TODO make setters for things that are null - firebase seems to use blank constructors then setters!
        skipsOrdered = new ArrayList<>();

        Skip skip = Skip.MAXI_SKIP;

        if (skipTypeString == "MIDI"){
            skip = Skip.MIDI_SKIP;
        }

        if (skipTypeString == "MINI"){
            skip = Skip.MINI_SKIP;
        }

        if (skipTypeString == "SKIPBAG"){
            skip = Skip.DUMPY_BAG;
        }

        for (int i = 0; i < numberOfSkipsOrdered; i++){
            skipsOrdered.add(skip);
        }

        parseArrivalDate(dateOfSkipArrivalString);
    }

    private void parseArrivalDate(String date) throws ParseException {
        Date skipArrival = simpleDateFormat.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(skipArrival);
        dateOfSkipArrival = c;
    }

    protected void setChosenSkipCo(Company company){
        skipCo = company;
    }

    protected void setPermitRequired(boolean permitRequired){
        this.permitRequired = permitRequired;
    }

    public boolean getPermitRequired(){
        return permitRequired;
    }

    protected void setPrice(double price){
        this.price = price;
    }

    public String getAddressLine1(){
        return addressLine1;
    }

    public String getAddressLine2(){
        return addressLine2;
    }

    public String getPostCode(){
        return postCode;
    }

    @Exclude //This stops firebase trying to take an ArrayList of Skips into its database and crashing it
    protected ArrayList<Skip> getSkipsOrderedArrayList(){
        return skipsOrdered;
    }

    @Exclude //This stops firebase trying to take a Calender into its database and crashing it
    protected Calendar getDateOfSkipArrival(){
        return dateOfSkipArrival;
    }

    @Exclude
    protected Company getCompany(){
        return skipCo;
    }

    public double getPrice(){
        return price;
    }

    @Exclude
    protected Calendar getDateOfSkipCollection() {
        return dateOfSkipCollection;
    }

    protected void setDateOfSkipArrival(Calendar newDateOfSkipArrival){
        dateOfSkipArrival = newDateOfSkipArrival;
        dateOfSkipArrivalString = simpleDateFormat.format(dateOfSkipArrival.getTime());
    }

    protected void setDateOfSkipCollection(Calendar dateSkipWillBePickedUp) {
        this.dateOfSkipCollection = dateSkipWillBePickedUp;
        dateOfSkipCollectionString = simpleDateFormat.format(dateOfSkipCollection.getTime());
        collectionDateSpecified = true;
    }

    @Exclude
    protected int getButtonTypeForTrackOrders() {
        return buttonTypeForTrackOrders;
    }

    public void setButtonTypeForTrackOrders(int buttonTypeForTrackOrders) {
        this.buttonTypeForTrackOrders = buttonTypeForTrackOrders;
    }

    @Exclude
    protected static Comparator<Order> dateLatestFirstComparator = new Comparator<Order>() {
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

    @Exclude
    protected static Comparator<Order> dateEarliestFirstComparator = new Comparator<Order>() {
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


    //As only skips of the same type can be ordered at at least 1 is always ordered, the skip
    //returned will be indicative of all the skips ordered
    @Exclude
    protected Skip getSkipType(){
        return skipsOrdered.get(0);
    }

    protected void setSkipsOrdered(ArrayList<Skip> skipsOrdered){
        this.skipsOrdered.clear();
        this.skipsOrdered.addAll(skipsOrdered);
    }

    public String getDateOfSkipArrivalString() {
        return dateOfSkipArrivalString;
    }

    public String getDateOfSkipCollectionString() {
        return dateOfSkipCollectionString;
    }

    public String getSkipTypeString(){
        return skipTypeString;
    }

    public int getNumberOfSkipsOrdered(){
        return numberOfSkipsOrdered;
    }

    public boolean getCollectionDateSpecified(){
        return collectionDateSpecified;
    }

    public String getUserUID(){
        return userUID;
    }

    public void setDateOfSkipArrivalString(String dateOfSkipArrivalString) throws ParseException {
        this.dateOfSkipArrivalString = dateOfSkipArrivalString;
        //parseArrivalDate(dateOfSkipArrivalString);
    }

    public void setDateOfSkipCollectionString(String dateOfSkipCollectionString){
        this.dateOfSkipCollectionString = dateOfSkipCollectionString;
        collectionDateSpecified = true;
    }

    public void setSkipType(String skipTypeString){
        this.skipTypeString = skipTypeString;

        Skip skip = Skip.MAXI_SKIP;

        if (skipTypeString == "MIDI"){
            skip = Skip.MIDI_SKIP;
        }

        if (skipTypeString == "MINI"){
            skip = Skip.MINI_SKIP;
        }

        if (skipTypeString == "SKIPBAG"){
            skip = Skip.DUMPY_BAG;
        }

        skipsOrdered = new ArrayList<>();
        for (int i = 0; i < numberOfSkipsOrdered; i++){
            skipsOrdered.add(skip);
        }
    }

    public String getCompanyUID(){
        return companyUID;
    }

    public void setCompanyUID(String companyUID){
        this.companyUID = companyUID;
    }


    public String getOrderUIDakaFirebaseDatabaseKey() {
        return orderUIDakaFirebaseDatabaseKey;
    }

    public void setOrderUIDakaFirebaseDatabaseKey(String orderUIDakaFirebaseDatabaseKey) {
        this.orderUIDakaFirebaseDatabaseKey = orderUIDakaFirebaseDatabaseKey;
    }


}
