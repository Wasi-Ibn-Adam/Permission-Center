package com.wasitech.permissioncenter.java.com.wasitech.register.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wasitech.assist.R;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.basics.classes.Issue;
import com.wasitech.register.activity.PrivacyPolicy;
import com.wasitech.theme.Theme;

public class Policy extends AssistFragment {
    private TextView h1, h2, h3, h4, h5, h6, h7;
    private LinearLayout l1, l2, l3, l4, l5, l6, l7;
    private final int H1 = 20, H2 = 17;
    private Button acp, deny;
    public static final int ACCEPTED = 1;
    public static final int DENIED = 2;

    public static Policy getInstance() {
        return new Policy();
    }

    @Override
    protected int setLayout() {
        return R.layout.frag_r_app_policy;
    }

    @Override
    public void setViews(View view) {
        try {
            h1 = view.findViewById(R.id.policy_h1);
            h2 = view.findViewById(R.id.policy_h2);
            h3 = view.findViewById(R.id.policy_h3);
            h4 = view.findViewById(R.id.policy_h4);
            h5 = view.findViewById(R.id.policy_h5);
            h6 = view.findViewById(R.id.policy_h6);
            h7 = view.findViewById(R.id.policy_h7);
            l1 = view.findViewById(R.id.lay1);
            l2 = view.findViewById(R.id.lay2);
            l3 = view.findViewById(R.id.lay3);
            l4 = view.findViewById(R.id.lay4);
            l5 = view.findViewById(R.id.lay5);
            l6 = view.findViewById(R.id.lay6);
            l7 = view.findViewById(R.id.lay7);
            acp = view.findViewById(R.id.policy_accept);
            deny = view.findViewById(R.id.policy_deny);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setActions(View view) {
        try {
            h1.setOnClickListener(v -> {
                if (l1.getChildCount() == 0) {
                    h1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.up,0);
                    l1.addView(text(getString(R.string.policy_h1_des)));
                    listText(l1,R.array.policy_h1_detail);
                } else {
                    h1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.down,0);
                    l1.removeAllViews();
                }
            });
            h2.setOnClickListener(v -> {
                if (l2.getChildCount() == 0) {
                    h2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.up,0);

                    l2.addView(heading(getString(R.string.policy_h2_1), H1));
                    {
                        l2.addView(heading(getString(R.string.policy_h2_1_1), H2));
                        l2.addView(text(getString(R.string.policy_h2_1_1_des)));   // text
                        listText(l2,R.array.policy_h21_detail1);
                        l2.addView(heading(getString(R.string.policy_h2_1_2), H2));
                        l2.addView(text(getString(R.string.policy_h2_1_2_des)));   // text
                    }
                    l2.addView(heading(getString(R.string.policy_h2_2), H1));
                    {
                        l2.addView(text(getString(R.string.policy_h2_2_des_1)));   // text
                        listText(l2,R.array.policy_h22_detail1);
                        l2.addView(text(getString(R.string.policy_h2_2_des_2)));   // text
                        listText(l2,R.array.policy_h22_detail2);
                    }
                    l2.addView(heading(getString(R.string.policy_h2_3), H1));
                    {
                        l2.addView(text(getString(R.string.policy_h2_3_des)));   // text
                    }
                    l2.addView(heading(getString(R.string.policy_h2_4), H1));
                    {
                        l2.addView(text(getString(R.string.policy_h2_4_des)));   // text
                    }
                    l2.addView(heading(getString(R.string.policy_h2_5), H1));
                    {
                        l2.addView(heading(getString(R.string.policy_h2_5_1), H2));
                        l2.addView(text(getString(R.string.policy_h2_5_1_des)));   // text

                        l2.addView(heading(getString(R.string.policy_h2_5_2), H2));
                        l2.addView(text(getString(R.string.policy_h2_5_2_des)));   // text

                        l2.addView(heading(getString(R.string.policy_h2_5_3), H2));
                        l2.addView(text(getString(R.string.policy_h2_5_3_des)));   // text
                        listText(l2,R.array.policy_h253_detail);
                    }
                    l2.addView(heading(getString(R.string.policy_h2_6), H1));
                    {
                        l2.addView(text(getString(R.string.policy_h2_6_des)));   // text
                    }
                } else {
                    h2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.down,0);
                    l2.removeAllViews();
                }
            });
            h3.setOnClickListener(v -> {
                if (l3.getChildCount() == 0) {
                    h3.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.up,0);
                    l3.addView(text(getString(R.string.policy_h3_des)));
                } else {
                    h3.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.down,0);
                    l3.removeAllViews();
                }
            });
            h4.setOnClickListener(v -> {
                if (l4.getChildCount() == 0) {
                    h4.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.up,0);
                    l4.addView(text(getString(R.string.policy_h4_des)));
                } else {
                    h4.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.down,0);
                    l4.removeAllViews();
                }
            });
            h5.setOnClickListener(v -> {
                if (l5.getChildCount() == 0) {
                    h5.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.up,0);
                    l5.addView(text(getString(R.string.policy_h5_des_1)));
                    listText(l5,R.array.policy_h5_detail1);
                    l5.addView(text(getString(R.string.policy_h5_des_2)));
                    listText(l5,R.array.policy_h5_detail2);
                } else {
                    h5.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.down,0);
                    l5.removeAllViews();
                }
            });
            h6.setOnClickListener(v -> {
                if (l6.getChildCount() == 0) {
                    h6.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.up,0);
                    l6.addView(text(getString(R.string.policy_h6_des)));
                } else {
                    h6.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.down,0);
                    l6.removeAllViews();
                }
            });
            h7.setOnClickListener(v -> {
                if (l7.getChildCount() == 0) {
                    h7.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.up,0);
                    l7.addView(text(getString(R.string.policy_h7_des)));
                } else {
                    h7.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.down,0);
                    l7.removeAllViews();
                }
            });
            deny.setOnClickListener(v -> {
                PrivacyPolicy.CODE = Policy.DENIED;
                task.onComplete();
            });
            acp.setOnClickListener(v -> {
                PrivacyPolicy.CODE = Policy.ACCEPTED;
                task.onComplete();
            });
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setExtra(View view) {
        h1.callOnClick();
        h2.callOnClick();
        h3.callOnClick();
        h4.callOnClick();
        h5.callOnClick();
        h6.callOnClick();
        h7.callOnClick();
    }

    private TextView heading(String val, int size) {
        TextView view = new TextView(requireContext());
        view.setText(val);
        view.setTextSize(size);
        view.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Theme.textView(view);
        return view;
    }

    private TextView text(String val) {
        TextView view = new TextView(requireContext());
        view.setText(val);
        view.setTextSize(14);
        view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Theme.textView(view);
        return view;
    }

    private TextView listText(String val) {
        TextView view = new TextView(requireContext());
        view.setText(val);
        view.setTextSize(14);
        view.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        view.setPadding(5, 2, 5, 2);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Theme.textView(view);
        return view;
    }

    private void listText(LinearLayout lay,int rid) {
        int i=0;
        for (String val : getResources().getStringArray(rid)) {
            i++;
            lay.addView(listText(i+")  "+val));
        }
    }

    @Override
    public void setTheme(View view) {
        Theme.textView(h1);
        Theme.textView(h2);
        Theme.textView(h3);
        Theme.textView(h4);
        Theme.textView(h5);
        Theme.textView(h6);
        Theme.textView(h7);
        Theme.button(acp);
        Theme.button(deny);
    }

    @Override
    public void setPermission(View view) {

    }

    @Override
    public void setAnimation(View view) {

    }

    @Override
    public void setValues(View view) {

    }
}
