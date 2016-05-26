package com.qiito.umepal.adapters; /**
 * Created by abin on 5/8/15.
 */

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qiito.umepal.R;
import com.qiito.umepal.fragments.NavigationDrawerFragment;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;

public class DrawerAdapter extends BaseAdapter {

    /**
     * LayoutInflater instance for inflating the requested layout in the list view
     */
    private LayoutInflater mInflater;
    private ArrayList<String> mDataSet;
    private LinearLayout notificationCount;
    private BadgeView  notificationbadgeView;
    private NavigationDrawerFragment navigationDrawerFragment;
    Context context;
    /**
     * Default constructor
     */
    public DrawerAdapter(Context context, ArrayList<String> dataSet, NavigationDrawerFragment navigationDrawerFragment) {

        mInflater = LayoutInflater.from(context);
        mDataSet = dataSet;
        this.navigationDrawerFragment = navigationDrawerFragment;
        this.context = context;

    }

    public int getCount() {
        return mDataSet.size();
    }

    public Object getItem(int index) {
        return mDataSet.get(index);
    }

    public long getItemId(int index) {
        return index;
    }

    public DrawerLayout mDrawerLayout;

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {

            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_drawer, parent, false);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.num_of_noti=(LinearLayout) view.findViewById(R.id.linear_num_of_notification);
            view.setTag(holder);

        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(mDataSet.get(position));
        //navigationDrawerFragment.SetNotificationCount( holder.num_of_noti);

/* show number of notification in notification */
        if(position==4)
        {
            //navigationDrawerFragment = new NavigationDrawerFragment();
            holder.num_of_noti.setVisibility(View.VISIBLE);
       /*     View target = view.findViewById(R.id.linear_num_of_notification);
           // navigationDrawerFragment.SetNotificationCount(target);*/


        }
        else {
            holder.num_of_noti.setVisibility(View.GONE);
        }

        /*if(position==7){

        }*/
        return view;
    }

    private static class ViewHolder {
        TextView title;
        LinearLayout num_of_noti;
    }
}