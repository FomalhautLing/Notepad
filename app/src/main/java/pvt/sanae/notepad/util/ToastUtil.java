package pvt.sanae.notepad.util;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {

    private ToastUtil() {
    }

    public static void showShort(Context context, String text) {
        Toast.makeText(context, text, LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String text) {
        Toast.makeText(context, text, LENGTH_LONG).show();
    }

}
