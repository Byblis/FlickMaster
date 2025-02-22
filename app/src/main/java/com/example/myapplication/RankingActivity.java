package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Collections;

public class RankingActivity extends AppCompatActivity {
    private RecyclerView rankingRecyclerView;
    private RankingAdapter rankingAdapter;
    private List<RankingEntry> rankingList;
    private Button replayButton, homeButton; // 🔥 ボタンを追加

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rankingRecyclerView = findViewById(R.id.rankingRecyclerView);
        replayButton = findViewById(R.id.replayButton); // 🔥 ボタン取得
        homeButton = findViewById(R.id.homeButton); // 🔥 ボタン取得

        rankingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 🔹 ランキングデータを取得してソート
        rankingList = RankingManager.getRanking(this);
        Collections.sort(rankingList, (a, b) -> b.getScore() - a.getScore());

        // 🔹 順位をつける
        int currentRank = 1;
        for (int i = 0; i < rankingList.size(); i++) {
            if (i > 0 && rankingList.get(i).getScore() == rankingList.get(i - 1).getScore()) {
                rankingList.get(i).setRank(rankingList.get(i - 1).getRank());
            } else {
                rankingList.get(i).setRank(currentRank);
            }
            currentRank = i + 2;
        }

        rankingAdapter = new RankingAdapter(this, rankingList);
        rankingRecyclerView.setAdapter(rankingAdapter);

        // 🔥 **リプレイボタンの処理**
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ButtonCheck", "リプレイボタンが押された！");
                Intent intent = new Intent(RankingActivity.this, GameActivity.class);
                startActivity(intent);
                finish(); // 今の画面を閉じる
            }
        });

        // 🔥 **ホームボタンの処理**
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ButtonCheck", "ホームに戻るボタンが押された！");
                Intent intent = new Intent(RankingActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // 今の画面を閉じる
            }
        });
    }
}





