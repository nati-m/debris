package com.example.android.debris1_1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static android.graphics.Color.GREEN;


/**
 * Created by Paaat VII on 31/03/2018.
 */

public class ExpandableListTrackOrdersAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Order> ordersArrayList;
    private HashMap<String, ArrayList<Order>> listHashMap;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");

    public ExpandableListTrackOrdersAdapter(Context context, ArrayList<Order> ordersArrayList, HashMap<String, ArrayList<Order>> listHashMap) {
        this.context = context;
        this.ordersArrayList = ordersArrayList;
        this.listHashMap = listHashMap;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        //String headerTitle = (String) getGroup(groupPosition);
        Order currentOrder = (Order) getGroup(groupPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_view_track_orders, null);
        }

        TextView numberAndTypeOfSkipsOrdered = convertView.findViewById(R.id.number_and_type_of_skips_track_order_expandable_list);
        TextView dateOfDeliveryOrPickUp = convertView.findViewById(R.id.date_of_delivery_or_picked_up_track_order_expandable_list);

        String numberTypeSkipsAndPostcode = currentOrder.getNumberOfSkipsOrdered() + " x " + currentOrder.getSkipTypeString() + "\nto " + currentOrder.getPostCode();
        numberAndTypeOfSkipsOrdered.setText(numberTypeSkipsAndPostcode);

//        String dateOfDeliveryOrPickUpAndRelevantMessage = sortOutDateOfDeliveryOrPickUpRelevantMessageParentView(currentOrder, dateOfDeliveryOrPickUp);
//        dateOfDeliveryOrPickUp.setText(dateOfDeliveryOrPickUpAndRelevantMessage);

        //TODO this is a temp test thing
        String date = "Arrive " + currentOrder.getDateOfSkipArrivalString();
        dateOfDeliveryOrPickUp.setText(date);


        return convertView;
    }

    private String sortOutDateOfDeliveryOrPickUpRelevantMessageParentView(Order order, TextView textView){
        String message;
        Calendar c = Calendar.getInstance(); //This contains today's date

        //order.setParentTypeForExpandleListView(1); //1 means red text. It'll change to 2 if it's green text.

        //If the skip is being delivered today
        if(c.getTime() == order.getDateOfSkipArrival().getTime()){
            message = "DUE TO ARRIVE TODAY";
            return message;
        }

        //If the skip(s) have not yet been delivered
        if(c.before(order.getDateOfSkipArrival())){
            message = "DUE TO ARRIVE\n" + simpleDateFormat.format(order.getDateOfSkipArrival().getTime());
            return message;
        }

        //If the skip(s) have not yet been collected and the user has not chosen the date of collection
        if(!order.getCollectionDateSpecified()){
            message = "ARRIVED.\nCHOOSE SKIP\nPICKUP DATE";
            return message;
        }

        //If the skip is being collected today
        if(c.getTime() == order.getDateOfSkipCollection().getTime()){
            message = "COLLECTION\nTODAY";
            return message;
        }

        //If the skip(s) have not yet been collected and the user has chosen the date of collection
        if(c.before(order.getDateOfSkipCollection())){
            message = "COLLECTION ON\n" + simpleDateFormat.format(order.getDateOfSkipCollection().getTime());
            return message;
        }

        //if the order has been completed
        message = "ORDER\nCOMPLETED";
        //order.setParentTypeForExpandleListView(2);
        //textView.setTextColor(GREEN);
        //This is commented out for now as it was causing the wrong text to become green and the reason
        //could not be found

        return message;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Order currentOrder = (Order) getGroup(groupPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expanded_section_expandle_list_view_track_orders, null);
        }



        Button buttonOfManyFunctions = convertView.findViewById(R.id.button_track_orders_expandable_list_view_child);

        String message = sortOutWhichChildViewMessageIsNeeded(currentOrder);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.status_message_expandable_list_view_child_track_orders);
        messageTextView.setText(message);

