package com.qiito.umepal.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qiito.umepal.Constants.Checkout;
import com.qiito.umepal.Helper.DBHelper;
import com.qiito.umepal.holder.CheckoutProductsHolder;


public class CheckoutDAO implements Checkout {

	private static final String TAG = CheckoutDAO.class.getSimpleName();

	private DBHelper dbHelper;

	private static CheckoutDAO instance = null;

	String SELECT_ALL_ROWS_BY_USERID = new StringBuffer(" select *")
			.append(" from ").append(DB_TABLE_NAME).append(" where ")
			.toString();

	String SELECT_USER_DETAILS = new StringBuffer(" select *").append(" from ")
			.append(DB_TABLE_NAME).toString();

	public static CheckoutDAO getInstance() {

		if (instance == null) {

			instance = new CheckoutDAO();
		}

		return instance;

	}

	public CheckoutDAO() {

		dbHelper = DBHelper.getInstance();
	}

	/**
	 * This method will insert all user data into user table
	 * 
	 * @param price
	 *            ,productquantity,productname,productimage,productid
	 */
	public void insertProductTocheckout(String userid,String productid,String productname,
			String price, String productquantity,String productimage,
					String storename, String shippingcharge, String estimatedarrival,String availability,String savedprice) {
		Log.e("","inside insert table function");
		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			try {
				db.beginTransaction();
					Log.e("product id<<<<<", productid);
					Log.e("price<<<<<",price);
					Log.e("hello",productimage);
					Log.e("productname<<<<<",productname);
					Log.e("productquantity<<<<<",productquantity);
					Log.e("userid<<<<<", userid);
				//Log.e("storename<<<<<", storename);
				Log.e("estiamted shipping<<<<<", shippingcharge);
				Log.e("estim arrival<<<<<", estimatedarrival);
				Log.e("avail<<<<<", availability);
				Log.e("savedprice>>",savedprice);

				ContentValues values = new ContentValues();
				values.put(Checkout.PRODUCTID, productid);
				values.put(Checkout.PRICE, price);
				values.put(Checkout.PRODUCTIMAGE, productimage);
				values.put(Checkout.PRODUCTNAME, productname);
				values.put(Checkout.QUANTITY, productquantity);
				values.put(Checkout.USERID, userid);
				values.put(Checkout.STORENAME,storename);
				values.put(Checkout.SHIPPINGCHARGE,shippingcharge);
				values.put(Checkout.ESTIMATEDARRIVAL,estimatedarrival);
				values.put(Checkout.AVAILABILITY,availability);
				values.put(Checkout.SAVEDPRICE,savedprice);

				Log.e("",""+values);
				// Inserting Row
				db.insert(DB_TABLE_NAME, null, values);
				db.setTransactionSuccessful();

				Log.e("","Insertion successfull");

			} catch (Exception e) {
				Log.e(TAG, "Exception while inserting in to user table", e);
			} finally {
				db.endTransaction();
				db.close();
			}
		}

	}

	/**
	 * This method will delete current user.
	 */
	public void deleteAllCheckout() {

		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			try {
				db.beginTransaction();
				db.delete(DB_TABLE_NAME, null, null);
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
				db.close();
			}
		}
	}

	public List<CheckoutProductsHolder> getProductDetailsFromCheckout(String userid) {
Log.e("","from method"+userid);
		synchronized (DBHelper.lock) {
			List<CheckoutProductsHolder> checkoutProductsHolders = new ArrayList<CheckoutProductsHolder>();

			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(
					new StringBuffer(SELECT_ALL_ROWS_BY_USERID)
							.append(Checkout.USERID).append(" = ").append("'")
							.append(userid).append("'").toString(), null);

			try {
				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {
						
						checkoutProductsHolders.add(
							new CheckoutProductsHolder(
								cursor.getString(cursor.getColumnIndex(Checkout.USERID)),
								cursor.getString(cursor.getColumnIndex(Checkout.PRODUCTID)),
								cursor.getString(cursor.getColumnIndex(Checkout.PRODUCTNAME)),
								cursor.getString(cursor.getColumnIndex(Checkout.QUANTITY)),
								cursor.getString(cursor.getColumnIndex(Checkout.PRICE)),
								cursor.getString(cursor.getColumnIndex(Checkout.PRODUCTIMAGE)),
								cursor.getString(cursor.getColumnIndex(Checkout.STORENAME)),
								cursor.getString(cursor.getColumnIndex(Checkout.SHIPPINGCHARGE)),
								cursor.getString(cursor.getColumnIndex(Checkout.ESTIMATEDARRIVAL)),
								cursor.getString(cursor.getColumnIndex(Checkout.AVAILABILITY)),
								cursor.getString(cursor.getColumnIndex(Checkout.SAVEDPRICE)),
								cursor.getInt(cursor.getColumnIndex(Checkout.AUTOINCREMENT_ID))));
					}
				}

			} catch (Exception e) {
					Log.e("UserDao", "Exception in dao" + e);

			} finally {

				cursor.close();
				db.close();
			}
			return checkoutProductsHolders;
		}
		

	}
	
	
	/*public void deleteSingleRow(String productid,String quantity,String userid){
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.beginTransaction();

			db.delete(DB_TABLE_NAME, Checkout.PRODUCTID+ "= ? AND "+Checkout.USERID+ "= ? AND "+Checkout.QUANTITY+"=? AND "+Checkout.AUTOINCREMENT_ID+"=?", new String[] { productid +"",userid+"",quantity+""});
				Log.d(TAG, "SINGLE ROW FROM ORDER TABLE DELETED SUCCESSFULLY");
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.endTransaction();
			db.close();
		}
		
	}*/
	public void deleteSingleRow(String productid,String quantity,String userid,int autoincrementid){

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.beginTransaction();


			db.delete(DB_TABLE_NAME, Checkout.PRODUCTID+ "= ? AND "+Checkout.USERID+ "= ? AND "+Checkout.QUANTITY+"=? AND "+Checkout.AUTOINCREMENT_ID+"=?", new String[] { productid +"",userid+"",quantity+"",autoincrementid+""});
			Log.d(TAG, "SINGLE ROW FROM ORDER TABLE DELETED SUCCESSFULLY");
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.endTransaction();
			db.close();
		}

	}

	public  void updateCheckoutTable(String productID, String quantity){

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.beginTransaction();
			ContentValues values = new ContentValues();

			values.put(Checkout.QUANTITY, quantity);// list count

			db.update(Checkout.DB_TABLE_NAME, values, Checkout.PRODUCTID
					+ "= ?", new String[]{String.valueOf(productID)});
			db.setTransactionSuccessful();

		} catch (Exception e) {
			Log.e(TAG, "Exception while updating checkout table", e);
		} finally {
			db.endTransaction();
			db.close();
		}
	}

	
}
