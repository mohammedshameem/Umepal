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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(QRcodeScanner.this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
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
                        finish();
                        Intent intent=new Intent(QRcodeScanner.this,Signupactivity.class);
                        intent.putExtra("reffereID",result.getText());
                        //Log.e("result ",""+result.getText());
                        //startActivityForResult(intent,1);
                        onActivityResult(1,1,intent);
                    }else {

                    }
            }
        });
        yesNoPopUp.show();
    }


}
