package com.qiito.umepal.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.facebook.FacebookSdk;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.LoginManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

public class Loginactivity extends Activity {


    private LoginCallBackClass loginCallBackClass;
    private Dialog dialogTransparent;
    private View progressview;
    private Button loginButton;
    private Button signupButton;
    private TextView forgotPassword;
    private EditText email;
    private EditText passwordsEdtTxt;
    private String Email;
    private String Password;
    private int loginforbuy;
    private int productId;
    private int id = 0;
    private int Productid = 0;

    private int requestcode = 1;

    private boolean emailFlag;
    private boolean passwordEmptyFlag;

    private String appLinkURL = "https://fb.me/999378886770057";
    private String previewImageURL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        FacebookSdk.sdkInitialize(this);
        initView();
        initManager();
        Intent intent = getIntent();
        loginforbuy = intent.getIntExtra("buy", id);
        productId = intent.getIntExtra("productId", id);

        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                email.setHint("");
                passwordsEdtTxt.setHint("Password");
                return false;
            }
        });

        passwordsEdtTxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                passwordsEdtTxt.setHint("");
                email.setHint("Email");
                return false;
            }
        });

        /*LOGIN BUTTON*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdetail();
            }
        });

        /*FORGOT PASSWPRD BUTTON*/
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgot = new Intent(Loginactivity.this, Forgotpassword.class);
                startActivity(forgot);

            }
        });

        /*SIGNUP BUTTON*/
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(Loginactivity.this, Signupactivity.class);
                signup.putExtra("buy",loginforbuy);
                signup.putExtra("productId",productId);
                startActivity(signup);

            }
        });

    }

    private void initManager() {

        loginCallBackClass = new LoginCallBackClass();
    }

    private void initView() {

        email = (EditText) findViewById(R.id.emailEditText);
        passwordsEdtTxt = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.submitButton);
        forgotPassword = (TextView) findViewById(R.id.forgotPasswordText);
        signupButton = (Button) findViewById(R.id.signupButton);

    }

    private void checkdetail() {

        if (!email.getText().toString().equalsIgnoreCase("")) {

            emailFlag = true;
            Email = email.getText().toString();
        } else {
            emailFlag = false;
            email.setError("Enter UmeId");
        }
        if (passwordsEdtTxt.getText().length() == 0) {
            passwordEmptyFlag = false;
        } else {
            passwordEmptyFlag = true;
            Password = passwordsEdtTxt.getText().toString();
        }


        /*CORRECT*/
        if (emailFlag && passwordEmptyFlag) {
            dialogTransparent = new Dialog(this, android.R.style.Theme_Black);
            progressview = LayoutInflater.from(this).inflate(R.layout.progrssview, null);
            dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
            dialogTransparent.setContentView(progressview);
            dialogTransparent.show();

            LoginManager.getInstance().emailLogin(Loginactivity.this, Email, Password, loginCallBackClass, requestcode);
        }
    }


    public class LoginCallBackClass implements AsyncTaskCallBack {


        @Override
        public void onFinish(int responseCode, Object result) {

            final UserBaseHolder userBaseHolder = (UserBaseHolder) result;
            if (UtilValidate.isNotNull(userBaseHolder)) {

                if (userBaseHolder.getStatus().equalsIgnoreCase("failure")) {
                    dialogTransparent.dismiss();
                    Toast.makeText(Loginactivity.this, userBaseHolder.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (userBaseHolder.getStatus().equalsIgnoreCase("success")) {

                    dialogTransparent.dismiss();
                    DbManager.getInstance().deleteCurrentlyLoggedUserTable();
                    DbManager.getInstance().insertIntoCurrentUser(userBaseHolder.getUser().getId(),
                            userBaseHolder.getUser().getSession_id());
                    DbManager.getInstance().deleteAllRowsFromUserTable();
                    DbManager.getInstance().insertIntoUserTable(userBaseHolder.getUser());
                    DbManager.getInstance().deleteShippingData();

                    if (UtilValidate.isNotNull(userBaseHolder)) {

                        if (UtilValidate.isNotNull(userBaseHolder.getUser())) {

                            if (loginforbuy == 1) {
                                finish();
                            } else {
                                Intent i = new Intent(Loginactivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }

                        }

                    }
                } else {
                    dialogTransparent.dismiss();
                    Toast.makeText(Loginactivity.this, userBaseHolder.getMessage(), Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(Loginactivity.this, "Please try again ", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int responseCode, String result) {

            dialogTransparent.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent(Loginactivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
