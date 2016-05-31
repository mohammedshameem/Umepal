package com.qiito.umepal.holder;

import java.io.Serializable;
import java.util.List;

public class UserObjectHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String facebookId;
	private String password;
	private String shippingname;
	private String telephone;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String pin;
	private String uniqueDeviceId;
	private String profilePic;
	private int isAdmin;
	private int isDeleted;
	private int status;
	private String createdDate;

	private String created;
	private String ExpiryDate;
	private String membership_status;
	private String membership_blocked;
	private ShippingAddress shipping_address;
	//private MembershipBaseHolder member_status;
	private List<PurchasedItems> purchased_items;
	private List<ProductObject> liked_products;

	private String cea;
	private String mobilenumber;
	private String bank;
	private String estateagency;
	private String bankaccount;
	private String referralmember_id;
	private String referrerId;
	private String umeId;
	private String paymentStatus;
	private boolean is_member;
	private String session_id;

	private String membershipPrice;
	private String membershipType;
	private int membershipId;

	public String getMembershipPrice() {
		return membershipPrice;
	}

	public void setMembershipPrice(String membershipPrice) {
		this.membershipPrice = membershipPrice;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	public int getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
	}

	private String is_recentpurchase;

	public String getIs_recentpurchase() {
		return is_recentpurchase;
	}

	public void setIs_recentpurchase(String is_recentpurchase) {
		this.is_recentpurchase = is_recentpurchase;
	}

	public String getExpiryDate() {
		return ExpiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		ExpiryDate = expiryDate;
	}

	public String getCea() {
		return cea;
	}

	public void setCea(String cea) {
		this.cea = cea;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getEstateagency() {
		return estateagency;
	}

	public void setEstateagency(String estateagency) {
		this.estateagency = estateagency;
	}

	public String getReferralmember_id() {
		return referralmember_id;
	}

	public void setReferralmember_id(String referralmember_id) {
		this.referralmember_id = referralmember_id;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(String referrerId) {
		this.referrerId = referrerId;
	}

	public String getUmeId() {
		return umeId;
	}

	public void setUmeId(String umeId) {
		this.umeId = umeId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public boolean is_member() {
		return is_member;
	}

	public void setIs_member(boolean is_member) {
		this.is_member = is_member;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public ShippingAddress getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(ShippingAddress shipping_address) {
		this.shipping_address = shipping_address;
	}

	public String getExpirydate() {
		return ExpiryDate;
	}

	public void setExpirydate(String expirydate) {
		ExpiryDate = expirydate;
	}

	public String getMembership_blocked() {
		return membership_blocked;
	}

	public void setMembership_blocked(String membership_blocked) {
		this.membership_blocked = membership_blocked;
	}

	public String getMembership_status() {
		return membership_status;
	}

	public void setMembership_status(String membership_status) {
		this.membership_status = membership_status;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the facebookId
	 */
	public String getFacebookId() {
		return facebookId;
	}
	/**
	 * @param facebookId the facebookId to set
	 */
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getShippingname() {
		return shippingname;
	}

	public void setShippingname(String shippingname) {
		this.shippingname = shippingname;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}
	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}
	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}
	/**
	 * @param pin the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}
	/**
	 * @return the uniqueDeviceId
	 */
	public String getUniqueDeviceId() {
		return uniqueDeviceId;
	}
	/**
	 * @param uniqueDeviceId the uniqueDeviceId to set
	 */
	public void setUniqueDeviceId(String uniqueDeviceId) {
		this.uniqueDeviceId = uniqueDeviceId;
	}
	/**
	 * @return the profilePic
	 */
	public String getProfilePic() {
		return profilePic;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * @param profilePic the profilePic to set

	 */
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	/**
	 * @return the isAdmin
	 */
	public int getIsAdmin() {
		return isAdmin;
	}
	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	/**
	 * @return the isDeleted
	 */
	public int getIsDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}



	public List<PurchasedItems> getPurchased_items() {
		return purchased_items;
	}

	public void setPurchased_items(List<PurchasedItems> purchased_items) {
		this.purchased_items = purchased_items;
	}

	public List<ProductObject> getLiked_products() {
		return liked_products;
	}

	public void setLiked_products(List<ProductObject> liked_products) {
		this.liked_products = liked_products;
	}
}
