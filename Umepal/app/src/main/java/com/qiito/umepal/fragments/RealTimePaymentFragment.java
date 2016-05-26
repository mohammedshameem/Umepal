package com.qiito.umepal.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.Result;
import com.qiito.umepal.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by abin on 26/5/16.
 */
public class RealTimePaymentFragment extends Fragment implements ZXingScannerView.ResultHandler{

    private View view;
    private EditText memberID;
    private Button scanQRcode;
    private ZXingScannerView mScannerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.real_time_payment_page, container, false);
        initViews();

        scanQRcode.setOnClickListener(scanqrcodeListener);
        return view;

    }

    View.OnClickListener scanqrcodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           QrScanner(v);
        }
    };


    private void initViews() {
        memberID = (EditText) view.findViewById(R.id.memberIdEdittext);
        scanQRcode = (Button)view.findViewById(R.id.scanQRCodeButton);
    }
    public void QrScanner(View view){

        mScannerView = new ZXingScannerView(getActivity());
        getActivity().setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        Log.e("handler", result.getText()); // Prints scan results
        Log.e("handler", result.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();

    }
}
