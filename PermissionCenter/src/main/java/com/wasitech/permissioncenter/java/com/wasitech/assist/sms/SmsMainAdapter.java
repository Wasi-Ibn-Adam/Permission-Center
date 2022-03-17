package com.wasitech.permissioncenter.java.com.wasitech.assist.sms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.ting.Chat.Adapater.TingGoMenuAdapter;
import com.wasitech.contact.classes.ContactSms;

import java.util.ArrayList;

public abstract class SmsMainAdapter extends RecyclerView.Adapter<TingGoMenuAdapter.ViewHolder> implements View.OnLongClickListener  {
    private final ArrayList<ContactSms> list;
    public abstract void onClick(ContactSms contactSms);

    public SmsMainAdapter(ArrayList<ContactSms> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public TingGoMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tinggo_menu, parent, false);
        return new TingGoMenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TingGoMenuAdapter.ViewHolder holder, int position) {
        ContactSms tuple = list.get(position);
        holder.name.setText(tuple.getName());
        //DataBaseHandler handler=new DataBaseHandler(holder.itemView.getContext());
        //int n=db.getTingCount(tuple.getUid());
        //if(n>0) {
        //    holder.counter.setText(n + "");
        //    holder.counter.setVisibility(View.VISIBLE);
        //} else {
        //    holder.counter.setVisibility(View.INVISIBLE);
        //}
        holder.time.setText(tuple.getTime());
        holder.ting.setText(tuple.getMsg());
        holder.itemView.setOnClickListener(v->onClick(tuple));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

