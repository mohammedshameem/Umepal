package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ReviewObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 22/9/15.
 */
public class ReviewAdapter extends BaseAdapter{

    List<ReviewObject>reviewlist=new ArrayList<ReviewObject>();

    private Activity activity;
    private LayoutInflater inflater;
    private ViewHolder viewholder;

    public ReviewAdapter(Activity activity, List<ReviewObject> reviewList) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.reviewlist = reviewList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return reviewlist.size();
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

            view = inflater.inflate(R.layout.review_list_item, null);
            viewholder.profile_name=(TextView)view.findViewById(R.id.profile_name);
            viewholder.like_comment_txtview=(TextView)view.findViewById(R.id.like_comment_txtview);
            viewholder.item_hours=(TextView)view.findViewById(R.id.item_hours);
            viewholder.profile_image=(ImageView)view.findViewById(R.id.profile_image);




            view.setTag(viewholder);
        }else {
            viewholder = (ViewHolder)view.getTag();
        }


        if(UtilValidate.isNotNull(reviewlist.get(position).getUserFirstName()))
        {
            viewholder.profile_name.setText(reviewlist.get(position).getUserFirstName()+" "+reviewlist.get(position).getUserLastName());
        } else
        {viewholder.profile_name.setText("Unknown");}

       if(UtilValidate.isNotNull(reviewlist.get(position).getReview())){
           viewholder.like_comment_txtview.setText(reviewlist.get(position).getReview());
       }
        if(UtilValidate.isNotNull(reviewlist.get(position).getUserImage()) && UtilValidate.isNotEmpty(reviewlist.get(position).getUserImage()))
        {
            if(!reviewlist.get(position).getUserImage().equals(""))
            {
                Picasso.with(activity).load(reviewlist.get(position).getUserImage()).into(viewholder.profile_image);
            }
            else
            {Picasso.with(activity).load(R.drawable.logo_splash).into(viewholder.profile_image);}
        } else
        {Picasso.with(activity).load(R.drawable.logo_splash).into(viewholder.profile_image);}




        if(UtilValidate.isNotNull(reviewlist.get(position).getCreated())){
            if(reviewlist.get(position).getCreated()!="") {
               String time = reviewlist.get(position).getCreated();
                viewholder.item_hours.setText(time);
            }
        }



        return view;
    }

    private class ViewHolder {

        private ImageView profile_image;
        private TextView profile_name;
        private TextView like_comment_txtview;
        private TextView item_hours;


    }
}
