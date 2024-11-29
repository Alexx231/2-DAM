package com.example.tapgamealejandropawlukiewicz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tapgamealejandropawlukiewicz.UserData;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    private List<UserData> rankingList;

    public RankingAdapter(List<UserData> rankingList) {
        this.rankingList = rankingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserData userData = rankingList.get(position);
        holder.text1.setText(String.format("%d. %s", position + 1, userData.getUsername()));
        holder.text2.setText(String.format("Puntuación: %d", userData.getHighScore()));
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;

        ViewHolder(View view) {
            super(view);
            text1 = view.findViewById(android.R.id.text1);
            text2 = view.findViewById(android.R.id.text2);
        }
    }
}