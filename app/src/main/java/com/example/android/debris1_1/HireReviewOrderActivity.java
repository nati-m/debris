package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class HireReviewOrderActivity extends AppCompatActivity {

    TextView addressTextView;
    TextView skipArrivalDateAndTime;
    TextView skipTypeAndNumberTextView;
    Button continueButton;

    TextView subtotalBeforeVAT;
    TextView VAT;
    TextView permitPrice;
    TextView totalPrice;
    //TextView skipCollectionDateAndTime;
    TextView skipPointsNumber;

    SimpleDateFormat simpleDateFormat;

    int skipPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_review_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        simpleDateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.UK);

        addressTextView = findViewById(R.id.hire_review_address);
        skipArrivalDateAndTime = findViewById(R.id.skip_arriving_review_order);
        //skipCollectionDateAndTime = findViewById(R.id.skip_collection_review_order);
        skipTypeAndNumberTextView = findViewById(R.id.hire_review_skip_type_and_number);
        totalPrice = findViewById(R.id.total_price_review_order);
        subtotalBeforeVAT = findViewById(R.id.subtotal_minus_VAT_review_order);
        VAT = findViewById(R.id.VAT_review_order);
        permitPrice = findViewById(R.id.permit_price_review_order);
        skipPointsNumber = findViewById(R.id.skip_points_amount_review_order);

        String fullAddress = (Control.CONTROL.getCurrentOrderAddressAsString());
        addressTextView.setText(fullAddress);

        String arrival = simpleDateFormat.format(Control.CONTROL.getCurrentOrder().getDateOfSkipArrival().getTime()) + ", " + Control.CONTROL.getCurrentOrder().getTimeOfArrival();
        skipArrivalDateAndTime.setText(arrival);

        //THE USER NO LONGER CAN CHOOSE COLLECTION DATE BEFORE THIS POINT. THIS REMAINS COMMENTED IN CASE IT IS NEEDED AT A LATER DATE
//        if(Control.CONTROL.getCurrentOrder().getCollectionDateSpecified()){
//            String collection = simpleDateFormat.format(Control.CONTROL.getCurrentOrder().getDateOfSkipCollection().getTime()) + ", " + Control.CONTROL.getCurrentOrder().getTimeOfCollection();
//            skipCollectionDateAndTime.setText(collection);
//        }
//        else {
//            skipCollectionDateAndTime.setText("NOT SPECIFIED");
//        }

        String skipTypeAndNumber = Control.CONTROL.getCurrentOrder().getSkipsOrderedArrayList().size() + " x " + Control.CONTROL.getCurrentOrder().getSkipsOrderedArrayList().get(0).getSkipTypeAsString();
        skipTypeAndNumberTextView.setText(skipTypeAndNumber);


        double priceMinusVAT = Control.CONTROL.getCurrentOrder().getPrice() * Control.VAT_REMOVAL_PERCENTAGE / 100;
        priceMinusVAT = Math.round(priceMinusVAT * 1.00d); //This rounds the price to 2 decimal places
        String priceMinusVATString = Control.CONTROL.moneyFormat(priceMinusVAT);
        subtotalBeforeVAT.setText(priceMinusVATString);

        double VATdouble = Control.CONTROL.getCurrentOrder().getPrice() - priceMinusVAT;
        VATdouble = Math.round(VATdouble * 1.00d);
        String VATString = Control.CONTROL.moneyFormat(VATdouble);
        VAT.setText(VATString);

        String permitPriceString;
        if(Control.CONTROL.getCurrentOrder().getPermitRequired()){
            permitPriceString = Control.CONTROL.moneyFormat(Control.CONTROL.getCurrentOrder().getPermitPrice());
        } else {
            permitPriceString = "N/A";
        }

        permitPrice.setText(permitPriceString);

        double totalPriceDouble = Control.CONTROL.getCurrentOrder().getPrice() + Control.CONTROL.getCurrentOrder().getPermitPrice();

        String price = Control.CONTROL.moneyFormat(totalPriceDouble) + "*";
        totalPrice.setText(price);

        skipPoints = (int) totalPriceDouble;
        skipPointsNumber.setText("" + skipPoints);
        Control.CONTROL.getCurrentOrder().setSkipPointsForThisOrder(skipPoints);


        continueButton = findViewById(R.id.button_continue_review_order);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPageIntent = new Intent(HireReviewOrderActivity.this, HireConfirmOrderActivity.class);
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
                Intent nextPageIntent = new Intent(HireReviewOrderActivity.this, MainActivity.class);
                startActivity(nextPageIntent);
                //Returns to the top page
                return true;
            case R.id.menu_action_user_settings:
                Intent settingsIntent = new Intent(HireReviewOrderActivity.this, UserSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
