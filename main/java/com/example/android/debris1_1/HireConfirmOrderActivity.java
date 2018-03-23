package com.example.android.debris1_1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class HireConfirmOrderActivity extends AppCompatActivity {

    TextView addressTextView;
    TextView dateTextView;
    TextView skipTypeAndNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_confirm_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addressTextView = (TextView) findViewById(R.id.hire_confirm_address);
        dateTextView = (TextView) findViewById(R.id.hire_confirm_date);
        skipTypeAndNumberTextView = (TextView) findViewById(R.id.hire_confirm_skip_type_and_number);

        String fullAddress = (Control.CONTROL.getCurrentOrderAddressAsString());
        addressTextView.setText(fullAddress);
        dateTextView.setText(Control.CONTROL.getCurrentOrder().getDateOfSkipArrivalAsAString());

        String skipTypeAndNumber = Control.CONTROL.getCurrentOrder().getSkipsOrderedArrayList().size() + " x " + Control.CONTROL.getCurrentOrder().getSkipsOrderedArrayList().get(0).getSkipType();
        skipTypeAndNumberTextView.setText(skipTypeAndNumber);





    }

}
