package com.qiito.umepal.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qiito.umepal.R;
import com.qiito.umepal.fragments.NavigationDrawerFragment;


/**
 * Created by aswathy on 20/10/15.
 */
public class NavigationDrawerActivity extends Activity   {

    private FragmentTransaction notificationFragmentTransaction;
    private Fragment mContent;
    private NavigationDrawerFragment mItemsFragment;
    private android.app.Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationFragmentTransaction = getSupportFragmentManager().beginTransaction();
        notificationFragmentTransaction.commit();
    }

    private FragmentManager getSupportFragmentManager() {
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.container) == null) {

            fm.beginTransaction().add(R.id.container, frag).commit();
        }
        return fm;
    }
}








