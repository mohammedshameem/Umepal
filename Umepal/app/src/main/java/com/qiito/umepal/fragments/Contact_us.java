package com.qiito.umepal.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.holder.UserObjectHolder;

/**
 * Created by abin on 12/8/15.
 */



public class Contact_us extends Fragment {
    private MenuItem mSearchAction;
    private boolean disableButtonFlag = false;
private View content;
    private EditText mail_subject;
    private EditText mail_msg;
    private Button btn_clear_mail;
    private Button btn_send_mail;
    private EditText from_mailid;
    UserObjectHolder userObjectHolder;
    private TextView mobilenumber;
    private ImageView callimg;
    private TextView mail;
    UserBaseHolder userBaseHolder;

    public Contact_us() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("", "IN ONRESUME");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        content = inflater.inflate(R.layout.contact_us, container, false);
        initView();
        initManager();
        callimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + mobilenumber.getText().toString()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "contact@qiito.com.sg", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject : ");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body : ");
                startActivity(Intent.createChooser(emailIntent, "Contact using"));
            }
        });



        return content;
    }

    private void initManager() {
        userObjectHolder=new UserObjectHolder();
    }

    private void initView() {
        mobilenumber= (TextView)content.findViewById(R.id.mobilenumber);
        mail = (TextView)content.findViewById(R.id.mailid);
        callimg = (ImageView) content.findViewById(R.id.callimg);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.e("","in onCreateOptionsMenu");
        menu.removeItem(R.id.action_search);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.e("","in onOptionsItemSelected");
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                // handleMenuSearch();
                //findViewById(R.id.action_search).setVisibility();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }



}
