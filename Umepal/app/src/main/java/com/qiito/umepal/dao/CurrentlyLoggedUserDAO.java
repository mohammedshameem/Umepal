package com.qiito.umepal.dao;

import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qiito.umepal.Constants.CurrentlyLoggedUser;
import com.qiito.umepal.Helper.DBHelper;

public class CurrentlyLoggedUserDAO implements CurrentlyLoggedUser {

	private static final String TAG = CurrentlyLoggedUserDAO.class
			.getSimpleName();
	private DBHelper dbHelper;

	private static CurrentlyLoggedUserDAO instance = null;

	String SELECT_ROWS_COUNT = new StringBuffer(" select count(*)")
			.append(" from ").append(DB_TABLE_NAME).toString();

	String SELECT_ALL_ROWS = new StringBuffer(" select *").append(" from ")
			.append(DB_TABLE_NAME).toString();

	public static CurrentlyLoggedUserDAO getInstance() {

		if (instance == null) {

			instance = new CurrentlyLoggedUserDAO();
		}

		return instance;

	}

	public CurrentlyLoggedUserDAO() {

		dbHelper = DBHelper.getInstance();

		// TODO Auto-generated constructor stub
	}

	/**
	 * check whether any user existing in currently logged user table
	 * 
	 * @return {@code boolean} : True if user exist Or false.
	 */
	public boolean isAnyUserInCurrentTable() {

		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			boolean isExisting = false;
			Cursor cursor = null;

			try {
				cursor = db.rawQuery(
						new StringBuffer(SELECT_ROWS_COUNT).toString(), null);

				if (cursor != null) {

					Log.e(TAG, "cursor " + cursor);

					cursor.moveToFirst();

					if (cursor.getInt(0) == 0) {

						isExisting = false;
					}

					else {

						isExisting = true;

					}
				}

			} catch (Exception e) {
				// TODO: handle exception

				Log.e(TAG, "Exception in dao", e);

			} finally {

				cursor.close();
				db.close();

			}

			return isExisting;
		}
	}

	/**
	 * This method will fetch all the fields from current user table and returns
	 * the data in a map object
	 * 
	 * @return {@code HashMap<String, String>} : Current user details
	 */
	public HashMap<String, String> getCurrentUserDetails() {
		synchronized (DBHelper.lock) {

			HashMap<String, String> map = new HashMap<String, String>();

			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(SELECT_ALL_ROWS, null);

			try {
				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						int user_id_index = cursor
								.getColumnIndex(CurrentlyLoggedUser.USER_USERID);

						int user_session_index = cursor
								.getColumnIndex(CurrentlyLoggedUser.USER_SESSIONID);

						map.put("user_id",
								String.valueOf(cursor.getInt(user_id_index)));

						map.put("session_id",
								cursor.getString(user_session_index));
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				Log.e("UserDao", "Exception in dao" + e);

			} finally {

				cursor.close();
				db.close();
			}

			return map;
		}
	}

	/**
	 * This method will delete current user.
	 */
	public void deleteAllRows() {

		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			try {
				db.beginTransaction();
				Log.e("Homepage Logout click", "dbHomepage Logout click");
				db.delete(DB_TABLE_NAME, null, null);

				Log.e("Homepage Logout click", "dbdeletedHomepage Logout click");
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
				db.close();
			}
		}
	}

	public void setCurrentlyLoggedUserDetails(int user_id, String sessionId) {

		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			try {
				db.beginTransaction();
				ContentValues values = new ContentValues();
				values.put(CurrentlyLoggedUser.USER_USERID, user_id);// userid
				values.put(CurrentlyLoggedUser.USER_SESSIONID, sessionId);// sessionid

				// Inserting Row
				db.insert(CurrentlyLoggedUser.DB_TABLE_NAME, null, values);
				db.setTransactionSuccessful();

			} catch (Exception e) {
				Log.e(TAG,
						"Exception while inserting in to currently logged user table",
						e);
			} finally {
				db.endTransaction();
				db.close();
			}
		}

	}

	/**
	 * 
	 * @return session_id 
	 */
	public String getSessionId(){
		
		synchronized (DBHelper.lock) {

			String session_id = null;

			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(SELECT_ALL_ROWS, null);

			try {
				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						
						int user_session_index = cursor
								.getColumnIndex(CurrentlyLoggedUser.USER_SESSIONID);
					
						 session_id = cursor.getString(user_session_index);
					}
				}

			} catch (Exception e) {
				
				Log.e("UserDao", "Exception in dao" + e);

			} finally {

				cursor.close();
				db.close();
			}

			return session_id;
		}
	}

}
