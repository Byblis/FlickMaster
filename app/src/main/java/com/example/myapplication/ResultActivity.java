package com.example.myapplication;

import android.content.Intent;
import androidx.core.graphics.Insets;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
        Button retryButton = findViewById(R.id.retryButton);
        Button homeButton = findViewById(R.id.homeButton); // ✅ ホームボタンを追加

        // スコアを表示
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        finalScoreTextView.setText("スコア: " + score);

        // リトライボタンのクリックリスナーを設定
        retryButton.setOnClickListener(v -> {
            Intent retryIntent = new Intent(ResultActivity.this, TypingActivity.class);
            startActivity(retryIntent);
            finish(); // 現在の画面を終了
        });

        // ✅ ホームボタンのクリックリスナーを追加
        homeButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish(); // 現在の画面を終了
        });

        // システムバーのインセットを適用（もし必要なら）
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}
