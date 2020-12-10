package com.example.qrcreate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    Button btn;
    EditText keycode;
    Button search;
    private String text;
    Spinner spinner;

    HttpPost httppost;
    HttpResponse response;
    HttpClient httpClient;
    List<NameValuePair> nameValuePairs;
    HttpGet httpget;


    ArrayList<String> arrayList_for_layer_spinner = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.layer_spinner);
        iv = (ImageView)findViewById(R.id.qrcode);

        /* Layer Spinner Load 시작 */
        Thread loadThread = new Thread(){
            public void run(){
                contact_server_for_load();
            }
        };

        loadThread.start();
        System.out.println("--- loadThread go!");

        try{
            loadThread.join();
            System.out.println("--- loadThread done!");
        }catch(Exception e){
            e.getMessage();
        }

        ArrayAdapter<String> Adapter_for_layer = new ArrayAdapter<String>(
                this, R.layout.item_spinner, R.id.name, arrayList_for_layer_spinner);

        spinner.setAdapter(Adapter_for_layer);
        //keycode = (EditText)findViewById(R.id.keycode);
        //btn = (Button)findViewById(R.id.btn);
        //search = (Button)findViewById(R.id.search);
        /*
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, CreateQR.class);
                text = keycode.getText().toString();
                intent.putExtra("keycode", text);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, building_search.class);
                startActivity(intent);
            }
        });*/
    }

    void contact_server_for_load(){
        System.out.println("--- 스레드 시작");

        try{

            httpClient = new DefaultHttpClient();
            httppost = new HttpPost("http://14.45.108.71:80/corona_tracker/get_building_array.php");
            response = httpClient.execute(httppost);

            //
            Scanner scan = new Scanner(response.getEntity().getContent());

            String response_string = "";
            while(scan.hasNext()){
                response_string += scan.nextLine();
            }

            System.out.println("---Response : "+response_string);

            String[] token;
            token = response_string.split("\\^");

            for(int i=0;i< token.length;i++){
                System.out.println("token "+i+" : "+ token[i]);
                arrayList_for_layer_spinner.add(token[i]);
            }

        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
    }

    public void on_click_bt(View view) {
        Bitmap bitmap = null ;
        String selected_layer = spinner.getSelectedItem().toString();

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            BitMatrix bitMatrix = multiFormatWriter.encode(selected_layer, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            iv.setImageBitmap(bitmap);
        }catch (Exception e){}

    }
}