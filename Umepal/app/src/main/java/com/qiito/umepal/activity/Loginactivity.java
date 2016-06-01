package com.qiito.umepal.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.facebook.FacebookSdk;
import com.facebook.applinks.AppLinkData;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
/*import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;*/
import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.Utilvalidate.Utils;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.LoginManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import java.util.List;

import bolts.AppLinks;


public class Loginactivity extends Activity {


    private LoginCallBackClass loginCallBackClass;
    private UserBaseHolder userBaseHolder;

    private Dialog dialogTransparent;
    private View progressview;

    private Button loginButton;
    private Button signupButton;

    private TextView forgotPassword;

    private EditText email;
    private EditText passwordsEdtTxt;
//    private TextView skip;
    private String Email;
    private String Password;
    private int loginforbuy;
    private int productId;
    private int id = 0;
    private int Productid = 0;

 //   private LinearLayout loginWithFacebook;
    private int requestcode = 1;

    private boolean emailEmptyFlag;
    private boolean emailFlag;
    private boolean passwordEmptyFlag;
    private boolean passwordFlag;

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
//        skip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

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
                startActivity(signup);

            }
        });

         /*LOGINWITH FACEBOOK BUTTON*/
       /* loginWithFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fb = new Intent(Loginactivity.this, Loginwithfacebook.class);
                startActivity(fb);
                finish();
            }
        });*/

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
   //     loginWithFacebook = (LinearLayout) findViewById(R.id.facebook_login_layout);
   //     skip = (TextView) findViewById(R.id.skip);

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
                    //fbInvite();
                   // Toast.makeText(Loginactivity.this, userBaseHolder.getMessage(), Toast.LENGTH_SHORT).show();

                    // Log.e("zzz",""+userBaseHolder.getData().getMember_status().getMembershipname());
              /*  if(UtilValidate.isNotNull(userBaseHolder.getData().getUser().getMember_status())){
                    DbManager.getInstance(). deleteMembershipdata();
                    DbManager.getInstance().insertintoMembership(userBaseHolder.getData().getUser().getMember_status());
                }*/
                    dialogTransparent.dismiss();
                    DbManager.getInstance().deleteCurrentlyLoggedUserTable();
                    DbManager.getInstance().insertIntoCurrentUser(userBaseHolder.getUser().getId(),
                            userBaseHolder.getUser().getSession_id());
                    DbManager.getInstance().deleteAllRowsFromUserTable();
                    DbManager.getInstance().insertIntoUserTable(userBaseHolder.getUser());
                    DbManager.getInstance().deleteShippingData();
                  //  TodaysParentApp.setShippingAddress(userBaseHolder.getUser().getShipping_address());
                   /* if(userBaseHolder.getData().getUser().getShipping_address() != null) {
                        DbManager.getInstance().insertintoShippingTable(userBaseHolder.getData().getUser().getShipping_address());

                    }*/
                    if (UtilValidate.isNotNull(userBaseHolder)) {

                        if (UtilValidate.isNotNull(userBaseHolder.getUser())) {

                            /**
                             * PARSE TABLE USER_ID FIELD UPDATION ......
                             */

                            /**
                             * Retrieving all rows from parse based on user_id ...
                             */
/*
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("_Installation");
                            query.whereEqualTo("device_id", Utils.getUniqueDeviceId(Loginactivity.this) + "");
                            query.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> parseList, ParseException e) {
                                    // TODO Auto-generated method stub
                                    if (e == null) {

                                        Log.d("", "Retrieved Parse List size " + parseList.size());

                                        for (ParseObject myObject : parseList) {

                                            myObject.remove("device_id");
                                            myObject.deleteInBackground();

                                        }


                                    } else {

                                        Log.d("", "Error ##### " + e.getMessage());
                                    }
                                }
                            });*/

                          /*  Log.d("LOG", "user_id >>" + userBaseHolder.getUser().getId());
                            Log.d("LOG", "device_id >>" + Utils.getUniqueDeviceId(Loginactivity.this));
                            final ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                            installation.put("user_id", userBaseHolder.getUser().getId());
                            installation.put("device_id", Utils.getUniqueDeviceId(Loginactivity.this));
                            Log.e("device id>>>>",""+Utils.getUniqueDeviceId(Loginactivity.this));
                            installation.saveInBackground();*/
/*
                            installation.saveInBackground(new SaveCallback() {

                                @Override
                                public void done(ParseException arg0) {
                                    // TODO Auto-generated method stub
                                    installation.put("user_id", userBaseHolder.getUser().getId());
                                    installation.put("device_id", Utils.getUniqueDeviceId(Loginactivity.this));
                                    installation.saveInBackground();
                                }
                            });
*/

                            /**
                             *  END OF PARSE TABLE USER_ID FIELD UPDATION...........
                             */
                            if (loginforbuy == 1) {
                                finish();
                            } else {
                                Intent i = new Intent(Loginactivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }

                        }

                    }
                }else {
                    dialogTransparent.dismiss();
                    Toast.makeText(Loginactivity.this,userBaseHolder.getMessage(),Toast.LENGTH_SHORT).show();
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
        /*Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);*/
        finish();
    }

    private void fbInvite() {
        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            String refCode = targetUrl.getQueryParameter("referral");
            if (AppInviteDialog.canShow()) {
                AppInviteContent content = new AppInviteContent.Builder()
                        .setApplinkUrl(appLinkURL)
                        .setPreviewImageUrl(previewImageURL)
                        .build();
                AppInviteDialog.show(this, content);
            }
        } else {
            AppLinkData.fetchDeferredAppLinkData(
                    this,
                    new AppLinkData.CompletionHandler() {
                        @Override
                        public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                            //process applink data

                        }
                    });
            if (AppInviteDialog.canShow()) {
                AppInviteContent content = new AppInviteContent.Builder()
                        .setApplinkUrl(appLinkURL)
                        .setPreviewImageUrl(previewImageURL)
                        .build();
                AppInviteDialog.show(this, content);
            }

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent(Loginactivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
