package com.dimentor.victorina.util;

public class Coding {

    public static String code(String text) {
        String k = "qwe123rty456@";
        if (k.length() > text.length())
            k = k.substring(0, text.length());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++)
            sb.append((char)(text.charAt(i) ^ k.charAt(i % k.length())));
        return sb.toString();
    }
}
