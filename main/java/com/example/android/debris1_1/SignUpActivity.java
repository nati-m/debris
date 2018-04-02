package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.android.debris1_1.Control.CONTROL;

public class SignUpActivity extends AppCompatActivity {

    Button signUpButton;

    EditText nameEntered;
    EditText emailEntered;
    EditText postcodeEntered;
    EditText passwordEntered;
    EditText confirmPasswordEntered;

    private boolean formEntryIsValid;

    PublicUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        signUpButton = (Button) findViewById(R.id.sign_up_button);

        nameEntered = (EditText) findViewById(R.id.enter_name);
        emailEntered = (EditText) findViewById(R.id.enter_email);
        postcodeEntered = (EditText) findViewById(R.id.enter_postcode);
        passwordEntered = (EditText) findViewById(R.id.enter_password);
        confirmPasswordEntered = (EditText) findViewById(R.id.enter_confirm_password);

        formEntryIsValid = false;

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                attemptFormEntry();

//                if(formEntryIsValid){
//                Intent nextPageIntent = new Intent(SignUpActivity.this, FrontPageLoggedInActivity.class);
//                startActivity(nextPageIntent);
//            }
        }
        });

    }


    public void attemptFormEntry(){

        // Reset errors.
        nameEntered.setError(null);
        emailEntered.setError(null);
        postcodeEntered.setError(null);
        passwordEntered.setError(null);
        confirmPasswordEntered.setError(null);

        // Store values at the time of the login attempt.
        String name = nameEntered.getText().toString();
        String email = emailEntered.getText().toString();
        String postcode = postcodeEntered.getText().toString();
        String password = passwordEntered.getText().toString();
        String confirmPassword = confirmPasswordEntered.getText().toString();


        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(name)){
            nameEntered.setError(getString(R.string.error_field_required));
            focusView = nameEntered;
            cancel = true;
        }

        if(TextUtils.isEmpty(email)){
            emailEntered.setError(getString(R.string.error_field_required));
            focusView = emailEntered;
            cancel = true;
        }

        if(TextUtils.isEmpty(postcode)){
            postcodeEntered.setError(getString(R.string.error_field_required));
            focusView = postcodeEntered;
            cancel = true;
        }

        if(TextUtils.isEmpty(password)){
            passwordEntered.setError(getString(R.string.error_field_required));
            focusView = passwordEntered;
            cancel = true;
        }

        if(TextUtils.isEmpty(confirmPassword)){
            confirmPasswordEntered.setError(getString(R.string.error_field_required));
            focusView = confirmPasswordEntered;
            cancel = true;
        }

        //Checking if email is valid
        if(!isEmailValid(email)){
            emailEntered.setError("This is not a valid email address.");
            focusView = emailEntered;
            cancel = true;
        }

        //TO ADD: checking if email is taken

        //Checking if password and confirm password are the same
        if(!password.equals(confirmPassword)){
            confirmPasswordEntered.setError("This does not match the password entered above");
            focusView = confirmPasswordEntered;
            cancel = true;
        }



        if(!cancel){
            //creates a user object
            currentUser = new PublicUser(name, postcode, email);

            //sets this to the current user using the app in CONTROL
            CONTROL.setCurrentUser(currentUser);



            //goes to the next page
            Intent nextPageIntent = new Intent(SignUpActivity.this, FrontPageLoggedInActivity.class);
            startActivity(nextPageIntent);
        }



    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }


//    public void handleFormEntry(View button){
//
//        //If any of the form's EditTexts are blank,
//        if (nameEntered.getText().toString() == "" || emailEntered.getText().toString() == "" || postcodeEntered.getText().toString() == "" || passwordEntered.getText().toString() == ""){
//            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        //TO BE ADDED: if email is the same as another user, don't allow.
//
//        if(emailEntered.getText().toString() == "everyEmail@AllUsers.com"){
//            return;
//        }
//
//        //if the password is less than 6 characters long
//        if (passwordEntered.getText().toString().length() < 6){
//            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        //if the password doesn't match the confirm password
//        if (!passwordEntered.getText().toString().equals(confirmPasswordEntered.getText().toString())){
//            return;
//        }
//
//
//
//        currentUser = new PublicUser(nameEntered.getText().toString(), postcodeEntered.getText().toString(), emailEntered.getText().toString());
//
//        formEntryIsValid = true;
//
//    }

}
