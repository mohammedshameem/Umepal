package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.R;
import com.qiito.umepal.fragments.NavigationDrawerFragment;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.model.NavDrawerItem;

import java.util.ArrayList;

/**
 * Created by aswathy on 12/10/15.
 */
public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavigationDrawerFragment navigationDrawerFragment;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems, NavigationDrawerFragment navigationDrawerFragment){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
        this.navigationDrawerFragment = navigationDrawerFragment;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

     //   Log.d("","in navDrawerListAdapter");
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
        View drawer_view = (View)convertView.findViewById(R.id.drawer_view);

        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        txtTitle.setText(navDrawerItems.get(position).getTitle());

        if (position==0){

            drawer_view.setVisibility(View.VISIBLE);

        }

        // displaying count
        // check whether it set visible or not
        if (navDrawerItems.get(position).getCounterVisibility()) {
            //txtCount.setText(navDrawerItems.get(position).getCount());

            //txtCount.setText(TodaysParentApp.getNotificationBaseHoldersList().size()+"");

            if(DbManager.getInstance().getOpenNotificationListCount()==0){

                txtCount.setText(TodaysParentApp.getNotification_count()+"");

            }else{
                txtCount.setText(DbManager.getInstance().getOpenNotificationListCount()+"");
            }
           // txtCount.setText(TodaysParentApp.getNotificationBaseHoldersList()+"");
        } else {
            // hide the counter view
            txtCount.setVisibility(View.GONE);
        }

        return convertView;
    }

}
