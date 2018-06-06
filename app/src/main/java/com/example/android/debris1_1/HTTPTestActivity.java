package com.example.android.debris1_1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class ValidPostcodeTask extends AsyncTask<Void, Void, Void> {

    private Exception exception;

    private boolean validPostcode = false;
    private String postcode;

    protected void setPostcode(String postcode){
        this.postcode = postcode;
    }

    protected Void doInBackground(Void... voids) {
        try {
            String urlString = "https://api.postcodes.io/postcodes/" + postcode + "/validate/";
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            String jsonData = "";
            InputStream inputStream = httpURLConnection.getInputStream();
            //TODO See if there's another way to do this, I think it might be the issue
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                if(line!=null) {
                    jsonData = jsonData + line;
                }
            }

            JSONParser parser = new JSONParser();



            Object object = new JSONParser().parse(jsonData);
                JSONObject jsonObject = (JSONObject) object;

                validPostcode = (boolean) jsonObject.get("result");



        } catch (Exception e) {
            this.exception = e;
            validPostcode = false;
           // HTTPTestActivity.textView.setText("GeneralException");
            return null;
        }

        return null;
    }


    protected void onPostExecute(Void aVoid) {

        if(validPostcode) {
            HTTPTestActivity.textView2.setText("Valid");
            validPostcode = false;
        } else {
            HTTPTestActivity.textView2.setText("Not Valid");
        }

    }
}

public class HTTPTestActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    static TextView textView;
    GetPostcodeData getPostcodeData;

    //orders
    TextView ordersTextView;
    ArrayList<Order> ordersFromThisUser;

    //new
    Button button2;
    EditText editText2;
    static TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);

        editText = findViewById(R.id.edittext_http_test);
        button = findViewById(R.id.button_http_test);
        textView = findViewById(R.id.http_test_text_view);
        ordersTextView = findViewById(R.id.orders_test_http_test_view);



        DatabaseReference ordersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child("unconfirmed");
        String uid = Control.CONTROL.getCurrentUser().getFirebaseUid();

        ordersFromThisUser = new ArrayList<>();
        ordersDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                 Order order = dataSnapshot.getValue(Order.class);
                 ordersFromThisUser.add(order);

                Order order1 = ordersFromThisUser.get(0);
                String orders = "Skip Type " + order1.getSkipTypeString() + " Date " + order1.getDateOfSkipArrivalString() + " postcode " + order1.getPostCode();
                ordersTextView.setText(orders);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


//                .addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot child : dataSnapshot.getChildren()){
//
//                            Order order = child.getValue(Order.class);
//                            ordersFromThisUser.add(order);
//                        }
//                        //When the data is finished loading, THEN render the page
//
//                        Order order1 = ordersFromThisUser.get(0);
//                        String orders = "Skip Type " + order1.getSkipTypeString() + " Date " + order1.getDateOfSkipArrivalString() + " postcode " + order1.getPostCode();
//                        ordersTextView.setText(orders);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                }
//        );
//


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postcode = editText.getText().toString();
                String urlString = "http://www.simplylookupadmin.co.uk/JSONservice/JSONSearchForAddress.aspx?datakey=W_B7C4975860B64332998F4398F8DBA0&postcode=" + postcode;
                try {
                    URL url = new URL(urlString);
                    getPostcodeData = new GetPostcodeData(url);
                    getPostcodeData.execute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        button2 = findViewById(R.id.isItValidHTTPTestButton);
        editText2 = findViewById(R.id.isItValidHTTPTestEditText);
        textView2 = findViewById(R.id.isItValidHTTPTestTextView);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postcode = editText2.getText().toString();
                if(postcode==""){return;}
                boolean valid;

                ValidPostcodeTask validPostcodeTask = new ValidPostcodeTask();
                validPostcodeTask.setPostcode(postcode);
                validPostcodeTask.execute();
            }
        });
    }
}
