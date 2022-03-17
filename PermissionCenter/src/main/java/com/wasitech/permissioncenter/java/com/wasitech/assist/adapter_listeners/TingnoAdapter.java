package com.wasitech.permissioncenter.java.com.wasitech.assist.adapter_listeners;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.assist.classes.Tingno;
import com.wasitech.basics.app.ProcessApp;

import java.util.List;

public class TingnoAdapter extends RecyclerView.Adapter<TingnoAdapter.ViewHolder>{

    private final List<Tingno> list;
    public TingnoAdapter( List<Tingno> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tingno,parent,false);
        return new ViewHolder(themeView(view));
    }

    private View themeView(View view){
        if(ProcessApp.isDarkTheme()){
            ((TextView)view.findViewById(R.id.tingno_sender)).setTextColor(Color.WHITE);
            ((TextView)view.findViewById(R.id.tingno_text)).setTextColor(Color.WHITE);
            ((TextView)view.findViewById(R.id.tingno_state)).setTextColor(Color.WHITE);
        }
        return view;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(list.get(position).getText().trim());
        holder.user.setText(list.get(position).getSender().split(" ")[0]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView user;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text =itemView.findViewById(R.id.tingno_text);
            user=itemView.findViewById(R.id.tingno_sender);
        }
    }
}
