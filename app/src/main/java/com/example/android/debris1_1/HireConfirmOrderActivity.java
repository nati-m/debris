package com.example.android.debris1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HireConfirmOrderActivity extends AppCompatActivity {

    TextView price;
    Button termsAndConditionsButton;
    Button chooseCollectionDateButton;
    Button goToPaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_confirm_order);

        price = findViewById(R.id.total_price_confirm_order);
        termsAndConditionsButton = findViewById(R.id.terms_and_conditions_check_box_confirm_order);
        chooseCollectionDateButton = findViewById(R.id.button_choose_pick_up_date_before_ordering_confirm_order);
        goToPaymentButton = findViewById(R.id.button_to_payment_page_confirm_order);

        double pricePlusPermit = Control.CONTROL.getCurrentOrder().getPrice() + Control.CONTROL.getCurrentOrder().getPermitPrice();
        String priceString = "£" + pricePlusPermit + "0*";
        price.setText(priceString);

        goToPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This sets the value of Skip collection date and time to null.
                //This resets the values if the user chose to select their collection date/time and then changed
                //their mind by pressing the back button.
                Control.CONTROL.getCurrentOrder().setDateOfSkipCollection(null);
                Control.CONTROL.getCurrentOrder().setTimeOfCollection(null);
                Control.CONTROL.getCurrentOrder().setSurchargeForLongHire(0);

                Intent nextPageIntent = new Intent(HireConfirmOrderActivity.this, HirePaymentActivity.class);
                startActivity(nextPageIntent);
            }
        });

        chooseCollectionDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPageIntent = new Intent(HireConfirmOrderActivity.this, HireOptionalChooseCollectionDateActivity.class);
                startActivity(nextPageIntent);
            }
        });


    }
}
