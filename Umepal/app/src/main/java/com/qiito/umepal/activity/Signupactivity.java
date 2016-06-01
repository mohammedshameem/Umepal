package com.qiito.umepal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.Result;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.LoginManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class Signupactivity extends Activity implements ZXingScannerView.ResultHandler {

    private SignUpCallBack signUpCallBack;
    private LoginCallBackClass loginCallBackClass;

    private LinearLayout loginWithFacebok;

    // private Button loginButton;
    // private Button signupButton;
    private Button cancelButton;
    private Button scanQRquoteButton;
    private Button nextButton;
    private ImageButton uploadPicButton;

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText ceaEditText;
    private EditText mobileEditText;
    private EditText bankEditText;
    private EditText estateagencyEditText;
    private EditText bankaccountEditText;
    private EditText refferalmemberidEditText;
    private EditText password;


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
    private String refferalmemberID;
    private String profilePic;
    private ZXingScannerView mScannerView;

    private final int requestCode = 200;
    private int requestcode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);
        initView();
        initManager();

        androidId = Secure.getString(getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.HeaderRed));
        }

        /* SIGN UP */

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FirstName = nameEditText.getText().toString();
                Email = emailEditText.getText().toString();
                CEA = ceaEditText.getText().toString();
                MOBILE = mobileEditText.getText().toString();
                BANK = bankEditText.getText().toString();
                ESTATEAGENCY = estateagencyEditText.getText().toString();
                BankAccount = bankaccountEditText.getText().toString();
                refferalmemberID = refferalmemberidEditText.getText().toString();
                Password = password.getText().toString();

                CHECKDETAILS();


            }
        });

        /*LOGIN*/

       /* loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent login=new Intent(Signupactivity.this,Loginactivity.class);
                startActivity(login);
                finish();
            }
        });*/

        /* LOGIN WITH FACEBOOK */

       /* loginWithFacebok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent fb=new Intent(Signupactivity.this,Loginwithfacebook.class);
                startActivity(fb);
                finish();
            }
        });*/
        scanQRquoteButton.setOnClickListener(scanQRcodeListener);
        cancelButton.setOnClickListener(cancelListener);
    }

    private void CHECKDETAILS() {
        LastName = "";


        LoginManager.getInstance().emailSignup(Signupactivity.this, signUpCallBack, FirstName, LastName,
                Email, CEA, MOBILE, BANK, ESTATEAGENCY, BankAccount, Password, androidId, refferalmemberID, profilePic, requestCode);

    }

    View.OnClickListener cancelListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //  finish();
            Intent intent = new Intent(Signupactivity.this, MembershipSelectionActivity.class);
            startActivity(intent);
        }
    };
   /* View.OnClickListener scanQRcodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            QrScanner(v);
        }
    };*/
    View.OnClickListener scanQRcodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            QrScanner(v);
            //   Intent cam = new Intent(Signupactivity.this,QRcodeScanner.class);
            // startActivity(cam);
              /*  QRcodeScanner qRcodeScanner= new QRcodeScanner();
                qRcodeScanner.onClick(v);*/
        }
    };
    public void QrScanner(View view) {


        Signupactivity.this.setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    private void initView() {

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        ceaEditText = (EditText) findViewById(R.id.ceaEditText);
        mobileEditText = (EditText) findViewById(R.id.mobileEditText);
        bankEditText = (EditText) findViewById(R.id.bankEditText);
        estateagencyEditText = (EditText) findViewById(R.id.estateagencyEditText);
        bankaccountEditText = (EditText) findViewById(R.id.bankaccountEditText);
        refferalmemberidEditText = (EditText) findViewById(R.id.refferalmemberidEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        scanQRquoteButton = (Button) findViewById(R.id.scanQRquoteButton);
        nextButton = (Button) findViewById(R.id.nextButton);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_signupactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initManager() {
        signUpCallBack = new SignUpCallBack();
        loginCallBackClass = new LoginCallBackClass();
        mScannerView = new ZXingScannerView(Signupactivity.this);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        if (mScannerView != null) {
            if (mScannerView.isShown()) {
                Log.e("camera ", "shown");
                mScannerView.stopCamera();
                Intent intent = new Intent(Signupactivity.this, Signupactivity.class);
                startActivity(intent);
            } else {
                Log.e("camera ", "hide");

                finish();
            }

        }
    }


    @Override
    public void handleResult(Result result) {
       /* AlertDialog.Builder builder = new AlertDialog.Builder(Signupactivity.this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();*/
        mScannerView.stopCamera();
        refferalmemberidEditText.setText(result.getText());

    }

    public class SignUpCallBack implements AsyncTaskCallBack {


        @Override
        public void onFinish(int responseCode, Object result) {
            UserBaseHolder userBaseHolder = (UserBaseHolder) result;
            if (userBaseHolder.getStatus().equals("success")) {
                Log.e("", "In callBack success");

                if (userBaseHolder != null) {


                    DbManager.getInstance().deleteCurrentlyLoggedUserTable();

                    DbManager.getInstance().insertIntoCurrentUser(userBaseHolder.getData().getUser().getId(),
                            userBaseHolder.getData().getSession_id());

                    DbManager.getInstance().deleteUserTable();


                    DbManager.getInstance().insertIntoUserTable(userBaseHolder.getData().getUser());

                    Toast.makeText(getApplicationContext(), "Signup Success", Toast.LENGTH_LONG).show();

                    //  LoginManager.getInstance().emailLogin(Signupactivity.this, Email, Password, loginCallBackClass, requestcode);

                    Intent next = new Intent(Signupactivity.this, MembershipSelectionActivity.class);
                    startActivity(next);
                }
            } else if (userBaseHolder.getStatus().equals("error")) {
                Toast.makeText(Signupactivity.this, userBaseHolder.getMessage(), Toast.LENGTH_LONG).show();
            }


        }

        @Override
        public void onFinish(int responseCode, String result) {
            Toast.makeText(Signupactivity.this, "" + result, Toast.LENGTH_SHORT).show();
        }
    }

    public class LoginCallBackClass implements AsyncTaskCallBack {


        @Override
        public void onFinish(int responseCode, Object result) {

            UserBaseHolder userBaseHolder = (UserBaseHolder) result;
            if (UtilValidate.isNotNull(userBaseHolder)) {

                if (userBaseHolder.getStatus().equalsIgnoreCase("error")) {


                } else {

                    DbManager.getInstance().deleteCurrentlyLoggedUserTable();
                    DbManager.getInstance().insertIntoCurrentUser(userBaseHolder.getData().getUser().getId(),
                            userBaseHolder.getData().getSession_id());
                    DbManager.getInstance().deleteAllRowsFromUserTable();
                    DbManager.getInstance().insertIntoUserTable(userBaseHolder.getData().getUser());
                    DbManager.getInstance().deleteShippingData();
                    if (userBaseHolder.getData().getUser().getShipping_address() != null) {
                        DbManager.getInstance().insertintoShippingTable(userBaseHolder.getData().getUser().getShipping_address());

                    }

                    Intent intent = new Intent(Signupactivity.this, MainActivity.class);

                    startActivity(intent);

                }
                finish();
            } else {
                //Toast.makeText(Loginactivity.this,"Please try again ",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int responseCode, String result) {


        }
    }

}
