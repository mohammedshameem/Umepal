package com.qiito.umepal.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.activity.ReturnPolicy;

import com.qiito.umepal.activity.ShoppingCart;
import com.qiito.umepal.holder.ShoppingCartList;
import com.qiito.umepal.holder.ShoppingCartResponseHolder;
import com.qiito.umepal.holder.UserObjectHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.ShoppingCartManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shiya on 15/9/15.
 */
public class ShoppingCartAdapter extends BaseAdapter {

    private ShoppingCart shoppingCartActivity = new ShoppingCart();
    private DeleteFromCartCallBack deleteFromCartCallBack;
    private List<ShoppingCartList> itemList;
    private Activity activity;
    private ViewHolder viewHolder;
    private LinearLayout emptyCart;
    private LinearLayout shoppingCart;
    private LayoutInflater inflater;
    private TextView totalPrice;
    private TextView savings;

    private double Quantity;
    private double price;
    private double totalAmount;
    private double SumTotal = 0.0;
    private double shippingamount;
    private double TotalAmount;
    private double orderTotal;
    private double convertedPrice;
    private double DollarConvert = 0.7011;
    private int i = 0;

    public ShoppingCartAdapter(Activity activity, List<ShoppingCartList> itemList, TextView totalPrice,
                               TextView savings, LinearLayout emptyCart, LinearLayout shoppingCart) {
        this.activity = activity;
        this.itemList = itemList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.totalPrice = totalPrice;
        this.savings = savings;
        this.shoppingCart = shoppingCart;
        this.emptyCart = emptyCart;

    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        //clearAll = (TextView)findViewById(R.id.clear_all_cart_list);
        int count = itemList.size();

        if (view == null) {
            viewHolder = new ViewHolder();

            view = inflater.inflate(R.layout.check_out_list_item, null);
            viewHolder.productImage = (ImageView) view.findViewById(R.id.item_image);
            viewHolder.productName = (TextView) view.findViewById(R.id.item_name);
            viewHolder.quantity = (TextView) view.findViewById(R.id.item_quantity_number);
            viewHolder.individualPrice = (TextView) view.findViewById(R.id.item_individual_price_number);
            viewHolder.totalPrice = (TextView) view.findViewById(R.id.item_total_price);
            viewHolder.removeItem = (ImageView) view.findViewById(R.id.item_remove);
            viewHolder.productdetails = (LinearLayout) view.findViewById(R.id.cart_product_detail_layout);
            viewHolder.orderSummary = (LinearLayout) view.findViewById(R.id.cart_order_summary_layout);
            viewHolder.storeName = (TextView) view.findViewById(R.id.cart_store_name);
            viewHolder.estimatedShipping = (TextView) view.findViewById(R.id.cart_shipping);
            viewHolder.estimatedArrival = (TextView) view.findViewById(R.id.cart_arrival);
            viewHolder.availability = (TextView) view.findViewById(R.id.cart_availability);
            viewHolder.itemTotal = (TextView) view.findViewById(R.id.cart_item_total);
            viewHolder.shippingTotal = (TextView) view.findViewById(R.id.cart_shipping_total);
            viewHolder.orderTotal = (TextView) view.findViewById(R.id.cart_order_total);
            viewHolder.convertPrice = (TextView) view.findViewById(R.id.cart_convert_price);
            viewHolder.returnPolicy = (TextView) view.findViewById(R.id.cart_return_policy);
            viewHolder.availabilityKeyword = (TextView) view.findViewById(R.id.availability_keyword);
            viewHolder.estimatedArrivalKeyword = (TextView) view.findViewById(R.id.estimated_arrival_keyword);
            viewHolder.shippingChargesKeyword = (TextView) view.findViewById(R.id.shipping_charges_keyword);
            viewHolder.shippingChargeDollar = (TextView) view.findViewById(R.id.shipping_charges_dollar);
            viewHolder.storePickup = (TextView) view.findViewById(R.id.store_pickup);
            viewHolder.memberKeyword = (LinearLayout) view.findViewById(R.id.member_keyword);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (position == count - 1) {
            viewHolder.orderSummary.setVisibility(View.VISIBLE);
        } else {
            viewHolder.orderSummary.setVisibility(View.GONE);
        }
        if (UtilValidate.isNotNull(itemList.get(position).getName())) {
            viewHolder.productName.setText(itemList.get(position).getName());
        }

        if (UtilValidate.isNotNull(itemList.get(position).getQuantity())) {
            viewHolder.quantity.setText(itemList.get(position).getQuantity());
            Quantity = Double.parseDouble(itemList.get(position).getQuantity());
        }

        if (UtilValidate.isNotNull(DbManager.getInstance().getCurrentUserDetails())) {
            if ((DbManager.getInstance().getCurrentUserDetails().is_member())) {

                Log.e("user", "is a member");
                if (UtilValidate.isNotNull(itemList.get(position).getDiscountprice())) {
                    viewHolder.individualPrice.setText(" " + itemList.get(position).getDiscountprice());
                    viewHolder.memberKeyword.setVisibility(View.VISIBLE);
                    price = Double.parseDouble(itemList.get(position).getDiscountprice());
                } else {
                    if (UtilValidate.isNotNull(itemList.get(position).getPrice())) {
                        Log.e("price<<", itemList.get(position).getPrice());
                        viewHolder.individualPrice.setText(" " + itemList.get(position).getPromoprice());
                        price = Double.parseDouble(itemList.get(position).getPromoprice());
                    }
                }
                if (UtilValidate.isNotNull(itemList.get(position).getQuantity())) {
                    Log.e("quantity<<", itemList.get(position).getQuantity());
                    viewHolder.quantity.setText(itemList.get(position).getQuantity());
                    Quantity = Double.parseDouble(itemList.get(position).getQuantity());
                }
                totalAmount = price * Quantity;

                String total = String.format("%.2f", totalAmount);
                viewHolder.totalPrice.setText(" " + total);
                // totalAmount = price * Quantity;

                //String total = String.format("%.2f", totalAmount);
                //viewHolder.totalPrice.setText(" " + total);


            } else {

                Log.e("user", "not a member");

                if (UtilValidate.isNotNull(itemList.get(position).getPrice())) {
                    Log.e("price<<", itemList.get(position).getPrice());
                    viewHolder.individualPrice.setText(" " + itemList.get(position).getPromoprice());
                    price = Double.parseDouble(itemList.get(position).getPromoprice());
                }

                totalAmount = price * Quantity;

                String total = String.format("%.2f", totalAmount);
                viewHolder.totalPrice.setText(" " + total);

            }

        }

        if (UtilValidate.isNotNull(itemList.get(position).getImage())) {
            if (!itemList.get(position).getImage().equals("")) {
                Picasso.with(activity).load(itemList.get(position).getImage()).placeholder(R.drawable.logo_splash)
                        .error(R.drawable.logo_splash).into(viewHolder.productImage);
            }
        }

        /*if (UtilValidate.isNotNull(itemList.get(position).getQuantity())) {
            viewHolder.quantity.setText(itemList.get(position).getQuantity());
            Quantity = Double.parseDouble(itemList.get(position).getQuantity());
        }*/
        /*totalAmount = price * Quantity;

        String total = String.format("%.2f", totalAmount);
        viewHolder.totalPrice.setText(" " + total);*/
        if (UtilValidate.isNotNull(itemList.get(position).getShippingcharge())) {
            if (UtilValidate.isNotNull(itemList.get(position).getQuantity())) {
                double totalShipping = Double.parseDouble(itemList.get(position).getShippingcharge()) * Double.parseDouble(itemList.get(position).getQuantity());
                String estimatedship = String.format("%.2f", totalShipping);
                viewHolder.estimatedShipping.setText(" " + estimatedship);
            }

        }
        if (UtilValidate.isNotNull(itemList.get(position).getShipping_from_name())) {

            viewHolder.storeName.setText(itemList.get(position).getShipping_from_name());
        }


        if (itemList.get(position).getCollect_at_store() == 1) {
            viewHolder.availabilityKeyword.setText("Store Address : ");
            viewHolder.shippingChargesKeyword.setText("Pick up at Store : ");
            viewHolder.estimatedArrivalKeyword.setText("Phone : ");

            viewHolder.availability.setText(itemList.get(position).getShipping_from_address());
            viewHolder.estimatedArrival.setText(itemList.get(position).getStorePhone());


            viewHolder.shippingChargeDollar.setVisibility(View.GONE);
            viewHolder.estimatedShipping.setVisibility(View.GONE);
            viewHolder.storePickup.setVisibility(View.VISIBLE);
        } else {

            viewHolder.availabilityKeyword.setText("Availability : ");
            viewHolder.shippingChargesKeyword.setText("Shipping Charges : ");
            viewHolder.estimatedArrivalKeyword.setText("Estimated Arrival : ");

            viewHolder.availability.setText(itemList.get(position).getAvailability());
            viewHolder.estimatedArrival.setText(itemList.get(position).getEstimated_arrival());

            viewHolder.shippingChargeDollar.setVisibility(View.VISIBLE);
            viewHolder.estimatedShipping.setVisibility(View.VISIBLE);
            viewHolder.storePickup.setVisibility(View.GONE);

            if (UtilValidate.isNotNull(itemList.get(position).getShippingcharge())) {
                if (UtilValidate.isNotNull(itemList.get(position).getQuantity())) {
                    double totalShipping = Double.parseDouble(itemList.get(position).getShippingcharge()) * Double.parseDouble(itemList.get(position).getQuantity());
                    String estimatedship = String.format("%.2f", totalShipping);
                    viewHolder.estimatedShipping.setText(" " + estimatedship);
                }

            }
        }

        TotalAmount = Double.parseDouble(TodaysParentApp.getItemTotalValue());
        shippingamount = Double.parseDouble(TodaysParentApp.getShippingValue());
        viewHolder.itemTotal.setText(" " + (TodaysParentApp.getItemTotalValue()));
        viewHolder.shippingTotal.setText(" " + TodaysParentApp.getShippingValue());


        orderTotal = TotalAmount + shippingamount;
        String OrderTotal = String.format("%.2f", orderTotal);
        viewHolder.orderTotal.setText(" " + TodaysParentApp.getOrderTotalValue());
        TodaysParentApp.setOrderTotalValue(OrderTotal);

        convertedPrice = orderTotal * DollarConvert;
        String Converted = String.format("%.2f", convertedPrice);
        viewHolder.convertPrice.setText(Converted);


        viewHolder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (itemList.size() > 0) {
                    UserObjectHolder userObjectHolder;
                    userObjectHolder = DbManager.getInstance().getCurrentUserDetails();

                    String userId = String.valueOf(userObjectHolder.getId());
                    String productId = String.valueOf(itemList.get(position).getId());
                    deleteFromCartCallBack = new DeleteFromCartCallBack();
                    String session_id = DbManager.getInstance().getSessionId();
                    ShoppingCartManager.getInstance().deleteFromCart(activity, deleteFromCartCallBack, userId, productId, session_id);
                    viewHolder.itemTotal.setText(" " + TodaysParentApp.getItemTotalValue());
                    viewHolder.shippingTotal.setText(" " + TodaysParentApp.getShippingValue());
                    TotalAmount = Double.parseDouble(TodaysParentApp.getItemTotalValue());
                    shippingamount = Double.parseDouble(TodaysParentApp.getShippingValue());

                    orderTotal = TotalAmount + shippingamount;
                    String OrderTotal = String.format("%.2f", orderTotal);
                    TodaysParentApp.setOrderTotalValue(OrderTotal);
                    viewHolder.orderTotal.setText(" " + orderTotal);
                    itemList.remove(position);
                    shoppingCartActivity.decrementPrice(itemList, totalPrice, savings, shoppingCart, emptyCart);

                    notifyDataSetChanged();

                } else {

                }
            }
        });

        viewHolder.returnPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent returnpolicyIntent = new Intent(v.getContext(), ReturnPolicy.class);
                v.getContext().startActivity(returnpolicyIntent);

            }
        });


        i++;

        return view;
    }

    private class ViewHolder {

        ImageView productImage;
        TextView productName;
        TextView quantity;
        TextView individualPrice;
        TextView totalPrice;
        ImageView removeItem;
        TextView storeName;
        TextView estimatedShipping;
        TextView estimatedArrival;
        TextView availability;
        TextView itemTotal;
        TextView shippingTotal;
        TextView orderTotal;
        TextView convertPrice;
        TextView returnPolicy;
        LinearLayout productdetails;
        TextView availabilityKeyword;
        TextView shippingChargesKeyword;
        TextView estimatedArrivalKeyword;
        TextView shippingChargeDollar;
        TextView storePickup;
        LinearLayout orderSummary;
        LinearLayout memberKeyword;


    }

    private class DeleteFromCartCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {

            ShoppingCartResponseHolder shoppingCartResponseHolder = (ShoppingCartResponseHolder) result;
            if (shoppingCartResponseHolder.getStatus().equals("success")) {

                Toast.makeText(activity, shoppingCartResponseHolder.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }
}
