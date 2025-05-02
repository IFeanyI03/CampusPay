package com.example.campusPayApp.utils;
import com.google.gson.*;

import java.util.prefs.Preferences;

public class LocalStorageManager {
    private static final String APPLICATION_ID = "com.example.campusPayApp"; // Replace with your actual package name
    private static final Preferences prefs = Preferences.userNodeForPackage(LocalStorageManager.class).node(APPLICATION_ID);

    static Gson gson = new Gson();

    public static void saveString(String key, String string) {
        prefs.put(key, string);
    }

    public static String getString(String key) {
        String jsonString = prefs.get(key, null);
        if (jsonString != null) {
            return jsonString;
        }
        return null;
    }

    public static void saveObject(String key, Object object) {
        String jsonString = gson.toJson(object);
        prefs.put(key, jsonString);
    }

    public static Object getObject(String key) {
        String jsonString = prefs.get(key, null);
        if (jsonString != null) {
            return gson.fromJson(jsonString, Object.class);
        }
        return null;
    }

    public static void saveArray(String key, JsonArray jsonArray) {
        String jsonString = gson.toJson(jsonArray);
        prefs.put(key, jsonString);
    }

    public static JsonElement getArray(String key) {
        String jsonString = prefs.get(key, null);
        if (jsonString != null) {
            return gson.fromJson(jsonString, JsonArray.class);
        }
        return null; // Or return a default instance if appropriate
    }

    public static void clear() {
        try {
            prefs.clear();
        } catch (java.util.prefs.BackingStoreException e) {
            System.err.println("Error clearing preferences: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
