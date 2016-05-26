package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ProductObject;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecommendationsListAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private List<ProductObject> itemList;
	private ViewHolder viewHolder;


	public RecommendationsListAdapter(Activity activity,
			List<ProductObject> itemList) {
		// TODO Auto-generated constructor stub
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.recommendations_list_item,
					null);
			viewHolder.item_image = (ImageView) convertView
					.findViewById(R.id.item_image);
			viewHolder.item_name = (TextView) convertView
					.findViewById(R.id.item_name);
			viewHolder.item_total_price = (TextView) convertView
					.findViewById(R.id.item_total_price);
			convertView.setTag(viewHolder);

		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		// SET VALUES HERE...

		if (UtilValidate.isNotNull(itemList.get(position).getImage())) {

			if (UtilValidate.isNotEmpty(itemList.get(position).getImage())) {

	Picasso.with(activity).load(itemList.get(position).getImage())
						.placeholder(R.drawable.logo_splash)               /* single_background_color*/
						.error(R.drawable.logo_splash).fit()               /* single_background_color*/
			.into(viewHolder.item_image);

			}
		}

		if (UtilValidate.isNotNull(itemList.get(position).getName())) {
			viewHolder.item_name.setText(itemList.get(position).getName());
		}
		if (UtilValidate.isNotNull(itemList.get(position).getPrice())) {
			viewHolder.item_total_price.setText("$"+itemList.get(position)
					.getPrice());
		}
		return convertView;
	}

	private class ViewHolder {

		private ImageView item_image;
		private TextView item_name;
		private TextView item_total_price;

	}

}
