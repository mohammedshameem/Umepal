package com.qiito.umepal.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProductDetailImageAdapter extends PagerAdapter {

	private  ArrayList<String>imageList;
	LayoutInflater inflater;
	Activity activity;

	public ProductDetailImageAdapter(Context context,
			 ArrayList<String>imageList,Activity activity) {
		// TODO Auto-generated constructor stub
		this.imageList = imageList;
		this.activity=activity;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object imageView) {
		// TODO Auto-generated method stub
		return view == ((ImageView)imageView );
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		
		ImageView productserviceimage = new ImageView(activity);
		//productserviceimage.setImageResource(imageList.get(position));
		Log.e("", "url:"+imageList.get(position));
		if(UtilValidate.isNotNull(imageList.get(position))){
			productserviceimage.setScaleType(ScaleType.FIT_XY);
			if(UtilValidate.isNotEmpty(imageList.get(position))){
			
				Picasso.with(activity)
				.load(imageList.get(position))
				.placeholder(R.drawable.logo_splash)
				.error(R.drawable.logo_splash).into(productserviceimage);
			/*	Picasso.with(activity).load(imageList.get(position))
				.into(productserviceimage);
				*/
			}
		}
		
		
		
		
		
		
/*		
		
		if(UtilValidate.isNotNull(imageList.get(position)) && UtilValidate.isNotEmpty(imageList.get(position)))
		{
			if(!imageList.get(position).equals(""))
			{
			//viewholder.productIcon.
			Picasso.with(activity).load(imageList.get(position))
			.into(productserviceimage);
			}
			else
			{
				Picasso.with(activity).load(R.drawable.logo_splash)
				.into(productserviceimage);
			}
		}
		else{
			Picasso.with(activity).load(R.drawable.logo_splash)
			.into(productserviceimage);
		}
		*/
		
		
		
		
		
		
		
		
		
		((ViewPager)container).addView(productserviceimage);
			
		return productserviceimage;
	}
	
	@Override
    public void destroyItem(View container, int position, Object object) {
         ((ViewPager) container).removeView((View) object);
    }

}

