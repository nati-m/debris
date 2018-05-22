package com.example.android.debris1_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserSettingsActivity extends AppCompatActivity {

    Button changeDefaultAddressButton;
    TextView defaultAddressTextView;
    LinearLayout appearingChangeAddressInputLinearLayout;
    EditText enterAddressLine1;
    EditText enterAddressLine2;
    EditText enterPostcode;
    Button cancelAddressChange;
    Button confirmAddressChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        changeDefaultAddressButton = findViewById(R.id.edit_default_address_button_settings);
        defaultAddressTextView = findViewById(R.id.users_address_text_view_settings);
        appearingChangeAddressInputLinearLayout = findViewById(R.id.choose_new_address_linear_layout_settings);
        enterAddressLine1 = findViewById(R.id.enter_address_line_1_settings);
        enterAddressLine2 = findViewById(R.id.enter_address_line_2_settings);
        enterPostcode =  findViewById(R.id.enter_postcode_settings);
        cancelAddressChange = findViewById(R.id.cancel_change_address_button_settings);
        confirmAddressChange = findViewById(R.id.confirm_change_address_button_settings);

        String usersAddressAndPostcode;
        if(Control.CONTROL.getCurrentUser().getAddressLine2().isEmpty()){
            usersAddressAndPostcode = Control.CONTROL.getCurrentUser().getAddressLine1() +
                    "\n" + Control.CONTROL.getCurrentUser().getPostCode();
        } else {
            usersAddressAndPostcode = Control.CONTROL.getCurrentUser().getAddressLine1() +
                    "\n" + Control.CONTROL.getCurrentUser().getAddressLine2() +
                    "\n" + Control.CONTROL.getCurrentUser().getPostCode();
        }
        defaultAddressTextView.setText(usersAddressAndPostcode);

        changeDefaultAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appearingChangeAddressInputLinearLayout.setVisibility(View.VISIBLE);
                changeDefaultAddressButton.setVisibility(View.GONE);
            }
        });

        cancelAddressChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDefaultAddressButton.setVisibility(View.VISIBLE);
                appearingChangeAddressInputLinearLayout.setVisibility(View.GONE);
            }
        });

        confirmAddressChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptAddressChange();
            }
        });


    }

    private void attemptAddressChange(){
        
    }
}
