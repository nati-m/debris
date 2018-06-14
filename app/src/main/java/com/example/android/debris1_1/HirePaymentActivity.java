package com.example.android.debris1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HirePaymentActivity extends AppCompatActivity {

    Button confirmOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_payment);

        confirmOrderButton = findViewById(R.id.button_confirm_order_payment);

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The full price is set to the price plus the permit.
                //They were kept separate earlier before this "point of no return" in case the user went back and changed their mind.
                double pricePlusPermit = Control.CONTROL.getCurrentOrder().getPrice() + Control.CONTROL.getCurrentOrder().getPermitPrice();
                Control.CONTROL.getCurrentOrder().setPrice(pricePlusPermit);

                Order currentOrder = Control.CONTROL.getCurrentOrder();

                DatabaseReference orderDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child("unconfirmed");
                orderDatabaseReference.push().setValue(currentOrder);

                //Updates the user's skip points on Firebase and locally
                int currentSkipPoints = Control.CONTROL.getCurrentUser().getPoints();
                int thisOrdersSkipPoints = Control.CONTROL.getCurrentOrder().getSkipPointsForThisOrder();
                Control.CONTROL.getCurrentUser().setPoints(currentSkipPoints + thisOrdersSkipPoints);
                DatabaseReference skipPointsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(Control.CONTROL.getCurrentUser().getFirebaseUid()).child("points");
                skipPointsDatabaseReference.setValue(Control.CONTROL.getCurrentUser().getPoints());

                Intent nextPageIntent = new Intent(HirePaymentActivity.this, HireCongratulationsActivity.class);
                startActivity(nextPageIntent);
            }
        });
    }
}
