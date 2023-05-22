package com.ListedCo.listedcotask.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ListedCo.listedcotask.adapter.LinksAdapter;
import com.ListedCo.listedcotask.api.Builder.Apibuilder;
import com.ListedCo.listedcotask.api.Interface.ApiInterface;
import com.ListedCo.listedcotask.api.response.dashboardNew;
import com.ListedCo.listedcotask.R;

import retrofit2.Callback;


public class HomeActivity extends AppCompatActivity {

    RecyclerView rv_links;
    LinksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rv_links = findViewById(R.id.rv_product);
        rv_links.setLayoutManager(new LinearLayoutManager(this));
        rv_links.setHasFixedSize(true);

        Apibuilder.getRetrofitClient(this).create(ApiInterface.class).dashboardNew().enqueue(new Callback<dashboardNew>() {
            @Override
            public void onResponse(retrofit2.Call<dashboardNew> call, retrofit2.Response<dashboardNew> response) {
                if (response.isSuccessful()) {
                    adapter = new LinksAdapter(HomeActivity.this, response.body().data.recent_links);
                    rv_links.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<dashboardNew> call, Throwable t) {
                Log.d("Res", t.getMessage());
            }
        });
    }
}