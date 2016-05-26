package com.qiito.umepal.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.holder.SubCategoryObject;

import java.util.ArrayList;
import java.util.List;


public class SubCategoryAdpater extends ArrayAdapter<SubCategoryObject> {
	
	private Activity ctx;
	private List<SubCategoryObject> subcategoryList = new ArrayList<SubCategoryObject>();

	public SubCategoryAdpater(Activity ctx, int txtViewResourceId, List<SubCategoryObject> subcategoryList) {
		super(ctx, txtViewResourceId, subcategoryList);
		this.subcategoryList=subcategoryList;
		this.ctx=ctx;
	}

	@Override
	public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
		return getCustomView(position, cnvtView, prnt);
	}

	@Override
	public View getView(int pos, View cnvtView, ViewGroup prnt) {
		return getCustomView(pos, cnvtView, prnt);
	}

	public View getCustomView(int position, View convertView,
			ViewGroup parent) {
		//LayoutInflater inflater = getActivity().getLayoutInflater();
		LayoutInflater inflater = ctx.getLayoutInflater();
		View mySpinner = inflater.inflate(
				R.layout.category_spinner_itemlayout, parent, false);
		TextView category_text = (TextView) mySpinner
				.findViewById(R.id.spinnertext_id);
		TextView category_listtext = (TextView) mySpinner
				.findViewById(R.id.spinner_textcategory_id);
		category_listtext.setText(subcategoryList.get(position).getName());
		
		if(position==0){
			
			category_listtext.setText("All");
		}
		
		return mySpinner;
	}
}