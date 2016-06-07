package com.qiito.umepal.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.holder.MembershipBaseHolder;

/**
 * Created by abin on 7/6/16.
 */
public class MembershipTypeAdapter extends BaseAdapter {

    private MembershipBaseHolder membershipBaseHolder;
    private Activity activity;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;

    public MembershipTypeAdapter(Activity activity, MembershipBaseHolder membershipBaseHolder){
        this.activity= activity;
        this.membershipBaseHolder=membershipBaseHolder;
    }


    @Override
    public int getCount() {
        return getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.membership_upgrade_button, null);
            viewHolder.membershipTypeTxt = (TextView) convertView.findViewById(R.id.membershipTypeTxt);

            if(membershipBaseHolder.getData().get(position).getMembershipname()!=null){
                viewHolder.membershipTypeTxt.setText(membershipBaseHolder.getData().get(position).getMembershipname());
            }

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    private class ViewHolder {

        private TextView membershipTypeTxt;

    }
}
