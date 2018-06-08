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
