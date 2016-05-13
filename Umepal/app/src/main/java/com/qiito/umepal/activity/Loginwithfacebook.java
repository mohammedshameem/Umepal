package com.qiito.umepal.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.FacebookManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Loginwithfacebook extends FragmentActivity {

    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private FacebookCallBack fblogincallback;
    private LogoutReceiver logoutReceiver;

    private String accessToken;
    private int requestcode = 0;

    private double longitude;
    private double latitude;
    private AccessTokenTracker accessTokenTracker;

    public class LogoutReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.package.ACTION_LOGOUT")){
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initManager();

        Log.e("", "in facebook activity");



        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(logoutReceiver, intentFilter);


        try{

            PackageInfo info = getPackageManager().getPackageInfo(Loginwithfacebook.this.getPackageName().toString(), PackageManager.GET_SIGNATURES);

            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.e("KeyHash>>>:", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }

        }catch(NameNotFoundException e){

            Log.e("NAme not found>>",""+e);

        }catch(NoSuchAlgorithmException e){

            Log.e("No algorithm>>",""+e);

        }

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();


        /*profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                setProfile(currentProfile);
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // On AccessToken changes fetch the new profile which fires the event on
                // the ProfileTracker if the profile is different
                Profile.fetchProfileForCurrentAccessToken();
            }
        };

        // Ensure that our profile is up to date
        Profile.fetchProfileForCurrentAccessToken();
        setProfile(Profile.getCurrentProfile());*/

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","user_friends","email"));

        Log.e("","Going to call fb login_manager");

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            AccessToken accessToken1 = AccessToken.getCurrentAccessToken();

            @Override
            public void onSuccess(LoginResult loginResult) {

               accessToken = loginResult.getAccessToken().getToken();
               String fbId =  loginResult.getAccessToken().getUserId();

                Log.e("TOKEN>>>",""+accessToken);
                Log.e("","FBID>>>>>"+fbId);


                FacebookManager.getInstance().callUserLoginApi(Loginwithfacebook.this, fblogincallback,
                        requestcode, accessToken, longitude, latitude, fbId);

            }

            @Override
            public void onCancel() {

                Intent gotoIntent = new Intent(Loginwithfacebook.this, Loginactivity.class);
                startActivity(gotoIntent);

            }

            @Override
            public void onError(FacebookException e) {


                Log.e("","FB_eXCEPTION>>>>"+e);
                if (e instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {

                    }
                }
            }
        });



    }

    private void setProfile(Profile currentProfile) {

        /*if (userNameView == null || profilePictureView == null || !isAdded()) {
            // Fragment not yet added to the view. So let's store which user was intended
            // for display.
            pendingUpdateForUser = profile;
            return;
        }

        if (profile == null) {
            profilePictureView.setProfileId(null);
            userNameView.setText(R.string.greeting_no_user);
        } else {
            profilePictureView.setProfileId(profile.getId());
            userNameView.setText(String.format(getString(R.string.greeting_format),
                    profile.getName()));
        }*/
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
       // accessTokenTracker.stopTracking();

        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
            AccessToken.setCurrentAccessToken(null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

    }

    private void initManager(){
        fblogincallback = new FacebookCallBack();
        logoutReceiver = new LogoutReceiver();

    }

    public class FacebookCallBack implements AsyncTaskCallBack{
        @Override
        public void onFinish(int responseCode, Object result) {

            UserBaseHolder userBaseHolder = (UserBaseHolder) result;
            if (userBaseHolder.getStatus().equals("success")){

               /* if(userBaseHolder.getData().getUser().getMember_status()!=null){
                    DbManager.getInstance().deleteMembershipdata();
                    DbManager.getInstance().insertintoMembership(userBaseHolder.getData().getUser().getMember_status());
                }*/

                DbManager.getInstance().deleteCurrentlyLoggedUserTable();

                DbManager.getInstance().insertIntoCurrentUser(userBaseHolder.getData().getUser().getId(),
                        userBaseHolder.getData().getSession_id());

                DbManager.getInstance().deleteUserTable();

                DbManager.getInstance().insertIntoUserTable(userBaseHolder.getData().getUser());
                DbManager.getInstance().deleteShippingData();
                if(userBaseHolder.getData().getUser().getShipping_address() != null) {
                    DbManager.getInstance().insertintoShippingTable(userBaseHolder.getData().getUser().getShipping_address());

                }
                Log.e("image from faceboook",""+userBaseHolder.getData().getUser().getProfilePic());

                Toast.makeText(getApplicationContext(), "Facebook login Success", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Loginwithfacebook.this,MainActivity.class);
                startActivity(intent);
                finish();


            }
            else{
                Log.e("","status error");
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Log.e("", "backpressed");
        finish();
        super.onBackPressed();

    }

}
