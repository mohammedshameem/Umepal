package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.btventures.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.LoginManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;




public class Signupactivity extends Activity {

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


    private String androidId;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;
    private String ConfirmPassword;

    private boolean firstNameFlag;
    private boolean lastNameFlag;
    private boolean emailFlag;
    private boolean EmailFlag2;
    private boolean passwordFlag;
    private boolean confirmPasswordFlag;
    private boolean CheckPasswordsFlag;

    private final  int requestCode = 200;
    private int requestcode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);
        initView();
        initManager();

        androidId= Secure.getString(getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);

        /* SIGN UP */

       /* signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //checkdetails();
                //  include requestCode as parameter and check value fir unique device id


            }
        });*/

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
        cancelButton.setOnClickListener(cancelListener);
    }

        View.OnClickListener cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 finish();
            }
        };

/*
    private void checkdetails() {

        if (UtilValidate.isValidFirstname(firstName.getText().toString())){

            FirstName=firstName.getText().toString();
            firstNameFlag = true;

            if (UtilValidate.isValidLastname(lastName.getText().toString())){

                LastName = lastName.getText().toString();
                lastNameFlag = true;

                if (UtilValidate.isValidemail(email.getText().toString())){

                    emailFlag = true;
                    Email = email.getText().toString();

                    if (UtilValidate.isValidPassword(password.getText().toString())){

                        passwordFlag = true;
                        Password=password.getText().toString();
                        if(UtilValidate.isValidPassword(confirmPassword.getText().toString())){
                            confirmPasswordFlag = true;
                            ConfirmPassword = confirmPassword.getText().toString();

                            if(password.getText().toString().equals(confirmPassword.getText().toString())){

                                CheckPasswordsFlag = true;

                            }else{

                                password.setError("passwords do not match");
                                confirmPassword.setError("passwords do not match");
                            }
                        }else{
                            if (confirmPassword.getText().length()<8){
                                confirmPassword.setError("password lenght should be between 8 and 15");
                            }
                            confirmPassword.setError("Please provide value here");
                        }

                    }else{
                        if (password.getText().length()<8){
                            password.setError("password lenght should be between 8 and 15");
                        }

                    }

                }else{
                    email.setError("Email should be in the form abc@abc.abc");
                }

            }else{
                if (lastName.getText().toString().length()<0){
                    lastName.setError("Please enter your last name");
                }
                else{
                    lastName.setError("Enter a valid data");
                }
            }

        }else{
            if(firstName.getText().toString().length()<0){
                firstName.setError("Please enter your first name");
            }else{
                firstName.setError("enter valid data");
            }
        }



        if (firstNameFlag && lastNameFlag && emailFlag && passwordFlag && confirmPasswordFlag && CheckPasswordsFlag) {


            Log.e("","dsaadsfsdfsfsdfdsfsddddddddddddddddddddd"+FirstName);
            Log.e("",""+LastName);
            Log.e("",""+Email);
            Log.e("",""+Password);
            Log.e("",""+androidId);
            Log.e("",""+requestCode);


            LoginManager.getInstance().emailSignup(Signupactivity.this, signUpCallBack, FirstName, LastName,
                    Email, Password, androidId, requestCode);

            */
/*  SUCCESS LOGIN  *//*


        }
        else {
            Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_LONG).show();
        }

    }
*/
    /*check details*/

    private void initView() {

     //   loginWithFacebok=(LinearLayout)findViewById(R.id.facebook_login_button);
     //   loginButton=(Button)findViewById(R.id.login_button);
     //   signupButton=(Button)findViewById(R.id.signup_button);
        nameEditText =(EditText) findViewById(R.id.nameEditText);
        emailEditText=(EditText) findViewById(R.id.emailEditText);
        ceaEditText=(EditText) findViewById(R.id.ceaEditText);
        mobileEditText=(EditText) findViewById(R.id.mobileEditText);
        bankEditText=(EditText) findViewById(R.id.bankEditText);
        estateagencyEditText=(EditText) findViewById(R.id.estateagencyEditText);
        bankaccountEditText=(EditText) findViewById(R.id.bankaccountEditText);
        refferalmemberidEditText=(EditText) findViewById(R.id.refferalmemberidEditText);
        cancelButton = (Button)findViewById(R.id.cancelButton);

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
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public class SignUpCallBack implements AsyncTaskCallBack {


        @Override
        public void onFinish(int responseCode, Object result) {
            UserBaseHolder userBaseHolder = (UserBaseHolder) result;
            if(userBaseHolder.getStatus().equals("success")) {
                Log.e("","In callBack success");

                if (userBaseHolder != null) {


                    DbManager.getInstance().deleteCurrentlyLoggedUserTable();

                    DbManager.getInstance().insertIntoCurrentUser(userBaseHolder.getData().getUser().getId(),
                            userBaseHolder.getData().getSession_id());

                    DbManager.getInstance().deleteUserTable();


                    DbManager.getInstance().insertIntoUserTable(userBaseHolder.getData().getUser());

                    Toast.makeText(getApplicationContext(), "Signup Success", Toast.LENGTH_LONG).show();

                    LoginManager.getInstance().emailLogin(Signupactivity.this, Email, Password, loginCallBackClass, requestcode);


                }
            }

            else if(userBaseHolder.getStatus().equals("error")) {
                Toast.makeText(Signupactivity.this,"User already exsists!!!",Toast.LENGTH_LONG).show();
            }


        }

        @Override
        public void onFinish(int responseCode, String result) {
            Toast.makeText(Signupactivity.this,""+result,Toast.LENGTH_SHORT).show();
        }
    }

    public class LoginCallBackClass implements AsyncTaskCallBack{


        @Override
        public void onFinish(int responseCode, Object result) {

            UserBaseHolder userBaseHolder = (UserBaseHolder)result;
            if(UtilValidate.isNotNull(userBaseHolder)){

                if(userBaseHolder.getStatus().equalsIgnoreCase("error")) {


                }
                else {

                    DbManager.getInstance().deleteCurrentlyLoggedUserTable();
                    DbManager.getInstance().insertIntoCurrentUser(userBaseHolder.getData().getUser().getId(),
                            userBaseHolder.getData().getSession_id());
                    DbManager.getInstance().deleteAllRowsFromUserTable();
                    DbManager.getInstance().insertIntoUserTable(userBaseHolder.getData().getUser());
                    DbManager.getInstance().deleteShippingData();
                    if(userBaseHolder.getData().getUser().getShipping_address() != null) {
                        DbManager.getInstance().insertintoShippingTable(userBaseHolder.getData().getUser().getShipping_address());

                    }

                    Intent intent = new Intent(Signupactivity.this, MainActivity.class);

                    startActivity(intent);

                }
                finish();
            }
            else{
                //Toast.makeText(Loginactivity.this,"Please try again ",Toast.LENGTH_SHORT).show();
                }
        }

        @Override
        public void onFinish(int responseCode, String result) {


        }
    }

}
