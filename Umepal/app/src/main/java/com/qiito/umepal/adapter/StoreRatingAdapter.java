package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.StoreRating;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiya on 8/10/15.
 */
public class StoreRatingAdapter extends BaseAdapter {
    private Activity activity;
    private List<StoreRating> store_ratings = new ArrayList<>();
    private LayoutInflater inflater;
    private ViewHolder viewholder;

    public StoreRatingAdapter(Activity activity,List<StoreRating> store_ratings){
        this.store_ratings.clear();
        Log.e("","in constructor");
        this.activity = activity;
        this.store_ratings = store_ratings;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return store_ratings.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        Log.e("","size>>>"+store_ratings.size());
        Log.e("","position>>>"+position);

        if (view == null){

            viewholder = new ViewHolder();

            view = inflater.inflate(R.layout.rating_list, null);
            viewholder.userName = (TextView)view.findViewById(R.id.rating_user_name);
            viewholder.userImage = (ImageView)view.findViewById(R.id.rating_user_image);
            viewholder.ratingBar = (RatingBar)view.findViewById(R.id.user_ratings);
            viewholder.ratingCreatedDate = (TextView)view.findViewById(R.id.rating_created_date);

            view.setTag(viewholder);
        }else {
            Log.e("","view not null");
            viewholder = (ViewHolder)view.getTag();
        }

        if (UtilValidate.isNotNull(store_ratings.get(position).getUser_name())){
            Log.e("name>>>",store_ratings.get(position).getUser_name());
            viewholder.userName.setText(store_ratings.get(position).getUser_name());
        }

        if (store_ratings.get(position).getUser_img()!= ""){


            Log.e("image not null>>",store_ratings.get(position).getUser_img());

            Picasso.with(activity).load(store_ratings.get(position).getUser_img()).placeholder(R.drawable.logo_splash).error(R.drawable.logo_splash).into(viewholder.userImage);
        }else{
            Picasso.with(activity).load(R.drawable.logo_splash).into(viewholder.userImage);
        }

        if (UtilValidate.isNotNull(store_ratings.get(position).getRating())){

            Log.e("","rating>>"+store_ratings.get(position).getRating());
            viewholder.ratingBar.setRating(Float.parseFloat(store_ratings.get(position).getRating()));
        }
        if (UtilValidate.isNotNull(store_ratings.get(position).getCreated())){
            viewholder.ratingCreatedDate.setText(store_ratings.get(position).getCreated());
        }

        return view;
    }

    private class ViewHolder{

        private TextView userName;
        private ImageView userImage;
        private RatingBar ratingBar;
        private TextView ratingCreatedDate;



    }
}
