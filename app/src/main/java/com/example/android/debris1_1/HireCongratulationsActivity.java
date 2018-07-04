package com.example.android.debris1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HireCongratulationsActivity extends AppCompatActivity {

    Button toMainMenu;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_congratulations);

        toMainMenu = findViewById(R.id.button_to_main_menu_congratulations);
        email = findViewById(R.id.email_confirmation_text_view_congratulations);

        String emailMessage = "Email confirmation has been sent to you at " + Control.CONTROL.getCurrentUser().getEmail();
        if(Control.CONTROL.getCurrentUser().getNumberOfAdditionalEmails() == 1){
            emailMessage = emailMessage + " and " + Control.CONTROL.getCurrentUser().getAdditionalEmail1();
        } else if (Control.CONTROL.getCurrentUser().getNumberOfAdditionalEmails() == 2){
            emailMessage = emailMessage + ", " + Control.CONTROL.getCurrentUser().getAdditionalEmail1() + " and " + Control.CONTROL.getCurrentUser().getAdditionalEmail2();
        }


        email.setText(emailMessage);

        toMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPageIntent = new Intent(HireCongratulationsActivity.this, FrontPageLoggedInActivity.class);
                startActivity(nextPageIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
