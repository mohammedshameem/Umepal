package com.qiito.umepal.holder;

public class ProductNotificationBaseHolder {

	private int id;
	private ProductObject product;
	private String user_firstname;
	private String user_lastname;
    private String user_profilePic;
	private String message;
	private String description;
	private String dcraetedDate;
	private String notification_type;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_firstname() {
		return user_firstname;
	}

	public void setUser_firstname(String user_firstname) {
		this.user_firstname = user_firstname;
	}

	public ProductObject getProduct() {
		return product;
	}

	public void setProduct(ProductObject product) {
		this.product = product;
	}

	public String getUser_lastname() {
		return user_lastname;
	}

	public void setUser_lastname(String user_lastname) {
		this.user_lastname = user_lastname;
	}

	public String getUser_profilePic() {
		return user_profilePic;
	}

	public void setUser_profilePic(String user_profilePic) {
		this.user_profilePic = user_profilePic;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDcraetedDate() {
		return dcraetedDate;
	}

	public void setDcraetedDate(String dcraetedDate) {
		this.dcraetedDate = dcraetedDate;
	}

	public String getNotification_type() {
		return notification_type;
	}

	public void setNotification_type(String notification_type) {
		this.notification_type = notification_type;
	}
}
