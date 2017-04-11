package com.codecool.actimate.controller;


import android.content.SharedPreferences;
import android.util.Log;

public class TextValidator {
    public static boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean isPasswordValid(String password) {
        boolean upperCase = false, lowerCase = false, number = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                upperCase = true;
            } else if (Character.isLowerCase(password.charAt(i))) {
                lowerCase = true;
            } else if (Character.isDigit(password.charAt(i))) {
                number = true;
            }
        }
        return password.length() > 5 && upperCase && lowerCase && number;

    }
}
