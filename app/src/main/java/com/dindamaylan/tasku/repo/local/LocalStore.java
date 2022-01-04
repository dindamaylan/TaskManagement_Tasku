package com.dindamaylan.tasku.repo.local;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class LocalStore {
    private static final String USER_PREF = "user_pref";
    private static final String USER_ID_KEY = "user_id_key";
    private static LocalStore INSTANCE;
    private final Activity context;

    public LocalStore(Activity context) {
        this.context = context;
    }

    public static LocalStore getInstance(Activity context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalStore(context);
        }
        return INSTANCE;
    }

    public void getUserId(onGetUserListener listener) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString(USER_ID_KEY, "");
        if (!user_id.isEmpty()) {
            listener.isSuccess(true, user_id);
        } else {
            listener.isSuccess(false, "");
        }
    }

    public void storeUserId(String user_id, onGetUserListener listener) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID_KEY, user_id);
        editor.apply();
        listener.isSuccess(true, user_id);
    }

    public interface onGetUserListener {
        void isSuccess(Boolean isSuccess, String user_id);
    }
}