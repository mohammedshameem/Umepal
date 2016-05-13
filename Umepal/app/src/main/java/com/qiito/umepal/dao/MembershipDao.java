package com.qiito.umepal.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qiito.umepal.Constants.Membership;
import com.qiito.umepal.Helper.DBHelper;
import com.qiito.umepal.holder.MembershipBaseHolder;


/**
 * Created by abin on 17/11/15.
 */
public class MembershipDao implements Membership {
    private static final String TAG = MembershipDao.class.getSimpleName();
    private DBHelper dbHelper;

    private static MembershipDao instance = null;


    String SELECT_ALL_ROWS_BY_USERID = new StringBuffer(" select *")
            .append(" from ").append(DB_TABLE_NAME).append(" where ")
            .toString();

    String SELECT_USER_DETAILS = new StringBuffer(" select *").append(" from ")
            .append(DB_TABLE_NAME).toString();

    public static MembershipDao getInstance() {

        if (instance == null) {

            instance = new MembershipDao();
        }

        return instance;

    }
    public MembershipDao() {

        dbHelper = DBHelper.getInstance();
    }


    public void insertMembershipDetails(MembershipBaseHolder membershipBaseHolder) {

        synchronized (DBHelper.lock) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            try {
                db.beginTransaction();

                ContentValues values = new ContentValues();
                values.put(Membership.CREATED_DATE, membershipBaseHolder.getCreated());
                values.put(Membership.EXPIRY_DATE, membershipBaseHolder.getExpirydate());


                // Inserting Row
                db.insert(DB_TABLE_NAME, null, values);
                db.setTransactionSuccessful();

                Log.d(TAG, "inserting in to user table row successfull");

            } catch (Exception e) {
                Log.e(TAG, "Exception while inserting in to user table", e);
            } finally {
                db.endTransaction();
                db.close();
            }
        }

    }
    public void deleteMembershipdata() {

        synchronized (DBHelper.lock) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            try {
                db.beginTransaction();
                Log.e("USERS", " user deleted");
                db.delete(DB_TABLE_NAME, null, null);

                Log.e("USERS ", " user deleted");
                db.setTransactionSuccessful();
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                db.endTransaction();
                db.close();
            }
        }
    }

    public String getExpirydate(){

        synchronized (DBHelper.lock) {

            String status = null;

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(SELECT_USER_DETAILS, null);

            try {
                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {


                        int user_session_index = cursor
                                .getColumnIndex(Membership.EXPIRY_DATE);

                        status = cursor.getString(user_session_index);
                    }
                }

            } catch (Exception e) {

                Log.e("UserDao", "Exception in dao" + e);

            } finally {

                cursor.close();
                db.close();
            }

            return status;
        }
    }
    public String getcreateddate(){

        synchronized (DBHelper.lock) {

            String status = null;

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(SELECT_USER_DETAILS, null);

            try {
                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {


                        int user_session_index = cursor
                                .getColumnIndex(Membership.CREATED_DATE);

                        status = cursor.getString(user_session_index);
                    }
                }

            } catch (Exception e) {

                Log.e("UserDao", "Exception in dao" + e);

            } finally {

                cursor.close();
                db.close();
            }

            return status;
        }
    }
}
