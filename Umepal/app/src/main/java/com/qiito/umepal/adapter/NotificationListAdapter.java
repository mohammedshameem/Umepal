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
import android.content.Intent;

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
import com.qiito.umepal.activity.RefererPayment;
import com.qiito.umepal.fragments.Notifica;
import com.qiito.umepal.fragments.Notifications;
import com.qiito.umepal.holder.ProductNotificationBaseHolder;
import com.qiito.umepal.util.MessagePopup;
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
    //Context context;
    private StringBuilder message;
    Spanned sb1 = null;

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


        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.notifications_list_item, null);
            viewHolder.profile_image = (ImageView) convertView.findViewById(R.id.notification_profile_image);
            viewHolder.item_image = (ImageView) convertView.findViewById(R.id.notification_product_image);
            viewHolder.item_hours = (TextView) convertView.findViewById(R.id.notification_time);
            viewHolder.notificationMessage = (TextView) convertView.findViewById(R.id.notification_message);
            viewHolder.descriptionMainLayout = (LinearLayout) convertView.findViewById(R.id.description_main_layout);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        message = new StringBuilder();

        if (UtilValidate.isNotNull(notificationBaseHoldersList)) {

            if (UtilValidate.isNotNull(notificationBaseHoldersList.get(position).getNotification_type())) {
                if (notificationBaseHoldersList.get(position).getNotification_type().equals("1")) {

                    viewHolder.item_image.setVisibility(View.VISIBLE);
                    message.append("You have liked  " + notificationBaseHoldersList.get(position).getProduct().getName());
                    viewHolder.notificationMessage.setText(message);

                } else if (notificationBaseHoldersList.get(position).getNotification_type().equals("2")) {

                    //viewHolder.tvUserName.setVisibility(View.GONE);
                    viewHolder.item_image.setVisibility(View.VISIBLE);
                    message.append("You have commented on " + notificationBaseHoldersList.get(position).getProduct().getName());
                    viewHolder.notificationMessage.setText(message);

                } else if (notificationBaseHoldersList.get(position).getNotification_type().equals("3")) {

                    //viewHolder.tvUserName.setVisibility(View.GONE);
                    viewHolder.item_image.setVisibility(View.VISIBLE);
                    message.append("You have purchased " + notificationBaseHoldersList.get(position).getProduct().getName());
                    viewHolder.notificationMessage.setText(message);

                } else if (notificationBaseHoldersList.get(position).getNotification_type().equals("5")) {

                    viewHolder.item_image.setVisibility(View.GONE);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 4);
                    viewHolder.descriptionMainLayout.setLayoutParams(lp);


                    StringBuilder sb = new StringBuilder();
                    if (notificationBaseHoldersList.get(position).getReferer() != null) {

                        if (!notificationBaseHoldersList.get(position).getReferer().getFirstName().equalsIgnoreCase("")) {

                            sb.append(notificationBaseHoldersList.get(position).getReferer().getFirstName());

                            if (!notificationBaseHoldersList.get(position).getReferer().getLastName().equalsIgnoreCase("")) {
                                sb.append(" " + notificationBaseHoldersList.get(position).getReferer().getLastName());
                                //sb1 = (Spanned) TextUtils.concat(getColorString(notificationBaseHoldersList.get(position).getReferer().getFirstName()), "  ", getColorString(notificationBaseHoldersList.get(position).getReferer().getLastName()), " ", " requested a membership fee payment of $ " + notificationBaseHoldersList.get(position).getMembershipPrice());

                            }
                            sb1 = (Spanned) TextUtils.concat(getColorString(sb.toString()), "  ", "requested a membership fee payment of $ " + notificationBaseHoldersList.get(position).getMembershipPrice());
                            viewHolder.notificationMessage.setText(sb1);
                        }
                    }


                    viewHolder.notificationMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.notification_message:

                                    if (notificationBaseHoldersList.get(position).getReferer().getPaymentStatus().equalsIgnoreCase("")) {

                                        Intent newIntent = new Intent(activity, RefererPayment.class);
                                        newIntent.putExtra("notification_object", notificationBaseHoldersList.get(position));
                                        activity.startActivity(newIntent);
                                        break;


                                    } else {
                                        MessagePopup messagePopup = new MessagePopup("Error!", "You have already done payment", activity);
                                        messagePopup.show();
                                    }
                            }
                        }
                    });


                } else if (notificationBaseHoldersList.get(position).getNotification_type().equals("6")) {

                    viewHolder.item_image.setVisibility(View.GONE);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 4);
                    viewHolder.descriptionMainLayout.setLayoutParams(lp);


                    StringBuilder sb = new StringBuilder();
                    if (notificationBaseHoldersList.get(position).getReferer() != null) {

                        if (!notificationBaseHoldersList.get(position).getReferer().getFirstName().equalsIgnoreCase("")) {

                            sb.append(notificationBaseHoldersList.get(position).getReferer().getFirstName());

                            if (!notificationBaseHoldersList.get(position).getReferer().getLastName().equalsIgnoreCase("")) {
                                sb.append(" " + notificationBaseHoldersList.get(position).getReferer().getLastName());
                                //sb1 = (Spanned) TextUtils.concat(getColorString(notificationBaseHoldersList.get(position).getReferer().getFirstName()), "  ", getColorString(notificationBaseHoldersList.get(position).getReferer().getLastName()), " ", " requested a membership fee payment of $ " + notificationBaseHoldersList.get(position).getMembershipPrice());

                            }
                            sb1 = (Spanned) TextUtils.concat(getColorString(sb.toString()), "  ",notificationBaseHoldersList.get(position).getMessage());
                            viewHolder.notificationMessage.setText(sb1);
                        }
                    }

                    //viewHolder.notificationMessage.setText(notificationBaseHoldersList.get(position).getMessage());

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
                } else {
                    Picasso.with(activity).load(R.drawable.logo_splash).into(viewHolder.profile_image);
                }
            }
        }
        notifyDataSetChanged();

        Log.e("", "exiting notification adapter");
        return convertView;
    }

    private Spanned getColorString(String s) {
        return Html.fromHtml("<font color='#E6252A'>" + s + "</font>");
    }


    private class ViewHolder {

        private ImageView item_image;
        private ImageView profile_image;
        private LinearLayout descriptionMainLayout;
        private TextView notificationMessage;

        private TextView tvUserName;

        private TextView item_hours;

    }

}
