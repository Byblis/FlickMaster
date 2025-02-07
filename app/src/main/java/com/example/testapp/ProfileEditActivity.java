package com.example.testapp;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.ProgressBar;
import android.net.Uri;
import android.widget.Toast;
import com.example.testapp.ProfileEditActivity;



public class ProfileEditActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; // ギャラリー選択リクエストコード

    private EditText editName;
    private Button saveButton;
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
        setContentView(R.layout.activity_profile_edit);

        // ここでビューを取得
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
        saveButton = findViewById(R.id.saveButton);
        displayName = findViewById(R.id.displayName);
        profileImage = findViewById(R.id.profileImage);

        if (profileImage == null || editName == null || saveButton == null) {
            throw new NullPointerException("ビューが正しく初期化されていません。");
        }

        // プロフィール画像タップリスナーを追加
        profileImage.setOnClickListener(v -> openGallery());

        // 保存ボタンのクリック処理
        saveButton.setOnClickListener(v -> {
            String name = editName.getText().toString();
            displayName.setText("名前: " + name);
        });
        // レベル表示UIの取得
        levelText = findViewById(R.id.levelText);
        levelProgressBar = findViewById(R.id.levelProgressBar);

        // 初期表示を更新
        updateLevelUI();

        // 保存ボタンで経験値を増やす
        saveButton.setOnClickListener(v -> {
            addExperience(20); // 例: 保存ごとに20ポイント加算
            String name = editName.getText().toString();
            displayName.setText("名前: " + name);
        });

        Button startGameButton = findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(v -> {
            // ゲーム画面がまだない場合、何もしない
            Toast.makeText(this, "ゲーム画面はまだ準備中です！", Toast.LENGTH_SHORT).show();
        });

    }

    // 経験値を追加してレベルアップを判定
    private void addExperience(int points) {
        experience += points;

        // レベルアップ処理
        if (experience >= experienceToNextLevel) {
            level++;
            experience -= experienceToNextLevel; // 必要経験値を引く
            experienceToNextLevel += 50; // 次のレベルに必要な経験値を増加
        }

        // UIを更新
        updateLevelUI();
    }

    // レベルと経験値バーのUIを更新
    private void updateLevelUI() {
        levelText.setText("レベル: " + level);
        levelProgressBar.setMax(experienceToNextLevel);
        levelProgressBar.setProgress(experience);
    }

    // ギャラリーを開くメソッド
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // 選択した画像を受け取る
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData(); // 画像URIを取得
            profileImage.setImageURI(imageUri); // 画像をImageViewに設定
        }
    }
}
