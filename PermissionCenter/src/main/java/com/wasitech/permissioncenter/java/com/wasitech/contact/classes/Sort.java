package com.wasitech.permissioncenter.java.com.wasitech.contact.classes;

import com.wasitech.basics.app.ProcessApp;

import java.util.Comparator;

public class Sort {
    public static final String CONTACT_SORT = "contact_sort";
    public static final String CONTACT_ORDER = "contact_order";
    public static final int SORT_TYPE_NUMBER = 0;
    public static final int SORT_TYPE_NAME = 1;
    public static final int SORT_TYPE_NUMBERS_COUNT = 2;

    public static final int ORDER_TYPE_ASC = 0;
    public static final int ORDER_TYPE_DES = 1;

    public static void setSortingOrder(int order, int sort) {
        ProcessApp.getPref().edit()
                .putInt(CONTACT_SORT, sort)
                .putInt(CONTACT_ORDER, order)
                .apply();
    }

    public static int getSort() {
        return ProcessApp.getPref().getInt(CONTACT_SORT, SORT_TYPE_NAME);
    }

    public static int getOrder() {
        return ProcessApp.getPref().getInt(CONTACT_ORDER, ORDER_TYPE_ASC);
    }

    private final static Comparator<Contact> nameComAcs = (s1, s2) -> s1.getName().toUpperCase().compareTo(s2.getName().toUpperCase());
    private final static Comparator<Contact> numCountComAcs = (s1, s2) -> {
        int a = Integer.compare(s1.size(), s2.size());
        if (a != 0) return a;
        return s1.getName().toUpperCase().compareTo(s2.getName().toUpperCase());
    };
    private final static Comparator<Contact> numComAcs = (s1, s2) -> {
        String n = s1.getNumbers().get(0).getNumber(), m = s2.getNumbers().get(0).getNumber();
        int a = Long.compare(Long.parseLong(n), Long.parseLong(m));
        if (a != 0) return a;
        return s1.getName().toUpperCase().compareTo(s2.getName().toUpperCase());
    };

    private final static Comparator<Contact> nameComDecs = (s2, s1) -> s1.getName().toUpperCase().compareTo(s2.getName().toUpperCase());
    private final static Comparator<Contact> numCountComDecs = (s2, s1) -> {
        int a = Integer.compare(s1.size(), s2.size());
        if (a != 0) return a;
        return s1.getName().toUpperCase().compareTo(s2.getName().toUpperCase());
    };
    private final static Comparator<Contact> numComDecs = (s2, s1) -> {
        String n = s1.getNumbers().get(0).getNumber(), m = s2.getNumbers().get(0).getNumber();
        int a = Long.compare(Long.parseLong(n), Long.parseLong(m));
        if (a != 0) return a;
        return s1.getName().toUpperCase().compareTo(s2.getName().toUpperCase());
    };



    public static Comparator<Contact> Compare() {
        switch (getSort()) {
            default:
            case SORT_TYPE_NAME: {
                return getOrder() == ORDER_TYPE_ASC ? nameComAcs : nameComDecs;
            }
            case SORT_TYPE_NUMBER: {
                return getOrder() == ORDER_TYPE_ASC ? numComAcs : numComDecs;
            }
            case SORT_TYPE_NUMBERS_COUNT: {
                return getOrder() == ORDER_TYPE_ASC ? numCountComAcs : numCountComDecs;
            }

        }
    }

}
