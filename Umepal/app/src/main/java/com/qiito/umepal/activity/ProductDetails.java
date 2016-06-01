package com.qiito.umepal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.qiito.umepal.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.adapter.ExpandableHeightGridView;
import com.qiito.umepal.adapter.ProductAdapter;
import com.qiito.umepal.adapter.ReviewAdapter;
import com.qiito.umepal.adapters.ArrayWheelAdapter;
import com.qiito.umepal.adapters.ImageViewPagerAdapter;
import com.qiito.umepal.holder.CheckoutProductsHolder;
import com.qiito.umepal.holder.LikedProducts;
import com.qiito.umepal.holder.MyProfileBaseclass;
import com.qiito.umepal.holder.ProductCategoryBaseHolder;
import com.qiito.umepal.holder.ProductDetailsBaseHolder;
import com.qiito.umepal.holder.ProductLikeHolder;
import com.qiito.umepal.holder.ProductObject;
import com.qiito.umepal.holder.ReviewBaseHolder;
import com.qiito.umepal.holder.ReviewObject;
import com.qiito.umepal.holder.ShoppingCartResponseHolder;
import com.qiito.umepal.holder.StoreRatingBaseHolder;
import com.qiito.umepal.holder.UserObjectHolder;
import com.qiito.umepal.listeners.OnWheelChangedListener;
import com.qiito.umepal.listeners.OnWheelScrollListener;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.ProductDetailsManager;
import com.qiito.umepal.managers.ProductLikeManager;
import com.qiito.umepal.managers.ReviewManager;
import com.qiito.umepal.managers.ShoppingCartManager;
import com.qiito.umepal.managers.StoreRatingManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.whellview.WheelView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import static com.qiito.umepal.Utilvalidate.NetChecker.isConnected;


/**
 * Created by abin on 11/9/15.
 */
public class ProductDetails extends FragmentActivity {

    private ProductCallBack productCallBack;
    private ProductAdapter productAdapter;
    private UserObjectHolder userObjectHolder;
    private ProductDetailsBaseHolder productDetailsBaseHolder;
    private ProductObject productObject;
    private ReviewAdapter reviewAdapter;
    private ReviewCallBack reviewCallBack;
    private ReviewBaseHolder reviewBaseHolder;
    private ImageViewPagerAdapter imageViewPagerAdapter;
    private LikeCallBack likeCallBack;
    //private UnlikeCallBack unlikeCallBack;
    private SetRatingCallBack setRatingCallBack;

    private ArrayAdapter<String> adapter;
    private android.app.FragmentManager fragmentManager;


    private List<ProductObject> youmaylikeList = new ArrayList<>();
    private List<ProductCategoryBaseHolder> productsDetails;
    private List<ReviewObject> reviewlist = new ArrayList<>();
    private ArrayList<String> productimagelist = new ArrayList<String>();
    private ArrayList<String> quant = new ArrayList<String>();
    private List<LikedProducts> likedProducts = new ArrayList<>();
    private List<CheckoutProductsHolder> checkoutProductsList = new ArrayList<>();

    private ListView reviewListview;

    private LinearLayout continue_readingTxtlayout;
    private LinearLayout descriptionLayout;
    private LinearLayout buyItem;
    private LinearLayout onlyleftlinear;
    private LinearLayout review_write_linear;
    private LinearLayout reviewSubmitLinear;
    private LinearLayout shareApp;
    private LinearLayout likeLinear;
    private LinearLayout linear_shipping_details;
    private LinearLayout topBuy;
    private LinearLayout lessDescriptionLayout;
    private LinearLayout reviewLayout;
    private LinearLayout popularLayout;
    private LinearLayout editorLayout;
    private LinearLayout availabilityLayout;
    private LinearLayout estimatedArrivalLayout;
    private LinearLayout shipsFromLayout;
    private LinearLayout quantityLinear;
    private LinearLayout sizeLinear;
    private LinearLayout colorLinear;
    private LinearLayout sizeLayout;
    private LinearLayout colorLayout;

    private ExpandableHeightGridView gridview;
    private ScrollView SCROLLER_ID;

    private RatingBar storeRatingStar;

    private LinearLayout scroll;

    private PopupWindow popupWindow;
    private PopupWindow mPopup;

    private WheelView wheelView;

    private Button buy;
    private Button cancel;

    private TextView productNametext;
    private TextView productPriceText;
    private TextView productPriceinGreen;
    private TextView productLikeCount;
    private TextView StoreName;
    private TextView productDiscountText;
    private TextView productsDetailstxt;
    private TextView numberOfItemLeft;
    private TextView profileName;
    private TextView time;
    private TextView crossedPrice;
    private TextView numberofReviews;
    private TextView viewAllReviews;
    private TextView backViewAllReviews;
    private TextView review_profile_name;
    private TextView submitReview;
    private TextView continueReadingtxt;
    private TextView storeRating;
    private TextView orginal_price;
    private TextView savings;
    private TextView estimatedShipingTxt;
    private TextView productAvailabiltyTxt;
    private TextView estimatedArrivalTxt;
    private TextView productShipsFromStore;
    private TextView returnPolicyTxt;
    private TextView pageHeading;
    private TextView visitStore;
    private TextView viewAllRatings;
    private TextView lessDescription;
    private TextView details;
    private TextView shipsFromAddress;
    private TextView collectatstore;
    private TextView quantity;
    private TextView color;
    private TextView size;
    private TextView shopinfo;
    private TextView donePopup;
    private EditText review_editText;
    private String review;

    private ImageView backMenuIcon;
    private ImageView product_Image;
    private ImageView onlyleftorange;
    private ImageView profileImage;
    private ImageView cartIcon;
    private ImageView review_profile_image;
    private ImageView likeGray;
    private ImageView likeGreen;
    private ImageView closePopup;

    private String sessionId;
    private String onlyleft;
    private String userid;
    private String pdt_id;
    private String productId;
    private String productName;
    private String productPrice;
    private String productImage;
    private String storeName;
    private String shippingCharge;
    private String EstimatedArrival;
    private String Availability;
    private String newPrice;
    private String savingroundedprice;
    private String offer_price;
    private String product_discount;
    private String stock_count;
    private String strQuantity = "1";
    private String savedPrice;
    private String numbers;
    private String colours;
    private String dimen;

    private int categoryId;
    private int offset = 0;
    private int limit = 10;
    private int count = 1;
    private int product_id;
    private int id = 0;
    private int purchaseID = 0;
    private int estimatedShipping = 0;
    private int widthofDescription;
    private int heightofDescription;
    private int newQuantity;

    private boolean mWheelScrolled = false;
    private String mIntialMeeting = "";
    private String[] mMeeting;

