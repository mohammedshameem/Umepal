package com.qiito.umepal.holder;

public class CheckoutProductsHolder {
	

	
	private String userid;
	private String productid;
	private String productname;
	private String quantity;
	private String price;
	private String image;
	private String storename;
	private String shippingcharge;
	private String estimatedarrival;
	private String availability;
	private String savedprice;
	private int autoincrementid;
	
	public CheckoutProductsHolder()
	{
		 super();
	}
	public CheckoutProductsHolder(String userid,String productid,String productname,String quantity,String price,
								  String image,String storename,String shippingcharge,
								  String estimatedarrival,String availability,String savedprice,int autoincrementid)
	{
		this.userid=userid;
		this.productid=productid;
		this.productname=productname;
		this.quantity=quantity;
		this.price=price;
		this.image=image;
		this.storename = storename;
		this.shippingcharge = shippingcharge;
		this.estimatedarrival = estimatedarrival;
		this.availability = availability;
		this.savedprice = savedprice;
		this.autoincrementid=autoincrementid;
	}
	
	
	
	
	/**
	 * @return the autoincrementid
	 */
	public int getAutoincrementid() {
		return autoincrementid;
	}
	/**
	 * @param autoincrementid the autoincrementid to set
	 */
	public void setAutoincrementid(int autoincrementid) {
		this.autoincrementid = autoincrementid;
	}
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the productid
	 */
	public String getProductid() {
		return productid;
	}
	/**
	 * @param productid the productid to set
	 */
	public void setProductid(String productid) {
		this.productid = productid;
	}
	/**
	 * @return the productname
	 */
	public String getProductname() {
		return productname;
	}
	/**
	 * @param productname the productname to set
	 */
	public void setProductname(String productname) {
		this.productname = productname;
	}
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getShippingcharge() {
		return shippingcharge;
	}

	public void setShippingcharge(String shippingcharge) {
		this.shippingcharge = shippingcharge;
	}

	public String getEstimatedarrival() {
		return estimatedarrival;
	}

	public void setEstimatedarrival(String estimatedarrival) {
		this.estimatedarrival = estimatedarrival;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getSavedprice() {
		return savedprice;
	}

	public void setSavedprice(String savedprice) {
		this.savedprice = savedprice;
	}
}
