package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.btventures.umepal.R;
import com.qiito.umepal.dao.CurrentlyLoggedUserDAO;
import com.qiito.umepal.managers.LoginManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

/**
 * Created by abin on 19/5/16.
 */
public class MembershipSelectionActivity extends Activity {

    private MembershipCallBackClass membershipCallback;

    private Button requestpaymentButton;
    private Button paynowButton;

    private ImageView MemAcheck;
    private ImageView MemAuncheck;
    private ImageView MemBcheck;
    private ImageView MemBuncheck;
    private ImageView MemCcheck;
    private ImageView MemCuncheck;

    private TextView membershipfeeText;

    private String MembershipId;
    private String session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membership_selection_page);
        initViews();
        initManager();
        session = CurrentlyLoggedUserDAO.getInstance().getSessionId();

        MemAuncheck.setOnClickListener(A_uncheckListener);
        MemBuncheck.setOnClickListener(B_uncheckListener);
        MemCuncheck.setOnClickListener(C_uncheckListener);

        MemAcheck.setOnClickListener(A_checkListener);
        MemBcheck.setOnClickListener(B_checkListener);
        MemCcheck.setOnClickListener(C_checkListener);

        paynowButton.setOnClickListener(paynow_Listener);



    }

    private void initManager() {
        membershipCallback=new MembershipCallBackClass();
    }

    View.OnClickListener paynow_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            LoginManager.getInstance().membershipPaypal(MembershipSelectionActivity.this,session,MembershipId,membershipCallback);

        }
    };

    View.OnClickListener A_uncheckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
              MembershipId = "1";
              MemAuncheck.setVisibility(View.GONE);
              MemAcheck.setVisibility(View.VISIBLE);

            membershipfeeText.setText("A");
        }
    };
    View.OnClickListener B_uncheckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MembershipId = "2";
            MemBuncheck.setVisibility(View.GONE);
            MemBcheck.setVisibility(View.VISIBLE);

            membershipfeeText.setText("B");
        }
    };
    View.OnClickListener C_uncheckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MembershipId = "3";
            MemCuncheck.setVisibility(View.GONE);
            MemCcheck.setVisibility(View.VISIBLE);
        }
    };
    View.OnClickListener A_checkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MemAuncheck.setVisibility(View.VISIBLE);
            MemAcheck.setVisibility(View.GONE);

            membershipfeeText.setText("C");
        }
    };
    View.OnClickListener B_checkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MemBuncheck.setVisibility(View.VISIBLE);
            MemBcheck.setVisibility(View.GONE);
        }
    };
    View.OnClickListener C_checkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MemCuncheck.setVisibility(View.VISIBLE);
            MemCcheck.setVisibility(View.GONE);
        }
    };


    private void initViews() {
        requestpaymentButton = (Button)findViewById(R.id.requestpaymentButton);
        paynowButton=(Button)findViewById(R.id.paynowButton);

        MemAcheck =(ImageView)findViewById(R.id.checkA);
        MemAuncheck = (ImageView) findViewById(R.id.uncheckA);
        MemBcheck = (ImageView)findViewById(R.id.checkB);
        MemBuncheck = (ImageView)findViewById(R.id.uncheckB);
        MemCcheck = (ImageView)findViewById(R.id.checkC);
        MemCuncheck =(ImageView)findViewById(R.id.uncheckC);

        membershipfeeText = (TextView)findViewById(R.id.membershipfeeText);
    }

private class MembershipCallBackClass implements AsyncTaskCallBack{

    @Override
    public void onFinish(int responseCode, Object result) {

    }

    @Override
    public void onFinish(int responseCode, String result) {

    }
}
}
