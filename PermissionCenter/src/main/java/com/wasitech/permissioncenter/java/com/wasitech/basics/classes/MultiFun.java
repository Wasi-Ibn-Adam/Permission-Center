package com.wasitech.permissioncenter.java.com.wasitech.basics.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MultiFun {
    public static boolean contains(String s1, String s2) {
        return s2.contains(s1) || s1.contains(s2) || s1.equalsIgnoreCase(s2);
    }

    public static boolean containsIgnoreCase(String s1, String s2) {
        return s1.toLowerCase().contains(s2.toLowerCase())
                || s2.toLowerCase().contains(s1.toLowerCase())
                || s1.equalsIgnoreCase(s2);
    }

    public static boolean isContactNumber(String str) {
        if (str.isEmpty()) return false;
        int plus = 0, dash = 0;
        for (char c : str.toCharArray()) {
            if (c < '0' || c > '9') {
                if (c == '+') {
                    if (plus == 0)
                        plus = 1;
                    else
                        return false;
                }
                else if (c == '-') {
                    if (dash == 0)
                        dash = 1;
                    else
                        return false;
                } else if (c != ' ')
                    return false;
            }

        }
        return true;
    }


    public static ArrayList<String> removeDuplicate(ArrayList<String>list){
        int end = list.size();
        Set<String> set = new HashSet<>();
        for(int i = 0; i < end; i++){
            set.add(list.get(i));
        }
        return new ArrayList<>(set);
    }
}
