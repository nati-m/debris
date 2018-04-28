package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class HireConfirmOrderActivity extends AppCompatActivity {

    TextView addressTextView;
    TextView dateTextView;
    TextView skipTypeAndNumberTextView;
    TextView companyChosenTextView;
    TextView totalPrice;
    Button confirmOrder;
    Spinner paymentOptionsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_confirm_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addressTextView = (TextView) findViewById(R.id.hire_confirm_address);
        dateTextView = (TextView) findViewById(R.id.hire_confirm_date);
        skipTypeAndNumberTextView = (TextView) findViewById(R.id.hire_confirm_skip_type_and_number);
        companyChosenTextView = findViewById(R.id.chosen_company_confirm_order);
        totalPrice = findViewById(R.id.total_price_confirm_orders);

        String fullAddress = (Control.CONTROL.getCurrentOrderAddressAsString());
        addressTextView.setText(fullAddress);

        Calendar skipArrivalDate = Control.CONTROL.getCurrentOrder().getDateOfSkipArrival();
        dateTextView.setText(Control.CONTROL.getDateAsAStringInFormatWed18JUN2018(skipArrivalDate.get(Calendar.YEAR), skipArrivalDate.get(Calendar.MONTH), skipArrivalDate.get(Calendar.DAY_OF_MONTH)));

        String skipTypeAndNumber = Control.CONTROL.getCurrentOrder().getSkipsOrderedArrayList().size() + " x " + Control.CONTROL.getCurrentOrder().getSkipsOrderedArrayList().get(0).getSkipTypeAsString();
        skipTypeAndNumberTextView.setText(skipTypeAndNumber);

        String companyChosen = Control.CONTROL.getCurrentOrder().getCompany().getName();
        companyChosenTextView.setText(companyChosen);

        String price = "£" + Control.CONTROL.getCurrentOrder().getPrice() + "0";
        totalPrice.setText(price);

        confirmOrder = findViewById(R.id.button_confirm_order_to_banks);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order currentOrder = Control.CONTROL.getCurrentOrder();
                Control.CONTROL.getOrdersFromThisUser().add(currentOrder);
                //TODO TEST DATABASE FIREBASE
                DatabaseReference orderDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders");
                orderDatabaseReference.push().setValue(currentOrder);

                Intent nextPageIntent = new Intent(HireConfirmOrderActivity.this, FrontPageLoggedInActivity.class);
                startActivity(nextPageIntent);
            }
        });

        paymentOptionsSpinner = findViewById(R.id.spinner_payment_method_confirm_orders);

        ArrayList<String> spinnerOptions = new ArrayList<>();
        spinnerOptions.add("Credit Card"); spinnerOptions.add("Debit Card"); spinnerOptions.add("PayPal");
        ArrayAdapter<String> arrayAdapterString = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerOptions);
        arrayAdapterString.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentOptionsSpinner.setAdapter(arrayAdapterString);


    }

}
