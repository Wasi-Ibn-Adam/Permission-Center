package com.wasitech.permissioncenter.java.com.wasitech.assist.adapter_listeners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;

import java.util.List;

public class PolicyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<String>list;
    Context context;
    public PolicyAdapter(@NonNull Context context,@NonNull List<String> objects) {
        list= objects;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.row_privacy_policy,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextView)holder.itemView.findViewById(R.id.policy_list_text)).setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private static class Holder extends RecyclerView.ViewHolder{
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
