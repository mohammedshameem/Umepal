package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.activity.RefererPayment;
import com.qiito.umepal.holder.UserObjectHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vivek on 2/6/16.
 */
public class NewRefereeAdapter extends BaseAdapter {
    // NewRefereeFragment newRefereeFragmentoObject=new NewRefereeFragment();
    private Activity activity;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;
    Context context;
    private List<UserObjectHolder> newRefereeList;

    public NewRefereeAdapter(Activity activity, List<UserObjectHolder> newRefereeList) {
        //this.newRefereeFragmentoObject = newRefereeFragmentoObject;
        this.activity = activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       //New Code
        //inflater = LayoutInflater.from(this.context);

        this.newRefereeList = newRefereeList;
    }

    @Override
    public int getCount() {
        return newRefereeList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.new_reffree_listitem, null);
            //New Code
            //convertView = inflater.inflate(R.layout.layout_list_item, parent, false);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.pic);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.confirmButton = (Button) convertView.findViewById(R.id.confirmButton);
            viewHolder.confirmedButton = (Button)convertView.findViewById(R.id.confirmed_button);

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (UtilValidate.isNotNull(newRefereeList.get(position).getProfilePic())) {
            if (!newRefereeList.get(position).getProfilePic().equals("")) {
                Picasso.with(activity)
                        .load(newRefereeList.get(position).getProfilePic())
                        .placeholder(R.drawable.logo_splash)
                        .error(R.drawable.logo_splash)
                        .into(viewHolder.pic);
            }

        }

        if (UtilValidate.isNotNull(newRefereeList.get(position).getFirstName())) {
            viewHolder.name.setText(newRefereeList.get(position).getFirstName());
        }
        //lastName

        if (UtilValidate.isNotNull(newRefereeList.get(position).getPaymentStatus())) {
            if (newRefereeList.get(position).getPaymentStatus() != null) {
                if (!newRefereeList.get(position).getPaymentStatus().equalsIgnoreCase( "")) {
                    Log.e("PAYMENT STATUS", newRefereeList.get(position).getPaymentStatus());
                    viewHolder.confirmButton.setVisibility(View.GONE);
                    viewHolder.confirmedButton.setVisibility(View.VISIBLE);

                }else {
                    viewHolder.confirmButton.setVisibility(View.VISIBLE);
                    viewHolder.confirmedButton.setVisibility(View.GONE);
                }
            }


        }

        viewHolder.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.confirmButton:
                        if (newRefereeList.get(position).getPaymentStatus().equalsIgnoreCase("")){
                            Intent refereeIntent=new Intent(activity, RefererPayment.class);
                            refereeIntent.putExtra("referee_object",newRefereeList.get(position));
                            activity.startActivity(refereeIntent);
                        }
                        break;
                }

            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView pic;
        TextView name;
        Button confirmButton;
        Button confirmedButton;

    }
}
