package com.wasitech.permissioncenter.java.com.wasitech.assist.adapter_listeners;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.assist.classes.Conversation;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {
    private final List<Conversation> list;

    public MainRecyclerAdapter(List<Conversation> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ASSIST)
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_recycler_assist, parent, false));
        else
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_recycler_user, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.get(position).getUser().trim().isEmpty()) {
            holder.text.setText(list.get(position).getAssistant().trim());
        } else {
            holder.text.setText(list.get(position).getUser().trim());
        }
    }

    public static final int USER = 0;
    public static final int ASSIST = 1;

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getUser().trim().isEmpty())
            return ASSIST;
        else
            return USER;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
