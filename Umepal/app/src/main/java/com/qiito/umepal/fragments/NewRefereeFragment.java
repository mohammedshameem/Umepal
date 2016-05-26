package com.qiito.umepal.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.qiito.umepal.holder.ListRefereeBaseHolder;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.UserManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

/**
 * Created by abin on 26/5/16.
 */
public class NewRefereeFragment extends Fragment {

    private View content;
    private ListRefereesCallBack listRefereesCallBack;
    private String session;
    private ListRefereeBaseHolder listRefereeBaseHolder;


    public NewRefereeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.new_referee_page, container, false);
        listRefereesCallBack=new ListRefereesCallBack();
        listRefereeBaseHolder = new ListRefereeBaseHolder();
        session = DbManager.getInstance().getCurrentUserDetails().getSession_id();

        UserManager.getInstance().ListReferees(getActivity(),session,listRefereesCallBack);


        return content;

    }

    public class ListRefereesCallBack implements AsyncTaskCallBack{

        @Override
        public void onFinish(int responseCode, Object result) {
            listRefereeBaseHolder = (ListRefereeBaseHolder)result;

            if(listRefereeBaseHolder.getStatus().equalsIgnoreCase("success")){
                Toast.makeText(getActivity(),"success",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(),"fail",Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFinish(int responseCode, String result) {
            Toast.makeText(getActivity(),"fail",Toast.LENGTH_SHORT).show();

        }
    }
}
