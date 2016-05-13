package com.qiito.umepal.holder;

import java.io.Serializable;
import java.util.List;

public class SerialisableCategoryList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CategoryObject>categoryList;
	
	

	/**
	 * @return the categoryList
	 */
	public List<CategoryObject> getCategoryList() {
		return categoryList;
	}

	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List<CategoryObject> categoryList) {
		this.categoryList = categoryList;
	}
	
	

}
