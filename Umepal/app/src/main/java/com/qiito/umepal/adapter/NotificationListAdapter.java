package com.qiito.umepal.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.fragments.Notifica;
import com.qiito.umepal.fragments.Notifications;
import com.qiito.umepal.holder.ProductNotificationBaseHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationListAdapter extends BaseAdapter {

    private Notifications notifications = new Notifications();
    private Activity activity;
    private LayoutInflater inflater;
    private List<ProductNotificationBaseHolder> notificationBaseHoldersList;
    private ViewHolder viewHolder;
    private ImageView remove;
    String sessionId;
    Context context;
    private StringBuilder message;
    Spanned sb=null,sb1=null;
    public NotificationListAdapter(Activity activity, List<ProductNotificationBaseHolder> notificationBaseHoldersList, Notifica notifica) {

        this.notificationBaseHoldersList = notificationBaseHoldersList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return notificationBaseHoldersList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // Log.e("","in notification adapter"+ TodaysParentApp.getNotification_count());

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.notifications_list_item, null);
            viewHolder.profile_image = (ImageView) convertView.findViewById(R.id.notification_profile_image);
            viewHolder.item_image = (ImageView) convertView.findViewById(R.id.notification_product_image);
            viewHolder.item_hours = (TextView) convertView.findViewById(R.id.notification_time);
            viewHolder.notificationMessage = (TextView) convertView.findViewById(R.id.notification_message);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        message = new StringBuilder();

        if (UtilValidate.isNotNull(notificationBaseHoldersList)) {

            if (UtilValidate.isNotNull(notificationBaseHoldersList.get(position).getNotification_type())) {
                if (notificationBaseHoldersList.get(position).getNotification_type().equals("1")) {
                    message.append("You have liked  " + notificationBaseHoldersList.get(position).getProduct().getName());
                    viewHolder.notificationMessage.setText(message);
                } else if (notificationBaseHoldersList.get(position).getNotification_type().equals("2")) {
                    message.append("You have commented on " + notificationBaseHoldersList.get(position).getProduct().getName());
                    viewHolder.notificationMessage.setText(message);
                } else if (notificationBaseHoldersList.get(position).getNotification_type().equals("3")) {
                    message.append("You have purchased " + notificationBaseHoldersList.get(position).getProduct().getName());
                    viewHolder.notificationMessage.setText(message);
                } else if (notificationBaseHoldersList.get(position).getNotification_type().equals("5")) {
                    // message.append("You have purchased " + notificationBaseHoldersList.get(position).getProduct().getName());
                    viewHolder.profile_image.setVisibility(View.GONE);
//                    viewHolder.tvUserName.setVisibility(View.VISIBLE);

                    sb= (Spanned) TextUtils.concat(getColorString(notificationBaseHoldersList.get(position).getUser_firstname()),"\\s",getColorString( notificationBaseHoldersList.get(position).getUser_lastname()),"\\s"," requested a membership fee payment of $ " + notificationBaseHoldersList.get(position).getMembershipPrice());
                    //sb1= (Spanned) TextUtils.concat(getMessageColorString(" requested a membership fee payment of $ " + notificationBaseHoldersList.get(position).getMembershipPrice()));
                    // Span to set text color to some RGB value




                    //viewHolder.tvUserName.setText(notificationBaseHoldersList.get(position).getUser_firstname() + " " + notificationBaseHoldersList.get(position).getUser_lastname());
                   /* message.append(sb);
                    message.append(sb1);*/
                    viewHolder.notificationMessage.setText(sb);
                    viewHolder.notificationMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.notification_message:
                                    // Intent newIntent=new Intent(activity,PaymentActivity.class);
                                    //context.startActivity(newIntent);

                                    break;
                            }
                        }
                    });


/*// Span to make text bold
                    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

// Set the text color for first 4 characters
                    sb.setSpan(fcs, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

// make them also bold
                    sb.setSpan(bss, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    yourTextView.setText(sb);*/

                }


                else if (notificationBaseHoldersList.get(position).getNotification_type().equals("6")) {
                    message.append("You have purchased " + notificationBaseHoldersList.get(position).getProduct().getName());
                    viewHolder.notificationMessage.setText(message);
                   /* viewHolder.tvUserName.setVisibility(View.VISIBLE);
                    viewHolder.tvUserName.setText(notificationBaseHoldersList.get(position).getUser_firstname() + " " + notificationBaseHoldersList.get(position).getUser_lastname());*/
                    message.append("done a membership fee payment");
                }


            } else {

                viewHolder.notificationMessage.setText(notificationBaseHoldersList.get(position).getDescription());
            }

            if (UtilValidate.isNotNull(notificationBaseHoldersList.get(position).getDcraetedDate())) {
                viewHolder.item_hours.setText(notificationBaseHoldersList.get(position).getDcraetedDate());

            }
            if (UtilValidate.isNotNull(notificationBaseHoldersList.get(position).getProduct().getImage()) &&
                    UtilValidate.isNotEmpty(notificationBaseHoldersList.get(position).getProduct().getImage())) {

                if (!notificationBaseHoldersList.get(position).getProduct().getImage().equals("")) {

                    Picasso.with(activity).load(notificationBaseHoldersList.get(position).getProduct().getImage()).into(viewHolder.item_image);
                } else {
                    Picasso.with(activity).load(R.drawable.logo_splash).into(viewHolder.item_image);
                }
            } else {
                Picasso.with(activity).load(R.drawable.logo_splash).into(viewHolder.item_image);
            }


            if (UtilValidate.isNotNull(notificationBaseHoldersList.get(position).getUser_profilePic())) {
                Log.e("pic", "notification" + notificationBaseHoldersList.get(position).getUser_profilePic());
                if (!notificationBaseHoldersList.get(position).getUser_profilePic().equalsIgnoreCase("")) {
                    Picasso.with(activity).load(notificationBaseHoldersList.get(position).getUser_profilePic()).into(viewHolder.profile_image);
                }
            } else {
                Picasso.with(activity).load(R.drawable.logo_splash).into(viewHolder.profile_image);
            }
        }
        notifyDataSetChanged();

        Log.e("", "exiting notification adapter");
        return convertView;
    }

    private Spanned getColorString(String s) {
        return Html.fromHtml("<font color='#E6252A'>" + s + "</font>");
    }
    private Spanned getMessageColorString(String s) {
        return Html.fromHtml("<font color='#000000'>" + s + "</font>");
    }



    private class ViewHolder {

        private ImageView item_image;
        private ImageView profile_image;
        private TextView profile_name;
        private TextView item_name;
        private TextView type;
        private TextView notificationMessage;
       // private TextView tvUserName;
        private TextView item_quantity_number;
        private TextView item_individual_price_number;
        private TextView item_total_price;
        private TextView item_desc;
        private LinearLayout item_quantity_layout;
        private LinearLayout item_individual_price_layout;
        private TextView item_date;
        private LinearLayout linearfull;
        private TextView item_hours;
        private ImageView remove;
    }

}
