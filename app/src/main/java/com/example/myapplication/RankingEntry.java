package com.example.myapplication;

public class RankingEntry {
    private String username;
    private int score;
    private int rank = 0;  // ✅ 初期値を `0` にする！


    // **コンストラクタ**
    public RankingEntry(String username, int score) {
        this.username = username;
        this.score = score;
        this.rank = -1; // 🔥 初期値は -1（未設定）
    }

    // **ゲッター**
    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getRank() {
        return rank;
    }

    // **セットメソッド**
    public void setRank(int rank) {
        this.rank = rank;
    }
}

