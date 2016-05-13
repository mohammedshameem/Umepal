package com.qiito.umepal.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.qiito.umepal.Constants.ShippingData;
import com.qiito.umepal.Helper.DBHelper;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ShippingAddress;

import java.sql.SQLException;

/**
 * Created by shiya on 20/1/16.
 */
public class ShippingDAO implements ShippingData {

    private static final String TAG = ShippingDAO.class.getSimpleName();

    private DBHelper dbHelper;

    private static ShippingDAO instance = null;

    String SELECT_ALL_ROWS_BY_USERID = new StringBuffer(" select *")
            .append(" from ").append(DB_TABLE_NAME).append(" where ")
            .toString();

    String SELECT_USER_DETAILS = new StringBuffer(" select *").append(" from ")
            .append(DB_TABLE_NAME).toString();

    public static ShippingDAO getInstance() {

        if (instance == null) {

            instance = new ShippingDAO();
        }

        return instance;

    }

    public ShippingDAO() {

        dbHelper = DBHelper.getInstance();
    }
    
    /*** inserting into table***/

    public void  insertShippingDetails(ShippingAddress shippingAddress){
        
        synchronized (DBHelper.lock){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            try{
                db.beginTransaction();

                ContentValues values = new ContentValues();
                if (shippingAddress != null) {

                    Log.e("Shipping Address >>>> ", "NULL!!");

                    values.put(ShippingData.SHIPPING_FULLNAME, shippingAddress.getShippingfullname());
                    values.put(ShippingData.SHIPPING_STREET, shippingAddress.getShippingstreetaddress());
                    values.put(ShippingData.SHIPPING_UNIT, shippingAddress.getShippingunit());
                    values.put(ShippingData.SHIPPING_COUNTRY, shippingAddress.getShippingcountry());
                    values.put(ShippingData.SHIPPING_STATE, shippingAddress.getShippingstate());
                    values.put(ShippingData.SHIPPING_CITY, shippingAddress.getShippingcity());
                    values.put(ShippingData.SHIPPING_POSTALCODE, shippingAddress.getShippingpostalcode());
                    values.put(ShippingData.SHIPPING_PHONE, shippingAddress.getShippingphone());
                }

                db.insert(DB_TABLE_NAME, null, values);
                db.setTransactionSuccessful();

                Log.d(TAG, "inserting in to user table row successfull");

            }catch (Exception e) {
                Log.e(TAG, "Exception while inserting in to user table", e);
            } finally {
                db.endTransaction();
                db.close();
            }
        }

    }


    /*** Getting value from table with userid ***/

    /*public HashMap<String, String> getShippingDetails(int userid) {

        synchronized (DBHelper.lock) {

            HashMap<String, String> map = new HashMap<String, String>();

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(new StringBuffer(SELECT_ALL_ROWS_BY_USERID)
                    .append(ShippingData._ID).append(" = ").append("'")
                    .append(userid).append("'").toString(), null);

            map.put(SHIPPING_FULLNAME, cursor.getString(cursor
                    .getColumnIndex(SHIPPING_FULLNAME)));
            map.put(SHIPPING_STREET, cursor.getString(cursor
                    .getColumnIndex(SHIPPING_STREET)));
            map.put(SHIPPING_UNIT, cursor.getString(cursor
                    .getColumnIndex(SHIPPING_UNIT)));
            map.put(SHIPPING_COUNTRY, cursor.getString(cursor
                    .getColumnIndex(SHIPPING_COUNTRY)));

            map.put(SHIPPING_STATE, cursor.getString(cursor
                    .getColumnIndex(SHIPPING_STATE)));
            map.put(SHIPPING_CITY, cursor.getString(cursor
                    .getColumnIndex(SHIPPING_CITY)));
            map.put(SHIPPING_POSTALCODE, cursor.getString(cursor
                    .getColumnIndex(SHIPPING_POSTALCODE)));
            map.put(SHIPPING_PHONE, cursor.getString(cursor
                    .getColumnIndex(SHIPPING_PHONE)));


            return map;

        }

    }*/


    /*** This method will delete current user ***/

