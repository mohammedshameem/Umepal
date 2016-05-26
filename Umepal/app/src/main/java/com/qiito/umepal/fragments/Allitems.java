package com.qiito.umepal.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.adapter.ProductAdapter;
import com.qiito.umepal.holder.ProductBaseHolder;
import com.qiito.umepal.holder.ProductLikeHolder;
import com.qiito.umepal.holder.ProductObject;
import com.qiito.umepal.holder.ShoppingCartResponseHolder;
import com.qiito.umepal.holder.UserObjectHolder;
import com.qiito.umepal.listeners.AdapterViewItemListener;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.ProductLikeManager;
import com.qiito.umepal.managers.ProductManager;
import com.qiito.umepal.timer.UpdateClockTimer;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by abin on 21/8/15.
 */
public class Allitems extends Fragment {

    private static final String TAG = Allitems.class.getSimpleName();

    ProductBaseHolder productBaseHolder = new ProductBaseHolder();


    private MenuItem mSearchAction;
    private boolean disableButtonFlag = false;
    private ProductCallBack productCallBack;
    private UserObjectHolder userObjectHolder;
    private ProductAdapter productAdapter;
    //private LikeCallBack likeCallBack;
    private ProductLikeHolder productLikeHolder;
    //private UnlikeCallBack unlikeCallBack;
    private AddToCartCallBack addToCartCallBack;
    private int requestCode = 200;

    private int subcategory = 0;
    private int categoryId;
    private int offset = 0;
    private int limit = 10;
    private int Position;
    private int Id;
    private int unlikePosition;
    private List<ProductObject> productsList = new ArrayList<>();
    private List<String> times = new ArrayList<>();
    private List<ProductObject> productsList1 = new ArrayList<>();
    private Dialog dialogTransparent;
    private Date offerdate;
    private ImageView like_product_img;
    private ImageView liked_product_image;
    private TextView noitems;

    private LinearLayout progressbarPaginationlinear;
    private View progressview;
    private Activity activity;
    private GridView gridView;
    List<UpdateClockTimer> s1 = new ArrayList<UpdateClockTimer>();

    private View content;
    private String productId;
    private String productName;
    private String productPrice;
    private String productImage;
    private String Quantity = "1";
    private String userId;
    private String sessionId;
    private int currentFirstVisibleItem;
    private int currentVisibleItemCount;
    private int currentScrollState;
    private boolean isLoading = true;
    private int currenttotalItemCount;
    private int mPreviousTotal = 0;
    private boolean mLoading = true;
    private boolean mLastPage = false;
    private int mCurrentPage = 0;
    private int mVisibleThreshold = 5;

    private ImageView likegreenImg;


    private TextView offer_time_left_minute_1;
    private android.os.Handler handler = new android.os.Handler();
    private int ProductId;
    private ProductObject productObject;
    private ImageView likegreyImg;
    private LikeCallBack likeCallBack;
    private ProductLikeListener productLikeListener;


    public Allitems() {

    }


    @SuppressLint("ValidFragment")
    public Allitems(int categoryId, Activity activity, int subcategory) {

        super();

        Log.e("!!!!!!!!!!!!", "all items constructor");
        this.activity = activity;
        this.categoryId = categoryId;
        this.subcategory = subcategory;
        //productsList=new ArrayList<>();
        clearTimer();
        gridView = (GridView) activity.findViewById(R.id.productGridView);

    }

    @Override
    public void onResume() {
        initViews();
        initManager();
        Log.e(TAG, "on resume......");

        dialogTransparent = new Dialog(getActivity(), android.R.style.Theme_Black);
        progressview = LayoutInflater.from(getActivity()).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);
        dialogTransparent.show();
        sessionId = DbManager.getInstance().getSessionId();
        clearTimer();

        ProductManager.getInstance().getAllProducts(activity, productCallBack, sessionId, categoryId, subcategory, offset, limit);

        Log.e("resume>> ", "id>>" + productId);
        //ProductLikeManager.getInstance().setLike(activity, productId, DbManager.getInstance().getSessionId(), likeCallBack);
       // productAdapter.notifyDataSetChanged();
        super.onResume();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        Log.e("on pause......", TAG);
        clearTimer();

        //productAdapter.notifyDataSetChanged();
        super.onPause();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e("on activity created...", TAG);

      /*  dialogTransparent = new Dialog(getActivity(), android.R.style.Theme_Black);
        progressview = LayoutInflater.from(getActivity()).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);
        dialogTransparent.show();*/
        super.onActivityCreated(savedInstanceState);
        clearTimer();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        content = inflater.inflate(R.layout.products_details, container, false);
        initViews();
        initManager();
        sessionId = DbManager.getInstance().getSessionId();
        noitems.setVisibility(View.GONE);

        progressbarPaginationlinear.setVisibility(View.GONE);
        clearTimer();
        Log.e(TAG, "in on creat>>>>>>>>>>>>>>...." + categoryId);

