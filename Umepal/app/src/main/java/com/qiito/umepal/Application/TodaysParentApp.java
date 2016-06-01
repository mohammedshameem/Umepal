/**
 *
 */

package com.qiito.umepal.Application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.util.Log;

/*import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;*/
import com.qiito.umepal.holder.Category;
import com.qiito.umepal.holder.CategoryResponseHolder;
import com.qiito.umepal.holder.ProductNotificationBaseHolder;
import com.qiito.umepal.holder.ShippingAddress;
import com.qiito.umepal.holder.ShippingAddressHolder;

import java.util.List;


public class TodaysParentApp extends Application {

    public static Context context;

    protected static final String TAG = TodaysParentApp.class.getName();

    private static String sessionId;

    private static String isinwebview;

    private static int userId;

    private static String ShippingValue;

    private static String itemTotalValue;

    private static String orderTotalValue;

    private static CategoryResponseHolder categoryResponseHolder;

    private static List<Category> category;

    private static int Notification_count;

    private static int like_count;

    private static String searchword;

    private static boolean shareStatus = false;

    private static final String APPLICATION_ID = "fHC2JeqOZgzqGMvXpwRdOauUFoqYQYbhj5jz4RPS";

    private static final String CLIENT_KEY = "6nJ2eynK23kxEGJCxDOUgsi9pJYoCyiKmR12Fv6a";

    private static List<ProductNotificationBaseHolder> notificationBaseHoldersList;

    private static ShippingAddressHolder shippingDetailsHolder;

    private static ShippingAddress shippingAddress;


    /**
     * @return the context
     */
    @Override
    public void onCreate() {
        super.onCreate();
        TodaysParentApp.context = getApplicationContext();
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        /*Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.d("push", "The push campaign has been created.");
				} else {
					Log.d("push", "Error sending push:" + e.getMessage());
				}
			}

		});;*/
        /*ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        //If you would like all objects to be private by default, remove this line.
      defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("LOG", "android id >>" + android_id);*/
        // PushService.setDefaultPushCallback(this, NavigationDrawerActivity.class);


    }

    /*
     *
     * (non-Javadoc)
     *
     * @see android.app.Application#onLowMemory()
     */
    @Override
    public void onLowMemory() {

        super.onLowMemory();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Application#onConfigurationChanged(android.content.res.
     * Configuration)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Application#onTerminate()
     */
    @Override
    public void onTerminate() {

        super.onTerminate();
    }


    public static String getIsinwebview() {
        return isinwebview;
    }

    public static void setIsinwebview(String isinwebview) {
        TodaysParentApp.isinwebview = isinwebview;
    }

    /**
     * @return the notificationBaseHoldersList
     */
    public static List<ProductNotificationBaseHolder> getNotificationBaseHoldersList() {
        return notificationBaseHoldersList;
    }


    public static String getSearchword() {
        return searchword;
    }

    public static void setSearchword(String searchword) {
        TodaysParentApp.searchword = searchword;
    }

    /**
     * @param notificationBaseHoldersList the notificationBaseHoldersList to set
     */
    public static void setNotificationBaseHoldersList(
            List<ProductNotificationBaseHolder> notificationBaseHoldersList) {
        TodaysParentApp.notificationBaseHoldersList = notificationBaseHoldersList;
    }


    public static Context getCurrentcontext() {

        return TodaysParentApp.context;

    }

    public static String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId
     */
    public static void setSessionId(String sessionId) {
        TodaysParentApp.sessionId = sessionId;
    }

    /**
     * @return userId
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public static void setUserId(int userId) {
        TodaysParentApp.userId = userId;
    }

    /**
     * @return
     */
    public static CategoryResponseHolder getCategoryResponseHolder() {
        return categoryResponseHolder;
    }

    /**
     * @param categoryResponseHolder
     */
    public static void setCategoryResponseHolder(
            CategoryResponseHolder categoryResponseHolder) {
        TodaysParentApp.categoryResponseHolder = categoryResponseHolder;
    }

    public static boolean isShareStatus() {
        return shareStatus;
    }

    public static void setShareStatus(boolean shareStatus) {
        TodaysParentApp.shareStatus = shareStatus;
    }

    public static List<Category> getCategory() {
        return category;
    }

    public static void setCategory(List<Category> category) {
        TodaysParentApp.category = category;
    }

    public static String getShippingValue() {
        return ShippingValue;
    }

    public static void setShippingValue(String shippingValue) {
        ShippingValue = shippingValue;
    }

    public static String getItemTotalValue() {
        return itemTotalValue;
    }

    public static void setItemTotalValue(String itemTotalValue) {
        TodaysParentApp.itemTotalValue = itemTotalValue;
    }

    public static String getOrderTotalValue() {
        return orderTotalValue;
    }

    public static void setOrderTotalValue(String orderTotalValue) {
        TodaysParentApp.orderTotalValue = orderTotalValue;
    }

    public static int getLike_count() {
        return like_count;
    }

    public static void setLike_count(int like_count) {
        TodaysParentApp.like_count = like_count;
    }

    public static ShippingAddressHolder getShippingDetailsHolder() {
        return shippingDetailsHolder;
    }

    public static void setShippingDetailsHolder(ShippingAddressHolder shippingDetailsHolder) {
        TodaysParentApp.shippingDetailsHolder = shippingDetailsHolder;
    }

    public static int getNotification_count() {
        return Notification_count;
    }

    public static void setNotification_count(int notification_count) {
        Notification_count = notification_count;
    }

    public static ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public static void setShippingAddress(ShippingAddress shippingAddress) {
        TodaysParentApp.shippingAddress = shippingAddress;
    }
}
