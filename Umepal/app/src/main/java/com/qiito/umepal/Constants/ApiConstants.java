package com.qiito.umepal.Constants;

public interface ApiConstants {

    String SESSION_ID = "session_id";

    String STATUS = "status";

    String DATA = "data";

    String SUCCESS = "success";

    String ERROR = "error";

    String TYPE = "type";

    String MESSAGE = "Un-Authorised";

    // PRODUCTION BASEURL
    //static final String BASE_URL = "http://lufluf.qiito.com.sg/";

    // STAGING BASEURL

    //static final String BASE_URL = "http://wws.parentsv4.x-minds.info/";


    static final String BASE_URL = "http://umepal-s.x-minds.org/";

    interface Facebook {

        String LOGIN_URL = BASE_URL + "api/facebook-login";

        interface FacebookRequestParams {

            String UNIQUE_DEVICE_ID = "unique_device_id";
            String FB_ID = "fb_id";
            String FB_ACCESS_TOKEN = "access_token";
            String ACCESS_TOKEN = "access_token";
            String FB_ACCESS_EXPIRATION_DATE = "fb_access_expiration_date";
            String CURRENT_LOCATION_LAT = "current_location_lat";
            String CURRENT_LOCATION_LONG = "current_location_long";
            String FIELDS = "fields";
            String DEVICETYPE = "device_type";

        }

    }

    interface EmailLoginRequestParams {

        String EMAILLOGIN_URL = BASE_URL + "api/email-login";
        String EMAIL = "ume_id";
        String PASSWORD = "password";

    }

    interface IntentKeys {
        String CLOSE_ALL = "close_all";
    }

    interface Categories {

        String GET_CATEGORY_URL = BASE_URL + "api/list-all-categories";
        String SESSION_ID = "session_id";
    }

    interface EmailSignUpRequestParams {

        String EMAILSIGNUP_URL = BASE_URL + "api/email-sign-up";
        String FIRSTNAME = "firstName";
        String LASTNAME = "lastName";
        String EMAIL = "email";
        String CEA = "cea";
        String MOBILE = "mobilenumber";
        String BANK = "bank";
        String ESTATEAGENCY = "estateagency";
        String BANKACCOUNT = "bankaccount";
        String PASSWORD = "password";
        String UNIQUEDEVICEID = "uniqueDeviceID";
        String REFERRALMEMBERID = "referralmember";
        String PROFILE_PIC = "profilePic";

    }
    interface MembershipPaypalParams {
        String MEMBERPAYPAL = BASE_URL + "api/member-paypal";
        String SESSION_ID = "session_id";
        String MEMBERSHIPID = "membership_id";
        String REFFEREE_ID = "refferee_id";
        String REFFERER_ID = "refferer_id";
    }
    interface Products {

        String GET_PRODUCTS = BASE_URL + "api/list-all-products";
        String SESSION_ID = "session_id";
        String CATEGORY_ID = "catgory_id";
        String SUB_CATEGORY_ID = "sub_catgory_id";
        String OFFSET = "offset";
        String LIMIT = "limit";

    }

    interface ProductCategories {
        String GET_CATEGORY = BASE_URL + "api/list-all-categories";
        String SESSION_ID = "session_id";
    }

    interface ForgotPasswordRequestParams {

        String FORGOTPASSWORD_URL = BASE_URL + "api/forgot-password";
        String EMAIL = "email";
    }

    interface RestPasswordRequestParams {

        String RESET_PASSWORD_URL = BASE_URL + "api/reset-password";
        String EMAIL = "email";
        String PASSWORD = "password";
        String TOKEN = "token";
    }

    interface UserProfileRequestParams {

        String USERPROFILE_URL = BASE_URL + "api/user-profile";
        String SESSION = "session_id";
        String OFFSET = "offset";
    }

    interface UserEditProfileRequestParams {

        String USER_EDIT_PROFILE_URL = BASE_URL + "api/user-editprofile";
        String SESSION = "session_id";
        String FIRSTNAME = "firstname";
        String LASTNAME = "lastname";
        String EMAIL = "email";
        String CITY = "city";
        String MOBILE = "mobile";
        String PICTURE = "pic";
    }

    interface ProductDetailsRequestParams {

        String PRODUCT_DETAIL_URL = BASE_URL + "api/product-detail";
        String SESSION_ID = "session_id";
        String PRODUCT_ID = "product_id";
    }

    interface ProductDetails {
        String PRODUCTDETAIL_URL = BASE_URL + "api/product-detail";
        String PRODUCT_ID = "product_id";
        String SESSION_ID = "session_id";
        String OFFSET = "offset";
        String LIMIT = "limit";
    }

