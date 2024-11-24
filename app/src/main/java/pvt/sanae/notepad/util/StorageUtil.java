package pvt.sanae.notepad.util;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;

import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.dialog.TernaryDialog;
import pvt.sanae.notepad.fragment.ContentFragment;

public class StorageUtil {

    private static final String TAG = "StorageUtil";

    private static final String pathname = "Notepad";
    private static final File defaultSavePath;

    static {
        defaultSavePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), pathname);
        if (!defaultSavePath.exists() && !defaultSavePath.mkdirs()) {
            Log.w(TAG, "unable to create save path.");
        }
    }

    public static File getDefaultSavePath() {
        if (!defaultSavePath.exists() && !defaultSavePath.mkdirs()) {
            Log.e(TAG, "save path not exist.");
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        }
        return defaultSavePath;
    }

    public static void saveContent(MainActivity ac, int position, boolean removeTab) {
        Log.d(TAG, "save content on " + position + ", remove: " + removeTab);

        ContentFragment cf = ac.mPage.getFragment(position);
        if (cf.contentNotChanged()) {
            if (removeTab) ac.mPage.removePage(position);
            return;
        }

        String descPath;
        Runnable positiveRunnable;
        if (cf.getUri() != null) {
            descPath = cf.getUri().getPath();
            positiveRunnable = () -> {
                try (OutputStream out = ac.getContentResolver().openOutputStream(cf.getUri())) {
                    if (out != null) {
                        out.write(cf.getContent().getBytes(StandardCharsets.UTF_8));
                        cf.saveContent();
                        if (removeTab) ac.mPage.removePage(position);
                    } else {
                        throw new IOException("Unable to open OutputStream.");
                    }
                } catch (IOException | SecurityException e) {
                    Log.e(TAG, "unable to save file: " + e);
                    ToastUtil.showShort(ac, "保存失败");
                }
            };
        } else {
            File savePath = getDefaultSavePath();
            String filename = cf.getFirstLine(10) + ".txt";
            descPath = savePath + "/" + filename;
            positiveRunnable = () -> {
                try {
                    File target = new File(savePath, filename);
                    if (target.createNewFile()) {
                        try (FileOutputStream out = new FileOutputStream(target)) {
                            out.write(cf.getContent().getBytes(StandardCharsets.UTF_8));
                            cf.setUri(Uri.fromFile(target));
                            cf.saveContent();
                            if (removeTab) ac.mPage.removePage(position);
                        }
                    } else {
                        throw new FileAlreadyExistsException(target.toString());
                    }
                } catch (IOException e) {
                    Log.e(TAG, "unable to save file: " + e);
                    ToastUtil.showShort(ac, "保存失败");
                }
            };
        }

        if (removeTab || cf.getUri() == null) {
            new TernaryDialog()
                    .setTitle("记事本")
                    .setDesc("是否要将更改保存到 " + descPath + "?")
                    .setPositiveButton("保存", positiveRunnable)
                    .setNegativeButton("不保存", () -> {
                        if (removeTab) ac.mPage.removePage(position);
                    })
                    .setNeutralButton("取消", null)
                    .show(ac.getSupportFragmentManager(), null);
        } else {
            positiveRunnable.run();
            cf.saveContent();
        }
    }
}
