package com.example.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import android.widget.TextView;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    private List<RankingEntry> rankingList;

    public RankingAdapter(List<RankingEntry> rankingList) {
        this.rankingList = rankingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ranking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RankingEntry entry = rankingList.get(position);

        // 🔥 順位・名前・スコアをセット
        holder.rankTextView.setText(String.valueOf(position + 1));
        holder.playerNameTextView.setText(entry.getUsername());
        holder.scoreTextView.setText(entry.getScore() + "点");

        // 🔥 順位ごとに背景色を変える
        if (position == 0) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFD700")); // ゴールド
        } else if (position == 1) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C0C0C0")); // シルバー
        } else if (position == 2) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#CD7F32")); // ブロンズ
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE); // 通常の背景
        }
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rankTextView, playerNameTextView, scoreTextView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            rankTextView = itemView.findViewById(R.id.rankTextView);
            playerNameTextView = itemView.findViewById(R.id.playerNameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}

