package com.example.android.debris1_1.CompanyUserActivities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.android.debris1_1.Control;
import com.example.android.debris1_1.Order;
import com.example.android.debris1_1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TodaysOrdersCompanyViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Order> todaysConfirmedOrders;
    DatabaseReference confirmedOrdersFromThisUser;
    Context contextForArrayAdapter;
    SimpleDateFormat simpleDateFormat;
    Calendar TODAY = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_orders_company_view);

        contextForArrayAdapter = this;
        todaysConfirmedOrders = new ArrayList<>();
        simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.UK);

        recyclerView = findViewById(R.id.recycler_view_todays_orders_company_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //This is a dummy adapter so the screen doesn't just show nothing. It is replaced straight away when the data is retrieved from Firebase.
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {return null;}
            @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {}
            @Override public int getItemCount() {return 0;}
        });


        confirmedOrdersFromThisUser = FirebaseDatabase.getInstance().getReference().child("orders").child("confirmed").child(Control.CONTROL.getCurrentUser().getFirebaseUid());
        confirmedOrdersFromThisUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String TODAYSTRING = simpleDateFormat.format(TODAY.getTime());
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Order order = child.getValue(Order.class);
                    if(order.getDateOfSkipArrivalString().equals(TODAYSTRING)) {
                        todaysConfirmedOrders.add(order);
                    } else if (order.getDateOfSkipCollectionString() != null && order.getDateOfSkipCollectionString().equals(TODAYSTRING)){
                        todaysConfirmedOrders.add(order);
                    }
                }
                AllOrdersRecyclerViewAdapter arrayAdapter = new AllOrdersRecyclerViewAdapter(todaysConfirmedOrders, contextForArrayAdapter);
                recyclerView.setAdapter(arrayAdapter);
            }

            @Override public void onCancelled(DatabaseError databaseError) {}
        });

    }
}
