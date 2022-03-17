package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

import com.google.firebase.auth.FirebaseUser;

import java.util.Comparator;

public class TingUser {
    private String uid, name, number, imagePath, email;

    public TingUser() { }
    public TingUser(TingUser user){
        this.uid = user.uid;
        this.name = user.name;
        this.number = user.number;
        this.imagePath = user.imagePath;
        this.email = user.email;
    }

    public TingUser(FirebaseUser user) {
        uid = user.getUid();
        name = user.getDisplayName();
        number = user.getPhoneNumber();
        if (user.getPhotoUrl() != null)
            imagePath = user.getPhotoUrl().toString();
        else
            imagePath = "No Image";
        email = user.getEmail();
    }

    public TingUser(String uid, String name, String number, String imagePath, String email) {
        this.uid = uid;
        this.name = name;
        this.number = number;
        this.imagePath = imagePath;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    @Override
    public String toString() {
        return "AssistUser{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static Comparator<TingUser> StuNameComparator = (u1, u2) -> u1.getName().toUpperCase().compareTo(u2.getName().toUpperCase());

}
