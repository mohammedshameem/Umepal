package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qiito.umepal.R;

/**
 * Created by abin on 6/6/16.
 */
public class QRcodeActivity extends Activity {

    private ImageView qrImg;
    private Button cancel;
    private String umeID;
    private TextView title;
    private TextView clear_alls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_page);
        initViews();
        setText();
        getINTENTvalue();
        generateQRCode();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getINTENTvalue() {
        Intent intent=getIntent();
        umeID = intent.getStringExtra("UMEID");
    }

    private void setText() {
        title.setText(" Scan Code");
        title.setTextColor(Color.parseColor("#FFFFFF"));
        clear_alls.setVisibility(View.GONE);
    }

    private void initViews() {
        qrImg = (ImageView)findViewById(R.id.qrimg);
        title = (TextView)findViewById(R.id.app_action_bar_title);
        cancel = (Button)findViewById(R.id.cancelBtn);
        clear_alls = (TextView)findViewById(R.id.clear_alls);
    }

    private void generateQRCode() {
        if (!TextUtils.isEmpty(umeID)) {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = qrCodeWriter.encode(umeID,
                        BarcodeFormat.QR_CODE, 400, 400);
                Bitmap imageBitmap = Bitmap.createBitmap(400, 400,
                        Bitmap.Config.ARGB_8888);

                for (int i = 0; i < 400; i++) {
                    for (int j = 0; j < 400; j++) {
                        imageBitmap
                                .setPixel(i, j,
                                        bitMatrix.get(i, j) ? Color.BLACK
                                                : Color.WHITE);
                    }
                }
                qrImg.setImageBitmap(imageBitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }


}
