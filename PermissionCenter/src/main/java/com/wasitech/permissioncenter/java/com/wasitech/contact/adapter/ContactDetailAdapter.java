package com.wasitech.permissioncenter.java.com.wasitech.contact.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.contact.classes.Contact;

import java.util.ArrayList;

public class ContactDetailAdapter extends RecyclerView.Adapter<ContactDetailAdapter.ViewHolder> {

    private final ArrayList<Contact.Number> numbers;
    private boolean show;
    private final DetailContact action;
    public ContactDetailAdapter(ArrayList<Contact.Number> numbers, boolean visible, DetailContact action) {
        this.action=action;
        this.numbers = numbers;
        show = visible;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact_detail, parent, false);
        ViewHolder holder=new ViewHolder(view);
        view.setOnLongClickListener(v -> {
            show = !show;
            action.onLongClick(show);
            notifyDataSetChanged();
            return false;
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.type.setText(numbers.get(position).getType());
        holder.number.setText(numbers.get(position).getNumber());
        holder.box.setId(position);
        holder.sms.setOnClickListener(v -> action.onSms(numbers.get(position).getNumber()));
        holder.call.setOnClickListener(v -> action.onCall(numbers.get(position).getNumber()));
        holder.box.setOnCheckedChangeListener((buttonView, isChecked) -> action.checkedInOut(numbers.get(position).getNumber(), isChecked));
        if (show) {
            holder.call.setVisibility(View.GONE);
            holder.sms.setVisibility(View.GONE);
            holder.box.setVisibility(View.VISIBLE);
        } else {
            holder.box.setVisibility(View.GONE);
            holder.call.setVisibility(View.VISIBLE);
            holder.sms.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (show) {
                holder.box.setChecked(!holder.box.isChecked());
                notifyDataSetChanged();
            } else
                action.onSelected(numbers.get(position).getNumber());
        });
    }
    @Override
    public int getItemCount() {
        return numbers.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView type;
        public TextView number;
        public CheckBox box;
        public ImageView call;
        public ImageView sms;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type_txt);
            number = itemView.findViewById(R.id.number_txt);
            call = itemView.findViewById(R.id.call_btn);
            sms = itemView.findViewById(R.id.sms_btn);
            box = itemView.findViewById(R.id.share_box);
        }
    }

    public interface DetailContact{
        void onSms(String num);
        void onCall(String num);
        void checkedInOut(String num, boolean add);
        void onSelected(String number);
        void onLongClick(boolean visible);
    }
}
