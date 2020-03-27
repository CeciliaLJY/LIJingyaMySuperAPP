package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private Button btn_add;
    private ProgressBar pb;
    private TextView textView;
    private String data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.pb);
        btn_add = (Button) findViewById(R.id.btn_add);
        textView = (TextView) findViewById(R.id.textView);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.GONE);
                callNetwork();
            }
        });
    }


    private void callNetwork(){


        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url("https://catfact.ninja/fact").get().build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("MainActivity", "", e);

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                Log.d("MainActivity", "got" + json);
                data = json;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(data);
                    }
                });
            }
        });
    }
}
