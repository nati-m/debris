package com.example.android.debris1_1;

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

        String priceString = "£" + Control.CONTROL.getCurrentOrder().getPrice() + "0*";
        price.setText(priceString);

        //If the user has already chosen their collection date, the button to do so is removed
        //and some text on the page is changed.
        if(Control.CONTROL.getCurrentOrder().getCollectionDateSpecified()){
            chooseCollectionDateButton.setVisibility(View.GONE);
            goToPaymentButton.setText("");
        }


    }
}
