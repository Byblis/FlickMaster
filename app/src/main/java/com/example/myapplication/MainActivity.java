package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testapp.ProfileEditActivity; //  testappパッケージのProfileEditActivityをインポート！
import com.example.flickmaster.StoryModeActivity;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    private EditText playerNameEditText;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 🔹 UIコンポーネントの初期化
        playerNameEditText = findViewById(R.id.playerNameEditText);
        Button startButton = findViewById(R.id.startButton);
        Button rankingButton = findViewById(R.id.rankingButton);
        Button gardenButton = findViewById(R.id.gardenButton);
        Button profileButton = findViewById(R.id.profileButton);

        // 🔹 SharedPreferences を取得
        prefs = getSharedPreferences("PlayerPrefs", MODE_PRIVATE);
        String savedName = prefs.getString("player_name", "Player");
        playerNameEditText.setText(savedName);

        //  **スタートボタンのクリック処理**
        startButton.setOnClickListener(v -> {
            String playerName = playerNameEditText.getText().toString().trim();
            if (playerName.isEmpty()) {
                playerName = "Player";
            }

            //  プレイヤー名を SharedPreferences に保存
            prefs.edit().putString("player_name", playerName).apply();

            //  ゲーム画面へ遷移し、プレイヤー名を渡す
            Intent intent = new Intent(MainActivity.this, TypingActivity.class);
            intent.putExtra("PLAYER_NAME", playerName);
            startActivity(intent);
        });

        //  **ランキングボタンのクリック処理**
        rankingButton.setOnClickListener(v -> RankingManager.openRankingActivity(MainActivity.this));

        //  **庭へ行くボタンのクリック処理**
        gardenButton.setOnClickListener(v -> {
            String playerName = playerNameEditText.getText().toString().trim();
            if (playerName.isEmpty()) {
                playerName = "Player";
            }

            // 🔹 庭画面へプレイヤー名を渡して遷移
            Intent intent = new Intent(MainActivity.this, GardenActivity.class);
            intent.putExtra("PLAYER_NAME", playerName);
            startActivity(intent);
        });

        //  **プロフィール編集ボタンのクリック処理**
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileEditActivity.class); // 🔥 ProfileActivity → ProfileEditActivity に修正！
            startActivity(intent);
        });

        Button storyModeButton = findViewById(R.id.storyModeButton);
        storyModeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StoryModeActivity.class);
            startActivity(intent);
        });
    }
}

