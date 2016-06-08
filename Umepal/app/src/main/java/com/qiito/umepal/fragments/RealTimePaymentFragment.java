package com.qiito.umepal.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.qiito.umepal.R;
import com.qiito.umepal.activity.QRcodeScanner;
import com.qiito.umepal.activity.TransferPaymentActivity;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.listeners.ClickListener;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.RealTimePaymentManager;
import com.qiito.umepal.util.YesNoPopUp;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by abin on 26/5/16.
 */
@SuppressLint("ValidFragment")
public class RealTimePaymentFragment extends Fragment implements ZXingScannerView.ResultHandler{

    private View view;
    private EditText memberID;
    private Button scanQRcode;
    private ZXingScannerView mScannerView;
    private TextView verifyText;
    private VerifyCallBack verifyCallBack;
    private Intent intent;
    private String memberid;
    private String memberId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.real_time_payment_page, container, false);
        initViews();
        memberID.setText(memberId);
        scanQRcode.setOnClickListener(scanqrcodeListener);
        verifyText.setOnClickListener(verifyListener);
        return view;

    }

     public RealTimePaymentFragment(){}

      public RealTimePaymentFragment(String a){
          this.memberId = a;
      }

    View.OnClickListener scanqrcodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent cam = new Intent(getActivity(), QRcodeScanner.class);
            cam.putExtra("from","fromRealTimePayment");
            startActivityForResult(cam,1);

        }
    };

    View.OnClickListener verifyListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Log.e("PARAMS >> ",DbManager.getInstance().getSessionId() +"  &&  "+memberID.getText().toString());
            RealTimePaymentManager.getInstance().verifyMember(getActivity(), DbManager.getInstance().getSessionId(),memberID.getText().toString(),verifyCallBack);

        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initViews() {
        memberID = (EditText) view.findViewById(R.id.memberIdEdittext);
        scanQRcode = (Button)view.findViewById(R.id.scanQRCodeButton);
        mScannerView = new ZXingScannerView(getActivity());
        verifyText = (TextView) view.findViewById(R.id.verify_text);
        verifyCallBack = new VerifyCallBack();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result result) {

        mScannerView.stopCamera();
        YesNoPopUp yesNoPopUp=new YesNoPopUp("ID: "+result.getText(), getActivity(), new ClickListener<Boolean>() {
            @Override
            public void onClick(Boolean aBoolean) {
                if(aBoolean){

                    memberID.setText(result.getText());

                    mScannerView.stopCamera();
                }else {
                    onResume();
                }
            }
        });
        yesNoPopUp.show();


    }

    private class VerifyCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {
            UserBaseHolder userBaseHolder = (UserBaseHolder) result;

            Log.e("inside","verify call back");
            if (userBaseHolder.getStatus().equalsIgnoreCase("success")){
                Toast.makeText(getActivity(),"verified",Toast.LENGTH_LONG).show();
                StringBuilder sb = new StringBuilder();
                if (userBaseHolder.getData().getUser().getFirstName() != ""){
                    sb.append(userBaseHolder.getData().getUser().getFirstName() + " ");
                    if (userBaseHolder.getData().getUser().getLastName() != ""){
                        sb.append(userBaseHolder.getData().getUser().getLastName());
                    }
                }
                Intent intent = new Intent(getActivity(), TransferPaymentActivity.class);
                intent.putExtra("name", sb.toString());
                intent.putExtra("member_id",userBaseHolder.getData().getUser().getId());
                startActivity(intent);
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }


}
