package com.example.mypc.fastfoodms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private ProgressDialog progress;
    private Button btnSignUp;
    private TextView txtSignIn;

    private FirebaseAuth mAuth;


    int PLACE_PICKER_REQUEST=1;
    private String restaurantAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        progress = new ProgressDialog(this);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtName  =(EditText) findViewById(R.id.edtName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignup);
        txtSignIn = (TextView) findViewById(R.id.txtSignin);
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(signInIntent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUp();
            }
        });
    }

    private void startSignUp() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password  = edtPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progress.setMessage("Signing Up ...");
            progress.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progress.dismiss();
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        Intent intent;
                        try {
                            intent = builder.build(SignUpActivity.this);
                            startActivityForResult(intent,PLACE_PICKER_REQUEST);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
                        //Intent mainIntent = new Intent(SignUpActivity.this, MainActivity.class);
                        //mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //startActivity(mainIntent);
                    }
                }
            });
        }else{
            Toast.makeText(SignUpActivity.this,"Please ensure that you do not have an empty field",Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityReturn(int reqCode, int resCode,Intent data ){
        if(reqCode==PLACE_PICKER_REQUEST){
            if(resCode== RESULT_OK){
                Place restuarant = PlacePicker.getPlace(data,this);
                restaurantAddress = String.format("%s",restuarant.getAddress());
            }
        }
    }
}
