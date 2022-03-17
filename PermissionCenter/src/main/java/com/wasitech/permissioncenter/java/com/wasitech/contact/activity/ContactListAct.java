package com.wasitech.permissioncenter.java.com.wasitech.contact.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wasitech.assist.R;
import com.wasitech.assist.sms.SmsSubActivity;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.classes.MultiFun;
import com.wasitech.contact.adapter.ContactAdapter;
import com.wasitech.contact.classes.Contact;
import com.wasitech.contact.pop.ContactPopUp;
import com.wasitech.contact.pop.DialerPopUp;
import com.wasitech.contact.runnable.ContactRunnable;
import com.wasitech.database.Params;
import com.wasitech.permission.Permission;
import com.wasitech.permission.PermissionGroup;
import com.wasitech.theme.Theme;

import org.jetbrains.annotations.Nullable;

public class ContactListAct extends AssistCompatActivity {
    private TelephonyManager manager;
    private ContactAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton call;
    private LinearLayout layout;

    private static String number, name;

    public ContactListAct() {
        super(R.layout.act_general_list_refresh);
    }

    @Override
    protected String titleBarText() {
        return "Contacts";
    }

    @Override
    public void setViews() {
        try {
            manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            recyclerView = findViewById(R.id.recyclerView);
            call = findViewById(R.id.refresh);
            layout = findViewById(R.id.empty_layout);
            setBack();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues() {
        try {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            call.setImageResource(R.drawable.dial);

        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setExtras() {
        try {
            setSearchMenu();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void setSearchMenu() {
        try {
            Toolbar bar = findViewById(R.id.toolbar);
            bar.inflateMenu(R.menu.only_search_menu);
            bar.setOnMenuItemClickListener(this::onOptionsItemSelected);
            MenuItem item = bar.getMenu().findItem(R.id.search_item);
            SearchView searchView = new SearchView(this);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
            item.setActionView(searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (adapter != null)
                        adapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (adapter != null)
                        adapter.getFilter().filter(newText);
                    return false;
                }
            });
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try {
            if (item.getItemId() == R.id.search_item)
                item.expandActionView();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTheme() {
        try {
            Theme.Activity(this);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setActions() {
        try {
            call.setOnClickListener(v -> new DialerPopUp(ContactListAct.this) {
                @Override
                protected void dial(String num) {
                    ContactListAct.number = num.replace("-", "");
                    permission.request().phone();
                    dismiss();
                }
            });
            switch (getAction()) {
                default:
                case Intents.SHOW: {
                    if (modifyValue() != null)
                        adapter.getFilter().filter(modifyValue());
                    break;
                }
                case Intents.DIAL: {
                    if (modifyValue() != null)
                        adapter.getFilter().filter(modifyValue());
                }
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }


    @Override
    public void setPermission() {
        try {
            permission = new PermissionGroup(ContactListAct.this) {
                @Override
                public void onDenied(int code) {
                    if (code == Permission.CODE_CONTACT) {
                        ProcessApp.talk(ContactListAct.this, "Contact Permission Denied.");
                    } else if (code == Permission.CODE_PHONE) {
                        ProcessApp.talk(ContactListAct.this, "Phone Call Permission Denied.");
                    }
                }

                @Override
                public void onGranted(int code) {
                    checkActionsOnGrant(code);
                }

                @Override
                public void neverAskAgain(int code) {
                    if (code == Permission.CODE_CONTACT) {
                        ProcessApp.talk(ContactListAct.this, "Grant permission to contacts.");
                    } else if (code == Permission.CODE_PHONE) {
                        ProcessApp.talk(ContactListAct.this, "Grant permission to make Phone Call.");
                    }
                    startActivity(Permission.gotoSettings(getPackageName()));
                }

                @Override
                public void requireSimpleAsk(String per, int code) {
                    super.requireSimpleAsk(per, code);
                    permission.displaySimple(getString(R.string.s_contact_find) +
                            " " + Permission.Talking.whichNotGranted(ContactListAct.this, code));
                }

                @Override
                public void requireRationaleAsk(String per, int code) {
                    super.requireRationaleAsk(per, code);
                    permission.displayRationale(getString(R.string.s_m_contact_find) +
                            " " + Permission.Talking.whichNotGranted(ContactListAct.this, code), code);
                }
            };
            permission.request().contacts();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void checkActionsOnGrant(int code) {
        switch (code) {
            case Permission.CODE_CONTACT: {
                setList();
                if (ProcessApp.cList.size() == 0) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new ContactRunnable(ContactListAct.this, true) {
                                @Override
                                public void onComplete() {
                                    super.onComplete();
                                    setList();
                                }
                            });
                        }
                    });
                }
                break;
            }
            case Permission.CODE_PHONE: {
                actionCall();
                break;
            }
            case Permission.CODE_SMS: {
                actionSms();
                break;
            }
        }
    }

    private void setList() {
        try {
            adapter = new ContactAdapter(ProcessApp.cList) {
                @Override
                public void listSize(int size) {
                    super.listSize(size);
                    layout.setVisibility(size == 0 ? View.VISIBLE : View.GONE);
                    if (size == 1) {
                        Contact contact = list.get(0);
                        switch (getAction()) {
                            default:
                            case Intents.SHOW: {
                                onContactSelection(contact);
                                break;
                            }
                            case Intents.DIAL:
                            case Intents.SMS:
                            case Intents.QUERY: {
                                if (contact.size() == 1)
                                    onNumberSelection(contact.getName(), contact.getNumbers().get(0).getNumber());
                                else
                                    onContactSelection(contact);
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onClick(Contact contact) {
                    onContactSelection(contact);
                }
            };
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    public void onContactSelection(Contact contact) {

        new ContactPopUp(ContactListAct.this, contact) {
            @Override
            public void onSelected(String number) {
                onNumberSelection(contact.getName(), number);
                dismiss();
            }

            @Override
            public void onCall(String num) {
                ContactListAct.number = num.replace("-", "");
                permission.request().phone();
                dismiss();
            }

            @Override
            public void onSms(String num) {
                ContactListAct.name = contact.getName();
                ContactListAct.number = num.replace("-", "");
                permission.request().sms();
                dismiss();
            }
        };
    }

    public void onNumberSelection(String name, String number) {
        switch (getAction()) {
            case Intents.SMS: {
                ContactListAct.name = name;
                ContactListAct.number = number.replace("-", "");
                permission.request().sms();
                break;
            }
            case Intents.DIAL: {
                ContactListAct.number = number.replace("-", "");
                permission.request().phone();
                break;
            }
        }

    }

    public static class Intents {
        public static final int SHOW = 1, DIAL = 2, QUERY = 3, SMS = 4;

        public static Intent showContacts(Context c) {
            return new Intent(c, ContactListAct.class)
                    .putExtra("action", SHOW)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        public static Intent showContacts(Context c, String num) {
            return new Intent(c, ContactListAct.class)
                    .putExtra("action", SHOW)
                    .putExtra("values", num)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        public static Intent dailContacts(Context c, String num) {
            return new Intent(c, ContactListAct.class)
                    .putExtra("action", DIAL)
                    .putExtra("values", num)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }


        public static Intent smsContacts(Context c) {
            return new Intent(c, ContactListAct.class)
                    .putExtra("action", SMS)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    private int getAction() {
        return getIntent().getIntExtra("action", Intents.SHOW);
    }

    private String getValue() {
        return getIntent().getStringExtra("values");
    }

    @Nullable
    private String modifyValue() {
        String data = getValue();
        if (data == null || data.trim().equals("")) return null;

        if (data.contains("call ")) {
            data = data.replace("call ", " ");
        }
        if (data.contains(" to ")) {
            data = data.replace(" to ", " ");
        }
        if (data.contains("make ")) {
            data = data.replace("make ", " ");
        }
        if (data.contains(" a ")) {
            data = data.replace(" a ", " ");
        }
        if (data.contains("show ")) {
            data = data.replace("show ", " ");
        }
        if (data.contains("number")) {
            data = data.replace("number", " ");
        }
        if (data.contains("contact")) {
            data = data.replace("contact", " ");
        }
        if (data.contains("is")) {
            data = data.replace("is", " ");
        }
        if (data.contains("'s")) {
            data = data.replace("'s", " ");
        }
        if (data.contains("milao")) {
            data = data.replace("milao", " ");
        }
        if (data.contains("karo")) {
            data = data.replace("karo", " ");
        }
        if (data.contains("lagao")) {
            data = data.replace("lagao", " ");
        }
        if (data.contains("phone")) {
            data = data.replace("phone", " ");
        }
        if (data.contains("dial")) {
            data = data.replace("dial", " ");
        }
        if (data.contains(" ko")) {
            data = data.replace(" ko", " ");
        }
        if (data.contains(" ka")) {
            data = data.replace(" ka", " ");
        }
        if (data.contains("dikhana")) {
            data = data.replace("dikhana", " ");
        }
        if (data.contains("dikhao")) {
            data = data.replace("dikhao", " ");
        }
        if (data.contains("nikaalo")) {
            data = data.replace("nikaalo", " ");
        }
        if (data.contains(" of ")) {
            data = data.replace(" of ", " ");
        }
        return data.trim();
    }

    private void actionSms() {
        if (ContactListAct.name.isEmpty() || ContactListAct.number.isEmpty()) return;
        switch (getDefaultSmsSubscriptionId()) {
            case SubscriptionManager.INVALID_SUBSCRIPTION_ID: {
                ProcessApp.talk(ContactListAct.this, "Choose Network.");
                break;
            }
            case -2: {
                break;
            }
            default: {
                ProcessApp.talk(ContactListAct.this, "sending.");
                break;
            }
        }

        Intent intent = new Intent(getApplicationContext(), SmsSubActivity.class);
        intent.putExtra(Params.NAME, ContactListAct.name);
        intent.putExtra(Params.NUM, ContactListAct.number.replace("-", ""));
        startActivity(intent);
    }

    private void actionCall() {
        if (manager != null && manager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            if (ContactListAct.number != null && MultiFun.isContactNumber(ContactListAct.number)) {
                switch (getDefaultVoiceSubscriptionId()) {
                    case SubscriptionManager.INVALID_SUBSCRIPTION_ID: {
                        ProcessApp.talk(ContactListAct.this, "Choose Network!");
                        break;
                    }
                    case -2: {
                        break;
                    }
                    default: {
                        ProcessApp.talk(ContactListAct.this, "calling.");
                        break;
                    }
                }
                manager.listen(new PhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + ContactListAct.number));
                startActivity(intent);
            } else
                ProcessApp.talk(this, "Can't make call. Please Make Sure Number is Valid.");

        } else {
            ProcessApp.talk(this, "Can't make call. Please Make Sure of Sim Status.");
        }
    }

    public static int getDefaultSmsSubscriptionId() {
        int id = -2;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            id = SubscriptionManager.getDefaultSmsSubscriptionId();
        }
        return id;
    }

    public static int getDefaultVoiceSubscriptionId() {
        int id = -2;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            id = SubscriptionManager.getDefaultVoiceSubscriptionId();
        }
        return id;
    }

}
