package com.wasitech.permissioncenter.java.com.wasitech.contact.pop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wasitech.assist.R;
import com.wasitech.assist.popups.ThemePopUp;
import com.wasitech.basics.classes.Format;
import com.wasitech.basics.classes.Issue;
import com.wasitech.contact.adapter.ContactDetailAdapter;
import com.wasitech.contact.classes.Contact;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class ContactPopUp extends ThemePopUp implements ContactDetailAdapter.DetailContact{
    private RecyclerView recyclerView;
    private ImageButton message;
    private final ArrayList<String> selectedNumbs;
    private final Contact contact;
    private final Context context;

    public ContactPopUp(Context context, Contact contact) {
        super(context, R.layout.pop_up_contact_view);
        this.contact = contact;
        this.context = context;
        selectedNumbs = new ArrayList<>();
        showAtLocation(view, Gravity.CENTER, 1, 1);
        setViews(view);
        setActions(view);
    }

    private void setViews(View view) {
        CircleImageView img = view.findViewById(R.id.con_img);
        Glide.with(context)
                .asBitmap()
                .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                .load(Contact.getPic(context, contact.getId()))
                .into(img);
        ((TextView) view.findViewById(R.id.name_txt)).setText(contact.getName());
        message = view.findViewById(R.id.sms_btn);
        message.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.pop_up_con_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    private void setActions(View view) {
        view.findViewById(R.id.cancel_btn).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.share_btn).setOnClickListener(v -> onShare(context, contact.getId(), contact.getName()));
        message.setOnClickListener(v -> onSms(Format.toString(selectedNumbs)));
        recyclerView.setAdapter(new ContactDetailAdapter(contact.getNumbers(), false,this));
    }

    @Override
    public void onLongClick(boolean visible) {
        if (visible) {
            message.setVisibility(View.VISIBLE);
        } else {
            message.setVisibility(View.GONE);
        }
    }

    private void onShare(Context context, String id, String name) {
        try {
            String lookupKey = Contact.lookupKey(getContentView().getContext().getApplicationContext(), id);
            Uri vcardUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType(ContactsContract.Contacts.CONTENT_VCARD_TYPE);
            intent.putExtra(Intent.EXTRA_STREAM, vcardUri);
            intent.putExtra(Intent.EXTRA_SUBJECT, name); // put the name of the contact here
            context.startActivity(intent);
        } catch (Exception e) {
            Issue.print(e, ContactPopUp.class.getName());
        }
        dismiss();
    }

    @Override
    public void checkedInOut(String num, boolean add) {
        if (add)
            selectedNumbs.add(num);
        else
            selectedNumbs.remove(num);
    }
}
