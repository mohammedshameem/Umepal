package com.qiito.umepal.holder;

import java.io.Serializable;

public class SubCategoryObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private boolean firstpostion;
	
	
	/**
	 * @return the firstpostion
	 */
	public boolean isFirstpostion() {
		return firstpostion;
	}
	/**
	 * @param firstpostion the firstpostion to set
	 */
	public void setFirstpostion(boolean firstpostion) {
		this.firstpostion = firstpostion;
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
	

}
