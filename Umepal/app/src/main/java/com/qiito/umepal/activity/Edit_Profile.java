package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.dao.CurrentlyLoggedUserDAO;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.UserManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static com.qiito.umepal.R.drawable.logo_splash;

public class Edit_Profile extends Activity {

    private static final int CAMERA_REQUEST = 1888;
    private static int RESULT_LOAD_IMG = 1;

    private Editprofilecallback edit_profilecallback;
    private PopupWindow pwindo;

    private EditText userName;
    private EditText firstName;
    private EditText lastName;
    private EditText city;
    private EditText email;
    private EditText mobile;

    private TextView edit_photo;
    private TextView page_heading;

    private ImageView profile_pic;
    private ImageView back_menu_icon;
    //private ImageView profile_picture;

    //private Button close;
    private Button Save;

    /*private String imgDecodableString;
    private String profilePicPath;
    private String encodedimage;
    private String photoString;*/
    private String FirstName;
    private String LastName;
    private String City;
    private String Email;
    private String Mobile;
    private String picturePath = null;

    //private File imageFile;

    //private int requestcode = 100;

    private boolean userNameFlag;
    private boolean firstNameFlag;
    private boolean lastNameFlag;
    private boolean cityFlag;
    private boolean emailFlag;
    private boolean mobileFlag;
    private boolean mobile_no_minimum_flag;


    private static final String TAG = Edit_Profile.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        initView();
        initManager();
        setVisibilities();

        page_heading.setText("Edit Profile ");

