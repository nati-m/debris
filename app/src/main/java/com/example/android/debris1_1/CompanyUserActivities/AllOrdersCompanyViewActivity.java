package com.example.android.debris1_1.CompanyUserActivities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.android.debris1_1.Company;
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

import java.util.ArrayList;


public class AllOrdersCompanyViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Order> allConfirmedOrders;
    DatabaseReference confirmedOrdersFromThisUser;
    Context contextForArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders_company_view);

        contextForArrayAdapter = this;
        allConfirmedOrders = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_all_orders_company_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //This is a dummy adapter so the screen doesn't just show nothing. It is replaced straight away when the data is retrieved from Firebase.
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {return null;}
            @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {}
            @Override public int getItemCount() {return 0;}
        });

        confirmedOrdersFromThisUser = FirebaseDatabase.getInstance().getReference().child("orders").child("confirmed").child(Control.CONTROL.getCurrentUser().getFirebaseUid());
        confirmedOrdersFromThisUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Order order = child.getValue(Order.class);
                    allConfirmedOrders.add(order);
                }
                AllOrdersRecyclerViewAdapter arrayAdapter = new AllOrdersRecyclerViewAdapter(allConfirmedOrders, contextForArrayAdapter);
                recyclerView.setAdapter(arrayAdapter);
            }

            @Override public void onCancelled(DatabaseError databaseError) {}
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
                Intent nextPageIntent = new Intent(AllOrdersCompanyViewActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
