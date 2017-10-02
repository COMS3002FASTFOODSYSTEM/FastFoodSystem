package com.example.mypc.fastfoodms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {
    private Button btnPayment;
    private TextView txtRefNo, txtTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        txtRefNo = (TextView) findViewById(R.id.txtRef);
        txtTotal = (TextView) findViewById(R.id.txtTotalCost);
        txtRefNo.setText( "Reference No:"+AccountActivity.refNo);
        txtTotal.setText("Total Cost: R"+AccountActivity.Total);
        btnPayment = (Button) findViewById(R.id.btnPay);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
