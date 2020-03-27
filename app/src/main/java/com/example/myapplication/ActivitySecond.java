package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Formattable;
import java.util.List;



public class ActivitySecond extends AppCompatActivity {

    private RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        myRecyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getJsonFact();
    }

    private void setRecyclerViewData(List<CatFactList> list) {
        myRecyclerView.setAdapter(new RecycleAdapterDome(list));
    }

    private void getJsonFact(){
        String url = "https://catfact.ninja/facts?limit=30";


        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("ActivitySecond", "", e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                final List<CatFactList> fact = parseCatFact(json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setRecyclerViewData(fact);
                    }
                });

                Log.d("ActivitySecond", "got" + json);

            }
        });
    }

    private List<CatFactList> parseCatFact(String json) {
        List<CatFactList> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.getJSONArray("data");
            for(int i = 0; i < array.length(); ++i) {
                JSONObject fact = array.getJSONObject(i);
                String factStr = fact.getString("fact");
                list.add(new CatFactList(factStr));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}