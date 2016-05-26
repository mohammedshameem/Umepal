package com.qiito.umepal.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by abin on 24/9/15.
 */
public class ImageViewPagerAdapter extends PagerAdapter {

    private ArrayList<String>productImagelist;
    LayoutInflater inflater;
    Activity activity;
    Context context;

    public ImageViewPagerAdapter(Context context,ArrayList<String>productImagelist,Activity activity){


        this.productImagelist=productImagelist;
        this.activity=activity;

    }


    @Override
    public int getCount() {
        return productImagelist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object imageView) {
        return view==((ImageView)imageView);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

ImageView productimage=new ImageView(activity);
        Log.e("", "url:" + productImagelist.get(position));
        if(UtilValidate.isNotNull(productImagelist.get(position))){
            if(UtilValidate.isNotEmpty(productImagelist.get(position))){

                Picasso.with(activity)
                        .load(productImagelist.get(position)).fit()
                        .placeholder(R.drawable.logo_splash)
                        .error(R.drawable.logo_splash)
                        .into(productimage);

            }else {productimage.setImageResource(R.drawable.logo_splash);}
        }else {productimage.setImageResource(R.drawable.logo_splash);}
        ((ViewPager)container).addView(productimage);
        return productimage;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }
}
