package com.wasitech.permissioncenter.java.com.wasitech.ting.Noti;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.assist.classes.Tingno;

import java.util.List;

public abstract class DeveloperTingNoChatAdapter extends RecyclerView.Adapter<DeveloperTingNoChatAdapter.ViewHolder> {

    private final List<Tingno> list;

    public DeveloperTingNoChatAdapter(List<Tingno> list) {
        this.list =list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tingno_ting, parent, false);
        return new ViewHolder(view);
    }

    protected abstract boolean onLongClicked(Tingno v);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(list.get(position).getText().trim());
        holder.user.setText(list.get(position).getSender());
        holder.time.setText(list.get(position).getTime());
        holder.itemView.setOnLongClickListener(v->onLongClicked(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView user;
        public TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            time = itemView.findViewById(R.id.tingno_time);
            user = itemView.findViewById(R.id.sender);
        }
    }
}
