package com.qiito.umepal.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.adapter.ProductAdapter;
import com.qiito.umepal.holder.ProductBaseHolder;
import com.qiito.umepal.holder.ProductObject;
import com.qiito.umepal.holder.ProductSubListHolder;
import com.qiito.umepal.holder.ShoppingCartResponseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.SearchProductManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiya on 4/11/15.
 */
public class SearchProductActivity extends Activity {

    private SearchCallBack searchCallBack;
    private ProductSubListHolder productSubListHolder;
    private ProductAdapter productAdapter;

    private List<ProductObject> productsList = new ArrayList<>();

    private Dialog dialogTransparent;
    private View progressview;
    private GridView gridView;
    private ImageView backArrow;
    private TextView pageHeading;
    private TextView noItems;

    private String searchWord;
    private int offset=0;
    private int limit=0;
    private AddToCartCallBack addToCartCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searched_products_layout);

        initViews();
        initManager();

        Intent searchIntent = getIntent();
        searchWord = searchIntent.getExtras().getString("searchWord");
        Log.e("SEARCH_WORD>>",""+searchWord);

        pageHeading.setVisibility(View.VISIBLE);
        pageHeading.setText("Discover");

        dialogTransparent = new Dialog(this, android.R.style.Theme_Black);
        progressview = LayoutInflater.from(this).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);

        dialogTransparent.show();

        SearchProductManager.getInstance().getSearchProductDetails(SearchProductActivity.this, searchCallBack, DbManager.getInstance().getSessionId(), searchWord, offset, limit);


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews(){
        gridView = (GridView)findViewById(R.id.searched_product_grid);
        backArrow = (ImageView)findViewById(R.id.back_menu_icon);
        pageHeading = (TextView)findViewById(R.id.page_heading);
        noItems = (TextView)findViewById(R.id.noitems);
    }

    private void initManager(){

        searchCallBack = new SearchCallBack();
        productSubListHolder = new ProductSubListHolder();
        addToCartCallBack = new AddToCartCallBack();
    }

    private class SearchCallBack implements AsyncTaskCallBack {

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

                        productAdapter = new ProductAdapter(SearchProductActivity.this, productsList , addToCartCallBack);
                        gridView.setAdapter(productAdapter);
                        productAdapter.notifyDataSetChanged();

                    }else{
                        dialogTransparent.dismiss();
                        noItems.setVisibility(View.VISIBLE);
                        gridView.setVisibility(View.GONE);
                        Log.e("","null7");
                    }
                }else{
                    Log.e("","null8");
                }

            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    private class AddToCartCallBack implements AsyncTaskCallBack{
        @Override
        public void onFinish(int responseCode, Object result) {

            Log.e("","on finish success");

            ShoppingCartResponseHolder addToCart = (ShoppingCartResponseHolder) result;
            if (addToCart.getStatus().equalsIgnoreCase("success")){
                Toast.makeText(SearchProductActivity.this, addToCart.getMessage().toString(), Toast.LENGTH_LONG).show();
                Log.e("", "" + addToCart.getMessage());
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

            Log.e("","failed");

        }
    }

}
