package com.example.testapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import com.example.testapp.ProfileEditActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button startGameButton = findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, ProfileEditActivity.class);
            startActivity(intent);
        });

        // 仮のデータ
        int mockScore = 100; // 仮のスコア
        int mockExperienceGained = 20; // 仮の経験値

        // スコアと経験値の表示
        TextView scoreText = findViewById(R.id.scoreText);
        TextView experienceText = findViewById(R.id.experienceText);
        scoreText.setText("スコア: " + mockScore + "点");
        experienceText.setText("+" + mockExperienceGained + " 経験値");

        // プロフィールに戻るボタン
        Button backToProfileButton = findViewById(R.id.backToProfileButton);
        backToProfileButton.setOnClickListener(v -> {
            // プロフィール画面に戻る
            Intent intent = new Intent(ResultActivity.this, ProfileEditActivity.class);
            startActivity(intent);
            finish();
        });
    }
}