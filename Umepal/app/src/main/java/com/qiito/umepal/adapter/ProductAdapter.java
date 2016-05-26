package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.activity.Loginactivity;
import com.qiito.umepal.activity.ProductDetails;
import com.qiito.umepal.adapters.ArrayWheelAdapter;
import com.qiito.umepal.fragments.Allitems;
import com.qiito.umepal.holder.CheckoutProductsHolder;
import com.qiito.umepal.holder.ProductLikeHolder;
import com.qiito.umepal.holder.ProductObject;
import com.qiito.umepal.holder.ShoppingCartResponseHolder;
import com.qiito.umepal.holder.UserObjectHolder;
import com.qiito.umepal.listeners.AdapterViewItemListener;
import com.qiito.umepal.listeners.OnWheelChangedListener;
import com.qiito.umepal.listeners.OnWheelScrollListener;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.ProductLikeManager;
import com.qiito.umepal.managers.ShoppingCartManager;
import com.qiito.umepal.timer.RaceTimeCounterTextView;
import com.qiito.umepal.timer.UpdateClockTimer;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.whellview.WheelView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class ProductAdapter extends BaseAdapter {

    private List<ProductObject> productList = new ArrayList<ProductObject>();
    //private LikeCallBack likeCallBackk;
    private UserObjectHolder userObjectHolder;
    private AsyncTaskCallBack likeCallBack;
    private Activity activity;
    private LayoutInflater inflater;
    private ViewHolder viewholder;
    private int likedCount;
    private int code;
    private PopupWindow popupWindow;
    private String product_color;
    private String product_dimension;
    private String product_quantity;
    private ArrayList<String> sizelist = new ArrayList<String>();
    private ArrayList<String> colorlist = new ArrayList<String>();
    private Allitems allitems;
    private String productId;
    private String userId;
    private Handler handler = new Handler();
    private Timer timer;
    private List<UpdateClockTimer> s1 = new ArrayList<UpdateClockTimer>();
    private List<CheckoutProductsHolder> checkoutProductsList = new ArrayList<>();
    private List<String> quant = new ArrayList<>();
    private String sessionId;
    private int newQuantity;
    private boolean newProductFlag;
    private TextView quantity;
    private TextView color;
    private TextView size;
    private String mIntialMeeting = "";
    private Button buy;
    private Button cancel;
    private ImageView closePopup;
    private TextView donePopup;
    private LinearLayout quantityLinear;
    private LinearLayout sizeLinear;
    private LinearLayout colorLinear;
    private ProductObject productObject;
    private AsyncTaskCallBack addToCartCallBack;
    private String mIntialValue = "";
    private boolean mWheelScrolled = false;
    private PopupWindow mPopup;
    private WheelView mSpinner;
    private String[] mMeeting;
    private ImageView close;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private String name;
    private LinearLayout sizeLayout;
    private LinearLayout colorLayout;
    private AdapterViewItemListener productLikeListener;

    public ProductAdapter(Activity activity, List<ProductObject> productList, AsyncTaskCallBack addToCartCallBack) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.productList = productList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addToCartCallBack = addToCartCallBack;
        //this.likeCallBack = likeCallBack;
        //likeCallBackk = new LikeCallBack();
    }
    public ProductAdapter(Activity activity, List<ProductObject> productList, AsyncTaskCallBack addToCartCallBack,
                          AsyncTaskCallBack likeCallBack,AdapterViewItemListener productLikeListener) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.productList = productList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addToCartCallBack = addToCartCallBack;
        this.likeCallBack = likeCallBack;
        this.productLikeListener = productLikeListener;
    }

    public List<UpdateClockTimer> getupdateClockTimerList() {
        return s1;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setmHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            viewholder = new ViewHolder();

            convertView = inflater.inflate(R.layout.product_details_single_grid, null);

            viewholder.popular = (LinearLayout) convertView.findViewById(R.id.popularLayout);
            viewholder.editorsPick = (LinearLayout) convertView.findViewById(R.id.editorsPickId);
            viewholder.discountLinear = (LinearLayout) convertView.findViewById(R.id.discountlinear);
            viewholder.buyLayout = (LinearLayout) convertView.findViewById(R.id.buy_layout);
            viewholder.shareLayout = (LinearLayout) convertView.findViewById(R.id.share_layout);
            viewholder.productImageLayout = (LinearLayout) convertView.findViewById(R.id.product_image_layout);
            viewholder.productDetailLayout = (LinearLayout) convertView.findViewById(R.id.product_detail_layout);
            viewholder.discountlinear_without_deal = (LinearLayout) convertView.findViewById(R.id.discountlinear_without_deal);
            viewholder.likeLayout = (LinearLayout) convertView.findViewById(R.id.likeLayout);
            viewholder.counter = (LinearLayout) convertView.findViewById(R.id.counter);
            viewholder.timerlayout = (LinearLayout) convertView.findViewById(R.id.timerlayout);
            viewholder.availableAtStore = (LinearLayout) convertView.findViewById(R.id.available_at_store_layout);

            viewholder.productTitle = (TextView) convertView.findViewById(R.id.name);
            viewholder.productPrice = (TextView) convertView.findViewById(R.id.dollar_price);
            viewholder.discount = (TextView) convertView.findViewById(R.id.discount);
            viewholder.discountwithoutdeal = (TextView) convertView.findViewById(R.id.discountwithoutdeal);
            viewholder.likeCount = (TextView) convertView.findViewById(R.id.like_count);
            viewholder.onlyLeft = (TextView) convertView.findViewById(R.id.only_left_number);
            viewholder.crossed_price = (TextView) convertView.findViewById(R.id.crossed_dollar_price);
            viewholder.offer_time_left_minute_1 = (TextView) convertView.findViewById(R.id.time_num1);
            viewholder.offer_time_left_minute_2 = (TextView) convertView.findViewById(R.id.time_num2);
            viewholder.offer_time_left_second_1 = (TextView) convertView.findViewById(R.id.time_num3);
            viewholder.offer_time_left_second_2 = (TextView) convertView.findViewById(R.id.time_num4);
            viewholder.hourlydeal = (TextView) convertView.findViewById(R.id.hourlydeal);

            viewholder.membertxt = (TextView) convertView.findViewById(R.id.membertxt);
            //viewholder.offerTime=(TextView)convertView.findViewById(R.id.time_num1);
            viewholder.crosseddollar = (RelativeLayout) convertView.findViewById(R.id.crosseddollar);

            viewholder.productGridImage = (ImageView) convertView.findViewById(R.id.image);
            viewholder.likegreenImg = (ImageView) convertView.findViewById(R.id.likegreenImg);
            viewholder.likegreyImg = (ImageView) convertView.findViewById(R.id.likegreyImg);
            viewholder.imageview_yellow_head = (ImageView) convertView.findViewById(R.id.imageview_yellow_head);
            viewholder.crossed_dollar_nonmember = (TextView) convertView.findViewById(R.id.crossed_dollar_nonmember);
            viewholder.member_layout = (LinearLayout) convertView.findViewById(R.id.member_layout);

            viewholder.green_membertxt = (TextView) convertView.findViewById(R.id.green_membertxt);
            viewholder.crossednonmember = (RelativeLayout) convertView.findViewById(R.id.crossednonmember);
            viewholder.memberPrice = (TextView) convertView.findViewById(R.id.member_price);
            viewholder.raceTimeCounterTextView = (RaceTimeCounterTextView) convertView.findViewById(R.id.timer);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        sessionId = DbManager.getInstance().getSessionId();
        /*converting to string*/
        String only_left = String.valueOf(productList.get(position).getStockCount());
        //final String liked = String.valueOf(productList.get(position).getLike_count());
        String discount = String.valueOf(productList.get(position).getDiscount());

        productObject = new ProductObject();

        productObject = productList.get(position);

        //likeCallBack = new LikeCallBack();


        /*member or not*/
        if (sessionId != null) {
            if (!sessionId.equals("")) {
                if (UtilValidate.isNotNull(DbManager.getInstance().getCurrentUserDetails())) {
                    if ((DbManager.getInstance().getCurrentUserDetails().is_member())) {
                        viewholder.crossed_dollar_nonmember.setVisibility(View.GONE);
                        viewholder.crossednonmember.setVisibility(View.GONE);

                            /* member price*/
                        if (UtilValidate.isNotNull(productList.get(position).getDiscountprice())) {

                            viewholder.memberPrice.setText("$" + productList.get(position).getDiscountprice());
                        } else {
                            viewholder.memberPrice.setText("0");
                        }
                            /*set price*/
                        if (UtilValidate.isNotNull(productList.get(position).getPromoprice())) {
                            viewholder.productPrice.setText("$" + productList.get(position).getPromoprice());
                        } else {
                            viewholder.productPrice.setText("0");
                        }

                    } else {
                        viewholder.membertxt.setVisibility(View.VISIBLE);
                        viewholder.crosseddollar.setVisibility(View.GONE);
                        viewholder.green_membertxt.setVisibility(View.GONE);
                                /* member price*/
                        if (UtilValidate.isNotNull(productList.get(position).getDiscountprice())) {
                            viewholder.memberPrice.setText("$" + productList.get(position).getPromoprice());
                        } else {
                            viewholder.memberPrice.setText("0");
                        }
                        if (UtilValidate.isNotNull(productList.get(position).getPromoprice())) {
                            viewholder.productPrice.setText("$" + productList.get(position).getDiscountprice());
                        } else {
                            viewholder.productPrice.setText("0");
                        }
                       /* if (DbManager.getInstance().getCurrentUserDetails().getMembership_blocked().equalsIgnoreCase("true")) {
                            viewholder.member_layout.setVisibility(View.GONE);
                        } else {
                            viewholder.member_layout.setVisibility(View.VISIBLE);

                        }*/
                    }
                } else {
                    Toast.makeText(activity, "null", Toast.LENGTH_LONG).show();
                }
            }
        } else {

            viewholder.member_layout.setVisibility(View.GONE);
            viewholder.membertxt.setVisibility(View.VISIBLE);
            viewholder.crosseddollar.setVisibility(View.GONE);
            viewholder.green_membertxt.setVisibility(View.GONE);
                                /* member price*/
            if (UtilValidate.isNotNull(productList.get(position).getDiscountprice())) {
                viewholder.memberPrice.setText("$" + productList.get(position).getPromoprice());
            } else {
                viewholder.memberPrice.setText("0");
            }
            if (UtilValidate.isNotNull(productList.get(position).getPromoprice())) {
                viewholder.productPrice.setText("$" + productList.get(position).getDiscountprice());
            } else {
                viewholder.productPrice.setText("0");
            }
        }

		/*set image*/
        if (UtilValidate.isNotNull(productList.get(position).getImage()) && UtilValidate.isNotEmpty(productList.get(position).getImage())) {
            if (!productList.get(position).getImage().equals("")) {
                Picasso.with(activity)
                        .load(productList.get(position).getImage()).fit()
                        .placeholder(R.drawable.logo_splash).fit()
                        .error(R.drawable.logo_splash).fit()
                        .into(viewholder.productGridImage);

            } else {
                Picasso.with(activity).load(R.drawable.logo_splash).into(viewholder.productGridImage);
            }
        } else {
            Picasso.with(activity).load(R.drawable.logo_splash).into(viewholder.productGridImage);
        }

		/*set name*/
        if (UtilValidate.isNotNull(productList.get(position).getName())) {
            viewholder.productTitle.setText(productList.get(position).getName());
        } else {
            viewholder.productTitle.setText("");
        }

        if (UtilValidate.isNotNull(productList.get(position).getPrice())) {
            viewholder.crossed_price.setText(productList.get(position).getPrice());
            viewholder.crossed_dollar_nonmember.setText(productList.get(position).getPrice());
        } else {

        }

        if (productList.get(position).getCollect_at_store() == 1) {
            viewholder.availableAtStore.setVisibility(View.VISIBLE);
        } else if (productList.get(position).getCollect_at_store() == 0) {
            viewholder.availableAtStore.setVisibility(View.INVISIBLE);
        }


        /*set like image*/
        if (productList.get(position).getIs_liked() == 1) {
            viewholder.likegreenImg.setVisibility(View.VISIBLE);
            viewholder.likegreyImg.setVisibility(View.GONE);
        }
        if (productList.get(position).getIs_liked() == 0) {
            viewholder.likegreenImg.setVisibility(View.GONE);
            viewholder.likegreyImg.setVisibility(View.VISIBLE);
        }

        /*setting popular and editor*/
        if (productList.get(position).isPopular()) {
            viewholder.popular.setVisibility(View.VISIBLE);

        } else {
            viewholder.popular.setVisibility(View.INVISIBLE);
        }

        if (productList.get(position).isEditor()) {
            viewholder.editorsPick.setVisibility(View.VISIBLE);
        } else {
            viewholder.editorsPick.setVisibility(View.INVISIBLE);
        }

        /*TIMER*/

        if (productList.get(position).getOfferDate()!= null && !productList.get(position).getOfferDate().equalsIgnoreCase("")) {
            viewholder.counter.setVisibility(View.VISIBLE);
            viewholder.hourlydeal.setVisibility(View.VISIBLE);
            viewholder.timerlayout.setVisibility(View.VISIBLE);
            viewholder.discountlinear_without_deal.setVisibility(View.GONE);
            viewholder.discountwithoutdeal.setVisibility(View.GONE);
            viewholder.imageview_yellow_head.setVisibility(View.VISIBLE);
            viewholder.discountLinear.setVisibility(View.VISIBLE);
            viewholder.raceTimeCounterTextView.setRaceStartTime(productList.get(position).getOfferDate());
        }else{
            viewholder.counter.setVisibility(View.GONE);
            viewholder.hourlydeal.setVisibility(View.GONE);
            viewholder.timerlayout.setVisibility(View.GONE);
            viewholder.discountlinear_without_deal.setVisibility(View.VISIBLE);
            viewholder.discountwithoutdeal.setVisibility(View.VISIBLE);
            viewholder.imageview_yellow_head.setVisibility(View.GONE);
            viewholder.discountLinear.setVisibility(View.GONE);

        }

		/*set num of item left*/

        if (UtilValidate.isEmpty(only_left)) {
        } else {
            viewholder.onlyLeft.setText(only_left);
        }

		/*set num of likes*/

        if (productList.get(position).getLike_count() >=0) {

            // int like = TodaysParentApp.getLike_count();
            viewholder.likeCount.setText("" + productList.get(position).getLike_count());

            // viewholder.likeCount.setText("" + like);
            TodaysParentApp.setLike_count(productList.get(position).getLike_count());
        }


		/*set discount*/
        if (UtilValidate.isEmpty(discount)) {

        } else {
            if (discount != "") {

                float d = Float.parseFloat(discount);
                Math.round(d);
                String dd = String.valueOf(Math.round(d));
                viewholder.discount.setText(dd);
                viewholder.discountwithoutdeal.setText(dd);

            }
        }

        viewholder.productDetailLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent productdetail = new Intent(v.getContext(), ProductDetails.class);
                productdetail.putExtra("productid", productList.get(position).getId());
                v.getContext().startActivity(productdetail);

            }
        });

        viewholder.productImageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent productdetail = new Intent(v.getContext(), ProductDetails.class);
                productdetail.putExtra("productid", productList.get(position).getId());
                v.getContext().startActivity(productdetail);
            }
        });


        viewholder.buyLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionId != null) {
                    if (!sessionId.equals("")) {
                        if (productList != null) {

                            if (productList.get(position).getId() != 0) {
                                productId = String.valueOf(productList.get(position).getId());
                            }
                            if(popupWindow!=null){
                                popupWindow.dismiss();
                            }

                            LinearLayout viewGroup = (LinearLayout) activity.findViewById(R.id.quantity_popup_layout);
                            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View layout = inflater.inflate(R.layout.quantity_popup, null);

                            popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
                            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
                            quantityLinear = (LinearLayout) layout.findViewById(R.id.quantityLinear);
                            sizeLinear = (LinearLayout) layout.findViewById(R.id.sizeLinear);
                            colorLinear = (LinearLayout) layout.findViewById(R.id.colorLinear);
                            quantity = (TextView) layout.findViewById(R.id.quantity_spinner);
                            buy = (Button) layout.findViewById(R.id.buy_quantity_popup);
                            color = (TextView) layout.findViewById(R.id.color_spinner);
                            size = (TextView) layout.findViewById(R.id.size_spinner);
                            cancel = (Button) layout.findViewById(R.id.cancel_quantity_popup);
                            sizeLayout = (LinearLayout) layout.findViewById(R.id.size_layout);
                            colorLayout = (LinearLayout) layout.findViewById(R.id.color_layout);

                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();
                                }
                            });
                            if(productList.get(position).getStockCount()!=null||productList.get(position).getStockCount().equalsIgnoreCase("")) {

                                if (!productList.get(position).getStockCount().equalsIgnoreCase("0")) {
                                    int i = Integer.parseInt(productList.get(position).getStockCount());
                                    final String[] quantity_array = new String[i];
                                    for (int j = 0; j < i; j++) {
                                        quantity_array[j] = String.valueOf(j + 1);
                                    }
                                    quantity.setText(quantity_array[0]);
                                    quantityLinear.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            wheelviewSelectionPopup(activity, quantity_array, quantity);
                                        }
                                    });
                                }
                            }
                            if (UtilValidate.isNotNull(productList.get(position).getColor())) {
                                if (UtilValidate.isNotEmpty(productList.get(position).getColor())) {

                                    colorLayout.setVisibility(View.VISIBLE);

                                    final String[] color_array = new String[productList.get(position).getColor().size()];
                                    colorlist = new ArrayList<>();

                                    for (int c = 0; c < productList.get(position).getColor().size(); c++) {
                                        colorlist.add(String.valueOf(productList.get(position).getColor().get(c)));
                                        color_array[c] = colorlist.get(c);
                                    }
                                    color.setText(color_array[0]);
                                    colorLinear.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            wheelviewSelectionPopup(activity, color_array, color);
                                        }
                                    });
                                } else {
                                    colorLayout.setVisibility(View.GONE);
                                }
                            }
                            if (UtilValidate.isNotEmpty(productList.get(position).getDimension())) {
                                if (UtilValidate.isNotEmpty(productList.get(position).getDimension())) {

                                    sizeLayout.setVisibility(View.VISIBLE);
                                    final String[] dimension_array = new String[productList.get(position).getDimension().size()];
                                    sizelist = new ArrayList<>();
                                    for (int c = 0; c < productList.get(position).getDimension().size(); c++) {
                                        sizelist.add(String.valueOf(productList.get(position).getDimension().get(c)));
                                        dimension_array[c] = sizelist.get(c);
                                    }
                                    size.setText(dimension_array[0]);
                                    sizeLinear.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            wheelviewSelectionPopup(activity, dimension_array, size);
                                        }
                                    });
                                } else {
                                    sizeLayout.setVisibility(View.GONE);
                                }
                            }
                            buy.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    popupWindow.dismiss();
                                    product_quantity = quantity.getText().toString();
                                    product_color = color.getText().toString();
                                    product_dimension = size.getText().toString();
                                    userObjectHolder = DbManager.getInstance().getCurrentUserDetails();
                                    userId = String.valueOf(userObjectHolder.getId());
                                    ShoppingCartManager.getInstance().addToCart(activity, addToCartCallBack,
                                            userId, productId, product_quantity, product_color, product_dimension, sessionId);
                                }
                            });
                        }
                    }
                } else {
                    loginPopup();
                }

            }
        });
        viewholder.shareLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, productList.get(position).getName() + " \n " + productList.get(position).getImage());
                sendIntent.setType("text/plain");
                view.getContext().startActivity(sendIntent);
            }
        });

        sessionId = DbManager.getInstance().getSessionId();

        viewholder.likeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                allitems = new Allitems();
                productId = String.valueOf(productList.get(position).getId());

                if (sessionId != null) {
                    if (!sessionId.equals("")) {
                        if (productList != null) {

                            userObjectHolder = DbManager.getInstance().getCurrentUserDetails();
                            name = userObjectHolder.getFirstName() + " " + userObjectHolder.getLastName();
                            if (!userObjectHolder.getFacebookId().equals("")) {
                                callbackManager = CallbackManager.Factory.create();
                                shareDialog = new ShareDialog(activity);
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
                            }
                            //ProductLikeManager.getInstance().setLike(activity, productId, sessionId, likeCallBackk);

                            Log.e("name",""+productList.get(position).getName());
                            //allitems.likeProduct(productList.get(position), position);
                            /*if (productList.get(position).getIs_liked() == 1) {
                                viewholder.likegreenImg.setVisibility(View.VISIBLE);
                                viewholder.likegreyImg.setVisibility(View.GONE);
                            } else {
                                viewholder.likegreenImg.setVisibility(View.GONE);
                                viewholder.likegreyImg.setVisibility(View.VISIBLE);
                            }*/

                            Log.e("name", "" + productList.get(position).getName());
                            //allitems.likeProduct(productList.get(position), position);
                            //productLikeListener.likeProduct(productList.get(position), position);
                            LikeCallBack callBack = new LikeCallBack(position);
                            ProductLikeManager.getInstance().setLike(activity, String.valueOf(productList.get(position).getId()), DbManager.getInstance().getSessionId(), callBack);


                        }
                    }
                } else {
                    loginPopup();
                }
                //notifyDataSetChanged();

            }
        });

        return convertView;
    }


    public void wheelviewSelectionPopup(final Activity context,
                                        final String[] meetingid, final TextView mAllMeting) {

        // TODO Auto-generated method stub

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.wheel_view_popup, null);
        mPopup = new PopupWindow(layout,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopup.showAtLocation(layout, Gravity.CENTER, 0, 0);
        mMeeting = meetingid;
        mPopup.update();
        close = (ImageView) layout.findViewById(R.id.close_popup);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopup.dismiss();
            }
        });
        mSpinner = (WheelView) layout.findViewById(R.id.wheel_view);
        initWheelkm(R.id.wheel_view, meetingid, mSpinner, mAllMeting);
        TextView mDonetext = (TextView) layout.findViewById(R.id.done_popup);

        mIntialValue = meetingid[mSpinner.getCurrentItem()];
        mAllMeting.setText(mIntialValue);
        mSpinner.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                mPopup.dismiss();
                mIntialValue = meetingid[mSpinner.getCurrentItem()];
                mAllMeting.setText(mIntialValue);
            }
        });

        mDonetext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopup.dismiss();
                mIntialValue = meetingid[mSpinner.getCurrentItem()];
                mAllMeting.setText(mIntialValue);
            }
        });

    }


    private void initWheelkm(int id, String meetingid[], View view, final TextView textview) {

        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(
                activity, meetingid);
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

    private class AddToCartCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {
            Log.e("@@", "on finish success");
            ShoppingCartResponseHolder addToCart = (ShoppingCartResponseHolder) result;
            if (addToCart.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(activity, addToCart.getMessage().toString(), Toast.LENGTH_LONG).show();
                Log.e("", "" + addToCart.getMessage());
            } else if (addToCart.getStatus().equalsIgnoreCase("error")) {
                Toast.makeText(activity, addToCart.getMessage().toString(), Toast.LENGTH_LONG).show();
                Log.e("", "" + addToCart.getMessage());
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {
            Log.e("add to cart", "failed");

        }
    }


    public class ViewHolder {

        private LinearLayout popular;
        private LinearLayout editorsPick;
        private LinearLayout discountLinear;
        private LinearLayout buyLayout;
        private LinearLayout shareLayout;
        private LinearLayout productImageLayout;
        private LinearLayout productDetailLayout;
        private LinearLayout likeLayout;
        private LinearLayout discountlinear_without_deal;
        private LinearLayout counter;
        private LinearLayout availableAtStore;
        private LinearLayout member_layout;
        private TextView productTitle;
        private TextView productPrice;
        private TextView populartext;
        private TextView editorsPickText;
        private TextView discount;
        private TextView discountwithoutdeal;
        private TextView likeCount;
        private TextView onlyLeft;
        private TextView crossed_price;
        private TextView offer_time_left_minute_1;
        private TextView offer_time_left_minute_2;
        private TextView offer_time_left_second_1;
        private TextView offer_time_left_second_2;
        private ImageView productGridImage;
        private ImageView productIcon;
        private ImageView popularImage;
        private ImageView editorsPickImage;
        private ImageView productGridIcon;
        private ImageView likegreenImg;
        private ImageView likegreyImg;
        private ImageView imageview_yellow_head;
        private TextView hourlydeal;
        private LinearLayout timerlayout;
        private TextView green_membertxt;
        private TextView memberPrice;
        private RelativeLayout crossednonmember;
        private TextView offerTime;
        private TextView crossed_dollar_nonmember;
        private TextView membertxt;
        private RelativeLayout crosseddollar;
        private RaceTimeCounterTextView raceTimeCounterTextView;

    }

    private void loginPopup() {
        Intent login = new Intent(activity, Loginactivity.class);
        activity.startActivity(login);
    }

    private class LikeCallBack implements AsyncTaskCallBack {

        int count;
        int Position;

        public LikeCallBack(int position) {
            Position = position;
        }

        @Override
        public void onFinish(int responseCode, Object result) {


            ProductLikeHolder productLikeHolder = (ProductLikeHolder) result;
            //dialogTransparent.dismiss();

            if (productLikeHolder.getStatus().equals("success")) {

                if (UtilValidate.isNotNull(productLikeHolder.getCode())) {
                    if (productLikeHolder.getCode() == 1) {
                        Log.e("@@", "product liked!!!!!");
                        Log.e("@@", "like position" + Position);

                        count =productList.get(Position).getLike_count()+1;

                    }
                    if (productLikeHolder.getCode() == 0) {
                        Log.e("@@", "product unliked!!!!!");
                        Log.e("@@", "unlike position" + Position);
                        count = productList.get(Position).getLike_count()-1;

                    }
                    productList.get(Position).setIs_liked(productLikeHolder.getCode());
                    productList.get(Position).setLike_count(count);
                    Log.e("count >>", "new count>>>" + count);
                    Log.e("LIKEE","LIKE: "+productList.get(Position).getLike_count());
                    ProductAdapter.this.notifyDataSetChanged();
                } else {

                }


            } else {
                Log.e("like status", "error");
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {
            //dialogTransparent.dismiss();

        }
    }


}