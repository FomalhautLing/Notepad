package pvt.sanae.notepad.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;

import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.R;
import pvt.sanae.notepad.dialog.TernaryDialog;
import pvt.sanae.notepad.fragment.ContentFragment;
import pvt.sanae.notepad.util.StorageUtil;
import pvt.sanae.notepad.util.ToastUtil;

public class NavbarItem extends FrameLayout {

    private static final String TAG = "NavbarItem";

    private final MainActivity ac;
    private final TextView text;

    public NavbarItem(@NonNull Context context) {
        this(context, null);
    }

    public NavbarItem(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.view_navbar_item, this);
        text = view.findViewById(R.id.text);
        ac = (MainActivity) context;

        if (attrs != null) {
            try (TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.NavbarItem)) {
                String s = t.getString(R.styleable.NavbarItem_text);
                setText(s);
            }
        }

        view.findViewById(R.id.close).setOnClickListener(this::onRemove);
        setOnClickListener(v -> ac.mPage.setCurrentPosition(ac.mNavbar.indexOfItem(this)));
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    private void onRemove(View v) {
        ContentFragment cf = ac.mPage.getCurrentFragment();
        if (cf.contentNotChanged()) {
            ac.mPage.removePage(ac.mNavbar.indexOfItem(this));
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
                        ac.mPage.removePage(ac.mNavbar.indexOfItem(this));
                    } else {
                        throw new IOException("Unable to open OutputStream.");
                    }
                } catch (IOException | SecurityException e) {
                    Log.e(TAG, "unable to save file: " + e);
                    ToastUtil.showShort(ac, "保存失败");
                }
            };
        } else {
            File savePath = StorageUtil.getDefaultSavePath();
            String filename = cf.getFirstLine(10) + ".txt";
            descPath = savePath + "/" + filename;
            positiveRunnable = () -> {
                try {
                    File target = new File(savePath, filename);
                    if (target.createNewFile()) {
                        try (FileOutputStream out = new FileOutputStream(target)) {
                            out.write(cf.getContent().getBytes(StandardCharsets.UTF_8));
                            ac.mPage.removePage(ac.mNavbar.indexOfItem(this));
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

        new TernaryDialog()
                .setTitle("记事本")
                .setDesc("是否要将更改保存到 " + descPath + "?")
                .setPositiveButton("保存", positiveRunnable)
                .setNegativeButton("不保存", ac.mPage::removeCurrentPage)
                .setNeutralButton("取消", null)
                .show(ac.getSupportFragmentManager(), null);
    }
}
