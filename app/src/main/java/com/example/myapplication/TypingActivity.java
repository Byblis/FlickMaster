package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
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
    private CountDownTimer countDownTimer; // タイマー管理用

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
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("残り時間: " + millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                endGame(); // ゲーム終了処理
            }
        }.start();

        // 🔹 **Enter キーが押されたときにチェック**
        userInputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                checkAnswer();
                return true;
            }
            return false;
        });

        // 🔹 **送信ボタンを押したときにチェック**
        submitButton.setOnClickListener(v -> checkAnswer());
    }

    // 🔹 **ゲーム終了処理**
    private void endGame() {
        isGameActive = false; // ゲーム終了
        timerTextView.setText("時間切れ！");
        submitButton.setEnabled(false); // ボタンを無効化
        userInputEditText.setEnabled(false); // 入力を無効化

        // 🔹 **結果画面に遷移**
        Intent resultIntent = new Intent(TypingActivity.this, ResultActivity.class);
        resultIntent.putExtra("SCORE", score); // スコアを渡す
        startActivity(resultIntent);
        finish(); // 現在のアクティビティを終了
    }

    // 🔹 **解答チェック**
    private void checkAnswer() {
        if (!isGameActive) return; // ゲーム終了後は無効

        String userInput = userInputEditText.getText().toString();
        if (userInput.equals(currentText)) {
            // **正解！スコアを加算**
            score++;
            scoreTextView.setText("スコア: " + score);
            currentText = getRandomText();
            randomTextView.setText(currentText);
            userInputEditText.setText(""); // 入力をリセット
            Toast.makeText(this, "正解！", Toast.LENGTH_SHORT).show();
        } else {
            // **不正解**
            Toast.makeText(this, "間違っています！もう一度！", Toast.LENGTH_SHORT).show();
        }
    }

    // **ランダムテキストを取得するメソッド**
    private String getRandomText() {
        int index = random.nextInt(randomTexts.length);
        return randomTexts[index];
    }

    // **画面が閉じるときにタイマーをキャンセル**
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
