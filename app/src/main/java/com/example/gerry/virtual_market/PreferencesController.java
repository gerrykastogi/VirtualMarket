package com.example.gerry.virtual_market;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Gerry on 7/11/2017.
 */

public class PreferencesController {
    private static  final String ID = "user_id";

    public static void setUser(Context context, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ShoppingList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID, id);
        editor.apply();
    }

    public static String getID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ShoppingList", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString(ID, null);
        return userID;
    }

    public static void removeUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ShoppingList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ID);
        editor.apply();
    }

    public static boolean isLoggedIn(Context context) {
        if(getID(context) != null) {
            return true;
        } else {
            return false;
        }
    }
}
