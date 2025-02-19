package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    private RecyclerView rankingRecyclerView;
    private RankingAdapter rankingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        // 🔥 RecyclerView の設定
        rankingRecyclerView = findViewById(R.id.rankingRecyclerView);
        rankingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 🔥 ランキングデータを取得してセット
        List<RankingEntry> rankingList = RankingManager.getRanking(this);
        rankingAdapter = new RankingAdapter(rankingList);
        rankingRecyclerView.setAdapter(rankingAdapter);
    }
}