       /* dialogTransparent = new Dialog(getActivity(), android.R.style.Theme_Black);
        progressview = LayoutInflater.from(getActivity()).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);
        dialogTransparent.show();


        ProductManager.getInstance().getAllProducts(activity, productCallBack, sessionId, categoryId, subcategory, offset, limit);
        productAdapter.notifyDataSetChanged();
*/
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

    private void initManager() {
        productCallBack = new ProductCallBack();
        //likeCallBack = new LikeCallBack();
        productLikeHolder = new ProductLikeHolder();
        //unlikeCallBack = new UnlikeCallBack();
        productLikeListener = new ProductLikeListener();
    }

    private void initViews() {

        gridView = (GridView) content.findViewById(R.id.productGridView);
        noitems = (TextView) content.findViewById(R.id.noitems);
        likegreenImg = (ImageView) content.findViewById(R.id.likegreenImg);
        progressbarPaginationlinear = (LinearLayout) content.findViewById(R.id.progressbarPaginationlayout);
        offer_time_left_minute_1 = (TextView) content.findViewById(R.id.time_num1);
        likegreyImg = (ImageView) content.findViewById(R.id.likegreyImg);

        productAdapter = new ProductAdapter(getActivity(), productsList, addToCartCallBack, likeCallBack,productLikeListener);
        //productAdapter = new ProductAdapter(getActivity(), productsList, addToCartCallBack);
        addToCartCallBack = new AddToCartCallBack();
        //gridView.setEmptyView(getActivity().findViewById(R.id.noitems));
        gridView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
        clearTimer();


    }

