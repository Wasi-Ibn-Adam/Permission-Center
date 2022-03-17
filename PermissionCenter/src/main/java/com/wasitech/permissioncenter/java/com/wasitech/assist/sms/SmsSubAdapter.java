package com.wasitech.permissioncenter.java.com.wasitech.assist.sms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.assist.classes.Sms;

import java.util.ArrayList;

public abstract class SmsSubAdapter extends RecyclerView.Adapter<SmsSubAdapter.ReceiverHolder> implements View.OnLongClickListener {
    private final ArrayList<Sms> list;

    public SmsSubAdapter( ArrayList<Sms> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ReceiverHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == Sms.RECEIVER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tinggo_reciever, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tinggo_sender, parent, false);
        }
        return new ReceiverHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getWho();
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiverHolder holder, int position) {
        holder.text.setText(list.get(position).getMsg());
        holder.time.setText(list.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ReceiverHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView time;
        public ReceiverHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.ting_time);
            text = itemView.findViewById(R.id.ting_text);
        }
    }
}