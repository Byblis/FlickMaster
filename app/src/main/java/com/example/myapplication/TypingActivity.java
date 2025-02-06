package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class TypingActivity extends AppCompatActivity {
    private int score = 0; // スコア
    private String currentText; // 現在のランダムテキスト
    private boolean isGameActive = true; // ゲーム中かどうか
    private final String[] randomTexts = {"こんにちは", "ありがとう", "さようなら", "すみません", "おはよう"};
    private Random random = new Random();

    private TextView timerTextView;
    private TextView randomTextView;
    private TextView scoreTextView;
    private EditText userInputEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing);

        // UIコンポーネントの初期化
        timerTextView = findViewById(R.id.timerTextView);
        randomTextView = findViewById(R.id.randomTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        userInputEditText = findViewById(R.id.userInputEditText);
        submitButton = findViewById(R.id.submitButton);

        // 初回ランダムテキストを表示
        currentText = getRandomText();
        randomTextView.setText(currentText);

        // タイマー設定 (60秒)
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("残り時間: " + millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                isGameActive = false; // ゲーム終了
                timerTextView.setText("時間切れ！");
                submitButton.setEnabled(false); // ボタンを無効化

                // 結果画面に遷移
                Intent resultIntent = new Intent(TypingActivity.this, ResultActivity.class);
                resultIntent.putExtra("SCORE", score); // スコアを渡す
                startActivity(resultIntent);
                finish();
            }
        }.start();

        // ボタンクリックで入力チェック
        submitButton.setOnClickListener(v -> {
            if (!isGameActive) return; // ゲーム終了後は無効

            String userInput = userInputEditText.getText().toString();
            if (userInput.equals(currentText)) {
                // 正解の場合
                score++;
                scoreTextView.setText("スコア: " + score);
                currentText = getRandomText();
                randomTextView.setText(currentText);
                userInputEditText.setText("");
                Toast.makeText(this, "正解！", Toast.LENGTH_SHORT).show();
            } else {
                // 不正解の場合
                Toast.makeText(this, "間違っています！もう一度！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ランダムテキストを取得するメソッド
    private String getRandomText() {
        int index = random.nextInt(randomTexts.length);
        return randomTexts[index];
    }
}
