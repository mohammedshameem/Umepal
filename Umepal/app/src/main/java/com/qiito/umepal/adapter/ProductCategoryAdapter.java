package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 2/9/15.
 */




    public class ProductCategoryAdapter extends BaseAdapter {
    private List<Category>productCategoryList = new ArrayList<Category>();
    private Activity activity;
    private LayoutInflater inflater;
    private ViewHolder viewholder;
    private String message;

    public ProductCategoryAdapter(Activity activity, List<Category> productcategoryList) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.productCategoryList = productcategoryList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ProductCategoryAdapter(FragmentManager fragmentManager) {

    }


    @Override
    public int getCount() {
        return productCategoryList.size();
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /*
     * (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView( final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null){
            viewholder = new ViewHolder();

            convertView = inflater.inflate(R.layout.activity_pager, null);
            viewholder.CategoryName = (TextView)convertView.findViewById(R.id.tabs);


            convertView.setTag(viewholder);
        }else {
            viewholder = (ViewHolder)convertView.getTag();
        }




        if(UtilValidate.isNotNull(productCategoryList.get(position).getCategory_name()))
        {
            viewholder.CategoryName.setText("alo");
            //viewholder.CategoryName.setText(productCategoryList.get(position).getCategory_name());
        }
        else
        {
            viewholder.CategoryName.setText("A");
        }


        return convertView;
    }

    public class ViewHolder{

        private TextView CategoryName;



    }
}