//        if(currentOrder.getButtonTypeForTrackOrders() == Order.BUTTON_TYPE_NO_BUTTON){
//            buttonOfManyFunctions.setVisibility(View.GONE);
//        } else {
//            View.OnClickListener buttonOnClickListener;
//            buttonOnClickListener = sortOutWhichChildViewButtonIsNeeded(currentOrder, buttonOfManyFunctions);
//            //This sets the correct text on the button too
//        }


        return convertView;
    }



    private View.OnClickListener sortOutWhichChildViewButtonIsNeeded(Order order, Button button){
        View.OnClickListener toReturn;

        if(order.getButtonTypeForTrackOrders() == Order.BUTTON_TYPE_EDIT_ORDER_ONLY_BEFORE_SKIP_IS_DELIVERED){
            button.setText("Edit\nOrder");
            toReturn = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextPageIntent = new Intent(v.getContext(), EditOrderActivity.class);
                    context.startActivity(nextPageIntent);
                }
            };
            return toReturn;
        }

        if(order.getButtonTypeForTrackOrders() == Order.BUTTON_TYPE_CHOOSE_DATE_OF_SKIP_PICKUP){
            button.setText("Choose\nDate");
            toReturn = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextPageIntent = new Intent(v.getContext(), EditOrderActivity.class);
                    context.startActivity(nextPageIntent);

                    //TODO
                }
            };
            return toReturn;
        }

        if(order.getButtonTypeForTrackOrders() == Order.BUTTON_TYPE_EDIT_DATE){
            button.setText("Edit\nDate");
            toReturn = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextPageIntent = new Intent(v.getContext(), EditOrderActivity.class);
                    context.startActivity(nextPageIntent);

                    //TODO
                }
            };
            return toReturn;
        }

        if(order.getButtonTypeForTrackOrders() == Order.BUTTON_TYPE_LEAVE_FEEDBACK_NOW){
            button.setText("Leave\nFeedback");
            toReturn = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextPageIntent = new Intent(v.getContext(), LeaveFeedbackActivity.class);
                    context.startActivity(nextPageIntent);
                }
            };
            return toReturn;
        }

        if(order.getButtonTypeForTrackOrders() == Order.BUTTON_TYPE_VIEW_FEEDBACK){
            button.setText("View\nFeedback");
            toReturn = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextPageIntent = new Intent(v.getContext(), EditOrderActivity.class);
                    context.startActivity(nextPageIntent);

                    //TODO
                }
            };
            return toReturn;
        }









        return null;
    }


    /*
    This sorts out which of the several messages a tracked order can display.
    This is based on the date the skip is/was delivered, picked up, whether the user has left feedback and
    whether the time allocated for feedback (2 months after the skip is picked up) has ended.
    It also sets an int buttonTypeForTrackOrders to a certain value in the Order, to signal what the
    button in the child view will do -
    Edit Order (before skip(s) are delivered), Choose Date of Skip Pickup, Edit Date of Skip Pickup,
    Leave Feedback Now, View Feedback, No Button
     */
    private String sortOutWhichChildViewMessageIsNeeded(Order order) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -1);
        //For some reason this is necessary to make all of the dates sync up.
        //It may have something to do with a timezone or comparison error but nevertheless
        //it makes everything work as it should so is a good patch for now.

        Calendar skipArrival = order.getDateOfSkipArrival();
        String message;

        String skipOrSkips;
        String skipIsOrSkipsAre;
        String skipWasOrSkipsWere;
        if (order.getSkipsOrderedArrayList().size() == 1) {
            skipOrSkips = "skip";
            skipIsOrSkipsAre = "skip is";
            skipWasOrSkipsWere = "skip was";
        } else {
            skipOrSkips = "skips";
            skipIsOrSkipsAre = "skips are";
            skipWasOrSkipsWere = "skips were";
        }

        //If the skip is due to arrive today
        if (c.getTime() == skipArrival.getTime()) {
            message = "Your " + skipIsOrSkipsAre + " arriving today.";
            order.setButtonTypeForTrackOrders(Order.BUTTON_TYPE_NO_BUTTON);
            return message;
        }

        //If the skip is due to arrive in the future.
        //Should include something like order.HASARRIVED() boolean in future versions
        if (c.before(skipArrival)) {
            long daysBetweenInMilliseconds = skipArrival.getTimeInMillis() - c.getTimeInMillis();
            int days = (int) TimeUnit.DAYS.convert(daysBetweenInMilliseconds, TimeUnit.MILLISECONDS);
            //if this doesn't work use something like float days = (daysBetweenInMilliseconds / (1000*60*60*24));
            if (days == 0) {
                message = "Your " + skipIsOrSkipsAre + " arriving today.";
            } else if (days == 1) {
                message = "Your " + skipIsOrSkipsAre + " due to arrive tomorrow.";
            } else {
                message = "Your " + skipIsOrSkipsAre + " due to arrive in " + days + " days.";
            }
            order.setButtonTypeForTrackOrders(Order.BUTTON_TYPE_EDIT_ORDER_ONLY_BEFORE_SKIP_IS_DELIVERED);
            return message;
        }

        //if the skip has been delivered but a pick up date has not been set by the user.
        if (!order.getCollectionDateSpecified()) {
            Calendar TwoWeeksAfterSkipArrival = Calendar.getInstance();
            TwoWeeksAfterSkipArrival.setTime(skipArrival.getTime());
            TwoWeeksAfterSkipArrival.add(Calendar.DAY_OF_MONTH, 14);
            long daysBetweenInMilliseconds = TwoWeeksAfterSkipArrival.getTimeInMillis() - c.getTimeInMillis(); //c is today's date
            int days = (int) TimeUnit.DAYS.convert(daysBetweenInMilliseconds, TimeUnit.MILLISECONDS);

            message = "You have " + days + " days remaining until your " + skipOrSkips + " must be picked up, or incur an extra charge. Choose your pick up date now.";
            order.setButtonTypeForTrackOrders(Order.BUTTON_TYPE_CHOOSE_DATE_OF_SKIP_PICKUP);
            return message;
        }

        //If the pick up date is today
        if (c.getTime() == order.getDateOfSkipCollection().getTime()) {
            message = "Your " + skipOrSkips + " will be picked up today.";
            order.setButtonTypeForTrackOrders(Order.BUTTON_TYPE_NO_BUTTON);
            return message;
        }


        //If the skip has been delivered, user has set a pick up date which has not yet occurred
        if (order.getDateOfSkipCollection().after(c)) {
            long daysBetweenInMilliseconds = order.getDateOfSkipCollection().getTimeInMillis() - c.getTimeInMillis();
            int days = (int) TimeUnit.DAYS.convert(daysBetweenInMilliseconds, TimeUnit.MILLISECONDS);

            if (days == 0) {
                message = "Your " + skipOrSkips + " will be picked up today.";
            } else if (days == 1) {
                message = "Your " + skipOrSkips + " will be picked up tomorrow.";
            } else {
                message = "Your " + skipOrSkips + " will be picked up in " + days + " days.";
            }
            order.setButtonTypeForTrackOrders(Order.BUTTON_TYPE_EDIT_DATE);
            return message;
        }

        //The deadline for feedback is set to 2 months after the skip is picked up.
        Calendar feedbackDeadline = Calendar.getInstance();
        feedbackDeadline.setTime(skipArrival.getTime());
        feedbackDeadline.add(Calendar.MONTH, 2);

        // If the order has happened, no feedback has been left and the feedback deadline (2 months
        // after skip was picked up) has not been reached yet.
        //TODO I just disabled this as I took userfeedback out of Orders - new Firebase thingy must be implemented
