package com.example.android.debris1_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
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
    ArrayList<Order> ordersFromThisUser;
    HashMap<String, ArrayList<Order>> stringArrayListHashMap;
    TextView sortOrdersByTextView;
    Spinner sortOrdersBy;
    ArrayList<String> spinnerSortByOptions = new ArrayList<>();

    RecyclerView recyclerView;
    TrackOrdersRecyclerViewAdapter trackOrdersRecyclerViewAdapter;
    Context contextForArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_track_orders);
        youHaveNoOrders = findViewById(R.id.you_have_no_orders_track_orders);
        sortOrdersByTextView = findViewById(R.id.sort_by_date_of_skip_arrival_track_orders);
        sortOrdersBy = findViewById(R.id.spinner_sort_orders_by_track_orders);

        //getOrdersForThisUserFromFirebase();

        DatabaseReference ordersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child("unconfirmed");
        String uid = Control.CONTROL.getCurrentUser().getFirebaseUid();

        ordersFromThisUser = new ArrayList<>();
        ordersDatabaseReference.orderByChild("userUID").equalTo(uid).addValueEventListener(
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

            contextForArrayAdapter = this;


            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            trackOrdersRecyclerViewAdapter = new TrackOrdersRecyclerViewAdapter(ordersFromThisUser, contextForArrayAdapter);
            recyclerView.setAdapter(trackOrdersRecyclerViewAdapter);



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



    private void sortOrdersLatestFirst(){
        ArrayList<Order> latestFirstArrayList = new ArrayList<>();
        latestFirstArrayList.addAll(ordersFromThisUser);
        //Collections.sort(latestFirstArrayList, Order.dateLatestFirstComparator);

//        expandableListAdapter = new ExpandableListTrackOrdersAdapter(this, latestFirstArrayList, stringArrayListHashMap);
//        expandableListView.setAdapter(expandableListAdapter);
    }

    private void sortOrdersEarliestFirst(){
        ArrayList<Order> earliestFirstArrayList = new ArrayList<>();
        earliestFirstArrayList.addAll(ordersFromThisUser);
        //Collections.sort(earliestFirstArrayList, Order.dateEarliestFirstComparator);

//        expandableListAdapter = new ExpandableListTrackOrdersAdapter(this, earliestFirstArrayList, stringArrayListHashMap);
//        expandableListView.setAdapter(expandableListAdapter);
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




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_log_out:
                AuthUI.getInstance().signOut(this);
                //Signs out of Firebase
                Control.CONTROL.setCurrentUser(new PublicUser());
                //Sets current user in CONTROL to a blank user
                Intent nextPageIntent = new Intent(TrackOrdersActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            case R.id.menu_action_user_settings:
                Intent settingsIntent = new Intent(TrackOrdersActivity.this, UserSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
