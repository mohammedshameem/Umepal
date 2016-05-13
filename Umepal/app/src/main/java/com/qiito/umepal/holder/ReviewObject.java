package com.qiito.umepal.holder;

import java.io.Serializable;

public class ReviewObject implements Serializable {


	private static final long serialVersionUID = 1L;

	private int id;
	private String review;
	private String rating;
	private int status;
	private int reviewreply;
	private String created;
	private UserObjectInReviews user;
	private String userFirstName;
	private String userLastName;
	private String userImage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getReviewreply() {
		return reviewreply;
	}

	public void setReviewreply(int reviewreply) {
		this.reviewreply = reviewreply;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public UserObjectInReviews getUser() {
		return user;
	}

	public void setUser(UserObjectInReviews user) {
		this.user = user;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
}
