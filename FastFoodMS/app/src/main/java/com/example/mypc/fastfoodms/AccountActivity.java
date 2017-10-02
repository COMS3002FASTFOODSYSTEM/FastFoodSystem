package com.example.mypc.fastfoodms;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.PaymentInstrumentType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private CheckBox chkPizza;
    private CheckBox chkBurger;
    private CheckBox chkHotWings;
    private CheckBox chkHotDog;
    private Button btnCheckOut;
    public static double Total=0.0;
    public static String refNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders");
        chkBurger = (CheckBox) findViewById(R.id.chkBurger);
        chkHotDog = (CheckBox) findViewById(R.id.chkHotDog);
        chkHotWings = (CheckBox) findViewById(R.id.chkWings);
        chkPizza = (CheckBox) findViewById(R.id.chkPizza);

        btnCheckOut = (Button) findViewById(R.id.btnCheckOut);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();
                //startActivity(new Intent(AccountActivity.this, PaymentActivity.class));
            }
        });
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(mToolbar);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 refNo = dataSnapshot.getKey();
                //Toast.makeText(AccountActivity.this,value,Toast.LENGTH_LONG).show();
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
        });

    }

    public void placeOrder() {
        ArrayList<String> FoodItems = new ArrayList<>();
        ArrayList<HashMap> Order = new ArrayList<>();
        double price;
        //double Total=0.0;
        int counter=0;
        HashMap<String, String> dataMap = new HashMap<String, String>();
        HashMap<String, HashMap> IntermediateData = new HashMap<>();
        if(chkBurger.isChecked()){
            Total = Total+50;
            counter++;
            FoodItems.add("Burger");
            dataMap.put("FoodItem","Burger");
            dataMap.put("Price","50");
            IntermediateData.put("Item"+counter,dataMap);
        }
        if(chkPizza.isChecked()){
            Total = Total+120;
            counter++;
            FoodItems.add("Pizza");
            dataMap.put("FoodItem","Pizza");
            dataMap.put("Price","120");
            IntermediateData.put("Item"+counter,dataMap);
        }
        if(chkHotWings.isChecked()){
            Total = Total+50;
            counter++;
            FoodItems.add("Hot Wings");
            dataMap.put("FoodItem","HotWings");
            dataMap.put("Price","50");
            IntermediateData.put("Item"+counter,dataMap);
        }
        if(chkHotDog.isChecked()){
            Total = Total+40;
            counter++;
            FoodItems.add("Hot Dog");
            dataMap.put("FoodItem","Hot Dog");
            dataMap.put("Price","40");
            IntermediateData.put("Item"+counter,dataMap);
        }

        mDatabase.push().setValue(IntermediateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AccountActivity.this,"Your order has been placed...",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AccountActivity.this, PaymentActivity.class));
                }else{
                    Toast.makeText(AccountActivity.this,"Error...",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater MenuInflater = getMenuInflater();
        MenuInflater.inflate(R.menu.my_menu,menu);
        return true;//super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            logout();
            startActivity(new Intent(AccountActivity.this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        mAuth.signOut();
    }
}
