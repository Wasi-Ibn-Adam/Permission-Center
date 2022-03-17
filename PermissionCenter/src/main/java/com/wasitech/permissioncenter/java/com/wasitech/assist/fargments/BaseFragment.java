package com.wasitech.permissioncenter.java.com.wasitech.assist.fargments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.permission.Permission;
import com.wasitech.permission.PermissionGroup;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseFragment extends Fragment {
    protected PermissionGroup permissions;
    private int code;
    protected ActivityResultLauncher<String[]> launcher =registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions()
            , result -> permissions.onResult(code)
    );
    protected void onPermission(int code) {
        this.code=code;
        launcher.launch(Permission.Talking.permissionList(code));
    }
    protected void inputTextViewLayout(TextInputLayout layout){
        if(ProcessApp.isDarkTheme()){
            layout.setHintTextColor(ColorStateList.valueOf(Color.WHITE));
            layout.setDefaultHintTextColor(ColorStateList.valueOf(Color.WHITE));
            layout.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
            layout.setEndIconTintList(ColorStateList.valueOf(Color.WHITE));
        }
        else{
            layout.setHintTextColor(ColorStateList.valueOf(Color.BLACK));
            layout.setDefaultHintTextColor(ColorStateList.valueOf(Color.BLACK));
            layout.setStartIconTintList(ColorStateList.valueOf(Color.BLACK));
            layout.setEndIconTintList(ColorStateList.valueOf(Color.BLACK));
        }
    }
    protected void inputTextView(TextInputEditText text){
        if(ProcessApp.isDarkTheme()){
            text.setHintTextColor(ColorStateList.valueOf(Color.WHITE));
            text.setTextColor(ColorStateList.valueOf(Color.WHITE));
        }
        else{
            text.setHintTextColor(ColorStateList.valueOf(Color.BLACK));
            text.setTextColor(ColorStateList.valueOf(Color.BLACK));
        }
    }
    protected void textView(TextView text){
        if(ProcessApp.isDarkTheme()){
            text.setTextColor(ColorStateList.valueOf(Color.WHITE));
        }
        else{
            text.setTextColor(ColorStateList.valueOf(Color.BLACK));
        }
    }
    protected void radioButton(RadioButton btn){
        if(ProcessApp.isDarkTheme()){
            btn.setTextColor(ColorStateList.valueOf(Color.WHITE));
            btn.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
        }
        else{
            btn.setTextColor(ColorStateList.valueOf(Color.BLACK));
            btn.setButtonTintList(ColorStateList.valueOf(Color.BLACK));
        }
    }
    protected void button(Button btn){
        if(ProcessApp.isDarkTheme()){
            btn.setTextColor(ColorStateList.valueOf(Color.WHITE));
            btn.setBackgroundResource(R.drawable.shape_btn_1_light);
        }
        else{
            btn.setTextColor(ColorStateList.valueOf(Color.BLACK));
            btn.setBackgroundResource(R.drawable.shape_btn_1_dark);
        }
    }
    protected void imageView(ImageView img){
        if(ProcessApp.isDarkTheme()){
            img.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        }
        else{
            img.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        }
    }
    protected void circleImageView(CircleImageView img){
        if(ProcessApp.isDarkTheme()){
            img.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            img.setImageTintList(ColorStateList.valueOf(Color.WHITE));
        }
        else{
            img.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            img.setImageTintList(ColorStateList.valueOf(Color.BLACK));
        }
    }
}
