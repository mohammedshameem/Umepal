package com.qiito.umepal.Helper;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.Constants.Advertisement;
import com.qiito.umepal.Constants.Checkout;
import com.qiito.umepal.Constants.CurrentlyLoggedUser;
import com.qiito.umepal.Constants.Membership;
import com.qiito.umepal.Constants.Notification;
import com.qiito.umepal.Constants.ShippingData;
import com.qiito.umepal.Constants.User;


public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    private static final String DB_NAME = "todaysparentv2.db";

    private static final int DB_VERSION_NO = 1;

    private static DBHelper mInstance = null;

    public static final Object lock = new Object();

    /**
     * Constructor should be private to prevent direct instantiation. make call
     * to static factory method "getInstance()" instead.    
     */
    public DBHelper() {

        super(TodaysParentApp.getCurrentcontext(), DB_NAME, null, DB_VERSION_NO);

        // TODO Auto-generated constructor stub
    }

    public static DBHelper getInstance() {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {

            Log.i(TAG,
                    "Context **************** :"
                            + TodaysParentApp.getCurrentcontext());
            mInstance = new DBHelper();
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        Log.d(TAG, "in onCreate for creating db");

        db.execSQL(new StringBuilder(" CREATE TABLE ")
                .append(CurrentlyLoggedUser.DB_TABLE_NAME).append(" (")
                .append(CurrentlyLoggedUser.USER_USERID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(CurrentlyLoggedUser.USER_SESSIONID)
                .append(" Varchar(20) ").append(");").toString());

        db.execSQL(new StringBuilder(" CREATE TABLE ")
                .append(User.DB_TABLE_NAME).append(" (")
                .append(User._ID).append(" INTEGER,")
                .append(User.FNAME).append(" Varchar(20),")
                .append(User.LNAME).append(" Varchar(20),")
                .append(User.EMAIL_ID).append(" Varchar(20),")
                .append(User.FB_ID).append(" Varchar(20),")
                .append(User.PASSWORD).append(" Varchar(20),")
                .append(User.TELEPHONE).append(" Varchar(20),")
                .append(User.ADDRESSLINE1).append(" Varchar(20),")
                .append(User.ADDRESSLINE2).append(" Varchar(20),")
                .append(User.CITY).append(" Varchar(20),")
                .append(User.STATE).append(" Varchar(20),")
                .append(User.COUNTRY).append(" Varchar(20),")
                .append(User.PIN).append(" Varchar(20),")
                .append(User.UNIQUEDEVICEID).append(" Varchar(20),")
                .append(User.PIC_URL).append(" Varchar(20),")
                .append(User.STATUS).append(" INTEGER,")
                .append(User.ISDELETED).append(" INTEGER,")
                .append(User.ISADMIN).append(" INTEGER,")
                .append(User.CREATED_DATE).append(" Varchar(20),")
                .append(User.CEA).append(" Varchar(20),")
                .append(User.MOBILE_NUMBER).append(" Varchar(20),")
                .append(User.BANK).append(" Varchar(20),")
                .append(User.ESTATEAGENCY).append(" Varchar(20),")
                .append(User.BANKACCOUNT).append(" Varchar(20),")
                .append(User.REFERRERID).append(" Varchar(20),")
                .append(User.UMEID).append(" Varchar(20),")
                .append(User.PAYMENTSTATUS).append(" Varchar(20),")
                .append(User.IS_RECENTPURCHASE).append(" Varchar(20),")
                .append(User.IS_MEMBER).append(" BOOLEAN,")
                .append(User.MEMBERSHIPID).append(" INTEGER,")
                .append(User.MEMBERSHIPTYPE).append(" Varchar(20),")
                .append(User.MEMBERSHIPPRICE).append(" Varchar(20),")
                .append(User.SESSION_ID).append(" Varchar(20),")
                .append(" FOREIGN KEY ").append("(")
                .append(User._ID).append(")").append(" REFERENCES ")
                .append(CurrentlyLoggedUser.DB_TABLE_NAME).append(" ( ")
                .append(CurrentlyLoggedUser.USER_USERID).append(" )")
                .append(");").toString());

        db.execSQL(new StringBuilder(" CREATE TABLE ")
                .append(Checkout.DB_TABLE_NAME).append(" (")
                .append(Checkout.AUTOINCREMENT_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(Checkout.PRODUCTID).append(" Varchar(20),")
                .append(Checkout.USERID).append(" Varchar(20),")
                .append(Checkout.PRICE).append(" Varchar(20),")
                .append(Checkout.PRODUCTIMAGE).append(" Varchar(20),")
                .append(Checkout.PRODUCTNAME).append(" Varchar(20),")
                .append(Checkout.QUANTITY).append(" Varchar(20),")
                .append(Checkout.STORENAME).append(" Varchar(20),")
                .append(Checkout.SHIPPINGCHARGE).append(" Varchar(20),")
                .append(Checkout.ESTIMATEDARRIVAL).append(" Varchar(20),")
                .append(Checkout.AVAILABILITY).append(" Varchar(20),")
                .append(Checkout.SAVEDPRICE).append(" Varchar(20)").append("); ")
                .toString());

        db.execSQL(new StringBuilder("CREATE TABLE ")
                .append(Advertisement.DB_TABLE_NAME).append(" (")
                .append(Advertisement.ADS_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(Advertisement.ADS_URL).append(" Varchar(80),")
                .append(Advertisement.ADS_IMAGE_LARGE).append(" Varchar(150),")
                .append(Advertisement.ADS_IMAGE_THUMB).append(" Varchar(150)")
                .append(");").toString());

        db.execSQL(new StringBuilder("CREATE TABLE ")
                .append(Notification.DB_TABLE_NAME).append(" (")
                .append(Notification.USER_ID).append(" INTEGER,")
                .append(Notification.LIST_COUNT).append(" INTEGER")
                .append(");").toString());
        db.execSQL(new StringBuilder(" CREATE TABLE ")
                .append(Membership.DB_TABLE_NAME).append(" (")
                .append(Membership.CREATED_DATE).append(" Varchar(20),")
                .append(Membership.EXPIRY_DATE).append(" Varchar(20)").append("); ")
                .toString());

        db.execSQL(new StringBuilder("CREATE TABLE ")
                .append(ShippingData.DB_TABLE_NAME).append("(")
                .append(ShippingData.SHIPPING_FULLNAME).append(" Varchar(20),")
                .append(ShippingData.SHIPPING_CITY).append(" Varchar(20),")
                .append(ShippingData.SHIPPING_COUNTRY).append(" Varchar(20),")
                .append(ShippingData.SHIPPING_UNIT).append(" Varchar(20),")
                .append(ShippingData.SHIPPING_STREET).append(" Varchar(20),")
                .append(ShippingData.SHIPPING_STATE).append(" Varchar(20),")
                .append(ShippingData.SHIPPING_POSTALCODE).append(" Varchar(20),")
                .append(ShippingData.SHIPPING_PHONE).append(" Varchar(20)")
                .append(");").toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        db.execSQL(new StringBuilder(" DROP TABLE IF EXISTS ").append(
                CurrentlyLoggedUser.DB_TABLE_NAME).toString());

        db.execSQL(new StringBuilder(" DROP TABLE IF EXISTS ").append(
                User.DB_TABLE_NAME).toString());

        db.execSQL(new StringBuilder(" DROP TABLE IF EXISTS ").append(
                Checkout.DB_TABLE_NAME).toString());

        db.execSQL(new StringBuilder(" DROP TABLE IF EXISTS ").append(
                Advertisement.DB_TABLE_NAME).toString());

        onCreate(db);

    }
}
