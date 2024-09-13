package com.example.mirea10;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREFERENCES_FILE_KEY = "your.package.name.PREFERENCES";
    private static final String USERNAME_KEY = "username";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
    }

    // Метод для сохранения имени пользователя
    public void saveUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.apply();
    }

    // Метод для чтения имени пользователя
    public String getUsername() {
        return sharedPreferences.getString(USERNAME_KEY, "");
    }

    // Метод для удаления имени пользователя
    public void removeUsername() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USERNAME_KEY);
        editor.apply();
    }
}