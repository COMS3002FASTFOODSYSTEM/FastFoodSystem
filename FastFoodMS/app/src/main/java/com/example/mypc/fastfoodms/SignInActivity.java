package com.example.mypc.fastfoodms;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText edtEmail ;
    private EditText edtPassword;
    private Button btnSignIn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String restaurantAddress;
    int PLACE_PICKER_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignin);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    Intent intent;

                    try {
                        intent = builder.build(SignInActivity.this);
                        startActivityForResult(intent,PLACE_PICKER_REQUEST);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                   //startActivity(new Intent(SignInActivity.this,MapsActivity.class));
                }
            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startSignIn();
            }
        });

    }
    @Override
    protected void onActivityResult(int reqCode, int resCode,Intent data ){
        if(reqCode==PLACE_PICKER_REQUEST){
            if(resCode== RESULT_OK){
                Place restuarant = PlacePicker.getPlace(data,this);
                restaurantAddress = String.format("%s",restuarant.getAddress());
            }
        }
        startActivity(new Intent(SignInActivity.this,AccountActivity.class));
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
            Toast.makeText(SignInActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this, "Email or password incorrect", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
