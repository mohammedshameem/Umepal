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

import java.util.List;

/**
 * Created by shiya on 30/5/16.
 */
public class WalletListViewAdapter extends BaseAdapter {

    private Activity activity;
    private List<String> price;
    private LayoutInflater inflater;
    private ViewHolder viewholder;

    public WalletListViewAdapter(Activity activity, List<String> price){

        this.activity = activity;
        this.price = price;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return price.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        if(view==null){
            viewholder = new ViewHolder();

            view = inflater.inflate(R.layout.expandablelist_header, null);
            viewholder.arrowLayout = (LinearLayout)view.findViewById(R.id.arrow_layout);
            viewholder.textLayout = (LinearLayout) view.findViewById(R.id.text_layout);
            viewholder.amountLayout = (LinearLayout)view.findViewById(R.id.amount_layout);
            viewholder.statementPrice = (TextView) view.findViewById(R.id.statement_price);
            viewholder.month = (TextView) view.findViewById(R.id.month);
;
            view.setTag(viewholder);
        }else {
            viewholder = (ViewHolder)view.getTag();
        }
        viewholder.arrowLayout.setVisibility(View.GONE);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        viewholder.amountLayout.setLayoutParams(lp);

        viewholder.statementPrice.setText(price.get(position));

        return view;
    }

    private class ViewHolder{

        LinearLayout arrowLayout;
        LinearLayout textLayout;
        LinearLayout amountLayout;
        TextView statementPrice;
        TextView month;

    }
}
