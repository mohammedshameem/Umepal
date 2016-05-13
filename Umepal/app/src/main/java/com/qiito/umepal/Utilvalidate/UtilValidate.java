package com.qiito.umepal.Utilvalidate;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 
 * @author sujith 
 * <a> href="sujith@xminds.in">mail to </a> 
 *
 */
public class UtilValidate {

	public static boolean isEmpty(int value) {
		if (value == 0)
			return true;

		return false;
	}

	public static boolean isEmpty(String value) {
		if (value == null)
			return true;

		if (value.trim().length() == 0) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Long value) {
		if (value == null)
			return true;

		if (value == 0L) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Object[] value) {
		if (value == null)
			return true;

		if (value.length == 0) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(byte[] value) {
		if (value == null)
			return true;

		if (value.length == 0) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Collection<?> value) {
		if (value == null)
			return true;

		if (value.size() == 0) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Map<?, ?> value) {
		if (value == null)
			return true;

		if (value.size() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(Collection<?> o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(Long o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(String o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(Object[] o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(byte[] o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(Map<?, ?> o) {
		return !isEmpty(o);
	}

	public static boolean isNull(Object o) {
		return (o == null);
	}

	public static boolean isNotNull(Object o) {
		return !isNull(o);
	}

	public static boolean isValidDate(String date) {
		SimpleDateFormat spdf = new SimpleDateFormat("dd-MM-yyyy");
		Date cDate = null;

		// Calendar calendar=Calendar.getInstance();

		try {

			cDate = spdf.parse(date);

		} catch (ParseException e) {

			return false;
		}
		if (!spdf.format(cDate).equals(date)) {

			return false;
		}
		return true;
	}

	public static boolean afterToday(String date) {
		SimpleDateFormat spdf = new SimpleDateFormat("dd-MM-yy");
		Date cDate = null;
		Date todays_date = new Date();
		try {
			cDate = spdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		if (!cDate.after(todays_date)) {
			return false;
		}
		return true;

	}

	public static boolean isValidemail(String email) {

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

	public static boolean isValidUsername(String userName){
		final Pattern USER_NAME_PATTERN = Pattern.compile("[a-zA-Z0-9]{1,15}");
		try {
			return USER_NAME_PATTERN.matcher(userName).matches();
		}catch (NullPointerException exception){
			return false;
		}
	}

	public static boolean isValidFirstname(String firstName){
		final Pattern FIRST_NAME_PATTERN = Pattern.compile("[A-Za-z]{1,15}");
		try {
			return FIRST_NAME_PATTERN.matcher(firstName).matches();
		}catch (NullPointerException exception){
			return false;
		}
	}

	public static boolean isValidLastname(String lastName){
		final Pattern LAST_NAME_PATTERN = Pattern.compile("[A-Za-z\\s]{1,30}");
		try {
			return LAST_NAME_PATTERN.matcher(lastName).matches();
		}catch (NullPointerException exception){
			return false;
		}
	}

	public static boolean isValidCity(String City){
		final Pattern CITY_PATTERN = Pattern.compile("[A-Za-z0-9\\s]{1,20}");
		try {
			return CITY_PATTERN.matcher(City).matches();
		}catch (NullPointerException exception){
			return false;
		}
	}

	public static boolean isValidMobileNumber(String mobileNumber){
		final Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("[0-9]{8,13}");
		try {
			return MOBILE_NUMBER_PATTERN.matcher(mobileNumber).matches();
		}catch (NullPointerException exception){
			return false;
		}
	}

	public static boolean isValidPassword(String Password){
		final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9]{8,15}");
		try {
			return PASSWORD_PATTERN.matcher(Password).matches();
		}catch (NullPointerException exception){
			return false;
		}
	}



	public static boolean isNetworkAvailable(Activity activity) {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public static boolean haveNetworkConnection(Activity activity) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}

	//Time difference calculation
	public static String getTimeDifference(String start_time){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String currentDateandTime = sdf.format(new Date());
		Date date1=null;
		Date date2=null;
		String formattedDate;
		long different=0;
		try {
			date1 = sdf.parse(currentDateandTime);
			/*date1=new Date();
			Calendar c = Calendar.getInstance();
			formattedDate = sdf.format(c.getTime());*/
			date2 = sdf.parse(start_time);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Log.e("","111111"+start_time);
		different = date2.getTime() -date1.getTime() ;
		Log.e("Today::::","date "+date1);
		Log.e("TIME:::::","time "+date2.getTime());
		Log.e("TIME:::::","time "+date1.getTime());

		Log.e("TIME:::::","time "+different);


		if(UtilValidate.isNotNull(different)) {
			String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(different),
					TimeUnit.MILLISECONDS.toMinutes(different) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(different)),
					TimeUnit.MILLISECONDS.toSeconds(different) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(different)));
			return hms;
		}
		return null;
	}



    // convert InputStream to String
    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
