package com.qiito.umepal.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.qiito.umepal.Constants.Notification;
import com.qiito.umepal.Helper.DBHelper;


public class NotificationDao implements Notification {

	private static final String TAG = NotificationDao.class.getSimpleName();
	private DBHelper dbHelper;
	private static NotificationDao instance = null;

	String SELECT_ALL_ROWS = new StringBuffer(" select *").append(" from ")
			.append(DB_TABLE_NAME).toString();

	public static NotificationDao getInstance() {

		if (instance == null) {

			instance = new NotificationDao();
		}

		return instance;

	}

	public NotificationDao() {

		this.dbHelper = DBHelper.getInstance();

	}

	/**
	 * 
	 * @return notificationListCount
	 */
	public int getNotificationListCount() {

		int notificationListCount = 0;

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(SELECT_ALL_ROWS, null);

		try {
			if (cursor.getCount() > 0) {

				while (cursor.moveToNext()) {

					notificationListCount = cursor.getInt(cursor
							.getColumnIndex(Notification.LIST_COUNT));

                    Log.d("","LIST COUNT from dao"+notificationListCount);

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
				Log.e(TAG, "Exception while fetching notification cout from db"
						+ e);

		} finally {

			cursor.close();
			db.close();
		}

		return notificationListCount;
	}

	/**
	 * This method will insert session and user id into current user table
	 * 
	 * @param userid
	 * 
	 */
	public void insertNotificationDetails(int userid, int list_count) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.beginTransaction();
			ContentValues values = new ContentValues();
			values.put(Notification.USER_ID, userid);// userid
			values.put(Notification.LIST_COUNT, list_count);// list count

			// Inserting Row
			db.insert(Notification.DB_TABLE_NAME, null, values);
			db.setTransactionSuccessful();

		} catch (Exception e) {
				Log.e(TAG,
						"Exception while inserting in to notification table", e);
		} finally {
			db.endTransaction();
			db.close();
		}

	}

	/**
	 * 
	 * @param userid
	 * @param list_count
	 */
	public void updateNotificationDetails(int userid, int list_count) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.beginTransaction();
			ContentValues values = new ContentValues();

			values.put(Notification.LIST_COUNT, list_count);// list count

			db.update(Notification.DB_TABLE_NAME, values, Notification.USER_ID
					+ "= ?", new String[] { String.valueOf(userid) });
			db.setTransactionSuccessful();

		} catch (Exception e) {
				Log.e(TAG, "Exception while updating notification table", e);
		} finally {
			db.endTransaction();
			db.close();
		}

	}

	/**
	 * 
	 * @return count
	 */
	public long fetchNotificationTableCount() {

		long count = 0;

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.beginTransaction();
			String sql = "SELECT COUNT(*) FROM " + Notification.DB_TABLE_NAME;
			SQLiteStatement statement = db.compileStatement(sql);
			count = statement.simpleQueryForLong();
				Log.e(TAG, "COUNT from notification table @@@ " + count);
			db.setTransactionSuccessful();
		} catch (Exception e) {
				Log.e(TAG,
						"Exception while getting count from notification table",
						e);
		} finally {
			db.endTransaction();
			db.close();
		}
		return count;
	}

	/**
	 * This method will delete current user.
	 */
	public void deleteRow() {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.beginTransaction();

			db.delete(DB_TABLE_NAME, null, null);
				Log.d(TAG, "ROW DELETED SUCCESSFULLY");
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.endTransaction();
			db.close();
		}
	}

}
