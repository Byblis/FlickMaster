package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText playerNameEditText;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UIコンポーネントの初期化
        TextView textView = findViewById(R.id.textView);
        Button startButton = findViewById(R.id.startButton);
        Button rankingButton = findViewById(R.id.rankingButton);
        playerNameEditText = findViewById(R.id.playerNameEditText);

        // SharedPreferences を取得
        prefs = getSharedPreferences("PlayerPrefs", MODE_PRIVATE);
        String savedName = prefs.getString("player_name", "Player"); // デフォルトは "Player"
        playerNameEditText.setText(savedName);

        // スタートボタンのクリックリスナー
        startButton.setOnClickListener(v -> {
            String playerName = playerNameEditText.getText().toString().trim();
            if (playerName.isEmpty()) {
                playerName = "Player"; // 名前が空ならデフォルト
            }

            // プレイヤー名を SharedPreferences に保存
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("player_name", playerName);
            editor.apply();

            // 🔥 ゲーム画面へ遷移し、プレイヤー名を渡す
            Intent intent = new Intent(MainActivity.this, TypingActivity.class);
            intent.putExtra("PLAYER_NAME", playerName);
            startActivity(intent);
        });

        // ランキングボタンのクリックリスナー
        rankingButton.setOnClickListener(v -> {
            RankingManager.openRankingActivity(MainActivity.this);
        });
    }
}

