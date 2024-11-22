package pvt.sanae.notepad;

import android.os.Bundle;

import androidx.annotation.Nullable;

import pvt.sanae.notepad.util.ConfigUtil;
import pvt.sanae.notepad.view.SettingOption;

public class SettingActivity extends ViewActivity {

    private SettingOption appTheme;
    private SettingOption autoBreak;
    private SettingOption spellCheck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        getWindow().setWindowAnimations(R.style.onThemeChange);
        appTheme.setSubOption(ConfigUtil.getAppTheme());
        autoBreak.setChecked(ConfigUtil.isAutoBreak());
        spellCheck.setChecked(ConfigUtil.isSpellCheck());
    }

    @Override
    protected void onPause() {
        super.onPause();
        ConfigUtil.setAppTheme(appTheme.getSubOption());
        ConfigUtil.setAutoBreak(autoBreak.isChecked());
        ConfigUtil.setSpellCheck(spellCheck.isChecked());
    }

    @Override
    protected void initView() {
        appTheme = findViewById(R.id.appTheme);
        autoBreak = findViewById(R.id.autoBreak);
        spellCheck = findViewById(R.id.spellCheck);
    }

    @Override
    protected void bindListener() {
        findViewById(R.id.backBtn).setOnClickListener(v -> finish());
    }
}
