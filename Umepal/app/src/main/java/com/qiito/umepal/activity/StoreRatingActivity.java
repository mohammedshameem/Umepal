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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.adapter.StoreRatingAdapter;
import com.qiito.umepal.holder.StoreData;
import com.qiito.umepal.holder.StoreDetailBaseHolder;
import com.qiito.umepal.holder.StoreRating;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.StoreRatingDetailManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.qiito.umepal.Utilvalidate.NetChecker.isConnected;

/**
 * Created by shiya on 8/10/15.
 */
public class StoreRatingActivity extends Activity {

    private static final String TAG = StoreRatingActivity.class.getSimpleName();

    private StoreRatingDetailCallBack storeRatingDetailCallBack;
    private StoreRatingAdapter storeRatingAdapter;
    private StoreData storeData;

    private List<StoreRating> store_ratings = new ArrayList<>();

    private RatingBar storeRating;
    private Dialog dialogTransparent;
    private View progressview;
    private AlertDialog.Builder builder;
    private AlertDialog alert;

    private ListView ratingList;

    private TextView storeName;
    private TextView storeAddress;
    private TextView ratingCount;
    private TextView pageHeading;
    private TextView visitStore;

    private ImageView backicon;
    private ImageView storeImage;

    private String sessionId;
    private int storeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_rating_layout);
        initViews();
        initManagers();
        pageHeading.setVisibility(View.VISIBLE);
        pageHeading.setText("Store Rating");
        dialogTransparent = new Dialog(this, android.R.style.Theme_Black);
        progressview = LayoutInflater.from(this).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);

        dialogTransparent.show();
        sessionId = DbManager.getInstance().getSessionId();
        Bundle bundle = getIntent().getExtras();
        storeid = bundle.getInt("id");
        Log.e(TAG,"id>>>>>>>>>>>"+storeid);
        StoreRatingDetailManager.getInstance().getRatingDetail(StoreRatingActivity.this, storeRatingDetailCallBack, sessionId, storeid);

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        visitStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent storeIntent = new Intent(StoreRatingActivity.this, VisitStoreActivity.class);
                storeIntent.putExtra("storeid", storeData.getId());
                startActivity(storeIntent);
            }
        });
    }

    private void initViews(){

        storeName = (TextView)findViewById(R.id.rating_store_name);
        storeAddress = (TextView)findViewById(R.id.rating_store_address);
        storeRating = (RatingBar)findViewById(R.id.rating_rating);
        ratingCount = (TextView)findViewById(R.id.rating_rating_count);
        pageHeading = (TextView)findViewById(R.id.page_heading);
        ratingList = (ListView)findViewById(R.id.rating_list);
        backicon = (ImageView)findViewById(R.id.back_menu_icon);
        storeImage = (ImageView)findViewById(R.id.rating_store_image);
        visitStore = (TextView)findViewById(R.id.visit_store);
    }

    private void initManagers(){
        storeRatingDetailCallBack = new StoreRatingDetailCallBack();
        storeData = new StoreData();


    }

    private class StoreRatingDetailCallBack implements AsyncTaskCallBack{
        @Override
        public void onFinish(int responseCode, Object result) {

            Log.e("", "in on finish rating");

            StoreDetailBaseHolder storeDetailBaseHolder = (StoreDetailBaseHolder)result;

            if (UtilValidate.isNotNull(storeDetailBaseHolder)) {

                if (storeDetailBaseHolder.getStatus().equals("success")) {
                    dialogTransparent.dismiss();

                    if (UtilValidate.isNotNull(storeDetailBaseHolder.getData())) {

                        if (UtilValidate.isNotNull(storeDetailBaseHolder.getData().getStore())) {

                            storeData = storeDetailBaseHolder.getData().getStore();

                            if (UtilValidate.isNotNull(storeData.getStoreName())) {

                                storeName.setText(storeData.getStoreName());
                            }
                            if (UtilValidate.isNotNull(storeData.getStoreAddress())) {

                                storeAddress.setText(storeData.getStoreAddress());

                            }
                            if (UtilValidate.isNotNull(storeData.getStoreRating())){


                                String rate = String.valueOf(storeData.getStoreRating());
                                Log.e(TAG+"   "+storeData.getStoreRating(),">>>>>>>>>>>>>>>>"+rate);
                                storeRating.setRating(Float.parseFloat(rate));
                            }

                            if (storeData.getStoreImage()!= ""){
                                Picasso.with(StoreRatingActivity.this)
                                       .load(storeData.getStoreImage())
                                       .placeholder(R.drawable.logo_splash)
                                       .error(R.drawable.logo_splash)
                                       .into(storeImage);
                            }else {
                                Picasso.with(StoreRatingActivity.this).load(R.drawable.logo_splash).into(storeImage);
                            }

                        }



                        if (storeDetailBaseHolder.getData().getRatings() != null) {
                            Log.e(TAG,"rating not null");

                            store_ratings = storeDetailBaseHolder.getData().getRatings();
                            Log.e(TAG,"size>>>>"+store_ratings.size());
                            ratingCount.setText(store_ratings.size()+" ratings");
                            storeRatingAdapter = new StoreRatingAdapter(StoreRatingActivity.this, store_ratings);
                            ratingList.setAdapter(storeRatingAdapter);
                            storeRatingAdapter.notifyDataSetChanged();
                        }
                    }else{
                        Log.e(TAG,"data null");
                    }
                }else{
                    Log.e(TAG,"status not success");
                }
            }else{
                Log.e(TAG,"storeDetailBaseHolder null");
            }

            Log.e(TAG,"exiting on finish");

        }

        @Override
        public void onFinish(int responseCode, String result) {


            builder = new AlertDialog.Builder(StoreRatingActivity.this);
            builder.setMessage("Network Unavailable!!");
            builder.setCancelable(true);
            builder.setPositiveButton("Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialogTransparent.show();
                            dialog.cancel();
                            startActivity(getIntent());
                            if (isConnected(StoreRatingActivity.this)) {
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

}
