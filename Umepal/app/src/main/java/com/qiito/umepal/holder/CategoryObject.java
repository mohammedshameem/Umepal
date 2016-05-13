package com.qiito.umepal.holder;

import java.io.Serializable;
import java.util.List;


public class CategoryObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int category_id;
	private String category_name;
	private String category_icon;
	private List<SubCategoryObject> sub_categories;
	private String bg_colour;
	private String firstpostion;
	
	

	/**
	 * @return the firstpostion
	 */
	public String getFirstpostion() {
		return firstpostion;
	}

	/**
	 * @param firstpostion the firstpostion to set
	 */
	public void setFirstpostion(String firstpostion) {
		this.firstpostion = firstpostion;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCategory_icon() {
		return category_icon;
	}

	public void setCategory_icon(String category_icon) {
		this.category_icon = category_icon;
	}

	public List<SubCategoryObject> getSub_categories() {
		return sub_categories;
	}

	public void setSub_categories(List<SubCategoryObject> sub_categories) {
		this.sub_categories = sub_categories;
	}

	public String getBg_colour() {
		return bg_colour;
	}

	public void setBg_colour(String bg_colour) {
		this.bg_colour = bg_colour;
	}

}
