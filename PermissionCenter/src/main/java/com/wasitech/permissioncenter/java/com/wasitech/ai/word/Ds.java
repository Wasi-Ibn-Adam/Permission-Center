package com.wasitech.permissioncenter.java.com.wasitech.ai.word;

public class Ds {
    public static boolean developed(String str) {
        return (str.contains("developed"));
    }

    public static boolean dial(String str) {
        return (str.contains("dial"));
    }

    public static boolean day(String str) {
        return str.contains("day");
    }

    public static boolean dad(String str) {
        return (
                str.startsWith("dad ") || str.endsWith(" dad") ||
                        str.startsWith("abu ") || str.endsWith(" abu") ||
                        str.startsWith("dada ") || str.endsWith(" dada") ||
                        str.startsWith("father ") || str.endsWith(" father") ||
                        str.startsWith("baba ") || str.endsWith(" baba")
        );
    }

    public static boolean disconnect(String str) {
        return str.contains("disconnect");
    }

    public static boolean die(String str) {
        return str.contains("die");
    }

    public static boolean date(String str) {
        return str.contains("date");
    }

    public static boolean doing(String str) {
        return str.contains("doing");
    }

    public static boolean deactivate(String str) {
        return str.contains("deactivate");
    }

    public static boolean disable(String str) {
        return str.contains("disable");
    }


}

