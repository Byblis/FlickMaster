package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import android.widget.TextView;
import java.util.List;
import android.util.Log;
import androidx.core.content.ContextCompat;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    private List<RankingEntry> rankingList;
    private final Context context;

    public RankingAdapter(Context context, List<RankingEntry> rankingList) {
        this.context = context;
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

        // **🔥 デフォルトのデザインをリセット**
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        holder.rankTextView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        holder.rankTextView.setText(String.valueOf(entry.getRank())); // 順位をセット

        // **🔥 1位～3位のデザインを変更**
        switch (entry.getRank()) {
            case 1:
                holder.rankTextView.setText("🥇 " + entry.getRank());
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_gold));
                holder.rankTextView.setTextColor(ContextCompat.getColor(context, R.color.gold));
                break;
            case 2:
                holder.rankTextView.setText("🥈 " + entry.getRank());
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_silver));
                holder.rankTextView.setTextColor(ContextCompat.getColor(context, R.color.silver));
                break;
            case 3:
                holder.rankTextView.setText("🥉 " + entry.getRank());
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_bronze));
                holder.rankTextView.setTextColor(ContextCompat.getColor(context, R.color.bronze));
                break;
            default:
                holder.rankTextView.setText(String.valueOf(entry.getRank()));
                break;
        }

        // 🔹 プレイヤー名とスコアをセット
        holder.playerNameTextView.setText(entry.getUsername());
        holder.scoreTextView.setText(entry.getScore() + "点");
    }

    @Override
    public int getItemCount() {
        Log.d("RankingAdapter", "リストのアイテム数: " + rankingList.size());
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


