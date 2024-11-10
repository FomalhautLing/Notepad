package pvt.sanae.notepad.util;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;


public class ToastUtil {

    Context context;

    private enum E {
        INSTANCE;
        final ToastUtil toast = new ToastUtil();
    }

    private ToastUtil() {}

    private static ToastUtil getInstance() {
        return E.INSTANCE.toast;
    }

    public static void init(Context context) {
        getInstance().context = context;
    }

    public static void showShort(Context context, String text) {
        android.widget.Toast.makeText(context, text, LENGTH_SHORT).show();
    }

    public static void showShort(String text) {
        showShort(getInstance().context, text);
    }

    public static void showLong(Context context, String text) {
        android.widget.Toast.makeText(context, text, LENGTH_LONG).show();
    }

    public static void showLong(String text) {
        showLong(getInstance().context, text);
    }
}
