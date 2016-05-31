package com.qiito.umepal.holder;

import com.qiito.umepal.Constants.Membership;

import java.io.Serializable;

public class UserResponseHolder implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String session_id;
    private UserObjectHolder user;
    private MembershipObject membership;

    //private List<PurchasedItem> purchased_items;
    //private List<LikedItem> liked_products;


    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    /**
     * @return the user
     */
    public UserObjectHolder getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserObjectHolder user) {
        this.user = user;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public MembershipObject getMembership() {
        return membership;
    }

    public void setMembership(MembershipObject membership) {
        this.membership = membership;
    }

}
