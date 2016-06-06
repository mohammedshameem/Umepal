package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
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

        this.newRefereeList=newRefereeList;
    }

    @Override
    public int getCount() {
        return newRefereeList.size() ;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.new_reffree_listitem, null);
            //New Code
            //convertView = inflater.inflate(R.layout.layout_list_item, parent, false);
            viewHolder.pic= (ImageView) convertView.findViewById(R.id.pic);
            viewHolder.name= (TextView) convertView.findViewById(R.id.name);
            viewHolder.confirmButton= (Button) convertView.findViewById(R.id.confirmButton);
            convertView.setTag(viewHolder);


        }
     else {
        viewHolder = (ViewHolder) convertView.getTag();
    }

        if (UtilValidate.isNotNull(newRefereeList.get(position).getProfilePic())){
            if(!newRefereeList.get(position).getProfilePic().equals(""))
            {
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
      /* if (UtilValidate.isNotNull(newRefereeList.get(position).getLastName())){
            viewHolder.name.setText(newRefereeList.get(position).getLastName());
        }*/
        if(UtilValidate.isNotNull(newRefereeList.get(position).getPaymentStatus())){
            String paymentStatus=newRefereeList.get(position).getPaymentStatus();
            if (paymentStatus!=null){
                Log.d("PAYMENT STATUS",paymentStatus );
                viewHolder.confirmButton.setVisibility(View.VISIBLE);
            }
            else{
                viewHolder.confirmButton.setVisibility(View.INVISIBLE);
            }


        }

        return convertView;
    }
    private class ViewHolder {
        ImageView pic;
        TextView name;
        Button confirmButton;

    }
}
