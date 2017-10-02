package com.example.mypc.fastfoodms;

import android.accounts.Account;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        TextView txtRefNo = (TextView) findViewById(R.id.txtRef);
        TextView txtTotal = (TextView) findViewById(R.id.txtTotalCost);
        final TextView txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtRefNo.setText( "Reference No:"+AccountActivity.refNo);
        txtTotal.setText("Total Cost: R"+AccountActivity.Total);
        final Button btnPayment = (Button) findViewById(R.id.btnPay);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    switch (MainActivity.page){
                        case 0:{
                            txtAddress.setText("The Address to your restuarant:\n"+MainActivity.resName+'\n'+MainActivity.restaurantAddress);
                            Toast.makeText(PaymentActivity.this,"The Address to your restuarant:\n"+MainActivity.resName+'\n'+MainActivity.restaurantAddress,Toast.LENGTH_LONG).show();
                            break;
                        }
                        case 1:{
                            txtAddress.setText("The Address to your restuarant:\n"+SignInActivity.resName+'\n'+SignInActivity.restaurantAddress);
                            Toast.makeText(PaymentActivity.this,"The Address to your restuarant:\n"+SignInActivity.restaurantAddress,Toast.LENGTH_LONG).show();
                            break;
                        }
                        case 2:{
                            txtAddress.setText("The Address to your restuarant:\n"+SignUpActivity.resName+'\n'+SignUpActivity.restaurantAddress);
                            Toast.makeText(PaymentActivity.this,"The Address to your restuarant:\n"+SignUpActivity.restaurantAddress,Toast.LENGTH_LONG).show();
                            break;
                        }

                    }


            }
        });
    }
}
