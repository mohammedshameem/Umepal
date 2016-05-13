package com.qiito.umepal.holder;

import java.io.Serializable;
import java.util.List;

public class ProductObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String description;
	private String price;
	private String promoprice;
	private String discountprice;
	private String savings;
	private String shippingcharge;
	private String discount;
	private boolean popular;
	private boolean editor;
	private String image;
	private String imageone;
	private String imagetwo;
	private String imagethree;
	private String imagefour;
	private int status;
	private String stockCount;
	private String offerDate;
	private String productForAge;
	private int like_count;
	private int view_count;
	private int share_count;
	private String estimated_shipping;
	private String availability;
	private String return_policy;
	private String estimated_arrival;
	private String shipping_from_name;
	private String shipping_from_address;
	private String shipping_time;
	private int collect_at_store;
	private List<String> dimension;
	private List<String> color;
	private int is_liked;
	private StoreDetailsHolder store_detalis;

	public String getDiscountprice() {
		return discountprice;
	}

	public void setDiscountprice(String discountprice) {
		this.discountprice = discountprice;
	}

	public String getSavings() {
		return savings;
	}

	public void setSavings(String savings) {
		this.savings = savings;
	}

	public String getShipping_time() {
		return shipping_time;
	}

	public void setShipping_time(String shipping_time) {
		this.shipping_time = shipping_time;
	}

	public int getCollect_at_store() {
		return collect_at_store;
	}

	public void setCollect_at_store(int collect_at_store) {
		this.collect_at_store = collect_at_store;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPromoprice() {
		return promoprice;
	}

	public void setPromoprice(String promoprice) {
		this.promoprice = promoprice;
	}

	public String getShippingcharge() {
		return shippingcharge;
	}

	public void setShippingcharge(String shippingcharge) {
		this.shippingcharge = shippingcharge;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public boolean isPopular() {
		return popular;
	}

	public void setPopular(boolean popular) {
		this.popular = popular;
	}

	public boolean isEditor() {
		return editor;
	}

	public void setEditor(boolean editor) {
		this.editor = editor;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageone() {
		return imageone;
	}

	public void setImageone(String imageone) {
		this.imageone = imageone;
	}

	public String getImagetwo() {
		return imagetwo;
	}

	public void setImagetwo(String imagetwo) {
		this.imagetwo = imagetwo;
	}

	public String getImagethree() {
		return imagethree;
	}

	public void setImagethree(String imagethree) {
		this.imagethree = imagethree;
	}

	public String getImagefour() {
		return imagefour;
	}

	public void setImagefour(String imagefour) {
		this.imagefour = imagefour;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStockCount() {
		return stockCount;
	}

	public void setStockCount(String stockCount) {
		this.stockCount = stockCount;
	}

	public String getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(String offerDate) {
		this.offerDate = offerDate;
	}

	public String getProductForAge() {
		return productForAge;
	}

	public void setProductForAge(String productForAge) {
		this.productForAge = productForAge;
	}

	public int getLike_count() {
		return like_count;
	}

	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}

	public int getShare_count() {
		return share_count;
	}

	public void setShare_count(int share_count) {
		this.share_count = share_count;
	}


	public int getIs_liked() {
		return is_liked;
	}

	public void setIs_liked(int is_liked) {
		this.is_liked = is_liked;}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;

	}

	public String getEstimated_shipping() {
		return estimated_shipping;
	}

	public void setEstimated_shipping(String estimated_shipping) {
		this.estimated_shipping = estimated_shipping;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getReturn_policy() {
		return return_policy;
	}

	public void setReturn_policy(String return_policy) {
		this.return_policy = return_policy;
	}

	public String getEstimated_arrival() {
		return estimated_arrival;
	}

	public void setEstimated_arrival(String estimated_arrival) {
		this.estimated_arrival = estimated_arrival;
	}

	public String getShipping_from_name() {
		return shipping_from_name;
	}

	public void setShipping_from_name(String shipping_from_name) {
		this.shipping_from_name = shipping_from_name;
	}

	public String getShipping_from_address() {
		return shipping_from_address;
	}

	public void setShipping_from_address(String shipping_from_address) {
		this.shipping_from_address = shipping_from_address;
	}

	public StoreDetailsHolder getStore_detalis() {
		return store_detalis;
	}

	public void setStore_detalis(StoreDetailsHolder store_detalis) {
		this.store_detalis = store_detalis;
	}

	public List<String> getDimension() {
		return dimension;
	}

	public void setDimension(List<String> dimension) {
		this.dimension = dimension;
	}

	public List<String> getColor() {
		return color;
	}

	public void setColor(List<String> color) {
		this.color = color;
	}
}
