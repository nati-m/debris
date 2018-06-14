package com.example.android.debris1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CancelOrderActivity extends AppCompatActivity {

    TextView skipTypeAndNumber;
    TextView arrivalDate;
    TextView address;
    TextView price;

    Button doNotCancel;
    Button confirmOrderCancellation;

    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        simpleDateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.UK);

        skipTypeAndNumber = findViewById(R.id.cancel_order_skip_type_and_number);
        arrivalDate = findViewById(R.id.cancel_order_date_skip_arriving);
        address = findViewById(R.id.cancel_order_address);
        price = findViewById(R.id.cancel_order_price);

        doNotCancel = findViewById(R.id.button_do_not_cancel_cancel_order);
        confirmOrderCancellation = findViewById(R.id.button_confirm_cancel_cancel_order);

        String fullAddress = (Control.CONTROL.getCurrentOrderAddressAsString());
        address.setText(fullAddress);

        //The dateOfSkipArrivalString is used because Firebase cannot store Calendar objects!
        String arrival = Control.CONTROL.getCurrentOrder().getDateOfSkipArrivalString() + ", " + Control.CONTROL.getCurrentOrder().getTimeOfArrival();
        arrivalDate.setText(arrival);

        String skipTypeAndNumberString = Control.CONTROL.getCurrentOrder().getNumberOfSkipsOrdered() + " x " + Control.CONTROL.getCurrentOrder().getSkipTypeString();
        skipTypeAndNumber.setText(skipTypeAndNumberString);

        double totalPriceDouble = Control.CONTROL.getCurrentOrder().getPrice() + Control.CONTROL.getCurrentOrder().getPermitPrice();

        String priceString = "£" + totalPriceDouble + "0";
        price.setText(priceString);

        doNotCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //simply sends the user back to the Home Page
                Intent nextPageIntent = new Intent(CancelOrderActivity.this, FrontPageLoggedInActivity.class);
                startActivity(nextPageIntent);
            }
        });

        confirmOrderCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrderCancellation.setText("Cancelling...");
                doNotCancel.setVisibility(View.GONE);

                Order currentOrder = Control.CONTROL.getCurrentOrder();

                //Delete this order from the main orders section in firebase
                DatabaseReference thisOrderDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child("unconfirmed").child(currentOrder.getOrderUIDakaFirebaseDatabaseKey());
                thisOrderDatabaseReference.removeValue();

                //Add to the cancelled orders section in Firebase
                DatabaseReference thisOrderCancelledDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child("cancelled").child(Control.CONTROL.getCurrentUser().getFirebaseUid()).child(currentOrder.getOrderUIDakaFirebaseDatabaseKey());
                thisOrderCancelledDatabaseReference.setValue(currentOrder);

                Intent nextPageIntent = new Intent(CancelOrderActivity.this, CancelConfirmedActivity.class);
                startActivity(nextPageIntent);
            }
        });
    }
}
