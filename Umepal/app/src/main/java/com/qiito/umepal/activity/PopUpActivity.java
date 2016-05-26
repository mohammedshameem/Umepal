package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.dao.CurrentlyLoggedUserDAO;
import com.qiito.umepal.holder.ShippingAddress;
import com.qiito.umepal.holder.ShippingAddressHolder;
import com.qiito.umepal.holder.ShippingDetailsBaseHolder;
import com.qiito.umepal.managers.ShoppingCartManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

/**
 * Created by sini on 29/2/16.
 */
public class PopUpActivity extends Activity {
    private ImageView closeimg;
    private TextView cancel;
    private TextView continueShopping;
    private EditText fullName;
    private EditText city;
    private EditText street;
    private EditText unit;
    private EditText state;
    private EditText postalCode;
    private EditText phone;
    private Button done;
    private ShippingAddress shippingAddress;
    private ShippingAddressCallBack shippingAddressCallBack;
    private ShippingAddressHolder shippingDetailsHolder;
    private String session_id;
    private CurrentlyLoggedUserDAO currentlyLoggedUserDAO;
    private String result;
    private  ShippingAddress address;

@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.shippingdetails);
    initViews();
    initManager();
    session_id = currentlyLoggedUserDAO.getSessionId();
    continueShopping.setOnClickListener(cancelListener);
    cancel.setOnClickListener(cancelListener);
    closeimg.setOnClickListener(cancelListener);
    done.setOnClickListener(doneListener);

    if (TodaysParentApp.getShippingAddress() != null) {
        address=new ShippingAddress();
        address=TodaysParentApp.getShippingAddress();

        if(address.getShippingfullname()!=null){
            fullName.setText(address.getShippingfullname());
        }
        if(address.getShippingcity()!=null){
            city.setText(address.getShippingcity());
        }
        if(address.getShippingunit()!=null){
            unit.setText(address.getShippingunit());
        }
        if(address.getShippingstreetaddress()!=null){
            street.setText(address.getShippingstreetaddress());
        }
        if(address.getShippingstate()!=null){
            state.setText(address.getShippingstate());
        }
        if(address.getShippingpostalcode()!=null){
            postalCode.setText(address.getShippingpostalcode());
        }
        if(address.getShippingphone()!=null){
            phone.setText(address.getShippingphone());
        }

    }

    if (TodaysParentApp.getShippingDetailsHolder() != null) {
        shippingDetailsHolder = new ShippingAddressHolder();
        shippingDetailsHolder = TodaysParentApp.getShippingDetailsHolder();

        if (shippingDetailsHolder.getFullname() != null) {
            fullName.setText(shippingDetailsHolder.getFullname());
        }
        if (shippingDetailsHolder.getCity() != null) {
            city.setText(shippingDetailsHolder.getCity());
        }
        if (shippingDetailsHolder.getUnit() != null) {
            unit.setText(shippingDetailsHolder.getUnit());
        }
        if (shippingDetailsHolder.getStreetaddress() != null) {
            street.setText(shippingDetailsHolder.getStreetaddress());
        }
        if (shippingDetailsHolder.getState() != null) {
            state.setText(shippingDetailsHolder.getState());
        }
        if (shippingDetailsHolder.getPostalcode() != null) {
            postalCode.setText(shippingDetailsHolder.getPostalcode());
        }
        if (shippingDetailsHolder.getPhone() != null) {
            phone.setText(shippingDetailsHolder.getPhone());
        }

    }
}

    private void initManager() {
        shippingAddressCallBack = new ShippingAddressCallBack();
        shippingAddress = new ShippingAddress();
        currentlyLoggedUserDAO = new CurrentlyLoggedUserDAO();
    }

    private void initViews() {
        closeimg = (ImageView) findViewById(R.id.close_img);
        cancel = (TextView) findViewById(R.id.canceltxt);
        continueShopping = (TextView)findViewById(R.id.continueShopping);
        fullName = (EditText)findViewById(R.id.first_name_edttxt);
        city = (EditText) findViewById(R.id.city_edttxt);
        street = (EditText)findViewById(R.id.street_address_edttxt);
        unit = (EditText) findViewById(R.id.apt_suit_unit_edttxt);
        state = (EditText) findViewById(R.id.state_edttxt);
        postalCode = (EditText) findViewById(R.id.zip_postal_edttxt);
        phone = (EditText) findViewById(R.id.phone_edttxt);
        done = (Button)findViewById(R.id.done_bttn);
    }

    View.OnClickListener doneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (fullName.getText().length() > 0 && street.getText().length() > 0 && city.getText().length() > 0 && phone.getText().length() > 0) {
                shippingAddress.setShippingfullname(fullName.getText().toString());
                shippingAddress.setShippingstreetaddress(street.getText().toString());
                shippingAddress.setShippingunit(unit.getText().toString());
                shippingAddress.setShippingstate(state.getText().toString());
                shippingAddress.setShippingcity(city.getText().toString());
                shippingAddress.setShippingpostalcode(postalCode.getText().toString());
                shippingAddress.setShippingphone(phone.getText().toString());
                shippingAddress.setShippingcountry("Singapore");
                ShoppingCartManager.getInstance().ShippingAddressDetails(PopUpActivity.this, shippingAddressCallBack, session_id, shippingAddress);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            } else {

                Toast.makeText(PopUpActivity.this, "Fill all mandatory feilds.", Toast.LENGTH_LONG).show();
            }
        }


    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }
    };
    private class ShippingAddressCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {
            ShippingDetailsBaseHolder shippingDetailsBaseHolder = (ShippingDetailsBaseHolder) result;
            if(shippingDetailsBaseHolder.getStatus().equalsIgnoreCase("success")){
                TodaysParentApp.setShippingDetailsHolder(shippingDetailsBaseHolder.getDetails().getShippingaddress());
                Log.e("SUCESS VAL", TodaysParentApp.getShippingDetailsHolder().getFullname());
            }
        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
