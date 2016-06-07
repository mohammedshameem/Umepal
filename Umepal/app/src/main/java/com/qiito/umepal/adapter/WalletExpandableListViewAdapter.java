package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.holder.RebateDetailsHolder;
import com.qiito.umepal.holder.RebateListHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shiya on 30/5/16.
 */
public class WalletExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private List<RebateListHolder> listDataHeader = new ArrayList<>(); // header titles
    // child data in format of header title, child title
    //private HashMap<List<RebateListHolder>, List<RebateDetailsHolder>> listDataChild = new HashMap<>();


    private ViewHolder viewHolder;

    public WalletExpandableListViewAdapter(Activity activity, List<RebateListHolder> listDataHeader) {

        this.activity = activity;
        this.listDataHeader = listDataHeader;
        // this.listDataChild = listDataChild;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        //listDataHeader.get(groupPosition).getDetails().get(childPosititon)
        return listDataHeader.get(groupPosition).getDetails().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final RebateDetailsHolder rebatedetail = (RebateDetailsHolder) getChild(groupPosition, childPosition);

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandablelist_child, null);
            viewHolder.rebateSpendAmount = (TextView) convertView.findViewById(R.id.rebate_spend_amount);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (rebatedetail.getPrice() != null) {
            if (!rebatedetail.getPrice().equalsIgnoreCase("")) {
                viewHolder.rebateSpendAmount.setText(activity.getString(R.string.dollar) + " " + rebatedetail.getPrice());
            }
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        //listDataHeader.get(groupPosition).getDetails().size()
        return listDataHeader.get(groupPosition).getDetails().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandablelist_header, null);
            viewHolder.arrow_down = (ImageView) convertView.findViewById(R.id.arrow_down);
            viewHolder.arrow_right = (ImageView) convertView.findViewById(R.id.arrow_right);
            viewHolder.statementPrice = (TextView) convertView.findViewById(R.id.statement_price);
            viewHolder.month = (TextView) convertView.findViewById(R.id.month);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Log.e(groupPosition + " << Is EXPANDED >> ", "" + isExpanded);

        if (isExpanded) {
            viewHolder.arrow_down.setVisibility(View.VISIBLE);
            viewHolder.arrow_right.setVisibility(View.GONE);
        } else {
            viewHolder.arrow_down.setVisibility(View.GONE);
            viewHolder.arrow_right.setVisibility(View.VISIBLE);
        }

        if (listDataHeader.get(groupPosition).getTotal() != null) {
            if (!listDataHeader.get(groupPosition).getTotal().equalsIgnoreCase("")) {
                viewHolder.statementPrice.setText(activity.getString(R.string.dollar) + "" + listDataHeader.get(groupPosition).getTotal());
            }
        }

        if (listDataHeader.get(groupPosition).getMonth() != null) {
            if (!listDataHeader.get(groupPosition).getMonth().equalsIgnoreCase("")) {
                StringBuilder sb = new StringBuilder();
                sb.append(listDataHeader.get(groupPosition).getMonth());
                if (listDataHeader.get(groupPosition).getYear() != null) {
                    if (!listDataHeader.get(groupPosition).getYear().equalsIgnoreCase("")) {
                        sb.append(" " + listDataHeader.get(groupPosition).getYear());
                    }
                }

                viewHolder.month.setText(sb.toString());
            }
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosititon) {
        return true;
    }

    private class ViewHolder {

        private ImageView arrow_down;
        private ImageView arrow_right;
        private TextView statementPrice;
        private TextView month;
        private TextView rebateSpendAmount;

    }

}
