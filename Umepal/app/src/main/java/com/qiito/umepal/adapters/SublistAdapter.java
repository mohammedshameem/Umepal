package com.qiito.umepal.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qiito.umepal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 8/9/15.
 */



    public class SublistAdapter extends BaseAdapter {

        private List<String> subList = new ArrayList<String>();
        private Activity activity;
        private LayoutInflater inflater;
        private ViewHolder viewholder;
        private String message;

        public SublistAdapter(Activity activity, List<String> subList) {
            // TODO Auto-generated constructor stub
            this.activity = activity;
            this.subList = subList;
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return subList.size();
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

                convertView = inflater.inflate(R.layout.drawersublist, null);
viewholder.generalterms=(TextView)convertView.findViewById(R.id.generalterms);
                viewholder.contactus=(TextView)convertView.findViewById(R.id.contactus);

                convertView.setTag(viewholder);
            }else {
                viewholder = (ViewHolder)convertView.getTag();
            }



            return convertView;
        }

        public class ViewHolder{
            private ListView sublist;
            private TextView contactus;
            private TextView generalterms;
        }
    }




