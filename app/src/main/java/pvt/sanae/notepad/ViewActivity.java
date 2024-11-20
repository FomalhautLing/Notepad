package pvt.sanae.notepad;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 直接管理 View 的 Activity
 */

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        initView();
        bindListener();
    }

    protected void initView() {
    }

    protected void bindListener() {
    }
}
