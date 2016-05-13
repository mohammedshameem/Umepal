package com.qiito.umepal.dao;

import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qiito.umepal.Constants.User;
import com.qiito.umepal.Helper.DBHelper;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.UserObjectHolder;


public class UserDAO implements User {

	private static final String TAG = UserDAO.class.getSimpleName();

	private DBHelper dbHelper;

	private static UserDAO instance = null;

	String SELECT_ALL_ROWS_BY_USERID = new StringBuffer(" select *")
			.append(" from ").append(DB_TABLE_NAME).append(" where ")
			.toString();

	String SELECT_USER_DETAILS = new StringBuffer(" select *").append(" from ")
			.append(DB_TABLE_NAME).toString();

	public static UserDAO getInstance() {

		if (instance == null) {

			instance = new UserDAO();
		}

		return instance;

	}

	public UserDAO() {

		dbHelper = DBHelper.getInstance();
	}

	/**
	 * This method will insert all user data into user table
	 * 
	 * @param userObject
	 */
	public void insertUserDetails(UserObjectHolder userObject) {

		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			try {
				db.beginTransaction();

				ContentValues values = new ContentValues();
				values.put(User._ID, userObject.getId());
				values.put(User.FNAME, userObject.getFirstName());
				values.put(User.LNAME, userObject.getLastName());
				values.put(User.EMAIL_ID, userObject.getEmail());
				values.put(User.FB_ID, userObject.getFacebookId());
				values.put(User.PASSWORD, userObject.getPassword());
				values.put(User.TELEPHONE, userObject.getTelephone());
				values.put(User.ADDRESSLINE1, userObject.getAddressLine1());
				values.put(User.ADDRESSLINE2, userObject.getAddressLine2());
				values.put(User.CITY, userObject.getCity());
				values.put(User.STATE, userObject.getState());
				values.put(User.COUNTRY, userObject.getCountry());
				values.put(User.PIN, userObject.getPin());
				values.put(User.UNIQUEDEVICEID, userObject.getUniqueDeviceId());
				values.put(User.PIC_URL, userObject.getProfilePic());
				values.put(User.ISADMIN, userObject.getIsAdmin());
				values.put(User.ISDELETED, userObject.getIsDeleted());
				values.put(User.STATUS, userObject.getStatus());
				values.put(User.CREATED, userObject.getCreated());
				values.put(User.CREATED_DATE, userObject.getCreatedDate());
				values.put(User.EXPIRY_DATE, userObject.getExpirydate());
				values.put(User.MEMBERSHIP_STATUS,userObject.getMembership_status());
				values.put(User.MEMBERSHIP_BLOCKED, userObject.getMembership_blocked());


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

	/**
	 * This method will fetch all the fields from user table and returns the
	 * data in a map object
	 * 
	 * @return {@code HashMap<String, String>} : user details
	 */
	public HashMap<String, String> getUserDetails(int userid) {
		synchronized (DBHelper.lock) {

			HashMap<String, String> map = new HashMap<String, String>();

			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(
					new StringBuffer(SELECT_ALL_ROWS_BY_USERID)
							.append(User._ID).append(" = ").append("'")
							.append(userid).append("'").toString(), null);

			Log.e(TAG, "cursor , userid " + cursor + " " + userid);

			try {
				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {
						

						map.put(_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(_ID))));

						map.put(FNAME, cursor.getString(cursor.getColumnIndex(FNAME)));

						map.put(LNAME, cursor.getString(cursor.getColumnIndex(LNAME)));

						map.put(EMAIL_ID, cursor.getString(cursor.getColumnIndex(EMAIL_ID)));

						map.put(FB_ID, cursor.getString(cursor.getColumnIndex(FB_ID)));

						map.put(PASSWORD, cursor.getString(cursor.getColumnIndex(PASSWORD)));

						map.put(TELEPHONE, cursor.getString(cursor.getColumnIndex(TELEPHONE)));
						
						map.put(ADDRESSLINE1, cursor.getString(cursor.getColumnIndex(ADDRESSLINE1)));

						map.put(ADDRESSLINE2, cursor.getString(cursor.getColumnIndex(ADDRESSLINE2)));

						map.put(CITY, cursor.getString(cursor.getColumnIndex(CITY)));

						map.put(STATE, cursor.getString(cursor.getColumnIndex(STATE)));

						map.put(COUNTRY, cursor.getString(cursor.getColumnIndex(COUNTRY)));

						map.put(PIN, cursor.getString(cursor.getColumnIndex(PIN)));

						map.put(UNIQUEDEVICEID, cursor.getString(cursor.getColumnIndex(UNIQUEDEVICEID)));
						
						map.put(PIC_URL, cursor.getString(cursor.getColumnIndex(PIC_URL)));
						
						map.put(ISADMIN, cursor.getString(cursor.getColumnIndex(ISADMIN)));

						map.put(ISDELETED, cursor.getString(cursor.getColumnIndex(ISDELETED)));

						map.put(STATUS, cursor.getString(cursor.getColumnIndex(STATUS)));

						map.put(CREATED_DATE, cursor.getString(cursor.getColumnIndex(CREATED_DATE)));

						map.put(CREATED, cursor.getString(cursor.getColumnIndex(CREATED)));

						map.put(EXPIRY_DATE, cursor.getString(cursor.getColumnIndex(EXPIRY_DATE)));

						map.put(MEMBERSHIP_STATUS, cursor.getString(cursor.getColumnIndex(MEMBERSHIP_STATUS)));

						map.put(MEMBERSHIP_BLOCKED, cursor.getString(cursor.getColumnIndex(MEMBERSHIP_BLOCKED)));


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
	 * Method fetch logged user id from table user.....
	 * 
	 * @return userID
	 */
	public int getUserId() {

		synchronized (DBHelper.lock) {

			int userID = 0;

			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(
					new StringBuffer(SELECT_USER_DETAILS).toString(), null);

			try {
				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						userID = cursor.getInt(cursor
								.getColumnIndex(User._ID));

					}
				}

			} catch (Exception e) {

				Log.e("UserDao", "Exception in dao" + e);

			} finally {

				cursor.close();
				db.close();
			}

			return userID;
		}

	}
	
	/**
	 * Method fetch logged user id from table user.....
	 * 
	 * @return TOTAL SHARED COUNT
	 */


	public String getFbid() {

		synchronized (DBHelper.lock) {

			String fbid = null;

			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(SELECT_USER_DETAILS, null);

			try {
				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						fbid = cursor.getString(cursor
								.getColumnIndex(User.FB_ID));

					}
				}

			} catch (Exception e) {

				Log.e("UserDao", "Exception in dao" + e);

			} finally {

				cursor.close();
				db.close();
			}

			return fbid;
		}
	}


	
	 /**
     * This method will delete current user.
     */
    public void deleteUserTableRow() {

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
    
    
    
    
    /**
     * 
     * @return
     */
	public UserObjectHolder getUserDetails() {

		synchronized (DBHelper.lock) {

			UserObjectHolder userObjectHolder = new UserObjectHolder();

			int id;
			String firstName;
			String lastName;
			String email;
			String facebookId;
			String password;
			String telephone;
			String addressLine1;
			String addressLine2;
			String city;
			String state;
			String country;
			String pin;
			String uniqueDeviceId;
			String profilePic;
			String createdDate;
			int isAdmin;
			int isDeleted;
			int status;
			String created;
			String ExpiryDate;
			String membershipstatus;
			String membershipblocked;

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

			try {
				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {
						
						id= cursor.getInt(cursor.getColumnIndex(User._ID));
						userObjectHolder.setId(id);

						firstName = cursor.getString(cursor.getColumnIndex(User.FNAME));
						userObjectHolder.setFirstName(firstName);

						lastName = cursor.getString(cursor.getColumnIndex(User.LNAME));
						userObjectHolder.setLastName(lastName);
					
						email= cursor.getString(cursor.getColumnIndex(User.EMAIL_ID));
						userObjectHolder.setEmail(email);

						facebookId = cursor.getString(cursor.getColumnIndex(User.FB_ID));
						userObjectHolder.setFacebookId(facebookId);

						password= cursor.getString(cursor.getColumnIndex(User.PASSWORD));
						userObjectHolder.setPassword(password);

						telephone= cursor.getString(cursor.getColumnIndex(User.TELEPHONE));
						userObjectHolder.setTelephone(telephone);
						
						addressLine1= cursor.getString(cursor.getColumnIndex(User.ADDRESSLINE1));
						userObjectHolder.setAddressLine1(addressLine1);
						
						
						addressLine2= cursor.getString(cursor.getColumnIndex(User.ADDRESSLINE2));
						userObjectHolder.setAddressLine2(addressLine2);
						
						
						city= cursor.getString(cursor.getColumnIndex(User.CITY));
						userObjectHolder.setCity(city);
						
						
						state= cursor.getString(cursor.getColumnIndex(User.STATE));
						userObjectHolder.setState(state);
						
						
						country= cursor.getString(cursor.getColumnIndex(User.COUNTRY));
						userObjectHolder.setCountry(country);
						
						pin= cursor.getString(cursor.getColumnIndex(User.PIN));
						userObjectHolder.setPin(pin);
						
						uniqueDeviceId=cursor.getString(cursor.getColumnIndex(User.UNIQUEDEVICEID));
						userObjectHolder.setUniqueDeviceId(uniqueDeviceId);
						
						
						profilePic=cursor.getString(cursor.getColumnIndex(User.PIC_URL));
						userObjectHolder.setProfilePic(profilePic);
						
						isAdmin = cursor.getInt(cursor.getColumnIndex(User.ISADMIN));
						userObjectHolder.setIsAdmin(isAdmin);
						
						isDeleted = cursor.getInt(cursor.getColumnIndex(User.ISDELETED));
						userObjectHolder.setIsDeleted(isDeleted);
						
						status= cursor.getInt(cursor.getColumnIndex(User.STATUS));
						userObjectHolder.setStatus(status);

						createdDate = cursor.getString(cursor.getColumnIndex(User.CREATED_DATE));
						userObjectHolder.setCreatedDate(createdDate);

						created=cursor.getString(cursor.getColumnIndex(User.CREATED));
						userObjectHolder.setCreated(created);

						ExpiryDate=cursor.getString(cursor.getColumnIndex(User.EXPIRY_DATE));
						userObjectHolder.setExpirydate(ExpiryDate);

						membershipstatus=cursor.getString(cursor.getColumnIndex(User.MEMBERSHIP_STATUS));
						userObjectHolder.setMembership_status(membershipstatus);

						membershipblocked= ((cursor.getString(cursor.getColumnIndex(User.MEMBERSHIP_BLOCKED))));
						userObjectHolder.setMembership_blocked(membershipblocked);

					}
				}

			} catch (Exception e) {
					Log.e("UserDao", "Exception in dao" + e);

			} finally {

				cursor.close();
				db.close();
			}

			return userObjectHolder;
		}

	}

