package com.example.mypc.fastfoodms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentActivity extends AppCompatActivity {
    private Button btnPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btnPayment = (Button) findViewById(R.id.btnPay);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AccountActivity accountActivity = new AccountActivity();
               // accountActivity.placeOrder();
            }
        });
    }
}