//        if (order.getDateSkipWillBePickedUp().before(c) && feedbackDeadline.after(c) && order.getUserFeedback() == null) {
//            long daysBetweenInMilliseconds = c.getTimeInMillis() - order.getDateSkipWillBePickedUp().getTimeInMillis();
//            int days = (int) TimeUnit.DAYS.convert(daysBetweenInMilliseconds, TimeUnit.MILLISECONDS);
//
//            if (days < 14) {
//                message = "Your " + skipWasOrSkipsWere + " picked up " + days + " days ago.";
//            } else {
//                int weeks = days / 7; //ints will only say 2, 3, 4 weeks etc, never 3.4 etc
//                message = "Your " + skipWasOrSkipsWere + " picked up " + weeks + " weeks ago.";
//            }
//
//
//            daysBetweenInMilliseconds = feedbackDeadline.getTimeInMillis() - c.getTimeInMillis();
//            days = (int) TimeUnit.DAYS.convert(daysBetweenInMilliseconds, TimeUnit.MILLISECONDS);
//
//            if (days == 1) {
//                message += "\nYou have 1 more day to leave feedback for your skip provider.";
//                return message;
//            }
//            if (days < 7) {
//                message += "\nYou have " + days + " more days to leave feedback for your skip provider.";
//                return message;
//            } else {
//                int weeks = days / 7;
//                String weekOrWeeks;
//                if (weeks == 1) {
//                    weekOrWeeks = "week";
//                } else {
//                    weekOrWeeks = "weeks";
//                }
//                message += "\nYou have " + weeks + " more " + weekOrWeeks + " to leave feedback for your skip provider.";
//            }
//            order.setButtonTypeForTrackOrders(Order.BUTTON_TYPE_LEAVE_FEEDBACK_NOW);
//            return message;
//        }


        //If the feedback deadline has been passed and the user didn't leave feedback.
        //TODO I just disabled this as I took userfeedback out of Orders - new Firebase thingy must be implemented
