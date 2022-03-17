package com.wasitech.permissioncenter.java.com.wasitech.contact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wasitech.assist.R;
import com.wasitech.basics.classes.Issue;
import com.wasitech.contact.classes.Contact;
import com.wasitech.theme.Theme;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements Filterable {
    public ArrayList<Contact> list;
    private final ContactFilter filter;

    public ContactAdapter(ArrayList<Contact> list) {
        this.list = list;
        listSize(list.size());
        filter = new ContactFilter(this, list);
    }

    public ArrayList<Contact> getList() {
        return list;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact, parent, false);
        return new ViewHolder(view);
    }

    public abstract void onClick(Contact contact);

    public void listSize(int size) { }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.name.setText(list.get(position).getName());
            holder.count.setText(list.get(position).size()+"");
            Context context = holder.itemView.getContext();
            String id = list.get(position).getId();
            Glide.with(context).asBitmap().load(Contact.getPic(context, id)).placeholder(R.drawable.com_facebook_profile_picture_blank_portrait).circleCrop().into(holder.img);
            holder.itemView.setAnimation(new AlphaAnimation(0.0f, 1.0f));
            holder.itemView.setOnClickListener(v -> onClick(list.get(position)));
        } catch (Exception e) {
            Issue.print(e, ContactAdapter.class.getName());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,count;
        public CircleImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_txt);
            count = itemView.findViewById(R.id.count_txt);
            img = itemView.findViewById(R.id.contact_img);
            try{
                Theme.textView(name);
                Theme.textView(count);
            }
            catch (Exception e){
                Issue.print(e,getClass().getName());
            }
        }
    }

    protected static class ContactFilter extends Filter {
        private final  ContactAdapter adapter;
        private final ArrayList<Contact> list;

        public ContactFilter(ContactAdapter adapter, ArrayList<Contact> list) {
            this.adapter = adapter;
            this.list = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Contact> model = new ArrayList<>();
                for (Contact s:list)
                    if (s.contains(constraint.toString())) model.add(s);
                results.count = model.size();
                results.values = model;
            } else {
                results.count = list.size();
                results.values = list;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.list = (ArrayList<Contact>) results.values;
            adapter.notifyDataSetChanged();
            adapter.listSize(results.count);
        }
    }

}
