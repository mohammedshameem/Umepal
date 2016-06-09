package com.qiito.umepal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.zxing.Result;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.LoginManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import java.io.ByteArrayOutputStream;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.qiito.umepal.R.drawable.logo_splash;


public class Signupactivity extends Activity implements ZXingScannerView.ResultHandler {


    private static final int CAMERA_REQUEST = 1888;
    private static int RESULT_LOAD_IMG = 1;
    private SignUpCallBack signUpCallBack;
    private LoginCallBackClass loginCallBackClass;

    //private LinearLayout loginWithFacebok;
    

    // private Button loginButton;
    // private Button signupButton;
    private LinearLayout uploadImageLayout;
    private PopupWindow pwindo;
    private Button cancelButton;
    private Button scanQRquoteButton;
    private Button nextButton;
    private ImageView uploadPicButton;

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText ceaEditText;
    private EditText mobileEditText;
    private EditText bankEditText;
    private EditText estateagencyEditText;
    private EditText bankaccountEditText;
    private EditText refferalmemberidEditText;
    private EditText password;


    private String androidId;
    private String FirstName;
    private String LastName;
    private String Email;
    private String CEA;
    private String MOBILE;
    private String BANK;
    private String ESTATEAGENCY;
    private String BankAccount;
    private String Password;
    private String refferalmemberID;
    private String profilePic;
    private ZXingScannerView mScannerView;
    private String picturePath = null;

