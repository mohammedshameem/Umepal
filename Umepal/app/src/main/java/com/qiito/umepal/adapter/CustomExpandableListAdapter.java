package com.qiito.umepal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.CategoryObject;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
    private List<CategoryObject> _listMainCategory; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listChildCategory;
    
    
    /**
     * 
     * @param _context
     * @param _listMainCategory
     * @param _listChildCategory
     */
	
	public CustomExpandableListAdapter(Context _context,
			List<CategoryObject> _listMainCategory,
			HashMap<String, List<String>> _listChildCategory) {
		super();
		this._context = _context;
		this._listMainCategory = _listMainCategory;
		this._listChildCategory = _listChildCategory;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this._listMainCategory.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		
		Log.i("#########", ""+this._listChildCategory.get(this._listMainCategory.get(groupPosition).getCategory_name()));
		
		return this._listChildCategory.get(this._listMainCategory.get(groupPosition).getCategory_name()).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return this._listMainCategory.get(groupPosition).getCategory_name();
	}
	

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return this._listChildCategory.get(this._listMainCategory.get(groupPosition).getCategory_name()).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {https://docs.google.com/presentation/d/1dN0sDoag8AzgIwjeVmScl-cFuz3slz7KJmqSotWUBRs/edit#slide=id.g397da1429_034
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		    String headerTitle = (String) getGroup(groupPosition);
		    String iconImageUrl = _listMainCategory.get(groupPosition).getCategory_icon();
		    String bg_colour = _listMainCategory.get(groupPosition).getBg_colour();
		    
	        if (convertView == null) {
	        	
	            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.list_group1, null);
	        }
	 
	        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
	        ImageView image_arrow = (ImageView)convertView.findViewById(R.id.expandableIcon);
	        ImageView category_image = (ImageView)convertView.findViewById(R.id.categoryImage);
	        LinearLayout category_list_item = (LinearLayout)convertView.findViewById(R.id.category_list_item);
	        
	        category_list_item.setBackgroundColor(Color.parseColor(bg_colour));
	        
	        lblListHeader.setText(headerTitle);
	        
	        if (UtilValidate.isNotNull(iconImageUrl)) {
				if (UtilValidate.isNotEmpty(iconImageUrl)) {
					Picasso.with(_context).load(iconImageUrl + "")
							.into(category_image);

				}
			}

	   
	        Log.i("", "SIZE OF CHILD"+getChildrenCount(groupPosition));
	        
	        if(getChildrenCount(groupPosition)==0)
	        {
	        	
	        	image_arrow.setVisibility(View.INVISIBLE);
	        }
	        else
	        {
	        	image_arrow.setVisibility(View.VISIBLE);
	        }
	        
	        if(groupPosition != 0){
	        	int imageResourceId = isExpanded ? android.R.drawable.arrow_up_float : android.R.drawable.arrow_down_float;
	        	image_arrow.setImageResource(imageResourceId);
	        	//image_arrow.setVisibility(View.VISIBLE);
	        } else {
	        	int imageResourceId = isExpanded ? android.R.drawable.arrow_up_float : android.R.drawable.arrow_down_float;
	        	image_arrow.setImageResource(imageResourceId);
	        	//image_arrow.setVisibility(View.VISIBLE);
	        	
	        }
	        
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		 final String childText = (String) getChild(groupPosition, childPosition);
		 String bg_colour_child = _listMainCategory.get(groupPosition).getBg_colour();
		 
		 
	        if (convertView == null) {
	        	
	            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.list_item1, null);
	        }
	 
	        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
	        LinearLayout child_list_item = (LinearLayout) convertView.findViewById(R.id.child_list_item);
	        
	        txtListChild.setTextColor(this._context.getResources().getColor(R.color.white));
	        child_list_item.setBackgroundColor(Color.parseColor(bg_colour_child));
	        
	        txtListChild.setText(childText);
	        
	 
	        return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
