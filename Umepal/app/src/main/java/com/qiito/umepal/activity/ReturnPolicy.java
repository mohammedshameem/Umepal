package com.qiito.umepal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiito.umepal.R;


/**
 * Created by shiya on 14/10/15.
 */
public class ReturnPolicy extends Activity {

    private TextView pageheading;
    private ImageView backIcon;
    private TextView return_policy_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.return_policy);
        initViews();

        pageheading.setVisibility(View.VISIBLE);
        pageheading.setText("Return Policy");

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        return_policy_text.setText(Html.fromHtml(getResources().getString(R.string.return_policy)));
    }

    private  void initViews(){
        pageheading = (TextView)findViewById(R.id.page_heading);
        backIcon = (ImageView)findViewById(R.id.back_menu_icon);
        return_policy_text = (TextView)findViewById(R.id.return_policy_text);
    }
}
