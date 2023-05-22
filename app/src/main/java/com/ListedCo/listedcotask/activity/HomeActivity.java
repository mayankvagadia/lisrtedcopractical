package com.ListedCo.listedcotask.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ListedCo.listedcotask.R;
import com.ListedCo.listedcotask.adapter.LinksAdapter;
import com.ListedCo.listedcotask.api.Builder.Apibuilder;
import com.ListedCo.listedcotask.api.Interface.ApiInterface;
import com.ListedCo.listedcotask.api.response.RecentLink;
import com.ListedCo.listedcotask.api.response.dashboardNew;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Callback;


public class HomeActivity extends AppCompatActivity {

    RecyclerView rv_links;
    LinksAdapter adapter;
    TextView txt_good_morning, txt_top_links, txt_recent_links;
    LinearLayout ll_progress;
    public ArrayList<RecentLink> recent_links;
    public ArrayList<RecentLink> top_links;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Init();
        ApiCall();
        setGreeting();
        OnclickEvents();

    }

    private void Init() {
        rv_links = findViewById(R.id.rv_product);
        txt_good_morning = findViewById(R.id.txt_good_morning);
        txt_recent_links = findViewById(R.id.recent_links);
        ll_progress = findViewById(R.id.ll_progress);
        txt_top_links = findViewById(R.id.top_links);
        recent_links = new ArrayList<>();
        top_links = new ArrayList<>();
        rv_links.setLayoutManager(new LinearLayoutManager(this));
        rv_links.setHasFixedSize(true);
        adapter = new LinksAdapter(HomeActivity.this);
        rv_links.setAdapter(adapter);
        ll_progress.setVisibility(View.VISIBLE);
    }

    private void OnclickEvents() {
        txt_recent_links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_recent_links.setBackground(getResources().getDrawable(R.drawable.bg_txt_links));
                txt_top_links.setBackground(null);
                txt_top_links.setTextColor(getResources().getColor(R.color.black));
                txt_recent_links.setTextColor(getResources().getColor(R.color.white));
                adapter.setData(recent_links);
            }
        });

        txt_top_links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_top_links.setBackground(getResources().getDrawable(R.drawable.bg_txt_links));
                txt_recent_links.setBackground(null);
                txt_recent_links.setTextColor(getResources().getColor(R.color.black));
                txt_top_links.setTextColor(getResources().getColor(R.color.white));
                adapter.setData(top_links);
            }
        });
    }

    private void setGreeting() {
        Calendar cal = Calendar.getInstance();
        int timeOfDay = cal.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            txt_good_morning.setText("Good Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            txt_good_morning.setText("Good Afternoon");
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            txt_good_morning.setText("Good Evening");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            txt_good_morning.setText("Good Night");
        }
    }

    private void ApiCall() {
        Apibuilder.getRetrofitClient(this).create(ApiInterface.class).dashboardNew().enqueue(new Callback<dashboardNew>() {
            @Override
            public void onResponse(retrofit2.Call<dashboardNew> call, retrofit2.Response<dashboardNew> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        recent_links = response.body().data.recent_links;
                        top_links = response.body().data.top_links;
                        adapter.setData(recent_links);
                    }
                }
                ll_progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(retrofit2.Call<dashboardNew> call, Throwable t) {
                Log.d("ResponseError", t.getMessage());
                ll_progress.setVisibility(View.GONE);
            }
        });
    }
}