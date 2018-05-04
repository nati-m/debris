package com.example.android.debris1_1.CompanyUserActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.debris1_1.Control;
import com.example.android.debris1_1.MainActivity;
import com.example.android.debris1_1.Order;
import com.example.android.debris1_1.PublicUser;
import com.example.android.debris1_1.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CompanyHomePageLoggedInActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcomeAndTodaysJobsTextView;
    TextView jobsAvailableTextView;
    ArrayList<Order> relevantOrders;
    Button todaysOrders;
    Button allOrders;
    SimpleDateFormat simpleDateFormat;

    //You can't call "this" inside a nested class so I create a copy of "this" here as a Context
    //variable for the ValueEventListener below
    Context contextForArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home_page_logged_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        relevantOrders = new ArrayList<>();
        contextForArrayAdapter = this;
        welcomeAndTodaysJobsTextView = findViewById(R.id.todays_deliveries_and_collections_company_home_page_logged_in);
        welcomeAndTodaysJobsTextView.setText("Welcome " + Control.CONTROL.getCurrentUser().getName());
        jobsAvailableTextView = findViewById(R.id.unconfirmed_orders_text_view_company_home_page_logged_in);
        jobsAvailableTextView.setText("");
        todaysOrders = findViewById(R.id.todays_orders_button_company_home_page_logged_in);
        allOrders = findViewById(R.id.all_orders_button_company_home_page_logged_in);
        simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.UK);

        todaysOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPageIntent = new Intent(CompanyHomePageLoggedInActivity.this, TodaysOrdersCompanyViewActivity.class);
                startActivity(nextPageIntent);
            }
        });

        allOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPageIntent = new Intent(CompanyHomePageLoggedInActivity.this, AllOrdersCompanyViewActivity.class);
                startActivity(nextPageIntent);
            }
        });

        recyclerView = findViewById(R.id.recycler_view_company_home_page_logged_in);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //This is a dummy adapter so the screen doesn't just show nothing. It is replaced straight away when the data is retrieved from Firebase.
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {return null;}
            @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {}
            @Override public int getItemCount() {return 0;}
        });


        DatabaseReference confirmedOrdersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child("confirmed").child(Control.CONTROL.getCurrentUser().getFirebaseUid());
        confirmedOrdersDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String TODAYSTRING = simpleDateFormat.format(Calendar.getInstance().getTime());
                int numberOfDeliveriesToday = 0;
                int numberOfCollectionsToday = 0;

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Order order = child.getValue(Order.class);
                    if(order.getDateOfSkipArrivalString().equals(TODAYSTRING)) {
                        numberOfDeliveriesToday++;
                    } else if (order.getDateOfSkipCollectionString() != null && order.getDateOfSkipCollectionString().equals(TODAYSTRING)){
                        numberOfCollectionsToday++;
                    }
                }

                String welcome = "Welcome " + Control.CONTROL.getCurrentUser().getName() + ".";

                if (numberOfCollectionsToday == 0 && numberOfDeliveriesToday == 0){
                    welcome += " You have no deliveries or collections today.";
                    welcomeAndTodaysJobsTextView.setTextColor(getResources().getColor(R.color.black));
                    welcomeAndTodaysJobsTextView.setText(welcome);
                    return;
                }
                else if (numberOfDeliveriesToday > 1 || numberOfDeliveriesToday == 0){
                    welcome += " You have " + numberOfDeliveriesToday + " deliveries";
                }
                else if (numberOfDeliveriesToday == 1){
                    welcome += " You have 1 delivery";
                }


                if (numberOfCollectionsToday > 1 || numberOfCollectionsToday == 0){
                    welcome += " and " + numberOfCollectionsToday + " collections today.";
                }
                else if (numberOfCollectionsToday == 1){
                    welcome += " and 1 collection today.";
                }

                welcomeAndTodaysJobsTextView.setText(welcome);
            }

            @Override public void onCancelled(DatabaseError databaseError) {}
        });

        DatabaseReference unconfirmedOrdersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child("unconfirmed");
        unconfirmedOrdersDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Order order = child.getValue(Order.class);
                    order.setOrderUIDakaFirebaseDatabaseKey(child.getKey());
                    relevantOrders.add(order);
                }
                ConfirmOrdersFromPublicUsersRecyclerViewArrayAdapter arrayAdapter = new ConfirmOrdersFromPublicUsersRecyclerViewArrayAdapter(relevantOrders, contextForArrayAdapter);

                recyclerView.setAdapter(arrayAdapter);

                String jobsAvailable = "There are " + relevantOrders.size() + " jobs to consider.";
                jobsAvailableTextView.setText(jobsAvailable);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_log_out:
                AuthUI.getInstance().signOut(this);
                //Signs out of Firebase
                Control.CONTROL.setCurrentUser(new PublicUser());
                //Sets current user in CONTROL to a blank user
                Intent nextPageIntent = new Intent(CompanyHomePageLoggedInActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}








//  for (int i = 0; i < companyRecyclerViewArrayAdapter.getViewsArrayList().size(); i++){
//  //                  if (i == pos){
//                        companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(Color.CYAN);
//                    }
//                    else companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(getResources().getColor(R.color.background));
//                }

//        companyRecyclerViewArrayAdapter = new CompanyRecyclerViewArrayAdapter(getRelevantCompaniesArrayList(), this);
//
//        companyRecyclerView = findViewById(R.id.recycler_view_companies_single_day_view);
//        companyRecyclerView.setHasFixedSize(true);
//        companyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        companyRecyclerView.setAdapter(companyRecyclerViewArrayAdapter);
//
//        companyRecyclerViewArrayAdapter.setClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = companyRecyclerView.getChildViewHolder(v).getAdapterPosition();
//                Control.CONTROL.setCompanySelectedInRecyclerView(Control.CONTROL.getSingleDayViewCompanies().get(pos));
//
//                String priceString = "£" + Control.CONTROL.getCompanySelectedInRecyclerView().getDefaultPriceDifferentDependingOnSkipType(skipType) + "0";
//                priceSubtotal.setText(priceString);
//
//                for (int i = 0; i < companyRecyclerViewArrayAdapter.getViewsArrayList().size(); i++){
//                    if (i == pos){
//                        companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(Color.CYAN);
//                    }
//                    else companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(getResources().getColor(R.color.background));
//                }
//            }
//        });
