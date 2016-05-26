package com.qiito.umepal.fragments;
/**
 * Created by abin on 5/8/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.R;
import com.qiito.umepal.adapter.ProductAdapter;
import com.qiito.umepal.adapter.ProductCategoryAdapter;
import com.qiito.umepal.dao.CurrentlyLoggedUserDAO;
import com.qiito.umepal.holder.Category;
import com.qiito.umepal.holder.ProductCategoryBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.ProductCategoryManager;
import com.qiito.umepal.managers.ProductManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import java.util.ArrayList;
import java.util.List;

import static com.qiito.umepal.Utilvalidate.NetChecker.isConnected;


public class SlidingFragment extends Fragment {

    private MenuItem mSearchAction;
    private boolean disableButtonFlag = false;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private ProductCategoryAdapter productCategoryAdapter;
    private ProductCategoryBaseHolder productCategoryBaseHolder;
    private MyPagerAdapter adapter;
    private final Handler handler = new Handler();
    private ProductManager productManager;
    private ProductAdapter productAdapter;
    private ProductCategorycallback productCategorycallback;
    private GridView gridView;
    private String session;
    private int requestCode = 400;
    private int subcategory = 0;
    private int offset = 0;
    private int limit = 10;
    private EditText search;
    private String searchword;
    private Allitems allitems;

    private AlertDialog.Builder builder;
    private AlertDialog alert;

    private View content;
    private Dialog dialogTransparent;
    private View progressview;
    private ProgressDialog dialog;
    private ImageView searchicon;
    private List<Category> categorynames = new ArrayList<>();


    public SlidingFragment() {


    }


    @Override
    public void onResume() {


        super.onResume();
        Log.e("", "IN ONRESUME");

        String sessionId = DbManager.getInstance().getSessionId();
        session = CurrentlyLoggedUserDAO.getInstance().getSessionId();

       /* dialogTransparent = new Dialog(getActivity(), android.R.style.Theme_Black);
        progressview = LayoutInflater.from(getActivity()).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);
        dialogTransparent.show();*/

        ProductCategoryManager.getInstance().getProductCategory(getActivity(), sessionId, productCategorycallback, requestCode);

        //dialogTransparent.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        content = inflater.inflate(R.layout.activity_pager, container, false);
        String sessionId = DbManager.getInstance().getSessionId();
        pager = (ViewPager) content.findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) content.findViewById(R.id.tabs);
        search = (EditText) container.findViewById(R.id.search_text);
        searchicon = (ImageView) container.findViewById(R.id.searchicon);
        initManager();

        dialogTransparent = new Dialog(getActivity(), android.R.style.Theme_Black);
        progressview = LayoutInflater.from(getActivity()).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);
        dialogTransparent.show();

        session = CurrentlyLoggedUserDAO.getInstance().getSessionId();

        ProductCategoryManager.getInstance().getProductCategory(getActivity(), sessionId, productCategorycallback, requestCode);

        //dialogTransparent.dismiss();
        return content;
    }

    private void initManager() {
        productCategorycallback = new ProductCategorycallback();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public class ProductCategorycallback implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {

           /* if(dialog != null&& dialog.isShowing()){
            dialog.dismiss();
            }*/
            //dialogTransparent.dismiss();

            Log.e("","dismissing dialog..SF1");

            productCategoryBaseHolder = (ProductCategoryBaseHolder) result;
            if (productCategoryBaseHolder.getStatus().equals("success")) {
                dialogTransparent.dismiss();
                if (productCategoryBaseHolder.getData() != null) {
                    if (productCategoryBaseHolder.getData().getCategory() != null) {

                        //dialogTransparent.dismiss();
                        Log.e("", "dismissing dialog..SF2");

                        TodaysParentApp.setCategory(null);
                        TodaysParentApp.setCategory(productCategoryBaseHolder.getData().getCategory());

                        if(pager.getAdapter()==null) {
                            adapter = new MyPagerAdapter(getFragmentManager());
                            pager.setAdapter(adapter);
                            tabs.setViewPager(pager);
                        }
                    }
                }
                Log.e("", "Call Back Success");
                //dialogTransparent.dismiss();
            } else {

                Toast.makeText(getActivity(), "server problem", Toast.LENGTH_SHORT).show();
                /*if(dialogTransparent != null&& dialogTransparent.isShowing()) {

                }*/
                // dialog.dismiss();
                dialogTransparent.dismiss();
                Log.e("", "CAll BAck error");

            }
        }

        @Override
        public void onFinish(int responseCode, String result) {

           /* if(dialogTransparent != null&& dialogTransparent.isShowing()) {

            }*/
            // dialog.dismiss();
            dialogTransparent.dismiss();
            if (isConnected(getActivity())) {

            } else {

                builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Network Unavailable!!");
                builder.setCancelable(true);
                builder.setPositiveButton("Retry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                                if (isConnected(getActivity())) {

                                } else {

                                }

                            }

                        });
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // dialogTransparent.show();
                                dialog.cancel();
                            }
                        });

                alert = builder.create();
                alert.show();
            }
        }
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
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mSearchAction = menu.findItem(R.id.action_search);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (disableButtonFlag) {
            menu.findItem(R.id.action_search).setEnabled(false);

        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.e("", "in onOptionsItemSelected");
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                // handleMenuSearch();

                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<Category> TITLES = TodaysParentApp.getCategory();


       /* public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }*/

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

            Category latest=new Category();
            latest.setCategory_name("Latest");
            latest.setCategory_id(0);
            TITLES.add(0, latest);
        }

        public MyPagerAdapter(Object manager) {
            super((FragmentManager) manager);
        }

        @Override
        public CharSequence getPageTitle(int position) {


            return TITLES.get(position).getCategory_name();
        }

        @Override
        public int getCount() {
            return TITLES.size();
        }

        @Override
        public Fragment getItem(int position) {


            allitems = new Allitems(TITLES.get(position).getCategory_id(), getActivity(), 0);

            Bundle bundle = new Bundle();
            bundle.putInt("category_id", TITLES.get(position).getCategory_id());
            Log.e("category id ", "category id" + TITLES.get(position).getCategory_id());
            allitems.setArguments(bundle);

            return allitems;

        }

        @Override
        public int getItemPosition(Object object) {

            return POSITION_NONE;
        }


    }


}