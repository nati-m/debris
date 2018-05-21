package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HireConfirmOrderActivity extends AppCompatActivity {

    TextView addressTextView;
    TextView skipArrivalDateAndTime;
    TextView skipTypeAndNumberTextView;
    Button confirmOrderButton;

    TextView subtotalBeforeVAT;
    TextView VAT;
    TextView permitPrice;
    TextView totalPrice;
    TextView skipCollectionDateAndTime;
    TextView skipPointsNumber;

    SimpleDateFormat simpleDateFormat;

    int skipPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_confirm_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        simpleDateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.UK);

        addressTextView = findViewById(R.id.hire_confirm_address);
        skipArrivalDateAndTime = findViewById(R.id.skip_arriving_confirm_order);
        skipCollectionDateAndTime = findViewById(R.id.skip_collection_confirm_order);
        skipTypeAndNumberTextView = findViewById(R.id.hire_confirm_skip_type_and_number);
        totalPrice = findViewById(R.id.total_price_confirm_order);
        subtotalBeforeVAT = findViewById(R.id.subtotal_minus_VAT_confirm_order);
        VAT = findViewById(R.id.VAT_confirm_order);
        permitPrice = findViewById(R.id.permit_price_confirm_order);
        skipPointsNumber = findViewById(R.id.skip_points_amount_confirm_order);

        String fullAddress = (Control.CONTROL.getCurrentOrderAddressAsString());
        addressTextView.setText(fullAddress);

        String arrival = simpleDateFormat.format(Control.CONTROL.getCurrentOrder().getDateOfSkipArrival().getTime()) + ", " + Control.CONTROL.getCurrentOrder().getTimeOfArrival();
        skipArrivalDateAndTime.setText(arrival);

        if(Control.CONTROL.getCurrentOrder().getCollectionDateSpecified()){
            String collection = simpleDateFormat.format(Control.CONTROL.getCurrentOrder().getDateOfSkipCollection().getTime()) + ", " + Control.CONTROL.getCurrentOrder().getTimeOfCollection();
            skipCollectionDateAndTime.setText(collection);
        }
        else {
            skipCollectionDateAndTime.setText("NOT SPECIFIED");
        }

        String skipTypeAndNumber = Control.CONTROL.getCurrentOrder().getSkipsOrderedArrayList().size() + " x " + Control.CONTROL.getCurrentOrder().getSkipsOrderedArrayList().get(0).getSkipTypeAsString();
        skipTypeAndNumberTextView.setText(skipTypeAndNumber);


        double priceMinusVAT = Control.CONTROL.getCurrentOrder().getPrice() * Control.VAT_REMOVAL_PERCENTAGE / 100;
        priceMinusVAT = Math.round(priceMinusVAT * 1.00d); //This round the price to 2 decimal places
        String priceMinusVATString = "£" + priceMinusVAT;
        subtotalBeforeVAT.setText(priceMinusVATString);

        double VATdouble = Control.CONTROL.getCurrentOrder().getPrice() - priceMinusVAT;
        VATdouble = Math.round(VATdouble * 1.00d);
        String VATString = "£" + VATdouble;
        VAT.setText(VATString);

        String permitPriceString;
        if(Control.CONTROL.getCurrentOrder().getPermitRequired()){
            permitPriceString = "£" + Control.CONTROL.getCurrentOrder().getPermitPrice() + "0";
        } else {
            permitPriceString = "N/A";
        }

        permitPrice.setText(permitPriceString);

        double totalPriceDouble = Control.CONTROL.getCurrentOrder().getPrice() + Control.CONTROL.getCurrentOrder().getPermitPrice();

        String price = "£" + totalPriceDouble + "0";
        totalPrice.setText(price);

        skipPoints = (int) totalPriceDouble;
        skipPointsNumber.setText("" + skipPoints);


        confirmOrderButton = findViewById(R.id.button_confirm_order_to_banks);
        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order currentOrder = Control.CONTROL.getCurrentOrder();
                //Control.CONTROL.getOrdersFromThisUser().add(currentOrder);
                //TODO TEST DATABASE FIREBASE
                DatabaseReference orderDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child("unconfirmed");
                orderDatabaseReference.push().setValue(currentOrder);

                Intent nextPageIntent = new Intent(HireConfirmOrderActivity.this, FrontPageLoggedInActivity.class);
                startActivity(nextPageIntent);
            }
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
                Intent nextPageIntent = new Intent(HireConfirmOrderActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}