package com.qiito.umepal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.adapter.ProductAdapter;
import com.qiito.umepal.holder.ProductBaseHolder;
import com.qiito.umepal.holder.ProductObject;
import com.qiito.umepal.holder.ProductSubListHolder;
import com.qiito.umepal.holder.ShoppingCartResponseHolder;
import com.qiito.umepal.holder.StoreData;
import com.qiito.umepal.holder.StoreDetailsHolder;
import com.qiito.umepal.holder.StoreRating;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.VisitStoreManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.qiito.umepal.Utilvalidate.NetChecker.isConnected;

/**
 * Created by shiya on 29/9/15.
 */
public class VisitStoreActivity extends Activity {

    private VisitStoreCallBack visitStoreCallBack;
    private ProductSubListHolder productSubListHolder;
    private StoreDetailsHolder storeDetailsHolder;
    private StoreData storeData;
    private String sessionId;
    private View progressview;
    private int offset = 0;
    private AlertDialog.Builder builder;
    private AlertDialog alert;
    private List<ProductObject> productsList = new ArrayList<>();
    private List<StoreRating> storeRatingList = new ArrayList<>();
    private Dialog dialogTransparent;
    private ProductAdapter productAdapter;
    private GridView gridView;
    private ImageView backArrow;
    private TextView pageHeading;
    private TextView productCount;
    private TextView storeName;
    private TextView rateCount;
    private TextView viewRatings;
    private ImageView storeImage;
    private RatingBar storeRate;
    private static int count;
    private int storeid;
    private String storeimage;
    private String ratings;
    private AddToCartCallBack addToCartCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_store_layout);
        initViews();
        initManagers();
        setVisibilities();
        pageHeading.setText("Store Details");
        sessionId = DbManager.getInstance().getSessionId();
        Bundle bundle = getIntent().getExtras();
        storeid = bundle.getInt("storeid");
        Log.e("","store id>>>>"+storeid);


        //Log.e("","product size>>>"+productSubListHolder.getProducts().size());
        /*if (productSubListHolder.getProducts().size()!= 0) {
            Log.e("","product size>>>"+productSubListHolder.getProducts().size());
            productCount.setText(productSubListHolder.getProducts().size());
        }else{
            productCount.setText("00");
        }*/
        dialogTransparent = new Dialog(this, android.R.style.Theme_Black);
        progressview = LayoutInflater.from(this).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);

        dialogTransparent.show();

        VisitStoreManager.getInstance().getStoreDetails(VisitStoreActivity.this, visitStoreCallBack, sessionId, offset,storeid);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rateintent = new Intent(VisitStoreActivity.this, StoreRatingActivity.class);
                rateintent.putExtra("id", storeData.getId());
                startActivity(rateintent);
            }
        });
    }

    private void initViews(){
        gridView = (GridView)findViewById(R.id.store_products);
        backArrow = (ImageView)findViewById(R.id.back_menu_icon);
        pageHeading = (TextView)findViewById(R.id.page_heading);
        productCount = (TextView)findViewById(R.id.product_count);
        storeName = (TextView)findViewById(R.id.store_name_layout);
        storeImage = (ImageView)findViewById(R.id.store_image);
        storeRate = (RatingBar)findViewById(R.id.store_rating);
        rateCount = (TextView)findViewById(R.id.rating_count);
        viewRatings = (TextView)findViewById(R.id.store_view_all_ratings);

    }
    private void initManagers(){
        visitStoreCallBack = new VisitStoreCallBack();
        productSubListHolder = new ProductSubListHolder();
        storeDetailsHolder = new StoreDetailsHolder();
        storeData = new StoreData();
        addToCartCallBack = new AddToCartCallBack();
    }

    private void  setVisibilities(){
        pageHeading.setVisibility(View.VISIBLE);
    }

    private class VisitStoreCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {
            ProductBaseHolder productBaseHolder = (ProductBaseHolder)result;

            if (productBaseHolder.getStatus().equals("success")){

                if(productBaseHolder.getData()!=null)
                {
                    productSubListHolder = productBaseHolder.getData();
                    if(UtilValidate.isNotEmpty(productSubListHolder.getProducts()))
                    {
                        productsList.clear();


                        productsList.addAll(productSubListHolder.getProducts());
                        dialogTransparent.dismiss();
                        count = productSubListHolder.getProducts().size();

                        Log.e("", "size>>>" + count);

                        if (UtilValidate.isNotNull(productSubListHolder.getProducts().get(count-1).getStore_detalis())) {

                            storeDetailsHolder = productSubListHolder.getProducts().get(count-1).getStore_detalis();

                            if (UtilValidate.isNotNull(storeDetailsHolder.getStore())) {
                                storeData = storeDetailsHolder.getStore();
                                if (UtilValidate.isNotNull(storeData.getStoreName())) {
                                    storeName.setText(storeData.getStoreName());

                                    Log.e("", "name>>>"+storeData.getStoreName());
                                }else{
                                    Log.e("","null1");
                                }

                                if (storeData.getStoreImage()!="") {

                                    Picasso.with(VisitStoreActivity.this)
                                            .load(storeData.getStoreImage())
                                            .placeholder(R.drawable.logo_splash)
                                            .error(R.drawable.logo_splash)
                                            .into(storeImage);

                                    Log.e("","image>>"+storeData.getStoreImage());
                                }else{
                                    Picasso.with(VisitStoreActivity.this)
                                           .load(R.drawable.logo_splash)
                                           .into(storeImage);
                                    Log.e("", "no image");
                                }
                                if (UtilValidate.isNotNull(productSubListHolder.getProducts().get(count-1).getStore_detalis().getStore().getStoreRating())) {

                                    ratings = String.valueOf(storeData.getStoreRating());
                                    Log.e("","rating>>"+storeData.getStoreRating());
                                    storeRate.setRating(Float.parseFloat(ratings));



                                }else{
                                    Log.e("", "null3");
                                }
                            }else{
                                Log.e("","null4");
                            }

                            if (UtilValidate.isNotNull(productSubListHolder.getProducts().get(count-1).getStore_detalis().getRatings())) {

                                Log.e("", "sizeeee>>>>>>>" + storeDetailsHolder.getRatings().size());

                                rateCount.setText(""+storeDetailsHolder.getRatings().size());
                            }else{
                                Log.e("", "null5");
                            }
                        }else{
                            Log.e("","null6");
                        }

                        //productCount.setText(count);
                        productAdapter = new ProductAdapter(VisitStoreActivity.this, productsList, addToCartCallBack);
                        gridView.setAdapter(productAdapter);
                        productAdapter.notifyDataSetChanged();
                        productCount.setText(""+count);

                    }else{
                        Log.e("","null7");
                    }
                }else{
                    Log.e("","null8");
                }

            }
            else{
                Toast.makeText(VisitStoreActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                dialogTransparent.dismiss();
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {
            builder = new AlertDialog.Builder(VisitStoreActivity.this);
            builder.setMessage("Network Unavailable!!");
            builder.setCancelable(true);
            builder.setPositiveButton("Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialogTransparent.show();
                            dialog.cancel();
                            startActivity(getIntent());
                            if (isConnected(VisitStoreActivity.this)) {
                                {
                                    finish();
                                    startActivity(getIntent());
                                }
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                            }
                        }
                    });
            builder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // dialogTransparent.show();
                            dialog.cancel();
                        }
                    });

            alert = builder.create();
            alert.show();
        }
    }

    private class AddToCartCallBack implements AsyncTaskCallBack{
        @Override
        public void onFinish(int responseCode, Object result) {

            Log.e("","on finish success");

            ShoppingCartResponseHolder addToCart = (ShoppingCartResponseHolder) result;
            if (addToCart.getStatus().equalsIgnoreCase("success")){
                Toast.makeText(VisitStoreActivity.this,addToCart.getMessage().toString(),Toast.LENGTH_LONG).show();
                Log.e("", "" + addToCart.getMessage());
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

            Log.e("", "failed");

        }
    }
}
