package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // UIコンポーネントの初期化
        TextView finalScoreTextView = findViewById(R.id.finalScoreTextView);
        EditText playerNameEditText = findViewById(R.id.playerNameEditText);
        Button retryButton = findViewById(R.id.retryButton);
        Button homeButton = findViewById(R.id.homeButton);
        Button viewRankingButton = findViewById(R.id.viewRankingButton);

        // SharedPreferences から前回の名前を取得
        SharedPreferences prefs = getSharedPreferences("PlayerPrefs", MODE_PRIVATE);
        String savedName = prefs.getString("player_name", "Player"); // デフォルトは "Player"
        playerNameEditText.setText(savedName);

        // スコアを取得
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        finalScoreTextView.setText("スコア: " + score);

        // 🔥 **画面が開いた時にスコアを自動保存**
        if (!savedName.isEmpty()) { // 名前がある場合のみ保存
            RankingManager.saveScore(this, savedName, score);
            Log.d("AutoSave", "スコアが自動保存されました: " + savedName + " - " + score);
        }

        // 🔥 ランキング画面に移動
        viewRankingButton.setOnClickListener(v -> {
            RankingManager.openRankingActivity(ResultActivity.this);
        });

        // 🔥 ホーム画面に戻る
        homeButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        });

        // ✅ **リトライボタンでゲームを再開**
        retryButton.setOnClickListener(v -> {
            Intent retryIntent = new Intent(ResultActivity.this, TypingActivity.class);
            startActivity(retryIntent);
            finish();
        });

        // システムバーのインセットを適用
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
