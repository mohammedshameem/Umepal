package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.fragments.Notifica;
import com.qiito.umepal.fragments.Notifications;
import com.qiito.umepal.holder.ProductNotificationBaseHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationListAdapter extends BaseAdapter {

	private Notifications notifications = new Notifications();
	private Activity activity;
	private LayoutInflater inflater;
	private List<ProductNotificationBaseHolder>notificationBaseHoldersList;
	private ViewHolder viewHolder;
	private ImageView remove;
	String sessionId;
	private StringBuilder message;

	public NotificationListAdapter(Activity activity, List<ProductNotificationBaseHolder> notificationBaseHoldersList, Notifica notifica) {

		this.notificationBaseHoldersList=notificationBaseHoldersList;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.activity=activity;
		
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return notificationBaseHoldersList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		 return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

       // Log.e("","in notification adapter"+ TodaysParentApp.getNotification_count());

		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.notifications_list_item, null);
			viewHolder.profile_image = (ImageView) convertView.findViewById(R.id.notification_profile_image);
			viewHolder.item_image=(ImageView)convertView.findViewById(R.id.notification_product_image);
			viewHolder.item_hours=(TextView)convertView.findViewById(R.id.notification_time);
			viewHolder.notificationMessage = (TextView)convertView.findViewById(R.id.notification_message);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		message = new StringBuilder();

		if (UtilValidate.isNotNull(notificationBaseHoldersList)){

			if (UtilValidate.isNotNull(notificationBaseHoldersList.get(position).getNotification_type())){
				if (notificationBaseHoldersList.get(position).getNotification_type().equals("1")){
					message.append("You have liked ");

				}
				else if (notificationBaseHoldersList.get(position).getNotification_type().equals("2")){
					//message.append("You have commented on ");
                     message.append(notificationBaseHoldersList.get(position).getMessage());
					 viewHolder.notificationMessage.setText(message);
				}
				else if (notificationBaseHoldersList.get(position).getNotification_type().equals("3")){
					message.append("You have purchased ");

				}
				/*if (UtilValidate.isNotNull(notificationBaseHoldersList.get(position).getProduct().getName())){
					message.append(notificationBaseHoldersList.get(position).getProduct().getName());
					viewHolder.notificationMessage.setText(message);
				}*/
			}
			else{
				viewHolder.notificationMessage.setText(notificationBaseHoldersList.get(position).getDescription());
			}

			if (UtilValidate.isNotNull(notificationBaseHoldersList.get(position).getDcraetedDate())) {
				viewHolder.item_hours.setText(notificationBaseHoldersList.get(position).getDcraetedDate());

			}
			if (UtilValidate.isNotNull(notificationBaseHoldersList.get(position).getProduct().getImage()) &&
					UtilValidate.isNotEmpty(notificationBaseHoldersList.get(position).getProduct().getImage())) {

				if (!notificationBaseHoldersList.get(position).getProduct().getImage().equals("")) {

					Picasso.with(activity).load(notificationBaseHoldersList.get(position).getProduct().getImage()).into(viewHolder.item_image);
				} else {
					Picasso.with(activity).load(R.drawable.logo_splash).into(viewHolder.item_image);
				}
			} else {
				Picasso.with(activity).load(R.drawable.logo_splash).into(viewHolder.item_image);
			}


			if(UtilValidate.isNotNull(notificationBaseHoldersList.get(position).getUser_profilePic())){
				Log.e("pic","notification"+notificationBaseHoldersList.get(position).getUser_profilePic());
				if(!notificationBaseHoldersList.get(position).getUser_profilePic().equalsIgnoreCase("")){
					Picasso.with(activity).load(notificationBaseHoldersList.get(position).getUser_profilePic()).into(viewHolder.profile_image);
				}
			}else {Picasso.with(activity).load(R.drawable.logo_splash).into(viewHolder.profile_image);}
		}
		notifyDataSetChanged();

        Log.e("","exiting notification adapter");
		return convertView;
	}


	private class ViewHolder {

		private ImageView item_image;
		private ImageView profile_image;
		private TextView profile_name;
		private TextView item_name;
		private TextView type;
		private TextView notificationMessage;
		private TextView item_quantity_number;
		private TextView item_individual_price_number;
		private TextView item_total_price;
		private TextView item_desc;
		private LinearLayout item_quantity_layout;
		private LinearLayout item_individual_price_layout;
		private TextView item_date;
		private LinearLayout linearfull;
		private TextView item_hours;
		private ImageView remove;
	}

}
