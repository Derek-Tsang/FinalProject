package algonquin.cst2335.finalproject.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *  CommonSharedPreference is a utility class for handling shared preferences in Android applications.
 */
public class CommonSharedPreference  {

    static SharedPreferences.Editor editor;

    // setting a value in shared preferences
    public static void setsharedText(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    // getting a value from shared preferences
    public static String getsharedText(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String value = preferences.getString(key, "");
        return value;
    }

    public static void setsharedInt(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    public static int getsharedInt(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        int value = preferences.getInt(key, 0);
        return value;
    }
}