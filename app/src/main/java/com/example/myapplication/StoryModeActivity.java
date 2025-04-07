package com.example.flickmaster;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.myapplication.R;

public class StoryModeActivity extends Activity {

    private TextView storyText;
    private EditText inputText;
    private TextView timerText;
    private TextView resultText;

    private CountDownTimer timer;
    private final int totalTime = 60; // 秒
    private String story = "あるひ　ちいさなまちの　すみっこで　くろいこねこが　めをさました　そのなまえは　カルモ　しっぽは　すこしだけ　くるんとまがっている　そして　ひとみは　きらきらと　ひかっていた　きょうは　だいすきな　ひかりのにわへいく　とくべつなひ　カルモは　ゆっくりと　のびをして　おおきなあくびをすると　たいせつにしていた　ふろしきに　おやつをつつんだ　にゃっ　ぼうけんの　じゅんびは　ばっちり！";
    private int timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_mode);

        storyText = findViewById(R.id.storyText);
        inputText = findViewById(R.id.inputText);
        timerText = findViewById(R.id.timerText);
        resultText = findViewById(R.id.resultText);

        timeLeft = totalTime;
        storyText.setText(story.substring(0, Math.min(30, story.length())));

        timer = new CountDownTimer(totalTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft--;
                timerText.setText("残り: " + timeLeft + "秒");
            }
            @Override
            public void onFinish() {
                inputText.setEnabled(false);
                showResult();
            }
        };
        timer.start();
    }
    private void showResult() {
        String input = inputText.getText().toString();
        int correct = 0;
        int minLength = Math.min(input.length(), story.length());

        for (int i = 0; i < minLength; i++) {
            if (input.charAt(i) == story.charAt(i)) {
                correct++;
            }
        }

        int mistakes = input.length() - correct;
        double speed = (double) input.length() / (totalTime - timeLeft);

        resultText.setText("✔ 正解: " + correct + "文字\n" +
                "❌ ミス: " + mistakes + "文字\n" +
                "⚡ スピード: " + String.format("%.2f", speed) + "文字/秒");
    }
}


