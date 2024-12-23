package pvt.sanae.notepad.util;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;
import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

import android.content.Context;
import android.content.SharedPreferences;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import lombok.Getter;

public class ConfigUtil {

    public static final int THEME_DAY = 0;
    public static final int THEME_NIGHT = 1;
    public static final int THEME_AUTO = 2;

    private static SharedPreferences sp;
    @Getter
    private static int appTheme;
    @Getter
    private static boolean autoBreak;
    @Getter
    private static boolean spellCheck;

    public static void init(Context context) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        appTheme = sp.getInt("appTheme", THEME_AUTO);
        autoBreak = sp.getBoolean("autoBreak", false);
        spellCheck = sp.getBoolean("spellCheck", false);
    }

    public static void setAppTheme(int appTheme) {
        ConfigUtil.appTheme = appTheme;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("appTheme", appTheme);
        editor.apply();
    }

    public static void setAutoBreak(boolean autoBreak) {
        ConfigUtil.autoBreak = autoBreak;
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("autoBreak", autoBreak);
        editor.apply();
    }

    public static void setSpellCheck(boolean spellCheck) {
        ConfigUtil.spellCheck = spellCheck;
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("spellCheck", spellCheck);
        editor.apply();
    }

    public static void setAppDayNight() {
        switch (appTheme) {
            case ConfigUtil.THEME_DAY -> setDefaultNightMode(MODE_NIGHT_NO);
            case ConfigUtil.THEME_NIGHT -> setDefaultNightMode(MODE_NIGHT_YES);
            case ConfigUtil.THEME_AUTO -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}
