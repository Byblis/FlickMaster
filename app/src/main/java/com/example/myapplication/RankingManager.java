package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.example.myapplication.RankingEntry;
import android.content.Intent;





public class RankingManager {
        private static final String PREFS_NAME = "RankingPrefs";
        private static final String KEY_RANKING = "ranking";
        private static final int MAX_RANKINGS = 10;

        public static void saveScore(Context context, String username, int score) {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            // 既存のランキングを取得
            List<RankingEntry> rankingList = getRanking(context);

            // 新しいスコアを追加
            rankingList.add(new RankingEntry(username, score));

            // スコアの高い順に並べる
            Collections.sort(rankingList, (a, b) -> Integer.compare(b.getScore(), a.getScore()));

            // 最大数を超えた場合は削除
            if (rankingList.size() > MAX_RANKINGS) {
                rankingList = rankingList.subList(0, MAX_RANKINGS);
            }

            // 保存するためのデータ変換
            StringBuilder rankingData = new StringBuilder();
            for (RankingEntry entry : rankingList) {
                rankingData.append(entry.getUsername()).append(":").append(entry.getScore()).append(",");
            }

            // データを保存
            editor.putString(KEY_RANKING, rankingData.toString());
            editor.apply();
        }

        public static List<RankingEntry> getRanking(Context context) {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String rankingData = prefs.getString(KEY_RANKING, "");

            List<RankingEntry> rankingList = new ArrayList<>();
            if (!rankingData.isEmpty()) {
                String[] entries = rankingData.split(",");
                for (String entry : entries) {
                    String[] parts = entry.split(":");
                    if (parts.length == 2) {
                        String username = parts[0];
                        int score;
                        try {
                            score = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            continue;
                        }
                        rankingList.add(new RankingEntry(username, score));
                    }
                }
            }
            return rankingList;
        }
        public static void openRankingActivity(Context context) {
            Intent intent = new Intent(context, RankingActivity.class);
            context.startActivity(intent);
        }
    }



