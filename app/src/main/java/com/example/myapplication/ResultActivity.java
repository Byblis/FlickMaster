
package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.core.graphics.Insets;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // レイアウトを設定
        setContentView(R.layout.activity_result);

        // UIコンポーネントの初期化
        TextView finalScoreTextView = findViewById(R.id.finalScoreTextView);
        EditText playerNameEditText = findViewById(R.id.playerNameEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button retryButton = findViewById(R.id.retryButton);
        Button homeButton = findViewById(R.id.homeButton);
        Button viewRankingButton = findViewById(R.id.viewRankingButton);
        Button replayButton = findViewById(R.id.replayButton);

        // SharedPreferences を使って前回の名前を復元
        SharedPreferences prefs = getSharedPreferences("PlayerPrefs", MODE_PRIVATE);
        String savedName = prefs.getString("player_name", "Player"); // デフォルトは "Player"
        playerNameEditText.setText(savedName);

        // スコアを取得
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        finalScoreTextView.setText("スコア: " + score);

        // 🔥 スコアを保存する処理
        saveButton.setOnClickListener(v -> {
            String playerName = playerNameEditText.getText().toString().trim();
            if (playerName.isEmpty()) {
                playerName = "Player"; // 空ならデフォルト名
            }

            // 名前を SharedPreferences に保存
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("player_name", playerName);
            editor.apply();

            // スコアをランキングに保存
            RankingManager.saveScore(this, playerName, score);

            Toast.makeText(this, "スコアが保存されました！", Toast.LENGTH_SHORT).show();
        });

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

        // 🔥 もう一度プレイする（リプレイボタン）
        replayButton.setOnClickListener(v -> {
            Intent replayIntent = new Intent(ResultActivity.this, TypingActivity.class);
            startActivity(replayIntent);
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
