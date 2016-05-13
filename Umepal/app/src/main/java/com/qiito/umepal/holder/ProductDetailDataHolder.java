package com.qiito.umepal.holder;

import java.io.Serializable;
import java.util.List;

public class ProductDetailDataHolder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProductObject product;
	private List<ReviewObject> reviews;
	private List<StoreDetailsHolder> storeDetails;
	private List<ProductObject> recommended_products;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public ProductObject getProduct() {
		return product;
	}

	public void setProduct(ProductObject product) {
		this.product = product;
	}

	public List<ReviewObject> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewObject> reviews) {
		this.reviews = reviews;
	}


	public List<StoreDetailsHolder> getStoreDetails() {
		return storeDetails;
	}

	public void setStoreDetails(List<StoreDetailsHolder> storeDetails) {
		this.storeDetails = storeDetails;
	}

	public List<ProductObject> getRecommended_products() {
		return recommended_products;
	}

	public void setRecommended_products(List<ProductObject> recommended_products) {
		this.recommended_products = recommended_products;
	}
}
