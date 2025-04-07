package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class TypingActivity extends AppCompatActivity {
    private int score = 0;
    private String currentText;
    private boolean isGameActive = true;
    private final String[] randomTexts = {"こんにちは", "ありがとう", "さようなら", "すみません", "おはよう"};
    private Random random = new Random();

    private TextView timerTextView;
    private TextView randomTextView;
    private TextView scoreTextView;
    private EditText userInputEditText;
    private Button submitButton;
    private SoundPool soundPool;
    private int mp3a;
    private String playerName;

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

        // プレイヤー名を取得
        playerName = getIntent().getStringExtra("PLAYER_NAME");
        if (playerName == null) {
            playerName = "Player"; // デフォルト
        }

        // SoundPool の初期化
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        } else {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attr)
                    .setMaxStreams(5)
                    .build();
        }
        mp3a = soundPool.load(this, R.raw.a, 1);

        //  初回ランダムテキストを表示
        currentText = getRandomText();
        randomTextView.setText(currentText);

        //  タイマー設定 (60秒)
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("残り時間: " + millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                endGame();
            }
        }.start();

        //  **解答チェック**
        userInputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                checkAnswer();
                return true;
            }
            return false;
        });

        submitButton.setOnClickListener(v -> checkAnswer());
    }

    private void checkAnswer() {
        if (!isGameActive) return;

        String userInput = userInputEditText.getText().toString();
        if (userInput.equals(currentText)) {
            score++;
            addFlickPower(); // 🔥 **正解時に「FlickPower」追加**
            scoreTextView.setText("スコア: " + score);
            currentText = getRandomText();
            randomTextView.setText(currentText);
            userInputEditText.setText("");
            Toast.makeText(this, "正解！", Toast.LENGTH_SHORT).show();
            playCorrectSound();
        } else {
            Toast.makeText(this, "間違っています！もう一度！", Toast.LENGTH_SHORT).show();
        }
    }

    private void playCorrectSound() {
        if (soundPool != null) {
            soundPool.play(mp3a, 1f, 1f, 0, 0, 1f);
        }
    }

    private void addFlickPower() {
        SharedPreferences prefs = getSharedPreferences("FlickMasterPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        int flickPower = prefs.getInt("FlickPower", 0);
        flickPower++; // **1回正解するごとに1ポイント追加**
        editor.putInt("FlickPower", flickPower);
        editor.apply();
    }

    private String getRandomText() {
        return randomTexts[random.nextInt(randomTexts.length)];
    }

    private void endGame() {
        isGameActive = false;
        timerTextView.setText("時間切れ！");
        submitButton.setEnabled(false);
        userInputEditText.setEnabled(false);
        Intent resultIntent = new Intent(TypingActivity.this, ResultActivity.class);
        resultIntent.putExtra("SCORE", score);
        resultIntent.putExtra("PLAYER_NAME", playerName); // 🔹 プレイヤー名も渡す
        startActivity(resultIntent);
        finish();
    }
}