    interface ProductLike {
        String LIKE_URL = BASE_URL + "api/product/likes";
        String SESSION_ID = "session_id";
        String PRODUCT_ID = "product_id";
    }

    interface ReviewProductRequestParams {

        String REVIEW_URL = BASE_URL + "api/save-review";
        String SESSION_ID = "session_id";
        String PRODUCT_ID = "product_id";
        String REVIEW = "review";
        String RATING = "rating";
    }

    interface PayPalRequestParams {

        String PAYPAL_PURCHASE_URL = BASE_URL + "api/product-purchase";
        String SESSION_ID = "session_id";
        String PRODUCT_IDS = "product_ids";
        String PRODUCT_QUANTITIES = "product_quantities";
        String PRODUCT_COLOR = "colour_ids";
        String PRODUCT_DIMENSION = "dimension_ids";
    }

    interface AdvertisementRequestParams {
        String ADS_END_POINT = BASE_URL + "api/advertisements";
    }

    interface NotificationRequestParams {

        String NOTIFICATION_URL = BASE_URL + "api/notification";
        String SESSION_ID = "session_id";
    }

    interface NotificationDeleteRequestParams {

        String NOTIFICATION_DELETE_URL = BASE_URL + "api/notification-delete";
        String SESSION_ID = "session_id";
        String NOTIFICATION_ID = "notification_id";
        String POSITION = "position";
    }

    interface userLogoutRequestParams {

        String LOGOUT_URL = BASE_URL + "api/logout";
        String SESSION_ID = "session_id";
    }

    interface StoreDetailRequestParams {
        String STORE_DETAIL_URL = BASE_URL + "api/list-products";
        String OFFSET = "offset";
        String SESSION_ID = "session_id";
        String STORE_ID = "store_id";
    }

    interface StoreRatingRequestParams {
        String STORE_RATING_URL = BASE_URL + "api/store-details";
        String SESSION_ID = "session_id";
        String STORE_ID = "storeid";

    }

    interface StoreRatingParams {
        String RATING_URL = BASE_URL + "api/store-rating";
        String SESSION_ID = "session_id";
        String STORE_ID = "storeid";
        String RATING = "store_rating";
    }

    interface SearchProductRequestParams {
        String SEARCH_URL = BASE_URL + "api/search-for-products";
        String SESSION_ID = "session_id";
        String SEARCH_KEY = "search_key";
        String OFFSET = "offset";
        String LIMIT = "limit";

    }

    interface GetProductsfromCartRequestParams {
        String GET_PRODUCTS_FROM_CART = BASE_URL + "api/cart-details";
        String USER_ID = "user_id";
        String SESSION_ID = "session_id";
    }

    interface ShoppingCartRequestParams {
        String ADD_TO_CART_URL = BASE_URL + "api/add-cart";
        String SESSION_ID = "session_id";
        String USER_ID = "user_id";
        String PRODUCT_ID = "product_id";
        String QUANTITY = "quantity";
        String PRODUCT_COLOR = "product_color";
        String PRODUCT_DIMENSION = "product_dimension";
    }

    interface ShoppingCartDeleteRequestParams {
        String DELETE_FROM_CART_URL = BASE_URL + "api/delete-cart";
        String USER_ID = "user_id";
        String SESSION_ID = "session_id";
        String PRODUCT_ID = "product_id";
    }

    interface JoinMembershipRequestParams {
        String JOIN_MEMBERSHIP = BASE_URL + "api/paypal-membership";
        String SESSION_ID = "session_id";
    }

    interface ShippingDetailsRequestParams {

        String SHIPPING_DETAILS_URL = BASE_URL + "api/shipping-address";
        String FULL_NAME = "fullname";
        String STREET_ADDRESS = "streetaddress";
        String UNIT = "unit";
        String COUNTRY = "country";
        String STATE = "state";
        String CITY = "city";
        String POSTAL_CODE = "postalcode";
        String PHONE = "phone";
        String SESSION_ID = "session_id";
    }

    interface  ListRefereesParams {

        String LIST_REFEREES_URL = BASE_URL + "api/list-referees";
        String SESSION_ID = "session_id";
    }

    interface Requestforpayment {

        String REQUEST_FOR_PAYMENT = BASE_URL + "api/reffer-notification";
        String REFFER_ID = "reffer_id";
        String REFFEREE_ID = "referee_id";
        String MEMBERSHIP_ID = "membership_id";

    }
    interface RefferNotification {
        String REFFER_NOTIFICATION_URL = BASE_URL +"api/reffer-notification";
        String REFFER_ID = "reffer_id";//pass umeid of referer)
        String REFFEREE_ID = "referee_id";
        String MEMBERSHIP_ID = "membership_id";

    }
}