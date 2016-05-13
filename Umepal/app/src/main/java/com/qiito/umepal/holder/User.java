package com.qiito.umepal.holder;

import java.util.List;

/**
 * Created by abin on 31/8/15.
 */
public class User {
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
    private String Expirydate;
    private String created;
    private String membership_blocked;
    private List<PurchasedItems> purchased_items;
    private List<ProductObject>liked_products;

    public String getMembership_blocked() {
        return membership_blocked;
    }

    public void setMembership_blocked(String membership_blocked) {
        this.membership_blocked = membership_blocked;
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

    public String getExpirydate() {
        return Expirydate;
    }

    public void setExpirydate(String expirydate) {
        Expirydate = expirydate;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUniqueDeviceId() {
        return uniqueDeviceId;
    }

    public void setUniqueDeviceId(String uniqueDeviceId) {
        this.uniqueDeviceId = uniqueDeviceId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

