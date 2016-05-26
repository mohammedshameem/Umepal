/**
 * 
 */
package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.EntryItem;
import com.qiito.umepal.holder.MenuProfileListItemHolder;
import com.qiito.umepal.holder.SectionItem;

import java.util.List;


public class CustomMenuSectionProfileAdapter extends BaseAdapter {

	private List<MenuProfileListItemHolder> items;
	private Activity context;
	private LayoutInflater vi;


	public CustomMenuSectionProfileAdapter(List<MenuProfileListItemHolder> items,
			Activity context) {
		super();
		this.items = items;
		this.context = context;
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private static final String TAG = CustomMenuSectionProfileAdapter.class
			.getName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		final MenuProfileListItemHolder holder = items.get(position);
		if (holder != null) {
			if (holder.isSection()) {
				SectionItem si = holder.getSectionItem();
				v = vi.inflate(R.layout.custom_menu_profile_list_item_section, null);

				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);

				final TextView sectionView = (TextView) v
						.findViewById(R.id.section_text);
				sectionView.setText(si.getTitle());

			} else {
				EntryItem ei = holder.getEntryItem();
				v = vi.inflate(R.layout.custom_menu_profile_list_item_entry, null);
				final TextView titleView = (TextView) v
						.findViewById(R.id.home_menu_text);
				final ImageView imageView = (ImageView) v
						.findViewById(R.id.home_menu_icon);
				final View view_layout4 = (View)v
						.findViewById(R.id.view_layout4);
				final TextView notificationcount= (TextView)v
						.findViewById(R.id.notificationcount);
				final LinearLayout layoutBackground= (LinearLayout)v
						.findViewById(R.id.layoutBackground);

					titleView.setText(ei.getTitle().toString());
					imageView.setImageResource(ei.getImage());
					
					
					if(ei.getTitle().toString().equalsIgnoreCase("Settings")||ei.getTitle().toString().equalsIgnoreCase("Home")||ei.getTitle().toString().equalsIgnoreCase("About")){
						view_layout4.setVisibility(View.VISIBLE);
					}else{
						view_layout4.setVisibility(View.GONE);
					}
					
					if(ei.getTitle().toString().equalsIgnoreCase("Notification")){
						notificationcount.setVisibility(View.VISIBLE);
						if(UtilValidate.isNotNull(TodaysParentApp.getNotificationBaseHoldersList()))
						notificationcount.setText(TodaysParentApp.getNotificationBaseHoldersList().size()+"");
					}else{
						notificationcount.setVisibility(View.GONE);
					}
					if(ei.getTitle().toString().equalsIgnoreCase("Promotion")){
						
						imageView.setVisibility(View.VISIBLE);
						layoutBackground.setBackgroundColor(context.getResources().getColor(R.color.white));
						titleView.setTextColor(context.getResources().getColor(R.color.black));
						
					}
					if(ei.getTitle().toString().equalsIgnoreCase("New")){
						
						imageView.setVisibility(View.VISIBLE);
						layoutBackground.setBackgroundColor(context.getResources().getColor(R.color.white));
						titleView.setTextColor(context.getResources().getColor(R.color.black));
					}
			}
		}
		return v;
	}

}
