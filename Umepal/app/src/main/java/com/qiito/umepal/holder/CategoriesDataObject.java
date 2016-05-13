package com.qiito.umepal.holder;

import java.io.Serializable;
import java.util.List;

public class CategoriesDataObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CategoryObject> category;

	public List<CategoryObject> getCategory() {
		return category;
	}

	public void setCategory(List<CategoryObject> category) {
		this.category = category;
	}

}
