package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GardenActivity extends AppCompatActivity {
    private int flickPower;   // 🌱 **成長ポイント (FlickPower)**
    private int growthStage;  // 成長段階（0〜3）

    private ImageView plantImageView;
    private TextView plantStatusTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);

        // 🔹 UIコンポーネントの初期化
        plantImageView = findViewById(R.id.plantImageView);
        plantStatusTextView = findViewById(R.id.plantStatusTextView);
        backButton = findViewById(R.id.backButton);

        // 🔹 **保存された FlickPower データを取得**
        SharedPreferences prefs = getSharedPreferences("FlickMasterPrefs", MODE_PRIVATE);
        flickPower = prefs.getInt("FlickPower", 0);
        growthStage = prefs.getInt("GROWTH_STAGE", 0);

        updatePlant(); // 🌱 成長段階を更新

        // 🔹 **戻るボタンでメイン画面に戻る**
        backButton.setOnClickListener(v -> finish());
    }

    // 🌱 **FlickPower を使って植物の成長をチェック＆更新**
    private void updatePlant() {
        if (flickPower >= 50) {
            growthStage = 3;  // 最終形態
            plantStatusTextView.setText("🌿 立派に成長しました！");
        } else if (flickPower >= 30) {
            growthStage = 2;  // 中成長
            plantStatusTextView.setText("🌱 だんだん大きくなってきた！");
        } else if (flickPower >= 10) {
            growthStage = 1;  // 少し成長
            plantStatusTextView.setText("🌱 芽が出たよ！");
        } else {
            growthStage = 0;  // 初期状態
            plantStatusTextView.setText("🌱 まだ小さい… タイピングで育てよう！");
        }

        // 🔹 **成長データを保存**
        SharedPreferences.Editor editor = getSharedPreferences("FlickMasterPrefs", MODE_PRIVATE).edit();
        editor.putInt("GROWTH_STAGE", growthStage);
        editor.apply();
    }
}
