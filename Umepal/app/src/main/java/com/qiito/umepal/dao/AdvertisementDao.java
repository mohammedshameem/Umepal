/**
 * 
 */
package com.qiito.umepal.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.qiito.umepal.Constants.Advertisement;
import com.qiito.umepal.Helper.DBHelper;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.AdvertisementData;

public class AdvertisementDao implements Advertisement {
	
	private static final String TAG = AdvertisementDao.class.getSimpleName();
	private DBHelper dbHelper;
	private static AdvertisementDao instance = null;

	String SELECT_ALL_ROWS = new StringBuffer(" select *").append(" from ")
			.append(DB_TABLE_NAME).toString();

	public static AdvertisementDao getInstance() {

		if (instance == null) {

			instance = new AdvertisementDao();
		}

		return instance;

	}

	public AdvertisementDao() {

		this.dbHelper = DBHelper.getInstance();

	}


  /**
   * 
   * @param adsList
   */
	public void insertAdsDetails(List<AdvertisementData> adsList) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			
			db.beginTransaction();
			
			
			if (UtilValidate.isNotNull(adsList) && UtilValidate.isNotEmpty(adsList)) {
				
				for (AdvertisementData ads : adsList) {
					
					ContentValues values = new ContentValues();

					if ((ads.getSite_url().trim().length() > 0) && (ads.getImage().trim().length() > 0)) {

						values.put(ADS_URL, ads.getImage().trim());
						values.put(ADS_IMAGE_LARGE, ads.getBanner_name().trim());
						values.put(ADS_IMAGE_THUMB, ads.getSite_url().trim());
						// Inserting Row
						db.insert(Advertisement.DB_TABLE_NAME, null, values);
						
							Log.e(TAG,"Ads row added ### ");
						
					}
				}

			}

			
			db.setTransactionSuccessful();

		} catch (Exception e) {
				Log.e(TAG,
						"Exception while inserting in to advertisement table", e);
		} finally {
			db.endTransaction();
			db.close();
		}

	}

	/**
	 * This method fetching all the ads from db and converted to List<AdvertisementObject>
	 * 
	 * @return adsList
	 */
	public List<AdvertisementData> getAllAdsFromDb(){
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		List<AdvertisementData> adsList = new ArrayList<AdvertisementData>();
		
		Cursor cursor = db.rawQuery(new StringBuffer(SELECT_ALL_ROWS).toString(), null);

		try {

			if (cursor != null) {

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						AdvertisementData object = new AdvertisementData();
						object.setImage(cursor.getString(1));
						object.setBanner_name(cursor.getString(2));
						object.setSite_url(cursor.getString(3));
						
						// Adding ads to list
						adsList.add(object);
					} while (cursor.moveToNext());
				}

			}
			
			db.setTransactionSuccessful();

		} catch (Exception e) {
			
				Log.e(TAG,"Exception while fetching rows from advertisement table", e);
			
		} finally {
			
			db.endTransaction();
			db.close();
		}
		
		return adsList;
		
		
	}
	
	
	/**
	 * 
	 * @return count
	 */
	public long fetchAdsTableCount() {

		long count = 0;

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.beginTransaction();
			String sql = "SELECT COUNT(*) FROM " + Advertisement.DB_TABLE_NAME;
			SQLiteStatement statement = db.compileStatement(sql);
			count = statement.simpleQueryForLong();
				Log.e(TAG, "COUNT from parse_info table @@@ " + count);
			db.setTransactionSuccessful();
		} catch (Exception e) {
				Log.e(TAG,"Exception while getting count from advertisement table",e);
		} finally {
			db.endTransaction();
			db.close();
		}
		return count;
	}
	
	
	/**
	 * This method will delete current user.
	 */
	public void deleteAllRows() {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.beginTransaction();

			db.delete(DB_TABLE_NAME, null, null);
				Log.d(TAG, "ADS TABLE ROWS DELETED SUCCESSFULLY");
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.endTransaction();
			db.close();
		}
	}

}
