package com.qiito.umepal.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.adapter.ItemsListAdapter;
import com.qiito.umepal.adapters.MyPurchasesAdapter;
import com.qiito.umepal.holder.PurchasedItems;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.UserManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 1/9/15.
 */


public class OrderHistory extends Fragment {

    private int offset = 0;
    private OrderHistoryCallback orderHistoryCallback;
    private ListView orderHistoryList;
    private TextView noOrder;
    private List<PurchasedItems> orderlist = new ArrayList<>();
    private Dialog dialogTransparent;
    private View progressview;
    private ItemsListAdapter itemsListAdapter;
    private MyPurchasesAdapter myPurchasesAdapter;
    private LinearLayout noOrderHistory;
    private LinearLayout orderHistory;

    public OrderHistory() {
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

        View content = inflater.inflate(R.layout.orderhistory, container, false);
        orderHistoryList = (ListView) content.findViewById(R.id.order_history_list);
        noOrder = (TextView) content.findViewById(R.id.order_empty_text);
        noOrderHistory = (LinearLayout)content.findViewById(R.id.no_order_history);
        orderHistory = (LinearLayout)content.findViewById(R.id.order_history);
        orderHistoryList.setEmptyView(content.findViewById(R.id.order_empty_text));


        initManager();
        String sessionId = DbManager.getInstance().getSessionId();

        dialogTransparent = new Dialog(getActivity(), android.R.style.Theme_Black);
        dialogTransparent.getWindow().setGravity(Gravity.BOTTOM);
        progressview = LayoutInflater.from(getActivity()).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);
        dialogTransparent.show();

        UserManager.getInstance().userProfile(getActivity(), sessionId, offset, orderHistoryCallback);
        return content;
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
        menu.removeItem(R.id.action_search);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                // handleMenuSearch();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initManager() {
        orderHistoryCallback = new OrderHistoryCallback();
    }

    public class OrderHistoryCallback implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {
            UserBaseHolder userBaseHolder = (UserBaseHolder) result;

            if (userBaseHolder.getStatus().equals("success")) {

                dialogTransparent.dismiss();
                if (userBaseHolder.getData() != null) {

                    if (userBaseHolder.getData().getUser().getPurchased_items() != null) {

                        orderlist.clear();
                        orderlist.addAll(userBaseHolder.getData().getUser().getPurchased_items());

                        if (orderlist.size() != 0) {
                            orderHistory.setVisibility(View.VISIBLE);
                            noOrderHistory.setVisibility(View.GONE);
                            myPurchasesAdapter = new MyPurchasesAdapter(getActivity(), orderlist);
                            orderHistoryList.setAdapter(myPurchasesAdapter);
                            myPurchasesAdapter.notifyDataSetChanged();

                            /*orderHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                                    Intent productdetail = new Intent(getActivity(), ProductDetails.class);
                                    productdetail.putExtra("productid", orderlist.get(position).getId());
                                    Log.e(" order Product id", "id;;;;" + orderlist.get(position).getId());

                                    productdetail.putExtra("PURCHASES", 1);
                                    startActivity(productdetail);
                                }
                            });*/
                        } else {
                            Log.e("order ", "list empty");
                            orderHistory.setVisibility(View.GONE);
                            noOrderHistory.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.e("purchased item ", "null");
                        orderHistory.setVisibility(View.GONE);
                        noOrderHistory.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.e("Data ", "null");
                    orderHistory.setVisibility(View.GONE);
                    noOrderHistory.setVisibility(View.VISIBLE);
                }
            } else {
                Log.e("", "CAll BAck error");
            }
        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

}




