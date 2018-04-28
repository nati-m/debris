package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static android.view.View.GONE;

public class TrackOrdersActivity extends AppCompatActivity {

    TextView youHaveNoOrders;
    ExpandableListView expandableListView;
    ArrayList<Order> ordersFromThisUser;
    ExpandableListTrackOrdersAdapter expandableListAdapter;
    HashMap<String, ArrayList<Order>> stringArrayListHashMap;
    TextView sortOrdersByTextView;
    Spinner sortOrdersBy;
    ArrayList<String> spinnerSortByOptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        youHaveNoOrders = (TextView) findViewById(R.id.you_have_no_orders_track_orders);
        expandableListView = findViewById(R.id.orders_expandable_list_view_track_orders);
        sortOrdersByTextView = findViewById(R.id.sort_by_date_of_skip_arrival_track_orders);
        sortOrdersBy = findViewById(R.id.spinner_sort_orders_by_track_orders);

        //getOrdersForThisUserFromFirebase();

        DatabaseReference ordersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders");
        String uid = Control.CONTROL.getCurrentUser().getFirebaseUid();

        ordersFromThisUser = new ArrayList<>();
        ordersDatabaseReference.orderByChild("userUID").equalTo("grKVZ54zGpdjhwwVtZBwSHgbFS02").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()){

                            Order order = child.getValue(Order.class);
                            ordersFromThisUser.add(order);
                        }
                        //When the data is finished loading, THEN render the page
                        render();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );


    }


    private void render(){

        //If there are 0 or 1 orders then the sort view by spinner and the textView explaining
        //it disappear from the page
        if (ordersFromThisUser.size() == 0 || ordersFromThisUser.size() == 1) {
            sortOrdersBy.setVisibility(GONE);
            sortOrdersByTextView.setVisibility(GONE);
        }

        //If they have made at least one order, this shows a list and hides the error message
        //saying they have not made an order.
        if (ordersFromThisUser.size() > 0) {

            youHaveNoOrders.setVisibility(GONE);
//            Collections.sort(ordersFromThisUser, Order.dateLatestFirstComparator);
            expandableListAdapter = new ExpandableListTrackOrdersAdapter(this, ordersFromThisUser, stringArrayListHashMap);

            expandableListView.setAdapter(expandableListAdapter);


            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {

                    Button button = findViewById(R.id.button_track_orders_expandable_list_view_child);
                    Order order = ordersFromThisUser.get(groupPosition);
                    View.OnClickListener toReturn = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //a blank one to fill with an option below
                        }
                    };

                    if (order.getButtonTypeForTrackOrders() == Order.BUTTON_TYPE_EDIT_ORDER_ONLY_BEFORE_SKIP_IS_DELIVERED) {
                        button.setText("Edit\nOrder");
                        toReturn = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent nextPageIntent = new Intent(TrackOrdersActivity.this, EditOrderActivity.class);
                                startActivity(nextPageIntent);
                            }
                        };
                    }


                    if (order.getButtonTypeForTrackOrders() == Order.BUTTON_TYPE_CHOOSE_DATE_OF_SKIP_PICKUP) {
                        button.setText("Choose\nDate");
                        toReturn = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent nextPageIntent = new Intent(TrackOrdersActivity.this, EditOrderActivity.class);
                                startActivity(nextPageIntent);

                                //TODO
                            }
                        };
                    }

                    if (order.getButtonTypeForTrackOrders() == Order.BUTTON_TYPE_EDIT_DATE) {
                        button.setText("Edit\nDate");
                        toReturn = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent nextPageIntent = new Intent(TrackOrdersActivity.this, EditOrderActivity.class);
                                startActivity(nextPageIntent);

                                //TODO
                            }
                        };
                    }

                    if (order.getButtonTypeForTrackOrders() == Order.BUTTON_TYPE_LEAVE_FEEDBACK_NOW) {
                        button.setText("Leave\nFeedback");
                        toReturn = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent nextPageIntent = new Intent(TrackOrdersActivity.this, LeaveFeedbackActivity.class);
                                startActivity(nextPageIntent);
                            }
                        };
                    }

                    if (order.getButtonTypeForTrackOrders() == Order.BUTTON_TYPE_VIEW_FEEDBACK) {
                        button.setText("View\nFeedback");
                        toReturn = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent nextPageIntent = new Intent(TrackOrdersActivity.this, EditOrderActivity.class);
                                startActivity(nextPageIntent);

                                //TODO
                            }
                        };
                    }
                    if(button != null) {
                        button.setOnClickListener(toReturn);
                    }
                }
            });



            spinnerSortByOptions.add("Latest First");
            spinnerSortByOptions.add("Earliest First");

            ArrayAdapter<String> spinnerSortByArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerSortByOptions);
            sortOrdersBy.setAdapter(spinnerSortByArrayAdapter);

            sortOrdersBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                    if (pos == 0) {
                        sortOrdersLatestFirst();
                    }
                    if (pos == 1) {
                        sortOrdersEarliestFirst();
                    }

                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }


    }
//    private void getOrdersForThisUserFromFirebase(){
//
//
//
//
//
//
//
//
//
//
//    }

    private void bla(){

    }

    private void sortOrdersLatestFirst(){
        ArrayList<Order> latestFirstArrayList = new ArrayList<>();
        latestFirstArrayList.addAll(ordersFromThisUser);
        //Collections.sort(latestFirstArrayList, Order.dateLatestFirstComparator);

        expandableListAdapter = new ExpandableListTrackOrdersAdapter(this, latestFirstArrayList, stringArrayListHashMap);
        expandableListView.setAdapter(expandableListAdapter);
    }

    private void sortOrdersEarliestFirst(){
        ArrayList<Order> earliestFirstArrayList = new ArrayList<>();
        earliestFirstArrayList.addAll(ordersFromThisUser);
        //Collections.sort(earliestFirstArrayList, Order.dateEarliestFirstComparator);

        expandableListAdapter = new ExpandableListTrackOrdersAdapter(this, earliestFirstArrayList, stringArrayListHashMap);
        expandableListView.setAdapter(expandableListAdapter);
    }



//    private void orderCompaniesByPrice(){
//        ArrayList<Company> priceOrderArrayList = new ArrayList<>();
//        priceOrderArrayList.addAll(companyArrayList);
//
//        Collections.sort(priceOrderArrayList, Company.priceComparator);
//
//        companyArrayAdapter = new CompanyArrayAdapter(this, android.R.layout.simple_list_item_single_choice, priceOrderArrayList);
//        companyListView.setAdapter(companyArrayAdapter);
//        companyListView.setOnItemSelectedListener(onItemSelectedListener);
//    }

}
