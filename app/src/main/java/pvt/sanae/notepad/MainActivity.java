package pvt.sanae.notepad;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import java.io.IOException;
import java.io.InputStream;

import pvt.sanae.notepad.fragment.ContentFragment;
import pvt.sanae.notepad.manager.FooterManager;
import pvt.sanae.notepad.manager.NavbarManager;
import pvt.sanae.notepad.manager.PageManager;
import pvt.sanae.notepad.manager.ToolbarManager;
import pvt.sanae.notepad.util.ConfigUtil;
import pvt.sanae.notepad.util.IOUtil;
import pvt.sanae.notepad.util.StorageUtil;
import pvt.sanae.notepad.util.ToastUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public PageManager mPage;
    public NavbarManager mNavbar;
    public FooterManager mFooter;
    public ToolbarManager mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initManager();
        mPage.addPage(new ContentFragment());

        Uri uri = getIntent().getData();
        if (uri != null) handleIntent(uri);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConfigUtil.init(this);
        ConfigUtil.setAppDayNight();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            StorageUtil.saveContent(this, mPage.getCurrentPosition(), true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void handleIntent(Uri uri) {
        Log.i(TAG, "intent from: " + uri);
        DocumentFile documentFile = DocumentFile.fromSingleUri(this, uri);
        try (InputStream in = getContentResolver().openInputStream(uri)) {
            if (in != null && documentFile != null) {
                String content = IOUtil.readToString(in, "UTF-8");
                mPage.setPageData(0, ContentFragment.newInstance(uri, content));
                mNavbar.setCurrentText(documentFile.getName());
            } else {
                throw new IOException("Unable to open InputStream or documentFile is null.");
            }
        } catch (IOException e) {
            Log.e(TAG, "unable to handle intent: " + e);
            ToastUtil.showShort(this, "无法读取内容");
        }
    }

    private void initManager() {
        mPage = new PageManager(this);
        mNavbar = new NavbarManager(this);
        mFooter = new FooterManager(this);
        mToolbar = new ToolbarManager(this);
    }
}