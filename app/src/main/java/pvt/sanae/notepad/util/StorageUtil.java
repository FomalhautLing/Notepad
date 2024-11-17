package pvt.sanae.notepad.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class StorageUtil {

    private static final String TAG = "StorageUtil";

    private static final String pathname = "Notepad";
    private static final File defaultSavePath;

    static {
        defaultSavePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), pathname);
        if (!defaultSavePath.exists() && !defaultSavePath.mkdir()) {
            Log.w(TAG, "Unable to create default save path.");
        }
    }

    public static File getDefaultSavePath() {
        if (!defaultSavePath.exists() && !defaultSavePath.mkdir()) {
            Log.e(TAG, "save path not exist.");
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        }
        return defaultSavePath;
    }

}
