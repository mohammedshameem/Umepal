package com.qiito.umepal.adapters;

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

/**
 * Created by abin on 1/9/15.
 */


    public class MyLikesAdapter extends BaseAdapter {

        private Activity activity;
        private LayoutInflater inflater;
        private List<ProductObject> itemList;
        private ViewHolder viewHolder;
        private int Quantity =1;
        private double singleprice;


        public MyLikesAdapter(Activity activity, List<ProductObject> itemList) {
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
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            if (convertView == null) {

                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.my_account_list_item, null);
                viewHolder.item_image = (ImageView) convertView
                        .findViewById(R.id.item_image);
                viewHolder.item_name = (TextView) convertView
                        .findViewById(R.id.item_name);
                viewHolder.item_individual_price_number = (TextView) convertView
                        .findViewById(R.id.item_individual_price_number);
                viewHolder.item_discount=(TextView)convertView
                        .findViewById(R.id.item_quantity);
                viewHolder.itemquantity=(TextView)convertView.findViewById(R.id.item_quantity_number);
                viewHolder.totalprice=(TextView)convertView.findViewById(R.id.item_total_price);

                convertView.setTag(viewHolder);

            } else {

                viewHolder = (ViewHolder) convertView.getTag();
            }

            // SET VALUES HERE...
            if(UtilValidate.isNotNull(itemList.get(position).getName()))
            {
                viewHolder.item_name.setText(itemList.get(position).getName());
            }

            if(UtilValidate.isNotNull(itemList.get(position).getPrice())){
                viewHolder.totalprice.setText("$ "+itemList.get(position).getPrice());
                viewHolder.item_individual_price_number.setText("$ "+itemList.get(position).getPrice());
            }

            viewHolder.itemquantity.setText(""+Quantity);
            if(UtilValidate.isNotNull(itemList.get(position).getImage())){
                if(!itemList.get(position).getImage().equals(""))
                {
                    Picasso.with(activity)
                            .load(itemList.get(position).getImage())
                            .placeholder(R.drawable.logo_splash)
                            .error(R.drawable.logo_splash)
                            .into(viewHolder.item_image);
                }
            }

            singleprice=(Double.parseDouble(itemList.get(position).getPrice())*Quantity);

            return convertView;
        }

        private class ViewHolder {

            private ImageView item_image;
            private TextView item_name;
            private TextView item_individual_price_number;
            private TextView itemquantity;
            private TextView item_discount;
            private TextView totalprice;

        }

    }



