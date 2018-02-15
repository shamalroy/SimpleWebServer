package com.sroy.webserver.utility;

/**
 * Created by shamalroy
 */
public class Utils {
    public static boolean isEmptyOrNull(String str) {
        return str == null || str.trim().isEmpty() ? true : false;
    }
}
