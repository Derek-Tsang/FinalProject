package algonquin.cst2335.finalproject.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *  CommonSharedPreference is a utility class for handling shared preferences in Android applications.
 */
public class CommonSharedPreference  {

    /**
     * Shared preferences Editor
     */
    static SharedPreferences.Editor editor;

    /**
     * Sets a string value in the SharedPreferences.
     *
     * @param context The application context.
     * @param key The key to use for storing the value.
     * @param value The string value to be stored.
     */
    public static void setsharedText(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    /**
     * Retrieves a string value from the SharedPreferences.
     *
     * @param context The application context.
     * @param key The key used for storing the value.
     * @return The retrieved string value, or an empty string if not found.
     */
    public static String getsharedText(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String value = preferences.getString(key, "");
        return value;
    }
    /**
     * Sets a float value in the SharedPreferences.
     *
     * @param context The application context.
     * @param key The key to use for storing the value.
     * @param value The float value to be stored.
     */
    public static void setsharedFloat(Context context, String key, float value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putFloat(key, value);
        prefsEditor.apply();
    }

    /**
     * Retrieves a float value from the SharedPreferences.
     *
     * @param context The application context.
     * @param key The key used for storing the value.
     * @return The retrieved float value, or 0.0 if not found.
     */
    public static double getsharedFloat(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        float value = preferences.getFloat(key, 0.0f);
        return value;
    }
    /**
     * Sets an integer value in the SharedPreferences.
     *
     * @param context The application context.
     * @param key The key to use for storing the value.
     * @param value The integer value to be stored.
     */
    public static void setsharedInt(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }
    /**
     * Retrieves an integer value from the SharedPreferences.
     *
     * @param context The application context.
     * @param key The key used for storing the value.
     * @return The retrieved integer value, or 0 if not found.
     */
    public static int getsharedInt(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        int value = preferences.getInt(key, 0);
        return value;
    }
}