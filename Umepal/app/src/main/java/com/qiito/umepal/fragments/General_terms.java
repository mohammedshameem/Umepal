package com.qiito.umepal.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.qiito.umepal.R;


/**
 * Created by abin on 12/8/15.
 */



public class General_terms extends Fragment {
    private MenuItem mSearchAction;
    private boolean disableButtonFlag = false;
    private View content;
    private TextView term_condition_text;
    private WebView mWebView = null;

    public General_terms() {

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

        content = inflater.inflate(R.layout.general_terms, container, false);

        initview();

        term_condition_text.setMovementMethod(LinkMovementMethod.getInstance());
        term_condition_text.setText(Html.fromHtml(getResources().getString(R.string.terms_condition)));

        return content;
    }

    public void initview() {

       term_condition_text = (TextView)content.findViewById(R.id.term_condition_text);
       // mWebView = (WebView)content.findViewById(R.id.main_webview);
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

                return true;
        }


        return super.onOptionsItemSelected(item);
    }



}
