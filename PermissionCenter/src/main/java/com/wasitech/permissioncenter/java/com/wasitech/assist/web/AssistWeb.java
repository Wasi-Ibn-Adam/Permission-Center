package com.wasitech.permissioncenter.java.com.wasitech.assist.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wasitech.assist.R;
import com.wasitech.assist.adapter_listeners.ServiceHeadsTouchListener;

import com.wasitech.database.CloudDB;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

@SuppressLint("ClickableViewAccessibility,SetJavaScriptEnabled")
public class AssistWeb extends Activity {
    private WebView webView;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        try {
            String searchFor = getIntent().getStringExtra(Params.DATA_TRANS);
            TextView backView = findViewById(R.id.backView);
            backView.setOnTouchListener(touchListener());
            webView = findViewById(R.id.webView);
            webView.setWebViewClient(new AssistWebViewClient());
            webView.loadUrl("https://www.google.com/search?q=" + searchFor);
            webView.getSettings().setJavaScriptEnabled(true);

        } catch (Exception e) {
            Issue.print(e, AssistWeb.class.getName());
        }
    }

    private View.OnTouchListener touchListener() {
        return new ServiceHeadsTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if (webView.canGoForward()) {
                    findViewById(R.id.forward).setVisibility(View.VISIBLE);
                    new Handler().postDelayed(() -> findViewById(R.id.forward).setVisibility(View.INVISIBLE), 300L);
                    webView.goForward();
                }
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if (webView.canGoBack()) {
                    findViewById(R.id.back).setVisibility(View.VISIBLE);
                    new Handler().postDelayed(() -> findViewById(R.id.back).setVisibility(View.INVISIBLE), 300L);
                    webView.goBack();
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private static class AssistWebViewClient extends WebViewClient {
        private final CloudDB.DataCenter center;
        public AssistWebViewClient() {
            center = new CloudDB.DataCenter();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url); // load the url
            center.Search(url);
            return true;
        }

    }
}
