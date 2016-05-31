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
    private String cea;
    private String mobilenumber;
    private String bank;
    private String estateagency;
    private String bankaccount;
    private String referralmember_id;
    private String referrerId;
    private String umeId;
    private String paymentStatus;
    private String is_recentpurchase;
    private boolean is_member;
    private int membershipId;
    private String membershipType;
    private String membershipPrice;
    private String session_id;

    private String created;
    private String ExpiryDate;
    private String membership_status;
    private String membership_blocked;
    private ShippingAddress shipping_address;
    //private MembershipBaseHolder member_status;
    private String shippingname;
    private List<PurchasedItems> purchased_items;
    private List<ProductObject> liked_products;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShippingname() {
        return shippingname;
    }

    public void setShippingname(String shippingname) {
        this.shippingname = shippingname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUniqueDeviceId() {
        return uniqueDeviceId;
    }

    public void setUniqueDeviceId(String uniqueDeviceId) {
        this.uniqueDeviceId = uniqueDeviceId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getReferralmember_id() {
        return referralmember_id;
    }

    public void setReferralmember_id(String referralmember_id) {
        this.referralmember_id = referralmember_id;
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

    public String getIs_recentpurchase() {
        return is_recentpurchase;
    }

    public void setIs_recentpurchase(String is_recentpurchase) {
        this.is_recentpurchase = is_recentpurchase;
    }

    public boolean is_member() {
        return is_member;
    }

    public void setIs_member(boolean is_member) {
        this.is_member = is_member;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembershipPrice() {
        return membershipPrice;
    }

    public void setMembershipPrice(String membershipPrice) {
        this.membershipPrice = membershipPrice;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getMembership_status() {
        return membership_status;
    }

    public void setMembership_status(String membership_status) {
        this.membership_status = membership_status;
    }

    public String getMembership_blocked() {
        return membership_blocked;
    }

    public void setMembership_blocked(String membership_blocked) {
        this.membership_blocked = membership_blocked;
    }

    public ShippingAddress getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(ShippingAddress shipping_address) {
        this.shipping_address = shipping_address;
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
