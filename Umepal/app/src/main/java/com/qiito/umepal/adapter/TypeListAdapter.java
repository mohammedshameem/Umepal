package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.CategoryObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TypeListAdapter extends BaseAdapter{
	
	private ViewHolder viewholder;
	private List<CategoryObject>categoryList = new ArrayList<CategoryObject>();
	private LayoutInflater inflater;
	private Activity activity;
	private String bgcolor;
	

	public TypeListAdapter(Activity activity, List<CategoryObject> categoryList) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.categoryList = categoryList;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categoryList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			
			viewholder = new ViewHolder();
			convertView = inflater.inflate(R.layout.types_list_item, null);
			viewholder.TypeItemid = (TextView)convertView.findViewById(R.id.TypeItemid);
			viewholder.TypeItemImageid = (ImageView)convertView.findViewById(R.id.TypeItemImageid);
			viewholder.typeItemLayout = (LinearLayout)convertView.findViewById(R.id.typeItemLayout);
			convertView.setTag(viewholder);
		}else{
			
			
			viewholder = (ViewHolder)convertView.getTag();
		}
		bgcolor=categoryList.get(position).getBg_colour();
		if(UtilValidate.isNotNull(categoryList.get(position).getCategory_name()))
		{
			
			viewholder.typeItemLayout.setBackgroundColor(Color.parseColor(bgcolor));
			

		//	viewholder.typeItemLayout.setBackgroundColor(Integer.parseInt(categoryList.get(position).getBg_colour()));
			viewholder.TypeItemid.setTextColor(this.activity.getResources().getColor(R.color.white));
			viewholder.TypeItemid.setText(categoryList.get(position).getCategory_name());

			Picasso.with(activity)
			.load(categoryList.get(position).getCategory_icon())
			.placeholder(R.drawable.background_button) /*  single_background_color*/
				.error(R.drawable.background_button).fit().into(viewholder.TypeItemImageid);
		
		}
		
		return convertView;
	}

	public class ViewHolder{
		
		private TextView TypeItemid;
		private ImageView TypeItemImageid;
		private LinearLayout typeItemLayout;
	}
	
}