	/**
	 * 
	 * @param userObject
	 * @return
	 * @throws SQLException
	 */
	public boolean UpdateUserTable(UserObjectHolder userObject) throws SQLException {
		// TODO Auto-generated method stub
		boolean isSuccess = false;

		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();

			try {
					Log.d(TAG, "Going to update member table");

				ContentValues values = new ContentValues();

				if (UtilValidate.isNotNull(userObject.getEmail()))
					values.put(User.EMAIL_ID, userObject.getEmail());

				if (UtilValidate.isNotNull(userObject.getFirstName()))
					values.put(User.FNAME, userObject.getFirstName());

				if (UtilValidate.isNotNull(userObject.getLastName()))
					values.put(User.LNAME, userObject.getLastName());

				if (UtilValidate.isNotNull(userObject.getTelephone()))
					values.put(User.TELEPHONE, userObject.getTelephone());
				
				if(UtilValidate.isNotNull(userObject.getCity()))
					values.put(User.CITY, userObject.getCity());

				if (UtilValidate.isNotNull(userObject.getProfilePic()))
					values.put(User.PIC_URL, userObject.getProfilePic());

				if (UtilValidate.isNotNull(userObject.getId()))
					values.put(User._ID, userObject.getId());

				if(UtilValidate.isNotNull(userObject.getCreatedDate()))
					values.put(User.CREATED_DATE, userObject.getCreatedDate());

				if(UtilValidate.isNotNull(userObject.getCreated()))
					values.put(User.CREATED, userObject.getCreated());


				if(UtilValidate.isNotNull(userObject.getExpirydate()))
					values.put(User.EXPIRY_DATE, userObject.getExpirydate());
				if(UtilValidate.isNotNull(userObject.getMembership_status()))
					values.put(User.MEMBERSHIP_STATUS, userObject.getMembership_status());
				if(UtilValidate.isNotNull(userObject.getMembership_blocked()))
					values.put(User.MEMBERSHIP_BLOCKED, userObject.getMembership_blocked());

				db.update(
						DB_TABLE_NAME,
						values,
						_ID + "= ?",
						new String[] { String.valueOf(userObject.getId()) });
				

				isSuccess = true;
				
				Log.e(TAG, "Status of updating member table"+ isSuccess);
			} catch (Exception e) {
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
