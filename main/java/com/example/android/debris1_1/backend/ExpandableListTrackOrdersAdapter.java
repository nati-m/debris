package com.example.android.debris1_1.backend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.android.debris1_1.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Paaat VII on 31/03/2018.
 */

public class ExpandableListTrackOrdersAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Order> ordersArrayList;
    private HashMap<String, ArrayList<Order>> listHashMap;

    public ExpandableListTrackOrdersAdapter(Context context, ArrayList<Order> listDataHeader, HashMap<String, ArrayList<Order>> listHashMap) {
        this.context = context;
        this.ordersArrayList = listDataHeader;
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







        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Order currentOrder = (Order) getGroup(groupPosition);



        TextView messageTextView = convertView.findViewById(R.id.status_message_expandable_list_view_track_orders);

        String message = sortOutWhichMessageIsNeeded(currentOrder);
        return null;
    }

    public String sortOutWhichMessageIsNeeded(Order order){
        Calendar c = Calendar.getInstance();
        Calendar skipArrival = order.getDateOfSkipArrival();
        String message;

        String skipOrSkips;
        String skipIsOrSkipsAre;
        String skipWasOrSkipsWere;
        if (order.getSkipsOrderedArrayList().size()==1){
            skipOrSkips = "skip";
            skipIsOrSkipsAre = "skip is";
            skipWasOrSkipsWere = "skip was";
        } else {
            skipOrSkips = "skips";
            skipIsOrSkipsAre = "skips are";
            skipWasOrSkipsWere = "skips were";
        }

        //If the skip is due to arrive today
        if(c.get(Calendar.YEAR) == skipArrival.get(Calendar.YEAR) &&
                c.get(Calendar.DAY_OF_YEAR) == skipArrival.get(Calendar.DAY_OF_YEAR)){
            message = "Your " + skipIsOrSkipsAre + " arriving today.";
            return message;
        }

        //If the skip is due to arrive in the future.
        //Should include something like order.HASARRIVED() boolean in future versions
        if (c.before(skipArrival)){
            long daysBetweenInMilliseconds = skipArrival.getTimeInMillis() - c.getTimeInMillis();
            int days = (int) TimeUnit.DAYS.convert(daysBetweenInMilliseconds, TimeUnit.MILLISECONDS);
            //if this doesn't work use something like float days = (daysBetweenInMilliseconds / (1000*60*60*24));
            if (days == 0){
                message = "Your " + skipIsOrSkipsAre + " arriving today.";
            } else if (days == 1){
                message = "Your " + skipIsOrSkipsAre + " due to arrive tomorrow.";
            }
            else {
                message = "Your " + skipIsOrSkipsAre + " due to arrive in " + days + " days.";
            }

            return message;
        }

        //if the skip has been delivered but a pick up date has not been set by the user.
        if (order.getDateSkipWillBePickedUp() == null){
            Calendar TwoWeeksAfterSkipArrival = Calendar.getInstance();
            TwoWeeksAfterSkipArrival.setTime(skipArrival.getTime());
            TwoWeeksAfterSkipArrival.add(Calendar.DAY_OF_MONTH, 14);
            long daysBetweenInMilliseconds = TwoWeeksAfterSkipArrival.getTimeInMillis() - c.getTimeInMillis(); //c is today's date
            int days = (int) TimeUnit.DAYS.convert(daysBetweenInMilliseconds, TimeUnit.MILLISECONDS);


            message = "You have " + days + " days remaining until your " + skipOrSkips + "must be picked up, or incur an extra charge. Choose your pick up date now.";
            return message;
        }

        //If the pick up date is today
        if ((c.get(Calendar.YEAR) == order.getDateSkipWillBePickedUp().get(Calendar.YEAR) &&
                c.get(Calendar.DAY_OF_YEAR) == order.getDateSkipWillBePickedUp().get(Calendar.DAY_OF_YEAR))){
            message = "Your " + skipOrSkips + " will be picked up today.";
            return message;
        }


        //If the skip has been delivered, user has set a pick up date which has not yet occurred
        if (order.getDateSkipWillBePickedUp().after(c)){
            long daysBetweenInMilliseconds = order.getDateSkipWillBePickedUp().getTimeInMillis() - c.getTimeInMillis();
            int days = (int) TimeUnit.DAYS.convert(daysBetweenInMilliseconds, TimeUnit.MILLISECONDS);

            if(days == 0){
                message = "Your " + skipOrSkips + " will be picked up today.";
            } else if (days == 1){
                message = "Your " + skipOrSkips + " will be picked up tomorrow.";
            } else {
                message = "Your " + skipOrSkips + " will be picked up in " + days + " days.";
            }
            return message;
        }

        if (order.getDateSkipWillBePickedUp().before(c) && order.getUserFeedback() == null){
            long daysBetweenInMilliseconds = c.getTimeInMillis() - order.getDateSkipWillBePickedUp().getTimeInMillis();
            int days = (int) TimeUnit.DAYS.convert(daysBetweenInMilliseconds, TimeUnit.MILLISECONDS);

            Calendar feedbackDeadline = Calendar.getInstance();
            feedbackDeadline.setTime(skipArrival.getTime());
            feedbackDeadline.add(Calendar.MONTH, 2);



            if (days < 14){
                message = "Your " + skipWasOrSkipsWere + " picked up " + days + " days ago.";
            } else {
                int weeks = days / 7; //ints will only say 2, 3, 4 weeks etc, never 3.4 etc
                message = "Your " + skipWasOrSkipsWere + " picked up " + weeks + " weeks ago.";
            }


        }




            return "";
    }

    @Override
    public int getGroupCount() {
        return ordersArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(ordersArrayList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ordersArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
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
}
