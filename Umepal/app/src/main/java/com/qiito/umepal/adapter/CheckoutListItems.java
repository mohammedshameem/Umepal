package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.dao.CheckoutDAO;
import com.qiito.umepal.holder.CheckoutProductsHolder;
import com.qiito.umepal.managers.DbManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class CheckoutListItems extends BaseAdapter {

	private Activity activity;
	private String userid;
	private LayoutInflater inflater;
	private List<CheckoutProductsHolder> itemList;
	private ViewHolder viewHolder;

//CheckoutListItems
	public CheckoutListItems(Activity activity, List<CheckoutProductsHolder> itemList) {
		// TODO Auto-generated constructor stub
		
		HashMap<String, String> getsessionid = DbManager.getInstance()
				.fetchCurrentUserDetails();
		userid = getsessionid.get("user_id");
		this.activity = activity;
		this.itemList = itemList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void notifyAdapter(){
		
		this.itemList.clear();
		this.itemList = CheckoutDAO.getInstance().getProductDetailsFromCheckout(userid);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.check_out_list_item, null);
			viewHolder.item_image = (ImageView) convertView.findViewById(R.id.item_image);
			viewHolder.item_name = (TextView) convertView.findViewById(R.id.item_name);
			viewHolder.item_quantity_number = (TextView) convertView.findViewById(R.id.item_quantity_number);
			viewHolder.item_individual_price_number = (TextView) convertView.findViewById(R.id.item_individual_price_number);
			viewHolder.item_total_price = (TextView) convertView.findViewById(R.id.item_total_price);
			viewHolder.item_remove= (ImageView) convertView.findViewById(R.id.item_remove);
			convertView.setTag(viewHolder);

		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		// SET VALUES HERE...
		if (UtilValidate.isNotNull(itemList.get(position).getProductname())) {
			viewHolder.item_name.setText(itemList.get(position).getProductname());
		}
		if (UtilValidate.isNotNull(itemList.get(position).getImage())) {

			if(!itemList.get(position).getImage().equals(""))
			{
			Picasso.with(activity).load(itemList.get(position).getImage())
					.placeholder(R.drawable.background_button)    /*single_background_color*/
					.error(R.drawable.background_button).fit()
					.into(viewHolder.item_image);
			}
		}
		if (UtilValidate.isNotNull(itemList.get(position).getPrice())) {
			viewHolder.item_individual_price_number.setText("$"+itemList.get(
					position).getPrice());
		}
		if (UtilValidate.isNotNull(itemList.get(position).getQuantity())) {
			viewHolder.item_quantity_number.setText(itemList.get(position)
					.getQuantity());
		}
		if (UtilValidate.isNotNull(itemList.get(position).getPrice())||(UtilValidate.isNotNull(itemList.get(position).getQuantity()))) {
			
			float a=Float.parseFloat(itemList.get(position).getPrice());
			float b=Float.parseFloat(itemList.get(position).getQuantity());
			float c=a*b;
			Log.e("", "value of c"+c);
		
			viewHolder.item_total_price.setText("$"+Math.round(c*100.0)/100.0+"");
		}
		
		
		viewHolder.item_remove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//CheckoutDAO.getInstance().deleteAllCheckout();
				
//				((ShoppingCartActivity) activity).removeItem(itemList.get(position),position);
			}
		});
		
		
		return convertView;
	}

	private class ViewHolder {

		private ImageView item_image;
		private TextView item_name;
		private TextView item_quantity_number;
		private TextView item_individual_price_number;
		private TextView item_total_price;
		private ImageView item_remove;

	}

}
