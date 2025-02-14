package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.testapp.ProfileEditActivity; // ✅ 正しいパス！

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UIコンポーネントの初期化
        TextView textView = findViewById(R.id.textView);
        Button startButton = findViewById(R.id.startButton);
        Button profileButton = findViewById(R.id.profileButton);

        // スタートボタンのクリックリスナー
        startButton.setOnClickListener(v -> {
            textView.setText("ボタンが押されました！");

            // TypingActivity へ遷移
            Intent intent = new Intent(MainActivity.this, TypingActivity.class);
            startActivity(intent);
        });

        // プロフィールボタンのクリックリスナー
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileEditActivity.class);
            startActivity(intent);
        });
    }
}
