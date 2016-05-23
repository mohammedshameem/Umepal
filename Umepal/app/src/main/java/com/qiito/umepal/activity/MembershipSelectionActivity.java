package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.btventures.umepal.R;

/**
 * Created by abin on 19/5/16.
 */
public class MembershipSelectionActivity extends Activity {


    private String Name;
    private String Email;
    private String CEA;
    private String Mobile;
    private String Bank;
    private String Estateagency;
    private String BankAccount;
    private String RefferalID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membership_selection_page);
        getIntentValue();
    }

    private void getIntentValue() {
        Intent intent=getIntent();
        Name = intent.getStringExtra("Name");
        Email = intent.getStringExtra("Email");
        CEA = intent.getStringExtra("CEA");
        Mobile = intent.getStringExtra("Mobile");
        Bank = intent.getStringExtra("Bank");
        Estateagency = intent.getStringExtra("Estateagency");
        BankAccount = intent.getStringExtra("BankAccount");
        RefferalID = intent.getStringExtra("RefferalID");
    }
}