    private RelativeLayout nonmember_crosseddoller;
    private TextView memberPrice;
    private TextView member_word;
    private TextView crossed_dollar_nonmember;
    private RelativeLayout crossed_relative;
    private LinearLayout nonmember_txt_layout;
    private boolean newProductFlag;
    private LinearLayout pricelayout;

    private ViewPager viewPager;
    private CirclePageIndicator circlePageIndicator;
    private FragmentTransaction shoppingCartFragmentTransaction;
    private Dialog dialogTransparent;
    private View progressview;
    private AddToCartCallBack addToCartCallBack;
    private Activity activity;

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        sessionId = DbManager.getInstance().getSessionId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_main);
        sessionId = DbManager.getInstance().getSessionId();

        initView();
        initManager();
        setVisibility();
        userObjectHolder = DbManager.getInstance().getCurrentUserDetails();
        Intent intent = getIntent();

        FacebookSdk.sdkInitialize(this);

        product_id = intent.getIntExtra("productid", id);


        int fromPurchases = intent.getIntExtra("PURCHASES", purchaseID);

        /*int estimatedSHIPPING = intent.getIntExtra("estimatedShipping", estimatedShipping);

        final String availability = intent.getStringExtra("availability");
        String estimatedarrival = intent.getStringExtra("estimatedArrival");
        String shipsfrom = intent.getStringExtra("shipsFromAddress");
        String returnpolicy = intent.getStringExtra("returnPolicy");*/

        if (fromPurchases == 1) {
            linear_shipping_details.setVisibility(View.VISIBLE);
        }


        pdt_id = String.valueOf((product_id));

        dialogTransparent = new Dialog(this, android.R.style.Theme_Black);
        progressview = LayoutInflater.from(this).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);
        dialogTransparent.show();

        ProductDetailsManager.getInstance().getProductDetails(ProductDetails.this,
                pdt_id, sessionId, productCallBack);

        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        reviewLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));


        viewAllReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (reviewlist.size() > 3) {
                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                    int height = Math.round(getResources().getDimension(R.dimen.review_height_more));
                    reviewLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                    viewAllReviews.setVisibility(View.GONE);
                    backViewAllReviews.setVisibility(View.VISIBLE);
                }
            }
        });
        backViewAllReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reviewlist.size() == 1) {
                    int height = Math.round(getResources().getDimension(R.dimen.review_height_1));
                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                    reviewLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                } else if (reviewlist.size() == 2) {
                    int height = Math.round(getResources().getDimension(R.dimen.review_height_2));
                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                    reviewLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));

                } else {
                    int height = Math.round(getResources().getDimension(R.dimen.review_height_3));
                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                    reviewLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                }
                backViewAllReviews.setVisibility(View.GONE);
                viewAllReviews.setVisibility(View.VISIBLE);
            }
        });


        review_write_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionId != null) {
                    if (!sessionId.equalsIgnoreCase("")) {
                        review_write_linear.setVisibility(View.GONE);
                        reviewSubmitLinear.setVisibility(View.VISIBLE);
                    }
                } else {
                    loginPopup();
                }


            }
        });
        reviewSubmitLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                review = review_editText.getText().toString();

                String rating = "0";

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(review_editText.getWindowToken(), 0);
                sessionId = DbManager.getInstance().getSessionId();
                Log.e("sessionId", "review" + sessionId);
                Log.e("product", "review" + product_id);
                Log.e("review", "review" + review);
                Log.e("rating", "review" + rating);
                if (review.equals("")) {
                    review_editText.setError("write something");
                } else {
                    reviewSubmitLinear.setVisibility(View.GONE);
                    review_write_linear.setVisibility(View.VISIBLE);
                    ReviewManager.getInstance().saveReview(ProductDetails.this, reviewCallBack, sessionId, product_id, review, rating);
                    review_editText.setText("");
                    reviewAdapter.notifyDataSetChanged();

                    ProductDetailsManager.getInstance().getProductDetails(ProductDetails.this,
                            pdt_id, sessionId, productCallBack);
                }
            }
        });


        if (isConnected(ProductDetails.this)) {

        } else {
            dialogTransparent.dismiss();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(ProductDetails.this);
            builder1.setMessage("Network Unavailable!!");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    startActivity(getIntent());
                }
            });
            builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialogTransparent.show();
                    dialog.cancel();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        Log.e("no of starts>>>>", "" + storeRatingStar.getNumStars());

        buyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userid = String.valueOf(userObjectHolder.getId());

                if (productObject != null) {
                    if (productObject.getId() != 0) {
                        productId = String.valueOf(productObject.getId());
                    }

                    if (productObject.getName() != null) {
                        productName = String.valueOf(productObject.getName());
                    }

                    if (productObject.getPrice() != null) {
                        if (productObject.getPromoprice() != null) {
                            double saving = Double.parseDouble(productObject.getPrice()) - Double.parseDouble(productObject.getPromoprice());
                            savedPrice = String.valueOf(saving);
                        }
                    }
                    if (productObject.getPromoprice() != null) {
                        productPrice = productObject.getPromoprice();
                    }

                    if (productObject.getImage() != "") {

                        productImage = String.valueOf(productObject.getImage());

                    } else {
                        productImage = String.valueOf(R.drawable.logo_splash);
                    }
                    if (UtilValidate.isNotNull(productObject.getStore_detalis())) {

                        if (UtilValidate.isNotNull(productObject.getStore_detalis().getStore())) {

                            if (UtilValidate.isNotNull(productObject.getStore_detalis().getStore().getStoreName())) {

                                storeName = productObject.getStore_detalis().getStore().getStoreName();
                            }
                        }
                    }
                    if (UtilValidate.isNotNull(productObject.getShippingcharge())) {
                        shippingCharge = productObject.getShippingcharge();
                    }
                    if (UtilValidate.isNotNull(productObject.getEstimated_arrival())) {
                        EstimatedArrival = productObject.getEstimated_arrival();
                    }
                    if (UtilValidate.isNotNull(productObject.getAvailability())) {
                        Availability = productObject.getAvailability();
                    }
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    if (productObject.getStockCount() != null || productObject.getStockCount().equalsIgnoreCase("")) {
                        if (Integer.parseInt(productObject.getStockCount()) > 0) {
                            getQuantity(ProductDetails.this, userid, productId, productName, productPrice, productImage,
                                    storeName, shippingCharge, EstimatedArrival, Availability, savedPrice);

                        } else {
                            Toast.makeText(ProductDetails.this, productObject.getName() + " is out of stock!", Toast.LENGTH_LONG).show();
                        }
                    }

                }

            }
        });

        topBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userid = String.valueOf(userObjectHolder.getId());

                if (productObject != null) {
                    if (productObject.getId() != 0) {
                        productId = String.valueOf(productObject.getId());
                    }

                    if (productObject.getName() != null) {
                        productName = String.valueOf(productObject.getName());
                    }
                    if (productObject.getPromoprice() != null) {
                        productPrice = productObject.getPromoprice();
                    }

                    if (productObject.getPrice() != null) {
                        if (productObject.getPromoprice() != null) {
                            double saving = Double.parseDouble(productObject.getPrice()) - Double.parseDouble(productObject.getPromoprice());
                            savedPrice = String.valueOf(saving);
                        }
                    }

                    if (productObject.getImage() != "") {

                        productImage = String.valueOf(productObject.getImage());

                    } else {
                        productImage = String.valueOf(R.drawable.logo_splash);
                    }
                    if (UtilValidate.isNotNull(productObject.getStore_detalis())) {

                        if (UtilValidate.isNotNull(productObject.getStore_detalis().getStore())) {

                            if (UtilValidate.isNotNull(productObject.getStore_detalis().getStore().getStoreName())) {

                                storeName = productObject.getStore_detalis().getStore().getStoreName();
                            }
                        }
                    }
                    if (UtilValidate.isNotNull(productObject.getShippingcharge())) {
                        shippingCharge = productObject.getShippingcharge();
                    }
                    if (UtilValidate.isNotNull(productObject.getEstimated_arrival())) {
                        EstimatedArrival = productObject.getEstimated_arrival();
                    }
                    if (UtilValidate.isNotNull(productObject.getAvailability())) {
                        Availability = productObject.getAvailability();
                    }
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }

                    if (Integer.parseInt(productObject.getStockCount()) > 0) {
                        getQuantity(ProductDetails.this, userid, productId, productName, productPrice, productImage,
                                storeName, shippingCharge, EstimatedArrival, Availability, savedPrice);

                    } else {
                        Toast.makeText(ProductDetails.this, productObject.getName() + " is out of stock!", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });


        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, productObject.getName() + " \n  " + productObject.getImage());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        visitStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilValidate.isNotNull(productObject.getStore_detalis())) {
                    if (UtilValidate.isNotNull(productObject.getStore_detalis().getStore())) {

                        Intent storeIntent = new Intent(ProductDetails.this, VisitStoreActivity.class);
                        storeIntent.putExtra("storeid", productObject.getStore_detalis().getStore().getId());
                        startActivity(storeIntent);
                    }
                }
            }
        });


        backMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        widthofDescription = LinearLayout.LayoutParams.MATCH_PARENT;
        heightofDescription = 73;

        descriptionLayout.setLayoutParams(new LinearLayout.LayoutParams(widthofDescription, heightofDescription));
        continueReadingtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int newWidthofDescription = LinearLayout.LayoutParams.MATCH_PARENT;

                int newHeightofDescription = LinearLayout.LayoutParams.WRAP_CONTENT;
                descriptionLayout.setLayoutParams(new LinearLayout.LayoutParams(newWidthofDescription, newHeightofDescription));
                continue_readingTxtlayout.setVisibility(View.GONE);
                lessDescriptionLayout.setVisibility(View.VISIBLE);


            }
        });

        continueReadingtxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        productsDetailstxt.setMovementMethod(new ScrollingMovementMethod());


        lessDescriptionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                descriptionLayout.setLayoutParams(new LinearLayout.LayoutParams(widthofDescription, heightofDescription));
                continue_readingTxtlayout.setVisibility(View.VISIBLE);
                lessDescriptionLayout.setVisibility(View.GONE);

            }
        });

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(ProductDetails.this, ShoppingCart.class);

                startActivity(cart);
            }
        });


     /*   scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });*/

        viewAllRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilValidate.isNotNull(productObject.getStore_detalis())) {
                    if (UtilValidate.isNotNull(productObject.getStore_detalis().getStore())) {
                        Intent rateintent = new Intent(ProductDetails.this, StoreRatingActivity.class);
                        rateintent.putExtra("id", productObject.getStore_detalis().getStore().getId());
                        startActivity(rateintent);
                    }
                }
            }
        });

        storeRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionId != null) {
                    if (!sessionId.equalsIgnoreCase("")) {
                        String rating = String.valueOf(storeRatingStar.getRating());
                        if (UtilValidate.isNotNull(productObject.getStore_detalis())) {
                            if (UtilValidate.isNotNull(productObject.getStore_detalis().getStore())) {
                                String storeid = String.valueOf(productObject.getStore_detalis().getStore().getId());
                                Log.e("" + rating, ">>>" + storeid);

                                StoreRatingManager.getInstance().setRating(ProductDetails.this, setRatingCallBack, sessionId, storeid, rating);
                            }
                        }
                    } else {
                        loginPopup();
                    }
                } else {
                    loginPopup();
                }
            }
        });

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnintent = new Intent(ProductDetails.this, ReturnPolicy.class);
                startActivity(returnintent);
            }
        });


    }

    private void setVisibility() {
        if (sessionId != null) {
            if (!sessionId.equals("")) {
                cartIcon.setVisibility(View.VISIBLE);
            }
        }
        pageHeading.setVisibility(View.VISIBLE);
        backViewAllReviews.setVisibility(View.GONE);
        reviewSubmitLinear.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        userObjectHolder = DbManager.getInstance().getCurrentUserDetails();
        sessionId = DbManager.getInstance().getSessionId();
        ProductDetailsManager.getInstance().getProductDetails(ProductDetails.this,
                pdt_id, sessionId, productCallBack);

        /*if (backViewAllReviews.getVisibility() == View.VISIBLE){

            Log.e("","in resume>>"+backViewAllReviews.getVisibility());
            viewAllReviews.setVisibility(View.GONE);
            backViewAllReviews.setVisibility(View.VISIBLE);
        }*/


    }

    private void initManager() {
        productCallBack = new ProductCallBack();
        productObject = new ProductObject();
        userObjectHolder = new UserObjectHolder();
        productDetailsBaseHolder = new ProductDetailsBaseHolder();
        reviewCallBack = new ReviewCallBack();
        likeCallBack = new LikeCallBack();
        //unlikeCallBack = new UnlikeCallBack();
        reviewBaseHolder = new ReviewBaseHolder();
        setRatingCallBack = new SetRatingCallBack();
        addToCartCallBack = new AddToCartCallBack();
    }

    private void initView() {

        continue_readingTxtlayout = (LinearLayout) findViewById(R.id.continue_readingTxtlayout);
        lessDescriptionLayout = (LinearLayout) findViewById(R.id.less_description_layout);
        buyItem = (LinearLayout) findViewById(R.id.linear_buy);
        onlyleftlinear = (LinearLayout) findViewById(R.id.onlyleftlinear);
        descriptionLayout = (LinearLayout) findViewById(R.id.descriptionLayout);
        shareApp = (LinearLayout) findViewById(R.id.linear_options);
        review_write_linear = (LinearLayout) findViewById(R.id.write_review_linear);
        reviewSubmitLinear = (LinearLayout) findViewById(R.id.submit_review_linear);
        topBuy = (LinearLayout) findViewById(R.id.top_buy);
        reviewLayout = (LinearLayout) findViewById(R.id.listview_linear);
        gridview = (ExpandableHeightGridView) findViewById(R.id.products_grid_you_may_also_like);
        profileName = (TextView) findViewById(R.id.profile_name);
        productNametext = (TextView) findViewById(R.id.product_name);
        productPriceText = (TextView) findViewById(R.id.product_price);
        crossedPrice = (TextView) findViewById(R.id.crossed_dollar_price);
        productsDetailstxt = (TextView) findViewById(R.id.details_about_product);
        productDiscountText = (TextView) findViewById(R.id.discount_value);
        productLikeCount = (TextView) findViewById(R.id.product_like_count);
        numberOfItemLeft = (TextView) findViewById(R.id.only_left_num);
        continueReadingtxt = (TextView) findViewById(R.id.continuereading);
        orginal_price = (TextView) findViewById(R.id.orginal_price);
        productPriceinGreen = (TextView) findViewById(R.id.product_price_green);
        savings = (TextView) findViewById(R.id.saving);
        collectatstore = (TextView) findViewById(R.id.collectatstore);
        storeRating = (TextView) findViewById(R.id.rateit);
        StoreName = (TextView) findViewById(R.id.store_name);
        lessDescription = (TextView) findViewById(R.id.less_description);
        numberofReviews = (TextView) findViewById(R.id.num_of_reviews);
        viewAllReviews = (TextView) findViewById(R.id.view_all_reviews);
        backViewAllReviews = (TextView) findViewById(R.id.backViewAllReviews);
        submitReview = (TextView) findViewById(R.id.submit_review);
        pageHeading = (TextView) findViewById(R.id.page_heading);
        visitStore = (TextView) findViewById(R.id.visit_store_textview);
        viewAllRatings = (TextView) findViewById(R.id.view_all_ratings);
        details = (TextView) findViewById(R.id.product_return_details);
        shipsFromAddress = (TextView) findViewById(R.id.ships_from_address);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        backMenuIcon = (ImageView) findViewById(R.id.back_menu_icon);
        cartIcon = (ImageView) findViewById(R.id.add_to_cart_icon);
        storeRatingStar = (RatingBar) findViewById(R.id.ratebar);
        reviewListview = (ListView) findViewById(R.id.listview_reviews);
        review_editText = (EditText) findViewById(R.id.review_editText);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circle_page_indicator);
        likeLinear = (LinearLayout) findViewById(R.id.linear_like);
        likeGray = (ImageView) findViewById(R.id.likegrey);
        likeGreen = (ImageView) findViewById(R.id.likegreen);
        scroll = (LinearLayout) findViewById(R.id.scrollYouMayAlsoLike);

        //SCROLLER_ID=(ScrollView)findViewById(R.id.SCROLLER_ID);
        estimatedShipingTxt = (TextView) findViewById(R.id.estimated_shipping_dollar);
        productAvailabiltyTxt = (TextView) findViewById(R.id.productAvailability);
        estimatedArrivalTxt = (TextView) findViewById(R.id.estimated_arrival);
        productShipsFromStore = (TextView) findViewById(R.id.ships_from_store);
        returnPolicyTxt = (TextView) findViewById(R.id.return_policy);
        linear_shipping_details = (LinearLayout) findViewById(R.id.linear_shipping_details);

        nonmember_txt_layout = (LinearLayout) findViewById(R.id.nonmember_txt_layout);
        crossed_relative = (RelativeLayout) findViewById(R.id.crossed_relative);
        nonmember_crosseddoller = (RelativeLayout) findViewById(R.id.nonmember_crosseddoller);
        member_word = (TextView) findViewById(R.id.member_word);
        crossed_dollar_nonmember = (TextView) findViewById(R.id.crossed_dollar_nonmember);
        pricelayout = (LinearLayout) findViewById(R.id.pricelayout);

        memberPrice = (TextView) findViewById(R.id.membership_price);
        popularLayout = (LinearLayout) findViewById(R.id.linear_popular);
        shopinfo = (TextView) findViewById(R.id.shopinfo);

        editorLayout = (LinearLayout) findViewById(R.id.linear_editorspic);
        //availabilityLayout = (LinearLayout)findViewById(R.id.availabi);
        estimatedArrivalLayout = (LinearLayout) findViewById(R.id.estimatedarrival_layout);
        shipsFromLayout = (LinearLayout) findViewById(R.id.shipsfrom_layout);

    }

    private void getQuantity(final Activity activity, final String userId, final String pdtId, final String pdtName,
                             final String pdtPrice, final String pdtImage, final String StoreName,
                             final String shipingcharge, final String estmarrive, final String avail, final String SavedPrice) {
        if (sessionId != null) {
            if (!sessionId.equalsIgnoreCase("")) {
                try {

                    LinearLayout viewGroup = (LinearLayout) activity.findViewById(R.id.quantity_popup_layout);
                    LayoutInflater inflater = (LayoutInflater) activity
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View layout = inflater.inflate(R.layout.quantity_popup, null);

                    popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);

                    popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

                    quantityLinear = (LinearLayout) layout.findViewById(R.id.quantityLinear);
                    sizeLinear = (LinearLayout) layout.findViewById(R.id.sizeLinear);
                    colorLinear = (LinearLayout) layout.findViewById(R.id.colorLinear);
                    quantity = (TextView) layout.findViewById(R.id.quantity_spinner);
                    size = (TextView) layout.findViewById(R.id.size_spinner);
                    color = (TextView) layout.findViewById(R.id.color_spinner);
                    buy = (Button) layout.findViewById(R.id.buy_quantity_popup);
                    cancel = (Button) layout.findViewById(R.id.cancel_quantity_popup);
                    sizeLayout = (LinearLayout) layout.findViewById(R.id.size_layout);
                    colorLayout = (LinearLayout) layout.findViewById(R.id.color_layout);

                    quant = new ArrayList<>();

                    if (productObject.getStockCount() != "") {
                        int stockCount = Integer.parseInt(productObject.getStockCount());
                        final String quantit[] = new String[stockCount];
                        for (int j = 0; j < stockCount; j++) {
                            quant.add(String.valueOf(j + 1));
                            quantit[j] = quant.get(j);

                        }
                        quantity.setText(quantit[0]);
                        quantityLinear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                wheelviewSelectionPopup(ProductDetails.this, quantit, quantity);
                            }
                        });
                    }

                    if (UtilValidate.isNotEmpty(productObject.getColor())) {

                        colorLayout.setVisibility(View.VISIBLE);

                        final String colors[] = new String[productObject.getColor().size()];
                        for (int j = 0; j < productObject.getColor().size(); j++) {
                            colors[j] = productObject.getColor().get(j);
                        }
                        color.setText(colors[0]);
                        colorLinear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                wheelviewSelectionPopup(ProductDetails.this, colors, color);

                            }
                        });
                    } else {
                        colorLayout.setVisibility(View.GONE);
                        // Toast.makeText(ProductDetails.this, "No color informations are available!!", Toast.LENGTH_LONG).show();
                    }

                    if (UtilValidate.isNotEmpty(productObject.getDimension())) {
                        sizeLayout.setVisibility(View.VISIBLE);

                        final String sizes[] = new String[productObject.getDimension().size()];
                        for (int j = 0; j < productObject.getDimension().size(); j++) {
                            sizes[j] = productObject.getDimension().get(j);
                        }
                        size.setText(sizes[0]);
                        sizeLinear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                wheelviewSelectionPopup(ProductDetails.this, sizes, size);
                            }
                        });
                    } else {

                        sizeLayout.setVisibility(View.GONE);
                        //Toast.makeText(ProductDetails.this, "No dimension informations are available!!", Toast.LENGTH_LONG).show();
                    }

                    buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            productImage = pdtImage;

                            Log.e(">>", "userid>>>" + userid);
                            Log.e(">>", "pdtID>>>" + productId);
                            Log.e(">>", "pdtName>>>" + productName);
                            Log.e(">>", "pdtprice>>>" + productPrice);
                            Log.e(">>", "pdtQuantity>>>" + numbers);
                            Log.e(">>", "pdtimage>>>" + productImage);
                            Log.e(">>", "store name>>>>" + StoreName);
                            Log.e(">>", "availability??????????" + avail);
                            Log.e(">>", "SAVINGS>>" + SavedPrice);

                            if (userId != "") {
                                userid = userId;

                            }
                            if (pdtId != "") {
                                productId = pdtId;

                            }
                            if (pdtName != "") {
                                productName = pdtName;
                            }
                            if (pdtPrice != "") {
                                productPrice = pdtPrice;

                            }
                            if (pdtImage != "") {
                                productImage = pdtImage;

                            }
                            if (StoreName != "") {
                                storeName = StoreName;

                            } else {
                                storeName = "(null)";
                            }
                            if (shipingcharge != "") {
                                shippingCharge = shipingcharge;

                            } else {
                                shippingCharge = "";
                            }

                            if (estmarrive != "") {
                                EstimatedArrival = estmarrive;

                            } else {
                                EstimatedArrival = "";
                            }

                            if (avail != "") {
                                Availability = avail;

                            } else {
                                Availability = "";
                            }
                            if (SavedPrice != "") {
                                savedPrice = SavedPrice;
                            } else {
                                savedPrice = "";
                            }

                    /*if (Integer.parseInt(strQuantity) > Integer.parseInt(productObject.getStockCount())) {
                        Toast.makeText(ProductDetails.this, "Stock Count is " + productObject.getStockCount() + "!! You have enter a bigger value", Toast.LENGTH_LONG).show();
                    }*/

                            numbers = quantity.getText().toString();
                            colours = color.getText().toString();
                            dimen = size.getText().toString();

                            ShoppingCartManager.getInstance().addToCart(ProductDetails.this, addToCartCallBack,
                                    userId, productId, numbers, colours, dimen, sessionId);
                            popupWindow.dismiss();


                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            loginPopup();
        }
    }


    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            if (position < -1) {
                view.setAlpha(0);
            } else if (position <= 0) {
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);
            } else if (position <= 1) {
                view.setAlpha(1 - position);
                view.setTranslationX(pageWidth * -position);
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else {
                view.setAlpha(0);
            }
        }
    }


    public class ProductCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {

            ProductDetailsBaseHolder productDetailsBaseHolder = (ProductDetailsBaseHolder) result;
            if (productDetailsBaseHolder != null) {

                if (productDetailsBaseHolder.getStatus().equals("success")) {
                    productObject = productDetailsBaseHolder.getData().getProduct();

                    pricelayout.setVisibility(View.VISIBLE);

                    if (productObject.getPrice() != null) {
                        crossed_dollar_nonmember.setText("$ " + productObject.getPrice());
                        crossedPrice.setText("$ " + productObject.getPrice());
                        orginal_price.setText("$ " + productObject.getPrice());
                    }
                       /* check wether a member or not */
                    if (sessionId != null) {
                        if (!sessionId.equals("")) {
                            if (UtilValidate.isNotNull(DbManager.getInstance().getCurrentUserDetails())) {
                                if ((DbManager.getInstance().getCurrentUserDetails().is_member())) {
                                    // is a member
                                    nonmember_crosseddoller.setVisibility(View.GONE);
                                    if (productObject.getDiscountprice() != null) {
                                        memberPrice.setText("$ " + productObject.getDiscountprice());
                                        double saving = Double.parseDouble(productObject.getPrice()) - Double.parseDouble(productObject.getDiscountprice());
                                        String save = String.format("%.2f", saving);
                                        savings.setText("$ " + save);
                                    }
                                    if (productObject.getPromoprice() != null) {
                                        productPriceinGreen.setText("$ " + productObject.getPromoprice());
                                        productPriceText.setText("$ " + productObject.getDiscountprice());
                                    }

                                } else {
                                    //not a member
                                    crossed_relative.setVisibility(View.GONE);
                                    nonmember_txt_layout.setVisibility(View.VISIBLE);
                                    member_word.setVisibility(View.GONE);
                                    if (productObject.getPromoprice() != null) {
                                        memberPrice.setText("$ " + productObject.getPromoprice());
                                        double saving = Double.parseDouble(productObject.getPrice()) - Double.parseDouble(productObject.getPromoprice());
                                        String save = String.format("%.2f", saving);
                                        savings.setText("$ " + save);
                                    }
                                    if (productObject.getPromoprice() != null) {
                                        productPriceinGreen.setText("$ " + productObject.getDiscountprice());
                                        productPriceText.setText("$ " + productObject.getPromoprice());
                                    }
                                }
                            }
                        }
                    } else {
                        crossed_relative.setVisibility(View.GONE);
                        nonmember_txt_layout.setVisibility(View.VISIBLE);
                        member_word.setVisibility(View.GONE);
                        if (productObject.getPromoprice() != null) {
                            memberPrice.setText("$ " + productObject.getPromoprice());
                            if (productObject.getPromoprice() != null) {
                                productPriceinGreen.setText("$ " + productObject.getDiscountprice());
                                productPriceText.setText("$ " + productObject.getPromoprice());
                            }
                        }
                        if (productObject.getPrice() != null) {
                            if (productObject.getPromoprice() != null) {
                                double saving = Double.parseDouble(productObject.getPrice()) - Double.parseDouble(productObject.getPromoprice());
                                String save = String.format("%.2f", saving);
                                savings.setText("$ " + save);
                            }
                        }
                    }

                    if (productDetailsBaseHolder.getData() != null) {

                        if (productDetailsBaseHolder.getData().getProduct() != null) {
                            /*if (UtilValidate.isNotNull(productDetailsBaseHolder.getData().getProduct().getCollect_at_store())) {
                                if (productDetailsBaseHolder.getData().getProduct().getCollect_at_store() == 1) {
                                    collectatstore.setVisibility(View.VISIBLE);
                                }
                            }*/
                        /* STORE PICK UP */
                            if (UtilValidate.isNotNull(productDetailsBaseHolder.getData().getProduct().getCollect_at_store())) {
                                if (productDetailsBaseHolder.getData().getProduct().getCollect_at_store() == 1) {
                                    collectatstore.setVisibility(View.VISIBLE);
                                    productAvailabiltyTxt.setText(productObject.getShipping_from_address());
                                    shopinfo.setVisibility(View.VISIBLE);
                                    estimatedArrivalLayout.setVisibility(View.GONE);
                                    shipsFromLayout.setVisibility(View.GONE);
                                    estimatedShipingTxt.setText("$0.00");


                                } else {
                                    shopinfo.setVisibility(View.GONE);
                                    if (UtilValidate.isNotNull(productObject.getAvailability())) {
                                        productAvailabiltyTxt.setText(productObject.getAvailability());
                                    }
                                    if (UtilValidate.isNotNull(productObject.getShippingcharge())) {
                                        estimatedShipingTxt.setText("$ " + productObject.getShippingcharge());
                                    }
                                }
                            }

                            productObject = productDetailsBaseHolder.getData().getProduct();


                            /*product image sliding*/
                            productimagelist.clear();
                            if (UtilValidate.isNotNull(productObject.getImage())) {

                                if (productObject.getImage() != "") {
                                    productimagelist.add(productObject.getImage());
                                } else {
                                }
                            }
                            if (UtilValidate.isNotNull(productObject.getImageone())) {

                                if (productObject.getImageone() != "") {
                                    productimagelist.add(productObject.getImageone());
                                }
                            }
                            if (UtilValidate.isNotNull(productObject.getImagetwo())) {

                                if (productObject.getImagetwo() != "") {
                                    productimagelist.add(productObject.getImagetwo());
                                }
                            }
                            if (UtilValidate.isNotNull(productObject.getImagethree())) {

                                if (productObject.getImagethree() != "") {
                                    productimagelist.add(productObject.getImagethree());
                                }
                            }
                            if (UtilValidate.isNotNull(productObject.getImagefour())) {
                                if (productObject.getImagefour() != "") {
                                    productimagelist.add(productObject.getImagefour());
                                }
                            }


                            imageViewPagerAdapter = new ImageViewPagerAdapter(getApplicationContext(), productimagelist, ProductDetails.this);

                            viewPager.setAdapter(imageViewPagerAdapter);
                            circlePageIndicator.setViewPager(viewPager);
                            viewPager.setPageTransformer(true, new DepthPageTransformer());


                            Log.e("!!", "IMAGE SLIDER SIZE" + productimagelist.size());
                            if (productimagelist.size() == 0) {

                            }
                            if (productimagelist.size() <= 1) {
                                circlePageIndicator.setVisibility(View.INVISIBLE);
                            } else {
                                circlePageIndicator.setVisibility(View.VISIBLE);
                            }


                            if (productObject.getName() != null) {
                                productNametext.setText(productObject.getName());
                            }
                            if (UtilValidate.isNotNull(productObject.getDiscount())) {
                                if (productObject.getDiscount() != "") {
                                    product_discount = String.valueOf(productObject.getDiscount());
                                    float d = Float.parseFloat(productObject.getDiscount());
                                    Math.round(d);
                                    String dd = String.valueOf(Math.round(d));

                                    productDiscountText.setText(dd);
                                }
                            }




                          /*  if (productObject.getPromoprice() != null){
                                productPriceinGreen.setText("$ "+productObject.getPromoprice());
                                productPriceText.setText("$ "+productObject.getPromoprice());
                            }*/

                            /*if (productObject.getPrice()!=null){
                                if (productObject.getPromoprice()!= null){
                                    double saving = Double.parseDouble(productObject.getPrice())-Double.parseDouble(productObject.getPromoprice());
                                    String save = String.format("%.2f", saving);
                                    savings.setText("$ "+save);
                                }
                            }*/

                            if (productObject.getDescription() != null) {
                                productsDetailstxt.setText(productObject.getDescription());
                                if (productObject.getDescription().length() > 80) {
                                    continue_readingTxtlayout.setVisibility(View.VISIBLE);
                                }
                            }
                            if (productObject.getStockCount() != "" || productObject.getStockCount() != null) {
                                stock_count = productObject.getStockCount().toString();
                                numberOfItemLeft.setText(stock_count);
                            } else {
                                numberOfItemLeft.setText("0");
                            }
                            if (productObject.getLike_count() != 0) {
                                String like = String.valueOf(productObject.getLike_count());
                                productLikeCount.setText(like);
                            }

                            if (productObject.isEditor()) {

                                editorLayout.setVisibility(View.VISIBLE);

                            } else {
                                editorLayout.setVisibility(View.GONE);
                            }

                            if (productObject.isPopular()) {

                                popularLayout.setVisibility(View.VISIBLE);

                            } else {

                                popularLayout.setVisibility(View.GONE);
                            }


                            if (productObject.getCollect_at_store() == 1) {
                                estimatedArrivalLayout.setVisibility(View.GONE);
                                shipsFromLayout.setVisibility(View.GONE);
                                collectatstore.setVisibility(View.VISIBLE);
                                if (UtilValidate.isNotNull(productObject.getStore_detalis())) {
                                    if (UtilValidate.isNotNull(productObject.getStore_detalis().getStore())) {
                                        if (UtilValidate.isNotNull(productObject.getStore_detalis().getStore().getStoreName())
                                                && UtilValidate.isNotNull(productObject.getStore_detalis().getStore().getStoreAddress())
                                                && UtilValidate.isNotNull(productObject.getStore_detalis().getStore().getStorePhone())) {

                                            productAvailabiltyTxt.setText(productObject.getStore_detalis().getStore().getStoreName() +
                                                    "\n" + productObject.getStore_detalis().getStore().getStoreAddress() +
                                                    "\n" + productObject.getStore_detalis().getStore().getStorePhone());
                                        }
                                    }
                                }

                            } else {
                                estimatedArrivalLayout.setVisibility(View.VISIBLE);
                                shipsFromLayout.setVisibility(View.VISIBLE);
                                collectatstore.setVisibility(View.GONE);
                                if (UtilValidate.isNotNull(productObject.getAvailability())) {
                                    productAvailabiltyTxt.setText(productObject.getAvailability());
                                }

                            }

                            if (UtilValidate.isNotNull(productObject.getShippingcharge())) {
                                estimatedShipingTxt.setText("$ " + productObject.getShippingcharge());
                            }
                            if (UtilValidate.isNotNull(productObject.getEstimated_arrival())) {
                                estimatedArrivalTxt.setText(productObject.getEstimated_arrival());
                            }


                            /*store*/
                            if (UtilValidate.isNotNull(productObject.getStore_detalis())) {
                                if (UtilValidate.isNotEmpty(String.valueOf(productObject.getStore_detalis()))) {
                                    if (UtilValidate.isNotNull(productObject.getStore_detalis().getStore())) {
                                        if (productObject.getStore_detalis().getStore().getStoreName() != null) {
                                            StoreName.setText(productObject.getStore_detalis().getStore().getStoreName());
                                            productShipsFromStore.setText(productObject.getStore_detalis().getStore().getStoreName());
                                        }
                                        if (productObject.getStore_detalis().getStore().getStoreAddress() != "") {

                                            shipsFromAddress.setText(productObject.getStore_detalis().getStore().getStoreAddress());
                                        }
                                        if (UtilValidate.isNotNull(productObject.getStore_detalis().getStore().getStoreRating())) {
                                            //storeRatingStar.setRating((float) productObject.getStore_detalis().getStore().getStoreRating());
                                        }

                                    }
                                }
                            }

                            if (productDetailsBaseHolder.getData().getProduct().getIs_liked() == 1) {
                                likeGreen.setVisibility(View.VISIBLE);
                                likeGray.setVisibility(View.GONE);

                            } else {
                                likeGreen.setVisibility(View.GONE);
                                likeGray.setVisibility(View.VISIBLE);
                            }


                            likeLinear.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (sessionId != null) {
                                        if (!sessionId.equals("")) {

                                            ProductLikeManager.getInstance().setLike(ProductDetails.this, pdt_id, sessionId, likeCallBack);
                                        }
                                    } else {
                                        loginPopup();
                                    }
                                    MyProfileBaseclass myProfileBaseclass = new MyProfileBaseclass();
                                    //likedProducts.add();
                                    //myProfileBaseclass.getData().setLiked_products(likedProducts);
                                }
                            });


                            if (UtilValidate.isNotNull(productDetailsBaseHolder.getData().getReviews())) {

                                /*int height = LinearLayout.LayoutParams.MATCH_PARENT;*/
                                reviewlist.clear();
                                reviewlist.addAll(productDetailsBaseHolder.getData().getReviews());
                                reviewAdapter = new ReviewAdapter(ProductDetails.this, reviewlist);
                                reviewListview.setAdapter(reviewAdapter);
                                reviewAdapter.notifyDataSetChanged();


                                if (reviewlist.size() == 1) {
                                    int height = Math.round(getResources().getDimension(R.dimen.review_height_1));
                                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    reviewLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                                } else if (reviewlist.size() == 2) {
                                    int height = Math.round(getResources().getDimension(R.dimen.review_height_2));
                                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    reviewLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));

                                } else {
                                    int height = Math.round(getResources().getDimension(R.dimen.review_height_3));
                                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    reviewLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                                }


                                if (reviewlist.size() > 3) {
                                    viewAllReviews.setVisibility(View.VISIBLE);
                                    backViewAllReviews.setVisibility(View.GONE);
                                }
                                numberofReviews.setText("" + reviewListview.getCount());
                                if (reviewListview.getCount() == 0) {
                                    reviewLayout.setVisibility(View.GONE);
                                    viewAllReviews.setVisibility(View.INVISIBLE);
                                } else {
                                    reviewLayout.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        if (UtilValidate.isNotEmpty(productDetailsBaseHolder.getData().getRecommended_products())) {

                            youmaylikeList.clear();
                            youmaylikeList.addAll(productDetailsBaseHolder.getData().getRecommended_products());
                            gridview.setExpanded(true);
                            productAdapter = new ProductAdapter(ProductDetails.this, youmaylikeList, addToCartCallBack);
                            gridview.setAdapter(productAdapter);
                            /*gridview.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    view.getParent().requestDisallowInterceptTouchEvent(true);

                                    return false;
                                }
                            });*/


                            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    Intent productdetail = new Intent(ProductDetails.this, ProductDetails.class);
                                    productdetail.putExtra("productid", youmaylikeList.get(i).getId());
                                    startActivity(productdetail);
                                }
                            });
                        } else {
                            Log.e("EMPTY", "recomented products.......");

                            scroll.setVisibility(View.GONE);
                        }
                    }

                    dialogTransparent.dismiss();

                } else {
                    dialogTransparent.dismiss();
                    Toast.makeText(ProductDetails.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                    Log.e("STATUS ", "ERROR.....");
                }
            }
            reviewListview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
           /* gridview.setFocusable(true);
            gridview.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            gridview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });*/
        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    public class ReviewCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {


            ReviewBaseHolder reviewBaseHolder = (ReviewBaseHolder) result;
            if (reviewBaseHolder.getStatus() == "success") {

                //reviewBaseHolder.getData().setReview();


                if (UtilValidate.isNotNull(productDetailsBaseHolder.getData().getReviews())) {
                    reviewlist.clear();
                    reviewlist.addAll(productDetailsBaseHolder.getData().getReviews());
                    reviewAdapter = new ReviewAdapter(ProductDetails.this, reviewlist);
                    reviewListview.setAdapter(reviewAdapter);
                    reviewAdapter.notifyDataSetChanged();
                    if (reviewlist.size() > 3) {
                        viewAllReviews.setVisibility(View.VISIBLE);
                    } else {
                        viewAllReviews.setVisibility(View.GONE);
                    }
                    numberofReviews.setText("" + reviewListview.getCount());
                    if (reviewListview.getCount() == 0) {
                        reviewLayout.setVisibility(View.GONE);
                        viewAllReviews.setVisibility(View.INVISIBLE);
                    } else {
                        reviewLayout.setVisibility(View.VISIBLE);
                    }
                }

                ProductDetailsManager.getInstance().getProductDetails(ProductDetails.this, pdt_id, sessionId, productCallBack);


            } else {
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }


    private String calculate_price() {

        double float_price = Double.parseDouble(productObject.getPrice());
        double float_discount = Double.parseDouble(productObject.getDiscount());
        double MRP_discount_price = ((float_price * float_discount) / 100);
        double float_offer_price = float_price - MRP_discount_price;
        offer_price = String.valueOf(float_offer_price);
        return offer_price;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buyicon, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.action_search:

                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    private class LikeCallBack implements AsyncTaskCallBack {

        private CallbackManager callbackManager;
        private ShareDialog shareDialog;
        private String name;

        @Override
        public void onFinish(int responseCode, Object result) {


            ProductLikeHolder productLikeHolder = (ProductLikeHolder) result;

            if (productLikeHolder.getStatus().equals("success")) {

                if (productLikeHolder.getCode() == 1) {

                    int like = productObject.getLike_count() + 1;
                    productObject.setLike_count(like);
                    productObject.setIs_liked(1);
                    productLikeCount.setText(String.valueOf(like));
                    likeGreen.setVisibility(View.VISIBLE);
                    likeGray.setVisibility(View.GONE);

                    userObjectHolder = DbManager.getInstance().getCurrentUserDetails();
                    name = userObjectHolder.getFirstName() + " " + userObjectHolder.getLastName();

                    if (!userObjectHolder.getFacebookId().equals("")) {
                        callbackManager = CallbackManager.Factory.create();
                        shareDialog = new ShareDialog(ProductDetails.this);
                        if (ShareDialog.canShow(ShareLinkContent.class)) {

                            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                    .setContentTitle(" luf luf")
                                    .setImageUrl(Uri.parse(productObject.getImage()))
                                    .setContentDescription(
                                            name + " has liked " + productObject.getName() + " on luf luf")
                                    .setContentUrl(null)
                                    .build();

                            shareDialog.show(linkContent);
                        }
                    } else {
                        Log.e("**", "Not logged with fb");
                    }

                }

                if (productLikeHolder.getCode() == 0) {

                    int like = productObject.getLike_count() - 1;
                    productObject.setLike_count(like);
                    productObject.setIs_liked(0);
                    productLikeCount.setText(String.valueOf(like));
                    likeGreen.setVisibility(View.GONE);
                    likeGray.setVisibility(View.VISIBLE);
                    productLikeCount.setText(String.valueOf(like));

                }


            } else {
                Log.e("status", "error");
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    /*private class UnlikeCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {
            ProductLikeHolder productLikeHolder = (ProductLikeHolder) result;

            int like = Integer.parseInt(productLikeCount.getText().toString());
            int unlike = like - 1;
            Log.e("like>>>", "like>>>" + unlike);
            productLikeCount.setText(String.valueOf(unlike));


        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }*/

    private class SetRatingCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {

            StoreRatingBaseHolder storeRatingBaseHolder = (StoreRatingBaseHolder) result;
            if (storeRatingBaseHolder.getStatus().equals("success")) {
                Toast.makeText(ProductDetails.this, storeRatingBaseHolder.getMessage(), Toast.LENGTH_LONG).show();

            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }


    private class AddToCartCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {

            Log.e("@@", "on finish success");

            ShoppingCartResponseHolder addToCart = (ShoppingCartResponseHolder) result;
            if (addToCart.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(ProductDetails.this, addToCart.getMessage().toString(), Toast.LENGTH_LONG).show();
                Log.e("", "" + addToCart.getMessage());
            } else if (addToCart.getStatus().equalsIgnoreCase("error")) {
                Toast.makeText(ProductDetails.this, addToCart.getMessage().toString(), Toast.LENGTH_LONG).show();
                Log.e("", "" + addToCart.getMessage());
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

            Log.e("add to cart", "failed");

        }
    }


    public void wheelviewSelectionPopup(final Activity context,
                                        final String[] meetingid, final TextView mAllMeting) {

        // TODO Auto-generated method stub

        LayoutInflater layoutInflater = (LayoutInflater) ProductDetails.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.wheel_view_popup, null);
        mPopup = new PopupWindow(layout,
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

        mMeeting = meetingid;
        mPopup.update();

        wheelView = (WheelView) layout.findViewById(R.id.wheel_view);
        closePopup = (ImageView) layout.findViewById(R.id.close_popup);
        donePopup = (TextView) layout.findViewById(R.id.done_popup);
        initWheelkm(R.id.wheel_view, meetingid, wheelView);

        mPopup.showAtLocation(wheelView, Gravity.CENTER, 0, 0);

        donePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopup.dismiss();
                // mIntialMeeting=mMeetingId()[mSpinner.getCurrentItem()];
                mIntialMeeting = meetingid[wheelView.getCurrentItem()];

                mAllMeting.setText(mIntialMeeting);
                //doneListener();
            }
        });

        closePopup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mPopup.dismiss();
                // mIntialMeeting=mMeetingId()[mSpinner.getCurrentItem()];
                mIntialMeeting = meetingid[wheelView.getCurrentItem()];

                mAllMeting.setText(mIntialMeeting);
                //doneListener();
            }
        });


    }


    private void initWheelkm(int id, String meetingid[], View view) {

        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(
                ProductDetails.this, meetingid);
        WheelView wheel = getWheelkm(id, view);
        wheel.setViewAdapter(adapter);
        wheel.setCurrentItem(0);
        wheel.setVisibleItems(5);
        wheel.addChangingListenerkm(changedListenerkm);
        wheel.addScrollingListenerkm(scrolledListenerkm);
        wheel.setCyclic(false);
        wheel.setEnabled(true);
    }

    private WheelView getWheelkm(int id, View view) {
        return (WheelView) view.findViewById(id);
    }

    private void mixWheelkm(int id, View view) {
        WheelView wheel = getWheel(id, view);
        wheel.scroll(-350 + (int) (Math.random() * 50), 2000);

    }

    private WheelView getWheel(int id, View view) {
        return (WheelView) view.findViewById(id);
    }

    private void mixWheel(int id, View view) {
        WheelView wheel = getWheel(id, view);
        wheel.scroll(-350 + (int) (Math.random() * 50), 2000);

    }

    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!mWheelScrolled) {

            }
        }
    };

    private OnWheelChangedListener changedListenerkm = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!mWheelScrolled) {
                // updateStatus();
            }
        }
    };

    OnWheelScrollListener scrolledListenerkm = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {
            mWheelScrolled = true;
        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            mWheelScrolled = false;
            mIntialMeeting = mMeeting[wheel.getCurrentItem()];

        }

    };

    private void loginPopup() {
        Intent login = new Intent(ProductDetails.this, Loginactivity.class);
        login.putExtra("buy", 1);
        login.putExtra("productId", product_id);
        startActivity(login);
        /*try {

            LayoutInflater inflater = (LayoutInflater) ProductDetails.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View logoutLayout = inflater.inflate(R.layout.logout_popup, null);

            final PopupWindow pwindo = new PopupWindow(logoutLayout, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT, true);

            pwindo.showAtLocation(logoutLayout, Gravity.FILL, 0, 0);

            *//*LOGOUT*//*
            LinearLayout yesLinear = (LinearLayout) logoutLayout.findViewById(R.id.yes_linear);
            TextView popupheading = (TextView) logoutLayout.findViewById(R.id.popupheading);
            popupheading.setText("Login to continue");

            yesLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();
                    Intent login = new Intent(ProductDetails.this, Loginactivity.class);

                    startActivity(login);
                    //finish();

                }
            });

            *//*CANCEL LOGOUT*//*
            LinearLayout cancelLinear = (LinearLayout) logoutLayout.findViewById(R.id.cancel_linear);
            cancelLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
