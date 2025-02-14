package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.TypingActivity; // ✅ TypingActivity をインポート！

public class ProfileEditActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; // ギャラリー選択リクエストコード

    private EditText editName;
    private Button saveProfileButton; // ✅ IDを修正
    private TextView displayName;
    private ImageView profileImage;
    private int level = 1; // 現在のレベル
    private int experience = 0; // 現在の経験値
    private int experienceToNextLevel = 100; // 次のレベルに必要な経験値

    // レベル表示用ビュー
    private TextView levelText;
    private ProgressBar levelProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit); // ✅ レイアウトを適用

        // 画面のメインビュー取得
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } else {
            Log.e("ProfileEditActivity", "mainView が null です");
        }

        // 各ビューを取得
        editName = findViewById(R.id.editName);
        saveProfileButton = findViewById(R.id.saveProfileButton); // ✅ 修正済み
        displayName = findViewById(R.id.displayName);
        profileImage = findViewById(R.id.profileImage);
        levelText = findViewById(R.id.levelText);
        levelProgressBar = findViewById(R.id.levelProgressBar);

        // UIが正しく取得できているか確認
        if (profileImage == null || editName == null || saveProfileButton == null) {
            throw new NullPointerException("ビューが正しく初期化されていません。");
        }

        // プロフィール画像タップリスナー（ギャラリーを開く）
        profileImage.setOnClickListener(v -> openGallery());

        // 保存ボタンのクリックリスナー
        saveProfileButton.setOnClickListener(v -> {
            String name = editName.getText().toString();
            displayName.setText("名前: " + name);
            addExperience(20); // ✅ 経験値を追加
        });

        // ✅ 「ゲームを始める」ボタンの取得と設定
        Button startGameButton = findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileEditActivity.this, TypingActivity.class);
            startActivity(intent);
        });

        // UIの初期更新
        updateLevelUI();
    }

    // 🎮 経験値を追加し、レベルアップ処理
    private void addExperience(int points) {
        experience += points;
        if (experience >= experienceToNextLevel) {
            level++;
            experience -= experienceToNextLevel; // 必要経験値を減らす
            experienceToNextLevel += 50; // 次のレベルに必要な経験値を増加
        }
        updateLevelUI(); // UIを更新
    }

    // 📊 レベルと経験値バーのUIを更新
    private void updateLevelUI() {
        levelText.setText("レベル: " + level);
        levelProgressBar.setMax(experienceToNextLevel);
        levelProgressBar.setProgress(experience);
    }

    // 🖼 ギャラリーを開く
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // 📷 選択した画像をImageViewにセット
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }
}

