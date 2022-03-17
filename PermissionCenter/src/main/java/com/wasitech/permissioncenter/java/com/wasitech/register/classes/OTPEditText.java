package com.wasitech.permissioncenter.java.com.wasitech.register.classes;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wasitech.assist.R;
import com.wasitech.basics.classes.Issue;

import java.util.Objects;

public class OTPEditText extends androidx.appcompat.widget.AppCompatEditText {

    @Nullable
    private View nextView,previousView;

    // Unfortunately getParent returns null inside the constructor. So we need to store the IDs.
    private int nextViewId, previousViewId;

    @Nullable
    private Listener listener;

    private static final int NO_ID = -1;

    public interface Listener {
        void onPaste(String s);
    }

    public OTPEditText(@NonNull Context context) {
        super(context);
    }

    public OTPEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public OTPEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setListener(@org.jetbrains.annotations.Nullable Listener listener) {
        this.listener = listener;
    }

    /**
     * Called when a context menu option for the text view is selected.  Currently
     * this will be one of {@link android.R.id#selectAll}, {@link android.R.id#cut},
     * {@link android.R.id#copy}, {@link android.R.id#paste} or {@link android.R.id#shareText}.
     *
     * @return true if the context menu item action was performed.
     */
    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.paste) {
            try{
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);

                // Examines the item on the clipboard. If getText() does not return null, the clip item contains the
                // text. Assumes that this application can only handle one item at a time.
                ClipData.Item item = Objects.requireNonNull(clipboard.getPrimaryClip()).getItemAt(0);

                // Gets the clipboard as text.
                CharSequence pasteData = item.getText();

                if (listener != null && pasteData != null) {
                    listener.onPaste(pasteData.toString());
                    return true;
                }
            }
            catch (Exception e){
                Issue.print(e,getClass().getName());
            }
        }
        return super.onTextContextMenuItem(id);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        // If we've gotten focus here
        if (focused && this.getText() != null) {
            this.setSelection(this.getText().length());
        }
    }

    private void init(Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OTPView, 0, 0);
        nextViewId = typedArray.getResourceId(R.styleable.OTPView_nextView, NO_ID);
        previousViewId = typedArray.getResourceId(R.styleable.OTPView_prevView, NO_ID);

        typedArray.recycle();

        this.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction()!= KeyEvent.ACTION_DOWN) {
                return true;
            }
            //You can identify which key pressed by checking keyCode value with KeyEvent.KEYCODE_
            if(keyCode == KeyEvent.KEYCODE_DEL) {
                // Back pressed. If we have a previous view. Go to it.
                if (getPreviousView() != null) {
                    getPreviousView().requestFocus();
                    return true;
                }
            }
            return false;
        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1 && getNextView() != null) {
                    getNextView().requestFocus();
                } else if (s.length() == 0 && getPreviousView() != null) {
                    getPreviousView().requestFocus();
                }
            }
        });

        // Android 3rd party keyboards show the copied text into the suggestion box for the user.
        // Users can then simply tap on that suggestion to paste the text on the edittext.
        // But I don't know of any API that allows handling of those paste actions.
        // Below code will try to tell those keyboards to stop showing those suggestion.
        this.setInputType(EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS | EditorInfo.TYPE_CLASS_NUMBER);
    }

    private View getNextView() {
        if (nextView != null) {
            return nextView;
        }
        if (nextViewId != NO_ID && getParent() instanceof View) {
            nextView = ((View) getParent()).findViewById(nextViewId);
            return nextView;
        }
        return null;
    }

    private View getPreviousView() {
        if (previousView != null) {
            return previousView;
        }
        if (previousViewId != NO_ID && getParent() instanceof View) {
            previousView = ((View) getParent()).findViewById(previousViewId);
            return previousView;
        }
        return null;
    }
}