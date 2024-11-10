package pvt.sanae.notepad;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.viewpager2.widget.ViewPager2;

import java.io.IOException;
import java.io.InputStream;

import pvt.sanae.notepad.adapter.PagerAdapter;
import pvt.sanae.notepad.fragment.ContentFragment;
import pvt.sanae.notepad.util.IOUtil;
import pvt.sanae.notepad.util.ToastUtil;
import pvt.sanae.notepad.view.TabItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private PagerAdapter adapter;
    private ViewPager2 pager;
    private LinearLayout navbar;
    private TextView infoPos;
    private TextView infoCount;
    private int lastPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUtil();
        bindAttrs();
        bindListener();

        Uri uri = getIntent().getData();
        if (uri != null) {
            Log.i(TAG, "intent from " + uri);
            DocumentFile documentFile = DocumentFile.fromSingleUri(this, uri);
            try (InputStream in = getContentResolver().openInputStream(uri)) {
                if (in != null && documentFile != null) {
                    String content = IOUtil.readToString(in, "UTF-8");
                    adapter.setContent(0, content);
                    ((TabItem) navbar.getChildAt(0)).setTitle(documentFile.getName());
                }
            } catch (IOException ignored) {
                ToastUtil.showShort("Error: Can not load content.");
            }
        }
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void setInfo(int row, int colum, int count) {
        infoPos.setText(String.format("行 %d，列 %d", row, colum));
        infoCount.setText(count + " 个字符");
    }

    public void setCurrentPosition(int position) {
        navbar.getChildAt(lastPosition).setSelected(false);
        pager.setCurrentItem(position);
        navbar.getChildAt(position).setSelected(true);
        lastPosition = position;
    }

    public void addTab() {
        navbar.addView(new TabItem(this));
        adapter.add(new ContentFragment());
        setCurrentPosition(adapter.getItemCount() - 1);
    }

    public void removeTab(int position) {
        if (navbar.getChildCount() == 1) {
            finish();
        } else {
            navbar.removeViewAt(position);
            adapter.remove(position);
            if (position < navbar.getChildCount()) {
                navbar.getChildAt(position).setSelected(true);
            }
            lastPosition = 0;
        }
    }

    private void initUtil() {
        ToastUtil.init(this);
    }

    private void bindAttrs() {
        adapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle());

        pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);

        navbar = findViewById(R.id.navbar);
        navbar.getChildAt(0).setSelected(true);

        infoPos = findViewById(R.id.info_pos);
        infoCount = findViewById(R.id.info_count);
    }

    private void bindListener() {
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
                navbar.getChildAt(lastPosition).setSelected(false);
                navbar.getChildAt(position).setSelected(true);
                lastPosition = position;
            }
        });
        findViewById(R.id.plus_tab).setOnClickListener(v -> addTab());
    }
}