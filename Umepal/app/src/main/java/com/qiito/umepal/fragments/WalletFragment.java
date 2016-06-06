package com.qiito.umepal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.qiito.umepal.adapter.WalletExpandableListViewAdapter;
import com.qiito.umepal.adapter.WalletListViewAdapter;
import com.qiito.umepal.holder.RebateDetailsHolder;
import com.qiito.umepal.holder.RebateListHolder;
import com.qiito.umepal.holder.WalletBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.WalletManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shiya on 30/5/16.
 */
public class WalletFragment extends Fragment {

    private View content;
    private TextView totalAvailableBalance;
    private TextView totalLeBadgerBalance;
    private LinearLayout topUpLayout;
    private LinearLayout upgradeMembershipLayout;
    private LinearLayout rebatesLayout;
    private LinearLayout commissionsLayout;
    private View rebatesView;
    private View commissionsView;
    private ExpandableListView expandableListView;
    private ListView listView;
    private List<RebateListHolder> listDataHeader;
    private HashMap<List<RebateListHolder>, List<RebateDetailsHolder>> listDataChild;
    private WalletExpandableListViewAdapter walletExpandableListViewAdapter;
    private WalletListViewAdapter walletListViewAdapter;
    private List<String> price;
    private WalletCallBack walletCallBack = new WalletCallBack();
    private WalletBaseHolder walletBaseHolder = new WalletBaseHolder();
    private List<RebateListHolder> rebateList = new ArrayList<>();
    private List<RebateDetailsHolder> rebateDetails = new ArrayList<>();
    private HashMap<List<RebateListHolder>, List<RebateDetailsHolder>> rebateDataPrepared;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.wallet_layout, container, false);
        initViews();

        WalletManager.getInstance().getWalletData(getActivity(), DbManager.getInstance().getCurrentUserDetails().getSession_id(), walletCallBack);
        expandableListView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        rebatesLayout.setOnClickListener(rebateClickListener);
        commissionsLayout.setOnClickListener(commissionClickListener);

        //prepareListData();
        /*walletExpandableListViewAdapter = new WalletExpandableListViewAdapter(getActivity(), listDataHeader, listDataChild);
        expandableListView.setAdapter(walletExpandableListViewAdapter);*/

        price = new ArrayList<>();

        price.add("$ 1.00");
        price.add("$ 5.50");
        price.add("$ 2.80");

        walletListViewAdapter = new WalletListViewAdapter(getActivity(), price);
        listView.setAdapter(walletListViewAdapter);
        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initViews() {
        totalAvailableBalance = (TextView) content.findViewById(R.id.total_available_balance);
        totalLeBadgerBalance = (TextView) content.findViewById(R.id.total_lebadger_balance);
        topUpLayout = (LinearLayout) content.findViewById(R.id.topup_layout);
        upgradeMembershipLayout = (LinearLayout) content.findViewById(R.id.upgrade_membership_layout);
        rebatesLayout = (LinearLayout) content.findViewById(R.id.rebates_layout);
        commissionsLayout = (LinearLayout) content.findViewById(R.id.commissions_layout);
        rebatesView = (View) content.findViewById(R.id.rebates_view);
        commissionsView = (View) content.findViewById(R.id.commissions_view);
        expandableListView = (ExpandableListView) content.findViewById(R.id.expandable_listview);
        listView = (ListView) content.findViewById(R.id.listview);

    }

    View.OnClickListener rebateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rebatesView.setVisibility(View.VISIBLE);
            commissionsView.setVisibility(View.GONE);
            expandableListView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    };

    View.OnClickListener commissionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rebatesView.setVisibility(View.GONE);
            commissionsView.setVisibility(View.VISIBLE);
            expandableListView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

        }
    };

    /*private void prepareListData() {
        listDataHeader = new ArrayList<RebateListHolder>();
        listDataChild = new HashMap<List<RebateListHolder>, List<RebateDetailsHolder>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }*/

    private class WalletCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {

            walletBaseHolder = (WalletBaseHolder) result;
            if (walletBaseHolder != null) {
                if (walletBaseHolder.getStatus() != null) {

                    if (walletBaseHolder.getStatus().equalsIgnoreCase("success")) {

                        if (walletBaseHolder.getData() != null) {

                            if (walletBaseHolder.getData().getTotal_credit_amount() != null) {
                                if (walletBaseHolder.getData().getTotal_credit_amount() != "") {
                                    totalAvailableBalance.setText(getString(R.string.dollar) + " " + walletBaseHolder.getData().getTotal_credit_amount());
                                } else {
                                    totalAvailableBalance.setText("$ 00.00");
                                }
                            }

                            if (walletBaseHolder.getData().getLedger_amount() != null) {

                                if (walletBaseHolder.getData().getLedger_amount() != "") {

                                    totalLeBadgerBalance.setText(getString(R.string.dollar) + " " + walletBaseHolder.getData().getLedger_amount());
                                }
                            }

                            if (walletBaseHolder.getData().getRebate() != null) {

                                if (!walletBaseHolder.getData().getRebate().isEmpty()) {

                                    prepareData();
                                    walletExpandableListViewAdapter = new WalletExpandableListViewAdapter(getActivity(), rebateList,rebateDataPrepared);
                                    expandableListView.setAdapter(walletExpandableListViewAdapter);


                                    expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                                        @Override
                                        public void onGroupExpand(int groupPosition) {

                                        }
                                    });

                                }
                            }


                        }
                    }
                }
            }
        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }


    private void prepareData() {

        rebateDataPrepared = new HashMap<>();

        for (int i = 0; i < walletBaseHolder.getData().getRebate().size(); i++) {
            rebateList.add(walletBaseHolder.getData().getRebate().get(i));
        }

        for (int i = 0; i < walletBaseHolder.getData().getRebate().size(); i++) {
            for (int j =0 ; j<walletBaseHolder.getData().getRebate().get(i).getDetails().size();j++)
            {
                rebateDetails.add(walletBaseHolder.getData().getRebate().get(i).getDetails().get(j));
            }

            rebateDataPrepared.put(rebateList , rebateDetails);
        }
    }
}