    private final int requestCode = 200;
    private int requestcode = 1;
    private String reffereid;
    private Intent intent;
    private int loginforbuy;
    private int productId;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);
        initView();
        initManager();
        intent = getIntent();
        getIntentValues();

        setText();


        androidId = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.HeaderRed));
        }

        /* SIGN UP */

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FirstName = nameEditText.getText().toString();
                Email = emailEditText.getText().toString();
                CEA = ceaEditText.getText().toString();
                MOBILE = mobileEditText.getText().toString();
                BANK = bankEditText.getText().toString();
                ESTATEAGENCY = estateagencyEditText.getText().toString();
                BankAccount = bankaccountEditText.getText().toString();
                refferalmemberID = refferalmemberidEditText.getText().toString();
                Password = password.getText().toString();

                CHECKDETAILS();


            }
        });

        /*LOGIN*/

       /* loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent login=new Intent(Signupactivity.this,Loginactivity.class);
                startActivity(login);
                finish();
            }
        });*/

        /* LOGIN WITH FACEBOOK */

       /* loginWithFacebok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent fb=new Intent(Signupactivity.this,Loginwithfacebook.class);
                startActivity(fb);
                finish();
            }
        });*/
        scanQRquoteButton.setOnClickListener(scanQRcodeListener);
        cancelButton.setOnClickListener(cancelListener);
        
        uploadImageLayout.setOnClickListener(uploadImageListener);
    }

    private void setText() {
        nameEditText.setText(FirstName);
        emailEditText.setText(Email);
        ceaEditText.setText(CEA);
        mobileEditText.setText(MOBILE);
        bankEditText.setText(BANK);
        estateagencyEditText.setText(ESTATEAGENCY);
        bankaccountEditText.setText(BankAccount);
        refferalmemberidEditText.setText(refferalmemberID);
        password.setText(Password);

    }

    private void getIntentValues() {
        loginforbuy = intent.getIntExtra("buy", id);
        productId = intent.getIntExtra("productId", id);
        FirstName=intent.getStringExtra("Name");
        Email=intent.getStringExtra("Email");
        CEA=intent.getStringExtra("CEA");
        MOBILE=intent.getStringExtra("Mobile");
        BANK=intent.getStringExtra("Bank");
        ESTATEAGENCY=intent.getStringExtra("Estateagency");
        BankAccount=intent.getStringExtra("BankAccount");
        Password=intent.getStringExtra("Password");
        refferalmemberID = intent.getStringExtra("reffereID");
    }

    private void CHECKDETAILS() {
        LastName = "";


        LoginManager.getInstance().emailSignup(Signupactivity.this, signUpCallBack, FirstName, LastName,
                Email, CEA, MOBILE, BANK, ESTATEAGENCY, BankAccount, Password, androidId, refferalmemberID, profilePic, requestCode);

    }

    View.OnClickListener cancelListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //  finish();
            Intent intent = new Intent(Signupactivity.this, MembershipSelectionActivity.class);
            startActivity(intent);
            finish();
        }
    };
    /* View.OnClickListener scanQRcodeListener = new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             QrScanner(v);
         }
     };*/
    View.OnClickListener scanQRcodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //QrScanner(v);
            Intent cam = new Intent(Signupactivity.this, QRcodeScanner.class);
            cam.putExtra("Name",nameEditText.getText().toString());
            cam.putExtra("Email",emailEditText.getText().toString());
            cam.putExtra("CEA",ceaEditText.getText().toString());
            cam.putExtra("Mobile",mobileEditText.getText().toString());
            cam.putExtra("Bank",bankEditText.getText().toString());
            cam.putExtra("Estateagency",estateagencyEditText.getText().toString());
            cam.putExtra("BankAccount",bankaccountEditText.getText().toString());
            cam.putExtra("Password",password.getText().toString());
            cam.putExtra("from","signup");
            startActivity(cam);
              /*  QRcodeScanner qRcodeScanner= new QRcodeScanner();
                qRcodeScanner.onClick(v);*/
        }
    };

    public void QrScanner(View view) {


        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }
    
    View.OnClickListener uploadImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                LayoutInflater inflater = (LayoutInflater) Signupactivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.activity_edit_profile_pic, null);

                pwindo = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
                pwindo.showAtLocation(layout, Gravity.FILL, 0, 0);

            /*  POPUP MENU  */

            /*CANCEL*/
                LinearLayout cancel = (LinearLayout) layout.findViewById(R.id.linear_cancel_change_pic);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pwindo.dismiss();

                    }
                });

            /*REMOVE PHOTO*/

                LinearLayout remove = (LinearLayout) layout.findViewById(R.id.linear_remove_photo);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageView i = (ImageView) findViewById(R.id.image_editprofile);
                    /*if empty*/
                        if (i.getDrawable().getConstantState() == getResources().getDrawable(logo_splash).getConstantState()) {
                            Toast.makeText(Signupactivity.this, "Empty !", Toast.LENGTH_SHORT).show();
                            pwindo.dismiss();
                        } else if (Integer.parseInt((String) i.getTag()) != logo_splash) {
                            i.setImageResource(logo_splash);
                            Toast.makeText(Signupactivity.this, "Removed..", Toast.LENGTH_SHORT).show();
                            pwindo.dismiss();
                        } else {
                            Toast.makeText(Signupactivity.this, "error..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            /*TAKE FROM CAMERA*/
                LinearLayout cam = (LinearLayout) layout.findViewById(R.id.linear_take_photo_from_camera);
                cam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    
                        Log.e(" >>>  ", "cameraa>>" + CAMERA_REQUEST);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST);
                        pwindo.dismiss();

                    }
                });


            /*CHOOSE FROM GALLARY*/
                LinearLayout gal = (LinearLayout) layout.findViewById(R.id.linear_choose_from_gallery);
                gal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e(">>>  ","gallery>>"+RESULT_LOAD_IMG);
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                        pwindo.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("", "in onActivityResult()");
        Log.i("", "requestCode $$$$ " + requestCode);
        Log.i("", "resultCode $$$$ " + resultCode);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            // String picturePath contains the path of selected Image

            try {

                if (UtilValidate.isNotEmpty(picturePath)) {

					/*
					 * Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
					 * profileimage.setImageBitmap(bitmap);
					 */
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;

                    Bitmap bm = BitmapFactory.decodeFile(picturePath, options);
                    uploadPicButton.setImageBitmap(bm);

                    if (bm != null && !bm.isRecycled()) {
                        // bm.recycle();
                        bm = null;
                    }

                } else {

                    Toast.makeText(Signupactivity.this,
                            "No file choosed...", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {

                Log.e("#$$", "Exception occured while decodeFile" + e);
            }

        }else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data !=null) {
            Log.e("%%%","in Camera");
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            uploadPicButton.setImageBitmap(photo);
            Log.e("image>>", String.valueOf(photo));
            Log.e("image1>>",String.valueOf(uploadPicButton));

            Uri selectedImage = getImageUri(getApplicationContext(), photo);
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            // String picturePath contains the path of selected Image

            try {

                if (UtilValidate.isNotEmpty(picturePath)) {

					/*
					 * Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
					 * profileimage.setImageBitmap(bitmap);
					 */
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;

                    Bitmap bm = BitmapFactory.decodeFile(picturePath, options);
                    uploadPicButton.setImageBitmap(bm);

                    if (bm != null && !bm.isRecycled()) {
                        // bm.recycle();
                        bm = null;
                    }

                } else {

                    Toast.makeText(Signupactivity.this,
                            "No file choosed...", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {

                Log.e("!!", "Exception occured while decodeFile" + e);
            }

        }

    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Log.e("reffereID", "" + data.getStringExtra("reffereID"));

            reffereid = intent.getStringExtra("reffereID");
            Log.e("ID ", "RID" + reffereid);
            refferalmemberidEditText.setText(reffereid);


        } else {
            Log.e("ID ", "RID result");
        }


    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

    }*/

    private void initView() {

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        ceaEditText = (EditText) findViewById(R.id.ceaEditText);
        mobileEditText = (EditText) findViewById(R.id.mobileEditText);
        bankEditText = (EditText) findViewById(R.id.bankEditText);
        estateagencyEditText = (EditText) findViewById(R.id.estateagencyEditText);
        bankaccountEditText = (EditText) findViewById(R.id.bankaccountEditText);
        refferalmemberidEditText = (EditText) findViewById(R.id.refferalmemberidEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        uploadPicButton = (ImageView) findViewById(R.id.uploadPicButton);
        uploadImageLayout = (LinearLayout)findViewById(R.id.upload_image_layout);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        scanQRquoteButton = (Button) findViewById(R.id.scanQRquoteButton);
        nextButton = (Button) findViewById(R.id.nextButton);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_signupactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initManager() {
        signUpCallBack = new SignUpCallBack();
        loginCallBackClass = new LoginCallBackClass();
        mScannerView = new ZXingScannerView(Signupactivity.this);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        if (mScannerView != null) {
            if (mScannerView.isShown()) {
                Log.e("camera ", "shown");
                mScannerView.stopCamera();
                Intent intent = new Intent(Signupactivity.this, Signupactivity.class);
                startActivity(intent);
            } else {
                Log.e("camera ", "hide");

                finish();
            }

        }
    }


    @Override
    public void handleResult(Result result) {
       /* AlertDialog.Builder builder = new AlertDialog.Builder(Signupactivity.this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();*/
        mScannerView.stopCamera();
        refferalmemberidEditText.setText(result.getText());

    }

    public class SignUpCallBack implements AsyncTaskCallBack {


        @Override
        public void onFinish(int responseCode, Object result) {
            UserBaseHolder userBaseHolder = (UserBaseHolder) result;
            if (userBaseHolder.getStatus().equals("success")) {
                Log.e("", "In callBack success");

                if (userBaseHolder != null) {


                    DbManager.getInstance().deleteCurrentlyLoggedUserTable();

                    DbManager.getInstance().insertIntoCurrentUser(userBaseHolder.getData().getUser().getId(),
                            userBaseHolder.getData().getSession_id());

                    DbManager.getInstance().deleteUserTable();


                    DbManager.getInstance().insertIntoUserTable(userBaseHolder.getData().getUser());

                    Toast.makeText(getApplicationContext(), "Signup Success", Toast.LENGTH_LONG).show();

                    //  LoginManager.getInstance().emailLogin(Signupactivity.this, Email, Password, loginCallBackClass, requestcode);

                    Intent next = new Intent(Signupactivity.this, MembershipSelectionActivity.class);
                    next.putExtra("password", Password);
                    next.putExtra("buy", loginforbuy);
                    next.putExtra("productId", productId);
                    startActivity(next);
                }
            } else if (userBaseHolder.getStatus().equals("error")) {
                Toast.makeText(Signupactivity.this, userBaseHolder.getMessage(), Toast.LENGTH_LONG).show();
            }


        }

        @Override
        public void onFinish(int responseCode, String result) {
            Toast.makeText(Signupactivity.this, "" + result, Toast.LENGTH_SHORT).show();
        }
    }

    public class LoginCallBackClass implements AsyncTaskCallBack {


        @Override
        public void onFinish(int responseCode, Object result) {

            UserBaseHolder userBaseHolder = (UserBaseHolder) result;
            if (UtilValidate.isNotNull(userBaseHolder)) {

                if (userBaseHolder.getStatus().equalsIgnoreCase("error")) {


                } else {

                    DbManager.getInstance().deleteCurrentlyLoggedUserTable();
                    DbManager.getInstance().insertIntoCurrentUser(userBaseHolder.getData().getUser().getId(),
                            userBaseHolder.getData().getSession_id());
                    DbManager.getInstance().deleteAllRowsFromUserTable();
                    DbManager.getInstance().insertIntoUserTable(userBaseHolder.getData().getUser());
                    DbManager.getInstance().deleteShippingData();
                    if (userBaseHolder.getData().getUser().getShipping_address() != null) {
                        DbManager.getInstance().insertintoShippingTable(userBaseHolder.getData().getUser().getShipping_address());

                    }

                    Intent intent = new Intent(Signupactivity.this, MainActivity.class);

                    startActivity(intent);

                }
                finish();
            } else {
                //Toast.makeText(Loginactivity.this,"Please try again ",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int responseCode, String result) {


        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    
}
