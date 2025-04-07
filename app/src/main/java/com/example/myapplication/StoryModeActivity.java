package com.example.flickmaster;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.myapplication.R;
import android.widget.HorizontalScrollView;
import android.text.Editable;
import android.text.TextWatcher;


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

        storyText = findViewById(R.id.storyTextView);
        inputText = findViewById(R.id.inputText);
        timerText = findViewById(R.id.timerText);
        resultText = findViewById(R.id.resultText);
        storyScrollView = findViewById(R.id.storyScrollView);

        timeLeft = totalTime;
        storyText.setText(story.substring(0, Math.min(30, story.length())));

        // タイマー処理
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

        // スクロール処理
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                long currentTime = System.currentTimeMillis();
                if (lastInputTime != 0) {
                    long interval = currentTime - lastInputTime;
                    int scrollAmount = Math.max(5, 300 - (int) interval);
                    scrollPosition += scrollAmount / 10;
                    storyScrollView.scrollTo(scrollPosition, 0);
                }
                lastInputTime = currentTime;
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }



