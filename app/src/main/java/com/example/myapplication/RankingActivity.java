package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import android.widget.Button; // ✅ これがないとエラーになる！
import android.content.Intent;



public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        // UIコンポーネント
        TextView rankingTextView = findViewById(R.id.rankingTextView);
        Button replayButton = findViewById(R.id.replayButton); // 🔥 リプレイボタン取得
        Button homeButton = findViewById(R.id.homeButton); // 🔥 ホームボタン取得

        // ランキングデータを取得
        List<RankingEntry> rankingList = RankingManager.getRanking(this);
        StringBuilder rankingText = new StringBuilder();

        // ランキングリストを画面に表示
        if (rankingList.isEmpty()) {
            rankingText.append("ランキングデータがありません");
        } else {
            for (int i = 0; i < rankingList.size(); i++) {
                RankingEntry entry = rankingList.get(i);
                rankingText.append((i + 1)).append(". ")
                        .append(entry.getUsername()).append(" - ")
                        .append(entry.getScore()).append("点\n");
            }
        }

        rankingTextView.setText(rankingText.toString());
        // 🔥 リプレイボタンの処理
        replayButton.setOnClickListener(v -> {
            Intent replayIntent = new Intent(RankingActivity.this, TypingActivity.class);
            startActivity(replayIntent);
            finish();
        });

        // 🔥 ホームに戻るボタンの処理
        homeButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(RankingActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        });
    }
}
