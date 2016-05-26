package com.qiito.umepal.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Created by aswathy on 3/2/16.
 */
public class Utilvalidate {


    public static boolean isValidEmail(String email) {

        final Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
        try {
            return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
        } catch (NullPointerException exception) {
            return false;
        }

    }

    public static boolean isValidMobileNumber(String mobileNumber) {
        final Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("[0-9]{8,13}");
        try {
            return MOBILE_NUMBER_PATTERN.matcher(mobileNumber).matches();
        } catch (NullPointerException exception) {
            return false;
        }
    }

    public static boolean isValidPassword(String Password) {
        final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9]{1,15}");

        try {
            return PASSWORD_PATTERN.matcher(Password).matches();
        } catch (NullPointerException exception) {
            return false;
        }
    }

    public static boolean isValidName(String firstName) {
        final Pattern NAME_PATTERN = Pattern.compile("[A-Za-z\\s]{1,15}");
        try {
            return NAME_PATTERN.matcher(firstName).matches();
        } catch (NullPointerException exception) {
            return false;
        }
    }

    public static boolean isValidDetail(String firstName) {
        final Pattern NAME_PATTERN = Pattern.compile("[A-Za-z0-9\\s]{1,40}");
        try {
            return NAME_PATTERN.matcher(firstName).matches();
        } catch (NullPointerException exception) {
            return false;
        }
    }

    public static boolean isConnectivity(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                Log.e("Network", "NET Type: " + info.getTypeName());
                return true;
            }
        }
        return false;
    }

    public static void showHashKey(Context context) {

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName().toString(),
                    PackageManager.GET_SIGNATURES); // Your
            // here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    public static boolean isEmpty(String value) {
        if (value == null)
            return true;

        if (value.trim().length() == 0) {
            return true;
        }

        return false;
    }

    public static boolean isNotEmpty(String o) {
        return !isEmpty(o);


    }

    public static boolean isValidCEAno(String ceaNo) {

        final Pattern CEA_PATTERN = Pattern.compile("[A-Z]{1}[0-9]{6}[A-Z]{1}");
        try {
            return CEA_PATTERN.matcher(ceaNo).matches();
        } catch (NullPointerException exception) {
            return false;
        }
    }

}
