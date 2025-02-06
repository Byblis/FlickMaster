package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TextViewとButtonを取得
        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        // ボタンのクリックイベントを設定
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // テキスト変更
                textView.setText("ボタンが押されました！");
                // TypingActivityに遷移
                Intent intent = new Intent(MainActivity.this, TypingActivity.class);
                startActivity(intent);
            }
        });

        // システムバーのインセット適用
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
