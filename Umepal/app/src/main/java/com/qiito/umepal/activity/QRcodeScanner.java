package com.qiito.umepal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.google.zxing.Result;
import com.qiito.umepal.R;
import com.qiito.umepal.listeners.ClickListener;
import com.qiito.umepal.util.YesNoPopUp;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * Created by abin on 31/5/16.
 */
public class QRcodeScanner extends Activity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private Signupactivity signupactivity;
    private String androidId;
    private String FirstName;
    private String LastName;
    private String Email;
    private String CEA;
    private String MOBILE;
    private String BANK;
    private String ESTATEAGENCY;
    private String BankAccount;
    private String Password;
    private String from;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView = new ZXingScannerView(QRcodeScanner.this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        Intent intent=getIntent();
        FirstName=intent.getStringExtra("Name");
        Email=intent.getStringExtra("Email");
        CEA=intent.getStringExtra("CEA");
        MOBILE=intent.getStringExtra("Mobile");
        BANK=intent.getStringExtra("Bank");
        ESTATEAGENCY=intent.getStringExtra("Estateagency");
        BankAccount=intent.getStringExtra("BankAccount");
        Password=intent.getStringExtra("Password");
        from=intent.getStringExtra("from");
        Log.e("Cam ::","resume ");
    }

    @Override
    public void handleResult(final Result result) {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(QRcodeScanner.this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();*/

        mScannerView.stopCamera();
        YesNoPopUp yesNoPopUp=new YesNoPopUp("ID: "+result.getText(), QRcodeScanner.this, new ClickListener<Boolean>() {
            @Override
            public void onClick(Boolean aBoolean) {
                    if(aBoolean){

                        if(from.equalsIgnoreCase("fromRealTimePayment")){


                            Intent intent = new Intent();
                            intent.putExtra("reffereID",result.getText());
                           //setResult(RESULT_OK, intent);
                            startActivityForResult(intent,1);
                            finish();


                        }else {
                            finish();
                            Intent intent=new Intent(QRcodeScanner.this,Signupactivity.class);
                            intent.putExtra("reffereID",result.getText());
                            intent.putExtra("Name",FirstName);
                            intent.putExtra("Email",Email);
                            intent.putExtra("CEA",CEA);
                            intent.putExtra("Mobile",MOBILE);
                            intent.putExtra("Bank",BANK);
                            intent.putExtra("Estateagency",ESTATEAGENCY);
                            intent.putExtra("BankAccount",BankAccount);
                            intent.putExtra("Password",Password);
                            //Log.e("result ",""+result.getText());
                            //startActivityForResult(intent,1);
                            //onActivityResult(1,1,intent);
                            startActivity(intent);
                        }

                        mScannerView.stopCamera();
                    }else {
                       onResume();
                    }
            }
        });
        yesNoPopUp.show();
    }


}