    public void deleteShippingDataTableRow() {

        synchronized (DBHelper.lock) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            try {
                db.beginTransaction();
                Log.e(TAG, " Shipping data deleted");
                db.delete(DB_TABLE_NAME, null, null);

                Log.e("USERS ", " Shipping data deleted ");
                db.setTransactionSuccessful();
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                db.endTransaction();
                db.close();
            }
        }
    }


    public ShippingAddress getShippingAddress(){
        synchronized (DBHelper.lock){

            ShippingAddress shippingAddress = new ShippingAddress();


            String shippingfullname;
            String shippingstreetaddress;
            String shippingunit;
            String shippingcountry;
            String shippingstate;
            String shippingcity;
            String shippingpostalcode;
            String shippingphone;

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(new StringBuffer(SELECT_USER_DETAILS).toString(), null);

            try{
                if (cursor.getCount()>0){
                    while (cursor.moveToNext()){

                        shippingfullname= ((cursor.getString(cursor.getColumnIndex(ShippingData.SHIPPING_FULLNAME))));
                        shippingAddress.setShippingfullname(shippingfullname);

                        shippingstreetaddress= ((cursor.getString(cursor.getColumnIndex(ShippingData.SHIPPING_STREET))));
                        shippingAddress.setShippingstreetaddress(shippingstreetaddress);

                        shippingunit= ((cursor.getString(cursor.getColumnIndex(ShippingData.SHIPPING_UNIT))));
                        shippingAddress.setShippingunit(shippingunit);

                        shippingcountry= ((cursor.getString(cursor.getColumnIndex(ShippingData.SHIPPING_COUNTRY))));
                        shippingAddress.setShippingcountry(shippingcountry);


                        shippingstate= ((cursor.getString(cursor.getColumnIndex(ShippingData.SHIPPING_STATE))));
                        shippingAddress.setShippingstate(shippingstate);

                        shippingcity= ((cursor.getString(cursor.getColumnIndex(ShippingData.SHIPPING_CITY))));
                        shippingAddress.setShippingcity(shippingcity);

                        shippingpostalcode= ((cursor.getString(cursor.getColumnIndex(ShippingData.SHIPPING_POSTALCODE))));
                        shippingAddress.setShippingpostalcode(shippingpostalcode);

                        shippingphone= ((cursor.getString(cursor.getColumnIndex(ShippingData.SHIPPING_PHONE))));
                        shippingAddress.setShippingphone(shippingphone);
                    }
                }
            }catch (Exception e) {
                Log.e("UserDao", "Exception in dao" + e);

            } finally {

                cursor.close();
                db.close();
            }

            return shippingAddress;
        }
    }

    public boolean updateShippingDataTable(ShippingAddress shippingAddress) throws SQLException{
        boolean isSuccess = false;

        synchronized (DBHelper.lock){

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            try{
                Log.d(TAG, "Going to update Shipping table");

                ContentValues values = new ContentValues();

                if(UtilValidate.isNotNull(shippingAddress.getShippingfullname()))
                    values.put(ShippingData.SHIPPING_FULLNAME, shippingAddress.getShippingfullname());

                if(UtilValidate.isNotNull(shippingAddress.getShippingstreetaddress()))
                    values.put(ShippingData.SHIPPING_STREET, shippingAddress.getShippingstreetaddress());

                if(UtilValidate.isNotNull(shippingAddress.getShippingunit()))
                    values.put(ShippingData.SHIPPING_UNIT, shippingAddress.getShippingunit());

                if(UtilValidate.isNotNull(shippingAddress.getShippingcountry()))
                    values.put(ShippingData.SHIPPING_COUNTRY, shippingAddress.getShippingcountry());


                if(UtilValidate.isNotNull(shippingAddress.getShippingstate()))
                    values.put(ShippingData.SHIPPING_STATE, shippingAddress.getShippingstate());

                if(UtilValidate.isNotNull(shippingAddress.getShippingcity()))
                    values.put(ShippingData.SHIPPING_CITY, shippingAddress.getShippingcity());

                if(UtilValidate.isNotNull(shippingAddress.getShippingpostalcode()))
                    values.put(ShippingData.SHIPPING_POSTALCODE, shippingAddress.getShippingpostalcode());

                if(UtilValidate.isNotNull(shippingAddress.getShippingphone()))
                    values.put(ShippingData.SHIPPING_PHONE, shippingAddress.getShippingphone());

                /*db.update(
                        DB_TABLE_NAME,
                        values,
                        _ID + "= ?",
                        new String[] { String.valueOf(userObject.getId()) });*/


                isSuccess = true;

                Log.e(TAG, "Status of updating member table"+ isSuccess);
            }catch (Exception e) {
                // TODO: handle exception
                Log.e(TAG, "Exception while updating member table", e);
                isSuccess = false;

            } finally {

                db.close();
            }

        }

        return isSuccess;
    }


    
}
