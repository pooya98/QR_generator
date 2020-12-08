package com.example.qrcreate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Hashtable;

public class CreateQR extends AppCompatActivity {
    private ImageView iv;
    private String text;
    private String settext;
    TextView receiveView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qr);
        Intent intent = getIntent();
        String receiveStr = intent.getStringExtra("keycode");
        Bitmap bitmap = null ;
        iv = (ImageView)findViewById(R.id.qrcode);
        text = receiveStr;

        settext = "건물명 " + receiveStr;
        receiveView = (TextView)findViewById(R.id.receiveText);
        receiveView.setText(settext);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            iv.setImageBitmap(bitmap);
        }catch (Exception e){}
    }
}