    public class ProductCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {
            Log.e("", "CAAALLLLLLLLLLL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
            final ProductBaseHolder productBaseHolder = (ProductBaseHolder) result;
            Log.e("###################", "" + productBaseHolder.getStatus());
            if (productBaseHolder.getStatus().equals("success")) {
                if (productBaseHolder.getData().getProducts().size() == 0) {
                    //dialogTransparent.dismiss();
                    noitems.setVisibility(View.VISIBLE);
                }
                dialogTransparent.dismiss();
                progressbarPaginationlinear.setVisibility(View.GONE);
                if (productBaseHolder.getData() != null) {
                    if (UtilValidate.isNotEmpty(productBaseHolder.getData().getProducts())) {

                        // dialogTransparent.dismiss();
                        if ((productsList.size() == 0) || (offset == 0)) {
                            productsList.clear();
                            productsList.addAll(productBaseHolder.getData().getProducts());
                            productAdapter = new ProductAdapter(getActivity(), productsList, addToCartCallBack, likeCallBack,productLikeListener);
                            //productAdapter = new ProductAdapter(getActivity(), productsList, addToCartCallBack);

                            gridView.setAdapter(productAdapter);
                            productAdapter.notifyDataSetChanged();
                            clearTimer();
                        } else {
                            productsList1.clear();

                            productsList1.addAll(productBaseHolder.getData().getProducts());
                            if (productBaseHolder.getData().getProducts().size() > 0) {
                                for (int i = 0; i < productBaseHolder.getData().getProducts().size(); i++) {

                                    for (int j = 0; j < productsList.size(); j++) {

                                        if (productBaseHolder.getData().getProducts().get(i).getId() == productsList.get(j).getId()) {
                                            productsList1.remove(productBaseHolder.getData().getProducts().get(i));

                                        }
                                    }
                                }
                            }
                            if (productsList1.size() > 0) {
                                productsList.addAll(productsList1);
                                productAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                }

            } else {
                Toast.makeText(getActivity(), "server problem", Toast.LENGTH_SHORT).show();
                //dialogTransparent.dismiss();
            }
            gridView.setOnScrollListener(new allitemsOnscroll());

        }

        @Override
        public void onFinish(int responseCode, String result) {
            if (dialogTransparent != null) {
                dialogTransparent.dismiss();
            }
            dialogTransparent.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Internet Not Available!!").setCancelable(false).setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ProductManager.getInstance().getAllProducts(activity, productCallBack, DbManager.getInstance().getSessionId(), categoryId, subcategory, offset, limit);
                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            getActivity().finish();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.setTitle("No Internet Connection!!");
            alert.show();

        }
    }

    private void clearTimer() {
        if (productAdapter != null) {

            if (productAdapter.getupdateClockTimerList() != null
                    && productAdapter.getHandler() != null) {
                for (UpdateClockTimer updateClockTimer : productAdapter
                        .getupdateClockTimerList()) {
                    productAdapter.getHandler().removeCallbacks(
                            updateClockTimer);
                }
            }

        }
    }


    /*public void likeProduct(ProductObject product, int position) {

        Log.e("name ++++", "" + product.getName());
        Log.e("like count ++++", "" + product.getLike_count());

        productObject = product;
        ProductId = productObject.getId();
        Position = position;
        //productId = String.valueOf(productObject);
        likeCallBack = new LikeCallBack();
        Log.e("position from adaper", "" + Position);
        Log.e("position from adaper", "" + Id);


        ProductLikeManager.getInstance().setLike(activity, String.valueOf(ProductId), DbManager.getInstance().getSessionId(), likeCallBack);
        Log.e("id", "" + String.valueOf(ProductId));

    }*/


    @Override
    public void onDestroy() {
        clearTimer();
        super.onDestroy();
    }


    private class LikeCallBack implements AsyncTaskCallBack {

        int count;

        @Override
        public void onFinish(int responseCode, Object result) {


            productLikeHolder = (ProductLikeHolder) result;
            //dialogTransparent.dismiss();

            if (productLikeHolder.getStatus().equals("success")) {
                Log.e("like count ++++---", "" + productObject.getLike_count());

                if (UtilValidate.isNotNull(productLikeHolder.getCode())) {
                    if (productLikeHolder.getCode() == 1) {
                        Log.e("@@", "product liked!!!!!");
                        Log.e("@@", "like position" + Position);

                        try {

                            count = productObject.getLike_count()+1;
                           // String likecount = String.valueOf(count + 1);
                            Log.e("count >>" + count, "new count>>>" + count);
                        } catch (IndexOutOfBoundsException e) {
                        }

                    }
                    if (productLikeHolder.getCode() == 0) {
                        Log.e("@@", "product unliked!!!!!");
                        Log.e("@@", "unlike position" + Position);

                        Log.e(".size", "" + productObject.getName());

                        try {
                            count = productObject.getLike_count()-1;
                            //int unlike = count - 1;
                            Log.e("like>>>" + productObject.getLike_count(), "unlike>>" + count);
                        } catch (IndexOutOfBoundsException e) {
                        }


                    }
                    productsList.get(Position).setIs_liked(productLikeHolder.getCode());
                    productsList.get(Position).setLike_count(count);
                    productAdapter.notifyDataSetChanged();
                    //ProductManager.getInstance().getAllProducts(activity, productCallBack, DbManager.getInstance().getSessionId(), categoryId, subcategory, offset, limit);
                } else {
                    dialogTransparent.dismiss();
                }


            } else {
                dialogTransparent.dismiss();
                Log.e("like status", "error");
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {
            //dialogTransparent.dismiss();

        }
    }

    private class AddToCartCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {

            Log.e(TAG, "on finish success");

            ShoppingCartResponseHolder addToCart = (ShoppingCartResponseHolder) result;
            if (addToCart.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(getActivity(), addToCart.getMessage().toString(), Toast.LENGTH_LONG).show();
                Log.e("", "" + addToCart.getMessage());
            }
            if (addToCart.getStatus().equalsIgnoreCase("error")) {
                Toast.makeText(getActivity(), addToCart.getMessage().toString(), Toast.LENGTH_LONG).show();
                Log.e("", "" + addToCart.getMessage());
            }
        }

        @Override
        public void onFinish(int responseCode, String result) {

            Log.e(TAG, "failed");

        }

    }

    public static CharSequence setTextStyleItalic(CharSequence text) {
        final StyleSpan style = new StyleSpan(Typeface.ITALIC);
        final SpannableString str = new SpannableString(text);
        str.setSpan(style, 0, text.length(), 0);
        return str;
    }


    // Scroll in gridview

    public class allitemsOnscroll implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // TODO Auto-generated method stub
            currentScrollState = scrollState;
            isScrollCompleted(currentFirstVisibleItem, currentVisibleItemCount,
                    currenttotalItemCount);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            // TODO Auto-generated method stub
            currentFirstVisibleItem = firstVisibleItem;
            currentVisibleItemCount = visibleItemCount;
            currenttotalItemCount = totalItemCount;
        }

        private void isScrollCompleted(int currentFirstVisibleItem,
                                       int currentVisibleItemCount, int currenttotalItemCount) {
            // TODO Auto-generated method stub

            if (currentVisibleItemCount > 0 && currentScrollState == SCROLL_STATE_IDLE) {

                if (currenttotalItemCount > mPreviousTotal) {
                    isLoading = false;
                    mPreviousTotal = currenttotalItemCount;
                    mCurrentPage++;
                    if (mCurrentPage + 1 > 50) {
                        mLastPage = true;
                    }
                }

                if (!mLastPage && !isLoading && (currenttotalItemCount - currentVisibleItemCount) <= (currentFirstVisibleItem + mVisibleThreshold)) {
                    limit = 10;
                    offset = productsList.size();
                    sessionId = DbManager.getInstance().getSessionId();
                    progressbarPaginationlinear.setVisibility(View.VISIBLE);
                    ProductManager.getInstance().getAllProducts(activity, productCallBack, sessionId, categoryId, subcategory, offset, limit);
                    clearTimer();
                    isLoading = true;

                } else {
                }

            }

        }

    }

    private class ProductLikeListener implements AdapterViewItemListener {

        @Override
        public void likeProduct(ProductObject product, int position) {
            productObject = product;
            ProductId = productObject.getId();
            Position = position;
            likeCallBack = new LikeCallBack();
            ProductLikeManager.getInstance().setLike(activity, String.valueOf(ProductId), DbManager.getInstance().getSessionId(), likeCallBack);
        }
    }

}

