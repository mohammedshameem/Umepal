package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.PurchasedItems;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemsListAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private List<PurchasedItems> itemList;
	private ViewHolder viewHolder;
	private int Quantity;
	private double singleprice;


	public ItemsListAdapter(Activity activity, List<PurchasedItems> itemList) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.itemList = itemList;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {

			//Log.e("","In Items List Adapter");
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.my_account_list_item, null);
			viewHolder.item_image = (ImageView) convertView.findViewById(R.id.item_image);
			viewHolder.item_name = (TextView) convertView.findViewById(R.id.item_name);
			viewHolder.item_quantity_number = (TextView) convertView.findViewById(R.id.item_quantity_number);
			viewHolder.item_individual_price_number = (TextView) convertView.findViewById(R.id.item_individual_price_number);
			viewHolder.item_total_price = (TextView) convertView.findViewById(R.id.item_total_price);
			convertView.setTag(viewHolder);

		}
		else {
			  viewHolder = (ViewHolder) convertView.getTag();
    		 }

		// SET VALUES HERE...
		if(UtilValidate.isNotNull(itemList.get(position).getProduct_name()))
		{
			viewHolder.item_name.setText(itemList.get(position).getProduct_name());
		}
		if(UtilValidate.isNotNull(itemList.get(position).getQuantity()))
		{
			Quantity = itemList.get(position).getQuantity();
			viewHolder.item_quantity_number.setText(String.valueOf(itemList.get(position).getQuantity()));
		}
		if(UtilValidate.isNotNull(itemList.get(position).getPrice())){
			
			viewHolder.item_individual_price_number.setText(itemList.get(position).getPurchased_price());
		}
		
		if(UtilValidate.isNotNull(itemList.get(position).getProduct_image())){
			if(!itemList.get(position).getProduct_image().equals(""))
			{
				Picasso.with(activity).load(itemList.get(position).getProduct_image()).placeholder(R.drawable.logo_splash)
				   	.error(R.drawable.logo_splash).into(viewHolder.item_image);
			}
		}

		singleprice=(Double.parseDouble(itemList.get(position).getPrice())*Quantity);
		viewHolder.item_total_price.setText("$"+singleprice);

		return convertView;
	}

	private class ViewHolder {

		private ImageView item_image;
		private TextView item_name;
		private TextView item_quantity_number;
		private TextView item_individual_price_number;
		private TextView item_total_price;

	}

}