//        if(feedbackDeadline.before(c) && order.getUserFeedback() == null) {
//            long daysBetweenInMilliseconds = c.getTimeInMillis() - order.getDateSkipWillBePickedUp().getTimeInMillis();
//            int weeks = (int) TimeUnit.DAYS.convert(daysBetweenInMilliseconds, TimeUnit.MILLISECONDS) / 7;
//            message = "Your " + skipWasOrSkipsWere + " picked up " + weeks + " weeks ago. You can no longer leave feedback for this order.";
//            order.setButtonTypeForTrackOrders(Order.BUTTON_TYPE_NO_BUTTON);
//            return message;
//        }

        //If the user has left feedback already.
        //TODO I just disabled this as I took userfeedback out of Orders - new Firebase thingy must be implemented
//        if(order.getUserFeedback() != null) {
//            long daysBetweenInMilliseconds = c.getTimeInMillis() - order.getDateSkipWillBePickedUp().getTimeInMillis();
//            int days = (int) TimeUnit.DAYS.convert(daysBetweenInMilliseconds, TimeUnit.MILLISECONDS);
//            int weeks = days / 7;
//            if (days < 7) {
//                message = "Your" + skipWasOrSkipsWere + " picked up " + days + " days ago. You have already left feedback for this order.";
//            } else {
//                message = "Your " + skipWasOrSkipsWere + " picked up " + weeks + " weeks ago. You have already left feedback for this order.";
//            }
//            order.setButtonTypeForTrackOrders(Order.BUTTON_TYPE_VIEW_FEEDBACK);
//            return message;
//        }

        return "";
    }
//    protected Dialog createDatePicker() {
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerStyle,
//                    dateListener, year, month, day);
//
//            Calendar c = Calendar.getInstance();
//            c.add(Calendar.DATE, 1); //This sets c's date to tomorrow
//            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//
//            c.add(Calendar.YEAR, 1);
//            c.add(Calendar.DATE, -1); //This sets c's date to a year in the future from now
//            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
//
//
//            //This is here in case a Calendar rather than a Spinner view is wanted in future.
//            //Simply uncomment to do so.
//            //datePickerDialog.getDatePicker().setCalendarViewShown(true);
//            //datePickerDialog.getDatePicker().setSpinnersShown(false);
//
//
//            return datePickerDialog;
//        }

    @Override
    public int getGroupCount() {
        return ordersArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
        //return listHashMap.get(ordersArrayList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ordersArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //return ordersArrayList.get(groupPosition);
       return listHashMap.get(ordersArrayList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getChildTypeCount() {
        return 2;
        //TODO real number
    }

    @Override
    public int getGroupTypeCount() {
        return 2;
        //TODO real number
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        //this just returns 0, needs to change
        Order currentOrder = (Order) getGroup(groupPosition);
        //int type = currentOrder.getChildTypeForExpandableListView();
        return super.getChildType(groupPosition, childPosition);
    }

//    @Override
//    public int getGroupType(int groupPosition) {
//        //this just returns 0, needs to change
//        Order currentOrder = (Order) getGroup(groupPosition);
//        //return currentOrder.getParentTypeForExpandleListView();
//    }
}
