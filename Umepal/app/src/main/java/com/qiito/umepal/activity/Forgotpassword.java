package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ForgotPasswordBaseHolder;
import com.qiito.umepal.holder.ResetPasswordBaseHolder;
import com.qiito.umepal.managers.LoginManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;


public class Forgotpassword extends Activity {


    private ForgotPasswordCallBack forgotPasswordCallBack;
    private ResetPasswordCallBack resetPasswordCallBack;

    private TextView cancelAndBackToLogin;

    private EditText email;
    private EditText token;
    private EditText newPassword;

    private String mailId;

    private Button Token;
    private Button OK;
    private Button resetPassword;

    private PopupWindow popupWindow;
    private View invalidMailLayout;
    private LayoutInflater invalidMailLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forgot_password_page);

        initViews();
        initManager();

        mailId=email.getText().toString();


        Token.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 mailId=email.getText().toString();
                 Log.e("##", "MailId" + mailId);
                 if(UtilValidate.isValidemail(mailId)) {
                     LoginManager.getInstance().forgotPassword(Forgotpassword.this, mailId, forgotPasswordCallBack);
                 }
                 else {
                    email.setError("Invalid Email");
                 }
             }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token_str = token.getText().toString();
                String password_str = newPassword.getText().toString();
                Log.e("##", "Token<< " + token_str);
                Log.e("((", "new_pass<<" + password_str);
                LoginManager.getInstance().resetPassword(Forgotpassword.this,mailId, password_str, token_str, resetPasswordCallBack);
            }
        });


        cancelAndBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtologin = new Intent(Forgotpassword.this, Loginactivity.class);
                startActivity(backtologin);
                finish();
            }
        });
    }

    public class ForgotPasswordCallBack implements AsyncTaskCallBack{

        @Override
        public void onFinish(int responseCode, Object result) {
            ForgotPasswordBaseHolder forgotPasswordBaseHolder = (ForgotPasswordBaseHolder) result;
            if(forgotPasswordBaseHolder.getStatus().equals("success")){
                Toast.makeText(getApplicationContext(), "Check ur mail for token", Toast.LENGTH_SHORT).show();

                Log.e("&&","Password callback success");

            }else{
                Log.e("&&","Password callback error"+forgotPasswordBaseHolder.getMessage());
                Toast.makeText(getApplicationContext(),forgotPasswordBaseHolder.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    public class ResetPasswordCallBack implements AsyncTaskCallBack{

        @Override
        public void onFinish(int responseCode, Object result) {
            ResetPasswordBaseHolder resetPasswordBaseHolder = (ResetPasswordBaseHolder) result;
            if (resetPasswordBaseHolder.getStatus().equals("success")){
                Log.e("!!", "reset password success");
                Toast.makeText(Forgotpassword.this,"Password reset successfull",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Forgotpassword.this,Loginactivity.class);
                startActivity(intent);
            }
            else{
                Log.e("**","reset password failed");
            }
        }
        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    private void initViews() {
        cancelAndBackToLogin=(TextView)findViewById(R.id.forgot_pswd_cancel_text);
        email=(EditText)findViewById(R.id.forgot_pswd_email_edittext);
        newPassword=(EditText)findViewById(R.id.newPassword_edittext);
        resetPassword=(Button)findViewById(R.id.reset_password_button);
        token = (EditText)findViewById(R.id.token_edittext);
        Token = (Button)findViewById(R.id.token_button);


    }

    private void initManager(){
        forgotPasswordCallBack = new ForgotPasswordCallBack();
        resetPasswordCallBack = new ResetPasswordCallBack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_forgotpassword, menu);
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
}
