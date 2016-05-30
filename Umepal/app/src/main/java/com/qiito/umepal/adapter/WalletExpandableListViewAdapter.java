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

import java.util.HashMap;
import java.util.List;

/**
 * Created by shiya on 30/5/16.
 */
public class WalletExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listDataChild;

    private ViewHolder viewHolder;

    public WalletExpandableListViewAdapter(Activity activity, List<String> listDataHeader, HashMap<String, List<String>> listDataChild) {

        this.activity = activity;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandablelist_child, null);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
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

    }

}