        /*Setting Details*/
        userName.setText(DbManager.getInstance().getCurrentUserDetails().getFirstName());
        firstName.setText(DbManager.getInstance().getCurrentUserDetails().getFirstName());
        lastName.setText(DbManager.getInstance().getCurrentUserDetails().getLastName());
        city.setText(DbManager.getInstance().getCurrentUserDetails().getCity());
        email.setText(DbManager.getInstance().getCurrentUserDetails().getEmail());
        mobile.setText(DbManager.getInstance().getCurrentUserDetails().getTelephone());
        String img_url = DbManager.getInstance().getCurrentUserDetails().getProfilePic();
        Bitmap bitmap = BitmapFactory.decodeFile(img_url);
        profile_pic.setImageBitmap(bitmap);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdetails();
            }
        });

        edit_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiatePopupWindow();
            }
        });
        back_menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (UtilValidate.isNotEmpty(DbManager.getInstance().getCurrentUserDetails().getProfilePic())) {
            if (DbManager.getInstance().getCurrentUserDetails().getProfilePic() != "") {

                Picasso.with(Edit_Profile.this)
                        .load(DbManager.getInstance().getCurrentUserDetails().getProfilePic().toString())
                        .placeholder(R.drawable.logo_splash)
                        .error(R.drawable.logo_splash).fit()
                        .into(profile_pic);

            } else {
                Picasso.with(Edit_Profile.this).load(R.drawable.logo_splash).into(profile_pic);
            }
        } else {
            Picasso.with(Edit_Profile.this).load(R.drawable.logo_splash).into(profile_pic);

        }

    }

    private void initManager() {
        edit_profilecallback = new Editprofilecallback();

    }

    /* POPUP */

    private void initiatePopupWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater) Edit_Profile.this
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
                        Toast.makeText(Edit_Profile.this, "Empty !", Toast.LENGTH_SHORT).show();
                        pwindo.dismiss();
                    } else if (Integer.parseInt((String) i.getTag()) != logo_splash) {
                        i.setImageResource(logo_splash);
                        Toast.makeText(Edit_Profile.this, "Removed..", Toast.LENGTH_SHORT).show();
                        pwindo.dismiss();
                    } else {
                        Toast.makeText(Edit_Profile.this, "error..", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            /*TAKE FROM CAMERA*/
            LinearLayout cam = (LinearLayout) layout.findViewById(R.id.linear_take_photo_from_camera);
            cam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);*/
                    Log.e(TAG, "cameraa>>" + CAMERA_REQUEST);
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
                    Log.e(TAG,"gallery>>"+RESULT_LOAD_IMG);
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                    pwindo.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* ^^ end popup window ^^ */

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
                    profile_pic.setImageBitmap(bm);

                    if (bm != null && !bm.isRecycled()) {
                        // bm.recycle();
                        bm = null;
                    }

                } else {

                    Toast.makeText(Edit_Profile.this,
                            "No file choosed...", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {

                Log.e("#$$", "Exception occured while decodeFile" + e);
            }

        }else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data !=null) {
            Log.e("%%%","in Camera");
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profile_pic.setImageBitmap(photo);
            Log.e("image>>", String.valueOf(photo));
            Log.e("image1>>",String.valueOf(profile_pic));

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
                    profile_pic.setImageBitmap(bm);

                    if (bm != null && !bm.isRecycled()) {
                        // bm.recycle();
                        bm = null;
                    }

                } else {

                    Toast.makeText(Edit_Profile.this,
                            "No file choosed...", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {

                Log.e("!!", "Exception occured while decodeFile" + e);
            }

        }

    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private String getRealPathFromURI(Uri selectedImageURI) {

        String result;
        Cursor cursor = getContentResolver().query(selectedImageURI, null, null, null, null);
        if (cursor == null) {
            result = selectedImageURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    private void checkdetails() {

        if (UtilValidate.isValidUsername(userName.getText().toString())) {
            userName.getText().toString();
            userNameFlag = true;
        } else {
            userName.setError("User name can be a combination of letters and digits");
            userNameFlag = false;
        }

        if (UtilValidate.isValidFirstname(firstName.getText().toString())) {
            FirstName = firstName.getText().toString();
            firstNameFlag = true;
        } else {
            if (firstName.getText().toString().length() == 0) {
                firstName.setError("Enter First Name");
                firstNameFlag = false;
            } else {
                firstName.setError("Enter valid First Name");
                firstNameFlag = false;
            }
        }

        if (UtilValidate.isValidLastname(lastName.getText().toString())) {
            LastName = lastName.getText().toString();
            lastNameFlag = true;
        } else {
            if (lastName.getText().toString().length() == 0) {
                lastName.setError("Enter Last Name");
                lastNameFlag = false;
            } else {
                lastName.setError("Enter valid Last Name");
                lastNameFlag = false;
            }
        }
        if (UtilValidate.isValidCity(city.getText().toString())) {
            City = city.getText().toString();
            cityFlag = true;
        } else {
            if (city.getText().toString().length() == 0) {
                city.setError("Enter City");
                cityFlag = false;
            }else{
                city.setError("Format is A-Za-z0-9");
            }

        }

        if (UtilValidate.isValidemail(email.getText().toString())) {
            Email = email.getText().toString();
            emailFlag = true;
        } else {
            email.setError("Email should be in the form abc@abc.abc");
            emailFlag = false;
        }
        if (UtilValidate.isValidMobileNumber(mobile.getText().toString())) {
            Mobile = mobile.getText().toString();
            mobileFlag = true;
        } else {
            mobileFlag = false;
        }

        if (mobile.getText().length() < 8 || mobile.getText().length()> 13) {
            mobile.setError("Please enter a valid mobile number");
            mobile_no_minimum_flag = false;
        } else {
            mobile_no_minimum_flag = true;
        }


        //String photo = "http:/" + profilePicPath;
        Log.e("PHOTO", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + picturePath);

        if (userNameFlag && firstNameFlag && lastNameFlag && cityFlag && emailFlag &&
                mobileFlag && mobile_no_minimum_flag) {

            String session_id = CurrentlyLoggedUserDAO.getInstance().getSessionId();
            if (picturePath!=null) {
                Log.e("^^","manager call with image");

                UserManager.getInstance().userEditProfilewithImage(Edit_Profile.this, session_id, FirstName,
                        LastName, Email, City, Mobile, picturePath, edit_profilecallback);
            }
            else{
                Log.e("**","manager call without image");
                UserManager.getInstance().userEditProfile(Edit_Profile.this,session_id,FirstName,LastName,Email,City,
                        Mobile,"",edit_profilecallback);
            }
            Toast.makeText(Edit_Profile.this, "saving..", Toast.LENGTH_LONG).show();

        }
    }

    public class Editprofilecallback implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {

            UserBaseHolder userBaseHolder = (UserBaseHolder) result;
            if (UtilValidate.isNotNull(userBaseHolder)) {

                if (userBaseHolder.getStatus().equalsIgnoreCase("success")) {
                    DbManager.getInstance().deleteAllRowsFromUserTable();
                    DbManager.getInstance().insertIntoUserTable(userBaseHolder.getData().getUser());


                    Log.e("**", "PROFILE PIC IN USERBASE HOLDER :" + userBaseHolder.getData().getUser().getProfilePic());
                    Log.e("**", "PROFILE NAME IN USERBASE HOLDER :" + userBaseHolder.getData().getUser().getFirstName().toString());
                    Log.e("**", "First Name :::" + DbManager.getInstance().getCurrentUserDetails().getFirstName().toString());
                    Log.e("**", "Email  :::" + DbManager.getInstance().getCurrentUserDetails().getEmail());
                    Log.e("**", "Password :::" + DbManager.getInstance().getCurrentUserDetails().getPassword().toString());
                    Log.e("**", "#################  Status ::" + userBaseHolder.getStatus());
                    Log.e("@@", "$$$$ profile pic  " + DbManager.getInstance().getCurrentUserDetails().getProfilePic().toString());
                    Toast.makeText(Edit_Profile.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(Edit_Profile.this, " ! ! ! " + userBaseHolder.getStatus(), Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(Edit_Profile.this, "Please try again ", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    private void initView() {

        userName = (EditText) findViewById(R.id.edttxt_username);
        firstName = (EditText) findViewById(R.id.edttxt_fname);
        lastName = (EditText) findViewById(R.id.edttxt_lname);
        city = (EditText) findViewById(R.id.edttxt_city);
        email = (EditText) findViewById(R.id.edttxt_email);
        mobile = (EditText) findViewById(R.id.edttxt_mobile);
        profile_pic = (ImageView) findViewById(R.id.image_editprofile);
        edit_photo = (TextView) findViewById(R.id.edit_user_profile_pic_textview);
        Save = (Button) findViewById(R.id.button_save_profile);
        page_heading = (TextView) findViewById(R.id.page_heading);
        back_menu_icon = (ImageView) findViewById(R.id.back_menu_icon);

    }

    private void setVisibilities() {
        page_heading.setVisibility(View.VISIBLE);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
