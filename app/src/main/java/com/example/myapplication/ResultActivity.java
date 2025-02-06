package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
        Button retryButton = findViewById(R.id.retryButton);

        // 前のアクティビティからスコアを取得
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        finalScoreTextView.setText("スコア: " + score);

        // 「もう一度プレイ」ボタンのクリックリスナー
        retryButton.setOnClickListener(v -> {
            Intent retryIntent = new Intent(ResultActivity.this, TypingActivity.class);
            startActivity(retryIntent);
            finish(); // 結果画面を終了
        });

        // システムバーのインセット適用
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
