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

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private Button btnRegister;
    private Button btnAlreadyRegistered;
    private Button btnSkipRegistration;
    private Button btnRestaurant;
    int PLACE_PICKER_REQUEST=1;
    public static String restaurantAddress;
    public static String resName;
    public static int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar3);
        //setSupportActionBar(mToolbar);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                   /*Intent loginIntent= new Intent(MainActivity.this,SignUpActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);*/

                }else{
                    //startActivity(new Intent(MainActivity.this, MapsActivity.class));
                    //use place picker
                    /*PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    Intent intent;
                    try {
                        intent = builder.build(MainActivity.this);
                        startActivityForResult(intent,PLACE_PICKER_REQUEST);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        };
        //startActivity(new Intent(MainActivity.this, SignInActivity.class));
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnAlreadyRegistered= (Button) findViewById(R.id.btnAlreadyRegistered);
        btnSkipRegistration = (Button) findViewById(R.id.btnNoRegistration);
        btnRestaurant = (Button) findViewById(R.id.btnRes);

        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page =2;
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });

        btnAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=1;
                startActivity(new Intent(MainActivity.this,SignInActivity.class));
                //startActivity(new Intent(MainActivity.this,OrdersActivity.class));
            }
        });

        btnSkipRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page =0;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent = builder.build(MainActivity.this);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int reqCode, int resCode,Intent data ){
        if(reqCode==PLACE_PICKER_REQUEST){
            if(resCode== RESULT_OK){
                Place restuarant = PlacePicker.getPlace(data,this);
                resName = String.format("%s",restuarant.getName());
                restaurantAddress = String.format("%s",restuarant.getAddress());
            }
        }
        startActivity(new Intent(MainActivity.this,AccountActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


}
