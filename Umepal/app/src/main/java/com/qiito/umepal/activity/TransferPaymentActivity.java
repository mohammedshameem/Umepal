package com.qiito.umepal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiito.umepal.R;

/**
 * Created by shiya on 3/6/16.
 */
public class TransferPaymentActivity extends Activity {

    private EditText amountEditText;
    private Button transferButton;
    private Button payNowButton;
    private TextView makePaymentText;
    private TextView amountText;
    private TextView heading;
    private LinearLayout backIcon;
    private RelativeLayout transferLayout;
    private RelativeLayout makePaymentLayout;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_layout);
        initViews();
        setVisibility();
        if (getIntent().hasExtra("name")) {
            name = getIntent().getStringExtra("name");
        }
        transferButton.setOnClickListener(transferListener);
        backIcon.setOnClickListener(backListener);
        payNowButton.setOnClickListener(payListener);

    }

    private void initViews() {

        amountEditText = (EditText) findViewById(R.id.amount_editText);
        transferButton = (Button) findViewById(R.id.transfer_button);
        payNowButton = (Button) findViewById(R.id.pay_now_button);
        makePaymentText = (TextView) findViewById(R.id.make_payment_text);
        amountText = (TextView) findViewById(R.id.amount_text);
        transferLayout = (RelativeLayout) findViewById(R.id.transfer_amount_layout);
        makePaymentLayout = (RelativeLayout) findViewById(R.id.make_payment_layout);
        heading = (TextView) findViewById(R.id.page_heading);
        heading.setVisibility(View.VISIBLE);
        backIcon = (LinearLayout) findViewById(R.id.back_icon_with_app_image);
    }

    private void setVisibility() {
        transferLayout.setVisibility(View.VISIBLE);
        makePaymentLayout.setVisibility(View.GONE);
        heading.setText("Transfer");
    }

    View.OnClickListener transferListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            transferLayout.setVisibility(View.GONE);
            makePaymentLayout.setVisibility(View.VISIBLE);
            heading.setText("Confirm Transaction");
            heading.setTextSize(22);
            makePaymentText.setText("Make a payment to " + name);
            amountText.setText(getString(R.string.dollar)+ ""+amountEditText.getText().toString());

        }
    };

    View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (transferLayout.getVisibility() == View.VISIBLE){
                finish();
            }else if (makePaymentLayout.getVisibility() == View.VISIBLE){
                setVisibility();
            }

        }
    };
    View.OnClickListener payListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}
