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
import com.qiito.umepal.holder.PurchasedItems;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by abin on 1/9/15.
 */


public class MyPurchasesAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<PurchasedItems> itemList;
    private ViewHolder viewHolder;
    private int Quantity;
    private double totalPrice;


    public MyPurchasesAdapter(Activity activity, List<PurchasedItems> itemList) {
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
            viewHolder.item_image = (ImageView) convertView.findViewById(R.id.item_image);
            viewHolder.item_name = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.item_individual_price_number = (TextView) convertView.findViewById(R.id.item_individual_price_number);
            viewHolder.itemquantity = (TextView) convertView.findViewById(R.id.item_quantity_number);
            viewHolder.price = (TextView) convertView.findViewById(R.id.item_total_price);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        // SET VALUES HERE...
        if (UtilValidate.isNotNull(itemList.get(position).getProduct_name())) {
            viewHolder.item_name.setText(itemList.get(position).getProduct_name());
        }
           /* if(UtilValidate.isNotNull(itemList.get(position).getProduct_description()))
            {
                viewHolder.item_description.setText(itemList.get(position).getProduct_description());
            }*/
        String productQuantity = String.valueOf(itemList.get(position).getQuantity());
        if (UtilValidate.isNotNull(itemList.get(position).getQuantity())) {
            if (itemList.get(position).getQuantity() != 0) {
                viewHolder.itemquantity.setText(productQuantity);
            }
        }
        if (UtilValidate.isNotNull(itemList.get(position).getPrice())) {
          /* if(DbManager.getInstance().getCurrentUserDetails().getMembership_status().equalsIgnoreCase("true")){
               viewHolder.item_individual_price_number.setText("$ " + itemList.get(position).getDiscountprice());
           }else {*/
               viewHolder.item_individual_price_number.setText("$ " + itemList.get(position).getPurchased_price());
           }

       // }

        if (UtilValidate.isNotNull(itemList.get(position).getProduct_image())) {
            if (!itemList.get(position).getProduct_image().equals("")) {
                Picasso.with(activity).load(itemList.get(position).getProduct_image()).placeholder(R.drawable.logo_splash)
                        .error(R.drawable.logo_splash).into(viewHolder.item_image);
            }
        }

        if (UtilValidate.isNotNull(itemList.get(position).getPurchased_price())){
            if (UtilValidate.isNotNull(itemList.get(position).getQuantity())){
                totalPrice = (Double.parseDouble(itemList.get(position).getPurchased_price()) * itemList.get(position).getQuantity());
                String a=String.format("%.2f", totalPrice);
                viewHolder.price.setText("$ "+a);
            }
        }



        return convertView;
    }

    private class ViewHolder {

        private ImageView item_image;
        private TextView item_name;
        private TextView item_individual_price_number;
        private TextView item_description;
        private TextView itemquantity;
        private TextView price;

    }

}





