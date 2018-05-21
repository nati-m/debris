package com.example.android.debris1_1;

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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HTTPTestActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    static TextView textView;
    GetPostcodeData getPostcodeData;

    //orders
    TextView ordersTextView;
    ArrayList<Order> ordersFromThisUser;

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
    }
}
