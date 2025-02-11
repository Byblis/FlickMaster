package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // レイアウトを設定
        setContentView(R.layout.activity_main);

        // UIコンポーネントの初期化
        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        // ボタンのクリックリスナー
        button.setOnClickListener(v -> {
            // テキストを変更
            textView.setText("ボタンが押されました！");
            // TypingActivityに遷移
            Intent intent = new Intent(MainActivity.this, TypingActivity.class);
            startActivity(intent);
        });

        // EdgeToEdgeを有効化（testappの機能）
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            WindowInsetsCompat.Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
