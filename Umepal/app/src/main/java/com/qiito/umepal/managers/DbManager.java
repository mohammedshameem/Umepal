package com.qiito.umepal.managers;

import com.qiito.umepal.dao.AdvertisementDao;
import com.qiito.umepal.dao.CheckoutDAO;
import com.qiito.umepal.dao.CurrentlyLoggedUserDAO;
import com.qiito.umepal.dao.MembershipDao;
import com.qiito.umepal.dao.NotificationDao;
import com.qiito.umepal.dao.ShippingDAO;
import com.qiito.umepal.dao.UserDAO;
import com.qiito.umepal.holder.AdvertisementData;
import com.qiito.umepal.holder.MembershipBaseHolder;
import com.qiito.umepal.holder.ShippingAddress;
import com.qiito.umepal.holder.UserObjectHolder;

import java.util.HashMap;
import java.util.List;
public class DbManager {

	private static final String TAG = DbManager.class.getSimpleName();

	private static DbManager instance = null;

	public static DbManager getInstance() {

		if (instance == null) {

			instance = new DbManager();

		}
		return instance;
	}


	/**
	 * Current user table Operations
	 * 
	 * */
	public boolean isUserExisting() {

		return CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentTable();
	}

	public void insertIntoCurrentUser(int user_id, String session_id) {

		CurrentlyLoggedUserDAO.getInstance().setCurrentlyLoggedUserDetails(
				user_id, session_id);
	}

	public HashMap<String, String> fetchCurrentUserDetails() {

		return CurrentlyLoggedUserDAO.getInstance().getCurrentUserDetails();
	}

	public void deleteCurrentlyLoggedUserTable() {
		CurrentlyLoggedUserDAO.getInstance().deleteAllRows();
	}
	public void deleteAllRowsFromShoppingcart() {
		CheckoutDAO.getInstance().deleteAllCheckout();
	}

	public void deleteAllRowsFromUserTable() {
		UserDAO.getInstance().deleteUserTableRow();
	}
	
	public void deleteAllRows() {
		CurrentlyLoggedUserDAO.getInstance().deleteAllRows();
	}

    /**
     * User table Operations
     *  
     */
	
	public void insertIntoUserTable(UserObjectHolder userObject) {

		UserDAO.getInstance().insertUserDetails(userObject);
	}

	public void updateUserTable(UserObjectHolder userObject) {

		UserDAO.getInstance().UpdateUserTable(userObject);
	}

	public HashMap<String, String> getUserDetails(int userid) {

		return UserDAO.getInstance().getUserDetails(userid);
	}
	
	public void deleteUserTable(){
		
		UserDAO.getInstance().deleteUserTableRow();
		
	}

	/**
	 * 
	 * @return all the information about logged in user...
	 */
	public UserObjectHolder getCurrentUserDetails() {

		return UserDAO.getInstance().getUserDetails();
	}

	public String getSessionId() {

		return CurrentlyLoggedUserDAO.getInstance().getSessionId();
	}

	public String getExpirydate() {

		return MembershipDao.getInstance().getExpirydate();
	}

	public String getcreateddate() {


		return MembershipDao.getInstance().getcreateddate();
	}


	public void insertAllAdsToDb(List<AdvertisementData> adsList){
		
		AdvertisementDao.getInstance().insertAdsDetails(adsList);
	}
	
	public List<AdvertisementData> getAllAdsFromDb(){
		
		return AdvertisementDao.getInstance().getAllAdsFromDb();
		
	}
	
	public long getAdsCountFromDb(){
		
		return AdvertisementDao.getInstance().fetchAdsTableCount();
		
	}
	
	public void deleteAllAdsfromDb(){
		
		AdvertisementDao.getInstance().deleteAllRows();
	}
    /**
     *
     * @return notification count
     */

    public int getOpenNotificationListCount() {

        return NotificationDao.getInstance().getNotificationListCount();
    }
    public void insertNotificationDetails(int userid, int list_count) {
        // TODO Auto-generated method stub
        NotificationDao.getInstance().insertNotificationDetails(userid,list_count);
    }

    public long fetchNotificationTableCount() {
        // TODO Auto-generated method stub
        return NotificationDao.getInstance().fetchNotificationTableCount();
    }

    public void deleteNotifications() {

        NotificationDao.getInstance().deleteRow();
    }



    /**
     *
     * @param list_count
     */
    public void updateNotificationDetails(int userid, int list_count) {

        NotificationDao.getInstance().updateNotificationDetails(userid, list_count);
    }
	public void deleteMembershipdata() {
		MembershipDao.getInstance().deleteMembershipdata();
	}
	public void insertintoMembership(MembershipBaseHolder membershipBaseHolder) {

		MembershipDao.getInstance().insertMembershipDetails(membershipBaseHolder);
	}


	/******
	 * ShippingData Table Operation
	 ******/

	public void insertintoShippingTable(ShippingAddress shippingAddress){
		ShippingDAO.getInstance().insertShippingDetails(shippingAddress);
	}

	public void deleteShippingData(){
		ShippingDAO.getInstance().deleteShippingDataTableRow();
	}

	public void getShippingAddress(){
		ShippingDAO.getInstance().getShippingAddress();
	}

}
