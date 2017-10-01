package com.example.mypc.fastfoodms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ListView lstOrders;
    private ArrayList<String> Orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        lstOrders = (ListView) findViewById(R.id.lstOrders);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Orders");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Orders);
        lstOrders.setAdapter(arrayAdapter);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable <DataSnapshot> children =  dataSnapshot.getChildren();
                for(DataSnapshot child: children){
                    String refNo= child.getKey();
                    Orders.add(refNo);
                }
                //txtOrder.setText("Name: "+ name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getKey();
                Orders.add(value);
                Toast.makeText(OrdersActivity.this,value,Toast.LENGTH_LONG).show();
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }



}
